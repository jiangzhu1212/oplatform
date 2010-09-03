package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.CardBatchConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class CardBatchData  extends OPlatformData {

	private int sum = 0;
	
	private String batch_id = ""+0;
	
	private String channel_id = ""+0;

	private String description = null;
	
	private String price = null;

	private String indate = null;
	
	private String addition = null;

	private String pro_batch = null;
	
	public void parseData(String text) {
		System.out.println("the text is :" + text);
		setSum(100);
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(!batch_id.equals("0")){
			json.put(CardBatchConstanst.BATCH_ID, new JSONString(batch_id));
		}
		if(!channel_id.equals("0")){
			json.put(CardBatchConstanst.CHANNEL_ID, new JSONString(channel_id));
		}
		if(description != null && !description.equals("")){
			json.put(CardBatchConstanst.DESCRIPTION, new JSONString(description));
		}
		if(price != null && !price.equals("")){
			json.put(CardBatchConstanst.PRICE, new JSONString(price));
		}
		if(indate != null && !indate.equals("")){
			json.put(CardBatchConstanst.INDATE, new JSONString(indate));
		}
		if(addition != null && !addition.equals("")){
			json.put(CardBatchConstanst.ADDITION, new JSONString(addition));
		}
		if(pro_batch != null && !pro_batch.equals("")){
			json.put(CardBatchConstanst.PRO_BATCH, new JSONString(pro_batch));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CardBatchConstanst.BATCH_ID, new JSONString(batch_id));
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

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
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
