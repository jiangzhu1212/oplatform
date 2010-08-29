package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.base.client.entry.TransactionConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransactionData extends OPlatformData {
	
	private int sum;
	
	/**
	 * 业务号
	 */
	private String trans_id = "" + 0;
	
	/**
	 * 
	 */
	private String alias = null;
	
	/**
	 * 
	 */
	private String name = null;
	
	/**
	 * 
	 */
	private String description = null;
	
	/**
	 * 
	 */
	private String url = null;
	
	/**
	 * 
	 */
	private String bindable = null;
	
	/**
	 * 
	 */
	private String merchant_number = null;
	
	/**
	 * 
	 */
	private String pos_number = null;
	
	/**
	 * 
	 */
	private String type = null;
	
	/**
	 * 
	 */
	private String addition = null;
	
	public void parseData(String text){
		
	}

	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(!trans_id.equals("0")){
			json.put(TransactionConstanst.TRANS_ID, new JSONString(trans_id));
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
			json.put(TransactionConstanst.ADDTION, new JSONString(addition));
		}
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(TransactionConstanst.TRANS_ID, new JSONString(trans_id));
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
		}else if(TransactionConstanst.ADDTION_ZH.equals(colName)){
			json.put(TransactionConstanst.ADDTION, new JSONString(colValue));
		}
		return json;
	}
	
	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
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

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}
}
