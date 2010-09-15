package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.EGoodsController;
import com.risetek.operation.platform.base.client.model.EGoodsData;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

public class EGoodsButtonDialog extends BaseButtonDailog {
	
	private Label E_GOODS_ID_ZH = new Label(EGoodConstanst.E_GOODS_ID_ZH);
	
	private Label E_GOODS_SN_ZH = new Label(EGoodConstanst.E_GOODS_SN_ZH);
	
	private Label TRANS_ID_ZH = new Label(EGoodConstanst.TRANS_ID_ZH);
	
	private Label CUSTOMER_ID_ZH = new Label(EGoodConstanst.CUSTOMER_ID_ZH);
	
	private Label DESCRIPTION_ZH = new Label(EGoodConstanst.DESCRIPTION_ZH);
	
	private Label INFO_ZH = new Label(EGoodConstanst.INFO_ZH);
	
	private Label CREATE_TIME_ZH = new Label(EGoodConstanst.CREATE_TIME_ZH);
	
	private Label BOLISH_TIME_ZH = new Label(EGoodConstanst.BOLISH_TIME_ZH);
	
	private Label USED_TIME_ZH = new Label(EGoodConstanst.USED_TIME_ZH);
	
	private Label STATUS_ZH = new Label(EGoodConstanst.STATUS_ZH);
	
	private Label THIRD_STATUS_ZH = new Label(EGoodConstanst.THIRD_STATUS_ZH);
	
	private Label ADDITION_ZH = new Label(EGoodConstanst.ADDITION_ZH);
	
	private Label VALIDITY_ZH = new Label(EGoodConstanst.VALIDITY_ZH);
	
	private Label CREATE_TIME_MIN_ZH = new Label(EGoodConstanst.CREATE_TIME_MIN_ZH);
	
	private Label CREATE_TIME_MAX_ZH = new Label(EGoodConstanst.CREATE_TIME_MAX_ZH);
	
	private Label BOLISH_TIME_MIN_ZH = new Label(EGoodConstanst.BOLISH_TIME_MIN_ZH);
	
	private Label BOLISH_TIME_MAX_ZH = new Label(EGoodConstanst.BOLISH_TIME_MAX_ZH);
	
	private Label USED_TIME_MIN_ZH = new Label(EGoodConstanst.USED_TIME_MIN_ZH);
	
	private Label USED_TIME_MAX_ZH = new Label(EGoodConstanst.USED_TIME_MAX_ZH);
	
	
	private TextBox E_GOODS_ID = new TextBox();
	
	private TextBox E_GOODS_SN = new TextBox();
	
	private TextBox TRANS_ID = new TextBox();
	
	private TextBox CUSTOMER_ID = new TextBox();
	
	private TextBox DESCRIPTION = new TextBox();
	
	private TextBox INFO = new TextBox();
	
	private TextBox ADDITION = new TextBox();
	
	private ListBox STATUS = Util.getBillStatus() ;
	
	private ListBox THIRD_STATUS = Util.getThirdStatus() ;
	
	private ListBox VALIDITY = Util.getValidity() ;
	
	private DateBox CREATE_TIME = new DateBox();
	
	private DateBox BOLISH_TIME = new DateBox();
	
	private DateBox USED_TIME = new DateBox();
	
	private DateBox CREATE_TIME_MIN = new DateBox();
	
	private DateBox BOLISH_TIME_MIN = new DateBox();
	
	private DateBox USED_TIME_MIN = new DateBox();
	
	private DateBox CREATE_TIME_MAX = new DateBox();
	
	private DateBox BOLISH_TIME_MAX = new DateBox();
	
	private DateBox USED_TIME_MAX = new DateBox();
	
	
	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_GOODS_INFO ;
		mainPanel.clear();
		setText("添加电子货物");
		gridFrame.resize(12, 2);
		gridFrame.setWidget(0, 0, E_GOODS_SN_ZH);
		gridFrame.setWidget(0, 1, E_GOODS_SN);
		gridFrame.setWidget(1, 0, TRANS_ID_ZH);
		gridFrame.setWidget(1, 1, TRANS_ID);
		gridFrame.setWidget(2, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(2, 1, CUSTOMER_ID);
		gridFrame.setWidget(3, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(3, 1, DESCRIPTION);
		gridFrame.setWidget(4, 0, INFO_ZH);
		gridFrame.setWidget(4, 1, INFO);
		gridFrame.setWidget(5, 0, CREATE_TIME_ZH);
		gridFrame.setWidget(5, 1, CREATE_TIME);
		gridFrame.setWidget(6, 0, BOLISH_TIME_ZH);
		gridFrame.setWidget(6, 1, BOLISH_TIME);
		gridFrame.setWidget(7, 0, USED_TIME_ZH);
		gridFrame.setWidget(7, 1, USED_TIME);
		gridFrame.setWidget(8, 0, STATUS_ZH);
		gridFrame.setWidget(8, 1, STATUS);	
		gridFrame.setWidget(9, 0, THIRD_STATUS_ZH);
		gridFrame.setWidget(9, 1, THIRD_STATUS);
		gridFrame.setWidget(10, 0, ADDITION_ZH);
		gridFrame.setWidget(10, 1, ADDITION);
		gridFrame.setWidget(11, 0, VALIDITY_ZH);
		gridFrame.setWidget(11, 1, VALIDITY);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_GOODS_INFO ;
		mainPanel.clear();
		setText("查询电子货物");
		gridFrame.resize(16, 2);
		gridFrame.setWidget(0, 0, E_GOODS_ID_ZH);
		gridFrame.setWidget(0, 1, E_GOODS_ID);
		gridFrame.setWidget(1, 0, E_GOODS_SN_ZH);
		gridFrame.setWidget(1, 1, E_GOODS_SN);
		gridFrame.setWidget(2, 0, TRANS_ID_ZH);
		gridFrame.setWidget(2, 1, TRANS_ID);
		gridFrame.setWidget(3, 0, CUSTOMER_ID_ZH);
		gridFrame.setWidget(3, 1, CUSTOMER_ID);
		gridFrame.setWidget(4, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(4, 1, DESCRIPTION);
		gridFrame.setWidget(5, 0, INFO_ZH);
		gridFrame.setWidget(5, 1, INFO);
		gridFrame.setWidget(6, 0, STATUS_ZH);
		gridFrame.setWidget(6, 1, STATUS);	
		gridFrame.setWidget(7, 0, THIRD_STATUS_ZH);
		gridFrame.setWidget(7, 1, THIRD_STATUS);
		gridFrame.setWidget(8, 0, ADDITION_ZH);
		gridFrame.setWidget(8, 1, ADDITION);
		gridFrame.setWidget(9, 0, VALIDITY_ZH);
		gridFrame.setWidget(9, 1, VALIDITY);
		gridFrame.setWidget(10, 0, CREATE_TIME_MIN_ZH);
		gridFrame.setWidget(10, 1, CREATE_TIME_MIN);
		gridFrame.setWidget(11, 0, CREATE_TIME_MAX_ZH);
		gridFrame.setWidget(11, 1, CREATE_TIME_MAX);
		gridFrame.setWidget(12, 0, BOLISH_TIME_MIN_ZH);
		gridFrame.setWidget(12, 1, BOLISH_TIME_MIN);
		gridFrame.setWidget(13, 0, BOLISH_TIME_MAX_ZH);
		gridFrame.setWidget(13, 1, BOLISH_TIME_MAX);
		gridFrame.setWidget(14, 0, USED_TIME_MIN_ZH);
		gridFrame.setWidget(14, 1, USED_TIME_MIN);
		gridFrame.setWidget(15, 0, USED_TIME_MAX_ZH);
		gridFrame.setWidget(15, 1, USED_TIME_MAX);		
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}

	@Override
	public void subminHandler() {
		String e_goods_sn = E_GOODS_SN.getText();
		String trans_id = TRANS_ID.getText() ;
		String customer_id = CUSTOMER_ID.getText() ;
		String description = DESCRIPTION.getText() ;
		String info = INFO.getText() ;			
		String addition = ADDITION.getText();
		int statusIndex = STATUS.getSelectedIndex() ;
		String status = STATUS.getValue(statusIndex);
		int third_status_index = THIRD_STATUS.getSelectedIndex() ;
		String third_status = THIRD_STATUS.getValue(third_status_index);
		int validity_index = VALIDITY.getSelectedIndex() ;
		String validity = VALIDITY.getValue(validity_index);
		
		EGoodsData egoodsdata = new EGoodsData() ;
		egoodsdata.setACTION_NAME(ACTION_NAME);
		if(e_goods_sn != null && !"".equals(e_goods_sn.trim())){
			egoodsdata.setE_goods_sn(Integer.parseInt(e_goods_sn));
		}
		if(trans_id != null && !"".equals(trans_id.trim())){
			egoodsdata.setTrans_id(Integer.parseInt(trans_id));
		}
		if(customer_id != null && !"".equals(customer_id.trim())){
			egoodsdata.setCustomer_id(Integer.parseInt(customer_id));
		}
		egoodsdata.setDescription(description);
		egoodsdata.setInfo(info);
		egoodsdata.setAddition(addition);
		egoodsdata.setStatus(status);
		egoodsdata.setThird_status(third_status);
		egoodsdata.setValidity(validity);
		
		if(Constanst.ACTION_NAME_ADD_GOODS_INFO.equals(ACTION_NAME)){
			if(e_goods_sn == null || "".equals(e_goods_sn.trim())){
				setMessage("货物编号不能为空");
				return ;
			}
			if(trans_id == null || "".equals(trans_id.trim())){
				setMessage("业务编号不能为空");
				return ;
			}
			if(customer_id == null || "".equals(customer_id.trim())){
				setMessage("用户编号不能为空");
				return ;
			}
			if(CREATE_TIME.getValue() == null){
				setMessage("创建时间不能为空");
				return ;
			}
			if(BOLISH_TIME.getValue() == null){
				setMessage("失效时间不能为空");
				return ;
			}
			String create_time = Util.formatDateToJsonString(CREATE_TIME.getValue());
			String bolish_time = Util.formatDateToJsonString(BOLISH_TIME.getValue());
			String used_time = Util.formatDateToJsonString(USED_TIME.getValue());
			egoodsdata.setCreate_time(create_time);
			egoodsdata.setBolish_time(bolish_time);
			egoodsdata.setUsed_time(used_time);
			String packet = egoodsdata.toHttpPacket();
			request.getBill(packet, EGoodsController.RemoteCaller);
			hide();
		}else if(Constanst.ACTION_NAME_QUERY_GOODS_INFO.equals(ACTION_NAME)){
			String e_goods_id = E_GOODS_ID.getText() ;
			if(e_goods_id != null && !"".equals(e_goods_id.trim())){
				egoodsdata.setE_goods_id(Integer.parseInt(e_goods_id));
			}				
			String create_time_min = Util.formatDateToJsonString(CREATE_TIME_MIN.getValue());
			String create_time_max = Util.formatDateToJsonString(CREATE_TIME_MAX.getValue());
			String bolish_time_min = Util.formatDateToJsonString(BOLISH_TIME_MIN.getValue());
			String bolish_time_max = Util.formatDateToJsonString(BOLISH_TIME_MAX.getValue());
			String used_time_min = Util.formatDateToJsonString(USED_TIME_MIN.getValue());
			String used_time_max = Util.formatDateToJsonString(USED_TIME_MAX.getValue());
			if(!Util.checkStringBulk(create_time_min, create_time_max)){
				setMessage("创建时间时间最小值不能大于最大值");
				return ;
			}
			if(!Util.checkStringBulk(bolish_time_min, bolish_time_max)){
				setMessage("失效时间时间最小值不能大于最大值");
				return ;
			}
			if(!Util.checkStringBulk(used_time_min, used_time_max)){
				setMessage("使用时间时间最小值不能大于最大值");
				return ;
			}
			egoodsdata.setCreate_time_min(create_time_min);
			egoodsdata.setCreate_time_max(create_time_max);
			egoodsdata.setBolish_time_min(bolish_time_min);
			egoodsdata.setBolish_time_max(bolish_time_max);
			egoodsdata.setUsed_time_min(used_time_min);
			egoodsdata.setUsed_time_max(used_time_max);
			
			EGoodsController.queryData = egoodsdata;
			String packet = egoodsdata.toHttpPacket();
			request.getBill(packet, EGoodsController.QueryCaller);
			hide();
		}
	}
}
