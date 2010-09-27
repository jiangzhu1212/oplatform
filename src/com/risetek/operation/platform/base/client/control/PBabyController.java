package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.PBabyData;
import com.risetek.operation.platform.base.client.view.PBabyView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.PBabyConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class PBabyController extends AController {

	public static PBabyController INSTANCE = new PBabyController();
	
	final PBabyData data = new PBabyData();
	
	public final PBabyView view = new PBabyView();
	
	public static String pBabyCreateTime = null;
	
	public static String ACTION_NAME = null ;
	
	public static PBabyData queryData = new PBabyData();
	private int pagePoint = 0;
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//操作的回调  查询电子货物还需要设已支付 现在还没设
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			if("".equals(ret)){
				Window.alert("无返回数据");
				return ;
			}
			if(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO.equals(ACTION_NAME)){
				PBabyData pData = new PBabyData();
				JSONArray jsa = JSONParser.parse(ret).isArray();
				pData.parseDataCustomer(jsa.get(0).isString().stringValue());
				pData.setCreate_dateTime(pBabyCreateTime);
				pData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
				queryData = pData ;
				String jsonStr = queryData.toHttpPacket();
				EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
				String json = EPay2Packet.listToString(epay2Packet);
				String packet = RequestFactory.PACKET + "="+ json ;
				remoteRequest.getBill(packet, QueryCaller);
			}else if(Constanst.ACTION_NAME_EDIT_PBABY.equals(ACTION_NAME)){
				try{
					JSONObject o = JSONParser.parse(ret).isObject();
					String retCode = o.get("flag").isString().stringValue();
					if("1".equals(retCode)){
						String jsonStr = queryData.toHttpPacket();
						EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
						String json = EPay2Packet.listToString(epay2Packet);
						String packet = RequestFactory.PACKET + "="+ json ;
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
			if("".equals(ret)){
				Window.alert("无返回数据");
				return ;
			}
			try {
				JSONArray jsa = JSONParser.parse(ret).isArray();
				data.parseData(jsa.get(0).isString().stringValue());
				view.render(data);
			} catch (Exception e) {
				// TODO: handle exception
			}
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
			Object obj = event.getSource() ;
			if(obj == PBabyView.checkTicket){
				List<Integer> list = Util.getCheckedRow(INSTANCE.view.grid);
				if(list.size() != 1){
					Window.alert("请选择一行数据执行操作");
					return ;
				}
				int row = list.get(0);
				
				dialog.makeMainPanel(INSTANCE.view.grid , row);
				dialog.submit.setVisible(true);
				dialog.submit.setText("出票");
				dialog.show();	
			}
			
			
		}
	
		
		public class TransactionEditControl implements ClickHandler {
						
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
					String createDate = Util.formatDateToStringByStr2(createDateTime, "yyyyMMdd");
					pBabyData.setCreate_dateTime(createDate);
					pBabyCreateTime = createDate;
				}
				
				if(phone != null && !"".equals(pBabyData)){
					ACTION_NAME = Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO;
					pBabyData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_CUSTOMER_INFO);
					String jsonStr = pBabyData.toHttpPacket();
					EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
	 				String json = EPay2Packet.listToString(epay2Packet);
	 				String packet = RequestFactory.PACKET + "="+ json ;
					remoteRequest.getBill(packet, RemoteCaller);
				}else {
					pBabyData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
					PBabyController.queryData = pBabyData;
					String jsonStr = pBabyData.toHttpPacket();
					EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
	 				String json = EPay2Packet.listToString(epay2Packet);
	 				String packet = RequestFactory.PACKET + "="+ json ;
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
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPlatformData getChildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(int page) {
		if(pagePoint == 0){
			return ;
		}
		queryData.setPAGE_POS(page);
		queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
		String jsonStr = queryData.toHttpPacket();
		EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
		String json = EPay2Packet.listToString(epay2Packet);
		String packet = RequestFactory.PACKET + "="+ json ;
		remoteRequest.getBill(packet, QueryCaller);
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPagePoint(int point) {
		pagePoint = point;
	}

	@Override
	public int getPagePoint() {
		return pagePoint;
	}

	@Override
	public void setChildPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getChildPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}
}
