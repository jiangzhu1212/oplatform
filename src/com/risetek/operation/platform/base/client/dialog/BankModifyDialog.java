package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.BankConstanst;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: BankDialog 
 * @Description: 管理发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-26 下午04:37:57 
 * @version  1.0
 */
public class BankModifyDialog extends CustomDialog implements ChangeHandler {  
	
	protected Label oldValueLabel = new Label();
	
	public TextBox newValueBox = new TextBox();
	
	public ListBox validityBox = new ListBox();
	
	private String[] listBoxValue = {"有效", "无效", "已注销", "已挂失"};
	
	public String validityValue = listBoxValue[0];
			
	private String colName;
	
	/**
	 * Description: 构造器
	 */
	public BankModifyDialog(String colName) {
		this.colName = colName;
		if (null != colName) {
			mainPanel.add(BankModifyClick(colName));
		} else {
			Label info = new Label("您确定删除该发卡行信息？");
			mainPanel.setWidth("260px");
			mainPanel.add(info);
		}

		validityBox.addChangeHandler(this);
		for (int i = 0; i < listBoxValue.length; i++) {
			validityBox.addItem(listBoxValue[i]);
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		int index = validityBox.getSelectedIndex();
		validityValue = listBoxValue[index];
	}
	
	/**
	 * @Description: 处理修改操作
	 * @param titleMsg
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid BankModifyClick(String titleMsg){
		setDescript("请输入新的"+titleMsg);
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setStyleName("notice");
		gridFrame.setWidget(0, 0, new Label("当前"+titleMsg+"："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的"+titleMsg+"："));
		
		//判断是否是日期文本框
		if(!titleMsg.equals(BankView.columns[2])){
			gridFrame.setWidget(1, 1, newValueBox);
			newValueBox.setWidth("200px");
			newValueBox.setTabIndex(1);
		}else{
			validityBox.setWidth("100px");
			gridFrame.setWidget(1, 1, validityBox);
		}
		return gridFrame;
	}
	
	/**
	 * @Description: 显示窗体 
	 * @param tips_id
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show(String tips_id, String tips_value) {
		setText("记录序号：" + tips_id);
		if (null != colName) {
			oldValueLabel.setText(tips_value);
			newValueBox.setText(tips_value);
			super.show();
			newValueBox.setFocus(true);
		} else {
			setDescript("发卡行代码值：" + tips_value);
			super.show();
		}
	}
	
	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		//colum为null,表示执行的是删除操作
		if (null == colName) {
			return true;
		}
		
		String check = Util.commValidity((newValueBox.getText()).trim(), BankConstanst.BANK_NAME_ZH);
		if (null != check) {
			setMessage(check);
			newValueBox.setFocus(true);
			return false;
		}
		return true;
	}
}