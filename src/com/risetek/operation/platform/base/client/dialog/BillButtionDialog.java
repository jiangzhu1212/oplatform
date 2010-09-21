package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.BillController;
import com.risetek.operation.platform.base.client.model.BillData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.BillConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

public class BillButtionDialog extends BaseButtonDailog {
	
	private Label BILL_ID_ZH = new Label(BillConstanst.BILL_ID_ZH);
	private Label TRADE_ID_ZH = new Label(BillConstanst.TRADE_ID_ZH);
	private Label CUSTOMER_ID_ZH = new Label(BillConstanst.CUSTOMER_ID_ZH);
	private Label ADDITION_ZH = new Label(BillConstanst.ADDITION_ZH);
	private Label VALIDITY_ZH = new Label(BillConstanst.VALIDITY_ZH);
	
	private TextBox BILL_ID = new TextBox();
	private TextBox TRADE_ID = new TextBox();
	private TextBox CUSTOMER_ID = new TextBox();
	private TextBox ADDITION = new TextBox();	
	private ListBox VALIDITY = Util.getValidity() ;

	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_BILL_INFO ;
		mainPanel.clear();
		setText("添加账单");
		gridFrame.resize(4, 2);
		gridFrame.setWidget(0, 0, TRADE_ID_ZH);
		gridFrame.setWidget(0, 1, TRADE_ID);
		gridFrame.setWidget(1, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(1, 1, CUSTOMER_ID);
		gridFrame.setWidget(2, 0, ADDITION_ZH);
		gridFrame.setWidget(2, 1, ADDITION);
		gridFrame.setWidget(3, 0, VALIDITY_ZH);
		gridFrame.setWidget(3, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_BILL_INFO ;
		mainPanel.clear();
		setText("查询账单");
		gridFrame.resize(5, 2);
		gridFrame.setWidget(0, 0, BILL_ID_ZH);
		gridFrame.setWidget(0, 1, BILL_ID);
		gridFrame.setWidget(1, 0, TRADE_ID_ZH);
		gridFrame.setWidget(1, 1, TRADE_ID);
		gridFrame.setWidget(2, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(2, 1, CUSTOMER_ID);
		gridFrame.setWidget(3, 0, ADDITION_ZH);
		gridFrame.setWidget(3, 1, ADDITION);
		gridFrame.setWidget(4, 0, VALIDITY_ZH);
		gridFrame.setWidget(4, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	@Override
	public void subminHandler() {
		String 	trade_id = TRADE_ID.getText() ;	
		String 	customer_id = CUSTOMER_ID.getText() ;	
		String 	addition = ADDITION.getText() ;	
		int validityIndex = VALIDITY.getSelectedIndex() ;
		String validity = VALIDITY.getValue(validityIndex);
		
		BillData billData = new BillData() ;
		billData.setACTION_NAME(ACTION_NAME);
		if(trade_id != null && !"".equals(trade_id)){
			billData.setTrade_id(Integer.parseInt(trade_id));
		}
		if(customer_id != null && !"".equals(customer_id)){
			billData.setCustomer_id(Integer.parseInt(customer_id));
		}
		billData.setAddition(addition);
		billData.setValidity(validity);
		
		if(Constanst.ACTION_NAME_ADD_BILL_INFO.equals(ACTION_NAME)){
			if(trade_id == null || "".equals(trade_id)){
				setMessage("交易编号不能为空");
				return ;
			}
			if(customer_id == null || "".equals(customer_id)){
				setMessage("用户编号不能为空");
				return ;
			}
			String jsonStr = billData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;
			request.getBill(packet, BillController.RemoteCaller);
			hide();
		}else if(Constanst.ACTION_NAME_QUERY_BILL_INFO.equals(ACTION_NAME)){
			String bill_id = BILL_ID.getText() ;
			if(bill_id != null && !"".equals(bill_id)){
				billData.setBill_id(Integer.parseInt(bill_id));
			}
			BillController.queryData = billData;
			String jsonStr = billData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;
			request.getBill(packet, BillController.QueryCaller);
			hide();
		}
	}
}
