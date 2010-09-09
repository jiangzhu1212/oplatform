package com.risetek.operation.platform.launch.client.json.constanst;

import java.io.Serializable;

/** 
 * @ClassName: Bank 
 * @Description: 发卡行实体
 * @author JZJ 
 * @date 2010-9-2 下午02:05:43 
 * @version 1.0 
 */
public class Bank implements Serializable {
	/** 
	 * @Fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String bank_id = null;
	
	private String PREFIX = null ;
	
	private String name = null;
	
	private String validity = null;
	
	private String description = null;

	
	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getPREFIX() {
		return PREFIX;
	}

	public void setPREFIX(String pREFIX) {
		PREFIX = pREFIX;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return validity
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 */
	public void setValidity(String validity) {
		this.validity = validity;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
