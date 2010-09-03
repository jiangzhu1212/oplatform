package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.json.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: BankAddDialog 
 * @Description: 增加发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:16:14 
 * @version 1.0
 */
public class BankAddDialog extends BaseDialog {   
	
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox bankNameBox = new TextBox();
	public final TextBox descBox = new TextBox();

	private String[] addColumns = { 
		BankConstanst.BANK_CODE_ZH,
		BankConstanst.BANK_NAME_ZH, 
		BankConstanst.BANK_DESCRIPTION_ZH,
		BankConstanst.BANK_VALIDITY_ZH
	};
	
	private String[] searchColumns = { 
		BankConstanst.BANK_CODE_ZH,
		BankConstanst.BANK_NAME_ZH, 
		BankConstanst.BANK_VALIDITY_ZH
	};
	
	/**
	 * Description: 构造器
	 */
	public BankAddDialog(String processTag) {
		super.processTag = processTag;
		if (null == processTag) {
			mainPanel.add(addBank());
		} else {
			mainPanel.add(searchBank());
		}
		
		initWith();
	}

	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addBank(){	
		gridFrame.resize(4, 2);
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,descBox);
		gridFrame.setWidget(3,1,createListBox(bankListBoxValue));
		setText("增加发卡行信息");
		setDescript("请输入新的发卡行信息");
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private Grid searchBank(){
		gridFrame.resize(3, 2);
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,createListBox(bankListBoxValue));
		setText("查询发卡行信息");
		setDescript("请输入查询信息");
		return gridFrame;
	}
	
	/**
	 * @Description: 设置文本框宽度
	 * @return void 返回类型
	 */
	private void initWith(){
		bankCodeBox.setWidth("200px");
		bankNameBox.setWidth("200px");
		descBox.setWidth("200px");
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		super.show();
		bankCodeBox.setFocus(true);
	}

	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		String check;
		if (null == processTag) {
			check = Util.commValidity((bankCodeBox.getText()).trim(), BankConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				bankCodeBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((bankNameBox.getText()).trim(), BankConstanst.BANK_NAME_ZH);
			if (null != check) {
				setMessage(check);
				bankNameBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}
