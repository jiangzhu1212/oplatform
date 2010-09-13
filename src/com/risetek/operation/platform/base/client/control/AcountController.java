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
import com.risetek.operation.platform.base.client.dialog.AcountButtonDialog;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
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
				queryData.setACTION_NAME(Constanst.ACTION_NAME_QUERY_ACCOUNT_INFO);
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

	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";
		private CustomerEditControl edit_control = new CustomerEditControl();
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
				// 选择了删除用户。
				edit_control.setColName(null);
				edit_control.dialog.submit.setText("删除");
				edit_control.dialog.show(rowid, tisp_value);
				break;
				
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				edit_control.setColName(colName);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}			
			
		}
		
		public class CustomerEditControl extends EditController implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {
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
