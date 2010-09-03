package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.CardTerminalConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CardTerminalData extends OPlatformData {

	private int sum = 0;
	
	private String terminal_id = ""+0;
	private String tm_key = null;
	private String description = null;
	private String addition = null;
	private String validity = null;
	
	public void parseData(String text){
		
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(!terminal_id.equals("0")){
			json.put(CardTerminalConstanst.TERMINAL_ID, new JSONString(terminal_id));
		}
		if(tm_key != null && !tm_key.equals("")){
			json.put(CardTerminalConstanst.TM_KEY, new JSONString(tm_key));
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
		json.put(CardTerminalConstanst.TERMINAL_ID, new JSONString(terminal_id));
		if(CardTerminalConstanst.TM_KEY_ZH.equals(colName)){
			json.put(CardTerminalConstanst.TM_KEY, new JSONString(colValue));
		}else if(CardTerminalConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(CardTerminalConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(CardTerminalConstanst.ADDITION_ZH.equals(colName)){
			json.put(CardTerminalConstanst.ADDITION, new JSONString(colValue));
		}else if(CardTerminalConstanst.VALIDITY_ZH.equals(colName)){
			json.put(CardTerminalConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}		

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getTm_key() {
		return tm_key;
	}

	public void setTm_key(String tm_key) {
		this.tm_key = tm_key;
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
