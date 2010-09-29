package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TradeConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TradeData extends OPlatformData {
	
	private int trade_id = 0 ;
	
	private int customer_id = 0 ;
	
	private int trans_id = 0 ;
	
	private String third_id = null ;
	
	private String amount = null ;
	
	private String status = null ;
	
	private String third_status = null ;
	
	private String create_time = null ;
	
	private String create_time_min = null ;
	
	private String create_time_max = null ;
	
	private String bolish_time = null ;
	
	private String bolish_time_min = null ;
	
	private String bolish_time_max = null ;
	
	private String description = null ;
	
	private String addition = null ;
	
	private String validity = null ;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		String jsonStr = jo.get(Constanst.ACTION_INFO).isString().stringValue();
		JSONObject actionInfo = JSONParser.parse(jsonStr).isObject();
		JSONNumber item_total = (JSONNumber)actionInfo.get(Constanst.QUERY_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONArray arr = actionInfo.get(Constanst.QUERY_DATA).isArray();
		String[][] data = new String[arr.size()][5];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject json = arr.get(i).isObject();

			try {
				data[i][0] = json.get(TradeConstanst.TRADE_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = json.get(TradeConstanst.CUSTOMER_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = json.get(TradeConstanst.TRANS_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][3] = json.get(TradeConstanst.AMOUNT)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = json.get(TradeConstanst.STATUS)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][5] = json.get(TradeConstanst.THIRD_STATUS)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][6] = json.get(TradeConstanst.CREATE_TIME)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][7] = json.get(TradeConstanst.BOLISH_TIME)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][8] = json.get(TradeConstanst.DESCRIPTION)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][9] = json.get(TradeConstanst.VALIDITY)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][10] = json.get(TradeConstanst.ADDITION)
						.isString().stringValue();
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
			if(Constanst.ACTION_NAME_QUERY_TRADE_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_TRADE_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_TRADE_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
			}
			packet.put(Constanst.ACTION_INFO,actionInfo);
			packet.put(Constanst.ACTION_INVOKER,new JSONString(Constanst.ACTION_INVOKER_WEB_CLIENT));
			packet.put(Constanst.ACTION_MODULE,new JSONString(Constanst.ACTION_MODULE_DATABASE));
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}

		return packet.toString();
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(trade_id!=0){
			json.put(TradeConstanst.TRADE_ID, new JSONNumber(trade_id));
		}
		if(customer_id!=0){
			json.put(TradeConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		if(trans_id!=0){
			json.put(TradeConstanst.TRANS_ID, new JSONNumber(trans_id));
		}
		if(third_id != null && !third_id.equals("")){
			json.put(TradeConstanst.THIRD_ID, new JSONString(third_id));
		}
		if(amount != null && !amount.equals("")){
			json.put(TradeConstanst.AMOUNT, new JSONString(amount));
		}
		if(status != null && !status.equals("")){
			json.put(TradeConstanst.STATUS, new JSONString(status));
		}
		if(third_status != null && !third_status.equals("")){
			json.put(TradeConstanst.THIRD_STATUS, new JSONString(third_status));
		}
		if(create_time != null && !create_time.equals("")){
			json.put(TradeConstanst.CREATE_TIME, new JSONString(create_time));
		}else {
			if(create_time_min != null && !create_time_min.equals("")){
				json.put(TradeConstanst.CREATE_TIME_MIN, new JSONString(create_time_min));
			}
			if(create_time_max != null && !create_time_max.equals("")){
				json.put(TradeConstanst.CREATE_TIME_MAX, new JSONString(create_time_max));
			}
		}
		if(bolish_time != null && !bolish_time.equals("")){
			json.put(TradeConstanst.BOLISH_TIME, new JSONString(bolish_time));
		}else{
			if(bolish_time_min != null && !bolish_time_min.equals("")){
				json.put(TradeConstanst.BOLISH_TIME_MIN, new JSONString(bolish_time_min));
			}
			if(bolish_time_max != null && !bolish_time_max.equals("")){
				json.put(TradeConstanst.BOLISH_TIME_MAX, new JSONString(bolish_time_max));
			}
		}
		if(description != null && !description.equals("")){
			json.put(TradeConstanst.DESCRIPTION, new JSONString(description));
		}
		if(addition != null && !addition.equals("")){
			json.put(TradeConstanst.ADDITION, new JSONString(addition));
		}
		return json ;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(TradeConstanst.TRADE_ID_ZH, new JSONNumber(trade_id));
		if(TradeConstanst.CUSTOMER_ID_ZH.equals(colName)){
			json.put(TradeConstanst.CUSTOMER_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(TradeConstanst.TRANS_ID_ZH.equals(colName)){
			json.put(TradeConstanst.TRADE_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(TradeConstanst.AMOUNT_ZH.equals(colName)){
			json.put(TradeConstanst.AMOUNT, new JSONString(colValue));
		}else if(TradeConstanst.STATUS_ZH.equals(colName)){
			json.put(TradeConstanst.STATUS, new JSONString(colValue));
		}else if(TradeConstanst.THIRD_STATUS_ZH.equals(colName)){
			json.put(TradeConstanst.THIRD_STATUS, new JSONString(colValue));
		}else if(TradeConstanst.CREATE_TIME_ZH.equals(colName)){
			json.put(TradeConstanst.CREATE_TIME, new JSONString(colValue));
		}else if(TradeConstanst.BOLISH_TIME_ZH.equals(colName)){
			json.put(TradeConstanst.BOLISH_TIME, new JSONString(colValue));
		}else if(TradeConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(TradeConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(TradeConstanst.VALIDITY_ZH.equals(colName)){
			json.put(TradeConstanst.VALIDITY, new JSONString(colValue));
		}else if(TradeConstanst.ADDITION_ZH.equals(colName)){
			json.put(TradeConstanst.ADDITION, new JSONString(colValue));
		}
		return json;
	}

	public int getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(int trade_id) {
		this.trade_id = trade_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getThird_id() {
		return third_id;
	}

	public void setThird_id(String third_id) {
		this.third_id = third_id;
	}	
	
}
