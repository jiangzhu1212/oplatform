package com.risetek.operation.platform.base.client.model;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class JCardData  extends OPlatformData  {

	private int sum;
	
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
	
	public void parseData(String text){
		
	}
	
	public String toHttpPacket(String ACTION_NAME){
		if(Constanst.ACTION_NAME_SELECT_JCARD.equals(ACTION_NAME) || Constanst.ACTION_NAME_MODIFY_STATUS.equals(ACTION_NAME)){
			JSONObject packet = new JSONObject();
			JSONObject actionInfo;
			try {
				packet.put(Constanst.ACTION_NAME, new JSONString(ACTION_NAME));
				actionInfo = packetData();
				if(Constanst.ACTION_NAME_SELECT_JCARD.equals(ACTION_NAME)){
//					Jcard jc = (Jcard) o;
//					int startPos = jc.getSTART_POS();
//					int pageIndex = startPos;				
//					actionInfo.put(Constanst.PAGE_INDEX,new JSONNumber(pageIndex));
//					if(jc.getMAX_COUNT()!=0){
//						actionInfo.put(Constanst.PAGE_SIZE,new JSONNumber(jc.getMAX_COUNT()));
//					}	
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
		return null;
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

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}
}
