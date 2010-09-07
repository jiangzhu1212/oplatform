package com.risetek.operation.platform.base.client.model;


import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CustomerData extends OPlatformData  {
	
	private int sum;
	
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
	 * 是否有效
	 */
	private String validity = null;
	
	/**
	 * 用户附加信息
	 */
	private String addition = null;
	
	private String action_name = null;

	public void parseData(String text){
		
	}
	
	public String toHttpPacket(){
		JSONObject packet = new JSONObject();
		JSONObject actionInfo = null;
		try {
			packet.put(Constanst.ACTION_NAME, new JSONString(action_name));	
			if(action_name == null){
				actionInfo = new JSONObject();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(0));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(50));

			}else if(Constanst.ACTION_NAME_SELECT_JCARD.equals(action_name)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(0));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(50));

			}else if(Constanst.ACTION_NAME_MODIFY_STATUS.equals(action_name)){
				actionInfo = packetData();
			}
			packet.put(Constanst.ACTION_INFO,actionInfo);
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(RequestFactory.CTI_PACKET);
		buffer.append("=");
		buffer.append(packet.toString());
		return buffer.toString();
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(!customer_id.equals("0")){
			json.put(CustomerConstanst.CUSTOMER_ID, new JSONString(customer_id));
		}
		if(name != null && !name.equals("")){
			json.put(CustomerConstanst.NAME, new JSONString(name));
		}
		if(phone != null && !phone.equals("")){
			json.put(CustomerConstanst.PHONE, new JSONString(phone));
		}
		if(address != null && !address.equals("")){
			json.put(CustomerConstanst.ADDRESS, new JSONString(address));
		}
		if(address_2 != null && !address_2.equals("")){
			json.put(CustomerConstanst.ADDRESS_2, new JSONString(address_2));
		}
		if(email != null && !email.equals("")){
			json.put(CustomerConstanst.EMAIL, new JSONString(email));
		}
		if(card_id != null && !card_id.equals("")){
			json.put(CustomerConstanst.CARD_ID, new JSONString(card_id));
		}
		if(create_time != null && !create_time.equals("")){
			json.put(CustomerConstanst.CREATE_TIME, new JSONString(create_time));
		}
		
		if(validity != null && !validity.equals("")){
			json.put(CustomerConstanst.VALIDITY, new JSONString(validity));
		}
		if(addition != null && !addition.equals("")){
			json.put(CustomerConstanst.ADDITION, new JSONString(addition));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CustomerConstanst.CUSTOMER_ID, new JSONString(customer_id));
		if(CustomerConstanst.NAME_ZH.equals(colName)){
			json.put(CustomerConstanst.NAME, new JSONString(colValue));
		}else if(CustomerConstanst.PHONE_ZH.equals(colName)){
			json.put(CustomerConstanst.PHONE, new JSONString(colValue));
		}else if(CustomerConstanst.ADDRESS_ZH.equals(colName)){
			json.put(CustomerConstanst.ADDRESS, new JSONString(colValue));
		}else if(CustomerConstanst.ADDRESS_2_ZH.equals(colName)){
			json.put(CustomerConstanst.ADDRESS_2, new JSONString(colValue));
		}else if(CustomerConstanst.EMAIL_ZH.equals(colName)){
			json.put(CustomerConstanst.EMAIL, new JSONString(colValue));
		}else if(CustomerConstanst.CARD_ID_ZH.equals(colName)){
			json.put(CustomerConstanst.CARD_ID, new JSONString(colValue));
		}else if(CustomerConstanst.CREATE_TIME_ZH.equals(colName)){
			json.put(CustomerConstanst.CREATE_TIME, new JSONString(colValue));
		}else if(CustomerConstanst.VALIDITY_ZH.equals(colName)){
			json.put(CustomerConstanst.VALIDITY, new JSONString(colValue));
		}else if(CustomerConstanst.ADDITION_ZH.equals(colName)){
			json.put(CustomerConstanst.ADDITION, new JSONString(colValue));
		}
		return json;
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

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}
		
}
