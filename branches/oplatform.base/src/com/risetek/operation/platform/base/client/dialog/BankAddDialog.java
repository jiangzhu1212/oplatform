package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.BankConstanst;
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
	
	private String[] columns1 = { 
		BankConstanst.BANK_CODE_ZH,
		BankConstanst.BANK_NAME_ZH, 
		BankConstanst.BANK_DESCRIPTION_ZH,
		BankConstanst.BANK_VALIDITY_ZH
	};
	
	private String[] columns2 = { 
		BankConstanst.BANK_CODE_ZH,
		BankConstanst.BANK_NAME_ZH, 
		BankConstanst.BANK_VALIDITY_ZH
	};
	
	/**
	 * Description: 构造器
	 */
	public BankAddDialog(boolean isSearch) {
		super(true);
		super.isSearch = isSearch;
		gridFrame.setStyleName("notice");

		if (isSearch) {
			mainPanel.add(searchBank());
		} else {
			mainPanel.add(addBank());
		}
	
//		message.setStyleName("tableMessagePanel-content-action");
		initWith();
	}

	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addBank(){	
		gridFrame.resize(4, 2);
		formatGrid(gridFrame, columns1);
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,descBox);
		gridFrame.setWidget(3,1,listBox);
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private Grid searchBank(){
		gridFrame.resize(3, 2);
		formatGrid(gridFrame, columns2);
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,listBox);
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
		listBox.setWidth("100px");
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		if(isSearch){
			setText("查询发卡行信息");
			setDescript("请输入查询信息");
		}else{
			setText("增加发卡行信息");
			setDescript("请输入新的发卡行信息");
		}
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
		if (!isSearch) {//如果isSearch为true表示查询操作
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
