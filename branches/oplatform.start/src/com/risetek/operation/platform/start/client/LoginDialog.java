package com.risetek.operation.platform.start.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.util.Util;

public class LoginDialog extends CustomDialog {

	private TextBox userName = new TextBox();
	private PasswordTextBox pws = new PasswordTextBox();
	
	public LoginDialog(){
		setText("登录系统");
		setTitle("登录");
		Grid grid = new Grid(2, 2);
		grid.setWidth("200px");
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "60px");
		grid.getColumnFormatter().setWidth(1, "140px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.setText(0, 0, "用户名：");
		grid.setWidget(0, 1, userName);
		grid.setText(1, 0, "密码：");
		grid.setWidget(1, 1, pws);
		mainPanel.add(grid);
		userName.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
				}
			}
		});
		pws.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
				}
			}
		});
	}
	
	public void show(){
		submit.setText("登录");
		super.show();
	}
	
	public boolean isValid(){
		String check = Util.isInputStringEmpty(userName.getText());
		if(check!=null){
			setMessage("用户名" + check);
			return false;
		}
		check = Util.isInputStringEmpty(pws.getText());
		if(check!=null){
			setMessage("密码" + check);
			return false;
		}
		return true;
	}

	public User getValue() {
		User user = new User();
		user.setUserName(userName.getText());
		user.setUserPassword(pws.getText());
		return user;
	}
}
