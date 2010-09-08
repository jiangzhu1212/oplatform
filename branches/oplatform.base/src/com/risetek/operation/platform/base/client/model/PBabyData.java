package com.risetek.operation.platform.base.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.PBabyConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class PBabyData extends OPlatformData {
	
	private int sum = 0;
	
	private String is_ok = null;

	private String ORDID = null;

	private String KEYID = null;
	
	private String CODE = null;
	
	private String SEAT = null;
	
	private String PNR = null;
	
	private String phoneNumber = null ;
	
	private String create_dateTime = null  ;
	
	private int customer_id = 0 ;
	
	private String ACTION_NAME;
	
	protected String addStringBegin = "000000";
	
	protected String addStringEnd = "245959";	
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONArray goodsArr = actionInfo.get(Constanst.ITEMS).isArray();
		List<String[]> list = new ArrayList<String[]>();
		String[] PBabyData = new String[6];
		for(int i = 0 ; i < goodsArr.size() ; i ++){
			String info = goodsArr.get(i).isObject().get(PBabyConstanst.BILL_INFO).isString().stringValue();
			if(info == null || "".equals(info)){
				continue ;
			}
			
			JSONObject pBabyInfo = JSONParser.parse(info).isObject();
			try {
				JSONArray arr = null;
				arr = pBabyInfo.get(PBabyConstanst.TICKETS).isArray();				
				int pBabySize = arr.size();
				for (int j = 0; j < pBabySize; j++) {
	
					JSONObject pBaby = arr.get(j).isObject();
					if(!"false".equals(pBaby.get(PBabyConstanst.IS_OK)
								.isString().stringValue())){
						continue ;
					}
					PBabyData = new String[6];
					try {
						PBabyData[3] = pBaby.get(PBabyConstanst.CODE)
								.isString().stringValue();
					} catch (Exception e) {
					}
					try {
						PBabyData[2] = pBaby.get(PBabyConstanst.IS_OK)
								.isString().stringValue();
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						PBabyData[1] = pBaby.get(PBabyConstanst.KEYID)
								.isString().stringValue();
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						PBabyData[0] = pBaby.get(PBabyConstanst.ORDID)
								.isString().stringValue();
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						PBabyData[4] = pBaby.get(PBabyConstanst.SEAT)
								.isString().stringValue();
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						PBabyData[5] = pBaby.get(PBabyConstanst.PNR)
								.isString().stringValue();
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					list.add(PBabyData);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}				
		}
		if(list.size() == 0){
			Window.alert("无异常票宝宝");
			return ;
		}
		String[][] data = new String[list.size()][6];
		for(int i = 0 ; i < list.size() ; i ++){
			data[i] = list.get(i);
		}
		setData(data);
	}
	public void parseDataCustomer(String text){
		if("".equals(text)){
			Window.alert("无返回值");
			return ;
		}
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
		JSONNumber item_total = (JSONNumber)actionInfo.get(Constanst.ITEM_TOTAL);
		if(Integer.parseInt(item_total.toString()) == 0){
			Window.alert("无此电话号码的用户");
			return ;
		}
		JSONArray customerArr = actionInfo.get(Constanst.ITEMS).isArray();
		for(int i = 0 ; i < customerArr.size() ; i ++){
			JSONObject customer = (customerArr.get(i)).isObject();
			if(Constanst.TRUE.equals(customer.get(CustomerConstanst.VALIDITY).isString().toString())){
				customer_id = Integer.parseInt(customer.get(CustomerConstanst.CUSTOMER_ID).isNumber().toString());
			}else {
				continue ;
			}
		}
		if(customer_id==0){
			Window.alert("有此电话号码用户，不过此用户已失效");
			return ;
		}
	}
	public String toHttpPacket(){
		JSONObject packet = new JSONObject();
		JSONObject actionInfo = null;
		try {
			packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));	
			actionInfo = packetData();
		} catch (Exception e) {			
		}
		packet.put(Constanst.ACTION_INFO,actionInfo);
		StringBuffer buffer = new StringBuffer();
		buffer.append(RequestFactory.PACKET);
		buffer.append("=");
		buffer.append(packet.toString());
		return buffer.toString();
	}
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(phoneNumber != null && !"".equals(phoneNumber)){
			json.put(PBabyConstanst.PHONE_NUMBER, new JSONString(phoneNumber));
			return json;
		}
		if(create_dateTime != null && !"".equals(create_dateTime)){
			json.put(PBabyConstanst.CREATE_DATETIME_MIN, new JSONString(phoneNumber+"addStringBegin"));
			json.put(PBabyConstanst.CREATE_DATETIME_MAX, new JSONString(phoneNumber+"addStringEnd"));
		}
		if(customer_id != 0){
			json.put(PBabyConstanst.CUSTOMER_ID, new JSONNumber(customer_id));
		}
		json.put(Constanst.TRANS_ID, new JSONNumber(76));
		return json ;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCreate_dateTime() {
		return create_dateTime;
	}

	public void setCreate_dateTime(String create_dateTime) {
		this.create_dateTime = create_dateTime;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getACTION_NAME() {
		return ACTION_NAME;
	}
	public void setACTION_NAME(String aCTION_NAME) {
		ACTION_NAME = aCTION_NAME;
	}
	
	public String getIs_ok() {
		return is_ok;
	}
	public void setIs_ok(String is_ok) {
		this.is_ok = is_ok;
	}
	public String getORDID() {
		return ORDID;
	}
	public void setORDID(String oRDID) {
		ORDID = oRDID;
	}
	public String getKEYID() {
		return KEYID;
	}
	public void setKEYID(String kEYID) {
		KEYID = kEYID;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getSEAT() {
		return SEAT;
	}
	public void setSEAT(String sEAT) {
		SEAT = sEAT;
	}
	public String getPNR() {
		return PNR;
	}
	public void setPNR(String pNR) {
		PNR = pNR;
	}
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
