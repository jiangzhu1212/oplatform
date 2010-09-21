package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransactionData extends OPlatformData {
	
	/**
	 * 业务号
	 */
	private int trans_id =  0;
	
	/**
	 * 业务别名
	 */
	private String alias = null;
	
	/**
	 * 业务名
	 */
	private String name = null;
	
	/**
	 * 业务描述
	 */
	private String description = null;
	
	/**
	 * 业务回调地址
	 */
	private String url = null;
	
	/**
	 * 业务是否能够绑定
	 */
	private String bindable = null;
	
	/**
	 * 商户号
	 */
	private String merchant_number = null;
	
	/**
	 * pos终端号
	 */
	private String pos_number = null;
	
	/**
	 * 业务类型
	 */
	private String type = null;
	
	/**
	 * 业务附加信息
	 */
	private String addition = null;
	/**
	 * 
	 * 是否有效
	 */
	private String validity = null ;
	
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
				data[i][0] = transaction.get(TransactionConstanst.TRANS_ID).isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = transaction.get(TransactionConstanst.ALIAS).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][2] = transaction.get(TransactionConstanst.NAME).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][3] = transaction.get(TransactionConstanst.DESCRIPTION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = transaction.get(TransactionConstanst.URL).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][5] = transaction.get(TransactionConstanst.BINDABLE).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][6] = transaction.get(TransactionConstanst.MERCHANT_NUMBER).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][7] = transaction.get(TransactionConstanst.POS_NUMBER).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][8] = transaction.get(TransactionConstanst.TYPE).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][9] = transaction.get(TransactionConstanst.ADDITION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][10] = transaction.get(TransactionConstanst.VALIDITY).isString().stringValue();
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
			if(Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_TRANSACTION_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_TRADE_INFO.equals(ACTION_NAME)){
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
		if( trans_id != 0){
			json.put(TransactionConstanst.TRANS_ID, new JSONNumber(trans_id));
		}
		if(alias != null && !alias.equals("")){
			json.put(TransactionConstanst.ALIAS, new JSONString(alias));
		}
		if(name != null && !name.equals("")){
			json.put(TransactionConstanst.NAME, new JSONString(name));
		}
		if(description != null && !description.equals("")){
			json.put(TransactionConstanst.DESCRIPTION, new JSONString(description));
		}
		if(url != null && !url.equals("")){
			json.put(TransactionConstanst.URL, new JSONString(url));
		}
		if(bindable != null && !bindable.equals("")){
			json.put(TransactionConstanst.BINDABLE, new JSONString(bindable));
		}
		if(merchant_number != null && !merchant_number.equals("")){
			json.put(TransactionConstanst.MERCHANT_NUMBER, new JSONString(merchant_number));
		}
		if(pos_number != null && !pos_number.equals("")){
			json.put(TransactionConstanst.POS_NUMBER, new JSONString(pos_number));
		}
		if(type != null && !type.equals("")){
			json.put(TransactionConstanst.TYPE, new JSONString(type));
		}
		if(addition != null && !addition.equals("")){
			json.put(TransactionConstanst.ADDITION, new JSONString(addition));
		}
		if(validity != null && !validity.equals("")){
			json.put(TransactionConstanst.VALIDITY, new JSONString(validity));
		}
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(TransactionConstanst.TRANS_ID, new JSONNumber(trans_id));
		if(TransactionConstanst.ALIAS_ZH.equals(colName)){
			json.put(TransactionConstanst.ALIAS, new JSONString(colValue));
		}else if(TransactionConstanst.NAME_ZH.equals(colName)){
			json.put(TransactionConstanst.NAME, new JSONString(colValue));
		}else if(TransactionConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(TransactionConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(TransactionConstanst.URL_ZH.equals(colName)){
			json.put(TransactionConstanst.URL, new JSONString(colValue));
		}else if(TransactionConstanst.BINDABLE_ZH.equals(colName)){
			json.put(TransactionConstanst.BINDABLE, new JSONString(colValue));
		}else if(TransactionConstanst.MERCHANT_NUMBER_ZH.equals(colName)){
			json.put(TransactionConstanst.MERCHANT_NUMBER, new JSONString(colValue));
		}else if(TransactionConstanst.POS_NUMBER_ZH.equals(colName)){
			json.put(TransactionConstanst.POS_NUMBER, new JSONString(colValue));
		}else if(TransactionConstanst.TYPE_ZH.equals(colName)){
			json.put(TransactionConstanst.TYPE, new JSONString(colValue));
		}else if(TransactionConstanst.ADDITION_ZH.equals(colName)){
			json.put(TransactionConstanst.ADDITION, new JSONString(colValue));
		}else if(TransactionConstanst.VALIDITY_ZH.equals(colName)){
			json.put(TransactionConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}
	
	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBindable() {
		return bindable;
	}

	public void setBindable(String bindable) {
		this.bindable = bindable;
	}

	public String getMerchant_number() {
		return merchant_number;
	}

	public void setMerchant_number(String merchant_number) {
		this.merchant_number = merchant_number;
	}

	public String getPos_number() {
		return pos_number;
	}

	public void setPos_number(String pos_number) {
		this.pos_number = pos_number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
