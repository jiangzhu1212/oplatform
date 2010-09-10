package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class ShowUserInfoDialog extends CustomDialog {

	public String id;
	
	public ShowUserInfoDialog(User user, OPlatformData roleData){
		setText("显示用户信息");
		setDescript("显示用户注册信息的详细内容");
		Grid grid = new Grid(9, 2);
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "100px");
		grid.getColumnFormatter().setWidth(1, "200px");
		for(int i=0;i<grid.getRowCount();i++){
			grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		grid.setText(0, 0, "用户编号：");
		grid.setText(0, 1, user.getId().toString());
		grid.setText(1, 0, "用户名：");
		grid.setText(1, 1, user.getUserName());
		grid.setText(2, 0, "状态：");
		int stat = user.getStatus();
		String style = "";
		String text = "离线";
		if(stat==-1){
			style = "red";
			text = "挂起";
		} else if (stat==-2){
			style = "gray";
			text = "失效";
		} else if (stat<-2){
			style = "white";
			text = "注销";
		} else if (stat>0){
			style = "green";
			text = "在线";
		}
		grid.setText(2, 1, text);
		if(style.length()>0){
			grid.getCellFormatter().addStyleName(2, 1, style);
		}
		grid.setText(3, 0, "密码：");
		grid.setText(3, 1, user.getUserPassword());
		grid.getCellFormatter().getElement(3, 1).setAttribute("style", "font-size:16px; font-weight:bold");
		grid.setText(4, 0, "角色：");
		String[][] data = roleData.getData();
		String role = "";
		String id = user.getRole().toString();
		for(int i=0;i<data.length;i++){
			String[] temp = data[i];
			if(temp[0].equals(id)){
				role = temp[1];
				break;
			}
		}
		grid.setText(4, 1, role);
		grid.setText(5, 0, "Email：");
		grid.setText(5, 1, user.getEmail());
		grid.setText(6, 0, "最后登录时间：");
		grid.setText(6, 1, Util.formatTimestampToString(user.getLastLoginTime()));
		grid.setText(7, 0, "最后登录地址：");
		grid.setText(7, 1, user.getLastLoginAddress());
		grid.setText(8, 0, "注册时间：");
		grid.setText(8, 1, Util.formatTimestampToString(user.getRegistTime()));
		mainPanel.add(grid);
	}
	
	public void show(){
		cancel.setText("关闭");
		submit.setVisible(false);
		super.show();
	}
}
