package com.risetek.operation.platform.base.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.launch.client.json.constanst.Role;
import com.risetek.operation.platform.launch.client.json.constanst.RoleOperation;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;

/**
 * @ClassName: RoleServiceImpl.java
 * @Description: 权限管理服务器端数据请求方法
 * @Author Amber
 * @Data 2010-9-3
 * 
 */
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
			statement.close();
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

	@Override
	public void addRole(String roleName) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select G_VALUE from GENERATOR_TABLE where G_KEY='RISETEK_ROLE'";
			ResultSet result = statement.executeQuery(SQL);
			int id = 0;
			if(result.next()){
				id = result.getInt(1);
			}
			SQL = "insert into RISETEK_ROLE (ID, ROLENAME) VALUES (" + id + ", '" + roleName + "')";
			statement.execute(SQL);
			id++;
			SQL = "update GENERATOR_TABLE set G_VALUE = " + id + " where G_KEY='RISETEK_ROLE'";
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return getAllRole();
	}

	@Override
	public RoleOperation[] getRoleOperationById(String id) {
		RoleOperation[] ros = null;
		List<RoleOperation> list = new ArrayList<RoleOperation>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_ROLE_OPERATION where ROLE_ID = " + id + " order by id";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				RoleOperation ro = new RoleOperation();
				ro.setId(result.getInt(1));
				ro.setRoleId(result.getInt(2));
				ro.setOperationMode(result.getString(3));
				ro.setOperationAction(result.getString(4));
				list.add(ro);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			ros = new RoleOperation[list.size()];
			for(int i=0;i<ros.length;i++){
				ros[i] = list.get(i);
			}
		} else {
			ros = new RoleOperation[0];
		}
		return ros;
	}

	@Override
	public void deleteRole(String id) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "delete RISETEK_ROLE where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return getAllRole();
	}
	
	@Override
	public void deleteManyRole(Role[] roles) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			for(int i=0;i<roles.length;i++){
				Role role = roles[i];
				String SQL = "delete RISETEK_ROLE where ID = " + role.getId();
				statement.execute(SQL);
				SQL = "delete RISETEK_ROLE_OPERATION where ROLE_ID = " + role.getId();
				statement.execute(SQL);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return getAllRole();
	}

	@Override
	public void editRoleName(String id, String name) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_ROLE set ROLENAME = '" + name + "' where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return getAllRole();
	}

	@Override
	public RoleOperation[] addRoleOperation(RoleOperation[] ros) {
		RoleOperation tro = ros[0];
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select G_VALUE from GENERATOR_TABLE where G_KEY='RISETEK_ROLE_OPERATION'";
			ResultSet result = statement.executeQuery(SQL);
			int id = 0;
			if(result.next()){
				id = result.getInt(1);
			}
			for(int i=0;i<ros.length;i++){
				RoleOperation ro = ros[i];
				SQL = "insert into RISETEK_ROLE_OPERATION (ID, ROLE_ID, OPERATION_MODE, OPERATION_ACTION) values (" + id + ", " + ro.getRoleId() + ", '" + ro.getOperationMode() + "', '" + ro.getOperationAction() + "')";
				statement.execute(SQL);
				id++;
			}
			SQL = "update GENERATOR_TABLE set G_VALUE = " + id + " where G_KEY='RISETEK_ROLE_OPERATION'";
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getRoleOperationById(tro.getRoleId().toString());
	}

	@Override
	public RoleOperation[] deleteRoleOperation(RoleOperation[] ros) {
		RoleOperation tro = ros[0];
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			for(int i=0;i<ros.length;i++){
				RoleOperation ro = ros[i];
				String SQL = "delete RISETEK_ROLE_OPERATION where ID = " + ro.getId();
				statement.execute(SQL);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getRoleOperationById(tro.getRoleId().toString());
	}

	@Override
	public RoleOperation[] deleteRoleOperationById(RoleOperation ro) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "delete RISETEK_ROLE_OPERATION where ID = " + ro.getId();
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getRoleOperationById(ro.getRoleId().toString());
	}

	@Override
	public int getRoleDataCount() {
		int count = 0;
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select count(*) from RISETEK_ROLE";
			ResultSet result = statement.executeQuery(SQL);
			if(result.next()){
				count = result.getInt(1);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Role[] getRolePage(int rowCount) {
		Role[] roles = null;
		List<Role> list = new ArrayList<Role>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_ROLE where rownum<=" + rowCount + " order by ID";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Role role = new Role();
				role.setId(result.getInt(1));
				role.setRoleName(result.getString(2));
				list.add(role);
			}
			statement.close();
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

	@Override
	public Role[] getRolePageToPoint(int rowCount, int pagePoint) {
		Role[] roles = null;
		List<Role> list = new ArrayList<Role>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			int start = rowCount * (pagePoint-1);
			int end = rowCount * pagePoint;
			String SQL = "select * from (select a.*, rownum rn from (select * from RISETEK_ROLE order by id) a where rownum <= " + end + ")where rn > " + start;
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Role role = new Role();
				role.setId(result.getInt(1));
				role.setRoleName(result.getString(2));
				list.add(role);
			}
			statement.close();
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

	@Override
	public int getRoleOperationDataCount(String id) {
		int count = 0;
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select count(*) from RISETEK_ROLE_OPERATION where ROLE_ID = " + id;
			ResultSet result = statement.executeQuery(SQL);
			if(result.next()){
				count = result.getInt(1);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public RoleOperation[] getRoleOperationByIdPage(int rowCount, String id, int pagePoint) {
		RoleOperation[] ros = null;
		List<RoleOperation> list = new ArrayList<RoleOperation>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			int start = rowCount * (pagePoint-1);
			int end = rowCount * pagePoint;
			String SQL = "select * from (select a.*, rownum rn from (select * from RISETEK_ROLE_OPERATION where ROLE_ID = " + id + " order by id) a where rownum <= " + end + ")where rn > " + start;
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				RoleOperation ro = new RoleOperation();
				ro.setId(result.getInt(1));
				ro.setRoleId(result.getInt(2));
				ro.setOperationMode(result.getString(3));
				ro.setOperationAction(result.getString(4));
				list.add(ro);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			ros = new RoleOperation[list.size()];
			for(int i=0;i<ros.length;i++){
				ros[i] = list.get(i);
			}
		} else {
			ros = new RoleOperation[0];
		}
		return ros;
	}
}
