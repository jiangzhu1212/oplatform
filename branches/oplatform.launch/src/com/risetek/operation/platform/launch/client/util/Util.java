package com.risetek.operation.platform.launch.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RadioButton;

public class Util {

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
	public static int getCheckedRow(Grid grid){
		
		int rowCount = grid.getRowCount();
		for (int i = 1; i < rowCount; i++) {
			RadioButton cBox = (RadioButton)grid.getWidget(i, 0);
			if(cBox.getValue()){
				return i;
			}
		}
		return 0;
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
	
}
