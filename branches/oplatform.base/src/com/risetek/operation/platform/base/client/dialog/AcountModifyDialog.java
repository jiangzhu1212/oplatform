package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.constanst.AcountConstanst;
import com.risetek.operation.platform.base.client.view.AcountView;
import com.risetek.operation.platform.base.shared.FieldVerifier;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/** 
 * @ClassName: AcountModifyDialog 
 * @Description: 管理银行卡信息事件实体类
 * @author JZJ 
 * @date 2010-8-27 上午09:56:06 
 * @version  1.0
 */
public class AcountModifyDialog extends CustomDialog {

	
	protected Label oldValueLabel = new Label();
	
	public TextBox newValueBox = new TextBox();
	
	public DateBox dateBox = new DateBox();
	
	public String rowid;
	
	public String keyid;
	
	private String colum;
	
	/**
	 * Description: 构造器
	 */
	public AcountModifyDialog(String colName){
		this.colum = colName;
		if(null != colName){
			mainPanel.add(AcountModifyClick(colName));
		}else{
			Label info = new Label("您确定删除该行卡列表信息？");
			mainPanel.setWidth("260px");
			mainPanel.add(info);
		}
	}
	
	/**
	 * @Description: 处理修改操作 
	 * @param titleMsg
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid AcountModifyClick(String titleMsg){
		setDescript("请输入新的"+titleMsg);
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setStyleName("notice");
		gridFrame.setWidget(0, 0, new Label("当前"+titleMsg+"："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的"+titleMsg+"："));
		if(!titleMsg.equals(AcountView.columns[2])){
			gridFrame.setWidget(1, 1, newValueBox);
			newValueBox.setWidth("240px");
			newValueBox.setTabIndex(1);
		}else{
			DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
			dateBox.setFormat(new DateBox.DefaultFormat(format));
			dateBox.setWidth("240px");
			dateBox.setTabIndex(1);
			gridFrame.setWidget(1, 1, dateBox);
		}
		return gridFrame;
	}
	
	/**
	 * @Description: 显示窗体 (修改操作) 
	 * @param tips_id
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show(String tips_id, String tips_keyid, String tips_value) {
		rowid = tips_id;
		keyid = tips_keyid;
		setText("记录序号：" + tips_id);
		oldValueLabel.setText(tips_value);
		newValueBox.setText(tips_value);
		super.show();
		newValueBox.setFocus(true);
	}
	
	/**
	 * @Description: 显示窗体 (删除操作) 
	 * @param tips_id
	 * @param tips_keyid
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show_del(String tips_id, String tips_keyid, String tips_value) {
		rowid = tips_id;
		keyid = tips_keyid;
		setText("记录序号：" + tips_id);
		setDescript("卡列表编号：" + tips_value);
		super.show();
	}
	
	/**
	 * @Description: 验证 
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		//colum为null,表示执行的是删除操作
		if (null == colum) {
			return true;
		}
		
		String check = null;
		if(colum.equals(AcountView.columns[1])){
			check = FieldVerifier.commValidity((newValueBox.getText()).trim(), AcountConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				newValueBox.setFocus(true);
				return false;
			}
		}
		if(colum.equals(AcountView.columns[2])){
			check = FieldVerifier.commValidity((dateBox.getTextBox().getText()).trim(), AcountConstanst.ACCOUNT_VALIDITY_ZH);
			if (null != check) {
				setMessage(check);
				dateBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}