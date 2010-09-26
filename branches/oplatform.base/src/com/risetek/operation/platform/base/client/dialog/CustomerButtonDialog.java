package com.risetek.operation.platform.base.client.dialog;

import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.CustomerController;
import com.risetek.operation.platform.base.client.control.TransBindController;
import com.risetek.operation.platform.base.client.model.CustomerData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.TransBindData;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 *	customerView的button点击弹出窗口
 */
public class CustomerButtonDialog  extends BaseButtonDailog {
	
	private final Label CUSTOMER_ID_ZH = new Label(CustomerConstanst.CUSTOMER_ID_ZH);
	private final Label NAME_ZH = new Label(CustomerConstanst.NAME_ZH);
	private final Label PHONE_ZH = new Label(CustomerConstanst.PHONE_ZH);
	private final Label ADDRESS_ZH = new Label(CustomerConstanst.ADDRESS_ZH);
	private final Label ADDRESS_2_ZH = new Label(CustomerConstanst.ADDRESS_2_ZH);
	private final Label EMAIL_ZH = new Label(CustomerConstanst.EMAIL_ZH);
	private final Label CARD_ID_ZH = new Label(CustomerConstanst.ID_CARD_ZH);
	private final Label CREATE_TIME_ZH = new Label(CustomerConstanst.CREATE_TIME_ZH);
	private final Label VALIDITY_ZH = new Label(CustomerConstanst.VALIDITY_ZH);
	private final Label ADDITION_ZH = new Label(CustomerConstanst.ADDITION_ZH);
	private final Label CREATE_TIME_MIN_ZH = new Label(CustomerConstanst.CREATE_TIME_MIN_ZH);
	private final Label CREATE_TIME_MAX_ZH = new Label(CustomerConstanst.CREATE_TIME_MAX_ZH);
	
	public final TextBox CUSTOMER_ID = new TextBox();
	private final TextBox NAME = new TextBox();
	private final TextBox PHONE = new TextBox();
	private final TextBox ADDRESS = new TextBox();
	private final TextBox ADDRESS_2 = new TextBox();
	private final TextBox EMAIL = new TextBox();
	private final TextBox CARD_ID = new TextBox();
	private final DateBox CREATE_TIME = new DateBox();
	private final TextBox ADDITION = new TextBox();
	
	private final DateBox CREATE_TIME_MIN = new DateBox();
	private final DateBox CREATE_TIME_MAX = new DateBox();
	
	private ListBox TRANS_ID = new ListBox() ;
	
	ListBox VALIDITY = Util.getValidity();
	
	Timer trans_timer = new Timer(){
		@Override
		public void run() {
			if(TransBindController.INSTANCE.trans_list != null){
				TRANS_ID = TransBindController.INSTANCE.trans_list;
				gridFrame.setWidget(0, 1, TRANS_ID);
				cancel() ;
			}
			
		}
	};
	
	/**
	 * 添加用户中mainPanel的具体显示
	 */
	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO ;
		mainPanel.clear();
		setText("添加用户");
		gridFrame.resize(9, 2);
		gridFrame.setWidget(0, 0, NAME_ZH);
		gridFrame.setWidget(0, 1, NAME);
		gridFrame.setWidget(1, 0, PHONE_ZH);
		gridFrame.setWidget(1, 1, PHONE);
		gridFrame.setWidget(2, 0, ADDRESS_ZH);
		gridFrame.setWidget(2, 1, ADDRESS);
		gridFrame.setWidget(3, 0, ADDRESS_2_ZH);
		gridFrame.setWidget(3, 1, ADDRESS_2);
		gridFrame.setWidget(4, 0, EMAIL_ZH);
		gridFrame.setWidget(4, 1, EMAIL);
		gridFrame.setWidget(5, 0, CARD_ID_ZH);
		gridFrame.setWidget(5, 1, CARD_ID);
		gridFrame.setWidget(6, 0, CREATE_TIME_ZH);
		gridFrame.setWidget(6, 1, CREATE_TIME);
		gridFrame.setWidget(7, 0, VALIDITY_ZH);
		gridFrame.setWidget(7, 1, VALIDITY);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	/**
	 * 查询用户中mainPanel的具体显示
	 */
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO ;
		mainPanel.clear();
		setText("查询用户");
		gridFrame.resize(11, 2);
		gridFrame.setWidget(0, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(0, 1, CUSTOMER_ID);
		gridFrame.setWidget(1, 0, NAME_ZH);
		gridFrame.setWidget(1, 1, NAME);
		gridFrame.setWidget(2, 0, PHONE_ZH);
		gridFrame.setWidget(2, 1, PHONE);
		gridFrame.setWidget(3, 0, ADDRESS_ZH);
		gridFrame.setWidget(3, 1, ADDRESS);
		gridFrame.setWidget(4, 0, ADDRESS_2_ZH);
		gridFrame.setWidget(4, 1, ADDRESS_2);
		gridFrame.setWidget(5, 0, EMAIL_ZH);
		gridFrame.setWidget(5, 1, EMAIL);
		gridFrame.setWidget(6, 0, CARD_ID_ZH);
		gridFrame.setWidget(6, 1, CARD_ID);	
		gridFrame.setWidget(7, 0, VALIDITY_ZH);
		gridFrame.setWidget(7, 1, VALIDITY);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);
		gridFrame.setWidget(9, 0, CREATE_TIME_MIN_ZH);
		gridFrame.setWidget(9, 1, CREATE_TIME_MIN);
		gridFrame.setWidget(10, 0, CREATE_TIME_MAX_ZH);
		gridFrame.setWidget(10, 1, CREATE_TIME_MAX);
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}
	
	/**
	 * 绑定客户
	 */
	public void bindMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_TRANS_BIND ;
		trans_timer.scheduleRepeating(1000) ;
		setText("添加业务绑定");
		mainPanel.clear();
		gridFrame.resize(2, 2);
		gridFrame.setWidget(1, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(1, 1, CUSTOMER_ID);	
		gridFrame.setWidget(0, 0, new Label("待绑定业务"));
		gridFrame.setWidget(0, 1, TRANS_ID);
		
		mainPanel.add(gridFrame);
		submit.setText("绑定");
		show();
	}
	
	@Override
	public void subminHandler() {
		
		String id = CUSTOMER_ID.getText();
		String name = NAME.getText();
		String phone = PHONE.getText();
		String address = ADDRESS.getText();
		String address2 = ADDRESS_2.getText();
		String email = EMAIL.getText();
		String cardId = CARD_ID.getText();		
		String addition = ADDITION.getText();
		int selectIndex = VALIDITY.getSelectedIndex();
		String validity =VALIDITY.getValue(selectIndex);
		CustomerData customer = new CustomerData();
		customer.setACTION_NAME(ACTION_NAME);
		if(id != null && !"".equals(id)){
			if(!Util.isNum(id)){
				setMessage("用户索引必须是数字");
				return ;
			}
			customer.setCustomer_id(Integer.parseInt(id));
		}
		
		customer.setName(name);
		customer.setPhone(phone);
		customer.setAddress(address);
		customer.setAddress_2(address2);
		customer.setEmail(email);
		customer.setId_card(cardId);
		
		customer.setValidity(validity);
		customer.setAddition(addition);
	
		if(Constanst.ACTION_NAME_ADD_CUSTOMER_INFO.equals(ACTION_NAME)){				
			if(phone == null || !"".equals(phone.trim())){
				setMessage("电话号码不能为空");
				return ;
			}
			Date createTime = CREATE_TIME.getValue();
			customer.setCreate_time(Util.formatDateToJsonString(createTime));
			String jsonStr = customer.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, CustomerController.RemoteCaller);
			hide();
		}else if(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO.equals(ACTION_NAME)){
			String create_time_min = Util.formatDateToJsonString(CREATE_TIME_MIN.getValue());
			String create_time_max = Util.formatDateToJsonString(CREATE_TIME_MAX.getValue());
			if(!Util.checkStringBulk(create_time_min, create_time_max)){
				setMessage("创建时间最小值不能大于最大值");
				return ;
			}			
			CustomerController.queryData = customer;
			String jsonStr = customer.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, CustomerController.QueryCaller);
			hide();
		}else if(Constanst.ACTION_NAME_ADD_TRANS_BIND.equals(ACTION_NAME)){
			TransBindData transBindData = new TransBindData();
			if(id == null || "".equals(id)){
				setMessage("用户索引不能为空");
				return ;				
			}else{
				transBindData.setCustomer_id(Integer.parseInt(id));
			}
			int transIndex = TRANS_ID.getSelectedIndex() ;
			String trans_id = TRANS_ID.getValue(transIndex);
			if(trans_id == null || "".equals(trans_id)){
				setMessage("绑定业务不能为空");
				return ;				
			}else{
				transBindData.setTrans_id(Integer.parseInt(trans_id));
			}
			String jsonStr = transBindData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;	
			request.getBill(packet, CustomerController.RemoteCaller);
		}
	}

	
}
