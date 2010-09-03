package com.risetek.operation.platform.base.client.model;

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
