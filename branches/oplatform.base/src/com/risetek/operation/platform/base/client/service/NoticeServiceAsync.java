package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.entry.Notice;

public interface NoticeServiceAsync {

	void getNoticeDataCount(AsyncCallback<Integer> callback);

	void getAllNotice(AsyncCallback<Notice[]> callback);

	void editNotice(Notice notice, AsyncCallback<Void> callback);

	void getNoticePage(int rowCount, AsyncCallback<Notice[]> callback);

	void getNoticePageToPoint(int rowCount, int pagePoint, AsyncCallback<Notice[]> callback);

	void addNotice(Notice notice, AsyncCallback<Void> callback);

}
