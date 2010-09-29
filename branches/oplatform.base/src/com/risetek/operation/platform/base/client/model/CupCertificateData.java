package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CupCertificateConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CupCertificateData extends OPlatformData {

	private int certificate_id = 0 ;
	
	private int bill_id = 0 ;
	
	private String time = null ;
	
	private String time_min = null ;
	
	private String time_max = null ;
	
	private String amount = null ;
	
	private String type = null ;
	
	private String account_number = null ;
	
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
				data[i][0] = json.get(CupCertificateConstanst.CERTIFICATE_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = json.get(CupCertificateConstanst.BILL_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = json.get(CupCertificateConstanst.TIME)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][3] = json.get(CupCertificateConstanst.AMOUNT)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = json.get(CupCertificateConstanst.TYPE)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][5] = json.get(CupCertificateConstanst.ACCOUNT_NUMBER)
						.isString().stringValue();
			} catch (Exception e) {
			}
		}
		setData(data);
	}
	
	public String toHttpPacket(){
		String ACTION_NAME = getACTION_NAME();
		JSONObject packet = new JSONObject();
		JSONObject actionInfo = null;
		try {
			packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));	
			if(Constanst.ACTION_NAME_QUERY_CERTIFICATE_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_ADD_CERTIFICATE_INFO.equals(ACTION_NAME)){
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
		if(certificate_id!=0){
			json.put(CupCertificateConstanst.CERTIFICATE_ID, new JSONNumber(certificate_id));
		}
		if(bill_id!=0){
			json.put(CupCertificateConstanst.BILL_ID, new JSONNumber(bill_id));
		}
		if(time != null && !time.equals("")){
			json.put(CupCertificateConstanst.TIME, new JSONString(time));
		}else {
			if(time_min != null && !time_min.trim().equals("")){
				json.put(CupCertificateConstanst.TIME_MIN, new JSONString(time_min));
			}
			if(time_max != null && !time_max.trim().equals("")){
				json.put(CupCertificateConstanst.TIME_MAX, new JSONString(time_max));
			}
		}
		if(amount != null && !amount.equals("")){
			json.put(CupCertificateConstanst.AMOUNT, new JSONString(amount));
		}
		if(type != null && !type.equals("")){
			json.put(CupCertificateConstanst.TYPE, new JSONString(type));
		}
		if(account_number != null && !account_number.equals("")){
			json.put(CupCertificateConstanst.ACCOUNT_NUMBER, new JSONString(account_number));
		}
		return json ;
	}

	
	public int getCertificate_id() {
		return certificate_id;
	}

	public void setCertificate_id(int certificate_id) {
		this.certificate_id = certificate_id;
	}

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime_min() {
		return time_min;
	}

	public void setTime_min(String time_min) {
		this.time_min = time_min;
	}

	public String getTime_max() {
		return time_max;
	}

	public void setTime_max(String time_max) {
		this.time_max = time_max;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
}
