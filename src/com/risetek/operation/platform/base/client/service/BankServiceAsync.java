package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.json.constanst.Bank;

/** 
 * @ClassName: BankServiceAsync 
 * @Description: 发卡行异步接口
 * @author JZJ 
 * @date 2010-9-2 下午02:15:20 
 * @version 1.0 
 */
public interface BankServiceAsync {

	void getAllBank(AsyncCallback<Bank[]> asyncCallback);

}
