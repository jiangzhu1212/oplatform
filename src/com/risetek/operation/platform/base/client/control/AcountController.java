package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.risetek.operation.platform.base.client.dialog.AcountButtonDialog;
import com.risetek.operation.platform.base.client.model.AcountData;
import com.risetek.operation.platform.base.client.view.AcountView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.AcountConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: AcountController 
 * @Description: 银行卡列表模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:00:43 
 * @version 1.0
 */
public class AcountController extends AController {

	public static AcountController INSTANCE = new AcountController();
	final AcountData data = new AcountData();
	public static AcountData queryData = new AcountData() ;
	public final AcountView view = new AcountView();
	public AcountButtonDialog acountButtonDialog = new AcountButtonDialog();
	private int pagePoint = 1;
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_SELECT_JCARD);
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
	
	private AcountController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
		//remoteRequest.get("", "", RemoteCaller);
	}
	
	public AcountData getData() {
		return data;
	}

	public static class TableEditAction extends BaseTableEditController {
		
		@Override
		public void setGrid() {
			grid = INSTANCE.view.grid;
		}
		
		@Override
		public void submintHandler() {
			AcountData editData = new AcountData() ;
			editData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_ACCOUNT_INFO);
			String row = dialog.rowid;
			String account_number = INSTANCE.view.grid.getText(Integer.parseInt(row), 2);
			editData.setAccount_number(account_number);
				String colName = dialog.colName;
				if(colName == null || "".equals(colName)){
					
				}else {
					String colValue = null ;
					if(AcountConstanst.VALIDITY_ZH.equals(colName)){
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
			INSTANCE.acountButtonDialog = new AcountButtonDialog();
			Object obj = event.getSource();
			if(obj == AcountView.addButton){
				INSTANCE.acountButtonDialog.addMainPanel();
			}else if(obj == AcountView.queryButton){
				INSTANCE.acountButtonDialog.queryMainPanel();
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
