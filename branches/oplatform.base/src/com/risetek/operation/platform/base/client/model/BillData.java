package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.BillConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class BillData extends OPlatformData {
	
	private int bill_id = 0 ;
	
	private int trade_id = 0 ;
	
	private int customer_id = 0 ;
	
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
				data[i][0] = json.get(BillConstanst.BILL_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = json.get(BillConstanst.TRADE_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = json.get(BillConstanst.CUSTOMER_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][3] = json.get(BillConstanst.VALIDITY)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = json.get(BillConstanst.ADDITION)
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
			if(Constanst.ACTION_NAME_QUERY_BILL_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_BILL_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_BILL_INFO.equals(ACTION_NAME)){
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
		if(bill_id!=0){
			json.put(BillConstanst.BILL_ID, new JSONNumber(bill_id));
		}
		if(trade_id!=0){
			json.put(BillConstanst.TRADE_ID, new JSONNumber(trade_id));
		}
		if(customer_id!=0){
			json.put(BillConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		if(addition != null && !addition.equals("")){
			json.put(BillConstanst.ADDITION, new JSONString(addition));
		}
		if(validity != null && !validity.equals("")){
			json.put(BillConstanst.VALIDITY, new JSONString(validity));
		}		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(BillConstanst.BILL_ID, new JSONNumber(bill_id));
		if(BillConstanst.BILL_ID_ZH.equals(colName)){
			json.put(BillConstanst.BILL_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(BillConstanst.TRADE_ID_ZH.equals(colName)){
			json.put(BillConstanst.TRADE_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(BillConstanst.CUSTOMER_ID_ZH.equals(colName)){
			json.put(BillConstanst.CUSTOMER_ID, new JSONNumber(Integer.parseInt(colValue)));
		}else if(BillConstanst.ADDITION_ZH.equals(colName)){
			json.put(BillConstanst.ADDITION, new JSONString(colValue));
		}else if(BillConstanst.VALIDITY_ZH.equals(colName)){
			json.put(BillConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
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
