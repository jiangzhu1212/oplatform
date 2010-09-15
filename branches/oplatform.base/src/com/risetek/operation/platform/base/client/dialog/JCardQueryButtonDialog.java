package com.risetek.operation.platform.base.client.dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.JCardQueryContorller;
import com.risetek.operation.platform.base.client.model.JCardData;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * 2010-9-2
 * 
 */
public class JCardQueryButtonDialog extends BaseButtonDailog {
	
	private final Label SN_ZH = new Label(JCardConstanst.SN_ZH);
	private final Label NUMBER_ZH = new Label(JCardConstanst.NUMBER_ZH);
	private final Label PWD_ZH = new Label(JCardConstanst.PWD_ZH);
	private final Label PAR_VALUE_ZH = new Label(JCardConstanst.PAR_VALUE_ZH);
	private final Label STATUS_ZH = new Label(JCardConstanst.STATUS_ZH);
	private final Label BILL_EXTEND_ID_ZH = new Label(JCardConstanst.BILL_EXTEND_ID_ZH);
	private final Label CREATE_DATE_ZH = new Label(JCardConstanst.CREATE_DATE_ZH);
	
	private final TextBox SN = new TextBox();
	private final TextBox NUMBER = new TextBox();
	private final TextBox PWD = new TextBox();
	private final TextBox PAR_VALUE = new TextBox();
	private final TextBox BILL_EXTEND_ID = new TextBox();
	private final DateBox CREATE_DATE = new DateBox();
	
	private final TextArea updateText = new TextArea();
	
	private final TextArea BALANCE_TEXT = new TextArea();
	
	public final TextArea BALANCE_RIGTH_TEXT = new TextArea();
	
	public final TextArea BALANCE_WRONG_TEXT = new TextArea();
	
	public final ListBox list_status = Util.getJCardStatus();
	
	public StringBuilder sbSuccess = new StringBuilder();
	
	public StringBuilder sbFail = new StringBuilder();
	
	public List<String> jwCard = new ArrayList<String>();
	
	public List<String> jwCheckKard = new ArrayList<String>();
	
	JCardData data = new JCardData();
	
	public  RequestCallback balanceCaller = new BalanceRequestCallback();
	//对账操作的回调
	class BalanceRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request0, Response response) {
			
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			ResolveResponseInfo opRetinfo = (ResolveResponseInfo)data.retInfo(ret);
			if (opRetinfo.getReturnCode()!=Constanst.OP_TRUE)  {
				String returnMessage = opRetinfo.getReturnMessage();
				
				sbFail.append(jwCheckKard.get(0));
				String status = getElecTicketStatusCn(returnMessage);
				sbFail.append("\r\n账单中心状态：" );
				if(status != null){
					sbFail.append(status);
				}				
				jwCheckKard.remove(0);
				sbFail.append("\r\n");
				jwCard.remove(0);
				BALANCE_WRONG_TEXT.setValue(sbFail.toString());
				if(jwCard.size() != 0){					
					request.getJCard(jwCard.get(0), balanceCaller);
				}else{					
					BALANCE_RIGTH_TEXT.setValue(sbSuccess.toString());
					Window.alert("对账完成");				
				}
			}else{
				sbSuccess.append(jwCheckKard.get(0));
				jwCheckKard.remove(0);
				sbSuccess.append("\r\n");
				jwCard.remove(0);
				BALANCE_RIGTH_TEXT.setValue(sbSuccess.toString());
				if(jwCard.size() != 0){
					request.getJCard(jwCard.get(0), balanceCaller);
				}else{
					BALANCE_WRONG_TEXT.setValue(sbFail.toString());
					Window.alert("对账完成");
				}
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	public void addMainPanel(){
		updateText.setSize("350px", "300px");
		ACTION_NAME = Constanst.ACTION_NAME_IMPORT_DATA;
		setText("添加俊卡");
		label.setText("上传的骏卡信息");
		gridFrame = new Grid(1,1);
		gridFrame.resize(1, 1);
		gridFrame.setWidget(0, 0, updateText);			
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void balancePanel(){
		BALANCE_TEXT.setSize("350px", "150px");
		BALANCE_RIGTH_TEXT.setSize("350px", "120px");
		BALANCE_WRONG_TEXT.setSize("350px", "180px");
		ACTION_NAME = Constanst.ACTION_NAME_BALANCE;
		setText("俊卡对账");
		gridFrame = new Grid(6,1);
		gridFrame.setWidget(0, 0, new Label("需要对账数据"));
		gridFrame.setWidget(1, 0, BALANCE_TEXT);
		gridFrame.setWidget(2, 0, new Label("对账成功数据"));
		gridFrame.setWidget(3, 0, BALANCE_RIGTH_TEXT);
		gridFrame.setWidget(4, 0, new Label("对账失败数据"));		
		gridFrame.setWidget(5, 0, BALANCE_WRONG_TEXT);
		mainPanel.add(gridFrame);
		submit.setText("对账");
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_SELECT_JCARD;
		setText("查询骏卡");
		gridFrame.resize(7, 2);
		gridFrame.setWidget(0, 0, SN_ZH);
		gridFrame.setWidget(0, 1, SN);
		gridFrame.setWidget(1, 0, NUMBER_ZH);
		gridFrame.setWidget(1, 1, NUMBER);
		gridFrame.setWidget(2, 0, PWD_ZH);
		gridFrame.setWidget(2, 1, PWD);
		gridFrame.setWidget(3, 0, PAR_VALUE_ZH);
		gridFrame.setWidget(3, 1, PAR_VALUE);
		gridFrame.setWidget(4, 0, STATUS_ZH);
		gridFrame.setWidget(4, 1, list_status);	
		gridFrame.setWidget(5, 0, BILL_EXTEND_ID_ZH);
		gridFrame.setWidget(5, 1, BILL_EXTEND_ID);	
		gridFrame.setWidget(6, 0, CREATE_DATE_ZH);
		gridFrame.setWidget(6, 1, CREATE_DATE);	
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}

	public static String getElecTicketStatusCn(String status) {
		if("free".equals(status)){
			return "可用";
		}else if("lock".equals(status)){
			return "锁定";
		}else if("fail".equals(status)){
			return "失败";
		}else if("cancel".equals(status)){
			return "注销";
		}else if("invalid".equals(status)){
			return "无效";
		}

		return status;
	}

	@Override
	public void subminHandler() {
		// TODO Auto-generated method stub
		
		if(Constanst.ACTION_NAME_IMPORT_DATA.equals(ACTION_NAME)){
			String uploadData = updateText.getText();
			if(uploadData == null || "".equals(uploadData)){
				setMessage("不能传空数据");
				return ;
			}			
			JCardData jCardData = new JCardData();
			jCardData.setUploadData(uploadData);
			jCardData.setACTION_NAME(ACTION_NAME);
			String packet = jCardData.toHttpPacket();
			if(packet == null){
				return ;
			}
			
			request.postJCard(packet, JCardQueryContorller.RemoteCaller);							
			
			hide();		
		}else if(Constanst.ACTION_NAME_BALANCE.equals(ACTION_NAME)){
			String balanceText = BALANCE_TEXT.getText();
			if(balanceText == null || "".equals(balanceText)){
				setMessage("不能传空数据");
				return ;
			}
			sbSuccess = new StringBuilder();					
			sbFail = new StringBuilder();
			JCardData jCardData = new JCardData();
			jCardData.setACTION_NAME(ACTION_NAME);
			jCardData.setBalanceData(balanceText);
			jCardData.toHttpPacketBl();
			jwCard = jCardData.getJCard();
			jwCheckKard = jCardData.getCheckCard();
			if(jwCard.size()>0){
				request.getJCard(jwCard.get(0), balanceCaller);
			}	
		}else if(Constanst.ACTION_NAME_SELECT_JCARD.equals(ACTION_NAME)){
			String sn = SN.getText();	
			String number = NUMBER.getText();
			String pwd = PWD.getText();
			String par_value = PAR_VALUE.getText();
			String bill_extend_id = BILL_EXTEND_ID.getText();
			Date creatTime = CREATE_DATE.getValue();
			String create_date = Util.formatDateToJsonString(creatTime);
			
			int statusIndex = list_status.getSelectedIndex();
			String status = list_status.getValue(statusIndex);
			
			JCardData jCardData = new JCardData();
			jCardData.setACTION_NAME(ACTION_NAME);
			jCardData.setSN(sn);
			jCardData.setNUMBER(number);
			jCardData.setPWD(pwd);
			jCardData.setPAR_VALUE(par_value);
			jCardData.setBILL_EXTEND_ID(bill_extend_id);
			jCardData.setCREATE_DATE(create_date);
			jCardData.setSTATUS(status);
			
			JCardQueryContorller.queryData = jCardData;
			
			String packet = jCardData.toHttpPacket();
			request.getJCard(packet, JCardQueryContorller.QueryCaller);
							
			hide();			
		}
	}
}
