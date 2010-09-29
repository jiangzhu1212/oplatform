package com.risetek.operation.platform.launch.client.entry;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String content;
	
	private String roles;
	
	private Timestamp addTime;
	
	private Timestamp effectTime;
	
	private Timestamp failureTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Timestamp getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Timestamp effectTime) {
		this.effectTime = effectTime;
	}

	public Timestamp getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(Timestamp failureTime) {
		this.failureTime = failureTime;
	}

}
