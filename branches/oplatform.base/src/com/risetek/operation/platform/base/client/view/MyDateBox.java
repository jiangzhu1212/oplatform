package com.risetek.operation.platform.base.client.view;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;

public class MyDateBox extends DateBox {
	
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public MyDateBox() {
		super();
		setFormat(new DateBox.DefaultFormat(format));
	}
}
