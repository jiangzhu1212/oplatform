package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.risetek.operation.platform.base.client.dialog.BankButtonDialog;
import com.risetek.operation.platform.base.client.model.BankData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.CustomerConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: BankController 
 * @Description: 发卡行模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:02:30 
 * @version 1.0
 */
public class BankController extends AController {

	public static BankController INSTANCE = new BankController();
	final BankData data = new BankData();
	public static BankData queryData = new BankData() ;
	public final BankView view = new BankView();
	public BankButtonDialog bankDialog = null;
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_BANK_INFO);
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
	
	private BankController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	public static void load(){
	}
	
	public BankData getData() {
		return data;
	}
	
	public static class TableEditAction extends BaseTableEditController {
			
			@Override
			public void setGrid() {
				grid = INSTANCE.view.grid;
			}
	
			@Override
			public void submintHandler() {
				BankData editData = new BankData() ;
				editData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_BANK_INFO);
				String row = dialog.rowid;
				String id = INSTANCE.view.grid.getText(Integer.parseInt(row), 2);
				editData.setBank_id(Integer.parseInt(id));
					String colName = dialog.colName;
					if(colName == null || "".equals(colName)){
						
					}else {
						String colValue = null ;
						if(CustomerConstanst.VALIDITY_ZH.equals(colName)){
							int selectIndex = dialog.list_status.getSelectedIndex();
							colValue = dialog.list_status.getValue(selectIndex);
						}else{
							colValue = dialog.newValueBox.getText();
						}
						String jsonStr = editData.toHttpPacket(colName,colValue);
						EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
		 				String json = EPay2Packet.listToString(epay2Packet);
		 				String packet = RequestFactory.PACKET + "="+ json ;
						remoteRequest.getBill(packet, RemoteCaller);
					}
			}
		
	}
		
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			INSTANCE.bankDialog = new BankButtonDialog();
			Object obj = event.getSource();
			if(obj == BankView.addButton){
				INSTANCE.bankDialog.addMainPanel();
			}else if(obj == BankView.queryButton){
				INSTANCE.bankDialog.queryMainPanel();
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
		queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_BANK_INFO);
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
