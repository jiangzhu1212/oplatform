package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransBindConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransEnableConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransEnableData extends OPlatformData {
	
	private int trans_enable_id = 0 ;
	private int trans_id = 0 ;
	private int channel_id = 0 ;
	private String enable = null ;
	private String description = null ;
	
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
			if(Constanst.ACTION_NAME_QUERY_TRANS_ENABLE.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_TRANS_ENABLE.equals(ACTION_NAME)){
				actionInfo = packetData(col[0] , col[1]);
			}else if(Constanst.ACTION_NAME_ADD_TRANS_ENABLE.equals(ACTION_NAME)){
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
		
		if(trans_enable_id != 0){
			json.put(TransEnableConstanst.TRANS_ENABLE_ID, new JSONNumber(trans_enable_id));
		}
		if(trans_id != 0){
			json.put(TransEnableConstanst.TRANS_ID, new JSONNumber(trans_id));
		}
		if(channel_id != 0){
			json.put(TransEnableConstanst.CHANNEL_ID, new JSONNumber(channel_id));
		}
		if(enable != null && !"".equals(enable)){
			json.put(TransEnableConstanst.ENABLE, new JSONString(enable));
		}
		if(description != null && !"".equals(description)){
			json.put(TransEnableConstanst.DESCRIPTION, new JSONString(description));
		}
		return json;
	}
	
	private JSONObject packetData(String colName, String colValue){
		JSONObject json = new JSONObject();
		
		json.put(TransEnableConstanst.TRANS_ENABLE_ID, new JSONNumber(trans_enable_id));
		if(TransEnableConstanst.TRANS_ID_ZH.equals(colName)){
			try {
				json.put(TransEnableConstanst.TRANS_ID,
						new JSONNumber(Integer.parseInt(colValue)));
			} catch (Exception e) {
				json.put(TransEnableConstanst.TRANS_ID,
						new JSONNumber(0));
			}
		}else if(TransEnableConstanst.CHANNEL_ID_ZH.equals(colName)){
			try {
				json.put(TransEnableConstanst.CHANNEL_ID,
						new JSONNumber(Integer.parseInt(colValue)));
			} catch (Exception e) {
				json.put(TransEnableConstanst.CHANNEL_ID,
						new JSONNumber(0));
			}
		}else if(TransEnableConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(TransEnableConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(TransEnableConstanst.ENABLE_ZH.equals(colName)){
			json.put(TransEnableConstanst.ENABLE, new JSONString(colValue));
		}
		return json;
	}
	
	
	public int getTrans_enable_id() {
		return trans_enable_id;
	}
	public void setTrans_enable_id(int trans_enable_id) {
		this.trans_enable_id = trans_enable_id;
	}
	public int getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
