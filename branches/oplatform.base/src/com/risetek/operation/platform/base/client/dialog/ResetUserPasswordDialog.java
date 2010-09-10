package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class ResetUserPasswordDialog extends CustomDialog {

	public ResetUserPasswordDialog(String value){
		Label info = new Label("确定复位用户\"" + value + "\"的登录密码？");
		info.setWidth("300px");
		mainPanel.add(info);
		setText("密码复位");
		setDescript("复位选中用户的登录密码");
	}
	
	public void show(){
		submit.setText("复位");
		super.show();
	}
}
