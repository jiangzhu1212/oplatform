package com.risetek.operation.platform.start.client;

import com.risetek.operation.platform.base.client.BaseSink;

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
			BaseSink.class	
		};
		return sinkList;
	}
	
}
