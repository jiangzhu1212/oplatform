package com.risetek.operation.platform.base.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.base.client.service.UserService;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.util.Util;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getUserDataCount() {
		int count = 0;
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select count(*) from RISETEK_USER";
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
	public User[] getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUserPage(int rowCount) {
		User[] users = null;
		List<User> list = new ArrayList<User>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_USER where rownum<=" + rowCount + " order by ID";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				User user = new User();
				user.setId(result.getInt(1));
				user.setUserName(result.getString(2));
				user.setUserPassword(result.getString(3));
				user.setStatus(result.getInt(4));
				user.setRole(result.getInt(5));
				user.setEmail(result.getString(6));
				user.setLastLoginTime(result.getTimestamp(7));
				user.setLastLoginAddress(result.getString(8));
				user.setRegistTime(result.getTimestamp(9));
				user.setNotes(result.getString(10));
				list.add(user);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			users = new User[list.size()];
			for(int i=0;i<users.length;i++){
				users[i] = list.get(i);
			}
		} else {
			users = new User[0];
		}
		return users;
	}

	@Override
	public User[] getUserPageToPoint(int rowCount, int pagePoint) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User[] getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUserByUserStatus(int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUserByUserRole(int role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registUser(User user) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select G_VALUE from GENERATOR_TABLE where G_KEY='RISETEK_USER'";
			ResultSet result = statement.executeQuery(SQL);
			int id = 0;
			if(result.next()){
				id = result.getInt(1);
			}
			SQL = "insert into RISETEK_USER (ID, USERNAME, USERPASSWORD, STATUS, ROLE, " +
					"EMAIL, LASTLOGINTIME, LASTLOGINADDRESS, REGISTTIME, NOTES) VALUES (" + 
					id + ", '" + user.getUserName() + "', '" +
					user.getUserPassword() + "', " + 
					user.getStatus() + ", " + 
					user.getRole() + ", '" + 
					user.getEmail() + "', sysdate, '" + 
					user.getLastLoginAddress() + "', sysdate, '" + 
					user.getNotes() + "')";
			statement.execute(SQL);
			id++;
			SQL = "update GENERATOR_TABLE set G_VALUE = " + id + " where G_KEY='RISETEK_USER'";
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editUserInfo(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hangUser(String id) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_USER set STATUS = -1 where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void kickUser(String id) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_USER set STATUS = 0 where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void hangManyUser(User[] users) {
		// TODO Auto-generated method stub

	}

	@Override
	public void kickManyUser(User[] users) {
		// TODO Auto-generated method stub

	}

	@Override
	public User[] resetUserPassword(String id) {
		User[] users = new User[1];
		User tu = new User();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_USER where id = " + id;
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				User user = new User();
				user.setId(result.getInt(1));
				user.setUserName(result.getString(2));
				user.setUserPassword(result.getString(3));
				user.setStatus(result.getInt(4));
				user.setRole(result.getInt(5));
				user.setEmail(result.getString(6));
				user.setLastLoginTime(result.getTimestamp(7));
				user.setLastLoginAddress(result.getString(8));
				user.setRegistTime(result.getTimestamp(9));
				user.setNotes(result.getString(10));
				tu = user;
			}
			String newPws = Util.makeRandomString();
			SQL = "update RISETEK_USER set USERPASSWORD = '" + newPws + "' , STATUS = -2 where ID = " + id;
			statement.execute(SQL);
			tu.setUserPassword(newPws);
			tu.setStatus(-2);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		users[0] = tu;
		return users;
	}

	@Override
	public void resetManyUserPassword(User[] users) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPasswordToEmail(User user) {
//		try {
//			sendEmail(user);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	 
	public static void sendEmail(User user) throws MessagingException {
		String host = "smtp.gmail.com";
		String from = "epay.operation@gmail.com";
		String pass = "risetekpass";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // 在本行添加
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		String[] to = { user.getEmail() }; // 在本行添加
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		InternetAddress[] toAddress = new InternetAddress[to.length]; // 获取地址的array
		for (int i = 0; i < to.length; i++) { // 从while循环更改而成
			toAddress[i] = new InternetAddress(to[i]);
		}
		for (int i = 0; i < toAddress.length; i++) { // 从while循环更改而成
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}
		message.setSubject("运维平台密码告知[系统自动发送]");
		String msg = "用户：" + user.getUserName() + "您好！\n\t欢迎使用手机支付运维平台。\n\t" +
				"请在使用过程中严格按照公司要求操作，如有问题请及时联系运维中心负责人。\n\t" +
				"以下为您的登录密码，为了使用方便请登录后修改为容易记忆的密码。\n\t\t" +
				user.getUserPassword() + "\n\t" +
						"运维中心联系电话：028-83202011\n\t" +
						"此邮件为系统自动发出，请勿回复。";
		message.setText(msg);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	@Override
	public void offUser(String id) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_USER set STATUS = -3 where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetUserStatuc(String id) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_USER set STATUS = 0 where ID = " + id;
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}