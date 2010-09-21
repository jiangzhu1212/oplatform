package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.base.client.dialog.JCardQueryButtonDialog;
import com.risetek.operation.platform.base.client.model.JCardData;
import com.risetek.operation.platform.base.client.view.JCardQueryView;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class JCardQueryContorller extends AController {

	public static JCardQueryContorller INSTANCE = new JCardQueryContorller();
	final JCardData data = new JCardData();
	public static JCardData queryData = new JCardData();
	public final JCardQueryView view = new JCardQueryView();
	private int pagePoint = 1;
	public JCardQueryButtonDialog jCardQueryDialog = null;
	
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
				remoteRequest.getJCard(packet, QueryCaller);
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
			data.parseData(ret);
			resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, data.getSum());
			view.render(data);
		}

		public void onError(Request request, Throwable exception) {
			
		}
	}
	
	private JCardQueryContorller(){
		
	}
	
	/**
	 * 功能：加载数据，会实现一个回调函数
	 *
	 * void
	 */
	public static void load(){
//		INSTANCE.view.render(INSTANCE.data);
//		remoteRequest.get("", "", RemoteCaller);
	}
	
	/**
	 * 功能：得到模块数据资源
	 *
	 * BaseData
	 * @return
	 */
	public JCardData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction extends BaseTableEditController {

		@Override
		public void setGrid() {
			grid = INSTANCE.view.grid;
		}

		@Override
		public void submintHandler() {
			int statusIndex = dialog.list_status.getSelectedIndex();
			String newStatusName = dialog.list_status.getItemText(statusIndex);
			String oldStatusName = dialog.oldValueLabel.getText();
			if(newStatusName.equals(oldStatusName)){
				Window.alert("状态未改变");
				return ;
			}
			String newStatus = dialog.list_status.getValue(statusIndex);
			if(newStatus.trim().equals("")){
				Window.alert("状态不能为空");
				return ;
			}
			JCardData jCardData = new JCardData();
			int rowId = Integer.parseInt(dialog.rowid);
			Grid grid = INSTANCE.view.grid;
			for(int i = 2 ; i < grid.getColumnCount() ; i ++){
				if(grid.getText(0, i).equals(JCardConstanst.SN_ZH)){
					jCardData.setSN(grid.getText(rowId, i));
				}else if(grid.getText(0, i).equals(JCardConstanst.NUMBER_ZH)){
					jCardData.setNUMBER(grid.getText(rowId, i));
				}else if(grid.getText(0, i).equals(JCardConstanst.PWD_ZH)){
					jCardData.setPWD(grid.getText(rowId, i));
				}else if(grid.getText(0, i).equals(JCardConstanst.PAR_VALUE_ZH)){
					jCardData.setPAR_VALUE(grid.getText(rowId, i));
				}
			}
			
			jCardData.setSTATUS(newStatus);
			jCardData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_STATUS);
			String packet = jCardData.toHttpPacket();
			remoteRequest.getJCard(packet, RemoteCaller);
			dialog.hide();}
		
	}
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			INSTANCE.jCardQueryDialog = new JCardQueryButtonDialog();
			Object obj = event.getSource();			
			 if(obj == JCardQueryView.queryButton){
				INSTANCE.jCardQueryDialog.queryMainPanel();
			}else if(obj == JCardQueryView.addButton){
				INSTANCE.jCardQueryDialog.addMainPanel();
			}else if(obj == JCardQueryView.balanceButton){
				INSTANCE.jCardQueryDialog.balancePanel();
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
		queryData.setACTION_NAME(Constanst.ACTION_NAME_SELECT_JCARD);
		String packet = queryData.toHttpPacket();
		remoteRequest.getJCard(packet, QueryCaller);
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
