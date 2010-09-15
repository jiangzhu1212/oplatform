package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.risetek.operation.platform.base.client.dialog.BankButtonDialog;
import com.risetek.operation.platform.base.client.model.BankData;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
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

	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_ANNOUCEMENT_INFO);
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
			data.parseData(response.getText());
			view.render(data);
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	private BankController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
		//remoteRequest.get("", "", RemoteCaller);
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
				editData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_CUSTOMER_INFO);
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
							colValue = dialog.getText();
						}
						String packet = editData.toHttpPacket(colName,colValue);
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
	public void load(int pagePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPagePoint() {
		// TODO Auto-generated method stub
		return 0;
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
