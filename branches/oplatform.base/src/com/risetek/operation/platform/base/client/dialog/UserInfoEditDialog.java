package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class UserInfoEditDialog extends CustomDialog {

	private TextBox email = new TextBox();
	private ListBox role = new ListBox();
	
	public UserInfoEditDialog(String value, String rold, OPlatformData roleData) {
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
		role.addItem("请选择......", Integer.toString(-1));
		for(int i=1;i<=data.length;i++){
			int index = i-1;
			role.addItem(data[index][1], data[index][0]);
			if(data[index][1].equals(rold)){
				role.setSelectedIndex(i);
			}
		}
		role.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		email.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		grid.setWidget(1, 1, role);
		mainPanel.add(grid);
	}

	public void show(){
		super.show();
	}

	public boolean isViald(){
		String check = Util.isInputStringEmpty(email.getText());
		if(check!=null){
			setMessage("Email" + check);
			return false;
		}
		String value = role.getValue(role.getSelectedIndex());
		if(value.equals("-1")){
			setMessage("未选择角色");
			return false;
		}
		return true;
	}
	
	public User getValue(){
		User user = new User();
		user.setEmail(email.getText());
		String value = role.getValue(role.getSelectedIndex());
		user.setRole(Integer.parseInt(value));
		return user;
	}
}
