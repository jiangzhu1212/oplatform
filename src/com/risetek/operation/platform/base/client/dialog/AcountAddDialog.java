package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.AcountConstanst;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: AcountAddDialog 
 * @Description: 增加银行卡信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午09:40:05 
 * @version 1.0
 */
public class AcountAddDialog extends CustomDialog implements ChangeHandler {   
	
	public final TextBox numberBox = new TextBox();
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox addtionBox = new TextBox();
	public final ListBox validityBox = new ListBox();
	public final TextBox descBox = new TextBox();

	private Grid gridFrame = new Grid();
	
	private boolean isSearch;// true 表示查询，false 表示增加
	
	private String[] listBoxValue = {"有效", "无效", "已注销", "已挂失"};

	public String validityValue = listBoxValue[0];
	
	/**
	 * Description: 构造器
	 */
	public AcountAddDialog(boolean isSearch) {
		this.isSearch = isSearch;
		if (isSearch) {
			gridFrame.resize(3, 2);
			mainPanel.add(searchAccount());
		} else {
			gridFrame.resize(5, 2);
			mainPanel.add(addAccount());
		}

		gridFrame.setStyleName("notice");
		validityBox.addChangeHandler(this);
		for (int i = 0; i < listBoxValue.length; i++) {
			validityBox.addItem(listBoxValue[i]);
		}
		initWith();
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		int index = validityBox.getSelectedIndex();
		validityValue = listBoxValue[index];
	}
	
	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addAccount(){	
		gridFrame.setWidget(0,0,new Label(AcountConstanst.ACCOUNT_NUMBER_ZH + "："));
		gridFrame.setWidget(1,0,new Label(AcountConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(2,0,new Label(AcountConstanst.ACCOUNT_VALIDITY_ZH + "："));
		gridFrame.setWidget(3,0,new Label(AcountConstanst.ACCOUNT_ADDTION_ZH + "："));
		gridFrame.setWidget(4,0,new Label(AcountConstanst.ACCOUNT_DESCRIPTION_ZH + "："));
		
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		gridFrame.setWidget(2,1,validityBox);
		gridFrame.setWidget(3,1,addtionBox);
		gridFrame.setWidget(4,1,descBox);
						
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private Grid searchAccount(){
		gridFrame.setWidget(0,0,new Label(AcountConstanst.ACCOUNT_NUMBER_ZH + "："));
		gridFrame.setWidget(1,0,new Label(AcountConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(2,0,new Label(AcountConstanst.ACCOUNT_VALIDITY_ZH + "："));
		
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		gridFrame.setWidget(2,1,validityBox);
		
		return gridFrame;
	}
	
	/**
	 * @Description: 设置文本框宽度
	 * @return void 返回类型
	 */
	private void initWith() {
		bankCodeBox.setWidth("200px");
		numberBox.setWidth("200px");
		addtionBox.setWidth("200px");
		descBox.setWidth("200px");
		validityBox.setWidth("100px");
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show() {
		if (isSearch) {
			setText("查询银行卡信息");
			setDescript("请输入查询信息");
		} else {
			setText("增加银行卡信息");
			setDescript("请输入新的银行卡信息");
		}
		super.show();
		numberBox.setFocus(true);
	}

	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		String check;
		if (!isSearch) {//如果isSearch为true表示查询操作
			check = Util.commValidity((numberBox.getText()).trim(), AcountConstanst.ACCOUNT_NUMBER_ZH);
			if (null != check) {
				setMessage(check);
				numberBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((bankCodeBox.getText()).trim(), AcountConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				bankCodeBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}
