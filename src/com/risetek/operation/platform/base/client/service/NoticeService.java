package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.launch.client.entry.Notice;

@RemoteServiceRelativePath("notice")
public interface NoticeService extends RemoteService {

	public Integer getNoticeDataCount();
	
	public Notice[] getAllNotice();
	
	public Notice[] getNoticePage(int rowCount);
	
	public Notice[] getNoticePageToPoint(int rowCount, int pagePoint);
	
	public void addNotice(Notice notice);
	
	public void editNotice(Notice notice);
	
}
