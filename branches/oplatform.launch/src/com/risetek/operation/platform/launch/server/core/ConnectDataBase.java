package com.risetek.operation.platform.launch.server.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDataBase {

	public static ConnectDataBase CONNDB = new ConnectDataBase();
	private Connection conn;
	public static String className;
	public static String url;
	public static String username;
	public static String password;
	
	public ConnectDataBase(){
		className = "oracle.jdbc.OracleDriver";
		url = "jdbc:oracle:thin:@192.168.6.2:1521:orcl";
		username = "bwc";
		password = "risetekpassok";
	}
	
	public Connection getConnection() {
		try{
			Class.forName(className);
			DriverManager.setLoginTimeout(60);
			conn = DriverManager.getConnection(url, username, password);
		} catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
