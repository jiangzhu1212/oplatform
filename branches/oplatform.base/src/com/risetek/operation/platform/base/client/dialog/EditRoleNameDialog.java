package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class EditRoleNameDialog extends CustomDialog {

	public TextBox newValue = new TextBox();
	
	public EditRoleNameDialog(String value){
		Grid grid = new Grid(2, 2);
		grid.setStyleName("table");
		grid.setText(0, 0, "原名称：");
		grid.setText(0, 1, value);
		grid.setText(1, 0, "新名称：");
		grid.setWidget(1, 1, newValue);
		grid.getColumnFormatter().setWidth(0, "60px");
		grid.getColumnFormatter().setWidth(1, "240px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setStyleName(0, 1, "oldvalue");
		grid.setWidth("300px");
		mainPanel.add(grid);
		setText("修改角色");
		setDescript("修改角色名称");
	}
	
	public void show(){
		submit.setText("修改");
		super.show();
	}
	
	public boolean isValid(){
		String value = newValue.getText();
		if(null == value || "".equals(value)){
			setMessage("用户角色不能为空");
			return false;
		}
		return true;
	}
}
