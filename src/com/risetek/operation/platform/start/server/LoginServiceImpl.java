package com.risetek.operation.platform.start.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;
import com.risetek.operation.platform.start.client.service.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public User getUserInfo(User user) {
		User tu = new User();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_USER where USERNAME = '" + user.getUserName() + "'";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				User us = new User();
				us.setId(result.getInt(1));
				us.setUserName(result.getString(2));
				us.setUserPassword(result.getString(3));
				us.setStatus(result.getInt(4));
				us.setRole(result.getInt(5));
				us.setEmail(result.getString(6));
				us.setLastLoginTime(result.getTimestamp(7));
				us.setLastLoginAddress(result.getString(8));
				us.setRegistTime(result.getTimestamp(9));
				us.setNotes(result.getString(10));
				tu = us;
			}
			SQL = "select * from RISETEK_ROLE where ID = " + tu.getRole();
			result = statement.executeQuery(SQL);
			if(result.next()){
				tu.setRoleName(result.getString(2));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<String> ro = getUserRoleInfo(tu.getRole());
		tu.setRoleOperation(ro);
		return tu;
	}

	private ArrayList<String> getUserRoleInfo(Integer role) {
		ArrayList<String> ro = new ArrayList<String>();
		try{
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_ROLE_OPERATION where ROLE_ID = " + role;
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				String mode = result.getString(3);
				String action = result.getString(4);
				ro.add(mode + "," + action);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ro;
	}

	@Override
	public User getLoginUser() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("userinfo");
		return user;
	}

	@Override
	public void refreshUserInfo(User user) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			int status = user.getStatus();
			status++;
			String ipaddress = getIpAddr();
			String[] temp = ipaddress.split("&");
			String SQL = "update RISETEK_USER set STATUS = " + status + ", LASTLOGINTIME = sysdate, LASTLOGINADDRESS = '" + temp[1] + "' where ID = " + user.getId();
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLoginUser(User user) {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		session.setAttribute("userinfo", user);
	}

	private String getIpAddr() {
		HttpServletRequest request = this.getThreadLocalRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip + ":" + request.getRemotePort() + "&" + request.getRemoteHost();
	}

	@Override
	public void setUserLogout(User user) {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("userinfo");
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_USER set STATUS = 0 where ID = " + user.getId();
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User updateUserInfo(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
