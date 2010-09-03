package com.risetek.operation.platform.base.client.dialog;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.entry.CustomerConstanst;
import com.risetek.operation.platform.base.client.model.CustomerData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 *	customerView的button点击弹出窗口
 */
public class CustomerButtonDialog  extends CustomDialog {
	
	private final Label NAME_ZH = new Label(CustomerConstanst.NAME_ZH ,false);
	private final Label PHONE_ZH = new Label(CustomerConstanst.PHONE_ZH ,false);
	private final Label ADDRESS_ZH = new Label(CustomerConstanst.ADDRESS_ZH ,false);
	private final Label ADDRESS_2_ZH = new Label(CustomerConstanst.ADDRESS_2_ZH ,false);
	private final Label EMAIL_ZH = new Label(CustomerConstanst.EMAIL_ZH ,false);
	private final Label CARD_ID_ZH = new Label(CustomerConstanst.CARD_ID_ZH ,false);
	private final Label CREATE_TIME_ZH = new Label(CustomerConstanst.CREATE_TIME_ZH ,false);
	private final Label VALIDITY_ZH = new Label(CustomerConstanst.VALIDITY_ZH ,false);
	private final Label ADDITION_ZH = new Label(CustomerConstanst.ADDITION_ZH ,false);
	
	private final TextBox NAME = new MyTextBox();
	private final TextBox PHONE = new MyTextBox();
	private final TextBox ADDRESS = new MyTextBox();
	private final TextBox ADDRESS_2 = new MyTextBox();
	private final TextBox EMAIL = new MyTextBox();
	private final TextBox CARD_ID = new MyTextBox();
	private final DateBox CREATE_TIME = new DateBox();
	private final TextBox VALIDITY = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	
	ListBox list = new ListBox();
	
	private String action_name = "";
	private String customer_id = null;
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public CustomerButtonDialog(){
		
		list.addItem("");
		list.addItem("你好"+1);
		list.addItem("你好"+2);
		list.addItem("你好"+3);
		CREATE_TIME.setFormat(new DateBox.DefaultFormat(format));
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	private void clearPanel(){
		NAME.setValue(null);
		PHONE.setValue(null);
		ADDRESS.setValue(null);
		ADDRESS_2.setValue(null);
		EMAIL.setValue(null);
		CARD_ID.setValue(null);
		CREATE_TIME.setValue(null);
		VALIDITY.setValue(null);
		ADDITION.setValue(null);
	}
	
	/**
	 * 添加用户中mainPanel的具体显示
	 */
	public void addMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("添加用户");
		Grid gridFrame = new Grid(9, 2);
		gridFrame.setWidget(0, 0, NAME_ZH);
		gridFrame.setWidget(0, 1, NAME);
		gridFrame.setWidget(1, 0, PHONE_ZH);
		gridFrame.setWidget(1, 1, PHONE);
		gridFrame.setWidget(2, 0, ADDRESS_ZH);
		gridFrame.setWidget(2, 1, ADDRESS);
		gridFrame.setWidget(3, 0, ADDRESS_2_ZH);
		gridFrame.setWidget(3, 1, ADDRESS_2);
		gridFrame.setWidget(4, 0, EMAIL_ZH);
		gridFrame.setWidget(4, 1, EMAIL);
		gridFrame.setWidget(5, 0, CARD_ID_ZH);
		gridFrame.setWidget(5, 1, CARD_ID);
		gridFrame.setWidget(6, 0, CREATE_TIME_ZH);
		gridFrame.setWidget(6, 1, CREATE_TIME);
		gridFrame.setWidget(7, 0, VALIDITY_ZH);
		gridFrame.setWidget(7, 1, list);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	/**
	 * 查询用户中mainPanel的具体显示
	 */
	public void queryMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("查询用户");
		Grid gridFrame = new Grid(9, 2);
		gridFrame.setWidget(0, 0, NAME_ZH);
		gridFrame.setWidget(0, 1, NAME);
		gridFrame.setWidget(1, 0, PHONE_ZH);
		gridFrame.setWidget(1, 1, PHONE);
		gridFrame.setWidget(2, 0, ADDRESS_ZH);
		gridFrame.setWidget(2, 1, ADDRESS);
		gridFrame.setWidget(3, 0, ADDRESS_2_ZH);
		gridFrame.setWidget(3, 1, ADDRESS_2);
		gridFrame.setWidget(4, 0, EMAIL_ZH);
		gridFrame.setWidget(4, 1, EMAIL);
		gridFrame.setWidget(5, 0, CARD_ID_ZH);
		gridFrame.setWidget(5, 1, CARD_ID);
		gridFrame.setWidget(6, 0, CREATE_TIME_ZH);
		gridFrame.setWidget(6, 1, CREATE_TIME);
		gridFrame.setWidget(7, 0, VALIDITY_ZH);
		gridFrame.setWidget(7, 1, VALIDITY);
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

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	

	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			String name = NAME.getText();
			String phone = PHONE.getText();
			String address = ADDRESS.getText();
			String address2 = ADDRESS_2.getText();
			String email = EMAIL.getText();
			String cardId = CARD_ID.getText();
			Date createTime = CREATE_TIME.getValue();
			String createDate = "";
			if(createTime != null){
				createDate = format.format(createTime);
			}
			
			String validity = VALIDITY.getText();
			String addition = ADDITION.getText();
			int selectIndex = list.getSelectedIndex();
			String validaty0 =list.getValue(selectIndex);
			CustomerData customer = new CustomerData();
			customer.setName(name);
			customer.setPhone(phone);
			customer.setAddress(address);
			customer.setAddress_2(address2);
			customer.setEmail(email);
			customer.setCard_id(cardId);
			customer.setCreate_time(createDate);
//			customer.setValidity(validity);
			customer.setAddition(addition);
			
		}
	}

	
}
