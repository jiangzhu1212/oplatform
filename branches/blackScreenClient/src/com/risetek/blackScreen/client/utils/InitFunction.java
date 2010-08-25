package com.risetek.blackScreen.client.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.risetek.blackScreen.client.database.ConnectDataBase;
import com.risetek.blackScreen.client.entry.CommandEntry;
import com.risetek.blackScreen.client.entry.UserLevelEntry;

public class InitFunction {

	private Connection conn;
	private ArrayList<String> childcmd = new ArrayList<String>();
	private File configFile;
	
	public InitFunction(Connection conn, File configFile){
		this.conn = conn;
		this.configFile = configFile;
	}
	
	public CommandEntry addCommand(String key, String value, String notes){
		CommandEntry cmdi = new CommandEntry();
		cmdi.setKey(key);
		cmdi.setValue(value);
		cmdi.setNotes(notes);
		cmdi.setType("process");
		cmdi.setLevel(1);
		cmdi.setParent(-1);
		return cmdi;
	}
	
	public CommandEntry addCommand(String key, String value, String notes, int level){
		CommandEntry cmdi = new CommandEntry();
		cmdi.setKey(key);
		cmdi.setValue(value);
		cmdi.setNotes(notes);
		cmdi.setType("process");
		cmdi.setLevel(level);
		cmdi.setParent(-1);
		return cmdi;
	}
	
	public ArrayList<UserLevelEntry> initUserLevelList(){
		ArrayList<UserLevelEntry> ul = new ArrayList<UserLevelEntry>();
		try{
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("select * from userlevel");
			while(result.next()){
				UserLevelEntry ule = new UserLevelEntry();
				ule.setId(result.getInt(1));
				ule.setLevel(result.getInt(2));
				ule.setLevelName(result.getString(3));
				ul.add(ule);
			}
			statement.close();
		} catch (SQLException e){
			
		}
		return ul;
	}

	public ArrayList<CommandEntry> initCommand() {
		if(Util.DEBUG_LEVEL>0){
			System.out.printf("\n  以下内容为配置文件内容。\n\n");
		}
		ArrayList<CommandEntry> cmdList = new ArrayList<CommandEntry>();
		cmdList.add(addCommand("HELP", "?", "获取帮助"));
		cmdList.add(addCommand("HELP", "help", "获取帮助"));
		cmdList.add(addCommand("QUIT", "quit", "注销登录，退出系统"));
		cmdList.add(addCommand("REPASSWORD", "repws", "更改当前用户的登录密码"));
		cmdList.add(addCommand("SHOWLOG", "showlog", "查看日志"));
		cmdList.add(addCommand("INITSYS", "initsys", "重新初始化系统参数", 2));
		try {
			FileInputStream in = new FileInputStream(configFile);
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String parent = "";
			String temp = "";
			while((line = br.readLine())!=null){
				line = line.trim();
				if(line.length()>0){
					int index = line.indexOf("#");
					if(index==-1){
						if(parent.length()==0){
							int e = line.indexOf("=");
							parent = line.substring(0, e);
						}
						if(parent.startsWith("$cmdname")){
							int e = line.indexOf("=");
							line = line.substring(e+1, line.length());
							int c = line.indexOf("*");
							String sl = line.substring(c+1, line.length());
							int level = Integer.parseInt(sl);
							line = line.substring(0, c);
							CommandEntry ce = new CommandEntry();
							String[] t = line.split("&");
							if(t.length>1){
								ce.setValue(t[0]);
								ce.setNotes(t[1]);
							} else {
								ce.setValue(t[0]);
								ce.setNotes(t[0]);
							}
							ce.setLevel(level);
							ce.setType("select");
							cmdList.add(ce);
							parent = "";
						} else if(parent.startsWith("$cmdlist")){
							if(line.endsWith(";")){
								temp += line;
								temp = temp.substring(0, temp.length()-1);
								childcmd.add(temp);
								parent = "";
								temp = "";
							} else {
								temp += line;
							}
						} else if(parent.startsWith("$debug")){
							int s = line.indexOf("=");
							line = line.substring(s+1, line.length());
							Util.DEBUG_LEVEL = Integer.parseInt(line);
							parent = "";
						} else if(parent.startsWith("$linux")){
							int s = line.indexOf("=");
							line = line.substring(s+1, line.length());
							int flag = Integer.parseInt(line);
							if(flag==1){
								Util.isLinux = true;
							} else {
								Util.isLinux = false;
							}
							parent = "";
						} else {
							parent = "";
						}
						if(Util.DEBUG_LEVEL>0){
							System.out.printf(line);
							System.out.printf("\n");
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
		}
		return cmdList;
	}
	
	public ArrayList<String> getChildCmd(){
		return childcmd;
	}

	public String getSendUrl() {
		String url = "";
		try{
			FileInputStream in = new FileInputStream(configFile);
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line = br.readLine())!=null){
				line = line.trim();
				if(line.length()>0){
					int index = line.indexOf("#");
					if(index==-1){
						if(line.startsWith("$sendurl")){
							String[] temp = line.split("=");
							url = temp[1];
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return url;
	}
	
	public boolean initDatebaseUrl(){
		String url = "";
		try{
			FileInputStream in = new FileInputStream(configFile);
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line = br.readLine())!=null){
				line = line.trim();
				if(line.length()>0){
					int index = line.indexOf("#");
					if(index==-1){
						if(line.startsWith("$dateurl")){
							String[] temp = line.split("=");
							url = temp[1];
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		if(url.length()>0){
			String[] urls = url.split("&");
			if(urls.length!=4){
				return false;
			} else {
				ConnectDataBase.className = urls[0];
				ConnectDataBase.url = urls[1];
				ConnectDataBase.username = urls[2];
				ConnectDataBase.password = urls[3];
			}
		} else {
			return false;
		}
		return true;
	}
}
