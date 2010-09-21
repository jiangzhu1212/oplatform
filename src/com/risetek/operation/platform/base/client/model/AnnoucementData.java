package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: AnnoucementData 
 * @Description: 公告格式化数据并返回模块数据 
 * @author JZJ 
 * @date 2010-8-27 上午10:42:54 
 * @version 1.0
 */
public class AnnoucementData extends OPlatformData {

	private int ace_id = 0;

	private String type = null;

	private String ace_data = null;

	private String description = null;

	private String addtion = null;

	private String create_time = null;
	
	private String create_time_min = null;
	
	private String create_time_max = null;

	private String stop_time = null;
	
	private String stop_time_min = null;
	
	private String stop_time_max = null;

	private String target_type = null;

	private String target_id = null;

	private String validity = null;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray arr = actionInfo.get(Constanst.ITEMS).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject annoucement = arr.get(i).isObject();
			try {
				try {
					data[i][0] = annoucement.get(AnnoucementConstanst.ACE_ID)
							.isNumber().toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][1] = annoucement.get(AnnoucementConstanst.TYPE)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][2] = annoucement.get(AnnoucementConstanst.DATA)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][3] = annoucement.get(AnnoucementConstanst.DESCRIPTION)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][4] = annoucement.get(AnnoucementConstanst.ADDIITION)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][5] = annoucement.get(AnnoucementConstanst.CREATE_TIME)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][6] = annoucement.get(AnnoucementConstanst.STOP_TIME)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][7] = annoucement.get(AnnoucementConstanst.TARGET_TYPE)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][8] = annoucement.get(AnnoucementConstanst.TARGET_ID)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][9] = annoucement.get(AnnoucementConstanst.VALIDITY)
							.isString().stringValue();
				} catch (Exception e) {
					// TODO: handle exception
				}
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
			if(Constanst.ACTION_NAME_QUERY_ANNOUCEMENT_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_MODIFY_ANNOUCEMENT_INFO.equals(ACTION_NAME)){
				actionInfo = packetData(col[0],col[1]);
			}else if(Constanst.ACTION_NAME_ADD_ANNOUCEMENT_INFO.equals(ACTION_NAME)){
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
		if(ace_id != 0){
			json.put(AnnoucementConstanst.ACE_ID, new JSONNumber(ace_id));
		}
		if(type != null && !type.equals("")){
			json.put(AnnoucementConstanst.TYPE, new JSONString(type));
		}
		if(ace_data != null && !ace_data.equals("")){
			json.put(AnnoucementConstanst.DATA, new JSONString(ace_data));
		}
		if(description != null && !description.equals("")){
			json.put(AnnoucementConstanst.DESCRIPTION, new JSONString(description));
		}
		if(addtion != null && !addtion.equals("")){
			json.put(AnnoucementConstanst.ADDIITION, new JSONString(addtion));
		}
		if(create_time != null && !create_time.equals("")){
			json.put(AnnoucementConstanst.CREATE_TIME, new JSONString(create_time));
		}else {
			if(create_time_min != null && !create_time_min.equals("")){
				json.put(AnnoucementConstanst.CREATE_TIME_MIN, new JSONString(create_time_min));
			}
			if(create_time_max != null && !create_time_max.equals("")){
				json.put(AnnoucementConstanst.CREATE_TIME_MAX, new JSONString(create_time_max));
			}
		}
		if(stop_time != null && !stop_time.equals("")){
			json.put(AnnoucementConstanst.STOP_TIME, new JSONString(stop_time));
		}else {
			if(stop_time_min != null && !stop_time_min.equals("")){
				json.put(AnnoucementConstanst.STOP_TIME_MIN, new JSONString(stop_time_min));
			}
			if(stop_time_max != null && !stop_time_max.equals("")){
				json.put(AnnoucementConstanst.STOP_TIME_MAX, new JSONString(stop_time_max));
			}
		}
		if(target_type != null && !target_type.equals("")){
			json.put(AnnoucementConstanst.TARGET_TYPE, new JSONString(target_type));
		}
		if(target_id != null && !target_id.equals("")){
			json.put(AnnoucementConstanst.TARGET_ID, new JSONString(target_id));
		}
		if(validity != null && !validity.equals("")){
			json.put(AnnoucementConstanst.VALIDITY, new JSONString(validity));
		}
		return json;
	}
	
	private JSONObject packetData(String colName , String colValue){
		JSONObject json = new JSONObject();
		json.put(CustomerConstanst.CUSTOMER_ID, new JSONNumber(ace_id));
		if(AnnoucementConstanst.TYPE_ZH.equals(colName)){
			json.put(AnnoucementConstanst.TYPE, new JSONString(colValue));
		}else if(AnnoucementConstanst.DATA_ZH.equals(colName)){
			json.put(AnnoucementConstanst.DATA, new JSONString(colValue));
		}else if(AnnoucementConstanst.DESCRIPTION_ZH.equals(colName)){
			json.put(AnnoucementConstanst.DESCRIPTION, new JSONString(colValue));
		}else if(AnnoucementConstanst.ADDITION_ZH.equals(colName)){
			json.put(AnnoucementConstanst.CREATE_TIME_ZH, new JSONString(colValue));
		}else if(AnnoucementConstanst.CREATE_TIME.equals(colName)){
			json.put(AnnoucementConstanst.STOP_TIME_ZH, new JSONString(colValue));
		}else if(AnnoucementConstanst.STOP_TIME_ZH.equals(colName)){
			json.put(AnnoucementConstanst.STOP_TIME, new JSONString(colValue));
		}else if(AnnoucementConstanst.TARGET_TYPE_ZH.equals(colName)){
			json.put(AnnoucementConstanst.TARGET_TYPE, new JSONString(colValue));
		}else if(AnnoucementConstanst.TARGET_ID_ZH.equals(colName)){
			json.put(AnnoucementConstanst.TARGET_ID, new JSONString(colValue));
		}else if(AnnoucementConstanst.VALIDITY_ZH.equals(colName)){
			json.put(AnnoucementConstanst.VALIDITY, new JSONString(colValue));
		}
		return json;
	}

	public String getAce_data() {
		return ace_data;
	}

	public void setAce_data(String ace_data) {
		this.ace_data = ace_data;
	}

	public int getAce_id() {
		return ace_id;
	}

	public void setAce_id(int ace_id) {
		this.ace_id = ace_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddtion() {
		return addtion;
	}

	public void setAddtion(String addtion) {
		this.addtion = addtion;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStop_time() {
		return stop_time;
	}

	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCreate_time_min() {
		return create_time_min;
	}

	public void setCreate_time_min(String create_time_min) {
		this.create_time_min = create_time_min;
	}

	public String getCreate_time_max() {
		return create_time_max;
	}

	public void setCreate_time_max(String create_time_max) {
		this.create_time_max = create_time_max;
	}

	public String getStop_time_min() {
		return stop_time_min;
	}

	public void setStop_time_min(String stop_time_min) {
		this.stop_time_min = stop_time_min;
	}

	public String getStop_time_max() {
		return stop_time_max;
	}

	public void setStop_time_max(String stop_time_max) {
		this.stop_time_max = stop_time_max;
	}
	
}
