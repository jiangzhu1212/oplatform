package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class DeleteRoleDialog extends CustomDialog {

	public String id;
	
	public DeleteRoleDialog(String value){
		Label info = new Label("确定删除该角色\"" + value + "\"？");
		info.setWidth("200px");
		mainPanel.add(info);
		setText("删除角色");
		setDescript("删除选中的用户角色");
	}
	
	public void show(){
		submit.setText("删除");
		super.show();
	}
}
