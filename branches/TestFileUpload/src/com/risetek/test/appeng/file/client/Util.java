package com.risetek.test.appeng.file.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Util {

	public final static String CONTENTTYPE_APK = "application/vnd.android.package-archive";
	
	public final static String CONTENTTYPE_MP3 = "audio/x-mpeg";
	
	/**
	 *  将日期类型转换为JSON发送的yyyyMMddHHmmss 类型的字符串 
	 */
	public static String formatDateToJsonString(Date date) {
		String str = "";
		if(date != null){
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMddHHmmss");
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
	 * 根据需要转换日期
	 * str2 转化的日期格式
	 */
	public static String formatDateToStringByStr2(Date date , String str2){
		String str = "";
		if(date != null){
			DateTimeFormat dateFormat = DateTimeFormat.getFormat(str2);
			str = dateFormat.format(date);
		}
		
		return str;
	}
	
}
