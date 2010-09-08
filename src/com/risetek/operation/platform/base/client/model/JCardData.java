package com.risetek.operation.platform.base.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class JCardData  extends OPlatformData  {

	/**
	 * 索引
	 */
	private Integer JCARDID = 0;
	
	/**
	 * 第三方账单编号
	 */
	private String BILL_EXTEND_ID = null;
	
	/**
	 * 卡状态
	 */
	private String STATUS = null;
	
	/**
	 * 序列号
	 */
	private String SN = null;
	
	/**
	 * 16位卡号
	 */
	private String NUMBER = null;
	
	/**
	 * 16位密码
	 */
	private String PWD = null;
	
	/**
	 * 面值
	 */
	private String PAR_VALUE = null;
	
	/**
	 * 创建日期
	 */
	private String CREATE_DATE = null;
	
	/**
	 * 失效日期
	 */
	private String STATUS_TIMEOUT = null;
	
	/**
	 * 需要上传的数据
	 */
	private String uploadData = null;
	/**
	 * 需要对账的数据
	 */
	private String balanceData = null;

	private List<String> JCard = new ArrayList<String>();
	private List<String> checkCard = new ArrayList<String>();
	
	public void parseData(String text){
		if("".equals(text)){
			Window.alert("无返回值");
			return ;
		}
		JSONObject jo = JSONParser.parse(text).isObject();
		JSONObject actionInfo = (JSONObject)jo.get(Constanst.ACTION_INFO);
		JSONArray jcardArr = (JSONArray)actionInfo.get(Constanst.JCARDS_INFO);
		int size = jcardArr.size();
		String data[][] = new String[size][9];
		
		for(int i = 0 ; i < size ; i++){
			JSONObject jcard = (JSONObject)jcardArr.get(i);
			try{
				data[i][0] = ((JSONNumber)jcard.get(JCardConstanst.JCARDID)).toString();
			}catch(Exception e){
				data[i][0] = "" + 0;
			}
			try{
				data[i][1] = ((JSONString)jcard.get(JCardConstanst.BILL_EXTEND_ID)).stringValue();
			}catch(Exception e){
				data[i][1] = "";
			}
			try{
				data[i][2] = ((JSONString)jcard.get(JCardConstanst.SN)).stringValue();
			}catch(Exception e){
				data[i][2] = "";
			}
			try{
				data[i][3] = ((JSONString)jcard.get(JCardConstanst.NUMBER)).stringValue();
			}catch(Exception e){
				data[i][3] = "";
			}
			try{
				data[i][4] = ((JSONString)jcard.get(JCardConstanst.PWD)).stringValue();
			}catch(Exception e){
				data[i][4] = "";
			}			
			try{
				String statusVlue = ((JSONString)jcard.get(JCardConstanst.STATUS)).stringValue();
				
				data[i][5] = parseToZh(statusVlue);
			}catch(Exception e){
				data[i][5] = "";
			}
			try{
				data[i][6] = ((JSONString)jcard.get(JCardConstanst.PAR_VALUE)).stringValue();
			}catch(Exception e){
				data[i][6] = "";
			}
			try{
				data[i][7] = ((JSONString)jcard.get(JCardConstanst.CREATE_DATE)).stringValue();
			}catch(Exception e){
				data[i][7] = "";
			}
			try{
				data[i][8] = ((JSONString)jcard.get(JCardConstanst.STATUS_TIMEOUT)).stringValue();
			}catch(Exception e){
				data[i][8] = "";
			}
			try {
				Integer.parseInt(((JSONNumber) actionInfo
						.get(Constanst.TOTAL)).toString());
			} catch (Exception e) {
				
			}
		}
		setData(data);
		setSum(size);
	
	}
	
	public String toHttpPacket(){

			JSONObject packet = new JSONObject();
			JSONObject actionInfo = null;
			try {
				packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));	
				if(ACTION_NAME == null){
					actionInfo = new JSONObject();
					actionInfo.put(Constanst.PAGE_INDEX,new JSONNumber(0));
					actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(50));

				}else if(Constanst.ACTION_NAME_SELECT_JCARD.equals(ACTION_NAME)){
					actionInfo = packetData();
					actionInfo.put(Constanst.PAGE_INDEX,new JSONNumber(0));
					actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(50));
//					Jcard jc = (Jcard) o;
//					int startPos = jc.getSTART_POS();
//					int pageIndex = startPos;				
//					actionInfo.put(Constanst.PAGE_INDEX,new JSONNumber(pageIndex));
//					if(jc.getMAX_COUNT()!=0){
//						actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(jc.getMAX_COUNT()));
//					}	
				}else if(Constanst.ACTION_NAME_MODIFY_STATUS.equals(ACTION_NAME)){
					actionInfo = packetData();
				}else if(Constanst.ACTION_NAME_IMPORT_DATA.equals(ACTION_NAME)){
					JSONArray array ;
					array = packetData(uploadData);
					if(array != null){
						actionInfo = new JSONObject();
						actionInfo.put(JCardConstanst.JCARDS_DATA, array);
					}else {
						return null;
					}					
				}
				packet.put(Constanst.ACTION_INFO,actionInfo);
			} catch (JSONException e) {			
				e.printStackTrace();
				return null;
			}
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(RequestFactory.CTI_PACKET);
			buffer.append("=");
			buffer.append(packet.toString());
			return buffer.toString();
	}
	
	public void toHttpPacketBl(){
		JCard = new ArrayList<String>();
		checkCard = new ArrayList<String>();
		JSONObject packet = new JSONObject();
		packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));
		String[] lines = balanceData.split("\r\n");
		JSONObject actionInfo = new JSONObject();
		for(int i = 0 ; i <lines.length ; i++){
			if(lines[i].trim().length()==0){
				continue ;
			}
			actionInfo = new JSONObject();
			checkCard.add(lines[i]);
			String[] attributes = lines[i].split(",");			
			if(attributes.length != JCardConstanst.JK_ATTRIBUTE_NUM){
				Window.alert("第"+(i+1)+"行数据不完整或格式不正确");
				return ;
			}
			for(int j = 0 ; j < attributes.length ; j++){
				if(attributes[j].trim().length() == 0){
					Window.alert("第"+(i+1)+"行,第"+(j+1)+"组数据不完整或格式不正确");
					return ;
				}
			}
			actionInfo.put("SN", new JSONString(attributes[0]));
			actionInfo.put("NUMBER", new JSONString(attributes[1]));
			actionInfo.put("PWD", new JSONString(attributes[2]));
			actionInfo.put("PAR_VALUE", new JSONString(attributes[3]));
			packet.put(Constanst.ACTION_INFO,actionInfo);
			StringBuffer buffer = new StringBuffer();
			buffer.append(RequestFactory.CTI_PACKET);
			buffer.append("=");
			buffer.append(packet.toString());
			JCard.add(buffer.toString());
		}
	}
	
	private JSONObject packetData(){
		JSONObject json = new JSONObject();
		if(JCARDID!= 0){
			json.put(JCardConstanst.JCARDID, new JSONNumber(JCARDID));
		}
		if(BILL_EXTEND_ID != null && !BILL_EXTEND_ID.equals("")){
			json.put(JCardConstanst.BILL_EXTEND_ID, new JSONString(BILL_EXTEND_ID));
		}
		if(STATUS != null && !STATUS.equals("")){
			json.put(JCardConstanst.STATUS, new JSONString(STATUS));
		}
		if(SN != null && !SN.equals("")){
			json.put(JCardConstanst.SN, new JSONString(SN));
		}
		if(NUMBER != null && !NUMBER.equals("")){
			json.put(JCardConstanst.NUMBER, new JSONString(NUMBER));
		}
		if(PWD != null && !PWD.equals("")){
			json.put(JCardConstanst.PWD, new JSONString(PWD));
		}
		if(PAR_VALUE != null && !PAR_VALUE.equals("")){
			json.put(JCardConstanst.PAR_VALUE, new JSONString(PAR_VALUE));
		}
		if(CREATE_DATE != null && !CREATE_DATE.equals("")){
			json.put(JCardConstanst.CREATE_DATE, new JSONString(CREATE_DATE));
		}
		if(STATUS_TIMEOUT != null && !STATUS_TIMEOUT.equals("")){
			json.put(JCardConstanst.STATUS_TIMEOUT, new JSONString(STATUS_TIMEOUT));
		}
		
		return json;
	}
	
	private JSONArray packetData(String text){
		if(text == null || "".equals(text)){
			return null ;
		}
		String[] lines = text.split("\r\n");
		JSONObject othr = new JSONObject();
		JSONArray ajk = new JSONArray();
		for(int i = 0 ; i <lines.length ; i++){
			othr = new JSONObject();
			String[] attributes = lines[i].split(",");
			if(lines[i].trim().length()==0){
				continue ;
			}
			if(attributes.length != JCardConstanst.JK_ATTRIBUTE_NUM){
				Window.alert("第"+(i+1)+"行数据不完整或格式不正确");
				return null;
			}
			if(attributes[0].length() != JCardConstanst.SN_SIZE){
				Window.alert("第"+(i+1)+"行序列号必须是"+JCardConstanst.SN_SIZE+"位");
				return null;
			}
			if(attributes[1].length() != JCardConstanst.NUMBER_SIZE){
				Window.alert("第"+(i+1)+"行卡号必须是"+JCardConstanst.NUMBER_SIZE+"位");
				return null;
			}
			if(attributes[2].length() != JCardConstanst.PWD_SIZE){
				Window.alert("第"+(i+1)+"行密码必须是"+JCardConstanst.PWD_SIZE+"位");
				return null;
			}
			if(!Util.isNum(attributes[3].trim())){
				Window.alert("第"+(i+1)+"行面值必须是数字");
				return null;
			}
			othr.put("SN", new JSONString(attributes[0]));
			othr.put("NUMBER", new JSONString(attributes[1]));
			othr.put("PWD", new JSONString(attributes[2]));
			othr.put("PAR_VALUE", new JSONString(attributes[3].trim()));
			
			int size = ajk.size();
			ajk.set(size, othr);
		}
		return ajk;
	}
	
	
	public ResolveResponseInfo[] retInfo(String retInfo) {
		ResolveResponseInfo opRetInfo[] = new ResolveResponseInfo[1];
		opRetInfo[0] = new ResolveResponseInfo();
		JSONObject jo = JSONParser.parse(retInfo).isObject();
		JSONObject actionInfo = (JSONObject) jo.get(Constanst.ACTION_INFO);
		opRetInfo[0].setActionInfo(actionInfo.toString());
		try {
			JSONNumber num = (JSONNumber)actionInfo.get(Constanst.RESPONSE_CODE);
			opRetInfo[0].setReturnCode(Integer.parseInt(num.toString()));
			opRetInfo[0].setReturnMessage(((JSONString)actionInfo.get(Constanst.RESPONSE_INFO)).stringValue());
		} catch (Exception e) {
			opRetInfo[0].setReturnCode(-1);
		}
		
		return opRetInfo;
	}
	
	private String parseToZh(String text){
		if("free".equals(text)){
			return "可用";
		}else if("lock".equals(text)){
			return "锁定";
		}else if("sold".equals(text)){
			return "销售";
		}else if("fail".equals(text)){
			return "失败";
		}else if("cancel".equals(text)){
			return "注销";
		}else if("invalid".equals(text)){
			return "无效";
		}
		return text;
	}
	
	
	public Integer getJCARDID() {
		return JCARDID;
	}

	public void setJCARDID(Integer jCARDID) {
		JCARDID = jCARDID;
	}

	public String getBILL_EXTEND_ID() {
		return BILL_EXTEND_ID;
	}

	public void setBILL_EXTEND_ID(String bILL_EXTEND_ID) {
		BILL_EXTEND_ID = bILL_EXTEND_ID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getPAR_VALUE() {
		return PAR_VALUE;
	}

	public void setPAR_VALUE(String pAR_VALUE) {
		PAR_VALUE = pAR_VALUE;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getSTATUS_TIMEOUT() {
		return STATUS_TIMEOUT;
	}

	public void setSTATUS_TIMEOUT(String sTATUS_TIMEOUT) {
		STATUS_TIMEOUT = sTATUS_TIMEOUT;
	}	


	public String getUploadData() {
		return uploadData;
	}

	public void setUploadData(String uploadData) {
		this.uploadData = uploadData;
	}	

	public String getBalanceData() {
		return balanceData;
	}

	public void setBalanceData(String balanceData) {
		this.balanceData = balanceData;
	}

	public List<String> getJCard() {
		return JCard;
	}

	public List<String> getCheckCard() {
		return checkCard;
	}
}
