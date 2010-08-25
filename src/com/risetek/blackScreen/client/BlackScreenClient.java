package com.risetek.blackScreen.client;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.risetek.blackScreen.client.database.ConnectDataBase;
import com.risetek.blackScreen.client.entry.CommandEntry;
import com.risetek.blackScreen.client.entry.UserEntry;
import com.risetek.blackScreen.client.entry.UserLevelEntry;
import com.risetek.blackScreen.client.run.LoginManager;
import com.risetek.blackScreen.client.run.RunCommand;
import com.risetek.blackScreen.client.utils.InitFunction;
import com.risetek.blackScreen.client.utils.LogRecord;
import com.risetek.blackScreen.client.utils.Util;
import com.risetek.blackScreen.client.utils.WaitThread;

public class BlackScreenClient {

	public static boolean reback = false;
	public static boolean reo = true;
	public Console con;
	public static Connection conn;
	public static UserEntry uInfo;
	public static ArrayList<UserLevelEntry> userLevelList;
	public static ArrayList<CommandEntry> cmdList;
	public static ArrayList<String> childcmd;
	public static String sendUrl;
	public static LogRecord logRecord;
	public static String ip;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		try{
			BlackScreenClient myConsole = new BlackScreenClient();
			boolean init0 = initSystemConfig();
			boolean init1 = myConsole.connectService();
			if(args.length>1){
				ip = args[0];
				if(Util.DEBUG_LEVEL>2){
					System.out.println("login ip address is :" + ip);
				}
				if(Util.DEBUG_LEVEL>2){
					System.out.println("login tty is :" + args[1]);
				}
			}
			if(init0&&init1){
				myConsole.run();
			} else {
				System.out.printf("\n  初始化过程中遇到问题，退出平台。\n  请联系管理员解决该问题。");
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("\n\n非正常退出！！！");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public BlackScreenClient(){
		con = System.console();
	}
	
	private boolean connectService(){
		ConnectDataBase cdb = new ConnectDataBase(con);
		System.out.printf("\n  连接远程服务器");
		WaitThread wt = new WaitThread();
		wt.start();
		try{
			conn = cdb.getConnection();
		} catch (SQLException e){
			System.out.printf("连接失败！\n");
			wt.stopThread();
			return false;
		}
		File configFile = new File(Util.CONFIG_FILE_PATH);
		InitFunction initFun = new InitFunction(conn, configFile);
		userLevelList = initFun.initUserLevelList();
		wt.stopThread();
		if(conn!=null){
			System.out.printf("连接成功！\n");
			logRecord = new LogRecord();
			return true;
		} else {
			System.out.printf("连接失败！\n");
			return false;
		}
	}
	
	public static boolean initSystemConfig(){
		System.out.printf("\n  初始化系统");
		WaitThread wt = new WaitThread();
		wt.start();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File configFile = new File(Util.CONFIG_FILE_PATH);
		InitFunction initFun = new InitFunction(conn, configFile);
		if(configFile.exists()){
			cmdList = initFun.initCommand();
			childcmd = initFun.getChildCmd();
			sendUrl = initFun.getSendUrl();
			if(!initFun.initDatebaseUrl()){
				return false;
			}
		} else {
			System.out.printf("初始化失败！\n");
			System.out.printf("\n  未找到系统配置文件！\n");
			return false;
		}
		wt.stopThread();
		System.out.printf("初始化完成！");
		System.out.printf("\n\n");
		return true;
	}
	
	public void run(){
		printStartInfo();
		LoginManager lm = new LoginManager(conn, con);
		reback = lm.login();
		String logMessage = "";
		if(!reback){
			reback = true;
		} else {
			uInfo = lm.getUserInfo();
			logRecord.setUserEntry(uInfo);
			logMessage = "用户：" + uInfo.getUserName() + 
			" 从地址：" + uInfo.getLastLoginAddress() + " 登录成功！";
			LogRecord.writeSystemLog(logMessage);
			reback = false;
		}
		RunCommand rc = new RunCommand(lm, conn, childcmd);
		if(!reback){
			System.out.print("  提示：输入 ? 或help来获取命令信息\n  输入命令区分大小写。\n\n");
		}
		while(!reback){
			String in = "";
			System.out.printf(">>> ");
			in = tinput();
			if(!reo){
				System.out.println("  命令未执行！");
				break;
			}
			if(in.length()>Util.COMMAND_INPUT_MAX_LENGHT-188){
				System.out.printf("  输入命令超长！\n");
			} else if(in.length() == 0){
				System.out.printf("  没有输入任何命令！\n");
			} else if(rc.checkCommand(in)){
				rc.resolveCommand(in);
			} else {
				if(rc.getFlag().equals("quit")){
					break;
				} else {
					System.out.printf("  输入的命令无效！\n");
				}
			}
		}
		if(!reo){
			System.out.printf("\n  您的账户状态异常！\n  您被迫注销登录！\n");
		}
	}
	
	
	
	public String getInput(){
		String line = con.readLine();
		return line;
	}
	
	public static String tinput(){
		byte[] b = new byte[Util.COMMAND_INPUT_MAX_LENGHT];
		try {
			InputStream input = System.in;
			while(input.available()>0){
				byte[] c = new byte[100];
				input.read(c);
			}
			input.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = -1;
		for(int i=0;i<b.length;i++){
			String s = Byte.toString(b[i]);
			int code = Integer.parseInt(s);
			if(code == 10){
				count = i;
				break;
			}
		}
		if(Util.DEBUG_LEVEL>0){
			System.out.println("the count is :" + count);
		}
		if(count == -1){
			String in = new String(b);
			return in;
		} else if(count == 1){
			if(Util.isLinux){
				byte[] t = new byte[count];
				for(int i=0;i<t.length;i++){
					t[i] = b[i];
				}
				String in = new String(t);
				if(Util.DEBUG_LEVEL>0){
					System.out.println("the input is: " + in);
				}
				return in;
			} else {
				return "";
			}
		} else {
			byte[] t;
			if(Util.isLinux){
				t = new byte[count];
			} else {
				t = new byte[count-1];
			}
			for(int i=0;i<t.length;i++){
				t[i] = b[i];
			}
			String in = new String(t);
			if(Util.DEBUG_LEVEL>0){
				System.out.println("the input is: " + in);
			}
			return in;
		}
	}
	
	public String getInputPassword(){
		char[] chars = con.readPassword();
		String pws = new String(chars);
		return pws;
	}
	
	private void printStartInfo(){
		printBorder();
		printEmptyLine();
		printLine1();
		printEmptyLine();
		printLine2();
		printEmptyLine();
		printBorder();
		System.out.printf("\n\n");
	}
	
	private void printLine1(){
		System.out.printf("*");
		for(int i=0;i<3;i++){
			System.out.printf("\t");
		}
		System.out.printf("欢迎使用手机支付运维管理平台");
		for(int i=0;i<3;i++){
			System.out.printf("\t");
		}
		System.out.printf("*");
		System.out.printf("\n");
	}
	
	private void printLine2(){
		System.out.printf("*");
		for(int i=0;i<3;i++){
			System.out.printf("\t");
		}
		System.out.print("中联信通科技有限公司\tv1.0");
		for(int i=0;i<3;i++){
			System.out.printf("\t");
		}
		System.out.printf("*");
		System.out.printf("\n");
	}
	
	private void printBorder(){
		for(int i=0;i<73;i++){
			System.out.printf("*");
		}
		System.out.printf("\n");
	}
	
	private void printEmptyLine(){
		System.out.printf("*");
		for(int i=0;i<9;i++){
			System.out.printf("\t");
		}
		System.out.printf("*\n");
	}
}