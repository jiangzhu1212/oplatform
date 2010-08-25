package com.risetek.blackScreen.client.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.risetek.blackScreen.client.entry.CommandEntry;

public class CommandList {

	public static String HELPS = "?";
	public static String HELP = "help";
	public static String QUIT = "quit";
	
	private ArrayList<CommandEntry> commdList = new ArrayList<CommandEntry>();
	
	public CommandList(Connection conn){
		commdList.add(addCommand("HELP", "?", "获取帮助"));
		commdList.add(addCommand("HELP", "help", "获取帮助"));
		getDataBaseCommdList(conn, commdList);
		commdList.add(addCommand("QUIT", "quit", "注销登录，退出系统"));
//		commdList.add(addCommand("QUIT", "exit", "注销登录，退出系统"));
	}
	
	private void getDataBaseCommdList(Connection conn, ArrayList<CommandEntry> commdList) {
		try {
			Statement statement = conn.createStatement();
			String sql = "select * from commdlist";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				CommandEntry cmdi = new CommandEntry();
				cmdi.setId(result.getInt(1));
				cmdi.setKey(result.getString(2));
				cmdi.setValue(result.getString(3));
				cmdi.setNotes(result.getString(4));
				cmdi.setType(result.getString(5));
				cmdi.setParent(result.getInt(6));
				commdList.add(cmdi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<CommandEntry> getCommandList(){
		return commdList;
	}
	
	public CommandEntry addCommand(String key, String value, String notes){
		CommandEntry cmdi = new CommandEntry();
		cmdi.setKey(key);
		cmdi.setValue(value);
		cmdi.setNotes(notes);
		cmdi.setType("process");
		cmdi.setParent(-1);
		return cmdi;
	}
}
