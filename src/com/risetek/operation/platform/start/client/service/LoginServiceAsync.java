package com.risetek.operation.platform.start.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.entry.User;

public interface LoginServiceAsync {

	void getUserInfo(User user, AsyncCallback<User> callback);

	void getLoginUser(AsyncCallback<User> callback);

	void refreshUserInfo(User user, AsyncCallback<Void> callback);

	void setLoginUser(User user, AsyncCallback<Void> callback);

}
