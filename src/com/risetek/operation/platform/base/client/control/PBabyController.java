package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
import com.risetek.operation.platform.base.client.model.PBabyData;
import com.risetek.operation.platform.base.client.view.PBabyView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.PBabyConstanst;

public class PBabyController extends AController {

	public static PBabyController INSTANCE = new PBabyController();
	
	final PBabyData data = new PBabyData();
	
	public final PBabyView view = new PBabyView();
	
	public static String pBabyCreateTime = null;
	
	public static String ACTION_NAME = null ;
	
	public static String packet = null;
	
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			if(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO.equals(ACTION_NAME)){
				PBabyData pData = new PBabyData();
				pData.parseDataCustomer(ret);
				pData.setCreate_dateTime(pBabyCreateTime);
				pData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
				packet = pData.toHttpPacket();
				remoteRequest.getBill(packet, QueryCaller);
			}else if(Constanst.ACTION_NAME_EDIT_PBABY.equals(ACTION_NAME)){
				try{
					JSONObject o = JSONParser.parse(ret).isObject();
					String retCode = o.get("flag").isString().stringValue();
					if("1".equals(retCode)){
						remoteRequest.getBill(packet, QueryCaller);
						Window.alert("出票成功");
					}else{
						Window.alert("出票失败");
					}
				}catch (Exception e) {
					Window.alert("出票失败");
				}
			}
			
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	//修改后的查询回调
	public static final RequestCallback QueryCaller = INSTANCE.new QueryRequestCallback();
	class QueryRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();			
			data.parseData(ret);
			view.render(data);
		}
		
		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	
	
	private PBabyController(){
		
	}
	
	/**
	 * 功能：加载数据，会实现一个回调函数
	 *
	 * void
	 */
	public static void load(){
		INSTANCE.data.setSum(10);
		INSTANCE.view.render(INSTANCE.data);
//		remoteRequest.get("", "", RemoteCaller);
	}
	
	/**
	 * 功能：得到模块数据资源
	 *
	 * BaseData
	 * @return
	 */
	public PBabyData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";
		ViewDetailDialog dialog = new ViewDetailDialog();
		public TableEditAction() {
			dialog.submit.addClickHandler(new TransactionEditControl());
		}
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。(这里是行号)
			String rowid = table.getText(row, 1);

			String tisp_value = table.getText(row, col);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			
			switch (col) {
			case 1:
			case 2:	
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:				
				dialog.makeMainPanel(INSTANCE.view.grid , row);
				dialog.submit.setVisible(true);
				dialog.submit.setText("出票");
				dialog.show();
				break;				
			default:
				break;
			}			
			
		}
	
		
		public class TransactionEditControl extends EditController implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {
				ACTION_NAME = Constanst.ACTION_NAME_EDIT_PBABY ;
				Grid grid = ViewDetailDialog.INSTANCE.gridFrame;
				String user = PBabyConstanst.USER_VALUE;
				String orderId = "";
				String keyId = "";
				for(int i = 0 ; i < grid.getRowCount() ; i++){
					if(PBabyConstanst.ORDID_ZH.equals(grid.getText(i, 0))){
						orderId = grid.getText(i, 1);
						continue ;
					}else if(PBabyConstanst.KEYID_ZH.equals(grid.getText(i, 0))){
						keyId = grid.getText(i, 1);
						continue ;
					}
				}
 				StringBuilder sb = new StringBuilder();
 				sb.append(PBabyConstanst.USER);
 				sb.append("=");
 				sb.append(user);
 				sb.append("&");
 				sb.append(PBabyConstanst.ORDID_EDIT);
 				sb.append("=");
 				sb.append(orderId);
 				sb.append("&");
 				sb.append(PBabyConstanst.KEYID_EDIT);
 				sb.append("=");
 				sb.append(keyId);
 				String sendData = sb.toString();
 				remoteRequest.getPBaby(sendData, RemoteCaller);
			}
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}			
		}
	}
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			Object obj = event.getSource();
			pBabyCreateTime = null;
			final PBabyData pBabyData = new PBabyData();
			 if(obj == PBabyView.queryButton){
				String phone = PBabyView.phoneNumber.getText();
				Date createDateTime = PBabyView.createDataTime.getValue();
				pBabyData.setPhoneNumber(phone);
				if(createDateTime != null){
					String createDate = PBabyView.format.format(createDateTime);
					pBabyData.setCreate_dateTime(createDate);
					pBabyCreateTime = createDate;
				}
				
				if(phone != null && !"".equals(pBabyData)){
					ACTION_NAME = Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO;
					pBabyData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO);
					String packet = pBabyData.toHttpPacket();
					remoteRequest.getBill(packet, RemoteCaller);
				}else {
					pBabyData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
					packet = pBabyData.toHttpPacket();
					remoteRequest.getBill(packet, QueryCaller);
				}
				
			}
		}
	}

	/** 
	 * 功能： 时间接口方法，返回该模块视图
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.control.AController#getView()
	 */
	@Override
	public Widget getView() {
		return view;
	}

	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
