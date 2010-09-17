package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.CardTerminalConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CardTerminalData extends OPlatformData {

	private int terminal_id = 0;
	private String sn = null;
	private String description = null;
	private String addition = null;
	private String validity = null;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][5];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject cardTerminal = arr.get(i).isObject();

			try {
				data[i][0] = cardTerminal.get(CardTerminalConstanst.TERMINAL_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = cardTerminal.get(CardTerminalConstanst.SN)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][2] = cardTerminal.get(CardTerminalConstanst.DESCRIPTION)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][3] = cardTerminal.get(CardTerminalConstanst.ADDITION)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = cardTerminal.get(CardTerminalConstanst.VALIDITY)
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
			if(Constanst.ACTION_NAME_QUERY_CARD_TERMINAL.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_CARD_TERMINAL.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_CARD_TERMINAL.equals(ACTION_NAME)){
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
		if(terminal_id!=0){
			json.put(CardTerminalConstanst.TERMINAL_ID, new JSONNumber(terminal_id));
		}
		if(sn != null && !sn.equals("")){
			json.put(CardTerminalConstanst.SN, new JSONString(sn));
		}
		if(description != null && !description.equals("")){
			json.put(CardTerminalConstanst.DESCRIPTION, new JSONString(description));
		}
		if(addition != null && !addition.equals("")){
			json.put(CardTerminalConstanst.ADDITION, new JSONString(addition));
		}
		if(validity != null && !validity.equals("")){
			json.put(CardTerminalConstanst.VALIDITY, new JSONString(validity));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CardTerminalConstanst.TERMINAL_ID, new JSONNumber(terminal_id));
		if(CardTerminalConstanst.SN_ZH.equals(colName)){
			json.put(CardTerminalConstanst.SN, new JSONString(colValue));
		}else if(CardTerminalConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(CardTerminalConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(CardTerminalConstanst.ADDITION_ZH.equals(colName)){
			json.put(CardTerminalConstanst.ADDITION, new JSONString(colValue));
		}else if(CardTerminalConstanst.VALIDITY_ZH.equals(colName)){
			json.put(CardTerminalConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}

	public int getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(int terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	
}
