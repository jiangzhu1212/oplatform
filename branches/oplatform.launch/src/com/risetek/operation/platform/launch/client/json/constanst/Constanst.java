package com.risetek.operation.platform.launch.client.json.constanst;

public interface Constanst {

	final String TRANS_ID = "TRANS_ID";
	
	final String CUSTOMER_ID = "CUSTOMER_ID";
	
	final String PHONE_NUMBER = "PHONE_NUMBER";
	
	final String TRUE = "TRUE";
	
	final String FALSE = "FALSE" ;

	public static final String BILLS_WEB_CLIENT="BILLS_REQUEST";
	
	public static final String CTI_PACKET="CTI_PACKET";
	
	/******************************************* JSON信息结构 *****************************************************************************/
	
	final String ACTION_NAME = "ACTION_NAME";

	final String ACTION_INFO = "ACTION_INFO";
	
	final String QUERY_DATA = "QUERY_DATA";
	/**
	 * 2.0开始页码
	 */
	final String PAGE_POS = "PAGE_POS";
	/**
	 * 2.0总页数
	 */
	final String PAGE_TOTAL = "PAGE_TOTAL" ;
	/**
	 * 2.0数据项总数
	 */
	final String ITEM_TOTAL = "ITEM_TOTAL" ;
	/**
	 * 2.0返回的查询数据项
	 */
	final String ITEMS = "ITEMS" ;
	/**
	 * 每页数量
	 */
	final String PAGE_SIZE = "PAGE_SIZE";

	final int OP_TRUE = 0;

	final String RETURN_CODE = "RETURN_CODE";

	final String RETURN_MESSAGE = "RETURN_MESSAGE";
	
	final String MAX_COUNT = "MAX_COUNT";
	
	final String START_POS = "START_POS";
	
	final String QUERY_TOTAL = "QUERY_TOTAL";
	
	final String STATUS = "STATUS";

	final String BILL_INFO = "BILL_INFO";

	final String RESPONSE_CODE = "RESPONSE_CODE";

	final String RESPONSE_INFO = "RESPONSE_INFO";

	final String PAGE_INDEX = "PAGE_INDEX";

	final String TOTAL = "TOTAL";

	final String JCARDS_INFO = "JCARDS_INFO";
	
	/****************************************action_name*******************************************************/
	 
	/**
	 * 添加银行卡
	 */
	final String ACTION_NAME_ADD_BANK_INFO = "ADD_BANK_INFO" ;
	/**
	 * 修改银行信息
	 */
	final String ACTION_NAME_MODIFY_BANK_INFO = "MODIFY_BANK_INFO" ;
	/**
	 * 查询银行卡信息
	 */
	final String ACTION_NAME_QUERY_BANK_INFO = "QUERY_BANK_INFO" ;
	/**
	 * 添加发卡终端
	 */
	final String ACTION_NAME_ADD_CARD_TERMINAL = "ADD_CARD_TERMINAL" ;
	/**
	 * 修改发卡终端
	 */
	final String ACTION_NAME_MODIFY_CARD_TERMINAL = "MODIFY_CARD_TERMINAL" ;
	/**
	 * 查询发卡终端
	 */
	final String ACTION_NAME_QUERY_CARD_TERMINAL = "QUERY_CARD_TERMINAL" ;
	/**
	 * 查询发卡终端密钥
	 */
	final String ACTION_NAME_QUERY_TM_KEY = "QUERY_TM_KEY" ;
	/**
	 * 添加渠道信息
	 */
	final String ACTION_NAME_ADD_CHANNEL_INFO = "ADD_CHANNEL_INFO" ;
	/**
	 * 修改渠道信息
	 */
	final String ACTION_NAME_MODIFY_CHANNEL_INFO = "MODIFY_CHANNEL_INFO" ;
	/**
	 * 查询渠道信息
	 */
	final String ACTION_NAME_QUERY_CHANNEL_INFO = "QUERY_CHANNEL_INFO" ;
	/**
	 * 添加批次
	 */
	final String ACTION_NAME_ADD_CARD_BATCH = "ADD_CARD_BATCH" ;
	/**
	 * 修改批次信息
	 */
	final String ACTION_NAME_MODIFY_CARD_BATCH = "MODIFY_CARD_BATCH" ;
	/**
	 * 查询批次信息
	 */
	final String ACTION_NAME_QUERY_CARD_BATCH = "QUERY_CARD_BATCH" ;
	/**
	 * 添加卡片信息
	 */
	final String ACTION_NAME_ADD_CARD_INFO = "ADD_CARD_INFO" ;
	/**
	 * 修改卡片信息
	 */
	final String ACTION_NAME_MODIFY_CARD_INFO = "MODIFY_CARD_INFO" ;
	/**
	 * 查询卡片信息
	 */
	final String ACTION_NAME_QUERY_CARD_INFO = "QUERY_CARD_INFO" ;
	/**
	 * 添加账户信息
	 */
	final String ACTION_NAME_ADD_ACCOUNT_INFO = "ADD_ACCOUNT_INFO" ;
	/**
	 * 修改账户信息
	 */
	final String ACTION_NAME_MODIFY_ACCOUNT_INFO = "MODIFY_ACCOUNT_INFO" ;
	/**
	 * 查询账户信息
	 */
	final String ACTION_NAME_QUERY_ACCOUNT_INFO = "QUERY_ACCOUNT_INFO" ;
	/**
	 * 添加账户绑定信息
	 */
	final String ACTION_NAME_ADD_AC_BIND = "ADD_AC_BIND" ;
	/**
	 * 修改账户绑定信息
	 */
	final String ACTION_NAME_MODIFY_AC_BIND = "MODIFY_AC_BIND" ;
	/**
	 * 查询账户绑定信息
	 */
	final String ACTION_NAME_QUERY_AC_BIND = "QUERY_AC_BIND" ;
	/**
	 * 添加用户信息
	 */
	final String ACTION_NAME_ADD_CUSTOMER_INFO = "ADD_CUSTOMER_INFO" ;
	/**
	 * 修改用户信息
	 */
	final String ACTION_NAME_MODIFY_CUSTOMER_INFO = "MODIFY_CUSTOMER_INFO" ;
	/**
	 * 查看用户信息
	 */	
	final String ACTION_NAME_QUERY_CUSTOMER_INFO = "QUERY_CUSTOMER_INFO";
	/**
	 * 添加业务信息
	 */
	final String ACTION_NAME_ADD_TRANSACTION_INFO = "ADD_TRANSACTION_INFO" ;
	/**
	 * 修改业务信息
	 */
	final String ACTION_NAME_MODIFY_TRANSACTION_INFO = "MODIFY_TRANSACTION_INFO" ;
	/**
	 * 查询业务信息
	 */
	final String ACTION_NAME_QUERY_TRANSACTION_INFO = "QUERY_TRANSACTION_INFO" ;
	/**
	 * 添加业务使能信息
	 */
	final String ACTION_NAME_ADD_TRANS_ENABLE = "ADD_TRANS_ENABLE" ;
	/**
	 * 修改业务使能信息
	 */
	final String ACTION_NAME_MODIFY_TRANS_ENABLE = "MODIFY_TRANS_ENABLE" ;
	/**
	 * 查询业务使能信息
	 */
	final String ACTION_NAME_QUERY_TRANS_ENABLE = "QUERY_TRANS_ENABLE" ;
	/**
	 * 添加业务绑定信息
	 */
	final String ACTION_NAME_ADD_TRANS_BIND = "ADD_TRANS_BIND" ;
	/**
	 * 修改业务绑定信息
	 */
	final String ACTION_NAME_MODIFY_TRANS_BIND = "MODIFY_TRANS_BIND" ;
	/**
	 * 查看业务绑定信息
	 */	
	final String ACTION_NAME_QUERY_TRANS_BIND = "QUERY_TRANS_BIND";
	/**
	 * 添加公告信息
	 */
	final String ACTION_NAME_ADD_ANNOUCEMENT_INFO = "ADD_ANNOUCEMENT_INFO" ;
	/**
	 * 修改公告信息
	 */
	final String ACTION_NAME_MODIFY_ANNOUCEMENT_INFO = "MODIFY_ANNOUCEMENT_INFO" ;
	/**
	 * 查询公告信息
	 */
	final String ACTION_NAME_QUERY_ANNOUCEMENT_INFO = "QUERY_ANNOUCEMENT_INFO" ;
	/**
	 * 添加交易信息
	 */
	final String ACTION_NAME_ADD_TRADE_INFO = "ADD_TRADE_INFO" ;
	/**
	 * 修改交易信息
	 */
	final String ACTION_NAME_MODIFY_TRADE_INFO = "MODIFY_TRADE_INFO" ;
	/**
	 * 查询交易信息
	 */	
	final String ACTION_NAME_QUERY_TRADE_INFO = "QUERY_TRADE_INFO";
	/**
	 * 添加账单信息
	 */
	final String ACTION_NAME_ADD_BILL_INFO = "ADD_BILL_INFO" ;
	/**
	 * 修改账单信息
	 */
	final String ACTION_NAME_MODIFY_BILL_INFO = "MODIFY_BILL_INFO" ;
	/**
	 * 查询账单信息
	 */	
	final String ACTION_NAME_QUERY_BILL_INFO = "QUERY_BILL_INFO";
	/**
	 * 添加支付凭证信息
	 */
	final String ACTION_NAME_ADD_CERTIFICATE_INFO = "ADD_CERTIFICATE_INFO" ;
	/**
	 * 查询支付凭证信息
	 */	
	final String ACTION_NAME_QUERY_CERTIFICATE_INFO = "QUERY_CERTIFICATE_INFO";
	/**
	 * 添加电子货物信息
	 */
	final String ACTION_NAME_ADD_GOODS_INFO = "ADD_GOODS_INFO" ;
	/**
	 * 修改电子货物信息
	 */
	final String ACTION_NAME_MODIFY_GOODS_INFO = "MODIFY_GOODS_INFO" ;
	/**
	 * 查询电子货物信息
	 */	
	final String ACTION_NAME_QUERY_GOODS_INFO = "QUERY_GOODS_INFO";
	/**
	 * 添加电子货物二维码信息
	 */
	final String ACTION_NAME_ADD_TDBC_INFO = "ADD_TDBC_INFO" ;
	/**
	 * 查询电子货物二维码信息
	 */	
	final String ACTION_NAME_QUERY_TDBC_INFO = "QUERY_TDBC_INFO";
	
	/**
	 * 全国直冲查询
	 */
	final String ACTION_NAME_SELECT_CARD_007 = "SELECT_CARD_INFORMATION";
	/**
	 * 查询骏卡
	 */
	final String ACTION_NAME_SELECT_JCARD = "SELECT_JCARD";
	
	final String ACTION_NAME_SELECT_BILL_INFORMATION = "SELECT_BILL_INFORMATION";
	/**
	 * 修改骏卡状态
	 */
	final String ACTION_NAME_MODIFY_STATUS = "MODIFY_STATUS";
	/**
	 * 上传骏卡
	 */
	final String ACTION_NAME_IMPORT_DATA = "IMPORT_DATA";
	/**
	 * 骏卡对账
	 */
	final String ACTION_NAME_BALANCE = "BALANCE";
	/**
	 * 票宝宝出票
	 */
	final String ACTION_NAME_EDIT_PBABY = "ACTION_NAME_EDIT_PBABY";
}
