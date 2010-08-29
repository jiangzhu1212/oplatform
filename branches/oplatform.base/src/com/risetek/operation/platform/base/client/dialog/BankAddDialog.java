package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: BankAddDialog 
 * @Description: 增加发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:16:14 
 * @version 1.0
 */
public class BankAddDialog extends CustomDialog implements ChangeHandler {  
	
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox bankNameBox = new TextBox();
	public final ListBox validityBox = new ListBox();
	public final TextBox descBox = new TextBox();
	
	private Grid gridFrame = new Grid();
	
	private boolean isSearch; // true 表示查询，false 表示增加
	
	private String[] listBoxValue = {"有效", "无效", "已注销", "已挂失"};
	
	public String validityValue = listBoxValue[0];
	
	/**
	 * Description: 构造器
	 */
	public BankAddDialog(boolean isSearch) {
		this.isSearch = isSearch;
		if (isSearch) {
			gridFrame.resize(3, 2);
			mainPanel.add(searchBank());
		} else {
			gridFrame.resize(4, 2);
			mainPanel.add(addBank());
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
	private Grid addBank(){		
		gridFrame.setWidget(0,0,new Label(BankConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(1,0,new Label(BankConstanst.BANK_NAME_ZH + "："));
		gridFrame.setWidget(2,0,new Label(BankConstanst.BANK_DESCRIPTION_ZH + "："));
		gridFrame.setWidget(3,0,new Label(BankConstanst.BANK_VALIDITY_ZH + "："));
				
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,descBox);
		gridFrame.setWidget(3,1,validityBox);
		
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private Grid searchBank(){
		gridFrame.setWidget(0,0,new Label(BankConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(1,0,new Label(BankConstanst.BANK_NAME_ZH + "："));
		gridFrame.setWidget(2,0,new Label(BankConstanst.BANK_VALIDITY_ZH + "："));
		
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,validityBox);
		
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
		validityBox.setWidth("100px");
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
			check = Util.commValidity((bankNameBox.getText()).trim(), BankConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				bankNameBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}
