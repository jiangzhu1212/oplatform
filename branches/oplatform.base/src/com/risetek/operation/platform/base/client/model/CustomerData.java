package com.risetek.operation.platform.base.client.model;


import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CustomerData extends OPlatformData  {
	/**
	 * 客户编号
	 */
	private String customer_id = ""+0;
	
	/**
	 * 客户名
	 */	
	private String name = null;
	
	/**
	 * 电话号码
	 */
	private String phone = null;
	
	/**
	 * 地址
	 */
	private String address = null;
	
	/**
	 * 地址2
	 */
	private String address_2 = null;
	
	/**
	 * email
	 */
	private String email = null;
	
	/**
	 * 银行
	 */
	private String card_id = null;
	
	/**
	 * 创建时间
	 */
	private String create_time = null;
	
	/**
	 * 实效时间
	 */
	private String validity = null;
	
	/**
	 * 预留
	 */
	private String addition = null;

	public void parseData(String text){
		
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}
		
}
