package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.model.TransactionData;
import com.risetek.operation.platform.base.client.view.TransactionView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

public class TransactionController extends AController {

	public static TransactionController INSTANCE = new TransactionController();
	final TransactionData data = new TransactionData();
	
	public final TransactionView view = new TransactionView();
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
	
	private TransactionController(){
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
	public TransactionData getData() {
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
			TransactionEditControl edit_control = new TransactionEditControl();
			switch (col) {
				
			case 2:
				// 选择了删除业务。
				TransactionDelControl del_control = new TransactionDelControl();
				del_control.dialog.submit.setText("删除");
				del_control.dialog.submit.addClickHandler(del_control);
				del_control.dialog.show(rowid, tisp_value);
				break;

			case 3:
				edit_control.setColName(TransactionView.columns[1]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 4:
				edit_control.setColName(TransactionView.columns[2]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 5:
				edit_control.setColName(TransactionView.columns[3]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 6:
				edit_control.setColName(TransactionView.columns[4]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 7:
				edit_control.setColName(TransactionView.columns[5]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 8:
				edit_control.setColName(TransactionView.columns[6]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 9:
				edit_control.setColName(TransactionView.columns[7]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 10:
				edit_control.setColName(TransactionView.columns[8]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
				break;
			case 11:
				edit_control.setColName(TransactionView.columns[9]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.submit.addClickHandler(edit_control);
				edit_control.dialog.show(rowid, tisp_value);
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
		
		public class TransactionEditControl extends DialogControl implements ClickHandler {
			String colName = null;
			public TransactionEditDialog dialog = null;
			public void setColName(String colName) {
				this.colName = colName;
				dialog = new TransactionEditDialog(colName);
			}
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("你好");
				if( !dialog.isValid() ){
					return;
				}					
				//delRow(dialog.rowid, myCaller);
			}
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}			
		}
		
		public class TransactionEditDialog extends CustomDialog{
			
			Label  oldValueLabel = new Label();
			public TextBox newValueBox = new TextBox();
			public String rowid;
			String colName = null ;
			public TransactionEditDialog(String colName){
				oldValueLabel.setWidth("220px");
				newValueBox.setWidth("220px");
				this.colName = colName;
				Grid gridFrame = new Grid(2, 2);
				label.setText("请输入新的"+colName);
				gridFrame.setWidget(0, 0, new Label("当前"+colName+":"));
				gridFrame.setWidget(0, 1, oldValueLabel);
				gridFrame.setWidget(1, 0, new Label("新的"+colName+":"));
				gridFrame.setWidget(1, 1, newValueBox);
				newValueBox.setTabIndex(1);
				
				mainPanel.add(gridFrame);
			}
			
			public void show(String tips_id, String tips_imsi) {
				rowid = tips_id;
				setText("修改" + colName);
				oldValueLabel.setText(tips_imsi);
				super.show();
				newValueBox.setFocus(true);
			}
			
			public boolean isValid()
			{
				//这里写入限制的判断
				
				return true;
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

	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
