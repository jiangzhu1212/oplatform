package com.risetek.operation.platform.start.client;

import com.risetek.operation.platform.base.client.AcountSink;
import com.risetek.operation.platform.base.client.AnnoucementSink;
import com.risetek.operation.platform.base.client.BankSink;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.base.client.CustomerSink;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.base.client.TdbcSink;
import com.risetek.operation.platform.base.client.TransEnableSink;
import com.risetek.operation.platform.base.client.TransactionSink;
import com.risetek.operation.platform.process.client.ProcessSink;

/**
 * @author Amber
 * 功能：模块列表类
 * 2010-8-23 下午11:59:28
 */
@SuppressWarnings("rawtypes")
public class SinkList {
	
	/**
	 * 功能：注册功能模块
	 *
	 * Class[]
	 * @return 返回功能模块列表
	 */
	public static Class[] getSinkList(){
		Class[] sinkList = new Class[]{
			BaseSink.class,
			ProcessSink.class,
			CustomerSink.class,
			TransactionSink.class,
			BankSink.class,
			AcountSink.class,
			TdbcSink.class,
			TransEnableSink.class,
			AnnoucementSink.class,
			RoleConfigSink.class
		};
		return sinkList;
	}
	
}
