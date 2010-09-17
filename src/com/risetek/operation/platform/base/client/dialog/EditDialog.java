package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodsConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.TradeConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * gridPanel 删除 作废 修改 内容弹出窗口
 */
public class EditDialog extends CustomDialog {
	
	public Label  oldValueLabel = new Label();
	public DateBox DATE_BOX = new DateBox();
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
	public TextBox newValueBox = new TextBox();
	public String rowid;
	public String colName = null;
	public ListBox list_status = null;
	public static EditDialog INSTANCE = new EditDialog();
	
	
	private EditDialog(){
	//	setWidth("400px");
		oldValueLabel.setWidth("220px");
		newValueBox.setWidth("220px");
	}
	
	public void clearPanel(){
		DATE_BOX.setValue(null);
		newValueBox.setValue("");
	}
	
	public void makeMainPanel(String colName){
		mainPanel.clear();
		clearPanel();
		this.colName = colName;
		if(colName!=null){			
			Grid gridFrame = new Grid(2, 2);
			gridFrame.setStyleName("table");
			gridFrame.setWidth("320px");
			gridFrame.getColumnFormatter().setWidth(0, "80px");
			label.setText("请输入新的"+colName);
			gridFrame.setWidget(0, 0, new Label("当前"+colName+":"));
			gridFrame.setWidget(0, 1, oldValueLabel);
			gridFrame.setWidget(1, 0, new Label("新的"+colName+":"));
			makeNewBox(gridFrame);			
			newValueBox.setTabIndex(1);			
			mainPanel.add(gridFrame);
		}else {
			label.setText("");
			Label  info = new Label("您确定删除或作废该条信息？");
			mainPanel.add(info);
		}
	}
	
	private void makeNewBox(Grid gridFrame){
		if(CustomerConstanst.CREATE_TIME_ZH.equals(colName)){
			/**
			 * 如果是日期格式执行下面的方法
			 */
			DATE_BOX.setFormat(new DateBox.DefaultFormat(format));
			gridFrame.setWidget(1, 1, DATE_BOX);
		}else if(JCardConstanst.STATUS_ZH.equals(colName) ){
			list_status = Util.getJCardStatus();
			list_status.setSelectedIndex(0);
			gridFrame.setWidget(1, 1, list_status);
		}else if(Constanst.VALIDITY_ZH.equals(colName)){
			list_status = Util.getValidity();
			list_status.setSelectedIndex(0);
			gridFrame.setWidget(1, 1, list_status);
		}else if(TradeConstanst.STATUS_ZH.equals(colName) || EGoodsConstanst.STATUS_ZH.equals(colName)){
			list_status = Util.getBillStatus();
			list_status.setSelectedIndex(0);
			gridFrame.setWidget(1, 1, list_status);
		}else if(TradeConstanst.THIRD_STATUS_ZH.equals(colName) || EGoodsConstanst.THIRD_STATUS_ZH.equals(colName)){
			list_status = Util.getThirdStatus();
			list_status.setSelectedIndex(0);
			gridFrame.setWidget(1, 1, list_status);
		}else{
			gridFrame.setWidget(1, 1, newValueBox);
		}
	}
	
	public void show(String tips_id, String tips_imsi) {
		rowid = tips_id;
		/**
		 * 如果colName为空，表示为删除或作废
		 */
		if(colName == null){
			setText("行号" + tips_id);
		}else{
			setText("修改" + colName);
			oldValueLabel.setText(tips_imsi);	
			newValueBox.setFocus(true);
		}
		super.show();
	}
	
	public void setMessage(String message){
		super.setMessage(message);
	}
	
}
