package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.CupCertificateController;
import com.risetek.operation.platform.base.client.model.CupCertificateData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CupCertificateConstanst;
import com.risetek.operation.platform.launch.client.util.Util;


public class CupCertificateButtonDialog extends BaseButtonDailog {
	
	private Label CERTIFICATE_ID_ZH = new Label(CupCertificateConstanst.CERTIFICATE_ID_ZH);
	private Label BILL_ID_ZH = new Label(CupCertificateConstanst.BILL_ID_ZH);
	private Label TIME_ZH = new Label(CupCertificateConstanst.TIME_ZH);
	private Label TIME_MIN_ZH = new Label(CupCertificateConstanst.TIME_MIN_ZH);
	private Label TIME_MAX_ZH = new Label(CupCertificateConstanst.TIME_MAX_ZH);
	private Label AMOUNT_ZH = new Label(CupCertificateConstanst.AMOUNT_ZH);
	private Label TYPE_ZH = new Label(CupCertificateConstanst.TYPE_ZH);
	private Label ACCOUNT_NUMBER_ZH = new Label(CupCertificateConstanst.ACCOUNT_NUMBER_ZH);
	
	private TextBox CERTIFICATE_ID = new TextBox();
	private TextBox BILL_ID = new TextBox();
	private TextBox AMOUNT = new TextBox();
	private TextBox TYPE = new TextBox();
	private TextBox ACCOUNT_NUMBER = new TextBox();
	
	private DateBox TIME = new DateBox();
	private DateBox TIME_MIN = new DateBox();
	private DateBox TIME_MAX = new DateBox();
	
	@Override
	public void addMainPanel() {
		ACTION_NAME = Constanst.ACTION_NAME_ADD_CERTIFICATE_INFO ;
		mainPanel.clear();
		setText("添加交易凭证");
		gridFrame.resize(5, 2);
		gridFrame.setWidget(0, 0, BILL_ID_ZH);
		gridFrame.setWidget(0, 1, BILL_ID);
		gridFrame.setWidget(1, 0, TIME_ZH);
		gridFrame.setWidget(1, 1, TIME);
		gridFrame.setWidget(2, 0, AMOUNT_ZH);
		gridFrame.setWidget(2, 1, AMOUNT);
		gridFrame.setWidget(3, 0, TYPE_ZH);
		gridFrame.setWidget(3, 1, TYPE);
		gridFrame.setWidget(4, 0, ACCOUNT_NUMBER_ZH);
		gridFrame.setWidget(4, 1, ACCOUNT_NUMBER);
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}

	@Override
	public void queryMainPanel() {
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CERTIFICATE_INFO ;
		mainPanel.clear();
		setText("查询交易凭证");
		gridFrame.resize(7, 2);
		gridFrame.setWidget(0, 0, CERTIFICATE_ID_ZH);
		gridFrame.setWidget(0, 1, CERTIFICATE_ID);
		gridFrame.setWidget(1, 0, BILL_ID_ZH);
		gridFrame.setWidget(1, 1, BILL_ID);
		gridFrame.setWidget(2, 0, AMOUNT_ZH);
		gridFrame.setWidget(2, 1, AMOUNT);
		gridFrame.setWidget(3, 0, TYPE_ZH);
		gridFrame.setWidget(3, 1, TYPE);
		gridFrame.setWidget(4, 0, ACCOUNT_NUMBER_ZH);
		gridFrame.setWidget(4, 1, ACCOUNT_NUMBER);
		gridFrame.setWidget(4, 0, ACCOUNT_NUMBER_ZH);
		gridFrame.setWidget(4, 1, ACCOUNT_NUMBER);
		gridFrame.setWidget(5, 0, TIME_MIN_ZH);
		gridFrame.setWidget(5, 1, TIME_MIN);
		gridFrame.setWidget(6, 0, TIME_MAX_ZH);
		gridFrame.setWidget(6, 1, TIME_MAX);
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}

	@Override
	public void subminHandler() {
		
		String bill_id = BILL_ID.getText() ;
		String amount = AMOUNT.getText() ;
		String type = TYPE.getText() ;
		String account_number = ACCOUNT_NUMBER.getText() ;
		
		CupCertificateData cupCertificateData = new CupCertificateData();
		cupCertificateData.setACTION_NAME(ACTION_NAME);
		if(bill_id != null && !"".equals(bill_id)){
			cupCertificateData.setBill_id(Integer.parseInt(bill_id));
		}
		cupCertificateData.setAmount(amount);
		cupCertificateData.setType(type);
		cupCertificateData.setAccount_number(account_number);
		
		if(Constanst.ACTION_NAME_ADD_CERTIFICATE_INFO.equals(ACTION_NAME)){
			if(bill_id == null || "".equals(bill_id)){
				setMessage("账单不能为空");
				return ;
			}
			if(amount == null || "".equals(amount)){
				setMessage("支付金额不能为空");
				return ;
			}
			if(type == null || "".equals(type)){
				setMessage("凭证类型不能为空");
				return ;
			}
			if(account_number == null || "".equals(account_number)){
				setMessage("支付账号不能为空");
				return ;
			}
			String time = Util.formatDateToJsonString(TIME.getValue());
			if(time == null || "".equals(time)){
				setMessage("支付时间不能为空");
				return ;
			}
			cupCertificateData.setTime(time);
			String jsonStr = cupCertificateData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, CupCertificateController.RemoteCaller);
		}else if(Constanst.ACTION_NAME_QUERY_CERTIFICATE_INFO.equals(ACTION_NAME)){
			String time_min = Util.formatDateToJsonString(TIME_MIN.getValue());
			String time_max = Util.formatDateToJsonString(TIME_MAX.getValue());
			if(!Util.checkStringBulk(time_min, time_max)){
				setMessage("支付时间最小值不能大于最大值");
				return ;
			}
			String certificate_id = CERTIFICATE_ID.getText();
			if(certificate_id != null && !"".equals(certificate_id)){
				cupCertificateData.setCertificate_id(Integer.parseInt(certificate_id));
			}
			
			CupCertificateController.queryData = cupCertificateData;
			String jsonStr = cupCertificateData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, CupCertificateController.QueryCaller);
		}
	
	}
	
}
