package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.TransBindController;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.TransBindData;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransBindConstanst;

public class TransBindButtonDialog  extends BaseButtonDailog {
	
	private final Label TRANS_BIND_ID_ZH = new Label(TransBindConstanst.TRANS_BIND_ID_ZH);
	private final Label TRANS_ID_ZH = new Label(TransBindConstanst.TRANS_ID_ZH);
	private final Label CUSTOMER_ID_ZH = new Label(TransBindConstanst.CUSTOMER_ID_ZH);
	
	private final TextBox TRANS_BIND_ID = new TextBox() ;	
	private ListBox TRANS_ID = new ListBox() ;
	private final TextBox CUSTOMER_ID = new TextBox() ;
	
	Timer trans_timer = new Timer(){
		@Override
		public void run() {
			if(TransBindController.INSTANCE.trans_list != null){
				TRANS_ID = TransBindController.INSTANCE.trans_list;
				gridFrame.setWidget(0, 1, TRANS_ID);
				cancel() ;
			}
			
		}
	};
	
	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_TRANS_BIND ;
		trans_timer.scheduleRepeating(1000) ;
		setText("添加业务绑定");
		mainPanel.clear();
		gridFrame.resize(2, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(1, 1, CUSTOMER_ID);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_TRANS_BIND ;
		trans_timer.scheduleRepeating(1000) ;
		setText("查询业务绑定");
		mainPanel.clear();
		gridFrame.resize(3, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, TRANS_BIND_ID_ZH);
		gridFrame.setWidget(1, 1, TRANS_BIND_ID);
		gridFrame.setWidget(2, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(2, 1, CUSTOMER_ID);	
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show() ;
	}
	
	@Override
	public void subminHandler() {
		// TODO Auto-generated method stub
		TransBindData transBindData = new TransBindData();
		int transIndex = TRANS_ID.getSelectedIndex();
		int trans_id = 0;
		try {
			trans_id = Integer.parseInt(TRANS_ID.getValue(transIndex));
		} catch (Exception e) {
		}
		int trans_bind_id = 0;
		try {
			trans_bind_id = Integer.parseInt(TRANS_BIND_ID.getText());
		} catch (Exception e) {
		}
		int customer_id = 0;
		try {
			customer_id = Integer.parseInt(CUSTOMER_ID.getText());
		} catch (Exception e) {
		}
		transBindData.setCustomer_id(customer_id);
		transBindData.setTrans_bind_id(trans_bind_id);
		transBindData.setTrans_id(trans_id);
		
		if(Constanst.ACTION_NAME_ADD_TRANS_BIND.equals(ACTION_NAME)){
			if(customer_id == 0){
				setMessage(TransBindConstanst.CUSTOMER_ID_ZH+"不能为空");
				return ;
			}else if(trans_id == 0){
				setMessage(TransBindConstanst.TRANS_ID_ZH+"不能为空");
				return ;
			}
			String jsonStr = transBindData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, TransBindController.RemoteCaller);
		}else if(Constanst.ACTION_NAME_QUERY_TRANS_BIND.equals(ACTION_NAME)){	
			String jsonStr = transBindData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			TransBindController.queryData = transBindData;
			request.getBill(packet, TransBindController.QueryCaller);
		}
		
		hide();
	}
	
	@Override
	public void hide() {
		trans_timer.cancel() ;
		super.hide();
	}
}
