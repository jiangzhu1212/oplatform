package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class EditUserInfoDialog extends CustomDialog {

	private TextBox email = new TextBox();
	private ListBox role = new ListBox();
	
	public EditUserInfoDialog(String value, String rold, OPlatformData roleData) {
		setText("修改用户信息");
		setDescript("修改选中用户的角色和邮箱地址");
		submit.setText("修改");
		Grid grid = new Grid(2, 2);
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "80px");
		grid.getColumnFormatter().setWidth(1, "220px");
		grid.setWidth("300px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.setText(0, 0, "邮箱地址：");
		grid.setText(1, 0, "角色：");
		email.setText(value);
		grid.setWidget(0, 1, email);
		String[][] data = roleData.getData();
		for(int i=0;i<data.length;i++){
			if(data[i][0].equals(rold)){
				role.setSelectedIndex(i);
			}
			role.addItem(data[i][1], data[i][0]);
		}
		grid.setWidget(1, 1, role);
		mainPanel.add(grid);
	}

	public void show(){
		super.show();
	}

}
