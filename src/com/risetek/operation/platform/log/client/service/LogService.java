package com.risetek.operation.platform.log.client.service;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.launch.client.entry.Log;

@RemoteServiceRelativePath("log")
public interface LogService extends RemoteService {

	public Integer getLogDataCount();
	
	public Log[] getAllLog();
	
	public Log[] getLogPage(int rowCount);
	
	public Log[] getLastLogPage(int rowCount);
	
	public void addLog(Log log);
	
	public void clearAllLog();
	
	public Log[] getLogByKey(String key);
	
	public Log[] getLogByContent(String cont);
	
	public Log[] getLogByTime(Timestamp start, Timestamp end);
	
}
