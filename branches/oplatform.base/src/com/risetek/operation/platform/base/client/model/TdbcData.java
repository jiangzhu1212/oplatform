package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TdbcConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: TdbcData 
 * @Description: 二维码格式化数据并返回模块数据
 * @author JZJ 
 * @date 2010-8-26 下午02:04:13 
 * @version 1.0
 */
public class TdbcData extends OPlatformData {

	private int tdbc_id = 0 ;
	
	private int e_goods_sn = 0 ;

	private String image= null ;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		String jsonStr = jo.get(Constanst.ACTION_INFO).isString().stringValue();
		JSONObject actionInfo = JSONParser.parse(jsonStr).isObject();
		JSONNumber item_total = (JSONNumber)actionInfo.get(Constanst.QUERY_TOTAL);
		setSum(Integer.parseInt(item_total.toString()));
		JSONArray arr = actionInfo.get(Constanst.QUERY_DATA).isArray();
		String[][] data = new String[arr.size()][10];
		for(int i = 0 ; i < arr.size() ; i ++){
			JSONObject customer = arr.get(i).isObject();
			try {
				try {
					data[i][0] = customer.get(TdbcConstanst.TDBC_ID)
							.isNumber().toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][1] = customer.get(TdbcConstanst.E_GOODS_SN)
							.isNumber().toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					data[i][2] = customer.get(TdbcConstanst.IMAGE)
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
			if(Constanst.ACTION_NAME_QUERY_TDBC_INFO.equals(ACTION_NAME)){
				actionInfo = packetData();
				actionInfo.put(Constanst.PAGE_POS,new JSONNumber(PAGE_POS));
				actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(PAGE_SIZE));
			}else if(Constanst.ACTION_NAME_ADD_TDBC_INFO.equals(ACTION_NAME)){
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
		if(tdbc_id != 0){
			json.put(TdbcConstanst.TDBC_ID, new JSONNumber(tdbc_id));
		}
		if(e_goods_sn != 0){
			json.put(TdbcConstanst.E_GOODS_SN, new JSONNumber(e_goods_sn));
		}
		if(image != null || !"".equals(image)){
			json.put(TdbcConstanst.IMAGE, new JSONString(image));
		}
		return json;
	}
	
	public int getTdbc_id() {
		return tdbc_id;
	}

	public void setTdbc_id(int tdbc_id) {
		this.tdbc_id = tdbc_id;
	}

	public int getE_goods_sn() {
		return e_goods_sn;
	}

	public void setE_goods_sn(int e_goods_sn) {
		this.e_goods_sn = e_goods_sn;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
