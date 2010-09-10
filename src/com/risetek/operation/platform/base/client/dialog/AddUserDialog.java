package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.util.Util;

public class AddUserDialog extends CustomDialog {

	TextBox userName = new TextBox();
	ListBox role = new ListBox();
	TextBox email = new TextBox();
	TextArea notes = new TextArea();
	
	public AddUserDialog(RoleConfigData roleData) {
		setText("添加用户");
		setTitle("添加用户");
		setDescript("添加一个平台用户");
		Grid grid = new Grid(4, 2);
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "60px");
		grid.getColumnFormatter().setWidth(1, "240px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.setText(0, 0, "用户名：");
		grid.setWidget(0, 1, userName);
		grid.setText(1, 0, "角色：");
		grid.setWidget(1, 1, role);
		grid.setText(2, 0, "Email：");
		grid.setWidget(2, 1, email);
		grid.setText(3, 0, "备注：");
		grid.setWidget(3, 1, notes);
		grid.setWidth("300px");
		mainPanel.add(grid);
		userName.setFocus(true);
		notes.setHeight("60px");
		
		role.setSelectedIndex(0);
		role.addItem("请选择......", "");
		String[][] data = roleData.getData();
		for(int i=0;i<data.length;i++){
			String[] item = data[i];
			role.addItem(item[1], item[0]);
		}
		
		userName.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
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
	}

	public void show(){
		submit.setText("添加");
		super.show();
	}
	
	public boolean isValid(){
		String check = Util.isInputStringEmpty(userName.getText());
		if(check!=null){
			setMessage("用户名" + check);
			return false;
		}
		int index = role.getSelectedIndex();
		String value = role.getValue(index);
		check = Util.isInputStringEmpty(value);
		if(check!=null){
			setMessage("请选择用户角色！");
			return false;
		}
		check = Util.isInputStringEmpty(email.getText());
		if(check!=null){
			setMessage("Email" + check);
			return false;
		}
		if(!Util.isMailAddr(email.getText())){
			setMessage("Email格式不合法！");
			return false;
		}
		return true;
	}
	
	public User getNewValue(){
		User user = new User();
		user.setUserName(userName.getText());
		String pws = Util.makeRandomString();
		user.setUserPassword(pws);
		user.setStatus(-2);
		int index = role.getSelectedIndex();
		String value = role.getValue(index);
		user.setRole(Integer.parseInt(value));
		user.setEmail(email.getText());
		user.setLastLoginAddress("0.0.0.0");
		user.setNotes(notes.getText());
		return user;
	}
}
