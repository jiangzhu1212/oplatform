package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.TransEnableController;
import com.risetek.operation.platform.base.client.model.TransEnableData;
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
	
	private final TextBox TRANS_ENABLE_ID = new TextBox();
	private ListBox TRANS_ID = new ListBox() ;
	private ListBox CHANNEL_ID = new ListBox()  ;
	private final ListBox ENABLE = Util.geteNable();
	private final TextBox DESCRIPTION = new TextBox();
	
	Grid gridFrame = new Grid(4, 2);
	
	private String ACTION_NAME = "";

	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public RequestFactory request = new RequestFactory();
	
	int m = 0 ;
	
	public TransEnableButtonDialog() {
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
	
	Timer channel_timer = new Timer(){
		@Override
		public void run() {
			if(TransEnableController.INSTANCE.channel_list != null){
				CHANNEL_ID = TransEnableController.INSTANCE.trans_list;
				gridFrame.setWidget(1, 1, CHANNEL_ID);
				cancel() ;
			}
		}
	};
	
	public void loadList(){
		trans_timer.scheduleRepeating(1000);
		channel_timer.scheduleRepeating(1000);		
	}
	
	public void addMainPanel(){
		loadList() ;
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
		loadList() ;
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
		return ACTION_NAME;
	}

	public void setAction_name(String action_name) {
		this.ACTION_NAME = action_name;	
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
			
			if(Constanst.ACTION_NAME_QUERY_TRANS_ENABLE.equals(ACTION_NAME)){
				request.getBill(packet, TransEnableController.QueryCaller);
				TransEnableController.queryData = transEnableData;			
			}else if(Constanst.ACTION_NAME_ADD_TRANS_ENABLE.equals(ACTION_NAME)){
				if(transId==0){
					setMessage(TransEnableConstanst.TRANS_ID_ZH+"不能为空");
					return ;
				}else if(channelId==0){
					setMessage(TransEnableConstanst.CHANNEL_ID_ZH+"不能为空");
					return ;
				}else if(enable == null || "".equals(enable)){
					setMessage(TransEnableConstanst.ENABLE_ZH+"不能为空");
					return ;
				}
				request.getBill(packet, TransEnableController.RemoteCaller);
			}
			hide();
		}
	}
}
