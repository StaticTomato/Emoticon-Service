/**
 * 
 */
package com.statictomato.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Thomas Asheim Smedmann
 *
 */
@Entity
@Table(schema = "emoticons", name = "log")
public class Log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer emoticonId;
	private String event;
	
	public Log() {
		// Do nothing
	}
	
	public Log(Integer emoticonId, String event) {
		this.emoticonId = emoticonId;
		this.event = event;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the emoticonId
	 */
	public Integer getEmoticonId() {
		return emoticonId;
	}

	/**
	 * @param emoticonId the emoticonId to set
	 */
	public void setEmoticonId(Integer emoticonId) {
		this.emoticonId = emoticonId;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	
}
