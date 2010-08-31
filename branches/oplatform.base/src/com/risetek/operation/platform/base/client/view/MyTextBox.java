package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author 杨彬
 * 直接设置textBox的长度
 */
public class MyTextBox extends TextBox{
	public MyTextBox() {
		super();
		setWidth("220px");
	}
	public MyTextBox(String width) {
		super();
		setWidth(width);
	}
}
