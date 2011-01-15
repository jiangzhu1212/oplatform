package com.risetek.test.appeng.file.client;

import java.io.Serializable;

public class FileEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String floder;
	
	private String size;
	
	private String creatTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFloder() {
		return floder;
	}

	public void setFloder(String floder) {
		this.floder = floder;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

}
