package com.risetek.operation.platform.base.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateTimeFormatUtil {

	/*
	 * 将yyyyMMddhhmmss的字符串时间格式转换为yyyy-MM-dd hh:mm:ss
	 */	
	static public String formatStringDate(String text){
		if(text==null || text.length()!=14){
			return text;
		}
		DateTimeFormat oldFormat = DateTimeFormat.getFormat("yyyyMMddhhmmss");		
		DateTimeFormat newFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");	
		Date date = oldFormat.parse(text);
		return newFormat.format(date);
	}
	
	/*
	 * 将TimeField控件中的Hm类型字符串的时钟值转换为JSON包发送的hhmmss
	 */
	static public String formatHmStringTime(String time)
	{
		/*
		time =time.replaceAll(":", "");
		//time = time.replace(':',' ');
		
		String temp = time.replaceAll(" ", "");		
		int endIndex = 0;
		if(temp.endsWith("AM"))
		{
			endIndex= temp.indexOf("AM");
			temp = temp.substring(0, endIndex);
			
		}
		else if(temp.endsWith("PM"))
		{
			endIndex = temp.indexOf("PM");
			temp = temp.substring(0, endIndex);
			int num = Integer.parseInt(temp);
			num +=1200;
			temp = String.valueOf(num);			
		}
		temp +="00";
		if(temp.length() == 5)
		{
			temp = "0"+temp;
		}
		*/
		time=time.replaceAll(":", "");
		time=time+"00";
		if(time.length() == 5)
		{
			time = "0"+time;
		}
		
		return time;
	}
	
	/* 
	 * change yyyy-MM-dd hh:mm:ss   To   yyyyMMddhhmmss 
	 *  */
	static public String changeDateStringFormat(String text){
		DateTimeFormat newFormat = DateTimeFormat.getFormat("yyyyMMddhhmmss");		
		DateTimeFormat oldFormat = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss");	
		Date date = oldFormat.parse(text);
		return newFormat.format(date);
	}
	
	/* 将日期类型转换为JSON发送的yyyyMMdd 类型的字符串 */
	static public String formatDateToJsonString(Date date)
	{
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd");	
		return dateFormat.format(date);
	}
	/* 将Hm时钟类型转换为字符串 */
	static public String formatTimeToString(Date date)
	{
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("H:m");
		String timeText = dateTimeFormat.format(date);
		return timeText;		
	}
	/* 将日期类型转换为yyyy-MM-dd 的字符串 */
	static public String formatDateToShowString(Date date)
	{
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
		String dateText = dateTimeFormat.format(date);
		return dateText;
		
	}


	static public String formatMINDateToJsonString(Date date)
	{
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd000000");	
		return dateFormat.format(date);
	}
	

	static public String formatMAXDateToJsonString(Date date)
	{
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd235959");	
		return dateFormat.format(date);
	}

}
