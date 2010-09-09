package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.model.TransBindData;
import com.risetek.operation.platform.base.client.model.TransactionData;
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
	private final ListBox TRANS_ID = new ListBox() ;
	private final TextBox CUSTOMER_ID = new MyTextBox() ;
	
	private String action_name = null;
	
	Grid gridFrame = new Grid(4, 2);
	
	/**
	 * 查询请求数据
	 */
	TransBindData data = null ;
		
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public RequestFactory request = new RequestFactory();
	
	public  RequestCallback balanceCaller ;
	
	//查询业务操作的回调
	class BalanceRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request0, Response response) {
		
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			TransactionData transData = new TransactionData();
			ListBox trans_list = new ListBox() ;
			trans_list.addItem("","");
			transData.parseData(ret);
			String[][]  data = transData.getData();
			for(int i = 0 ; i< data.length ; i ++){
				trans_list.addItem(data[i][0],data[i][2]);
			}
			gridFrame.setWidget(0, 1, trans_list);
			
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	public TransBindButtonDialog() {
		// TODO Auto-generated constructor stub
		balanceCaller = new BalanceRequestCallback();
		ClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
		TransactionData transData = new TransactionData();
		transData.setBindable(Constanst.TRUE);
		String transPacket = transData.toHttpPacket();
		request.getBill(transPacket, balanceCaller);		
	}
	
	public void addMainPanel(){
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
					Window.alert(TransBindConstanst.CUSTOMER_ID_ZH+"不能为空");
				}else if(trans_id == 0){
					Window.alert(TransBindConstanst.TRANS_ID_ZH+"不能为空");
				}
				String packet = transBindData.toHttpPacket();
				request.getBill(packet, balanceCaller);
			}else if(Constanst.ACTION_NAME_QUERY_TRANS_BIND.equals(action_name)){	
				String packet = transBindData.toHttpPacket();
				data = transBindData;
				request.getBill(packet, balanceCaller);
			}
		}
	}
}
