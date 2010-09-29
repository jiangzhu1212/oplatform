package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.TransactionController;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * transActionView button按钮事件
 */
public class TransactionButtonDialog extends BaseButtonDailog {
	
	private final Label TRANS_ID_ZH = new Label(TransactionConstanst.TRANS_ID_ZH);
	private final Label ALIAS_ZH = new Label(TransactionConstanst.ALIAS_ZH);
	private final Label NAME_ZH = new Label(TransactionConstanst.NAME_ZH);
	private final Label DESCRIPTION_ZH = new Label(TransactionConstanst.DESCRIPTION_ZH);
	private final Label URL_ZH = new Label(TransactionConstanst.URL_ZH);
	private final Label BINDABLE_ZH = new Label(TransactionConstanst.BINDABLE_ZH);
	private final Label MERCHANT_NUMBER_ZH = new Label(TransactionConstanst.MERCHANT_NUMBER_ZH);
	private final Label POS_NUMBER_ZH = new Label(TransactionConstanst.POS_NUMBER_ZH);
	private final Label TYPE_ZH = new Label(TransactionConstanst.TYPE_ZH);
	private final Label ADDITION_ZH = new Label(TransactionConstanst.ADDITION_ZH);
	private final Label VALIDITY_ZH = new Label(TransactionConstanst.VALIDITY_ZH);
	
	private final TextBox TRANS_ID = new TextBox();
	private final TextBox ALIAS = new TextBox();
	private final TextBox NAME = new TextBox();
	private final TextBox DESCRIPTION = new TextBox();
	private final TextBox URL = new TextBox();
	private final TextBox MERCHANT_NUMBER = new TextBox();
	private final TextBox POS_NUMBER = new TextBox();
	private final TextBox TYPE = new TextBox();
	private final TextBox ADDITION = new TextBox();	
	private final ListBox VALIDITY = Util.getValidity();
	
	private final ListBox BINDABLE = Util.getBindAble();
	
	public void addMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_ADD_TRANSACTION_INFO;
		setText("添加商户");
		gridFrame.resize(10, 2) ;
		gridFrame.setStyleName("table");
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
		gridFrame.setWidget(9, 0, VALIDITY_ZH);
		gridFrame.setWidget(9, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show() ;
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO;
		setText("查询商户");
		gridFrame.resize(11, 2) ;
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, ALIAS_ZH);
		gridFrame.setWidget(1, 1, ALIAS);
		gridFrame.setWidget(2, 0, NAME_ZH);
		gridFrame.setWidget(2, 1, NAME);
		gridFrame.setWidget(3, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(3, 1, DESCRIPTION);
		gridFrame.setWidget(4, 0, URL_ZH);
		gridFrame.setWidget(4, 1, URL);
		gridFrame.setWidget(5, 0, BINDABLE_ZH);
		gridFrame.setWidget(5, 1, BINDABLE);
		gridFrame.setWidget(6, 0, MERCHANT_NUMBER_ZH);
		gridFrame.setWidget(6, 1, MERCHANT_NUMBER);
		gridFrame.setWidget(7, 0, POS_NUMBER_ZH);
		gridFrame.setWidget(7, 1, POS_NUMBER);
		gridFrame.setWidget(8, 0, TYPE_ZH);
		gridFrame.setWidget(8, 1, TYPE);
		gridFrame.setWidget(9, 0, ADDITION_ZH);
		gridFrame.setWidget(9, 1, ADDITION);
		gridFrame.setWidget(10, 0, VALIDITY_ZH);
		gridFrame.setWidget(10, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show() ;
	}

	@Override
	public void subminHandler() {
		// TODO Auto-generated method stub
		String id = TRANS_ID.getText();
		String alias = ALIAS.getText();
		String name = NAME.getText();			
		String description = DESCRIPTION.getText();
		String url = URL.getText();
		
		String merchant_number = MERCHANT_NUMBER.getText();
		String pos_number = POS_NUMBER.getText();
		String type = TYPE.getText();
		String addition = ADDITION.getText();
		
		int bindableIndext = BINDABLE.getSelectedIndex();
		String bindable = BINDABLE.getValue(bindableIndext);
		
		int validityIndext = VALIDITY.getSelectedIndex();
		String validity = VALIDITY.getValue(validityIndext);
		
		TransactionData transaction = new TransactionData();
		transaction.setACTION_NAME(ACTION_NAME);
		if(id != null && !"".equals(id)){
			if(!Util.isNum(id)){
				setMessage("业务编号必须为数字");
				return ;
			}
			transaction.setTrans_id(Integer.parseInt(id));
		}
		transaction.setTrans_alias(alias);
		transaction.setName(name);
		transaction.setDescription(description);
		transaction.setUrl(url);
					
		transaction.setMerchant_number(merchant_number);
		transaction.setPos_number(pos_number);
		transaction.setType(type);
		transaction.setAddition(addition);	
		transaction.setBindable(bindable);
		transaction.setValidity(validity);
		
		if(Constanst.ACTION_NAME_ADD_TRANSACTION_INFO.equals(ACTION_NAME)){
			if(name == null || "".equals(name.trim())){
				setMessage("业务名称不能为空");
				return ;
			}
			String jsonStr = transaction.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, TransactionController.RemoteCaller);
		}else if(Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO.equals(ACTION_NAME)){
			TransactionController.queryData = transaction ;
			String jsonStr = transaction.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, TransactionController.QueryCaller);
		}
		
		hide() ;
	}
	
}
