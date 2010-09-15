package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class ChangUserStatusDialog extends CustomDialog {

	public String id;
	
	public ChangUserStatusDialog(String value, int type){
		String labelName = "";
		if(type == UIConfig.CHANG_USER_STATUS_TYPE_OFF){
			labelName = "确定注销用户\"" + value + "\"？";
			setText("注销用户");
			setDescript("注销选中的用户");
			submit.setText("注销");
		} else if(type == UIConfig.CHANG_USER_STATUS_TYPE_HANG){
			labelName = "确定挂起用户\"" + value + "\"？";
			setText("挂起用户");
			setDescript("挂起选中的用户");
			submit.setText("挂起");
		} else if(type == UIConfig.CHANG_USER_STATUS_TYPE_KICK){
			labelName = "强制用户\"" + value + "\"下线？";
			setText("强制用户下线");
			setDescript("强制选中的用户下线");
			submit.setText("下线");
		} else if(type == UIConfig.CHANG_USER_STATUS_TYPE_RESET){
			labelName = "确定复位用户\"" + value + "\"的密码？";
			setText("复位用户密码");
			setDescript("复位选中用户的登录密码");
			submit.setText("复位");
		}
		Label info = new Label(labelName);
		info.setWidth("300px");
		mainPanel.add(info);
		
	}
	
	public void show(){
		super.show();
	}
}
