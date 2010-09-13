package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.TransBindController;
import com.risetek.operation.platform.base.client.control.TransEnableController;
import com.risetek.operation.platform.base.client.model.TransBindData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransBindConstanst;

public class TransBindButtonDialog  extends CustomDialog {
	
	private final Label TRANS_BIND_ID_ZH = new Label(TransBindConstanst.TRANS_BIND_ID_ZH);
	private final Label TRANS_ID_ZH = new Label(TransBindConstanst.TRANS_ID_ZH);
	private final Label CUSTOMER_ID_ZH = new Label(TransBindConstanst.CUSTOMER_ID_ZH);
	
	private final TextBox TRANS_BIND_ID = new MyTextBox() ;	
	private ListBox TRANS_ID = new ListBox() ;
	private final TextBox CUSTOMER_ID = new MyTextBox() ;
	
	private String action_name = null;
	
	Grid gridFrame = new Grid(4, 2);
		
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public RequestFactory request = new RequestFactory();
		
	public TransBindButtonDialog() {
		// TODO Auto-generated constructor stub
		ClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	Timer trans_timer = new Timer(){
		@Override
		public void run() {
			if(TransEnableController.INSTANCE.trans_list != null){
				TRANS_ID = TransEnableController.INSTANCE.trans_list;
				gridFrame.setWidget(0, 1, TRANS_ID);
				cancel() ;
			}
			
		}
	};
	
	public void addMainPanel(){
		trans_timer.scheduleRepeating(1000) ;
		setText("添加业务使能");
		gridFrame.clear();
		gridFrame.resize(2, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(1, 1, CUSTOMER_ID);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		trans_timer.scheduleRepeating(1000) ;
		setText("查询业务使能");
		gridFrame.clear();
		gridFrame.resize(3, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, TRANS_BIND_ID_ZH);
		gridFrame.setWidget(1, 1, TRANS_BIND_ID);
		gridFrame.setWidget(2, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(2, 1, CUSTOMER_ID);	
		mainPanel.add(gridFrame);
		submit.setText("查询");
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;	
	}

	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			TransBindData transBindData = new TransBindData();
			int transIndex = TRANS_ID.getSelectedIndex();
			int trans_id = 0;
			try {
				trans_id = Integer.parseInt(TRANS_ID.getValue(transIndex));
			} catch (Exception e) {
			}
			int trans_bind_id = Integer.parseInt(TRANS_BIND_ID.getText());
			int customer_id = Integer.parseInt(CUSTOMER_ID.getText());
			transBindData.setCustomer_id(customer_id);
			transBindData.setTrans_bind_id(trans_bind_id);
			transBindData.setTrans_id(trans_id);
			
			if(Constanst.ACTION_NAME_ADD_TRANS_BIND.equals(action_name)){
				if(customer_id == 0){
					setMessage(TransBindConstanst.CUSTOMER_ID_ZH+"不能为空");
					return ;
				}else if(trans_id == 0){
					setMessage(TransBindConstanst.TRANS_ID_ZH+"不能为空");
					return ;
				}
				String packet = transBindData.toHttpPacket();
				request.getBill(packet, TransBindController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_QUERY_TRANS_BIND.equals(action_name)){	
				String packet = transBindData.toHttpPacket();
				TransBindController.queryData = transBindData;
				request.getBill(packet, TransBindController.QueryCaller);
			}
		}
	}
}
