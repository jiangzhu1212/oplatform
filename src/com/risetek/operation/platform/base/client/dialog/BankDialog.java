package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/** 
 * @ClassName: BankDialog 
 * @Description: 管理发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-26 下午04:37:57 
 * @version  1.0
 */
public class BankDialog extends CustomDialog {
	
	protected Label  oldValueLabel = new Label();
	public TextBox newNameBox = new TextBox();
	public String rowid;
	
	/**
	 * Description: 构造器
	 */
	public BankDialog(){
		mainPanel.setWidth("450px");
		setDescript("请输入新的发卡行名称");
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setWidget(0, 0, new Label("当前发卡行名称：", false));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的发卡行名称：", false));
		gridFrame.setWidget(1, 1, newNameBox);
		newNameBox.setWidth("240px");
		newNameBox.setTabIndex(1);
		mainPanel.add(gridFrame);

	}
	/**
	 * @Description: 显示窗体 
	 * @param tips_id
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show(String tips_id, String tips_value) {
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		oldValueLabel.setText(tips_value);
		newNameBox.setText(tips_value);
		super.show();
		newNameBox.setFocus(true);
	}
	
	/**
	 * @Description: 验证发卡行名称 
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		return true;
	}
}