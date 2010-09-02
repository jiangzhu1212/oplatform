package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.base.client.entry.Bank;

/** 
 * @ClassName: BankService 
 * @Description: 发卡行接口
 * @author JZJ 
 * @date 2010-9-2 下午02:15:53 
 * @version 1.0 
 */

@RemoteServiceRelativePath("bank")
public interface BankService extends RemoteService {

	Bank[] getAllBank();
}
