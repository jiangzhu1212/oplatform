package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.ChannelConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class ChannelData extends OPlatformData {

	int sum = 0 ;
	/**
	 * 渠道编号
	 */
	private String channel_id = ""+0 ;
	
	private String description = null;
	
	private String fee = null ;
	
	private String fee_type = null ;
	
	private String fee_addition = null ;
	
	private String addition = null ;
	
	private String loc_code = null ;
	
	public void parseData(String text){
		
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(!channel_id.equals("0")){
			json.put(ChannelConstanst.CHANNEL_ID, new JSONString(channel_id));
		}
		if(description != null && !description.equals("")){
			json.put(ChannelConstanst.DESCRIPTION, new JSONString(description));
		}
		if(fee != null && !fee.equals("")){
			json.put(ChannelConstanst.FEE, new JSONString(fee));
		}
		if(fee_type != null && !fee_type.equals("")){
			json.put(ChannelConstanst.FEE_TYPE, new JSONString(fee_type));
		}
		if(fee_addition != null && !fee_addition.equals("")){
			json.put(ChannelConstanst.FEE_ADDITION, new JSONString(fee_addition));
		}
		if(addition != null && !addition.equals("")){
			json.put(ChannelConstanst.ADDITION, new JSONString(addition));
		}
		if(loc_code != null && !loc_code.equals("")){
			json.put(ChannelConstanst.LOC_CODE, new JSONString(loc_code));
		}
		
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(ChannelConstanst.CHANNEL_ID, new JSONString(channel_id));
		if(ChannelConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(ChannelConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(ChannelConstanst.FEE_ZH.equals(colName)){
			json.put(ChannelConstanst.FEE, new JSONString(colValue));
		}else if(ChannelConstanst.FEE_TYPE_ZH.equals(colName)){
			json.put(ChannelConstanst.FEE_TYPE, new JSONString(colValue));
		}else if(ChannelConstanst.FEE_ADDITION_ZH.equals(colName)){
			json.put(ChannelConstanst.FEE_ADDITION, new JSONString(colValue));
		}else if(ChannelConstanst.ADDITION_ZH.equals(colName)){
			json.put(ChannelConstanst.ADDITION, new JSONString(colValue));
		}else if(ChannelConstanst.LOC_CODE_ZH.equals(colName)){
			json.put(ChannelConstanst.LOC_CODE, new JSONString(colValue));
		}
		return json;
	}
	
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getFee_addition() {
		return fee_addition;
	}

	public void setFee_addition(String fee_addition) {
		this.fee_addition = fee_addition;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getLoc_code() {
		return loc_code;
	}

	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}		
}
