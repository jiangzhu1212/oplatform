package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class RoleDeleteDialog extends CustomDialog {

	public String id;
	
	public RoleDeleteDialog(String value){
		Label info = new Label("确定删除该角色\"" + value + "\"及其全部操作内容？");
		info.setWidth("300px");
		mainPanel.add(info);
		setText("删除角色");
		setDescript("删除选中的用户角色");
	}
	
	public RoleDeleteDialog(String value, int count){
		Label info = new Label("确定删除选中的\"" + count + "\"条角色及其全部操作内容？");
		info.setWidth("300px");
		mainPanel.add(info);
		setText("删除角色");
		setDescript("删除选中的用户角色");
	}
	
	public void show(){
		submit.setText("删除");
		super.show();
	}
}
