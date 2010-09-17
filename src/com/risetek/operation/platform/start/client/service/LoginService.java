package com.risetek.operation.platform.start.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.launch.client.entry.User;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	public User getUserInfo(User user);
	
	public User getLoginUser();
	
	public void refreshUserInfo(User user);
	
	public void setLoginUser(User user);
	
	public void setUserLogout(User user);
	
	public User updateUserInfo(User user);
	
}
