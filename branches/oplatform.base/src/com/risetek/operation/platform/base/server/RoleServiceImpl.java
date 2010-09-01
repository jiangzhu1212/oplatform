package com.risetek.operation.platform.base.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.base.client.entry.Role;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;

public class RoleServiceImpl extends RemoteServiceServlet implements RoleService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Role[] getAllRole() {
		Role[] roles = null;
		List<Role> list = new ArrayList<Role>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from risetek_role order by id";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Role role = new Role();
				role.setId(result.getInt(1));
				role.setRoleName(result.getString(2));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(list.size()>0){
			roles = new Role[list.size()];
			for(int i=0;i<roles.length;i++){
				roles[i] = list.get(i);
			}
		} else {
			roles = new Role[0];
		}
		return roles;
	}

}
