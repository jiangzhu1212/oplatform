package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.CardTerminalController;
import com.risetek.operation.platform.base.client.model.CardTerminalData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.CardTerminalConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * cardTerminalView的按钮事件
 */
public class CardTerminalButtonDialog extends CustomDialog {
	
	private final Label TERMINAL_ID_ZH = new Label(CardTerminalConstanst.TERMINAL_ID_ZH);
	private final Label SN_ZH = new Label(CardTerminalConstanst.SN_ZH);
	private final Label DESCRIPTION_ZH = new Label(CardTerminalConstanst.DESCRIPTION_ZH);
	private final Label ADDITION_ZH = new Label(CardTerminalConstanst.ADDITION_ZH);
	private final Label VALIDITY_ZH = new Label(CardTerminalConstanst.VALIDITY_ZH);
	
	private final TextBox TERMINAL_ID = new MyTextBox();
	private final TextBox SN = new MyTextBox();
	private final TextBox DESCRIPTION = new MyTextBox();
	private final TextBox ADDITION = new MyTextBox();
	private final ListBox VALIDITY = Util.getValidity();
	
	private String ACTION_NAME = null;
	
	RequestFactory factory = new RequestFactory();
	
	public CardTerminalButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	
	public void addMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_ADD_CARD_TERMINAL ;
		setText("添加开卡终端");
		Grid gridFrame = new Grid(4, 2);
		gridFrame.setWidget(0, 0, SN_ZH);
		gridFrame.setWidget(0, 1, SN);
		gridFrame.setWidget(1, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(1, 1, DESCRIPTION);
		gridFrame.setWidget(2, 0, ADDITION_ZH);
		gridFrame.setWidget(2, 1, ADDITION);
		gridFrame.setWidget(3, 0, VALIDITY_ZH);
		gridFrame.setWidget(3, 1, VALIDITY);
		
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CARD_TERMINAL ;
		setText("查询开卡终端");
		Grid gridFrame = new Grid(5, 2);
		gridFrame.setWidget(0, 0, TERMINAL_ID_ZH);
		gridFrame.setWidget(0, 1, TERMINAL_ID);
		gridFrame.setWidget(1, 0, SN_ZH);
		gridFrame.setWidget(1, 1, SN);
		gridFrame.setWidget(2, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(2, 1, DESCRIPTION);
		gridFrame.setWidget(3, 0, ADDITION_ZH);
		gridFrame.setWidget(3, 1, ADDITION);
		gridFrame.setWidget(4, 0, VALIDITY_ZH);
		gridFrame.setWidget(4, 1, VALIDITY);	
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}

	
	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String id = TERMINAL_ID.getText();
			String tm_key = SN.getText();
			String description = DESCRIPTION.getText();
			String addition = ADDITION.getText();
			int validityIndext = VALIDITY.getTabIndex();			
			String validity = VALIDITY.getValue(validityIndext);
			
			CardTerminalData cardTerminalData = new CardTerminalData();
			cardTerminalData.setACTION_NAME(ACTION_NAME);
			if(id != null && !"".equals(id)){
				if(!Util.isNum(id)){
					setMessage("终端索引必须为数字");
					return ;
				}
				cardTerminalData.setTerminal_id(Integer.parseInt(id));
			}
			cardTerminalData.setSn(tm_key);
			cardTerminalData.setDescription(description);
			cardTerminalData.setAddition(addition);
			cardTerminalData.setValidity(validity);
			
			if(Constanst.ACTION_NAME_ADD_CARD_TERMINAL.equals(ACTION_NAME)){
				String packet = cardTerminalData.toHttpPacket();
				factory.getBill(packet, CardTerminalController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_QUERY_CARD_TERMINAL.equals(ACTION_NAME)){
				CardTerminalController.queryData = cardTerminalData;
				String packet = cardTerminalData.toHttpPacket();
				factory.getBill(packet, CardTerminalController.QueryCaller);
			}
		}
	}
	
}
