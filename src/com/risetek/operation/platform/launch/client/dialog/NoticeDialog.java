package com.risetek.operation.platform.launch.client.dialog;

import com.google.gwt.user.client.ui.Grid;

public class NoticeDialog extends CustomDialog {

	public NoticeDialog(String notice){
		Grid grid = new Grid(1, 1);
		grid.setText(0, 0, notice);
		grid.setWidth("300px");
		grid.getCellFormatter().setStyleName(0, 0, "notice");
		mainPanel.add(grid);
		setText("通知公告");
		setDescript("查看通知公告详细内容");
	}
	
	public void show(){
		cancel.setText("关闭");
		submit.setVisible(false);
		super.show();
	}
}
