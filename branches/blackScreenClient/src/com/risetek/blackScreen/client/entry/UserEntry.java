package com.risetek.blackScreen.client.entry;

import java.sql.Date;
import java.sql.Timestamp;

public class UserEntry {

	private int id;
	
	private String userName;
	
	private String passWord;
	
	private int level;
	
	private Date registTime;
	
	private Timestamp lastLoginTime;
	
	private String lastLoginAddress;
	
	private String notes;
	
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp timestamp) {
		this.lastLoginTime = timestamp;
	}

	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
