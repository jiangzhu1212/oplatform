package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.RadioButton;
import com.risetek.operation.platform.base.client.dialog.TransEnableButtonDialog;
import com.risetek.operation.platform.base.client.model.TransEnableData;
import com.risetek.operation.platform.base.client.view.TransEnableView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class TransEnableController extends AController {

	public static TransEnableController INSTANCE = new TransEnableController();
	final TransEnableData data = new TransEnableData();
	
	public final TransEnableView view = new TransEnableView();
	public TransEnableButtonDialog transEnableButtonDialog = new TransEnableButtonDialog(); 
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//修改操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			data.parseData(response.getText());
			view.render(data);
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
	private TransEnableController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
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
	public TransEnableData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";

		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			String rowid = table.getText(row, 2);

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
				((RadioButton)INSTANCE.view.grid.getWidget(row, 0)).setValue(true);
				break;
			
			default:
				break;
			}			
			
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
				INSTANCE.transEnableButtonDialog.setAction_name(Constanst.ACTION_NAME_QUERY_TRANS_ENABLE);
				INSTANCE.transEnableButtonDialog.queryMainPanel();
				INSTANCE.transEnableButtonDialog.show();
			}else if(obj == TransEnableView.addButton){
				INSTANCE.transEnableButtonDialog.setAction_name(Constanst.ACTION_NAME_ADD_TRANS_ENABLE);
				INSTANCE.transEnableButtonDialog.addMainPanel();
				INSTANCE.transEnableButtonDialog.show();
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
