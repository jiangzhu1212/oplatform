package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class ChangUserStatusDialog extends CustomDialog {

	public String id;
	
	public ChangUserStatusDialog(String value, int type){
		Label info = new Label("确定注销用户\"" + value + "\"？");
		info.setWidth("300px");
		mainPanel.add(info);
		setText("注销用户");
		setDescript("注销选中的用户");
	}
	
	public void show(){
		submit.setText("注销");
		super.show();
	}
}
