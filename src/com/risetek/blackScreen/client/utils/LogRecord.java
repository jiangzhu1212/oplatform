package com.risetek.blackScreen.client.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.risetek.blackScreen.client.database.ConnectDataBase;
import com.risetek.blackScreen.client.entry.LogEntry;
import com.risetek.blackScreen.client.entry.UserEntry;

public class LogRecord {

	private static UserEntry user;
	
	public LogRecord(){
	}
	
	@SuppressWarnings("static-access")
	public void setUserEntry(UserEntry user){
		this.user = user;
	}
	
	public static void writeUserLog(String value){
		if(user==null){
			return;
		}
		writeUserLog(value, 0);
	}
	
	public static void writeUserLog(String value, int logLevel){
		if(user==null){
			return;
		}
		LogEntry log = new LogEntry();
		log.setLogContent(value);
		log.setLogKey(user.getUserName());
		log.setLogLevel(logLevel);
		writeToDataBase(log);
	}
	
	public static void writeSystemLog(String value){
		writeSystemLog(value, 0);
	}
	
	public static void writeSystemLog(String value, int logLevel){
		LogEntry log = new LogEntry();
		log.setLogContent(value);
		log.setLogKey(Util.LOG_SYSTEM_KEY);
		log.setLogLevel(logLevel);
		writeToDataBase(log);
	}
	
	private static void writeToDataBase(LogEntry log){
		try{
			Class.forName(ConnectDataBase.className);
			DriverManager.setLoginTimeout(30);
			Connection conn = DriverManager.getConnection(ConnectDataBase.url, ConnectDataBase.username, ConnectDataBase.password);
			Statement statement = conn.createStatement();
			String sql = "select * from generator_table where g_key='system_log'";
			ResultSet result = statement.executeQuery(sql);
			int id = 0;
			if(result.next()){
				id = result.getInt(2);
			}
			sql = "insert into risetek_logs (id, logtime, loguser, logmessage, loglevel)" +
					" values(" + id + ", SYSDATE, '" + log.getLogKey() + "', '黑屏 - " + 
					log.getLogContent() + "', " + log.getLogLevel() + ")";
			if(Util.DEBUG_LEVEL>2){
				System.out.println("the write log message is : " + sql);
			}
			statement.execute(sql);
			sql = "update generator_table set g_value=" + ++id;
			statement.execute(sql);
			if(Util.DEBUG_LEVEL>2){
				System.out.println("write successful!");
			}
			statement.close();
		} catch (SQLException e){
			if(Util.DEBUG_LEVEL>2){
				System.out.println("write field!");
				System.out.println(e.toString());
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
