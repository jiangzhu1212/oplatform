package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: BankData 
 * @Description: 发卡行格式化数据并返回模块数据
 * @author JZJ 
 * @date 2010-8-26 下午02:04:13 
 * @version 1.0
 */
public class BankData extends OPlatformData {
	
	private int bank_id = 0;
	
	private String prefix = null ;
	
	private String name = null;
	
	private String validity = null;
	
	private String description = null;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject bank = arr.get(i).isObject();
			
			try {
				data[i][0] = bank.get(BankConstanst.BANK_ID)
						.isNumber().toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				data[i][1] = bank.get(BankConstanst.PREFIX)
						.isString().stringValue();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				data[i][2] = bank.get(BankConstanst.NAME)
						.isString().stringValue();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				data[i][3] = bank.get(BankConstanst.DESCRIPTION)
						.isString().stringValue();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				data[i][4] = bank.get(BankConstanst.VALIDITY)
						.isString().stringValue();
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
			if(ACTION_NAME == null){
				actionInfo = new JSONObject();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(0));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(UIConfig.TABLE_ROW_NORMAL));
			}else if(Constanst.ACTION_NAME_QUERY_BANK_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(0));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(UIConfig.TABLE_ROW_NORMAL));
			}else if(Constanst.ACTION_NAME_MODIFY_BANK_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_BANK_INFO.equals(ACTION_NAME)){
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

		if(bank_id != 0 ){
			json.put(BankConstanst.BANK_ID, new JSONNumber(bank_id));
		}
		if(prefix != null && !prefix.equals("")){
			json.put(BankConstanst.PREFIX, new JSONString(prefix));
		}
		if(name != null && !name.equals("")){
			json.put(BankConstanst.NAME, new JSONString(name));
		}
		if(validity != null && !validity.equals("")){
			json.put(BankConstanst.VALIDITY, new JSONString(validity));
		}
		if(description != null && !description.equals("")){
			json.put(BankConstanst.DESCRIPTION, new JSONString(description));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(BankConstanst.BANK_ID, new JSONNumber(bank_id));
		if(BankConstanst.PREFIX_ZH.equals(colName)){
			json.put(BankConstanst.PREFIX, new JSONString(colValue));
		}else if(BankConstanst.NAME_ZH.equals(colName)){
			json.put(BankConstanst.NAME, new JSONString(colValue));
		}else if(BankConstanst.VALIDITY_ZH.equals(colName)){
			json.put(BankConstanst.VALIDITY, new JSONString(colValue));
		}else if(BankConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(BankConstanst.DESCRIPTION, new JSONString(colValue));
		}
		return json;
	}
	
	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
