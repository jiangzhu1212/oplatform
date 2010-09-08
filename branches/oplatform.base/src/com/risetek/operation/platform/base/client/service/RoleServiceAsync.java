package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.json.constanst.Role;
import com.risetek.operation.platform.launch.client.json.constanst.RoleOperation;

public interface RoleServiceAsync {

	void getAllRole(AsyncCallback<Role[]> callback);

	void addRole(String roleName, AsyncCallback<Void> callback);

	void getRoleOperationById(String id, AsyncCallback<RoleOperation[]> callback);

	void deleteRole(String id, AsyncCallback<Void> callback);

	void editRoleName(String id, String name, AsyncCallback<Void> callback);

	void addRoleOperation(RoleOperation[] ros, AsyncCallback<RoleOperation[]> callback);

	void deleteRoleOperation(RoleOperation[] ros, AsyncCallback<RoleOperation[]> callback);

	void deleteRoleOperationById(RoleOperation ro, AsyncCallback<RoleOperation[]> callback);

	void deleteManyRole(Role[] roles, AsyncCallback<Void> callback);

	void getRoleDataCount(AsyncCallback<Integer> callback);

	void getRolePage(int rowCount, AsyncCallback<Role[]> callback);

	void getRolePageToPoint(int rowCount, int pagePoint, AsyncCallback<Role[]> callback);

	void getRoleOperationDataCount(String id, AsyncCallback<Integer> callback);

	void getRoleOperationByIdPage(int rowCount, String id, int pagePoint,	AsyncCallback<RoleOperation[]> callback);

}
