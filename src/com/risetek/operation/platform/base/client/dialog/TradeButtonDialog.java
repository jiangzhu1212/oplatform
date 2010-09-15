package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.TradeController;
import com.risetek.operation.platform.base.client.model.TradeData;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TradeConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

public class TradeButtonDialog extends BaseButtonDailog {
	
	private Label TRADE_ID_ZH = new Label(TradeConstanst.TRADE_ID_ZH);
	private Label CUSTOMER_ID_ZH = new Label(TradeConstanst.CUSTOMER_ID_ZH);
	private Label TRANS_ID_ZH = new Label(TradeConstanst.TRANS_ID_ZH);
	private Label AMOUNT_ZH = new Label(TradeConstanst.AMOUNT_ZH);
	private Label STATUS_ZH = new Label(TradeConstanst.STATUS_ZH);
	private Label THIRD_STATUS_ZH = new Label(TradeConstanst.THIRD_STATUS_ZH);
	private Label CREATE_TIME_ZH = new Label(TradeConstanst.CREATE_TIME_ZH);
	private Label BOLISH_TIME_ZH = new Label(TradeConstanst.BOLISH_TIME_ZH);
	private Label DESCRIPTION_ZH = new Label(TradeConstanst.DESCRIPTION_ZH);
	private Label ADDITION_ZH = new Label(TradeConstanst.ADDITION_ZH);
	private Label VALIDITY_ZH = new Label(TradeConstanst.VALIDITY_ZH);
	
	private TextBox TRADE_ID = new TextBox();
	private TextBox CUSTOMER_ID = new TextBox();
	private TextBox TRANS_ID = new TextBox();
	private TextBox AMOUNT = new TextBox();
	private TextBox DESCRIPTION = new TextBox();
	private TextBox ADDITION = new TextBox();
	
	private ListBox STATUS = Util.getBillStatus() ;
	private ListBox THIRD_STATUS = Util.getThirdStatus() ;
	private ListBox VALIDITY = Util.getValidity() ;
	
	private DateBox CREATE_TIME = new DateBox();
	private DateBox CREATE_TIME_MIN = new DateBox();
	private DateBox CREATE_TIME_MAX = new DateBox();
	private DateBox BOLISH_TIME = new DateBox();
	private DateBox BOLISH_TIME_MIN = new DateBox();
	private DateBox BOLISH_TIME_MAX = new DateBox();
	
	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_TRADE_INFO ;
		mainPanel.clear();
		setText("添加交易");
		gridFrame.resize(10, 2);
		gridFrame.setWidget(0, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(0, 1, CUSTOMER_ID);
		gridFrame.setWidget(1, 0, TRANS_ID_ZH);
		gridFrame.setWidget(1, 1, TRANS_ID);
		gridFrame.setWidget(2, 0, AMOUNT_ZH);
		gridFrame.setWidget(2, 1, AMOUNT);
		gridFrame.setWidget(3, 0, STATUS_ZH);
		gridFrame.setWidget(3, 1, STATUS);
		gridFrame.setWidget(4, 0, THIRD_STATUS_ZH);
		gridFrame.setWidget(4, 1, THIRD_STATUS);
		gridFrame.setWidget(5, 0, CREATE_TIME_ZH);
		gridFrame.setWidget(5, 1, CREATE_TIME);
		gridFrame.setWidget(6, 0, BOLISH_TIME_ZH);
		gridFrame.setWidget(6, 1, BOLISH_TIME);
		gridFrame.setWidget(7, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(7, 1, DESCRIPTION);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);
		gridFrame.setWidget(9, 0, VALIDITY_ZH);
		gridFrame.setWidget(9, 1, VALIDITY);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_TRADE_INFO ;
		mainPanel.clear();
		setText("查询交易");
		gridFrame.resize(9, 2);
		gridFrame.setWidget(0, 0, TRADE_ID_ZH);
		gridFrame.setWidget(0, 1, TRADE_ID);
		gridFrame.setWidget(1, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(1, 1, CUSTOMER_ID);
		gridFrame.setWidget(2, 0, TRANS_ID_ZH);
		gridFrame.setWidget(2, 1, TRANS_ID);
		gridFrame.setWidget(3, 0, AMOUNT_ZH);
		gridFrame.setWidget(3, 1, AMOUNT);
		gridFrame.setWidget(4, 0, STATUS_ZH);
		gridFrame.setWidget(4, 1, STATUS);
		gridFrame.setWidget(5, 0, THIRD_STATUS_ZH);
		gridFrame.setWidget(5, 1, THIRD_STATUS);
		gridFrame.setWidget(6, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(6, 1, DESCRIPTION);
		gridFrame.setWidget(7, 0, ADDITION_ZH);
		gridFrame.setWidget(7, 1, ADDITION);
		gridFrame.setWidget(8, 0, VALIDITY_ZH);
		gridFrame.setWidget(8, 1, VALIDITY);	
		mainPanel.add(gridFrame);
		mainPanel.add(Util.createDatePanel(CREATE_TIME_MIN, CREATE_TIME_MAX, TradeConstanst.CREATE_TIME_ZH));
		mainPanel.add(Util.createDatePanel(BOLISH_TIME_MIN, BOLISH_TIME_MAX, TradeConstanst.BOLISH_TIME_ZH));
		submit.setText("查询");
		show();
	}

	@Override
	public void subminHandler() {
		
		String customer_id = CUSTOMER_ID.getText();
		String trans_id = TRANS_ID.getText();
		String addition = ADDITION.getText();
		String amount = AMOUNT.getText();
		String description = DESCRIPTION.getText();
		
		int statusIndex = STATUS.getSelectedIndex() ;
		String status = STATUS.getValue(statusIndex);
		
		int third_status_index = THIRD_STATUS.getSelectedIndex() ;
		String third_status = THIRD_STATUS.getValue(third_status_index);
		
		int validityIndex = VALIDITY.getSelectedIndex() ;
		String validity = VALIDITY.getValue(validityIndex);
		
		TradeData tradeData = new TradeData() ;
		tradeData.setACTION_NAME(ACTION_NAME);
		if(customer_id != null && !"".equals(customer_id)){
			tradeData.setCustomer_id(Integer.parseInt(customer_id));
		}
		if(trans_id != null && !"".equals(trans_id)){
			tradeData.setTrans_id(Integer.parseInt(trans_id));
		}
		tradeData.setAddition(addition);
		tradeData.setAmount(amount);
		tradeData.setDescription(description);
		tradeData.setStatus(status);
		tradeData.setThird_status(third_status);
		tradeData.setValidity(validity);
		
		if(Constanst.ACTION_NAME_ADD_TRADE_INFO.equals(ACTION_NAME)){
			if(customer_id == null && "".equals(customer_id)){
				setMessage("客户编号不能为空");
				return ;
			}
			if(trans_id == null && "".equals(trans_id)){
				setMessage("业务编号不能为空");
				return ;
			}
			if(amount == null && "".equals(amount)){
				setMessage("金额不能为空");
				return ;
			}
			if(statusIndex == 0){
				setMessage("交易状态不能为空");
				return ;
			}
			if(third_status_index == 0){
				setMessage("第三方状态不能为空");
				return ;
			}
			String create_time = Util.formatDateToJsonString(CREATE_TIME.getValue());
			String bolish_time = Util.formatDateToJsonString(BOLISH_TIME.getValue());
			if(create_time == null && "".equals(create_time)){
				setMessage("创建时间不能为空");
				return ;
			}
			if(create_time == null && "".equals(create_time)){
				setMessage("失效时间不能为空");
				return ;
			}
			tradeData.setCreate_time(create_time);
			tradeData.setBolish_time(bolish_time);
			
			String packet = tradeData.toHttpPacket();
			request.getBill(packet, TradeController.RemoteCaller);
			hide();
		}else if(Constanst.ACTION_NAME_QUERY_TRADE_INFO.equals(ACTION_NAME)){
			String trade_id = TRADE_ID.getText();
			if(trade_id != null && !"".equals(trade_id)){
				tradeData.setTrans_id(Integer.parseInt(trans_id));
			}
			String create_time_min = Util.formatDateToJsonString(CREATE_TIME_MIN.getValue());
			String create_time_max = Util.formatDateToJsonString(CREATE_TIME_MAX.getValue());
			String bolish_time_min = Util.formatDateToJsonString(BOLISH_TIME_MIN.getValue());
			String bolish_time_max = Util.formatDateToJsonString(BOLISH_TIME_MAX.getValue());
			
			if(!Util.checkStringBulk(create_time_min, create_time_max)){
				setMessage("创建时间最小值不能大于最大值");
				return ;
			}
			if(!Util.checkStringBulk(bolish_time_min, bolish_time_max)){
				setMessage("失效时间最小值不能大于最大值");
				return ;
			}
			
			tradeData.setCreate_time_min(create_time_min);
			tradeData.setCreate_time_max(create_time_max);
			tradeData.setBolish_time_min(bolish_time_min);
			tradeData.setBolish_time_max(bolish_time_max);
			TradeController.queryData = tradeData ;
			String packet = tradeData.toHttpPacket();
			request.getBill(packet, TradeController.QueryCaller);
			
			hide();
		}
	}
}
