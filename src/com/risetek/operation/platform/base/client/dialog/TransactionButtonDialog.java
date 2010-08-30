package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.entry.TransactionConstanst;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 * transActionView button按钮事件
 */
public class TransactionButtonDialog extends CustomDialog {
	
	private final Label ALIAS_ZH = new Label(TransactionConstanst.ALIAS_ZH);
	private final Label NAME_ZH = new Label(TransactionConstanst.NAME_ZH);
	private final Label DESCRIPTION_ZH = new Label(TransactionConstanst.DESCRIPTION_ZH);
	private final Label URL_ZH = new Label(TransactionConstanst.URL_ZH);
	private final Label BINDABLE_ZH = new Label(TransactionConstanst.BINDABLE_ZH);
	private final Label MERCHANT_NUMBER_ZH = new Label(TransactionConstanst.MERCHANT_NUMBER_ZH);
	private final Label POS_NUMBER_ZH = new Label(TransactionConstanst.POS_NUMBER_ZH);
	private final Label TYPE_ZH = new Label(TransactionConstanst.TYPE_ZH);
	private final Label ADDITION_ZH = new Label(TransactionConstanst.ADDITION_ZH);
	
	private final TextBox ALIAS = new MyTextBox();
	private final TextBox NAME = new MyTextBox();
	private final TextBox DESCRIPTION = new MyTextBox();
	private final TextBox URL = new MyTextBox();
	private final TextBox MERCHANT_NUMBER = new MyTextBox();
	private final TextBox POS_NUMBER = new MyTextBox();
	private final TextBox TYPE = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	
	private final RadioButton bindRadio0 = new RadioButton("bind","是");
	private final RadioButton bindRadio1 = new RadioButton("bind","否");
	
	private final HorizontalPanel BINDABLE = new HorizontalPanel();
	
	private String action_name = "";
	private String trans_id = "";
	
	public TransactionButtonDialog() {
		BINDABLE.add(bindRadio0);
		BINDABLE.add(bindRadio1);
		bindRadio1.setValue(true);
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	public void addMainPanel(){
		mainPanel.clear();
		setText("添加商户");
		Grid gridFrame = new Grid(9, 2);
		gridFrame.setWidget(0, 0, ALIAS_ZH);
		gridFrame.setWidget(0, 1, ALIAS);
		gridFrame.setWidget(1, 0, NAME_ZH);
		gridFrame.setWidget(1, 1, NAME);
		gridFrame.setWidget(2, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(2, 1, DESCRIPTION);
		gridFrame.setWidget(3, 0, URL_ZH);
		gridFrame.setWidget(3, 1, URL);
		gridFrame.setWidget(4, 0, BINDABLE_ZH);
		gridFrame.setWidget(4, 1, BINDABLE);
		gridFrame.setWidget(5, 0, MERCHANT_NUMBER_ZH);
		gridFrame.setWidget(5, 1, MERCHANT_NUMBER);
		gridFrame.setWidget(6, 0, POS_NUMBER_ZH);
		gridFrame.setWidget(6, 1, POS_NUMBER);
		gridFrame.setWidget(7, 0, TYPE_ZH);
		gridFrame.setWidget(7, 1, TYPE);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		setText("查询商户");
		Grid gridFrame = new Grid(9, 2);
		gridFrame.setWidget(0, 0, ALIAS_ZH);
		gridFrame.setWidget(0, 1, ALIAS);
		gridFrame.setWidget(1, 0, NAME_ZH);
		gridFrame.setWidget(1, 1, NAME);
		gridFrame.setWidget(2, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(2, 1, DESCRIPTION);
		gridFrame.setWidget(3, 0, URL_ZH);
		gridFrame.setWidget(3, 1, URL);
		gridFrame.setWidget(4, 0, BINDABLE_ZH);
		gridFrame.setWidget(4, 1, BINDABLE);
		gridFrame.setWidget(5, 0, MERCHANT_NUMBER_ZH);
		gridFrame.setWidget(5, 1, MERCHANT_NUMBER);
		gridFrame.setWidget(6, 0, POS_NUMBER_ZH);
		gridFrame.setWidget(6, 1, POS_NUMBER);
		gridFrame.setWidget(7, 0, TYPE_ZH);
		gridFrame.setWidget(7, 1, TYPE);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);	
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
			String alias = ALIAS.getText();
			String name = NAME.getText();			
			String description = DESCRIPTION.getText();
			String url = URL.getText();
			
			String merchant_number = MERCHANT_NUMBER.getText();
			String pos_number = POS_NUMBER.getText();
			String type = TYPE.getText();
			String addition = ADDITION.getText();
			
			TransactionData transaction = new TransactionData();
			transaction.setAlias(alias);
			transaction.setName(name);
			transaction.setDescription(description);
			transaction.setUrl(url);
			if(bindRadio0.getValue()){
				transaction.setBindable("true");
			}else{
				transaction.setBindable("false");
			}
			
			transaction.setMerchant_number(merchant_number);
			transaction.setPos_number(pos_number);
			transaction.setType(type);
			transaction.setAddition(addition);
			
		}
	}
	
}
