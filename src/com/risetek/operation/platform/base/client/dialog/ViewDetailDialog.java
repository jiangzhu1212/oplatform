package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class ViewDetailDialog extends CustomDialog {
	
	public ViewDetailDialog(Grid grid , int row) {
		// TODO Auto-generated constructor stub
		setWidth("300px");
		setText("详细信息");
		int colCount = grid.getColumnCount();
		if(colCount < 3){
			Window.alert("错误");
		}
		Grid gridFrame = new Grid(colCount-2,2);
		for(int i = 0 ; i < colCount-2 ; i++){
			gridFrame.setWidget(i, 0, new Label(grid.getText(0, i+2)));
			gridFrame.setWidget(i, 1, new Label(grid.getText(row, i+2)));
		}
		mainPanel.add(gridFrame);
		submit.setVisible(false);
	}
}
