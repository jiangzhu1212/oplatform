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
import com.google.gwt.user.client.ui.ListBox;
import com.risetek.operation.platform.base.client.dialog.TransBindButtonDialog;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.base.client.model.TransBindData;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.base.client.view.TransBindView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class TransBindController extends AController {

	public static TransBindController INSTANCE = new TransBindController();
	final TransBindData data = new TransBindData();
	
	public static TransBindData queryData = new TransBindData();
	
	public final TransBindView view = new TransBindView();
	
	public TransBindButtonDialog transBindButtonDialog = null;
	private int pagePoint = 1;
	public ListBox trans_list = null ;
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_TRANS_BIND);
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
				data.parseData(jsa.get(0).isString().stringValue());
				view.render(data);
			} catch (Exception e) {
				// TODO: handle exception
			}
						
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	public static final RequestCallback QueryTransCaller = INSTANCE.new QueryTransRequestCallback();
	//查询transList和channelList的回调
	class QueryTransRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			String ret = response.getText();
			if("".equals(ret)){
				Window.alert("无返回数据");
				return ;
			}
			TransactionData transData = new TransactionData();
			trans_list = new ListBox() ;
			trans_list.addItem("","");
			try {
				JSONArray jsa = JSONParser.parse(ret).isArray();
				transData.parseData(jsa.get(0).isString().stringValue());
				String[][] data0 = transData.getData();
				for (int i = 0; i < data0.length; i++) {
					trans_list.addItem(data0[i][0], data0[i][2]);
				}
			} catch (Exception e) {
				Window.alert("返回数据解析异常");
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	private TransBindController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	/**
	 * 功能：加载数据，会实现一个回调函数
	 *
	 * void
	 */
	public static void load(){
		TransactionData transData = new TransactionData();
		transData.setPAGE_POS(0);
		transData.setPAGE_SIZE(1000);
		transData.setBindable("TRUE");
		transData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO) ;
		transData.setBindable(Constanst.TRUE);
		String transPacket = transData.toHttpPacket();
		remoteRequest.getBill(transPacket, QueryTransCaller);		
	}
	
	/**
	 * 功能：得到模块数据资源
	 *
	 * BaseData
	 * @return
	 */
	public TransBindData getData() {
		return data;
	}
	
	public static class TableEditAction extends BaseTableEditController {
		
		@Override
		public void setGrid() {
			grid = INSTANCE.view.grid;
		}

		@Override
		public void submintHandler() {
			
		}	
	}
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			INSTANCE.transBindButtonDialog = new TransBindButtonDialog();
			Object obj = event.getSource();
			if(obj == TransBindView.queryButton){
				INSTANCE.transBindButtonDialog.queryMainPanel();
			}else if(obj == TransBindView.addButton){
				INSTANCE.transBindButtonDialog.addMainPanel();
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
	public void load(int pagePoint) {
		queryData.setPAGE_POS(pagePoint);
		queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_TRANS_BIND);
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
