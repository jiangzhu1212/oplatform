package com.risetek.operation.platform.launch.client.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;

public class Util {

	/**
	 * 把分转化为元
	 * @param a
	 * @return
	 */
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
	/**
	 * 取得列名为colName的列号
	 */
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
	 * 取得账单状态
	 */
	public static ListBox getBillStatus(){
		ListBox list_status = new ListBox();
		list_status.addItem( "" , "" );
		list_status.addItem( "已生成" , "generated" );
		list_status.addItem( "已支付" , "paid" );
		list_status.addItem( "已失效" , "invalid" );
		list_status.addItem( "已超时" , "timeout" );
	
		return list_status;
	}
	
	/**
	 * 根据value把账单状态转化为中文
	 * @param billStatus
	 * @return
	 */
	public static String convertBillStatusZh(String billStatus) {
		String  billStatus_Zh= "";
		if("generated".equals(billStatus)){
			billStatus_Zh = "已生成"; 
		}else if("paid".equals(billStatus)){
			billStatus_Zh = "已支付";  
		}else if("invalid".equals(billStatus)){
			billStatus_Zh = "已失效"; 
		}else if("timeout".equals(billStatus)){
			billStatus_Zh = "已超时";  
		}
		return billStatus_Zh;
	}
	
	/**
	 * 取得第三方状态
	 */
	public static ListBox getThirdStatus(){
		ListBox list_status = new ListBox();
		list_status.addItem( "" , "" );
		list_status.addItem( "未发送" , "to_send" );
		list_status.addItem( "已通知" , "noticed" );
		list_status.addItem( "已发送" , "sent" );
		list_status.addItem( "已失效" , "invalid" );
	
		return list_status;
	}
	
	public static String convertThirdStatusZh(String sendStatus) {
		String sendStatus_Zh = "";
		if("to_send".equals(sendStatus)){
			sendStatus_Zh = "未发送";
		}else if("noticed".equals(sendStatus)){
			sendStatus_Zh = "已通知";
		}else if("sent".equals(sendStatus)){
			sendStatus_Zh = "已发送"; 
		}else if("invalid".equals(sendStatus)){
			sendStatus_Zh = "已失效"; 
		}
		return sendStatus_Zh;
	}
	
	
	/**
	 * 取得字段VALIDITY的listBox
	 */
	public static ListBox getValidity(){
		
		ListBox list_validity = new ListBox();
		list_validity.addItem( "" , "" );
		list_validity.addItem( "有效" , Constanst.TRUE );
		list_validity.addItem( "无效" , Constanst.FALSE );
		
		return list_validity;
	}
	/**
	 * 取得字段ENABLE的listBox
	 */
	public static ListBox geteNable(){
		
		ListBox list_enable = new ListBox();
		
		list_enable.addItem( "有效" , Constanst.TRUE );
		list_enable.addItem( "无效" , Constanst.FALSE );
		
		return list_enable;
	}
	
	
	/**
	 * 是否能够绑定的listBox
	 */
	public static ListBox getBindAble(){
		
		ListBox list_bindAble = new ListBox();
		
		list_bindAble.addItem( "否" , Constanst.FALSE );
		list_bindAble.addItem( "是" , Constanst.TRUE );
				
		return list_bindAble;
	}
	
	public static Widget createDatePanel(DateBox dateMin , DateBox dateMax , String panelName) {
		Grid stopGrid = new Grid(2, 2);
		stopGrid.setWidget(0, 0, new Label("开始日期："));
		stopGrid.setWidget(1, 0, new Label("结束日期："));
		stopGrid.setWidget(0, 1, dateMin);
		stopGrid.setWidget(1, 1, dateMax);

		DisclosurePanel dp = new DisclosurePanel(panelName);
		dp.setAnimationEnabled(true);
		dp.setContent(stopGrid);
		return dp;
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
	
	/**
	 * 判断时间大小正确否
	 * 如果日期带 时分秒 则去掉时分秒
	 * @param str1 最小时间
	 * @param str2 最大时间
	 * @return 
	 */
	public static boolean checkStringBulk (String str1 , String str2){		
		if(str1 == null || "".equals(str1.trim())){
			return true ;
		}
		if(str2 == null || "".equals(str2.trim())){
			return true ;
		}
		str1 = str1.replaceAll("000000", "");
		str1 = str1.replaceAll("235959", "");
		str2 = str2.replaceAll("000000", "");
		str2 = str2.replaceAll("235959", "");
		return Integer.parseInt(str1) <= Integer.parseInt(str2) ? true : false ;
	}
	
	/**
	 *  将日期类型转换为JSON发送的yyyyMMdd 类型的字符串 
	 */
	public static String formatDateToJsonString(Date date) {
		String str = "";
		if(date != null){
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd");
			str = dateFormat.format(date);
		}		
		return str;
		
	}
	
	/**
	 *  yyyy-MM-dd  To yyyyMMdd000000
	 *  传空则返回 ""
	*/
	public static String formatMINDateToJsonString(Date date) {
		String str = "";
		if(date != null){
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd000000");
			str = dateFormat.format(date);
		}
		
		return str;
	}
	
	/**
	 *  change yyyy-MM-dd To yyyyMMdd235959
	 */
	static public String formatMAXDateToJsonString(Date date) {
		String str = "";
		if(date != null){
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd235959");
			str = dateFormat.format(date);
		}
		
		return str;
	}
	
	public static String formatTimestampToString(Timestamp time){
		DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = format.format(time);
		return timeString;
	}
	
	/**
	 * 生成一组随机字符串
	 * @return
	 */
	public static String makeRandomString() {
        String radStr = "aA1bB2cC3dD4eE5fF6gG7hH8iI9jJ0kK1lL2mM3nN4oO5pP6qQ7rR8sS9tT0uU1vV2wW3xX4yY5zZ6";
        StringBuffer generateRandStr = new StringBuffer();
        Random rand = new Random();
        int length = 10;
        for(int i=0;i<length;i++)
        {
            int randNum = rand.nextInt(26);
            generateRandStr.append(radStr.substring(randNum,randNum+1));
        }
        return generateRandStr+"";
    }
	
	public static boolean isMailAddr(String mailAddr){
		if (mailAddr.matches("[ \\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]{2,3}")){
			return true;
		}
		return false;
	}
	
	public static String isInputStringEmpty(String input){
		input = input.trim();
		if(input.length()>0){
			return null;
		} else {
			return "不能为空！";
		}
	}
}
