package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodsConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class EGoodsData extends OPlatformData {

	private int e_goods_id = 0;

	private int e_goods_sn = 0;

	private int trans_id = 0;

	private int customer_id = 0;

	private String description = null;

	private String info = null;

	private String create_time = null;

	private String create_time_min = null;

	private String create_time_max = null;

	private String bolish_time = null;

	private String bolish_time_min = null;

	private String bolish_time_max = null;

	private String used_time = null;

	private String used_time_min = null;

	private String used_time_max = null;

	private String status = null;

	private String third_status = null;

	private String addition = null;
	
	private String validity = null ; 

	public void parseData(String text) {
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber) jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][13];
		for (int i = 0; i < arr.size(); i++) {
			JSONObject channel = arr.get(i).isObject();
			try {
				data[i][0] = channel.get(EGoodsConstanst.E_GOODS_ID).isNumber()
						.toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = channel.get(EGoodsConstanst.E_GOODS_SN).isNumber()
						.toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = channel.get(EGoodsConstanst.TRANS_ID).isNumber()
						.toString();
			} catch (Exception e) {
			}
			try {
				data[i][3] = channel.get(EGoodsConstanst.CUSTOMER_ID).isNumber()
						.toString();
			} catch (Exception e) {
			}
			try {
				data[i][4] = channel.get(EGoodsConstanst.DESCRIPTION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][5] = channel.get(EGoodsConstanst.INFO).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][6] = channel.get(EGoodsConstanst.CREATE_TIME).isString().stringValue();
			} catch (Exception e) {
			}			
			try {
				data[i][7] = channel.get(EGoodsConstanst.BOLISH_TIME).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][8] = channel.get(EGoodsConstanst.USED_TIME).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][9] = channel.get(EGoodsConstanst.STATUS).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][10] = channel.get(EGoodsConstanst.THIRD_STATUS).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][11] = channel.get(EGoodsConstanst.ADDITION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][12] = channel.get(EGoodsConstanst.VALIDITY).isString().stringValue();
			} catch (Exception e) {
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
			if(Constanst.ACTION_NAME_QUERY_GOODS_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_GOODS_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_GOODS_INFO.equals(ACTION_NAME)){
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
		if(e_goods_id!=0){
			json.put(EGoodsConstanst.E_GOODS_ID, new JSONNumber(e_goods_id));
		}
		if(e_goods_sn!=0){
			json.put(EGoodsConstanst.E_GOODS_SN, new JSONNumber(e_goods_sn));
		}
		if(trans_id!=0){
			json.put(EGoodsConstanst.TRANS_ID, new JSONNumber(trans_id));
		}
		if(customer_id!=0){
			json.put(EGoodsConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		if(description != null && !description.equals("")){
			json.put(EGoodsConstanst.DESCRIPTION, new JSONString(description));
		}
		if(info != null && !info.equals("")){
			json.put(EGoodsConstanst.INFO, new JSONString(info));
		}
		if(create_time != null && !create_time.equals("")){
			json.put(EGoodsConstanst.CREATE_TIME, new JSONString(create_time));
		}else {
			if(create_time_min != null && !create_time_min.trim().equals("")){
				json.put(EGoodsConstanst.CREATE_TIME_MIN, new JSONString(create_time_min));
			}
			if(create_time_max != null && !create_time_max.trim().equals("")){
				json.put(EGoodsConstanst.CREATE_TIME_MAX, new JSONString(create_time_max));
			}
		}
		if(create_time != null && !create_time.equals("")){
			json.put(EGoodsConstanst.CREATE_TIME, new JSONString(create_time));
		}else {
			if(create_time_min != null && !create_time_min.trim().equals("")){
				json.put(EGoodsConstanst.CREATE_TIME_MIN, new JSONString(create_time_min));
			}
			if(create_time_max != null && !create_time_max.trim().equals("")){
				json.put(EGoodsConstanst.CREATE_TIME_MAX, new JSONString(create_time_max));
			}
		}
		if(bolish_time != null && !bolish_time.equals("")){
			json.put(EGoodsConstanst.BOLISH_TIME, new JSONString(bolish_time));
		}else {
			if(bolish_time_min != null && !bolish_time_min.trim().equals("")){
				json.put(EGoodsConstanst.BOLISH_TIME_MIN, new JSONString(bolish_time_min));
			}
			if(bolish_time_max != null && !bolish_time_max.trim().equals("")){
				json.put(EGoodsConstanst.BOLISH_TIME_MAX, new JSONString(bolish_time_max));
			}
		}
		if(used_time != null && !used_time.equals("")){
			json.put(EGoodsConstanst.USED_TIME, new JSONString(used_time));
		}else {
			if(used_time_min != null && !used_time_min.trim().equals("")){
				json.put(EGoodsConstanst.USED_TIME_MIN, new JSONString(used_time_min));
			}
			if(used_time_max != null && !used_time_max.trim().equals("")){
				json.put(EGoodsConstanst.USED_TIME_MAX, new JSONString(used_time_max));
			}
		}
		if(status != null && !status.equals("")){
			json.put(EGoodsConstanst.STATUS, new JSONString(status));
		}
		if(third_status != null && !third_status.equals("")){
			json.put(EGoodsConstanst.THIRD_STATUS, new JSONString(third_status));
		}
		if(addition != null && !addition.equals("")){
			json.put(EGoodsConstanst.ADDITION, new JSONString(addition));
		}
		if(validity != null && !validity.equals("")){
			json.put(EGoodsConstanst.VALIDITY, new JSONString(validity));
		}
		return json ;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(EGoodsConstanst.E_GOODS_ID, new JSONNumber(e_goods_id));
		if(EGoodsConstanst.E_GOODS_SN_ZH.equals(colName)){
			json.put(EGoodsConstanst.E_GOODS_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(EGoodsConstanst.CUSTOMER_ID_ZH.equals(colName)){
			json.put(EGoodsConstanst.CUSTOMER_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(EGoodsConstanst.TRANS_ID_ZH.equals(colName)){
			json.put(EGoodsConstanst.TRANS_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(EGoodsConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(EGoodsConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(EGoodsConstanst.INFO_ZH.equals(colName)){
			json.put(EGoodsConstanst.INFO, new JSONString(colValue));
		}else if(EGoodsConstanst.CREATE_TIME_ZH.equals(colName)){
			json.put(EGoodsConstanst.CREATE_TIME, new JSONString(colValue));
		}else if(EGoodsConstanst.BOLISH_TIME_ZH.equals(colName)){
			json.put(EGoodsConstanst.BOLISH_TIME, new JSONString(colValue));
		}else if(EGoodsConstanst.USED_TIME_ZH.equals(colName)){
			json.put(EGoodsConstanst.USED_TIME, new JSONString(colValue));
		}else if(EGoodsConstanst.STATUS_ZH.equals(colName)){
			json.put(EGoodsConstanst.STATUS, new JSONString(colValue));
		}else if(EGoodsConstanst.THIRD_STATUS_ZH.equals(colName)){
			json.put(EGoodsConstanst.THIRD_STATUS, new JSONString(colValue));
		}else if(EGoodsConstanst.ADDITION_ZH.equals(colName)){
			json.put(EGoodsConstanst.ADDITION, new JSONString(colValue));
		}else if(EGoodsConstanst.VALIDITY_ZH.equals(colName)){
			json.put(EGoodsConstanst.VALIDITY, new JSONString(colValue));
		}
		
		return json ;
	}
	public int getE_goods_id() {
		return e_goods_id;
	}

	public void setE_goods_id(int e_goods_id) {
		this.e_goods_id = e_goods_id;
	}

	public int getE_goods_sn() {
		return e_goods_sn;
	}

	public void setE_goods_sn(int e_goods_sn) {
		this.e_goods_sn = e_goods_sn;
	}

	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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

	public String getBolish_time() {
		return bolish_time;
	}

	public void setBolish_time(String bolish_time) {
		this.bolish_time = bolish_time;
	}

	public String getBolish_time_min() {
		return bolish_time_min;
	}

	public void setBolish_time_min(String bolish_time_min) {
		this.bolish_time_min = bolish_time_min;
	}

	public String getBolish_time_max() {
		return bolish_time_max;
	}

	public void setBolish_time_max(String bolish_time_max) {
		this.bolish_time_max = bolish_time_max;
	}

	public String getUsed_time() {
		return used_time;
	}

	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}

	public String getUsed_time_min() {
		return used_time_min;
	}

	public void setUsed_time_min(String used_time_min) {
		this.used_time_min = used_time_min;
	}

	public String getUsed_time_max() {
		return used_time_max;
	}

	public void setUsed_time_max(String used_time_max) {
		this.used_time_max = used_time_max;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getThird_status() {
		return third_status;
	}

	public void setThird_status(String third_status) {
		this.third_status = third_status;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

}
