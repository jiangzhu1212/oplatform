package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.base.client.entry.Role;
import com.risetek.operation.platform.base.client.entry.RoleOperation;

@RemoteServiceRelativePath("role")
public interface RoleService extends RemoteService {

	public Role[] getAllRole();
	
	public Role[] addRole(String roleName);
	
	public Role[] deleteRole(String id);
	
	public Role[] editRoleName(String id, String name);
	
	public Role[] deleteManyRole(Role[] roles);
	
	public RoleOperation[] getRoleOperationById(String id);
	
	public RoleOperation[] addRoleOperation(RoleOperation[] ros);
	
	public RoleOperation[] deleteRoleOperation(RoleOperation[] ros);
	
	public RoleOperation[] deleteRoleOperationById(RoleOperation ro);
	
}
