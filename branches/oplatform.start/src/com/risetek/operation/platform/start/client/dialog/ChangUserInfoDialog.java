package com.risetek.operation.platform.start.client.dialog;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.util.Util;

public class ChangUserInfoDialog extends CustomDialog {

	private PasswordTextBox oldpws = new PasswordTextBox();
	private PasswordTextBox newpws = new PasswordTextBox();
	private PasswordTextBox confpws = new PasswordTextBox();
	private TextBox email = new TextBox();
	private User user;
	
	public ChangUserInfoDialog(User user){
		this.user = user;
		setText("更改个人信息");
		setTitle("更改个人信息");
		setDescript("更改登录密码和注册邮箱");
		email.setValue(user.getEmail());
		Grid grid = new Grid(4, 2);
		grid.setWidth("200px");
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "70px");
		grid.getColumnFormatter().setWidth(1, "120px");
		grid.setText(0, 0, "原密码：");
		grid.setWidget(0, 1, oldpws);
		grid.setText(1, 0, "新密码：");
		grid.setWidget(1, 1, newpws);
		grid.setText(2, 0, "确认密码：");
		grid.setWidget(2, 1, confpws);
		grid.setText(3, 0, "注册邮箱：");
		grid.setWidget(3, 1, email);
		for(int i=0;i<grid.getRowCount();i++){
			grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		mainPanel.add(grid);
		oldpws.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		newpws.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		confpws.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
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
	}
	
	public void show(){
		submit.setText("更改");
		super.show();
	}
	
	public boolean isVaild(){
		String check = Util.isInputStringEmpty(oldpws.getText());
		if(check!=null){
			setMessage("原密码" + check);
			return false;
		}
		check = Util.isInputStringEmpty(email.getText());
		if(check!=null){
			setMessage("注册邮箱" + check);
			return false;
		}
		check = Util.isInputStringEmpty(newpws.getText());
		if(check==null){
			String np = newpws.getText();
			String cnp = confpws.getText();
			if(!np.equals(cnp)){
				setMessage("新密码和确认密码不一致！");
				newpws.setText("");
				confpws.setText("");
				oldpws.setText("");
				return false;
			}
		}
		if(!oldpws.getText().equals(user.getUserPassword())){
			setMessage("原密码输入错误！");
			newpws.setText("");
			confpws.setText("");
			oldpws.setText("");
			return false;
		}
		return true;
	}
	
	public User getValue(){
		user.setEmail(email.getText());
		String check = Util.isInputStringEmpty(newpws.getText());
		if(check==null){
			user.setUserPassword(newpws.getText());
		}
		return user;
	}
}
