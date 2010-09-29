package com.risetek.operation.platform.start.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.client.entry.User;

public interface LoginServiceAsync {

	void getUserInfo(User user, AsyncCallback<User> callback);

	void getLoginUser(AsyncCallback<User> callback);

	void refreshUserInfo(User user, AsyncCallback<Void> callback);

	void setLoginUser(User user, AsyncCallback<Void> callback);

	void setUserLogout(User user, AsyncCallback<Void> callback);

	void updateUserInfo(User user, AsyncCallback<User> callback);

	void getNotices(String roleName, AsyncCallback<Notice[]> callback);

}
