package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodsConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.MINGYAppendMoneryConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class MingYAppendMoneyData extends OPlatformData {
	
	/**
	 * 结算平台帐号
	 */
	private String settlement_account = null ;
	/**
	 * 充值金额（分）
	 */
	private String amount = null ;
	/**
	 * 账单ID
	 */
	private String bill_id = null ;
	/**
	 * 账单的BILL_EXTERN_ID
	 */
	private String order_id = null ;
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String date_time = null ;
	/**
	 * 用户
	 */
	private String user_name = null ;
	/**
	 * 状态
	 */
	private String status = null ;
	/**
	 * 附录
	 */
	private String bill_info = null ;
	
	public void parseData(String text){
		JSONObject jo = JSONParser.parse(text).isObject();
		try {
			JSONNumber item_total = (JSONNumber)jo.get(Constanst.ITEM_TOTAL);
			setSum(Integer.parseInt(item_total.toString()));
			JSONObject actionInfo = jo.get(Constanst.ACTION_INFO).isObject();
			JSONArray goodsArr = actionInfo.get(Constanst.ITEMS).isArray();
			String[][] data = new String[goodsArr.size()][10];
			for(int i = 0 ; i < goodsArr.size() ; i ++){
				
				JSONObject myAppent = goodsArr.get(i).isObject();
				JSONObject bill_info = myAppent.get(EGoodsConstanst.INFO).isObject();	
					try {
						data[i][0] = myAppent.get(EGoodsConstanst.E_GOODS_ID).isNumber().toString();
					} catch (Exception e) {
					}
					try {
						data[i][1] = myAppent.get("").isString().stringValue();
					} catch (Exception e) {
					}
					
					try {
						data[i][2] = bill_info.get(MINGYAppendMoneryConstanst.SETTLEMENT_ACCOUNT).isString().stringValue();
					} catch (Exception e) {
					}
					
					try {
						String money = bill_info.get(MINGYAppendMoneryConstanst.AMOUNT).isString().toString();
						data[i][3] = Util.formatMoney(money);
					} catch (Exception e) {
					}
					try {
						data[i][4] = myAppent.get(EGoodsConstanst.CREATE_TIME).isString().stringValue() ;
					} catch (Exception e) {
					}
					try {
						data[i][5] = bill_info.get(MINGYAppendMoneryConstanst.USER_NAME).isString().toString() ;
					} catch (Exception e) {
					}
					try {
						data[i][6] = bill_info.get(checkStatusValue(MINGYAppendMoneryConstanst.STATUS)).isString().toString() ;
					} catch (Exception e) {
					}
					try {
						data[i][7] = bill_info.toString() ;
					} catch (Exception e) {
					}
			
			}
			setData(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private String checkStatusValue(String name){
		if("failed".equals(name)){
			return "通知失败";
		}else if("noticed".equals(name)){
			return "已通知";
		}else if("notNoticed".equals(name)){
			return "未通知";
		}
		return 	name ;
	}
	
	public String toHttpPacket(){
		JSONObject packet = new JSONObject();
		packet.put(Constanst.ACTION_NAME, new JSONString(Constanst.ACTION_NAME_NOTICE_REQUEST));
		packet.put(Constanst.ACTION_INFO,packetData());
		packet.put("ACTION_INVOKER", new JSONString(Constanst.ACTION_INVOKER_WEB_CLIENT));
		packet.put("ACTION_MODULE", new JSONString(Constanst.ACTION_MODULE_MY_SETTLEMENT_SERVICE));
		return packet.toString() ;
	}
	
	private JSONObject packetData(){
		JSONObject actionInfo = new JSONObject();
		
		try {
			actionInfo.put(MINGYAppendMoneryConstanst.SETTLEMENT_ACCOUNT,
					new JSONString(settlement_account));
			actionInfo.put(MINGYAppendMoneryConstanst.BILL_ID, new JSONString(
					bill_id));
			actionInfo.put(MINGYAppendMoneryConstanst.AMOUNT,
					new JSONString(amount));
			actionInfo.put(MINGYAppendMoneryConstanst.ORDER_ID, new JSONString(
					order_id));
			actionInfo.put(MINGYAppendMoneryConstanst.DATE_TIME, new JSONString(
					date_time));
			actionInfo.put(MINGYAppendMoneryConstanst.BILL_INFO, new JSONString(
					bill_info));
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
		return actionInfo ;
	
	}
	
	public String getSettlement_account() {
		return settlement_account;
	}
	public void setSettlement_account(String settlement_account) {
		this.settlement_account = settlement_account;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getBill_info() {
		return bill_info;
	}
	public void setBill_info(String bill_info) {
		this.bill_info = bill_info;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
