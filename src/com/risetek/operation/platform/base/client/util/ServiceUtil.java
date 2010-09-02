package com.risetek.operation.platform.base.client.util;

import com.google.gwt.core.client.GWT;


public abstract class ServiceUtil {


	public final static int PAGE_SIZE = 50;
	
	public final static int TIMEOUTMILLIS = 60 * 1000;
	
	public final static int JK_ATTRIBUTE_NUM = 4;
	
	public static final int SN_SIZE = 5;
	
	public static final int NUMBER_SIZE = 16;
	
	public static final int PWD_SIZE = 16;
	
	public final static String PRIVILEGE="privilege";
	
	public final static String LOG_FAIL = "FAIL   ";
	
	public final static String LOG_SUCCESS = "SUCCESS";

	public final static String DEL_CONFIRM = "确定删除被选择的记录？";

	public final static String EDIT_RECORD = "请选择一条记录执行修改操作！";

	public final static String DEL_NORECORD = "请至少选择一条记录执行删除操作！";

	public final static String DEL_FAIL = "数据库操作：删除记录失败！";
	
	public final static String DEL_SUC= "数据库操作：删除记录成功！";

	public final static String UPDATE_FAIL = "数据库操作：更新记录失败！";
	
	public final static String UPDATE_SUC= "数据库操作：更新记录成功！";

	public final static String ADD_FAIL = "数据库操作：添加记录失败！";
	
	public final static String ADD_SUC= "数据库操作：添加记录成功！";

	public static final String REQUEST_ERROR = "请求发送失败！";

	public static final String BOLISH_MESSAGE = "确定作废选中的账单？";
	
	public static final String BOLISH_CUSTOMER_MESSAGE = "确定作废选中的用户？";
	
	public static final String BOLISH_TICKET_MESSAGE = "确定作废选中的电子票？";
	
	public static final String DISTRIBUTE_E_TICKET_EXCETED = "<font color = 'red'>此用户已有电子票，确定还要添加电子票？</font>";
	
	public static final String DISTRIBUTE_E_TICKET = "确定添加电子票？";

	public static final String BILL_INFO_NOT_JSON = "交易额外信息格式不正确！";
	
	public static final String SELECT_A_MESSAGE = "请选择一条信息操作";
	
	public static final String MESSAGE_HAS_BOLISHED = "已是作废信息";

	public static final String REPLACE_CUSTOMER_CSN = "确定更换此用户的CSN？";
	
	public final static String SELECT_RECORD = "请选择一条记录执行操作！";
	
	public static final int CSN_SIZE = 20;
	
	public static final String CSN_LENGTH_NOT_RIGHT = "CSN长度不是"+CSN_SIZE+"位!";
	
	public final static String BASE_MODULE_URL = GWT.getModuleBaseURL();

	public final static String SERVICE_ROOT_PATH = BASE_MODULE_URL+ "../hibernate4gwt/";

	public static final String USERNAME = "userName";
	
	
	public static String string2unicode(String s) {
		if (s == null) {
			return null;
		}

		StringBuffer result = new StringBuffer();
		int i;
		for (i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 0x2018) {
				result.append('\\');
				result.append('u');
				String hex = Integer.toHexString(s.charAt(i));
				result.append(hex);
			} else {
				result.append(s.charAt(i));
			}
		}
		return result.toString();
	}
}
