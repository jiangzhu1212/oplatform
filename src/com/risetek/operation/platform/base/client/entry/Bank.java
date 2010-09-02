package com.risetek.operation.platform.base.client.entry;

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
	
	private String bank_code;
	
	private String name;
	
	private String validity;
	
	private String description;


	/**
	 * @return bank_code
	 */
	public String getBank_code() {
		return bank_code;
	}

	/**
	 * @param bank_code
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
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
