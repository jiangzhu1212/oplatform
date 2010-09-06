package com.risetek.operation.platform.launch.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;

public class Util {

	//把分转化为元
	public static String formatMoney(String a){
		StringBuffer amount = new StringBuffer();
		if(!a.startsWith("-")){
			if(a.length() > 2){
				String a1 = a.substring(0,a.length()-2);
				String a2 = a.substring(a.length()-2);
				amount.append(a1);
				amount.append(".");
				amount.append(a2);
			}else if(a.length()==2){
				amount.append("0.");
				amount.append(a);
			}else if(a.length() == 1){
				amount.append("0.0");
				amount.append(a);
			}				
		}else{
			if(a.length() > 3){
				String a1 = a.substring(1,a.length()-2);
				String a2 = a.substring(a.length()-2);
				amount.append(a1);
				amount.append(".");
				amount.append(a2);
			}else if(a.length()==3){
				amount.append("-0.");
				amount.append(a.substring(1));
			}else if(a.length() == 2){
				amount.append("-0.0");
				amount.append(a.substring(1));
			}	
		}
		return amount.toString();
	}
	
	/**
	 * 转化字符串
	 * @param text
	 * @return
	 */
	public static String string2unicode(String s) {

		if (s == null)
			return null;

		StringBuffer result = new StringBuffer();
		int i;
		for (i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 0x2018) {
				result.append('\\');
				result.append('u');	
				String hex = Integer.toHexString(s.charAt(i));
				result.append(hex);
			} else {
				result.append(s.charAt(i));
			}
		}
		return result.toString();
	}
	
	public static String formatDate(String text){
		if(text==null || text.length()!=14){
			return text;
		}
		DateTimeFormat format = DateTimeFormat.getFormat("yyyyMMddHHmmss");		
		DateTimeFormat format1 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");	
		Date date = format.parse(text);
		return format1.format(date);
	}
	
	
	
	
	public static String addLog(){
		String log = "";				
		return log;
	}
	//取得被选行号
	public static List<Integer> getCheckedRow(Grid grid){
		
		List<Integer> list = new ArrayList<Integer>();
		int rowCount = grid.getRowCount();
		for (int i = 1; i < rowCount; i++) {
			CheckBox cBox = (CheckBox)grid.getWidget(i, 0);
			if(cBox.getValue()){
				list.add(i);
			}
		}
		return list;
	}
	//取得列名为colName的列号
	public static int getColumNum(Grid grid , String colName){
		for(int i = 1 ; i < grid.getCellCount(0) ; i++){
			if(grid.getText(0, i).equals(colName)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @Description: 公共验证不能为空的字段
	 * @param text
	 * @return  参数 
	 * @return String 返回类型
	 */
	public static String commValidity(String text, String title) {
		if (null == text || "".equals(text)) {
			return title + "不能为空！";
		}
		return null;
	}
	
	/**
	 * 日期比较函数 little小于或等于big日期返回true
	 * @param little 小日期
	 * @param big 大日期
	 * @return 小日期小于大日期返回true，否则返回false
	 */
	public static boolean lessOrEqualThanDate(String little, String big) {
		String[] littleArray = little.split("-");
		String[] bigArray = big.split("-");
		int yearLittle = Integer.parseInt(littleArray[0]);
		int monthLittle = Integer.parseInt(littleArray[1]);
		int dayLittle = Integer.parseInt(littleArray[2]);

		int yearBig = Integer.parseInt(bigArray[0]);
		int monthBig = Integer.parseInt(bigArray[1]);
		int dayBig = Integer.parseInt(bigArray[2]);
		if (yearBig > yearLittle ? true : false) {
			return true;
		} else if (yearBig == yearLittle ? true : false) {
			if (monthBig > monthLittle ? true : false) {
				return true;
			} else if (monthBig == monthLittle ? true : false) {
				if (dayBig > dayLittle ? true : false) {
					return true;
				} else if (dayBig == dayLittle ? true : false) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * 取得骏网一卡通状态的listBox
	 * @return
	 */
	public static ListBox getJCardStatus(){
		ListBox list_status = new ListBox();
		list_status.addItem( "" , "" );
		list_status.addItem( "可用" , "free" );
		list_status.addItem( "锁定" , "lock" );
		list_status.addItem( "销售" , "sold" );
		list_status.addItem( "失败" , "fail" );
		list_status.addItem( "注销" , "cancel" );
		list_status.addItem( "无效" , "invalid" );
		return list_status;
	}
	
	/**
	 * 判断字符串是否可以转为纯数字
	 */
	public static boolean isNum(String string){
		if (string.matches("[0-9]*")){
			return true;
		}
		return false;
	}

	/*
	 * 将TimeField控件中的Hm类型字符串的时钟值转换为JSON包发送的hhmmss
	 */
	public static String formatHmStringTime(String time) {
		time = time.replaceAll(":", "");
		time = time + "00";
		if (time.length() == 5) {
			time = "0" + time;
		}

		return time;
	}

	/* change yyyy-MM-dd hh:mm:ss To yyyyMMddhhmmss*/
	public static String changeDateStringFormat(String text) {
		DateTimeFormat newFormat = DateTimeFormat.getFormat("yyyyMMddhhmmss");
		DateTimeFormat oldFormat = DateTimeFormat
				.getFormat("yyyy-MM-dd hh:mm:ss");
		Date date = oldFormat.parse(text);
		return newFormat.format(date);
	}

	/* 将日期类型转换为JSON发送的yyyyMMdd 类型的字符串 */
	public static String formatDateToJsonString(Date date) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	/* 将Hm时钟类型转换为字符串 */
	public static String formatTimeToString(Date date) {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("H:m");
		String timeText = dateTimeFormat.format(date);
		return timeText;
	}

	/* 将日期类型转换为yyyy-MM-dd 的字符串 */
	public static String formatDateToShowString(Date date) {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
		String dateText = dateTimeFormat.format(date);
		return dateText;

	}
	
	/* change yyyy-MM-dd  To yyyyMMdd000000*/
	public static String formatMINDateToJsonString(Date date) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd000000");
		return dateFormat.format(date);
	}
	
	/* change yyyy-MM-dd To yyyyMMdd235959*/
	static public String formatMAXDateToJsonString(Date date) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd235959");
		return dateFormat.format(date);
	}
}
