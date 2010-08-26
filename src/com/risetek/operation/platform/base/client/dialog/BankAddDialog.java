package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

public class BankAddDialog extends CustomDialog {
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox bankNameBox = new TextBox();
	public final DateBox validityBox = new DateBox();
	public final TextBox descBox = new TextBox();
		
	public BankAddDialog(){
		Grid gridFrame = new Grid(4, 2);
		gridFrame.setWidget(0,0,new Label("发卡行代码值：",false));
		gridFrame.setWidget(1,0,new Label("发卡行名称：",false));
		gridFrame.setWidget(2,0,new Label("有效期：",false));
		gridFrame.setWidget(3,0,new Label("备注：",false));
		
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,validityBox);
		gridFrame.setWidget(3,1,descBox);
		
		bankCodeBox.setWidth("240px");
		bankNameBox.setWidth("240px");
		validityBox.setWidth("240px");
		descBox.setWidth("240px");
		
		mainPanel.add(gridFrame);
	}
	
	public void show(){
		setText("增加发卡行信息");
		setDescript("请输入新的发卡行信息：");
		super.show();
		bankCodeBox.setFocus(true);
	}

	public boolean isValid() {

		return true;
	}
}
