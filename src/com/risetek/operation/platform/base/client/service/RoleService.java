package com.risetek.operation.platform.base.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.risetek.operation.platform.base.client.entry.Role;

@RemoteServiceRelativePath("role")
public interface RoleService extends RemoteService {

	public Role[] getAllRole();
	
}
