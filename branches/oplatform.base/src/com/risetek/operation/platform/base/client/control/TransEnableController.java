package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.risetek.operation.platform.base.client.dialog.TransEnableButtonDialog;
import com.risetek.operation.platform.base.client.model.ChannelData;
import com.risetek.operation.platform.base.client.model.TransEnableData;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.base.client.view.TransEnableView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class TransEnableController extends AController {

	public static TransEnableController INSTANCE = new TransEnableController();
	final TransEnableData data = new TransEnableData();
	public static TransEnableData queryData = new TransEnableData();
	public final TransEnableView view = new TransEnableView();
	public TransEnableButtonDialog transEnableButtonDialog = null; 
	private int pagePoint = 1;
	
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	public ListBox trans_list = null ;
	public ListBox channel_list = null ;
	public static String ACTION_NAME = null ;
	//修改操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			ResolveResponseInfo opRetinfo = (ResolveResponseInfo)data.retInfo(ret);
			if (opRetinfo.getReturnCode()!=Constanst.OP_TRUE)  {
				Window.alert(opRetinfo.getReturnMessage());
			}else{
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_TRANS_BIND);
				String packet = queryData.toHttpPacket();				
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
			if(Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO.equals(ACTION_NAME)){
				String ret = response.getText();
				TransactionData transData = new TransactionData();
				ListBox trans_list = new ListBox() ;
				trans_list.addItem("","");
				transData.parseData(ret);
				String[][]  data = transData.getData();
				for(int i = 0 ; i< data.length ; i ++){
					trans_list.addItem(data[i][0],data[i][2]);
				}
			}else if(Constanst.ACTION_NAME_QUERY_CHANNEL_INFO.equals(ACTION_NAME)){
				String ret = response.getText();
				ChannelData channelData = new ChannelData();
				ListBox channel_list = new ListBox() ;
				channel_list.addItem("","");
				channelData.parseData(ret);
				String[][]  data = channelData.getData();
				for(int i = 0 ; i< data.length ; i ++){
					channel_list.addItem(data[i][0],data[i][2]);
				}
			}else {
				data.parseData(response.getText());
				view.render(data);
			}			
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	private TransEnableController(){
		
	}
	
	/**
	 * 功能：加载数据，会实现一个回调函数
	 *
	 * void
	 */
	public static void load(){
		INSTANCE.data.setSum(10);
		INSTANCE.view.render(INSTANCE.data);
		TransactionData transData = new TransactionData();
		transData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_TRANSACTION_INFO);
		String transPacket = transData.toHttpPacket();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_BILL_INFO;
		remoteRequest.getBill(transPacket, QueryCaller);
		ChannelData channelData = new ChannelData();
		channelData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_CHANNEL_INFO);
		String channelPacket = channelData.toHttpPacket();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CHANNEL_INFO;
		remoteRequest.getBill(channelPacket, QueryCaller);
	}
	
	/**
	 * 功能：得到模块数据资源
	 *
	 * BaseData
	 * @return
	 */
	public TransEnableData getData() {
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
			INSTANCE.transEnableButtonDialog = new TransEnableButtonDialog();
			Object obj = event.getSource();			
			if(obj == TransEnableView.queryButton){
				INSTANCE.transEnableButtonDialog.queryMainPanel();
			}else if(obj == TransEnableView.addButton){
				INSTANCE.transEnableButtonDialog.addMainPanel();
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
		// TODO Auto-generated method stub
		queryData.setPAGE_POS(pagePoint);
		String paceket = queryData.toHttpPacket();
		remoteRequest.getBill(paceket, QueryCaller);
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
