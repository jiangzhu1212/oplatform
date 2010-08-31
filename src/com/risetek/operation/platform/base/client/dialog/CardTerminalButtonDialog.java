package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.entry.CardTerminalConstanst;
import com.risetek.operation.platform.base.client.model.CardTerminalData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 * cardTerminalView的按钮事件
 */
public class CardTerminalButtonDialog extends CustomDialog {
	
	private final Label TM_KEY_ZH = new Label(CardTerminalConstanst.TM_KEY_ZH);
	private final Label DESCRIPTION_ZH = new Label(CardTerminalConstanst.DESCRIPTION_ZH);
	private final Label ADDITION_ZH = new Label(CardTerminalConstanst.ADDITION_ZH);
	private final Label VALIDITY_ZH = new Label(CardTerminalConstanst.VALIDITY_ZH);
	
	private final TextBox TM_KEY = new MyTextBox();
	private final TextBox DESCRIPTION = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	private final TextBox VALIDITY = new MyTextBox();
	
	private String action_name = "";
	private String trans_id = "";
	
	public CardTerminalButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	private void clearPanel(){
		TM_KEY.setValue(null);
		DESCRIPTION.setValue(null);
		ADDITION.setValue(null);
		VALIDITY.setValue(null);
	}
	
	public void addMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("添加开卡终端");
		Grid gridFrame = new Grid(4, 2);
		gridFrame.setWidget(0, 0, TM_KEY_ZH);
		gridFrame.setWidget(0, 1, TM_KEY);
		gridFrame.setWidget(1, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(1, 1, DESCRIPTION);
		gridFrame.setWidget(2, 0, ADDITION_ZH);
		gridFrame.setWidget(2, 1, ADDITION);
		gridFrame.setWidget(3, 0, VALIDITY_ZH);
		gridFrame.setWidget(3, 1, VALIDITY);
		
		mainPanel.add(gridFrame);
		submit.setText("添加");
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		clearPanel();
		setText("查询开卡终端");
		Grid gridFrame = new Grid(4, 2);
		gridFrame.setWidget(0, 0, TM_KEY_ZH);
		gridFrame.setWidget(0, 1, TM_KEY);
		gridFrame.setWidget(1, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(1, 1, DESCRIPTION);
		gridFrame.setWidget(2, 0, ADDITION_ZH);
		gridFrame.setWidget(2, 1, ADDITION);
		gridFrame.setWidget(3, 0, VALIDITY_ZH);
		gridFrame.setWidget(3, 1, VALIDITY);	
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
			String tm_key = TM_KEY.getText();
			String description = DESCRIPTION.getText();
			String addition = ADDITION.getText();
			String validity = VALIDITY.getText();
			
			CardTerminalData cardTerminalData = new CardTerminalData();
			cardTerminalData.setTm_key(tm_key);
			cardTerminalData.setDescription(description);
			cardTerminalData.setAddition(addition);
			cardTerminalData.setValidity(validity);
			
		}
	}
	
}
