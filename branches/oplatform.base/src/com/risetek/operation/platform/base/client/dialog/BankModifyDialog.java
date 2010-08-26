package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/** 
 * @ClassName: BankDialog 
 * @Description: 管理发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-26 下午04:37:57 
 * @version  1.0
 */
public class BankModifyDialog extends CustomDialog {
	
	protected Label oldValueLabel = new Label();
	
	public TextBox newValueBox = new TextBox();
	
	public DateBox dateBox = new DateBox();
	
	public String rowid;
	
	public String bankid;
	
	/**
	 * Description: 构造器
	 */
	public BankModifyDialog(String colName){
		if(null != colName){
			mainPanel.add(BankModifyClick(colName));
		}else{
			Label info = new Label("您确定删除该发卡行信息？");
			mainPanel.add(info);
		}
	}
	
	private Grid BankModifyClick(String titleMsg){
		setDescript("请输入新的"+titleMsg);
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setWidget(0, 0, new Label("当前"+titleMsg+"："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的"+titleMsg+"："));
		if(!titleMsg.equals(BankView.columns[2])){
			gridFrame.setWidget(1, 1, newValueBox);
			newValueBox.setWidth("180px");
			newValueBox.setTabIndex(1);
		}else{
			DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
			dateBox.setFormat(new DateBox.DefaultFormat(format));
			dateBox.setWidth("130px");
			dateBox.setTabIndex(1);
			gridFrame.setWidget(1, 1, dateBox);
		}
		return gridFrame;
	}
	
	/**
	 * @Description: 显示窗体 
	 * @param tips_id
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show(String tips_id, String tips_bankid, String tips_value) {
		rowid = tips_id;
		bankid = tips_bankid;
		setText("记录序号：" + tips_id);
		oldValueLabel.setText(tips_value);
		newValueBox.setText(tips_value);
		super.show();
		newValueBox.setFocus(true);
	}
	
	public void show_del(String tips_id, String tips_bankid, String tips_value) {
		rowid = tips_id;
		bankid = tips_bankid;
		setText("记录序号：" + tips_id);
		setDescript("发卡行代码值：" + tips_value);
		super.show();
	}
	
	/**
	 * @Description: 验证发卡行名称 
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
//		String check = FieldVerifier.validUserName(newNameBox.getText());
//		if( null != check ){
//			newNameBox.setFocus(true);
//			setMessage(check);
//			return false;
//		}
		return true;
	}
}