package com.risetek.operation.platform.launch.client.ui;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.risetek.operation.platform.launch.client.config.UIConfig;

public class RisetekDateBox extends Grid {

	private DateBox dateBox = new DateBox();
	private static int minPart = 0;
	private ListBox hourList = new ListBox();
	private ListBox minList = new ListBox();
	
	public RisetekDateBox(String pattern){
		this(pattern, false, minPart);
	}
	
	public RisetekDateBox(String pattern, boolean isRead) {
		this(pattern, isRead, minPart);
	}

	public RisetekDateBox(String pattern, int minPart){
		this(pattern, false, minPart);
	}
	
	public RisetekDateBox(String pattern, boolean isRead, int minPart){
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(pattern);
		DefaultFormat format = new DefaultFormat(dateFormat);
		dateBox.setFormat(format);
		setReadOnly(isRead);
		if(minPart>0){
			for(int i=0;i<24;i++){
				if(i<10){
					String item = "0" + Integer.toString(i);
					hourList.addItem(item);
				} else {
					String item = Integer.toString(i);
					hourList.addItem(item);
				}
			}
			for(int i=0;i<minPart;i++){
				int min = i*(60/minPart);
				if(min<10){
					String item = "0" + Integer.toString(i*(60/minPart));
					minList.addItem(item);
				} else {
					String item = Integer.toString(i*(60/minPart));
					minList.addItem(item);
				}
			}
			resize(1, 6);
			setWidth("100%");
			addStyleName("dialog-table");
			setWidget(0, 0, dateBox);
			setText(0, 1, "日");
			setWidget(0, 2, hourList);
			setText(0, 3, "时");
			setWidget(0, 4, minList);
			setText(0, 5, "分");
			getColumnFormatter().setWidth(1, "20px");
			getColumnFormatter().setWidth(2, "45px");
			getColumnFormatter().setWidth(3, "20px");
			getColumnFormatter().setWidth(4, "45px");
			getColumnFormatter().setWidth(5, "20px");
		} else {
			resize(1, 1);
			setWidget(0, 0, dateBox);
		}
	}
	
	public void setReadOnly(boolean readOnly) {
		DOM.setElementPropertyBoolean(dateBox.getElement(), "readOnly", readOnly);
		String readOnlyStyle = "readonly";
		if (readOnly) {
			dateBox.addStyleDependentName(readOnlyStyle);
		} else {
			dateBox.removeStyleDependentName(readOnlyStyle);
		}
	}
	
	public DateBox getDateBox(){
		return dateBox;
	}
	
	public Date getDateValue(){
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(UIConfig.NOW_TIME_FORMAT);
		String text = dateBox.getTextBox().getText();
		int index = hourList.getSelectedIndex();
		text += " " + hourList.getItemText(index);
		index = minList.getSelectedIndex();
		text += ":" + minList.getItemText(index) + ":00";
		Date date = dateFormat.parse(text);
		return date;
	}

	public void setDateValue(Timestamp value) {
		String time = value.toString();
		String[] t1 = time.split(" ");
		dateBox.getTextBox().setText(t1[0]);
		String[] t2 = t1[1].split(":");
		String hour = t2[0];
		String min = t2[1];
		int count = hourList.getItemCount();
		for(int i=0;i<count;i++){
			String item = hourList.getItemText(i);
			if(item.equals(hour)){
				hourList.setSelectedIndex(i);
				break;
			}
		}
		count = minList.getItemCount();
		for(int i=0;i<count;i++){
			String item = minList.getItemText(i);
			if(item.equals(min)){
				minList.setSelectedIndex(i);
				break;
			}
		}
	}
}
