package com.risetek.operation.platform.log.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class FilterLogDialog extends CustomDialog {

	private TextBox value = new TextBox();
	
	public FilterLogDialog(){
		setText("过滤日志");
		setTitle("过滤日志");
		setDescript("根据输入的关键字过滤日志");
		Grid grid = new Grid(2, 2);
		grid.setWidth("340px");
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "80px");
		grid.getColumnFormatter().setWidth(1, "260px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.setText(0, 0, "过滤条件：");
		grid.setWidget(0, 1, value);
		grid.setText(1, 1, "若不输入任何内容，则显示全部日志。");
		mainPanel.add(grid);
	}
	
	public void show(){
		submit.setText("确定");
		super.show();
	}
	
	public String getValue(){
		return value.getValue();
	}
	
}
