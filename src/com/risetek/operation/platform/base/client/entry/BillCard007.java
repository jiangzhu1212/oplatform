package com.risetek.operation.platform.base.client.entry;

public class BillCard007 {
	
	/**
	 * 充值的手机号码
	 */
	private String CHARGE_PHONE_NUMBER;
	/**
	 * 终端名称 BILL_CUSTOMER.CSN
	 */
	private String Amount;
	/**
	 * 订单状态（0,已充值，1，未充值，2，充值失败,3，已委托,4，未委托）
	 */
	private int STATUS;

	/**
	 * 下订单日期
	 */
	private String Datetime;
	/**
	 * 007卡返回信息
	 */
	private String RetInfo;
	
	private String BILL_EXTERN_ID;
	
	private String payState;
	
	
	
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getBILL_EXTERN_ID() {
		return BILL_EXTERN_ID;
	}
	public void setBILL_EXTERN_ID(String bILLEXTERNID) {
		BILL_EXTERN_ID = bILLEXTERNID;
	}
	public String getCHARGE_PHONE_NUMBER() {
		return CHARGE_PHONE_NUMBER;
	}
	public void setCHARGE_PHONE_NUMBER(String cHARGEPHONENUMBER) {
		CHARGE_PHONE_NUMBER = cHARGEPHONENUMBER;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	public String getRetInfo() {
		return RetInfo;
	}
	public void setRetInfo(String retInfo) {
		RetInfo = retInfo;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
}
