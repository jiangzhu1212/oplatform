package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class AddRoleDialog extends CustomDialog {

	TextBox newValue = new TextBox();
	
	public AddRoleDialog() {
		setTitle("添加角色");
		setText("添加角色");
		setDescript("添加一条用户角色");
		Grid grid = new Grid(1, 2);
		grid.setStyleName("table");
		grid.setText(0, 0, "角色名称：");
		grid.getColumnFormatter().setWidth(0, "80px");
		grid.getColumnFormatter().setWidth(1, "280px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.setWidget(0, 1, newValue);
		grid.setWidth("300px");
		mainPanel.add(grid);
		newValue.setFocus(true);
		newValue.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
	}
	
	public void show(){
		submit.setText("添加");
		super.show();
	}
	
	public boolean isValid(){
		String value = newValue.getText();
		if(null == value || "".equals(value)){
			setMessage("角色名称不能为空");
			return false;
		}
		return true;
	}
	
	public String getNewValue(){
		return newValue.getText();
	}
}
