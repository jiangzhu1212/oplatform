package com.risetek.operation.platform.base.client.constanst;

import com.risetek.operation.platform.base.client.entry.Constanst;

public interface Card007Constanst extends Constanst {

	final String CHARGE_PHONE_NUMBER = "CHARGE_PHONE_NUMBER";

	final String CHARGE_PHONE_NUMBER_ZH = "充值手机号";

	final String AMOUNT = "Amount";

	final String AMOUNT_ZH = "充值金额(元)";

	final String STATUS = "STATUS";

	final String STATUS_ZH = "状态";

	final String DATETIME = "Datetime";

	final String DATETIME_ZH = "订单时间";

	final String RETINFO = "ReturnError";

	final String RETINFO_ZH = "返回信息";

	final String PAYSTATE = "PAYSTATUS";

	final String PAYSTATE_ZH = "支付状态";

	final String BILL_EXTERN_ID = "BILL_EXTERN_ID";

	final String BILL_EXTERN_ID_ZH = "007卡订单信息";

	final String CHARGE_DATE = "ChargeDate";

	final String CHARGE_DATE_ZH = "充值时间";

	final String CHARTE_TIME = "ChargeTime";

	final String CHARGE_TIME_ZH = "充值时间";

	final String PAY_DATETIME = "PAY_DATETIME";

	final String PAY_DATETIME_ZH = "支付日期";

	/* 设置请求超时的时间 */
	final int MAX_REQUEST_TIME = 10000;

	final int Card007InfoSize = 7;

	final String PAY_RET = "PAY_RET";

	final String BILL_RESULT = "BILL_RESULT";

	final String PAY_DATETIME_MAX = "PAY_DATETIME_MAX";

	final String PAY_DATETIME_MIN = "PAY_DATETIME_MIN";
	
	final String ACTION_NAME_SELECT_CARD_007 = "SELECT_CARD_INFORMATION";
}
