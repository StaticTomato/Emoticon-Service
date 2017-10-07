/**
 * 
 */
package com.statictomato.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.statictomato.model.Emoticon;
import com.statictomato.model.Log;
import com.statictomato.model.Updates;

/**
 * @author Thomas Asheim Smedmann
 *
 */
@Stateless
public class EmoticonEAO {

	@PersistenceContext(name = "persistenceUnit")
	private EntityManager em;
	
	public void addEmoticon(final Emoticon emoticon) {
		emoticon.setId(null); // Need to be auto generated when persisted.
		em.persist(emoticon);
		em.flush();
		addLog(emoticon.getId(),"create");
	}
	
	public boolean updateEmoticon(Integer id, Emoticon emoticon) {
		Emoticon e = em.find(Emoticon.class, id);
		if(e == null) {
			return false;
		}
		e.populate(emoticon);
		em.merge(e);
		addLog(e.getId(),"update");
		return true;
	}
	
	public boolean deleteEmoticon(final Integer id) {
		Emoticon e = em.find(Emoticon.class, id);
		if(e == null) {
			return false;
		}
		em.remove(e);
		addLog(id,"delete");
		return true;
	}
	
	public Emoticon getEmoticon(final Integer id) {
		Emoticon e = em.find(Emoticon.class, id);
		if(e == null) {
			return null;
		}
		return new Emoticon(e);
	}
	
	public Updates getUpdates(final Integer logId) {
		
		if(logId < 0) {
			return getAllEmoticonsAsUpdates();
		}
		
		TypedQuery<Log> query = 
				em.createQuery("SELECT DISTINCT l FROM Log l WHERE l.id > ?1", Log.class).setParameter(1, logId);
		List<Log> logList = query.getResultList();
		
		if(logList.isEmpty()) {
			return null;
		}
		
		Map<Integer,Log> idMap = extractLatestLogEntries(logList);
		
		return createUpdatesFromLogEntries(idMap);
	}
	
	public Integer getMaxLogId() {
		Query query = em.createNativeQuery("SELECT MAX(id) AS id FROM emoticons.log");
		return (Integer) query.getSingleResult();
	}
	
	private void addLog(Integer emoticonId, String event) {
		em.persist(new Log(emoticonId,event));
		em.flush();
	}
	
	private Updates getAllEmoticonsAsUpdates() {
		
		List<Emoticon> emoticons = null;
		int logId1 = 0;
		int logId2 = 1;
		while(logId1 != logId2) { // Making sure i get the correct logId.
			logId1 = getMaxLogId();
			TypedQuery<Emoticon> query = em.createQuery("SELECT e FROM Emoticon e ORDER BY e.id DESC", Emoticon.class);
			emoticons = query.getResultList();
			logId2 = getMaxLogId();
		}
		return new Updates(logId1,emoticons,null,null);
	}
	
	private Map<Integer,Log> extractLatestLogEntries(final List<Log> logList) {
		
		Map<Integer,Log> idMap = new HashMap<Integer,Log>();
		Log log;
		for(Log l : logList) {
			log = idMap.get(l.getEmoticonId());
			if(log != null) {
				if(l.getId() > log.getId()) {
					idMap.put(l.getEmoticonId(), l);
				}
			} else {
				idMap.put(l.getEmoticonId(), l);
			}
		}
		return idMap;
	}
	
	private Updates createUpdatesFromLogEntries(final Map<Integer,Log> idMap) {
		
		Integer maxLogId = 0;
		List<Emoticon> newEmoticons = new ArrayList<Emoticon>();
		List<Emoticon> updatedEmoticons = new ArrayList<Emoticon>();
		List<Integer> deletedEmoticons = new ArrayList<Integer>();
		
		for(Log l : idMap.values()) {
			
			if(l.getId() > maxLogId) {
				maxLogId = l.getId();
			}
			try {
				switch (l.getEvent()) {
				case "create":
					newEmoticons.add(new Emoticon(em.find(Emoticon.class, l.getEmoticonId())));
					break;
				case "update":
					updatedEmoticons.add(new Emoticon(em.find(Emoticon.class, l.getEmoticonId())));
					break;
				case "delete":
					deletedEmoticons.add(l.getEmoticonId());
					break;
				default:
					// Do nothing
					break;
				}
			} catch(Exception e) {
				// Ignore, the next update will catch the change.
			}
		}
		
		return new Updates(maxLogId,newEmoticons,updatedEmoticons,deletedEmoticons);
	}
}
