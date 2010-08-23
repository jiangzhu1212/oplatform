package com.risetek.operation.platform.start.client;

import com.risetek.operation.platform.base.client.BaseSink;

@SuppressWarnings("rawtypes")
public class SinkList {
	
	public static Class[] getSinkList(){
		Class[] sinkList = new Class[]{
			BaseSink.class	
		};
		return sinkList;
	}
	
}
