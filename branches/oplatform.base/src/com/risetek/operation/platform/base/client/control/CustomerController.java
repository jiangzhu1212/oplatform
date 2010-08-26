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
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.model.CustomerData;
import com.risetek.operation.platform.base.client.view.CustomerView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

public class CustomerController extends AController {

	public static CustomerController INSTANCE = new CustomerController();
	final CustomerData data = new CustomerData();
	
	public final CustomerView view = new CustomerView();

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
	
	private CustomerController(){
//		String name = new TableEditAction().getActionName();
//		System.out.println(name);
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
		//remoteRequest.get("", "", RemoteCaller);
	}
	
	public CustomerData getData() {
		return data;
	}

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
			CustomerEditControl edit_control = new CustomerEditControl();
			switch (col) {
				
			case 2:
				// 选择了删除用户。
				UserDelControl del_control = new UserDelControl();
				del_control.dialog.submit.setText("删除");
				del_control.dialog.submit.addClickHandler(del_control);
				del_control.dialog.show(rowid, tisp_value);
				break;

			case 3:
				edit_control.setColName(CustomerView.columns[1]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 4:
				edit_control.setColName(CustomerView.columns[2]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 5:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[3]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 6:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[4]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 7:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[5]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 8:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[6]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 9:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[7]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 10:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[8]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 11:
				// 选择了删除用户。
				edit_control.setColName(CustomerView.columns[9]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}			
			
		}
		
		public class UserDelControl extends DialogControl implements ClickHandler {
			public CustomerDelDialog dialog = new CustomerDelDialog();

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
		
		public class CustomerDelDialog extends CustomDialog{
			
			Label  info = new Label("您确定删除该客户？");
			public String rowid = null;
			public CustomerDelDialog() {
				mainPanel.add(info);
			}
			public void show(String tips_id, String tips_imsi) {
				rowid = tips_id;
				setText("客户编号：" + tips_id);
				super.show();
			}
		}
		
		public class CustomerEditControl extends DialogControl implements ClickHandler {
			String colName = null;
			public CustomerEditDialog dialog = null;
			public void setColName(String colName) {
				this.colName = colName;
				dialog = new CustomerEditDialog(colName);
			}
			
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
		
		public class CustomerEditDialog extends CustomDialog{
			
			Label  oldValueLabel = new Label();
			public TextBox newValueBox = new TextBox();
			public String rowid;
			
			public CustomerEditDialog(String ColName){
				Grid gridFrame = new Grid(2, 2);
				label.setText("请输入新的"+ColName);
				gridFrame.setWidget(0, 0, new Label("当前"+ColName+":"));
				gridFrame.setWidget(0, 1, oldValueLabel);
				gridFrame.setWidget(1, 0, new Label("新的"+ColName+":"));
				gridFrame.setWidget(1, 1, newValueBox);
				newValueBox.setWidth("220px");
				newValueBox.setTabIndex(1);
				
				mainPanel.add(gridFrame);
			}
			
			public void show(String tips_id, String tips_imsi) {
				rowid = tips_id;
				setText("记录序号：" + tips_id);
				oldValueLabel.setText(tips_imsi);
				newValueBox.setText(tips_imsi);
				super.show();
				newValueBox.setFocus(true);
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

	@Override
	public Widget getView() {
		return view;
	}
}
