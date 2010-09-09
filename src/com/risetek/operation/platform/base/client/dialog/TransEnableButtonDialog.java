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
import com.risetek.operation.platform.base.client.control.TransEnableController;
import com.risetek.operation.platform.base.client.model.ChannelData;
import com.risetek.operation.platform.base.client.model.TransEnableData;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransEnableConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

public class TransEnableButtonDialog  extends CustomDialog {
	
	private final Label TRANS_ENABEL_ID_ZH = new Label(TransEnableConstanst.TRANS_ENABLE_ID_ZH);
	private final Label TRANS_ID_ZH = new Label(TransEnableConstanst.TRANS_ID_ZH);
	private final Label CHANNEL_ID_ZH = new Label(TransEnableConstanst.CHANNEL_ID_ZH);
	private final Label ENABLE_ZH = new Label(TransEnableConstanst.ENABLE_ZH);
	private final Label DESCRIPTION_ZH = new Label(TransEnableConstanst.DESCRIPTION_ZH);
	
	private final TextBox TRANS_ENABLE_ID = new MyTextBox();
	private final ListBox TRANS_ID = new ListBox() ;
	private final ListBox CHANNEL_ID = new ListBox()  ;
	private final ListBox ENABLE = Util.geteNable();
	private final TextBox DESCRIPTION = new MyTextBox();
	
	Grid gridFrame = new Grid(4, 2);
	
	private String action_name = "";
	
	private String queryList_name = "";
	/**
	 * 查询请求数据
	 */
	TransEnableData data = null ;
		
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public RequestFactory request = new RequestFactory();
	
	public  RequestCallback balanceCaller ;
	//查询业务和渠道操作的回调
	class BalanceRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request0, Response response) {
			if(Constanst.ACTION_NAME_QUERY_BILL_INFO.equals(queryList_name)){
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
			}else if(Constanst.ACTION_NAME_QUERY_CHANNEL_INFO.equals(queryList_name)){
				int code = response.getStatusCode();
				System.out.println(code);
				String ret = response.getText();
				ChannelData channelData = new ChannelData();
				ListBox channel_list = new ListBox() ;
				channel_list.addItem("","");
				channelData.parseData(ret);
				String[][]  data = channelData.getData();
				for(int i = 0 ; i< data.length ; i ++){
					channel_list.addItem(data[i][0],data[i][2]);
				}
				gridFrame.setWidget(1, 1, channel_list);
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	public TransEnableButtonDialog() {
		balanceCaller = new BalanceRequestCallback();
		ClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
		TransactionData transData = new TransactionData();
		String transPacket = transData.toHttpPacket();
		queryList_name = Constanst.ACTION_NAME_QUERY_BILL_INFO;
		request.getBill(transPacket, balanceCaller);
		ChannelData channelData = new ChannelData();
		String channelPacket = channelData.toHttpPacket();
		queryList_name = Constanst.ACTION_NAME_QUERY_CHANNEL_INFO;
		request.getBill(channelPacket, balanceCaller);
	}
	
	public void addMainPanel(){
		setText("添加业务使能");
		gridFrame.clear();
		gridFrame.resize(4, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, CHANNEL_ID_ZH);
		gridFrame.setWidget(1, 1, CHANNEL_ID);
		gridFrame.setWidget(2, 0, ENABLE_ZH);
		gridFrame.setWidget(2, 1, ENABLE);
		gridFrame.setWidget(3, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(3, 1, DESCRIPTION);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		setText("查询业务使能");
		gridFrame.clear();
		gridFrame.resize(5, 2);
		gridFrame.setWidget(0, 0, TRANS_ID_ZH);
		gridFrame.setWidget(0, 1, TRANS_ID);
		gridFrame.setWidget(1, 0, CHANNEL_ID_ZH);
		gridFrame.setWidget(1, 1, CHANNEL_ID);
		gridFrame.setWidget(2, 0, TRANS_ENABEL_ID_ZH);
		gridFrame.setWidget(2, 1, TRANS_ENABLE_ID);
		gridFrame.setWidget(3, 0, ENABLE_ZH);
		gridFrame.setWidget(3, 1, ENABLE);
		gridFrame.setWidget(4, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(4, 1, DESCRIPTION);	
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
			int transIndex = TRANS_ID.getSelectedIndex();
			int channelIndex = 	CHANNEL_ID.getSelectedIndex();
			int enableIndex = ENABLE.getSelectedIndex();
			int trans_enable_id = 0;
			try {
				trans_enable_id = Integer.parseInt(TRANS_ENABLE_ID.getText());
			} catch (Exception e) {
			}
			int transId = 0;
			try {
				transId = Integer.parseInt(TRANS_ID.getValue(transIndex));
			} catch (Exception e) {
			}
			int channelId = 0;
			try {
				channelId = Integer.parseInt(CHANNEL_ID.getValue(channelIndex));
			} catch (Exception e) {
			}
			String enable = ENABLE.getValue(enableIndex);
			String description = DESCRIPTION.getText();
			TransEnableData transEnableData = new TransEnableData();
			transEnableData.setTrans_enable_id(trans_enable_id);
			transEnableData.setTrans_id(transId);
			transEnableData.setChannel_id(channelId);
			transEnableData.setEnable(enable);
			transEnableData.setDescription(description);
			String packet = transEnableData.toHttpPacket();
			
			if(Constanst.ACTION_NAME_QUERY_TRANS_ENABLE.equals(action_name)){
				request.getBill(packet, TransEnableController.QueryCaller);
				data = transEnableData;			
			}else if(Constanst.ACTION_NAME_ADD_TRANS_ENABLE.equals(action_name)){
				if(transId==0){
					Window.alert(TransEnableConstanst.TRANS_ID_ZH+"不能为空");
					return ;
				}else if(channelId==0){
					Window.alert(TransEnableConstanst.CHANNEL_ID_ZH+"不能为空");
					return ;
				}else if(enable == null || "".equals(enable)){
					Window.alert(TransEnableConstanst.ENABLE_ZH+"不能为空");
					return ;
				}
				request.getBill(packet, TransEnableController.RemoteCaller);
			}
			hide();
		}
	}
}
