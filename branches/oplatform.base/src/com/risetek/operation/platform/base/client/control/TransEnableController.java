package com.risetek.operation.platform.base.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.model.TransEnableData;
import com.risetek.operation.platform.base.client.view.TransEnableView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

public class TransEnableController extends AController {

	public static TransEnableController INSTANCE = new TransEnableController();
	final TransEnableData data = new TransEnableData();
	
	public final TransEnableView view = new TransEnableView();
	private static RequestFactory remoteRequest = new RequestFactory();
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
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
	
	public static abstract class DialogControl {
		protected abstract CustomDialog getDialog();
		
		RequestCallback myCaller = new myRemoteRequestCallback();
		class myRemoteRequestCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				int code = response.getStatusCode();
				//这里执行一个查询操作
			}
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
				
			case 2:
				// 选择了删除业务。
				TransactionDelControl del_control = new TransactionDelControl();
				del_control.dialog.submit.setText("删除");
				del_control.dialog.submit.addClickHandler(del_control);
				del_control.dialog.show(rowid, tisp_value);
				break;

			
			default:
				break;
			}			
			
		}
		
		public class TransactionDelControl extends DialogControl implements ClickHandler {
			public TransactionDelDialog dialog = new TransactionDelDialog();

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("你好");
				
				//delRow(dialog.rowid, myCaller);
			}
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
		
		public class TransactionDelDialog extends CustomDialog{
			
			Label  info = new Label("您确定删除该业务？");
			public String rowid = null;
			public TransactionDelDialog() {
				mainPanel.add(info);
			}
			public void show(String tips_id, String tips_imsi) {
				rowid = tips_id;
				setText("业务编号：" + tips_id);
				super.show();
			}
		}

	}
	
	public static class TransTableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑业务绑定";
		
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
			
			case 2:
				// 选择了修改业务绑定。
				
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
			
		}
	}

	/** 
	 * 功能： 时间接口方法，返回该模块视图
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.control.AController#getView()
	 */
	@Override
	public Widget getView() {
		return view;
	}
}
