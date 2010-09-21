package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CustomerData extends OPlatformData  {
	
	/**
	 * 客户编号
	 */
	private int customer_id = 0;
	
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
	private String id_card = null;
	
	/**
	 * 创建时间
	 */
	private String create_time = null;
	/**
	 * 创建时间最小值
	 */
	private String create_time_min = null;
	/**
	 * 创建时间最大值
	 */
	private String create_time_max = null;
	
	/**
	 * 是否有效
	 */
	private String validity = null;
	
	/**
	 * 用户附加信息
	 */
	private String addition = null;

	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject customer = arr.get(i).isObject();
			try {
				try {
					data[i][0] = customer.get(CustomerConstanst.CUSTOMER_ID)
							.isNumber().toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][1] = customer.get(CustomerConstanst.NAME)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][2] = customer.get(CustomerConstanst.PHONE)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][3] = customer.get(CustomerConstanst.ADDRESS)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][4] = customer.get(CustomerConstanst.ADDRESS_2)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][5] = customer.get(CustomerConstanst.EMAIL)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][6] = customer.get(CustomerConstanst.ID_CARD)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][7] = customer.get(CustomerConstanst.CREATE_TIME)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][8] = customer.get(CustomerConstanst.VALIDITY)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][9] = customer.get(CustomerConstanst.ADDITION)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		setData(data);
	}
	
	public String toHttpPacket(String... col){
		String ACTION_NAME = getACTION_NAME();
		JSONObject packet = new JSONObject();
		JSONObject actionInfo = null;
		try {
			packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));	
			if(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_CUSTOMER_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_CUSTOMER_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
			}
			packet.put(Constanst.ACTION_INFO,actionInfo);
			packet.put(Constanst.ACTION_INVOKER,new JSONString(Constanst.ACTION_INVOKER_WEB_CLIENT));
			packet.put(Constanst.ACTION_MODULE,new JSONString(Constanst.ACTION_MODULE_MY_SETTLEMENT_SERVICE));
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}

		return packet.toString();
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(customer_id != 0){
			json.put(CustomerConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		if(name != null && !name.trim().equals("")){
			json.put(CustomerConstanst.NAME, new JSONString(name));
		}
		if(phone != null && !phone.trim().equals("")){
			json.put(CustomerConstanst.PHONE, new JSONString(phone));
		}
		if(address != null && !address.trim().equals("")){
			json.put(CustomerConstanst.ADDRESS, new JSONString(address));
		}
		if(address_2 != null && !address_2.trim().equals("")){
			json.put(CustomerConstanst.ADDRESS_2, new JSONString(address_2));
		}
		if(email != null && !email.trim().equals("")){
			json.put(CustomerConstanst.EMAIL, new JSONString(email));
		}
		if(id_card != null && !id_card.trim().equals("")){
			json.put(CustomerConstanst.ID_CARD, new JSONString(id_card));
		}
		if(create_time != null && !create_time.trim().equals("")){
			json.put(CustomerConstanst.CREATE_TIME, new JSONString(create_time));
		}else {
			if(create_time_min != null && !create_time_min.trim().equals("")){
				json.put(CustomerConstanst.CREATE_TIME_MIN, new JSONString(create_time_min));
			}
			if(create_time_max != null && !create_time_max.trim().equals("")){
				json.put(CustomerConstanst.CREATE_TIME_MAX, new JSONString(create_time_max));
			}
		}
		if(validity != null && !validity.trim().equals("")){
			json.put(CustomerConstanst.VALIDITY, new JSONString(validity));
		}
		if(addition != null && !addition.trim().equals("")){
			json.put(CustomerConstanst.ADDITION, new JSONString(addition));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CustomerConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
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
		}else if(CustomerConstanst.ID_CARD_ZH.equals(colName)){
			json.put(CustomerConstanst.ID_CARD, new JSONString(colValue));
		}else if(CustomerConstanst.CREATE_TIME_ZH.equals(colName)){
			json.put(CustomerConstanst.CREATE_TIME, new JSONString(colValue));
		}else if(CustomerConstanst.VALIDITY_ZH.equals(colName)){
			json.put(CustomerConstanst.VALIDITY, new JSONString(colValue));
		}else if(CustomerConstanst.ADDITION_ZH.equals(colName)){
			json.put(CustomerConstanst.ADDITION, new JSONString(colValue));
		}
		return json;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
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
	
	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
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

	public String getCreate_time_min() {
		return create_time_min;
	}

	public void setCreate_time_min(String create_time_min) {
		this.create_time_min = create_time_min;
	}

	public String getCreate_time_max() {
		return create_time_max;
	}

	public void setCreate_time_max(String create_time_max) {
		this.create_time_max = create_time_max;
	}
	
}
