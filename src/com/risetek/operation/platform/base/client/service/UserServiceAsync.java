package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.entry.User;

public interface UserServiceAsync {

	void getUserDataCount(AsyncCallback<Integer> callback);
	
	void getAllUser(AsyncCallback<User[]> callback);
	
	void getUserPage(int rowCount, AsyncCallback<User[]> callback);
	
	void getUserPageToPoint(int rowCount, int pagePoint, AsyncCallback<User[]> callback);

	void getUserByUserName(String userName, AsyncCallback<User[]> callback);

	void getUserByUserStatus(int status, AsyncCallback<User[]> callback);
	
	void getUserByUserRole(int role, AsyncCallback<User[]> callback);

	void registUser(User user, AsyncCallback<Void> callback);
	
	void editUserInfo(User user, AsyncCallback<Void> callback);
	
	void hangUser(String id, AsyncCallback<Void> callback);
	
	void kickUser(String id, AsyncCallback<Void> callback);
	
	void hangManyUser(User[] users, AsyncCallback<Void> callback);

	void kickManyUser(User[] users, AsyncCallback<Void> callback);

	void resetUserPassword(String id, AsyncCallback<User[]> callback);

	void resetManyUserPassword(User[] users, AsyncCallback<Void> callback);

	void sendPasswordToEmail(User user, AsyncCallback<Void> callback);

	void offUser(String id, AsyncCallback<Void> callback);

	void resetUserStatuc(String id, AsyncCallback<Void> callback);
}
