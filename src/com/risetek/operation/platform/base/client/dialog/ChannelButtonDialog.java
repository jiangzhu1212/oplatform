package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.ChannelController;
import com.risetek.operation.platform.base.client.model.ChannelData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.ChannelConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * ChannelView的按钮事件
 */
public class ChannelButtonDialog extends CustomDialog {
	
	private final Label CHANNEL_ID_ZH = new Label(ChannelConstanst.CHANNEL_ID_ZH);
	private final Label DESCRIPTION_ZH = new Label(ChannelConstanst.DESCRIPTION_ZH);
	private final Label FEE_ZH = new Label(ChannelConstanst.FEE_ZH);
	private final Label FEE_TYPE_ZH = new Label(ChannelConstanst.FEE_TYPE_ZH);
	private final Label FEE_ADDTION_ZH = new Label(ChannelConstanst.FEE_ADDITION_ZH);
	private final Label ADDTION_ZH = new Label(ChannelConstanst.ADDITION_ZH);
	private final Label LOC_CODE_ZH = new Label(ChannelConstanst.LOC_CODE_ZH);
	private final Label VALIDITY_ZH = new Label(TransactionConstanst.VALIDITY_ZH);
	
	private final TextBox CHANNEL_ID = new MyTextBox();
	private final TextBox DESCRIPTION = new MyTextBox();
	private final TextBox FEE = new MyTextBox();
	private final TextBox FEE_TYPE = new MyTextBox();
	private final TextBox FEE_ADDITION = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	private final TextBox LOC_CODE = new MyTextBox();
	private final ListBox VALIDITY = Util.getValidity();
	
	private String ACTION_NAME = "";
	
	RequestFactory factory = new RequestFactory();
	
	public ChannelButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}

	public void addMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_ADD_CUSTOMER_INFO ;
		setText("添加商户");
		Grid gridFrame = new Grid(7, 2);
		gridFrame.setWidget(0, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(0, 1, DESCRIPTION);
		gridFrame.setWidget(1, 0, FEE_ZH);
		gridFrame.setWidget(1, 1, FEE);
		gridFrame.setWidget(2, 0, FEE_TYPE_ZH);
		gridFrame.setWidget(2, 1, FEE_TYPE);
		gridFrame.setWidget(3, 0, FEE_ADDTION_ZH);
		gridFrame.setWidget(3, 1, FEE_ADDITION);
		gridFrame.setWidget(4, 0, ADDTION_ZH);
		gridFrame.setWidget(4, 1, ADDITION);
		gridFrame.setWidget(5, 0, LOC_CODE_ZH);
		gridFrame.setWidget(5, 1, LOC_CODE);	
		gridFrame.setWidget(6, 0, VALIDITY_ZH);
		gridFrame.setWidget(6, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO ;
		setText("查询商户");
		Grid gridFrame = new Grid(8, 2);
		gridFrame.setWidget(0, 0, CHANNEL_ID_ZH);
		gridFrame.setWidget(0, 1, CHANNEL_ID);
		gridFrame.setWidget(1, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(1, 1, DESCRIPTION);
		gridFrame.setWidget(2, 0, FEE_ZH);
		gridFrame.setWidget(2, 1, FEE);
		gridFrame.setWidget(3, 0, FEE_TYPE_ZH);
		gridFrame.setWidget(3, 1, FEE_TYPE);
		gridFrame.setWidget(4, 0, FEE_ADDTION_ZH);
		gridFrame.setWidget(4, 1, FEE_ADDITION);
		gridFrame.setWidget(5, 0, ADDTION_ZH);
		gridFrame.setWidget(5, 1, ADDITION);
		gridFrame.setWidget(6, 0, LOC_CODE_ZH);
		gridFrame.setWidget(6, 1, LOC_CODE);	
		gridFrame.setWidget(7, 0, VALIDITY_ZH);
		gridFrame.setWidget(7, 1, VALIDITY);
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
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
			String id = CHANNEL_ID.getText();
			String description = DESCRIPTION.getText();
			String fee = FEE.getText();
			String fee_type = FEE_TYPE.getText();
			String addition = ADDITION.getText();
			String fee_addition = FEE_ADDITION.getText();
			String loc_code = LOC_CODE.getText();
			
			ChannelData channelData = new ChannelData();
			channelData.setACTION_NAME(ACTION_NAME);
			if(id != null && !"".equals(id.trim())){
				if(!Util.isNum(id)){
					setMessage("渠道索引必须是数字");
					return ;
				}
				channelData.setChannel_id(Integer.parseInt(id));
			}
			channelData.setDescription(description);
			channelData.setFee(fee);
			channelData.setFee_type(fee_type);
			channelData.setFee_addition(fee_addition);
			channelData.setAddition(addition);
			channelData.setLoc_code(loc_code);
			
			if(Constanst.ACTION_NAME_ADD_CUSTOMER_INFO.equals(ACTION_NAME)){
				if(description == null || "".equals(description.trim())){
					setMessage("渠道描述不能为空");
					return ;
				}
				String packet = channelData.toHttpPacket();
				factory.getBill(packet, ChannelController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO.equals(ACTION_NAME)){
				ChannelController.queryData = channelData;
				String packet = channelData.toHttpPacket();
				factory.getBill(packet, ChannelController.QueryCaller);
			}
		}
	}
	
}
