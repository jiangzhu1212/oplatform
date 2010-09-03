package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.entry.JCardConstanst;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 * 2010-9-2
 * 
 */
public class JCardQueryButtonDialog extends CustomDialog {
	
	private final Label SN_ZH = new Label(JCardConstanst.SN_ZH);
	private final Label NUMBER_ZH = new Label(JCardConstanst.NUMBER_ZH);
	private final Label PWD_ZH = new Label(JCardConstanst.PWD_ZH);
	private final Label PAR_VALUE_ZH = new Label(JCardConstanst.PAR_VALUE_ZH);
	private final Label STATUS_ZH = new Label(JCardConstanst.STATUS_ZH);
	private final Label BILL_EXTEND_ID_ZH = new Label(JCardConstanst.BILL_EXTEND_ID_ZH);
	private final Label CREATE_DATE_ZH = new Label(JCardConstanst.CREATE_DATE_ZH);
	
	private final TextBox SN = new MyTextBox();
	private final TextBox NUMBER = new MyTextBox();
	private final TextBox PWD = new MyTextBox();
	private final TextBox PAR_VALUE = new MyTextBox();
	private final TextBox BILL_EXTEND_ID = new MyTextBox();
	private final DateBox CREATE_DATE = new DateBox();
	
	public final ListBox list_status = new ListBox();
	
	private String action_name = "";
	private String trans_id = "";
	
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public JCardQueryButtonDialog() {
		createListStatus();
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
		CREATE_DATE.setFormat(new DateBox.DefaultFormat(format));
	}
	
	private void createListStatus(){
		list_status.addItem( "" , "" );
		list_status.addItem( "可用" , "free" );
		list_status.addItem( "锁定" , "lock" );
		list_status.addItem( "销售" , "sold" );
		list_status.addItem( "失败" , "fail" );
		list_status.addItem( "注销" , "cancel" );
		list_status.addItem( "无效" , "invalid" );
	}
	
	private void clearPanel(){
		SN.setValue(null);
		NUMBER.setValue(null);
		PWD.setValue(null);
		PAR_VALUE.setValue(null);
		list_status.setSelectedIndex(0);
	}
	
	public void addMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("添加商户");
		Grid gridFrame = new Grid(5, 2);
		gridFrame.setWidget(0, 0, SN_ZH);
		gridFrame.setWidget(0, 1, SN_ZH);
		gridFrame.setWidget(1, 0, NUMBER_ZH);
		gridFrame.setWidget(1, 1, NUMBER);
		gridFrame.setWidget(2, 0, PWD_ZH);
		gridFrame.setWidget(2, 1, PWD);
		gridFrame.setWidget(3, 0, PAR_VALUE_ZH);
		gridFrame.setWidget(3, 1, PAR_VALUE);
		gridFrame.setWidget(4, 0, STATUS_ZH);
		gridFrame.setWidget(4, 1, list_status);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("查询骏卡");
		Grid gridFrame = new Grid(7, 2);
		gridFrame.setWidget(0, 0, SN_ZH);
		gridFrame.setWidget(0, 1, SN);
		gridFrame.setWidget(1, 0, NUMBER_ZH);
		gridFrame.setWidget(1, 1, NUMBER);
		gridFrame.setWidget(2, 0, PWD_ZH);
		gridFrame.setWidget(2, 1, PWD);
		gridFrame.setWidget(3, 0, PAR_VALUE_ZH);
		gridFrame.setWidget(3, 1, PAR_VALUE);
		gridFrame.setWidget(4, 0, STATUS_ZH);
		gridFrame.setWidget(4, 1, list_status);	
		gridFrame.setWidget(5, 0, BILL_EXTEND_ID_ZH);
		gridFrame.setWidget(5, 1, BILL_EXTEND_ID);	
		gridFrame.setWidget(6, 0, CREATE_DATE_ZH);
		gridFrame.setWidget(6, 1, CREATE_DATE);	
		mainPanel.add(gridFrame);
		submit.setText("查询");
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	
	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
						
		}
	}
	
}
