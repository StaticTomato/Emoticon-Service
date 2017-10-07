/**
 * 
 */
package com.statictomato.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Thomas Asheim Smedmann
 *
 */
@XmlRootElement // @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER) is default.
public class Updates {

	private Integer logId;
	private List<Emoticon> newEmoticons;
	private List<Emoticon> updatedEmoticons;
	private List<Integer> deletedEmoticons;
	
	public Updates() {
		// Nothing to do
	}
	
	public Updates(Integer logId, List<Emoticon> newEmoticons, 
			List<Emoticon> updatedEmoticons, List<Integer> deletedEmoticons) {
		this.logId = logId;
		this.newEmoticons = newEmoticons;
		this.updatedEmoticons = updatedEmoticons;
		this.deletedEmoticons = deletedEmoticons;
	}

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the newEmoticons
	 */
	public List<Emoticon> getNewEmoticons() {
		return newEmoticons;
	}

	/**
	 * @param newEmoticons the newEmoticons to set
	 */
	public void setNewEmoticons(List<Emoticon> newEmoticons) {
		this.newEmoticons = newEmoticons;
	}

	/**
	 * @return the updatedEmoticons
	 */
	public List<Emoticon> getUpdatedEmoticons() {
		return updatedEmoticons;
	}

	/**
	 * @param updatedEmoticons the updatedEmoticons to set
	 */
	public void setUpdatedEmoticons(List<Emoticon> updatedEmoticons) {
		this.updatedEmoticons = updatedEmoticons;
	}

	/**
	 * @return the deletedEmoticons
	 */
	public List<Integer> getDeletedEmoticons() {
		return deletedEmoticons;
	}

	/**
	 * @param deletedEmoticons the deletedEmoticons to set
	 */
	public void setDeletedEmoticons(List<Integer> deletedEmoticons) {
		this.deletedEmoticons = deletedEmoticons;
	}
	
}
