package com.risetek.operation.platform.base.client.constanst;


/**
 * 账单数据表
 * Bill 2.0
 * @author 马俊
 *
 */
public class BILL_INFOMATION {

	private static final long serialVersionUID = 3202562727936637098L;
	
	/**
	 * 账单index(key,自增)
	 */
	private int BILL_ID = 0;
	
	/**
	 * 账单号，由第三方生成
	 */
	private String BILL_EXTERN_ID = null;
	
	/**
	 * 客户编号(BILL_CUSTOMER.CUSTOMER_ID)
	 */
	private int CUSTOMER_ID = 0;
	
	public void setBILL_ID(int bILLID) {
		BILL_ID = bILLID;
	}

	/**
	 * 交易码(BILL_TRANSACTION.TRANS_ID)
	 */
	private int TRANS_ID = 0;
	
	/**
	 * 交易金额(以分为单位)
	 */
	private String AMOUNT = null;
	
	/**
	 * 账单状态 ( generated / paid / invalid / timeout )
	 */
	private String STATUS = null;
	
	/**
	 * 发送状态( to_send / noticed / sent / invalid)
	 */
	private String SEND_STATUS = null;
	
	/**
	 * 生成日期( YYYYMMDDHHmmSS )
	 */
	private String CREATE_DATETIME = null;
	
	/**
	 * 失效日期( YYYYMMDDHHmmSS )
	 */
	private String BOLISH_DATETIME = null;
	
	/**
	 * 支付日期/作废日期( YYYYMMDDHHmmSS )
	 */
	private String PAY_DATETIME = null;
	
	/**
	 * 交易的额外信息(json对象)
	 */
	private String BILL_INFO = null;
	/**
	 * 查询范围
	 */
	private String CREATE_DATETIME_MIN = null;
	
	private String BOLISH_DATETIME_MIN = null;
	
	private String PAY_DATETIME_MIN = null;
	
	private String AMOUNT_MIN = null;
	
	private String CREATE_DATETIME_MAX = null;
	
	private String BOLISH_DATETIME_MAX = null;
	
	private String PAY_DATETIME_MAX = null;
	
	private String AMOUNT_MAX = null;

	private String DESCRIPTION = null;
	
	private String CHARGE_STATUS;
	
	public String getCHARGE_STATUS() {
		return CHARGE_STATUS;
	}

	public void setCHARGE_STATUS(String charge_status) {
		CHARGE_STATUS = charge_status;
	}

	public String getBILL_EXTERN_ID() {
		return BILL_EXTERN_ID;
	}

	public void setBILL_EXTERN_ID(String bILLEXTERNID) {
		if(null == bILLEXTERNID){
			return;
		}
		BILL_EXTERN_ID = bILLEXTERNID;
	}

	public int getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(int cUSTOMERID) {
		if(0 == cUSTOMERID){
			return;
		}
		CUSTOMER_ID = cUSTOMERID;
	}

	public int getTRANS_ID() {
		return TRANS_ID;
	}

	public void setTRANS_ID(int tRANSID) {
		if(tRANSID <= 0){
			return;
		}
		TRANS_ID = tRANSID;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		if(null == aMOUNT){
			return;
		}
		AMOUNT = aMOUNT;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		if(null == sTATUS){
			return;
		}
		STATUS = sTATUS;
	}

	public String getSEND_STATUS() {
		return SEND_STATUS;
	}

	public void setSEND_STATUS(String sENDSTATUS) {
		if(null == sENDSTATUS){
			return;
		}
		SEND_STATUS = sENDSTATUS;
	}

	public String getCREATE_DATETIME() {
		return CREATE_DATETIME;
	}

	public void setCREATE_DATETIME(String cREATEDATETIME) {
		if(null == cREATEDATETIME){
			return;
		}
		CREATE_DATETIME = cREATEDATETIME;
	}

	public String getBOLISH_DATETIME() {
		return BOLISH_DATETIME;
	}

	public void setBOLISH_DATETIME(String bOLISHDATETIME) {
		if(null == bOLISHDATETIME){
			return;
		}
		BOLISH_DATETIME = bOLISHDATETIME;
	}

	public String getPAY_DATETIME() {
		return PAY_DATETIME;
	}

	public void setPAY_DATETIME(String pAYDATETIME) {
		if(null == pAYDATETIME){
			return;
		}
		PAY_DATETIME = pAYDATETIME;
	}

	public String getBILL_INFO() {
		return BILL_INFO;
	}

	public void setBILL_INFO(String bILLINFO) {
		if(null == bILLINFO){
			return;
		}
		BILL_INFO = bILLINFO;
	}

	public int getBILL_ID() {
		return BILL_ID;
	}

	public String getCREATE_DATETIME_MIN() {
		return CREATE_DATETIME_MIN;
	}

	public void setCREATE_DATETIME_MIN(String create_datetime_min) {
		CREATE_DATETIME_MIN = create_datetime_min;
	}

	public String getBOLISH_DATETIME_MIN() {
		return BOLISH_DATETIME_MIN;
	}

	public void setBOLISH_DATETIME_MIN(String bolish_datetime_min) {
		BOLISH_DATETIME_MIN = bolish_datetime_min;
	}

	public String getPAY_DATETIME_MIN() {
		return PAY_DATETIME_MIN;
	}

	public void setPAY_DATETIME_MIN(String pay_datetime_min) {
		PAY_DATETIME_MIN = pay_datetime_min;
	}

	public String getAMOUNT_MIN() {
		return AMOUNT_MIN;
	}

	public void setAMOUNT_MIN(String amount_min) {
		AMOUNT_MIN = amount_min;
	}

	public String getCREATE_DATETIME_MAX() {
		return CREATE_DATETIME_MAX;
	}

	public void setCREATE_DATETIME_MAX(String create_datetime_max) {
		CREATE_DATETIME_MAX = create_datetime_max;
	}

	public String getBOLISH_DATETIME_MAX() {
		return BOLISH_DATETIME_MAX;
	}

	public void setBOLISH_DATETIME_MAX(String bolish_datetime_max) {
		BOLISH_DATETIME_MAX = bolish_datetime_max;
	}

	public String getPAY_DATETIME_MAX() {
		return PAY_DATETIME_MAX;
	}

	public void setPAY_DATETIME_MAX(String pay_datetime_max) {
		PAY_DATETIME_MAX = pay_datetime_max;
	}

	public String getAMOUNT_MAX() {
		return AMOUNT_MAX;
	}

	public void setAMOUNT_MAX(String amount_max) {
		AMOUNT_MAX = amount_max;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String description) {
		DESCRIPTION = description;
	}
	
	
}
