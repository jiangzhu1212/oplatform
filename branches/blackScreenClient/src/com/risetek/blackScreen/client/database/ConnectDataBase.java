package com.risetek.blackScreen.client.database;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.risetek.blackScreen.client.utils.Util;


public class ConnectDataBase {

	private Connection conn;
//	private Console con;
	public static String className;
	public static String url;
	public static String username;
	public static String password;
	
	public static void main(String[] args){
//		new ConnectDataBase();
	}
	
	public ConnectDataBase(Console con){
//		this.con = con;
//		url="jdbc:oracle:thin:@192.168.6.2:1521:orcl";
//		username = "bwc";
//		password = "risetekpassok";
		
	}
	
	public Connection getConnection() throws SQLException {
		try{
			if(Util.DEBUG_LEVEL>2){
				System.out.println("className is :" + className);
			}
			Class.forName(className);
			DriverManager.setLoginTimeout(30);
			conn = DriverManager.getConnection(url, username, password);
//			Statement statment = conn.createStatement();
//			ResultSet result = statment.executeQuery("select * from userinfo where username=\"admin\"");
//			ResultSetMetaData rsmd = result.getMetaData();
//			int count = rsmd.getColumnCount();
//			for(int i=1;i<=count;i++){
//				System.out.print(rsmd.getColumnName(i) + "\t");
//			}
//			System.out.print("\n");
//			while(result.next()){
//				for(int i=1;i<=count;i++){
//					String s = result.getString(i);
//					System.out.print(s + "\t");
//				}
//				System.out.print("\n");
//			}
//			result.toString();
		} catch(ClassNotFoundException e){
			System.out.printf("数据库错误！\n");
//			System.out.println("class not found");
//		} catch(SQLException e){
//			String msg = e.getCause().getMessage();
//			System.out.printf("数据库错误！\n错误信息：\n" + msg + "\n");
//			System.out.println(msg);
//			System.out.println("sql conn err!");
		}
		return conn;
	}
}
