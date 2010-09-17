package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransBindConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransBindData extends OPlatformData {
	
	private int trans_bind_id = 0;
	private int trans_id = 0 ;
	private int customer_id = 0 ;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject transaction = arr.get(i).isObject();
			try {
				data[i][0] = transaction.get(TransBindConstanst.TRANS_BIND_ID).isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = transaction.get(TransBindConstanst.TRANS_ID).isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = transaction.get(TransBindConstanst.CUSTOMER_ID).isNumber().toString();
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
			if(Constanst.ACTION_NAME_QUERY_TRANS_BIND.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_TRANS_BIND.equals(ACTION_NAME)){
				actionInfo = packetData(col[0] , col[1]);
			}else if(Constanst.ACTION_NAME_ADD_TRANS_BIND.equals(ACTION_NAME)){
				actionInfo = packetData();
			}
			packet.put(Constanst.ACTION_INFO,actionInfo);
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(RequestFactory.PACKET);
		buffer.append("=");
		buffer.append(packet.toString());
		return buffer.toString();
	}

	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		
		if(trans_bind_id != 0){
			json.put(TransBindConstanst.TRANS_BIND_ID, new JSONNumber(trans_bind_id));
		}
		if(trans_id != 0){
			json.put(TransBindConstanst.TRANS_ID, new JSONNumber(trans_id));
		}
		if(customer_id != 0){
			json.put(TransBindConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		return json;
	}
	
	private JSONObject packetData(String colName, String colValue){
		JSONObject json = new JSONObject();
		
		json.put(TransBindConstanst.TRANS_BIND_ID, new JSONNumber(trans_bind_id));
		if(TransBindConstanst.CUSTOMER_ID_ZH.equals(colName)){
			try {
				json.put(TransBindConstanst.CUSTOMER_ID,
						new JSONNumber(Integer.parseInt(colValue)));
			} catch (Exception e) {
				json.put(TransBindConstanst.CUSTOMER_ID,
						new JSONNumber(0));
			}
		}else if(TransBindConstanst.TRANS_ID_ZH.equals(colName)){
			try {
				json.put(CustomerConstanst.TRANS_ID, new JSONNumber(Integer.parseInt(colValue)));
			} catch (Exception e) {
				json.put(CustomerConstanst.TRANS_ID, new JSONNumber(0));
			}
		}
		return json;
	}
	
	
	public int getTrans_bind_id() {
		return trans_bind_id;
	}
	public void setTrans_bind_id(int trans_bind_id) {
		this.trans_bind_id = trans_bind_id;
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
	
	
}
