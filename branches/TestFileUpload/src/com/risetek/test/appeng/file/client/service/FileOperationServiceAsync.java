package com.risetek.test.appeng.file.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.test.appeng.file.client.FileEntry;


public interface FileOperationServiceAsync {

	void listFile(AsyncCallback<FileEntry[]> callback);

	void deleteFile(String path, AsyncCallback<FileEntry[]> callback);

}
