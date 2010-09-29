package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.CardBatchConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CardBatchData  extends OPlatformData {

	private int batch_id = 0;
	
	private String channel_id = ""+0;

	private String description = null;
	
	private String price = null;

	private String indate = null;
	
	private String addition = null;

	private String pro_batch = null;
	
	public void parseData(String text) {
		JSONObject jo = JSONParser.parse(text).isObject();
		String jsonStr = jo.get(Constanst.ACTION_INFO).isString().stringValue();
		JSONObject actionInfo = JSONParser.parse(jsonStr).isObject();
		JSONNumber item_total = (JSONNumber)actionInfo.get(Constanst.QUERY_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONArray arr = actionInfo.get(Constanst.QUERY_DATA).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject cardBatch = arr.get(i).isObject();
			try {
				data[i][0] = cardBatch.get(CardBatchConstanst.BATCH_ID).isNumber().toString();
			} catch (Exception e) {
			}
			try {
				data[i][1] = cardBatch.get(CardBatchConstanst.CHANNEL_ID).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][2] = cardBatch.get(CardBatchConstanst.DESCRIPTION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][3] = cardBatch.get(CardBatchConstanst.PRICE).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][4] = cardBatch.get(CardBatchConstanst.INDATE).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][5] = cardBatch.get(CardBatchConstanst.ADDITION).isString().stringValue();
			} catch (Exception e) {
			}
			try {
				data[i][6] = cardBatch.get(CardBatchConstanst.PRO_BATCH).isString().stringValue();
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
			if(Constanst.ACTION_NAME_QUERY_CARD_BATCH.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_CARD_BATCH.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_CARD_BATCH.equals(ACTION_NAME)){
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
		if(batch_id != 0){
			json.put(CardBatchConstanst.BATCH_ID, new JSONNumber(batch_id));
		}
		if(!channel_id.equals("0") && "".equals(channel_id.trim())){
			json.put(CardBatchConstanst.CHANNEL_ID, new JSONString(channel_id));
		}
		if(description != null && !description.trim().equals("")){
			json.put(CardBatchConstanst.DESCRIPTION, new JSONString(description));
		}
		if(price != null && !price.trim().equals("")){
			json.put(CardBatchConstanst.PRICE, new JSONString(price));
		}
		if(indate != null && !indate.trim().equals("")){
			json.put(CardBatchConstanst.INDATE, new JSONString(indate));
		}
		if(addition != null && !addition.trim().equals("")){
			json.put(CardBatchConstanst.ADDITION, new JSONString(addition));
		}
		if(pro_batch != null && !pro_batch.trim().equals("")){
			json.put(CardBatchConstanst.PRO_BATCH, new JSONString(pro_batch));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CardBatchConstanst.BATCH_ID, new JSONNumber(batch_id));
		if(CardBatchConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(CardBatchConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(CardBatchConstanst.CHANNEL_ID_ZH.equals(colName)){
			json.put(CardBatchConstanst.CHANNEL_ID, new JSONString(colValue));
		}else if(CardBatchConstanst.PRICE_ZH.equals(colName)){
			json.put(CardBatchConstanst.PRICE, new JSONString(colValue));
		}else if(CardBatchConstanst.INDATE_ZH.equals(colName)){
			json.put(CardBatchConstanst.INDATE, new JSONString(colValue));
		}else if(CardBatchConstanst.ADDITION_ZH.equals(colName)){
			json.put(CardBatchConstanst.ADDITION, new JSONString(colValue));
		}else if(CardBatchConstanst.PRO_BATCH_ZH.equals(colName)){
			json.put(CardBatchConstanst.PRO_BATCH, new JSONString(colValue));
		}
		return json;
	}
	
	public int getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getPro_batch() {
		return pro_batch;
	}

	public void setPro_batch(String pro_batch) {
		this.pro_batch = pro_batch;
	}
	
}
