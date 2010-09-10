package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.launch.client.entry.User;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	public Integer getUserDataCount();
	
	public User[] getAllUser();
	
	public User[] getUserPage(int rowCount);
	
	public User[] getUserPageToPoint(int rowCount, int pagePoint);
	
	public User[] getUserByUserName(String userName);
	
	public User[] getUserByUserStatus(int status);
	
	public User[] getUserByUserRole(int role);
	
	public void registUser(User user);
	
	public void editUserInfo(User user);
	
	public void resetUserStatuc(String id);
	
	public void offUser(String id);
	
	public void hangUser(String id);
	
	public void kickUser(String id);
	
	public void hangManyUser(User[] users);
	
	public void kickManyUser(User[] users);
	
	public User[] resetUserPassword(String id);
	
	public void resetManyUserPassword(User[] users);
	
	public void sendPasswordToEmail(User user);
	
}
