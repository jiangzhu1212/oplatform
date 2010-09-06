package com.risetek.operation.platform.base.client.model;

import java.util.Date;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.base.client.view.Card007View;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.BillCard007;
import com.risetek.operation.platform.launch.client.json.constanst.BillConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.BillInfomation;
import com.risetek.operation.platform.launch.client.json.constanst.Card007Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: BankData 
 * @Description: 全国充值 （007）格式化数据并返回模块数据
 * @author JZJ 
 * @date 2010-8-26 下午02:04:13 
 * @version 1.0
 */
public class Card007Data extends OPlatformData {
		
	private int sum;
	
	/**
	 * @Description: 格式化数据，并注入到模块的数据对象中 
	 * @param text  参数 
	 * @return void 返回类型 
	 */
	public void parseResult(BillCard007[] re) {
		setSum(re.length);//设置总数据
		String[][] data = new String[re.length][7];
		for (int i = 0; i < re.length; i++) {
			data[i][0] = re[i].getCHARGE_PHONE_NUMBER();
			data[i][1] = re[i].getBILL_EXTERN_ID();
			data[i][2] = re[i].getAmount();
			data[i][3] = getStatus(re[i].getSTATUS());
			data[i][4] = re[i].getDatetime();
			data[i][5] = re[i].getRetInfo();			
			if (re[i].getPayState().equals("paid")) {
				data[i][6] = "已支付";
			} else {
				data[i][6] = "未支付";
			}
		}
		setData(data);
	}
	
	/**
	 * @Description: 格式化返回数据 
	 * @param ret
	 * @return  参数 
	 * @return String 返回类型
	 */
	private String getStatus(int ret){
		String status = "";
		if (ret == 0) {
			status = "已充值";
		} else if (ret == 1) {
			status = "未充值";
		} else if (ret == 2) {
			status = "充值失败";
		} else if (ret == 3) {
			status = "已委托";
		} else if (ret == 4) {
			status = "未委托";
		}
		return status;
	}

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}	
	
	
	/**********************************************************/
	/**
	 * @ClassName: PacketParser 
	 * @Description: 统一处理007的JSON数据
	 * @author JZJ 
	 * @date 2010-9-6 上午09:07:36 
	 * @version 1.0
	 */
	public class PacketParser {

		private static final String SIGNATURE = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
		
		/**
		 * @Description: 请求发送成功后的控制方法，通过此方法来解析数据并构造对象数组
		 * @param retInfo
		 * @param ACTION_NAME
		 * @return  参数 
		 * @return Object[] 返回类型
		 */
		public Object[] packetParser(String retInfo, String ACTION_NAME) {
			Object[] obj = null;
			String rs = retInfo;
			if (retInfo.endsWith(SIGNATURE)) {
				rs = retInfo.substring(0, retInfo.length() - SIGNATURE.length());
			}
			if (Constanst.ACTION_NAME_SELECT_CARD_007.equals(ACTION_NAME)) {
				obj = build007Card(rs);
			}
			return obj;
		}
		
		/**
		 * @Description: TODO(这里用一句话描述这个方法的作用) 
		 * @param remoteRequest
		 * @param RemoteCaller
		 * @param actionName  参数 
		 * @return void 返回类型
		 */
		public void initializedata(RequestFactory remoteRequest, RequestCallback RemoteCaller, String actionName) {
			if (actionName.equals(Constanst.ACTION_NAME_SELECT_CARD_007)) {
				BillInfomation info = new BillInfomation();
				if(Card007View.dateBox != null){
					Date date = Card007View.dateBox.getValue();
					String dateTimeMIN = Util.formatMINDateToJsonString(date);
					String dateTimeMAX = Util.formatMAXDateToJsonString(date);
					info.setPAY_DATETIME_MIN(dateTimeMIN);
					info.setPAY_DATETIME_MAX(dateTimeMAX);
				}
				info.setCHARGE_STATUS("" + Card007View.statusValue);
				info.setSTART_POS(0);
				info.setMAX_COUNT(UIConfig.TABLE_ROW_NORMAL);
				
				String packet = toHttpPacket(info, Card007Constanst.ACTION_NAME_SELECT_CARD_007);	
				remoteRequest.get(packet, RemoteCaller);
			}	
		}
		
		/**
		 * @Description: 发送请求时构造帐单JSON数据
		 * @param o
		 * @param ACTION_NAME
		 * @return  参数 
		 * @return String 返回类型
		 */
		public String toHttpPacket(Object o, String ACTION_NAME) {
			JSONObject packet = new JSONObject();
			JSONObject actionInfo = null;
			try {
				if (Card007Constanst.ACTION_NAME_SELECT_CARD_007.equals(ACTION_NAME)){
					packet.put(Constanst.ACTION_NAME, new JSONString(Constanst.ACTION_NAME_SELECT_BILL_INFORMATION));
					
					BillInfomation card = (BillInfomation) o;
					actionInfo = buildBillObj(o);
					
					StringBuffer status = new StringBuffer();
					status.append(new JSONString(Constanst.STATUS).stringValue());
					status.append("\"");
					status.append(":");
					if(card.getCHARGE_STATUS()!=null){
						int s = Integer.parseInt(card.getCHARGE_STATUS());
						status.append(s);
					}else{
						status.append(2);
					}
					actionInfo.put(Constanst.START_POS, new JSONNumber(card.getSTART_POS()));
					if(card.getPAY_DATETIME_MIN() != null && card.getPAY_DATETIME_MAX() != null){
						actionInfo.put(Card007Constanst.PAY_DATETIME_MIN, new JSONString(card.getPAY_DATETIME_MIN()));
						actionInfo.put(Card007Constanst.PAY_DATETIME_MAX,  new JSONString(card.getPAY_DATETIME_MAX()));
					}else{
						Date date = new Date();
						String dateTimeMIN = Util.formatMINDateToJsonString(date);
						String dateTimeMAX = Util.formatMAXDateToJsonString(date);
						actionInfo.put(Card007Constanst.PAY_DATETIME_MIN, new JSONString(dateTimeMIN));
						actionInfo.put(Card007Constanst.PAY_DATETIME_MAX,  new JSONString(dateTimeMAX));
					}
					if(card.getMAX_COUNT() != 0){
						actionInfo.put(Constanst.MAX_COUNT, new JSONNumber(card.getMAX_COUNT()));
					}
					actionInfo.put(Constanst.BILL_INFO, new JSONString(status.toString()));				
					actionInfo.put(Constanst.TRANS_ID, new JSONNumber(74));
					packet.put(Constanst.ACTION_INFO, actionInfo);
				}
			} catch (JSONException e) {			
				e.printStackTrace();
			}
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(Constanst.BILLS_WEB_CLIENT);
			buffer.append("=");
			buffer.append(packet.toString());
			buffer.append(SIGNATURE);
			if(actionInfo == null){
				return null;
			}
			return buffer.toString();
		}

		/**
		 * @Description: 构造帐单JSON数据
		 * @param o
		 * @return  参数 
		 * @return JSONObject 返回类型
		 */
		private JSONObject buildBillObj(Object o) {
			JSONObject actionInfo = new JSONObject();
			BillInfomation bi = (BillInfomation) o;
			try {
				int transId = bi.getTRANS_ID();
				if (transId != 0) {
					actionInfo.put(BillConstanst.TRANS_ID, new JSONNumber(transId));
				}if(bi.getBILL_EXTERN_ID()!=null){
					actionInfo.put(BillConstanst.BILL_EXTERN_ID, new JSONString(bi.getBILL_EXTERN_ID()));
				}if(bi.getCUSTOMER_ID()!=0){
					actionInfo.put(BillConstanst.CUSTOMER_ID, new JSONNumber(bi.getCUSTOMER_ID()));
				}if(bi.getBILL_ID()!=0){
					actionInfo.put(BillConstanst.BILL_ID, new JSONNumber(bi.getBILL_ID()));
				}if(bi.getSTATUS()!=null){
					actionInfo.put(BillConstanst.STATUS, new JSONString(bi.getSTATUS()));
				}if(bi.getSEND_STATUS()!=null){
					actionInfo.put(BillConstanst.SEND_STATUS, new JSONString(bi.getSEND_STATUS()));
				}if(bi.getAMOUNT()!=null){
					actionInfo.put(BillConstanst.AMOUNT, new JSONString(bi.getAMOUNT()));
				}if(bi.getAMOUNT_MAX()!=null){
					actionInfo.put(BillConstanst.AMOUNT_MAX, new JSONString(bi.getAMOUNT_MAX()));
				}if(bi.getAMOUNT_MIN()!=null){
					actionInfo.put(BillConstanst.AMOUNT_MIN, new JSONString(bi.getAMOUNT_MIN()));
				}if(bi.getCREATE_DATETIME()!=null){
					actionInfo.put(BillConstanst.CREATE_DATETIME, new JSONString(bi.getCREATE_DATETIME()));
				}if(bi.getCREATE_DATETIME_MAX()!=null){
					actionInfo.put(BillConstanst.CREATE_DATETIME_MAX, new JSONString(bi.getCREATE_DATETIME_MAX()));
				}if(bi.getCREATE_DATETIME_MIN()!=null){
					actionInfo.put(BillConstanst.CREATE_DATETIME_MIN, new JSONString(bi.getCREATE_DATETIME_MIN()));
				}if(bi.getBOLISH_DATETIME()!=null){
					actionInfo.put(BillConstanst.BOLISH_DATETIME, new JSONString(bi.getBOLISH_DATETIME()));
				}if(bi.getBOLISH_DATETIME_MAX()!=null){
					actionInfo.put(BillConstanst.BOLISH_DATETIME_MAX, new JSONString(bi.getBOLISH_DATETIME_MAX()));
				}if(bi.getBOLISH_DATETIME_MIN()!=null){
					actionInfo.put(BillConstanst.BOLISH_DATETIME_MIN, new JSONString(bi.getBOLISH_DATETIME_MIN()));
				}if(bi.getPAY_DATETIME()!=null){
					actionInfo.put(BillConstanst.PAY_DATETIME, new JSONString(bi.getPAY_DATETIME()));
				}if(bi.getPAY_DATETIME_MAX()!=null){
					actionInfo.put(BillConstanst.PAY_DATETIME_MAX, new JSONString(bi.getPAY_DATETIME_MAX()));
				}if(bi.getPAY_DATETIME_MIN()!=null){
					actionInfo.put(BillConstanst.PAY_DATETIME_MIN, new JSONString(bi.getPAY_DATETIME_MIN()));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return actionInfo;
		}
		
		/**
		 * @Description: 解析JSON数据，并放入对象数组里
		 * @param retInfo
		 * @return  参数 
		 * @return BillCard007[] 返回类型
		 */
		private BillCard007[] build007Card(String retInfo) {
			JSONObject jo = JSONParser.parse(retInfo).isObject();
			JSONString actionName = (JSONString) jo.get(Constanst.ACTION_NAME);
			System.out.println("actionName =" + actionName.stringValue());
			JSONObject actionInfo = (JSONObject) jo.get(Constanst.ACTION_INFO);
			JSONArray billArr = (JSONArray) actionInfo.get(Constanst.QUERY_DATA);	
			BillCard007 cardInfo[] = new BillCard007[billArr.size()];
			
			for(int i=0;i<billArr.size();i++) {
				try{
					JSONObject bill = billArr.get(i).isObject();
					if(bill!=null) {
					cardInfo[i] = new BillCard007();
					cardInfo[i].setBILL_EXTERN_ID(bill.get(Card007Constanst.BILL_EXTERN_ID).isString().stringValue());
					cardInfo[i].setPayState(bill.get(Card007Constanst.STATUS).isString().stringValue());
					if(bill.get(Constanst.BILL_INFO)!=null)	{
						JSONObject bill_info = bill.get(Constanst.BILL_INFO).isObject();
						cardInfo[i].setCHARGE_PHONE_NUMBER(((JSONString)bill_info.get(Card007Constanst.CHARGE_PHONE_NUMBER)).stringValue());
						cardInfo[i].setAmount((bill_info.get(Card007Constanst.AMOUNT)).isString().stringValue());
						cardInfo[i].setSTATUS(Integer.parseInt(bill_info.get(Card007Constanst.STATUS).isNumber().toString()));
						cardInfo[i].setDatetime(((JSONString)bill_info.get(Card007Constanst.DATETIME)).stringValue());
						cardInfo[i].setRetInfo(((JSONString)bill_info.get(Card007Constanst.RETINFO)).stringValue());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}			
			}
			return cardInfo;		
		}
		
		/**
		 * @Description:  构建发送JSON数据包
		 * @param card
		 * @param ACTION_NAME
		 * @return  参数 
		 * @return String 返回类型
		 */
		public String chargePacket(BillCard007 card, String ACTION_NAME){			
			StringBuffer packet = new StringBuffer();
			JSONObject packetObject = new JSONObject();
			JSONObject actionInfo = new JSONObject();
			JSONObject billInfo = new JSONObject();
			
			try{
				packetObject.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));
				
				actionInfo.put(Card007Constanst.PAY_RET, new JSONNumber(0));
				actionInfo.put(Card007Constanst.BILL_EXTERN_ID, new JSONString(card.getBILL_EXTERN_ID()));

				billInfo.put(Card007Constanst.CHARGE_PHONE_NUMBER, new JSONString(card.getCHARGE_PHONE_NUMBER()));
				billInfo.put(Card007Constanst.AMOUNT, new JSONString(card.getAmount()));
				billInfo.put(Card007Constanst.RETINFO, new JSONString(card.getRetInfo()));
				billInfo.put(Card007Constanst.STATUS, new JSONString("2"));
				billInfo.put(Card007Constanst.DATETIME, new JSONString(card.getDatetime()));
				
//				Date date = Card007Dialog.dateBox.getValue();
//				String time = Card007Dialog.timeValue;
//				String dateTime = Util.formatDateToJsonString(date) + Util.formatHmStringTime(time);
				
				actionInfo.put(Constanst.BILL_INFO, billInfo);
				actionInfo.put(Card007Constanst.DATETIME, new JSONString(card.getChargeDateTime()));
				
				packetObject.put(Constanst.ACTION_INFO, actionInfo);

				packet.append(Constanst.CTI_PACKET);
				packet.append("=");
				packet.append(packetObject.toString());
				packet.append(SIGNATURE);
			}catch(Exception e){
				e.printStackTrace();
			}		
			return packet.toString();
		}
		
		/**
		 * @Description: 解析充值返回的JSON数据包
		 * @param retInfo
		 * @return  参数 
		 * @return int 返回类型
		 */
		public int dealResponsePacket(String retInfo) {
			if (retInfo.endsWith(SIGNATURE)) {
				retInfo = retInfo.substring(0, retInfo.length() - SIGNATURE.length());
			}
			System.out.println("ResponsePacket = " + retInfo);
			JSONObject jo = JSONParser.parse(retInfo).isObject();
			try {
				JSONObject actionInfo = (JSONObject) jo.get(Constanst.ACTION_INFO);
				String pay_ret = actionInfo.get(Card007Constanst.PAY_RET).isNumber().toString();
				if (pay_ret != null) {
					return Integer.parseInt(pay_ret);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1; // 当返回的信息没有PAY_RET对象的时候 返回-1
		}
	}
}
