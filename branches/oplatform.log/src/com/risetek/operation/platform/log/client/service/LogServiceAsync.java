package com.risetek.operation.platform.log.client.service;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.entry.Log;

public interface LogServiceAsync {

	void getLogDataCount(AsyncCallback<Integer> callback);

	void getAllLog(AsyncCallback<Log[]> callback);

	void getLogPage(int rowCount, AsyncCallback<Log[]> callback);

	void getLastLogPage(int rowCount, AsyncCallback<Log[]> callback);

	void addLog(Log log, AsyncCallback<Void> callback);

	void clearAllLog(AsyncCallback<Void> callback);

	void getLogByKey(String key, AsyncCallback<Log[]> callback);

	void getLogByContent(String cont, AsyncCallback<Log[]> callback);

	void getLogByTime(Timestamp start, Timestamp end, AsyncCallback<Log[]> callback);

}
