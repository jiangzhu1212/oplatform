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
import com.risetek.operation.platform.base.client.dialog.AnnoucementButtonDialog;
import com.risetek.operation.platform.base.client.model.AnnoucementData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.view.AnnoucementView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: AnnoucementController 
 * @Description: 公告模块控制器实体
 * @author JZJ 
 * @date 2010-8-27 上午11:02:51 
 * @version 1.0
 */
public class AnnoucementController extends AController {

	public static AnnoucementController INSTANCE = new AnnoucementController();
	final AnnoucementData data = new AnnoucementData();
	public static AnnoucementData queryData = new AnnoucementData() ;
	public final AnnoucementView view = new AnnoucementView();
	public AnnoucementButtonDialog acountmentButtonDialog = new AnnoucementButtonDialog();
	private int pagePoint = 1;
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//修改操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			List<EPay2Packet> list = EPay2Packet.listfromString(ret);
			if (list.get(0).getActionReturnCode()!=Constanst.OP_TRUE)  {
				Window.alert(Constanst.FAIL+"\n"+list.get(0).getActionReturnMessage());
			}else{
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_ACCOUNT_INFO);
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
	public static final RequestCallback QueryCaller = INSTANCE.new RemoteRequestCallback();
	class QueryRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			JSONArray jsa = JSONParser.parse(ret).isArray();
			data.parseData(jsa.get(0).isString().stringValue());
			view.render(data);
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	private AnnoucementController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
		//remoteRequest.get("", "", RemoteCaller);
	}
	
	public AnnoucementData getData() {
		return data;
	}

	public static class TableEditAction extends BaseTableEditController {
		
		@Override
		public void setGrid() {
			grid = INSTANCE.view.grid;
		}

		@Override
		public void submintHandler() {
			AnnoucementData editData = new AnnoucementData() ;
			editData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_ANNOUCEMENT_INFO);
			String row = dialog.rowid;
			String id = INSTANCE.view.grid.getText(Integer.parseInt(row), 2);
			editData.setAce_id(Integer.parseInt(id));
				String colName = dialog.colName;
				if(colName == null || "".equals(colName)){
					
				}else {
					String colValue = null ;
					if(AnnoucementConstanst.VALIDITY_ZH.equals(colName)){
						int selectIndex = dialog.list_status.getSelectedIndex();
						colValue = dialog.list_status.getValue(selectIndex);
					}else if(AnnoucementConstanst.CREATE_TIME_ZH.equals(colName) || AnnoucementConstanst.STOP_TIME_ZH.equals(colName)){
						colValue = Util.formatDateToJsonString(dialog.DATE_BOX.getValue());
					}else{
						colValue = dialog.getText();
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
			INSTANCE.acountmentButtonDialog = new AnnoucementButtonDialog();
			Object obj = event.getSource();
			if(obj == AnnoucementView.addButton){
				INSTANCE.acountmentButtonDialog.addMainPanel();
			}else if(obj == AnnoucementView.queryButton){
				INSTANCE.acountmentButtonDialog.queryMainPanel();
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
	public void load(int pagePoint) {
		// TODO Auto-generated method stub
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
