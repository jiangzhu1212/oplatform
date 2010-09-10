package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.launch.client.entry.Role;
import com.risetek.operation.platform.launch.client.entry.RoleOperation;

@RemoteServiceRelativePath("role")
public interface RoleService extends RemoteService {

	public Role[] getAllRole();
	
	public void addRole(String roleName);
	
	public void deleteRole(String id);
	
	public void editRoleName(String id, String name);
	
	public void deleteManyRole(Role[] roles);
	
	public RoleOperation[] getRoleOperationById(String id);
	
	public RoleOperation[] addRoleOperation(RoleOperation[] ros);
	
	public RoleOperation[] deleteRoleOperation(RoleOperation[] ros);
	
	public RoleOperation[] deleteRoleOperationById(RoleOperation ro);
	
	public int getRoleDataCount();
	
	public Role[] getRolePage(int rowCount);
	
	public Role[] getRolePageToPoint(int rowCount, int pagePoint);
	
	public int getRoleOperationDataCount(String id);
	
	public RoleOperation[] getRoleOperationByIdPage(int rowCount, String id, int pagePoint);
	
}
