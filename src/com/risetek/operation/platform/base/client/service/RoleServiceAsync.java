package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.json.constanst.Role;
import com.risetek.operation.platform.launch.client.json.constanst.RoleOperation;

public interface RoleServiceAsync {

	void getAllRole(AsyncCallback<Role[]> callback);

	void addRole(String roleName, AsyncCallback<Role[]> callback);

	void getRoleOperationById(String id, AsyncCallback<RoleOperation[]> callback);

	void deleteRole(String id, AsyncCallback<Role[]> callback);

	void editRoleName(String id, String name, AsyncCallback<Role[]> callback);

	void addRoleOperation(RoleOperation[] ros, AsyncCallback<RoleOperation[]> callback);

	void deleteRoleOperation(RoleOperation[] ros, AsyncCallback<RoleOperation[]> callback);

	void deleteRoleOperationById(RoleOperation ro, AsyncCallback<RoleOperation[]> callback);

	void deleteManyRole(Role[] roles, AsyncCallback<Role[]> callback);

}
