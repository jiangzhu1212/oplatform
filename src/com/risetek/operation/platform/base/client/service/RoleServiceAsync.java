package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.base.client.entry.Role;

public interface RoleServiceAsync {

	void getAllRole(AsyncCallback<Role[]> callback);

}
