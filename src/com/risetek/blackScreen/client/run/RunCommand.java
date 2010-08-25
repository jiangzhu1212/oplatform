package com.risetek.blackScreen.client.run;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.risetek.blackScreen.client.BlackScreenClient;
import com.risetek.blackScreen.client.entry.CommandEntry;
import com.risetek.blackScreen.client.entry.UserEntry;
import com.risetek.blackScreen.client.net.HttpConnect;
import com.risetek.blackScreen.client.utils.InitFunction;
import com.risetek.blackScreen.client.utils.LogRecord;
import com.risetek.blackScreen.client.utils.Util;
import com.risetek.blackScreen.client.utils.WaitThread;

public class RunCommand {

	LoginManager lm;
	private String flag = "";
	private ArrayList<CommandEntry> commdList;
	private Connection conn;
	private ArrayList<String> childcmd;
	String [] erMsg = new String[]{"执行正确","未知错误","没有权限","数据库操作错误","","","","","","","输入数据缺少字段",
			"商户不存在","客户不存在","其他数据不存在","输入数据非法","操作超时","数据重复"};
	
	public RunCommand(LoginManager lm, Connection conn, ArrayList<String> childcmd) {
		this.lm = lm;
		commdList = BlackScreenClient.cmdList;
		this.conn = conn;
		this.childcmd = childcmd;
	}

	public boolean checkCommand(String commd){
		if(BlackScreenClient.reback){
			setFlag("quit");
			return false;
		}
		for(int i=0;i<commdList.size();i++){
			CommandEntry cmdi = commdList.get(i);
			if(commd.equals(cmdi.getValue())){
				return true;
			}
		}
		return false;
	}
	
	public void resolveCommand(String commd){
		CommandEntry cmdi = null;
		for(int i=0;i<commdList.size();i++){
			CommandEntry ci = commdList.get(i);
			if(ci.getValue().equals(commd)){
				cmdi = ci;
				break;
			}
		}
		if(Util.COMMAND_TYPE_PROCESS.equals(cmdi.getType())){
			runCommand(cmdi);
		} else if(Util.COMMAND_TYPE_SELECT.equals(cmdi.getType())) {
			selectCommand(cmdi);
		} else if(Util.COMMAND_TYPE_SUM.equals(cmdi.getType())) {
//			sumCommand(cmdi);
		} else if(Util.COMMAND_TYPE_UPDATE.equals(cmdi.getType())){
//			updateCommand(cmdi);
		}
	}
	
	private void runCommand(CommandEntry cmdi){
		String commd = cmdi.getValue();
		if(commd.equals("?")||commd.equals("help")){
			for(int i=0;i<commdList.size();i++){
				CommandEntry ci = commdList.get(i);
				String value = ci.getValue();
				int level = lm.getUserInfo().getLevel();
				if(ci.getLevel()>level){
					continue;
				}
				if(value.length()<6){
					System.out.printf("  " + ci.getValue() + "\t\t" + ci.getNotes() + "\n");
				} else {
					System.out.printf("  " + ci.getValue() + "\t" + ci.getNotes() + "\n");
				}
			}
		}
		if(commd.equals("repws")){
			rePws();
		}
		if(commd.equals("showlog")){
			showLogInfo();
		}
		if(commd.equals("quit")){
			lm.logout();
			BlackScreenClient.reback = true;
		}
		if(commd.equals("initsys")){
			initSystem();
		}
	}
	
	private void rePws(){
		String message = "执行了修改密码操作。";
		LogRecord.writeUserLog(message);
		int index = 0;
		String oldpws = "";
		String newpws = "";
		String newpws1 = "";
		while(index<3){
			if(oldpws.length()==0){
				System.out.printf("  请输入当前密码: ");
				oldpws = new String(System.console().readPassword());//BlackScreenClient.tinput();
			}
			UserEntry ue = BlackScreenClient.uInfo;
			if(oldpws.equals(ue.getPassWord())){
				if(newpws.length()==0){
					System.out.printf("  请输入新密码:");
					newpws = new String(System.console().readPassword());//BlackScreenClient.tinput();
				}
				if(newpws.length()<4){
					System.out.printf("  密码长度不得小于4个字符！\n");
					newpws = "";
					if(newpws.length()==0){
						System.out.printf("  请输入新密码:");
						newpws = new String(System.console().readPassword());//BlackScreenClient.tinput();
					}
					index++;
				} else {
					if(newpws1.length()==0){
						System.out.printf("  请再次输入新密码:");
						newpws1 = new String(System.console().readPassword());//BlackScreenClient.tinput();
					}
					if(newpws1.length()<4){
						System.out.printf("  密码长度不得小于4个字符！\n");
						newpws1 = "";
						if(newpws1.length()==0){
							System.out.printf("  请再次输入新密码:");
							newpws1 = new String(System.console().readPassword());//BlackScreenClient.tinput();
						}
						index++;
					} else {
						if(newpws.equals(newpws1)){
							if(!changeLoginPws(ue, newpws)){
								BlackScreenClient.uInfo.setPassWord(newpws);
								System.out.printf("  登录密码修改成功！\n");
							} else {
								System.out.printf("\n  系统故障导致登录密码修改失败！\n  请联系管理员解决此问题。\n");
							}
							break;
						} else {
							System.out.printf("  两次输入的新密码不相符！\n");
							newpws = "";
							newpws1 = "";
							index++;
						}
					}
				}
			} else {
				System.out.printf("  原密码错误！\n");
				oldpws = "";
				index++;
			}
		}
		if(index>=3){
			System.out.printf("\n  错误次数太多！\n  若修改登录密码，请重新运行该命令！\n");
			message = "尝试修改密码，因为输入次数过多，修改失败。";
			LogRecord.writeUserLog(message);
		}
	}
	
	private void initSystem(){
		System.out.printf("\n  重新初始化系统");
		WaitThread wt = new WaitThread();
		wt.start();
		File configFile = new File(Util.CONFIG_FILE_PATH);
		InitFunction initFun = new InitFunction(conn, configFile);
		BlackScreenClient.userLevelList = initFun.initUserLevelList();
		wt.stopThread();
		if(configFile.exists()){
			BlackScreenClient.cmdList = initFun.initCommand();
			BlackScreenClient.childcmd = initFun.getChildCmd();
			BlackScreenClient.sendUrl = initFun.getSendUrl();
			this.childcmd = BlackScreenClient.childcmd;
		} else {
			System.out.printf("初始化失败！\n");
			System.out.printf("\n  未找到系统配置文件！\n");
		}
		System.out.printf("初始化完成！");
		System.out.printf("\n\n");
	}
	
	private boolean changeLoginPws(UserEntry ue, String newpws) {
		boolean result = true;
		try{
			Statement statement = conn.createStatement();
			String sql = "update risetek_users set userpwd='" + newpws + "' where id=" + ue.getId();
			result = statement.execute(sql);
		} catch (SQLException e){
			
		}
		return result;
	}

	private void selectCommand(CommandEntry cmdi){
		int count = 0;
		for(int i=0;i<childcmd.size();i++){
			String line = childcmd.get(i);
			int s = line.indexOf("{") + 1;
			int e = line.indexOf("}");
			String parent = line.substring(s, e);
			if(parent.equals(cmdi.getValue())){
				s = line.indexOf("=") + 1;
				e = line.indexOf("*");
				String le = line.substring(s, e);
				int level = Integer.parseInt(le);
				if(level<=lm.getUserInfo().getLevel()){
					count++;
				}
			}
		}
		if(count>0){
			String[] cmd = new String[count];
			count = 0;
			for(int i=0;i<childcmd.size();i++){
				String line = childcmd.get(i);
				int s = line.indexOf("{") + 1;
				int e = line.indexOf("}");
				String type = line.substring(s, e);
				int s0 = line.indexOf("=") + 1;
				int e0 = line.indexOf("*");
				String le = line.substring(s0, e0);
				int level = Integer.parseInt(le);
				if(type.equals(cmdi.getValue())&&(level<=lm.getUserInfo().getLevel())){
					cmd[count] = line.substring(e + 1, line.length());
					count++;
				}
			}
			selectChildCommand(cmdi, cmd);
		} else {
			System.out.printf("\n  当前无可执行功能。\n");
		}
	}
	
	boolean goonRun = true;
	private void selectChildCommand(CommandEntry cmdi, String[] cmd) {
		String message = "";
		System.out.printf("\n  0.取消执行");
		for(int i=0;i<cmd.length;i++){
			String temp = cmd[i];
			int e = temp.indexOf("@");
			String name = temp.substring(0, e);
			System.out.printf("\n  "+ (i+1) + "." + name);
		}
		String in = "";
		boolean sign = true;
		while(sign){
			if(in.length()==0){
				System.out.printf("\n请选择: ");
				in = BlackScreenClient.tinput();
				if(Util.DEBUG_LEVEL>1){
					byte[] b = in.getBytes();
					System.out.println("the input leng is :" + in.length());
					for(int i=0;i<b.length;i++){
						System.out.print(b[i]);
					}
					System.out.println("");
				}
			}
			try{
				int index = Integer.parseInt(in);
				if(index<0||index>cmd.length){
					System.out.printf("只能输入0-" + cmd.length + "之间的数字！\n");
					in = "";
				} else {
					if(index==0){
						message = "执行了命令\"" + cmdi.getNotes() + "\" 下的  \"取消执行\" 操作";
						System.out.printf("当前执行的\"" + cmdi.getNotes() + "\"操作已结束！\n");
						in = "";
						LogRecord.writeUserLog(message);
						break;
					} else {
						String line = cmd[index-1];
						int s = line.indexOf("@") + 1;
						String cmdName = line.substring(0, s-1);
						message = "执行了命令\"" + cmdi.getNotes() + "\" 下的 \"" + cmdName + "\" 操作";
						LogRecord.writeUserLog(message);
						line = line.substring(s, line.length());
						String[] section = line.split("!");
						boolean front = true;
						if(section.length>1){
							for(int i=0;i<section.length;i++){
								String sec = section[i];
								if(sec.startsWith("^")){
									if(front){
										front = runChildCommand(section[i], true);
									} else {
										System.out.println("  前置条件执行失败，本次操作终止！");
									}
								} else {
									front = runChildCommand(section[i], true);
								}
							}
							oldValues = null;
						} else {
							runChildCommand(section[0], false);
						}
						sign = false;
					}
				}
			} catch (NumberFormatException e){
				System.out.printf("只能输入0-" + cmd.length + "之间的数字！\n");
				in = "";
			} catch (JSONException e){
				e.printStackTrace();
			}
		}
	}
	
	private boolean runChildCommand(String section, boolean isLink) throws JSONException {
		String[] lines = section.split("\\?");
		if(isLink){
			if(lines.length>1){
				if("save".equals(lines[1])){
					return runLinkChildCommand(section, true);
				} else {
					return runLinkChildCommand(section, false);
				}
			} else {
				runLinkChildCommand(section, false);
			}
		} else {
			runOnceChildCommand(section, false);
		}
		return true;
	}
	
	private boolean runLinkChildCommand(String line, boolean isSave) throws JSONException {
		if(null == oldValues){
			return runOnceChildCommand(line, isSave);
		}
		String[] lines = line.split("\\?");
		String actionName = getActionName(lines[0]);
		JSONObject root = new JSONObject();
		root.put("ACTION_NAME", actionName);
		JSONObject info = null;
		String oldValue = "";
		JSONObject ov = null;
		if(isSave){
			ov = oldValues;
		}
		if(null!=oldValues){
			info = getInputParameter(oldValues, lines[0], false);
		} else {
			info = getInputParameter(lines[0]);
		}
		if(ov!=null){
			String[] temp = getOldValueContent(ov);
			for(int i=0;i<temp.length;i++){
				oldValue += temp[i] + ",";
			}
			oldValue = oldValue.substring(0, oldValue.length()-1);
			String message = "\n 用户原数据: <" + oldValue + "> \n 更改为新数据: <" + info.toString() + ">";
			LogRecord.writeUserLog(message);
		}
		if(null==info){
			String msg = "用户终止操作!";
			System.out.println("  " + msg);
			LogRecord.writeUserLog(msg);
			oldValues = null;
			return false;
		}
		root.put("ACTION_INFO", info);
		if(null!=oldValues){
			String[] temp = getOldValueContent(oldValues);
			for(int i=0;i<temp.length;i++){
				oldValue += temp[i] + ",";
			}
			oldValue = oldValue.substring(0, oldValue.length()-1);
			String message = "\n 用户原数据: <" + oldValue + "> \n 更改为新数据: <" + info.toString() + ">";
			LogRecord.writeUserLog(message);
		}
		String reback = "";
		try{
			reback = sendPack(root.toString());
			if(lines.length>1){
				resolveOnceReback(reback, lines[1]);
			} else {
				resolveOnceReback(reback);
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	private void resolveOnceReback(String reback, String line) throws JSONException {
		JSONObject root = new JSONObject(reback);
		if(root.isNull("ACTION_INFO")){
			return;
		}
		JSONObject info = root.getJSONObject("ACTION_INFO");
		JSONArray query = info.getJSONArray("QUERY_DATA");//getJSONObject("QUERY_DATA");
		if(query.length()<1){
			System.out.println("  未查询到相关数据！");
			return;
		} else {
			System.out.println("============================================================");
			boolean contu = false;
			for(int i=0;i<query.length();i++){
				String t = query.get(i).toString();
				JSONObject data = new JSONObject(t);
				JSONArray names = data.names();
				JSONArray values = data.toJSONArray(names);
				String[] desc = line.split(",");
				for(int a=0;a<names.length();a++){
					String name = names.getString(a);
					String value = values.getString(a);
					for(int b=0;b<desc.length;b++){
						String[] split = desc[b].split("@");
						if(split.length>1){
							String des = split[1];
							if(name.equals(des)){
								name = split[0];
								break;
							}
						} else {
							break;
						}
					}
					if(value.startsWith("{")){
						resolveOnceReback(value, line);
						continue;
					} else if(value.startsWith("[")){
						value = value.substring(1, value.length()-1);
						System.out.println("  " + name + "= <本项含多条记录,如下:>");
						String[] vs = value.split(",");
						for(int b=0;b<vs.length;b++){
							String v = vs[b];
							v = v.substring(1, v.length()-1);
							System.out.println("    记录" + (b+1) + ". " + v);
						}
						continue;
					}
					String show = name + "= " + value;
					System.out.println("  " + show);
				}
				System.out.println("------------------------------------------------------------");
				if(!contu){
					String in = BlackScreenClient.tinput();
					if(in.length()>0){
						if(Util.isLinux){
							if(in.charAt(0)==Util.KEY_CTRL_X){
								contu = true;
								break;
							} else if(in.charAt(0)==Util.KEY_CTRL_A) {
								contu = true;
							}
						} else {
							if(in.charAt(0)==Util.KEY_CTRL_Z){
								contu = true;
								break;
							} else if(in.charAt(0)==Util.KEY_CTRL_A) {
								contu = true;
							}
						}
					}
				}
			}
			System.out.println("\t\t查询结束");
			System.out.println("============================================================");
		}
	}
	
	private void resolveOnceReback(String reback) throws JSONException {
		JSONObject root = new JSONObject(reback);
		JSONObject info = root.getJSONObject("ACTION_INFO");
		JSONArray names = info.names();
		JSONArray values = info.toJSONArray(names);
		for(int i=0;i<names.length();i++){
			String name = names.getString(i);
			String value = values.getString(i);
			if(value.startsWith("{")){
				resolveOnceReback(value);
				continue;
			} else if(value.startsWith("[")){
				value = value.substring(1, value.length()-1);
				if(value.length()<1){
					continue;
				}
				System.out.println("  " + name + "= <本项含多条记录,如下:>");
				String[] vs = value.split(",");
				for(int a=0;a<vs.length;a++){
					String v = vs[a];
					if(v.length()>2){
						v = v.substring(1, v.length()-1);
						System.out.println("    记录" + (a+1) + ". " + v);
					}
				}
				continue;
			}
			String show = name + "= " + value;
			System.out.println("  " + show);
		}
	}
	
	private boolean runOnceChildCommand(String line, boolean isSave) throws JSONException {
		String[] lines = line.split("\\?");
		String actionName = getActionName(lines[0]);
		JSONObject root = new JSONObject();
		root.put("ACTION_NAME", actionName);
		JSONObject info = getInputParameter(lines[0]);
		if(null==info){
			return false;
		}
		root.put("ACTION_INFO", info);
		String reback = "";
		try{
			reback = sendPack(root.toString());
			if(isSave){
				oldValues = new JSONObject(reback);
			} else {
				if(lines.length>1){
					parseRecPack(reback, lines[1]);
				} else {
					parseRecPack(reback, null);
				}
			}
		} catch (IOException e){
			
		}
		return true;
	}
	
	private String getActionName(String line){
		String name = "";
		int s = line.indexOf("(") + 1;
		int e = line.indexOf(")");
		name = line.substring(s, e);
		return name;
	}
	
	private JSONObject getInputParameter(String line) throws JSONException {
		JSONObject info = new JSONObject();
		int s = line.indexOf(")") + 1;
		line = line.substring(s, line.length());
		String[] cmdlist = line.split(",");
		boolean goon = true;
		for(int i=0;i<cmdlist.length;i++){
			String cmd = cmdlist[i];
			String[] parts = cmd.split("@");
			if(parts[0].startsWith("^")){
				String name = parts[0];
				name = name.substring(1, name.length());
				System.out.printf("  " + name + "= ");
				while(true){
					String in = BlackScreenClient.tinput();
					if(in.length()>0){
						if(in.equals("!")){
							info = null;
							break;
						}
						info.put(parts[1], in);
						break;
					} else {
						System.out.println("  本项是必填字段。若终止操作请输入 \"！\"");
						System.out.printf("  " + name + "= ");
					}
				}
			} else {
				String name = parts[0];
				System.out.printf("  " + name + "= ");
				String in = "";
				if(goon){
					in = BlackScreenClient.tinput();
					if(in.length()>0){
						if(Util.DEBUG_LEVEL>2){
							System.out.println("the input char at :" + (int)in.charAt(0));
						}
						if(Util.isLinux){
							if((int)in.charAt(0)==Util.KEY_CTRL_X){
								goon = false;
								in = "";
							}
						} else {
							if((int)in.charAt(0)==Util.KEY_CTRL_Z){
								goon = false;
								in = "";
							}							
						}
					}
				} else {
					System.out.println("");
					continue;
				}
				if(Util.DEBUG_LEVEL>2){
					System.out.println("the put value is : " + in);
				}
				if(in.length()>0){
					info.put(parts[1], in);
				}
			}
		}
		return info;
	}
	
	private JSONObject getInputParameter(JSONObject oldValues, String line, boolean isChild) throws JSONException {
		String[] oldValueContent = null;
		if(isChild){
			oldValueContent = JsonObjectToStringList(oldValues.toString());
		} else {
			oldValueContent = getOldValueContent(oldValues);
		}
		if(oldValueContent==null){
			return null;
		}
		JSONObject info = new JSONObject();
		int s = line.indexOf(")") + 1;
		line = line.substring(s, line.length());
		String[] cmdlist = line.split(",");
		boolean isPrint = true;
		for(int i=0;i<cmdlist.length;i++){
			String cmd = cmdlist[i];
			String[] parts = cmd.split("@");
			String name = parts[1];
			if(name.startsWith("[")){
				name = name.substring(1, name.length()-1);
				String part = parts[0] + "@" + name;
				String oldValue = getOldValue(oldValueContent, part);
				info.put(name, oldValue);
				if(null!=this.oldValues){
					this.oldValues = null;
				}
			} else if(name.indexOf("'")!=-1) {
				boolean isBrfore = true;
				String[] tmp = name.split("\\+");
				String ov = "";
				if(tmp[0].indexOf("'")!=-1){
					name = tmp[1];
					ov = tmp[0];
				} else if(tmp[1].indexOf("'")!=-1){
					name = tmp[0];
					ov = tmp[1];
					isBrfore = false;
				}
				ov = ov.substring(1, ov.length()-1);
				if(checkKeyWord(ov)){
					ov = getKeyWord(ov);
				}
				String part = parts[0] + "@" + name;
				String oldValue = getOldValue(oldValueContent, part);
				if(isBrfore){
					info.put(name, ov + oldValue);
				} else {
					info.put(name, oldValue + ov);
				}
				if(null!=this.oldValues){
					this.oldValues = null;
				}
			} else if (name.indexOf("=")!=-1){
				String[] value = name.split("=");
				info.put(value[0], value[1]);
			} else {
				if(isPrint){
					System.out.println("  请在提示 \"新数据=\" 后面输入需要更改的新数据。不输入任何字符，直接按回车则保留数据");
					isPrint = false;
				}
				String oldValue = getOldValue(oldValueContent, cmd);
				if(oldValue.startsWith("{")){
					JSONObject child = getChildInputParameter(parts[0], oldValue, line);
					if(null==child){
						continue;
					} else {
						info.put(parts[1], child);
					}
				} else {
					System.out.printf("  " + parts[0] + "= " + oldValue);
					System.out.printf("\n    新数据= ");
					String in = BlackScreenClient.tinput();
					System.out.println("");
					if(in.length()>0){
						info.put(parts[1], in);
					} else {
						info.put(parts[1], oldValue);
					}
				}
			}
		}
		return info;
	}
	
	private JSONObject getChildInputParameter(String iname, String oldValue, String line) throws JSONException {
		JSONObject old = new JSONObject(oldValue);
		JSONObject info = new JSONObject();
		JSONArray names = old.names();
		String[] cmdlist = line.split(",");
		boolean print = false;
		for(int i=0;i<names.length();i++){
			String name = names.getString(i);
			String value = old.getString(name);
			String tname = "";
			for(int a=0;a<cmdlist.length;a++){
				String[] tl = cmdlist[a].split("@");
				String tn = tl[1];
				if(name.equals(tn)){
					tname = cmdlist[a];
					break;
				}
			}
			if(tname.length()>0){
				if(!print){
					System.out.printf("  " + iname + " <包含多项子数据>:\n");
					print = true;
				}
				if(value.startsWith("[")){
					System.out.println("    " + tname + " <此项目包含特殊数据，修改时请依照原数据格式>= " + value);
					System.out.printf("      新数据= ");
				} else {
					System.out.println("    " + tname + "= " + value);
					System.out.printf("      新数据= ");
				}
				String in = BlackScreenClient.tinput();
				if(in.length()>0){
					info.put(name, in);
				} else {
					info.put(name, value);
				}
			} else {
				info.put(name, value);
			}
		}
		return info;
	}
		
	private String getOldValue(String[] oldValueContent, String cmd){
		String[] parts = cmd.split("@");
		String oldValue = "";
		for(int a=0;a<oldValueContent.length;a++){
			String temp = oldValueContent[a];
			String[] ts = null;
			if(temp.endsWith(":")){
				temp = temp.substring(0, temp.length()-1);
				ts = new String[]{temp, ""};
			} else {
				if(temp.indexOf("{")!=-1){
					int s = temp.indexOf("{");
					int e = temp.indexOf("}");
					if(e==-1){
						e = temp.length()-1;
					}
					String value = temp.substring(s, e+1);
					s = temp.indexOf("{");
					String name = temp.substring(0, s-1);
					ts = new String[]{name, value};
				} else {
					ts = temp.split(":");
				}
			}
			String oldName = ts[0];
			if(oldName.equals(parts[1])){
				oldValue = ts[1];
				break;
			}
		}
		return oldValue;
	}
	
	private String[] getOldValueContent(JSONObject oldValues) throws JSONException {
		JSONObject info = oldValues.getJSONObject("ACTION_INFO");
		JSONArray data = info.getJSONArray("QUERY_DATA");
		String[] values = null;
		if(data.length()>0){
			String temp = data.getString(0);
			values = JsonObjectToStringList(temp);
		}
		return values;
	}
	
	private String[] JsonObjectToStringList(String object) throws JSONException {
		JSONObject info = new JSONObject(object);
		JSONArray names = info.names();
		int count = 0;
		String[] child = null;
		String[] result = null;
		for(int i=0;i<names.length();i++){
			String name = names.getString(i);
			String value = info.getString(name);
			if(value.startsWith("{")&&value.endsWith("}")){
				child = JsonObjectToStringList(value);
			} else {
				count++;
			}
		}
		if(null!=child){
			result = new String[count + child.length + 1];
			for(int i=0;i<result.length;i++){
				if(i>child.length-1){
					break;
				}
				result[i] = child[i];
			}
			for(int i=0;i<names.length();i++){
				String name = names.getString(i);
				String value = info.getString(name);
				result[i+child.length] = name + ":" + value;
			}
		} else {
			result = new String[count];
			for(int i=0;i<names.length();i++){
				String name = names.getString(i);
				String value = info.getString(name);
				result[i] = name + ":" + value;
			}
		}
		return result;
	}
	
	private String parseRecPack(String input, String resolve) {
		if(Util.DEBUG_LEVEL>0){
			System.out.println(input);
		}
		String macStr = input.substring(input.length()-64, input.length());
		String jsonStr = input.substring(0, input.length()-64);
		try{
			JSONObject root = new JSONObject(jsonStr);
			String ACTION_NAME = root.getString("ACTION_NAME");
			JSONObject ACTION_INFO = root.getJSONObject("ACTION_INFO");
			int RETURN_CODE = ACTION_INFO.getInt("RETURN_CODE");
			String RETURN_MESSAGE = ACTION_INFO.getString("RETURN_MESSAGE");
			int QUERY_TOTAL = 0;
			JSONArray QUERY_DATA = null;
			try{
				QUERY_TOTAL = ACTION_INFO.getInt("QUERY_TOTAL");
				QUERY_DATA = ACTION_INFO.getJSONArray("QUERY_DATA");
			}catch (Exception e) {
			}
			
			if(QUERY_TOTAL<1){
				System.out.println("  未得到符合条件的数据");
			}
			System.out.printf("\n收到应答:\n");
			if (QUERY_TOTAL > 0) {
				System.out.println("=================================================================");
				System.out.println("  返回数据:\n");
				boolean goon = false;
				for (int i = 0; i <QUERY_DATA.length(); i++) {
					String el = QUERY_DATA.getString(i);
					formatOutputDate(el, resolve);
					if(!goon){
						String in = BlackScreenClient.tinput();
						if(in.length()>0){
							if(Util.isLinux){
								if(((int)in.charAt(0))==Util.KEY_CTRL_X){
									System.out.println("  本次操作被用户终止！");
									break;
								} else if (((int)in.charAt(0))==Util.KEY_CTRL_A){
									goon = true;
								}
							} else {
								if(((int)in.charAt(0))==Util.KEY_CTRL_X){
									System.out.println("  本次操作被用户终止！");
									break;
								} else if (((int)in.charAt(0))==Util.KEY_CTRL_A){
									goon = true;
								}
							}
						}
					}
					if(i!=QUERY_DATA.length()-1){
						System.out.println("-----------------------------------------------------------------");
					}
				}
				System.out.println("=================================================================");
			}
			if(Util.DEBUG_LEVEL>3){
				System.out.println("ACTION_NAME:"+ACTION_NAME);
				System.out.println("返回码:"+RETURN_CODE+";"+((RETURN_CODE>=0&&RETURN_CODE<=16)?erMsg[RETURN_CODE]:""));
				System.out.println("返回信息:"+RETURN_MESSAGE);
				System.out.println("请求总数:"+QUERY_TOTAL);
				if(Util.DEBUG_LEVEL>0){
					System.out.println("校验:"+macStr);
				}
			}
			if(RETURN_CODE>0&&RETURN_CODE<=16){
				System.out.println("返回码:"+RETURN_CODE+";"+((RETURN_CODE>=0&&RETURN_CODE<=16)?erMsg[RETURN_CODE]:""));
				return "err";
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("JSON错误:"+e.getMessage());
			System.out.println("错误报文结构:"+input);
			return "err";
		}
		return "end";
	}

//	private String sendPack(String actionName, JSONObject info) {
//		String receive = "";
//		JSONObject root = new JSONObject();
//		try {
//			root.put("ACTION_INFO", info);
//			root.put("ACTION_NAME", actionName);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String pack = root.toString();
//		pack+=Util.SIGNATURE;
//		if(Util.DEBUG_LEVEL>0){
//			System.out.println(pack);
//		}
//		try {
//			HttpConnect hc = new HttpConnect(BlackScreenClient.sendUrl);
//			hc.SendData(pack);
//			receive = hc.ReceiveUTFString();
////			System.out.println(receive);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return receive;
//	}

	private String sendPack(String pack) throws IOException {
		String receive = "";
		pack+=Util.SIGNATURE;
		if(Util.DEBUG_LEVEL>0){
			System.out.println(pack);
		}
		HttpConnect hc = new HttpConnect(BlackScreenClient.sendUrl);
		hc.SendData(pack);
		receive = hc.ReceiveUTFString();
		if(Util.DEBUG_LEVEL>0){
			System.out.println(receive);
		}
		return receive;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@SuppressWarnings("deprecation")
	private void showLogInfo(){
		try{
			Statement statement = conn.createStatement();
			String sql = "select * from risetek_logs order by id";
			ResultSet result = statement.executeQuery(sql);
			System.out.print("\n");
			boolean goon = false;
			System.out.println("=================================================================");
			while(result.next()){
				String msg = "";
				Timestamp time = result.getTimestamp(4);
				if(null==time){
					continue;
				}
				msg = time.toLocaleString();
				System.out.println("记录时间: " + msg);
				msg = result.getString(2);
				System.out.println("记录者: " + msg);
				msg = result.getString(3);
				System.out.println("日志内容: " + msg);
				System.out.println("-----------------------------------------------------------------");
				if(!goon){
					String in = BlackScreenClient.tinput();
					if(in.length()>0){
						if(Util.isLinux){
							if(((int)in.charAt(0))==Util.KEY_CTRL_X){
								System.out.println("本次操作被用户终止！");
								break;
							} else if (((int)in.charAt(0))==Util.KEY_CTRL_A){
								goon = true;
							}
						} else {
							if(((int)in.charAt(0))==Util.KEY_CTRL_Z){
								System.out.println("本次操作被用户终止！");
								break;
							} else if (((int)in.charAt(0))==Util.KEY_CTRL_A){
								goon = true;
							}
						}
					}
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	JSONObject oldValues;
	private void formatOutputDate(String el, String resolve) {
		if(null!=resolve){
			if(resolve.indexOf(";")!=-1){
				resolve = resolve.substring(0, resolve.length()-1);
			}
		}
		if("save".equals(resolve)){
			System.out.println("已查询到客户信息");
			try{
				oldValues = new JSONObject(el);
			} catch (JSONException e){
				
			}
			return;
		}
		String[] descripts = null;
		if(null!=resolve){
			descripts = resolve.split(",");
		} else {
			descripts = new String[0];
		}
		try {
			JSONObject jo = new JSONObject(el);
			JSONArray names = jo.names();
			JSONArray values = jo.toJSONArray(names);
			for(int i=0;i<names.length();i++){
				String name = names.getString(i);
				String value = values.getString(i);
				for(int a=0;a<descripts.length;a++){
					String[] split = descripts[a].split("@");
					if(split.length>1){
						String des = split[1];
						if(name.equals(des)){
							name = split[0];
							break;
						}
					} else {
						break;
					}
				}
				if(value.startsWith("{")&&value.endsWith("}")){
					formatOutputDate(value, resolve);
					continue;
				} else if(value.startsWith("[")){
					value = value.substring(1, value.length()-1);
					System.out.println("  " + name + "= <本项含多条记录,如下:>");
					String[] vs = value.split(",");
					for(int a=0;a<vs.length;a++){
						String v = vs[a];
						if(!v.startsWith("\"")){
							break;
						}
						v = v.substring(1, v.length()-1);
						System.out.println("    记录" + (a+1) + ". " + v);
					}
					continue;
				}
				String show = name + "= " + value;
				System.out.println("  " + show);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean checkKeyWord(String value){
		for(int i=0;i<Util.KEY_WORD_LIST.length;i++){
			String key = Util.KEY_WORD_LIST[i];
			if(key.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	private String getKeyWord(String value){
		String reback = "";
		if("NOW".equals(value)){
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMddHHmm");
			reback = format.format(now);
		}
		return reback;
	}
}
