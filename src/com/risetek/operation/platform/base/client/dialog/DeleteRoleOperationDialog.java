package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class DeleteRoleOperationDialog extends CustomDialog {

	public String id;
	
	public DeleteRoleOperationDialog(String value, String msg){
		Label info = new Label("确定删除" + msg);
		info.setWidth("240px");
		mainPanel.add(info);
		setText("删除角色操作");
		setDescript("删除角色\"" + value + "\"下选中的操作");
	}
	
	public DeleteRoleOperationDialog(String value, int count){
		
		Label info = new Label("确定删除这" + count + "条操作？");
		info.setWidth("240px");
		mainPanel.add(info);
		setText("删除角色操作");
		setDescript("删除角色\"" + value + "\"下选中的操作");
	}
	
	public void show(){
		submit.setText("删除");
		super.show();
	}
}
