package com.risetek.test.appeng.file.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.test.appeng.file.client.FileEntry;

@RemoteServiceRelativePath("list")
public interface FileOperationService extends RemoteService {

	public FileEntry[] listFile();
	
	public FileEntry[] deleteFile(String path);
	
}
