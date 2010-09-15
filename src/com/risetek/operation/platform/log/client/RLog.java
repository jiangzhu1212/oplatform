package com.risetek.operation.platform.log.client;

import com.risetek.operation.platform.launch.client.entry.Log;
import com.risetek.operation.platform.log.client.control.LogController;

public class RLog {

	public static void writeLog(String content){
		Log log = new Log();
		log.setKey("系统消息");
		log.setContent(content);
		LogController.INSTANCE.writeLos(log);
	}
	
	public static void writeLog(String userName, String content){
		Log log = new Log();
		log.setKey(userName);
		log.setContent(content);
		LogController.INSTANCE.writeLos(log);
	}
	
}
