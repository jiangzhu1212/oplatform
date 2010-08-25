package com.risetek.blackScreen.client.run;

import java.io.Console;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.risetek.blackScreen.client.BlackScreenClient;
import com.risetek.blackScreen.client.database.ConnectDataBase;
import com.risetek.blackScreen.client.entry.UserEntry;
import com.risetek.blackScreen.client.entry.UserLevelEntry;
import com.risetek.blackScreen.client.utils.LogRecord;
import com.risetek.blackScreen.client.utils.Util;
import com.risetek.blackScreen.client.utils.WaitThread;


public class LoginManager {

	Connection conn;
	Console con;
	UserEntry uInfo;
	Statement statment;
//	UserStatus us;
	String localAddress;
	Timer checkUserStatus;
	
	public LoginManager(Connection conn, Console con) {
//		us = new UserStatus();
		this.conn = conn;
		this.con = con;
		try {
			statment = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getLocalAddress();
	}

	public String getLocalAddress(){
		try {
			String inet = InetAddress.getLocalHost().getHostAddress();
			localAddress = inet;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(Util.isLinux){
			localAddress = BlackScreenClient.ip;
		}
		return localAddress;
	}
	
	public boolean login(){
		int count = 0;
		while(count<3){
			System.out.printf("UserName:");
			String in = BlackScreenClient.tinput();
			uInfo = getUserInfo(in);
			if(null!=uInfo){
				System.out.printf("PassWord:");
				String oldPws = uInfo.getPassWord();
//				in = BlackScreenClient.tinput();
				in = new String(System.console().readPassword());
				if(oldPws.equals(in)){
					if(uInfo.getStatus()>0){
						if(uInfo.getStatus()<0){
//							String err = "用户已登录或上次登录时未正常退出";
//							String message = "用户：" + uInfo.getUserName() + " 在地址：" + 
//							uInfo.getLastLoginAddress() + " 因为 \"" + err + "\" 登录失败。";
//							LogRecord.writeSystemLog(message);
//							userStatusErr(err);
//							return false;
//						} else {
							String err = "用户状态不正常";
							String message = "用户：" + uInfo.getUserName() + " 在地址：" + 
							uInfo.getLastLoginAddress() + " 因为 \"" + err + "\" 登录失败。";
							LogRecord.writeSystemLog(message);
							userStatusErr(err);
							return false;
						}
					}
					printWelcomeInfo();
					try {
						setUserStatusLogin(uInfo, conn.createStatement());
						int s = uInfo.getStatus();
						uInfo.setStatus(++s);
//						us.setUserStatus(uInfo, conn.createStatement());
						checkUserStatus = new Timer();
						checkUserStatus.schedule(new UserS(uInfo), 0, 5000);
					} catch (SQLException e) {
						e.printStackTrace();
					}
//					us.start();
					return true;
				} else {
					System.out.printf("  密码错误！\n");
					uInfo = null;
					count++;
				}
			} else {
				System.out.printf("  该用户不存在！\n");
				count++;
			}
		}
		System.out.printf("\n  尝试登录超过3次，系统终止运行！\n  如有疑问请联系管理员。\n");
		try {
			statment.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}
	
	private void setUserStatusLogin(UserEntry uInfo, Statement statement) {
		try {
			String sql = "select * from risetek_users where id=" + uInfo.getId();
			ResultSet result = statement.executeQuery(sql);
			int us = 0;
			while(result.next()){
				String stat = result.getString(6);
				us = Integer.parseInt(stat);
			}
			sql = "update risetek_users set state='" + (++us) + "' where id=" + uInfo.getId();
			statement.execute(sql);
			sql = "update risetek_users set lastlogintime=SYSDATE where id=" + uInfo.getId();
//			sql = "update userinfo set lastlogintime=NOW() where id=" + uInfo.getId();
			statement.execute(sql);
			sql = "update risetek_users set lastloginaddress='" + localAddress + "'";
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void logout(){
		BlackScreenClient.reback = true;
		System.out.printf("\n  正在注销登录，请稍后");
		WaitThread wt = new WaitThread();
		wt.start();
		try {
			setUserStatusLogout(uInfo, conn.createStatement());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = "用户：" + uInfo.getUserName() + " 从地址：" + 
		uInfo.getLastLoginAddress() + " 注销登录！";
		LogRecord.writeSystemLog(message);
//		us.stopThread();
		wt.stopThread();
		checkUserStatus.cancel();
		BlackScreenClient.reo = true;
		System.out.print("注销成功！");
		System.out.printf("\n\n");
		System.out.printf("  谢谢使用！\n\n  Good Bye!\n");
	}
	
	private void setUserStatusLogout(UserEntry uInfo, Statement statement) {
		String sql = "update risetek_users set state='0' where id=" + uInfo.getId();
		try {
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public UserStatus getUserStatusThread(){
//		return us;
//	}
	
	private void printWelcomeInfo(){
		System.out.printf("\n");
		System.out.printf("  用户：" + uInfo.getUserName() + " 登录成功！\n");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.printf("  上次登录时间为：" + dateFormat.format(uInfo.getLastLoginTime()) + "\n");
		System.out.printf("  当前登录用户权限为：" + getUserLevel(uInfo.getLevel()) + "\n");
		if(null!=uInfo.getNotes()){
			System.out.printf("  用户备注：" + uInfo.getNotes() + "\n");
		}
		System.out.printf("\n\n");
	}
	
	public UserEntry getUserInfo(){
		return uInfo;
	}
	
	private UserEntry getUserInfo(String userName){
		UserEntry uInfo = null;
		String sql = "select * from risetek_users where username='" + userName + "'";
		try {
			ResultSet result = statment.executeQuery(sql);
			while(result.next()){
				uInfo = new UserEntry();
				uInfo.setId(result.getInt(1));
				uInfo.setUserName(result.getString(2));
				uInfo.setPassWord(result.getString(3));
				uInfo.setLevel(result.getInt(9));
				uInfo.setRegistTime(result.getDate(10));
				uInfo.setLastLoginTime(result.getTimestamp(11));
				uInfo.setLastLoginAddress(result.getString(12));
				uInfo.setNotes(result.getString(13));
				String stat = result.getString(6);
				uInfo.setStatus(Integer.parseInt(stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uInfo;
	}
	
	private String getUserLevel(int level){
		String levelName = "";
		for(int i=0;i<BlackScreenClient.userLevelList.size();i++){
			UserLevelEntry ule = BlackScreenClient.userLevelList.get(i);
			if(ule.getLevel() == level){
				levelName = ule.getLevelName();
				break;
			}
		}
		return levelName;
	}
	
	private void userStatusErr(String err){
		System.out.println("\n  " + err + "，请联系管理员！\n\n  谢谢使用！\n\n  Good Bye");
	}
	
	class UserS extends TimerTask {
		private UserEntry uInfo;
		private boolean status = true;
		
		public UserS(UserEntry uInfo){
			this.uInfo = uInfo;
		}
		
		public void run() {
			try {
				Class.forName(ConnectDataBase.className);
				DriverManager.setLoginTimeout(30);
				Connection conn = DriverManager.getConnection(ConnectDataBase.url, ConnectDataBase.username, ConnectDataBase.password);
				String sql = "select * from risetek_users where id=" + uInfo.getId();
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				while(result.next()){
					String stat = result.getString(6);
					int us = Integer.parseInt(stat);
					if(us > uInfo.getStatus()){
						BlackScreenClient.reback = true;
						BlackScreenClient.reo = false;
						status = false;
						System.out.println("该账户已在别处登录，您被迫下线。");
						break;
					}
				}
				if(!status){
					checkUserStatus.cancel();//this.cancel();
				}
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
//	public class UserStatus extends Thread {
//		
//		private UserEntry uInfo;
//		private Statement statement;
//		boolean status = true;
//		
//		public void setUserStatus(UserEntry uInfo, Statement statement){
//			this.uInfo = uInfo;
//			this.statement = statement;
//		}
//		
//		public void run(){
//			int index = 0;
//			while(status){
//				
//			}
//			if(!status){
//				BlackScreenClient.reback = true;
//				BlackScreenClient.reo = false;
//				Thread.yield();
//			}
//		}
//		
//		public void stopThread(){
//			status = false;
//		}
//	}
}
