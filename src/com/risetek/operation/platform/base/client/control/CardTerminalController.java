package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.dialog.CardTerminalButtonDialog;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
import com.risetek.operation.platform.base.client.model.CardTerminalData;
import com.risetek.operation.platform.base.client.view.CardTerminalView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class CardTerminalController extends AController {

	public static CardTerminalController INSTANCE = new CardTerminalController();
	final CardTerminalData data = new CardTerminalData();
	public static CardTerminalData queryData = new CardTerminalData();
	public final CardTerminalView view = new CardTerminalView();
	public CardTerminalButtonDialog cardTerminalButtonDialog = new CardTerminalButtonDialog();
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_CARD_TERMINAL);
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
	private CardTerminalController(){
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
	public CardTerminalData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";
		public CardTerminalEditControl edit_control = new CardTerminalEditControl();
		public TableEditAction() {
			edit_control.setColName(null);	
			edit_control.dialog.submit.addClickHandler(edit_control);
		}
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
			String rowid = table.getText(row, 1);
			String colName = table.getText(0, col);
			String tisp_value = table.getText(row, col);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			switch (col) {
			case 1:
				ViewDetailDialog dialog = ViewDetailDialog.INSTANCE;
				dialog.makeMainPanel(INSTANCE.view.grid , row);
				dialog.show();
				break;
			case 2:
				// 选择了删除业务。
				edit_control.setColName(null);	
				edit_control.dialog.submit.setText("删除");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;

			case 3:
			case 4:
			case 5:
			case 6:				
				edit_control.setColName(colName);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}			
			
		}
		
		public class CardTerminalEditControl extends EditController implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				CardTerminalData editData = new CardTerminalData() ;
				editData.setACTION_NAME(Constanst.ACTION_NAME_MODIFY_CARD_TERMINAL);
				String row = dialog.rowid;
				String id = INSTANCE.view.grid.getText(Integer.parseInt(row), 2);
				editData.setTerminal_id(Integer.parseInt(id));
				String colName = dialog.colName;
				if(colName == null || "".equals(colName)){
					
				}else {
					String colValue = null ;
					if(TransactionConstanst.VALIDITY_ZH.equals(colName)){
						int selectIndex = dialog.list_status.getSelectedIndex();
						colValue = dialog.list_status.getValue(selectIndex);
					}else{
						colValue = dialog.getText();
					}
					String packet = editData.toHttpPacket(colName,colValue);
					remoteRequest.getBill(packet, RemoteCaller);
				}
				dialog.hide();
			}
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}

	}

	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			INSTANCE.cardTerminalButtonDialog = new CardTerminalButtonDialog();
			Object obj = event.getSource();
			if(obj == CardTerminalView.addButton){
				INSTANCE.cardTerminalButtonDialog.addMainPanel();
			}else if(obj == CardTerminalView.queryButton){
				INSTANCE.cardTerminalButtonDialog.queryMainPanel();
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