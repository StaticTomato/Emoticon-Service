/**
 * 
 */
package com.statictomato.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Thomas Asheim Smedmann
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) // Only convert private field variables.
@Entity
@Table(schema = "emoticons", name = "emoticon")
public class Emoticon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String icon;
	private String description;
	
	public Emoticon() {
		// Do nothing
	}
	
	public Emoticon(final Emoticon emoticon) {
		this.id = emoticon.getId();
		this.icon = emoticon.getIcon();
		this.description = emoticon.getDescription();
	}
	
	public Emoticon(String icon, String description) {
		this.icon = icon;
		this.description = description;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void populate(final Emoticon emoticon) {
		this.icon = emoticon.getIcon();
		this.description = emoticon.getDescription();
	}
	
}
