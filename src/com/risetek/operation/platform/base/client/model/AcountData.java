package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.AcountConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: AcountData
 * @Description: 银行卡列格式化并返回模块数据
 * @author JZJ
 * @date 2010-8-26 下午02:03:46
 * @version 1.0
 */
public class AcountData extends OPlatformData {

	private String account_number = null;
	
	private int bank_id = 0;
	
	private String description = null;

	private String addition = null;

	private String validity = null;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject acount = arr.get(i).isObject();
			
			try {
				data[i][0] = acount.get(AcountConstanst.ACCOUNT_NUMBER)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][1] = acount.get(AcountConstanst.BANK_ID)
						.isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][2] = acount.get(AcountConstanst.DESCRIPTION)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][3] = acount.get(AcountConstanst.ADDITION)
						.isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = acount.get(AcountConstanst.VALIDITY)
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
			if(ACTION_NAME == null){
				actionInfo = new JSONObject();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_QUERY_ACCOUNT_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_ACCOUNT_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_AC_BIND.equals(ACTION_NAME)){
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

		if(bank_id != 0 ){
			json.put(AcountConstanst.BANK_ID, new JSONNumber(bank_id));
		}		
		if(account_number != null && !account_number.equals("")){
			json.put(AcountConstanst.ACCOUNT_NUMBER, new JSONString(account_number));
		}
		if(description != null && !description.equals("")){
			json.put(AcountConstanst.DESCRIPTION, new JSONString(description));
		}
		if(addition != null && !addition.equals("")){
			json.put(AcountConstanst.ADDITION, new JSONString(addition));
		}
		if(validity != null && !validity.equals("")){
			json.put(AcountConstanst.VALIDITY, new JSONString(validity));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(AcountConstanst.ACCOUNT_NUMBER, new JSONString(account_number));
		if(AcountConstanst.BANK_ID_ZH.equals(colName)){
			try {
				json.put(AcountConstanst.BANK_ID,
						new JSONNumber(Integer.parseInt(colValue)));
			} catch (Exception e) {
				json.put(AcountConstanst.BANK_ID,
						new JSONNumber(0));
			}
		}else if(AcountConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(AcountConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(AcountConstanst.ADDITION_ZH.equals(colName)){
			json.put(AcountConstanst.ADDITION, new JSONString(colValue));
		}else if(AcountConstanst.VALIDITY_ZH.equals(colName)){
			json.put(AcountConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}
	
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
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
