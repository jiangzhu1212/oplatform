package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.AcountController;
import com.risetek.operation.platform.base.client.model.AcountData;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.AcountConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: AcountAddDialog 
 * @Description: 增加银行卡信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午09:40:05 
 * @version 1.0
 */
public class AcountButtonDialog extends BaseDialog {   
	
	public final TextBox ACCOUNT_NUMBER = new TextBox();
	public final TextBox BANK_ID = new TextBox();
	public final TextBox ADDITION = new TextBox();
	public final TextBox DESCRIPTION = new TextBox();
	public final ListBox VALIDITY = Util.getValidity();
	
	private String ACTION_NAME = null;
	
	RequestFactory factory = new RequestFactory();
	
	private String[] addColumns = {
		AcountConstanst.ACCOUNT_NUMBER_ZH, 
		AcountConstanst.BANK_ID_ZH,
		AcountConstanst.DESCRIPTION_ZH,
		AcountConstanst.ADDITION_ZH,
		AcountConstanst.VALIDITY_ZH
	};
	
	private String[] searchColumns = {
		AcountConstanst.ACCOUNT_NUMBER_ZH, 
		AcountConstanst.BANK_ID_ZH,
		AcountConstanst.DESCRIPTION_ZH,
		AcountConstanst.ADDITION_ZH,
		AcountConstanst.VALIDITY_ZH
	};
	
	
	public AcountButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	
	public void addMainPanel(){	
		ACTION_NAME = Constanst.ACTION_NAME_ADD_ACCOUNT_INFO ;
		gridFrame.resize(5, 2);
		gridFrame.setStyleName("table");
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,ACCOUNT_NUMBER);
		gridFrame.setWidget(1,1,BANK_ID);
		gridFrame.setWidget(2,1,ADDITION);
		gridFrame.setWidget(3,1,DESCRIPTION);
		gridFrame.setWidget(4,1,VALIDITY);
		setText("增加银行卡信息");
		setDescript("请输入新的银行卡信息");
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_ACCOUNT_INFO ;
		gridFrame.resize(5, 2);
		gridFrame.setStyleName("table");
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,ACCOUNT_NUMBER);
		gridFrame.setWidget(1,1,BANK_ID);
		gridFrame.setWidget(2,1,ADDITION);
		gridFrame.setWidget(3,1,DESCRIPTION);
		gridFrame.setWidget(4,1,VALIDITY);
		setText("查询银行卡信息");
		setDescript("请输入查询信息");
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}
	
	
	public void show() {
		super.show();
		ACCOUNT_NUMBER.setFocus(true);
	}
	
	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String account_number = ACCOUNT_NUMBER.getText();
			String bank_id = BANK_ID.getText();
			String addition = ADDITION.getText();
			String description = DESCRIPTION.getText();
			int validityIndext = VALIDITY.getSelectedIndex();
			String validity = VALIDITY.getValue(validityIndext);
			
			AcountData acountData = new AcountData();
			acountData.setACTION_NAME(ACTION_NAME);
			if(bank_id != null && !"".equals(bank_id.trim())){
				if(!Util.isNum(bank_id)){
					setMessage("银行卡类型索引必须是数字");
					return ;
				}
				acountData.setBank_id(Integer.parseInt(bank_id));
			}
			acountData.setAccount_number(account_number);
			acountData.setAddition(addition);
			acountData.setDescription(description);
			acountData.setValidity(validity);
			
			if(Constanst.ACTION_NAME_ADD_ACCOUNT_INFO.equals(ACTION_NAME)){
				if(account_number == null || "".equals(account_number)){
					setMessage("账户号不能为空");
					return ;
				}
				String packet = acountData.toHttpPacket();
				factory.getBill(packet, AcountController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_QUERY_ACCOUNT_INFO.equals(ACTION_NAME)){
				AcountController.queryData = acountData ;
				String packet = acountData.toHttpPacket();
				factory.getBill(packet, AcountController.QueryCaller);
			}
		}
	}


}
