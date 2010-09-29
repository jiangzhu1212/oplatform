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
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.base.client.model.EGoodsData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.MingYAppendMoneyData;
import com.risetek.operation.platform.base.client.view.MingYAppendMoneyView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.MINGYAppendMoneryConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class MingYAppendMoneyController extends AController {

	public static MingYAppendMoneyController INSTANCE = new MingYAppendMoneyController();
	final MingYAppendMoneyData data = new MingYAppendMoneyData();
	public static EGoodsData queryData = new EGoodsData() ;
	public final MingYAppendMoneyView view = new MingYAppendMoneyView();
	private int pagePoint = 0;
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//修改操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			if("".equals(ret)){
				Window.alert("无返回数据");
				return ;
			}
			List<EPay2Packet> list = EPay2Packet.listfromString(ret);
			if (list.get(0).getActionReturnCode()!=Constanst.OP_TRUE)  {
				Window.alert(Constanst.FAIL+"\n"+list.get(0).getActionReturnMessage());
			}else{
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO);
				String jsonStr = queryData.toHttpPacket();
				EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
				String json = EPay2Packet.listToString(epay2Packet);
				String packet = RequestFactory.PACKET + "="+ json ;
				remoteRequest.getBill(packet, QueryCaller);
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	//查询的回调
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
				data.parseData(jsa.get(0).isObject().toString());
				view.render(data);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	private MingYAppendMoneyController(){
	}
	
	public static void load(){
	}
	
	public MingYAppendMoneyData getData() {
		return data;
	}

	public static class TableEditAction implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			
			Grid grid = INSTANCE.view.grid;

			MingYAppendMoneyData editData = new MingYAppendMoneyData() ;
			editData.setACTION_NAME(Constanst.ACTION_NAME_NOTICE_REQUEST);
			int billIdCol = Util.getColumNum(grid, MINGYAppendMoneryConstanst.BILL_ID_ZH) ;
			int orderIdCol = Util.getColumNum(grid, MINGYAppendMoneryConstanst.ORDER_ID_ZH) ;
			int settlementAccountCol = Util.getColumNum(grid, MINGYAppendMoneryConstanst.SETTLEMENT_ACCOUNT_ZH);
			int amountCol = Util.getColumNum(grid, MINGYAppendMoneryConstanst.AMOUNT_ZH) ;
			int billInfoCol = Util.getColumNum(grid, MINGYAppendMoneryConstanst.BILL_INFO_ZH) ;
			List<Integer> rowList = Util.getCheckedRow(grid);
			if(rowList.size() != 1){
				Window.alert("请选择一行数据执行操作");
				return ;
			}
			int row = rowList.get(0);
			
			String bill_id = grid.getText(row, billIdCol);
			String order_id = grid.getText(row, orderIdCol);
			String settlement_account = grid.getText(row, settlementAccountCol);
			String amount = grid.getText(row, amountCol);
			String bill_info = grid.getText(row, billInfoCol);				
			
			editData.setBill_id(bill_id);
			editData.setOrder_id(order_id);
			editData.setSettlement_account(settlement_account);
			if(amount != null && !"".equals(amount)){
				editData.setAmount(amount.replaceAll("\\.", ""));
			}
			editData.setBill_info(bill_info);
			editData.setDate_time(Util.formatDateToStringByStr2(new Date(),"yyyy-MM-dd HH:mm:ss"));
			
			String jsonStr = editData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "=" + json ;
			remoteRequest.getBill(packet, RemoteCaller);
			
		}
	
	}
		
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
			Object obj = event.getSource();
			if(obj == MingYAppendMoneyView.queryButton){
				EGoodsData eGoodsData = new EGoodsData();
				eGoodsData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_GOODS_INFO) ;
				int noticeIndex = MingYAppendMoneyView.noticeStatus.getSelectedIndex() ;
				String noticeStatus = MingYAppendMoneyView.noticeStatus.getValue(noticeIndex);
				Date date = MingYAppendMoneyView.payDataTime.getValue() ;
				String payDate = Util.formatDateToStringByStr2(date, "yyyyMMdd");
				
				eGoodsData.setInfo("STATUS\":\""+noticeStatus);
				eGoodsData.setCreate_time_max(payDate+"235959");
				eGoodsData.setCreate_time_min(payDate+"000000");
				eGoodsData.setTrans_id(Constanst.MINGY_APPEND_MONEY);
				String jsonStr = eGoodsData.toHttpPacket();
				EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
				String json = EPay2Packet.listToString(epay2Packet);
				String packet = RequestFactory.PACKET + "="+ json ;	
				remoteRequest.getBill(packet, MingYAppendMoneyController.QueryCaller);	
				
				queryData = eGoodsData ;
			}
		}
		
	}

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
	public void load(int page) {
		// TODO Auto-generated method stub
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

	@Override
	public OPlatformData getChildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
		// TODO Auto-generated method stub
		
	}
}
