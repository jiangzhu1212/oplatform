package com.risetek.operation.platform.log.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.launch.client.entry.Log;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;
import com.risetek.operation.platform.log.client.service.LogService;

public class LogServiceImpl extends RemoteServiceServlet implements LogService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getLogDataCount() {
		int count = 0;
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select count(*) from RISETEK_LOG";
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
	public Log[] getAllLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Log[] getLogPage(int rowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Log[] getLastLogPage(int rowCount) {
		Log[] logs = null;
		List<Log> list = new ArrayList<Log>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_LOG where rownum<=" + rowCount + " order by ID desc";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Log log = new Log();
				log.setId(result.getInt(1));
				log.setKey(result.getString(2));
				log.setContent(result.getString(3));
				log.setLogTime(result.getTimestamp(4));
				list.add(log);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			logs = new Log[list.size()];
			for(int i=0;i<logs.length;i++){
				logs[i] = list.get(i);
			}
		} else {
			logs = new Log[0];
		}
		return logs;
	}

	@Override
	public void addLog(Log log) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select G_VALUE from GENERATOR_TABLE where G_KEY='RISETEK_LOG'";
			ResultSet result = statement.executeQuery(SQL);
			int id = 0;
			if(result.next()){
				id = result.getInt(1);
			}
			SQL = "insert into RISETEK_LOG (ID, KEY, CONTENT, LOGTIME) VALUES (" + 
					id + ", '" + log.getKey() + "', '" +
					log.getContent() + "', sysdate)";
			statement.execute(SQL);
			id++;
			SQL = "update GENERATOR_TABLE set G_VALUE = " + id + " where G_KEY='RISETEK_LOG'";
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearAllLog() {
		// TODO Auto-generated method stub

	}

	@Override
	public Log[] getLogByKey(String key) {
		Log[] logs = null;
		List<Log> list = new ArrayList<Log>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_LOG where CONTENT = '" + key + "' order by ID desc";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Log log = new Log();
				log.setId(result.getInt(1));
				log.setKey(result.getString(2));
				log.setContent(result.getString(3));
				log.setLogTime(result.getTimestamp(4));
				list.add(log);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			logs = new Log[list.size()];
			for(int i=0;i<logs.length;i++){
				logs[i] = list.get(i);
			}
		} else {
			logs = new Log[0];
		}
		return logs;
	}

	@Override
	public Log[] getLogByContent(String cont) {
		Log[] logs = null;
		List<Log> list = new ArrayList<Log>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_LOG where CONTENT like '%" + cont + "%' order by ID desc";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Log log = new Log();
				log.setId(result.getInt(1));
				log.setKey(result.getString(2));
				log.setContent(result.getString(3));
				log.setLogTime(result.getTimestamp(4));
				list.add(log);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			logs = new Log[list.size()];
			for(int i=0;i<logs.length;i++){
				logs[i] = list.get(i);
			}
		} else {
			logs = new Log[0];
		}
		return logs;
	}

	@Override
	public Log[] getLogByTime(Timestamp start, Timestamp end) {
		// TODO Auto-generated method stub
		return null;
	}

}
