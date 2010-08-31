package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.AcountConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: AcountAddDialog 
 * @Description: 增加银行卡信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午09:40:05 
 * @version 1.0
 */
public class AcountAddDialog extends BaseDialog {   
	
	public final TextBox numberBox = new TextBox();
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox addtionBox = new TextBox();
	public final TextBox descBox = new TextBox();
	
	private String[] addColumns = {
		AcountConstanst.ACCOUNT_NUMBER_ZH, 
		AcountConstanst.BANK_CODE_ZH,
		AcountConstanst.ACCOUNT_ADDTION_ZH,
		AcountConstanst.ACCOUNT_DESCRIPTION_ZH,
		AcountConstanst.ACCOUNT_VALIDITY_ZH
	};
	
	private String[] searchColumns = {
		AcountConstanst.ACCOUNT_NUMBER_ZH, 
		AcountConstanst.BANK_CODE_ZH,
		AcountConstanst.ACCOUNT_VALIDITY_ZH
	};
	
	/**
	 * Description: 构造器
	 */
	public AcountAddDialog(boolean isSearch) {
		super.isSearch = isSearch;
		if (isSearch) {
			mainPanel.add(searchAccount());
		} else {
			mainPanel.add(addAccount());
		}
		initWith();
	}
	
	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addAccount(){	
		gridFrame.resize(5, 2);
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		gridFrame.setWidget(2,1,addtionBox);
		gridFrame.setWidget(3,1,descBox);
		gridFrame.setWidget(4,1,createListBox(bankListBoxValue));
		setText("增加银行卡信息");
		setDescript("请输入新的银行卡信息");
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private Grid searchAccount(){
		gridFrame.resize(3, 2);
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		gridFrame.setWidget(2,1,createListBox(bankListBoxValue));
		setText("查询银行卡信息");
		setDescript("请输入查询信息");
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
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show() {
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
