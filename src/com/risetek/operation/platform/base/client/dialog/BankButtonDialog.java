package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.BankController;
import com.risetek.operation.platform.base.client.model.BankData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: BankAddDialog 
 * @Description: 增加发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:16:14 
 * @version 1.0
 */
public class BankButtonDialog extends BaseDialog {   
	
	public final TextBox BANK_ID = new MyTextBox();
	public final TextBox PREFIX = new MyTextBox();
	public final TextBox NAME = new MyTextBox();
	public final TextBox DESCRIPTION = new MyTextBox();
	public final ListBox VALIDITY = Util.getValidity();
	
	private String ACTION_NAME = null ;
	RequestFactory factory = new RequestFactory();
	
	private String[] addColumns = { 
		BankConstanst.PREFIX_ZH,
		BankConstanst.NAME_ZH, 
		BankConstanst.DESCRIPTION_ZH,
		BankConstanst.VALIDITY_ZH
	};
	
	private String[] searchColumns = { 
		BankConstanst.BANK_ID_ZH,
		BankConstanst.PREFIX_ZH,
		BankConstanst.NAME_ZH, 
		BankConstanst.DESCRIPTION_ZH,
		BankConstanst.VALIDITY_ZH
	};
	
	public BankButtonDialog() {
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}

	/**
	 * @Description: 创建添加组件 
	 */
	public void addMainPanel(){	
		ACTION_NAME = Constanst.ACTION_NAME_ADD_BANK_INFO ;
		gridFrame.resize(4, 2);
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,PREFIX);
		gridFrame.setWidget(1,1,NAME);
		gridFrame.setWidget(2,1,DESCRIPTION);
		gridFrame.setWidget(3,1,VALIDITY);
		setText("增加发卡行信息");
		setDescript("请输入新的发卡行信息");
		mainPanel.add(gridFrame);
		show();
	}
	/**
	 * @Description: 创建查询组件
	 */
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_BANK_INFO ;
		gridFrame.resize(5, 2);
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,BANK_ID);
		gridFrame.setWidget(1,1,PREFIX);
		gridFrame.setWidget(2,1,NAME);
		gridFrame.setWidget(3,1,DESCRIPTION);
		gridFrame.setWidget(4,1,VALIDITY);
		setText("查询发卡行信息");
		setDescript("请输入查询信息");
		mainPanel.add(gridFrame);
		show();
	}

	@Override
	public void show(){
		super.show();
		BANK_ID.setFocus(true);
	}

	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String id = BANK_ID.getText();
			String prefix = PREFIX.getText();
			String name = NAME.getText();
			String description = DESCRIPTION.getText();
			int validityIndex = VALIDITY.getSelectedIndex();
			String validity = VALIDITY.getValue(validityIndex);
			
			BankData bankData = new BankData();
			bankData.setACTION_NAME(ACTION_NAME);
			
			if(id == null || "".equals(id.trim())){
				if(!Util.isNum(id)){
					setMessage("银行卡索引必须为数字");
					return ;
				}
				bankData.setBank_id(Integer.parseInt(id));
			}
			bankData.setPrefix(prefix);
			bankData.setName(name);
			bankData.setDescription(description);
			bankData.setValidity(validity);
			
			if(Constanst.ACTION_NAME_ADD_BANK_INFO.equals(ACTION_NAME)){
				if(prefix == null || "".equals(prefix.trim())){
					setMessage("银行卡卡号前缀不能为空");
					return ;
				}
				String packet = bankData.toHttpPacket();
				factory.getBill(packet, BankController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_QUERY_BANK_INFO.equals(ACTION_NAME)){
				BankController.queryData = bankData;
				String packet = bankData.toHttpPacket();
				factory.getBill(packet, BankController.RemoteCaller);
			}
			hide();
		}
	}
}	
