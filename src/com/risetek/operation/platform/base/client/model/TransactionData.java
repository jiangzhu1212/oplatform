package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransactionData extends OPlatformData {
	
	/**
	 * 业务号
	 */
	private String trans_id = "" + 0;
	
	/**
	 * 
	 */
	private String alias = null;
	
	/**
	 * 
	 */
	private String name = null;
	
	/**
	 * 
	 */
	private String description = null;
	
	/**
	 * 
	 */
	private String url = null;
	
	/**
	 * 
	 */
	private String bindable = null;
	
	/**
	 * 
	 */
	private String merchant_number = null;
	
	/**
	 * 
	 */
	private String pos_number = null;
	
	/**
	 * 
	 */
	private String type = null;
	
	/**
	 * 
	 */
	private String addition = null;
	
	public void parseData(String text){
		
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBindable() {
		return bindable;
	}

	public void setBindable(String bindable) {
		this.bindable = bindable;
	}

	public String getMerchant_number() {
		return merchant_number;
	}

	public void setMerchant_number(String merchant_number) {
		this.merchant_number = merchant_number;
	}

	public String getPos_number() {
		return pos_number;
	}

	public void setPos_number(String pos_number) {
		this.pos_number = pos_number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}
	
	
}
