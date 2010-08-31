package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.entry.ChannelConstanst;
import com.risetek.operation.platform.base.client.model.ChannelData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 * ChannelView的按钮事件
 */
public class ChannelButtonDialog extends CustomDialog {
	
	private final Label DESCRIPTION_ZH = new Label(ChannelConstanst.DESCRIPTION_ZH);
	private final Label FEE_ZH = new Label(ChannelConstanst.FEE_ZH);
	private final Label FEE_TYPE_ZH = new Label(ChannelConstanst.FEE_TYPE_ZH);
	private final Label FEE_ADDTION_ZH = new Label(ChannelConstanst.FEE_ADDITION_ZH);
	private final Label ADDTION_ZH = new Label(ChannelConstanst.ADDITION_ZH);
	private final Label LOC_CODE_ZH = new Label(ChannelConstanst.LOC_CODE_ZH);
	
	
	private final TextBox DESCRIPTION = new MyTextBox();
	private final TextBox FEE = new MyTextBox();
	private final TextBox FEE_TYPE = new MyTextBox();
	private final TextBox FEE_ADDITION = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	private final TextBox LOC_CODE = new MyTextBox();
	
	private String action_name = "";
	private String trans_id = "";
	
	public ChannelButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	private void clearPanel(){
		DESCRIPTION.setValue(null);
		FEE.setValue(null);
		FEE_TYPE.setValue(null);
		FEE_ADDITION.setValue(null);
		ADDITION.setValue(null);
		LOC_CODE.setValue(null);
	}
	
	public void addMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("添加商户");
		Grid gridFrame = new Grid(6, 2);
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
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("查询商户");
		Grid gridFrame = new Grid(6, 2);
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
		mainPanel.add(gridFrame);
		submit.setText("查询");
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	
	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			String description = DESCRIPTION.getText();
			String fee = FEE.getText();
			String fee_type = FEE_TYPE.getText();
			String addition = ADDITION.getText();
			String fee_addition = FEE_ADDITION.getText();
			String loc_code = LOC_CODE.getText();
			
			ChannelData channelData = new ChannelData();
			channelData.setDescription(description);
			channelData.setFee(fee);
			channelData.setFee_type(fee_type);
			channelData.setFee_addition(fee_addition);
			channelData.setAddition(addition);
			channelData.setLoc_code(loc_code);
			
		}
	}
	
}
