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
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.dialog.ChannelButtonDialog;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
import com.risetek.operation.platform.base.client.model.ChannelData;
import com.risetek.operation.platform.base.client.view.ChannelView;
import com.risetek.operation.platform.base.client.view.TransactionView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

public class ChannelController extends AController {

	public static ChannelController INSTANCE = new ChannelController();
	final ChannelData data = new ChannelData();
	
	public final ChannelView view = new ChannelView();
	public final ChannelButtonDialog channelButtonDialog = new ChannelButtonDialog();
	private static RequestFactory remoteRequest = new RequestFactory();
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
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
	private static final RequestCallback QueryCaller = INSTANCE.new RemoteRequestCallback();
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
	private ChannelController(){
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
	public ChannelData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";
		private ChannelEditControl edit_control = new ChannelEditControl();
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
				ViewDetailDialog dialog = ViewDetailDialog.INSTANCE;
				dialog.makeMainPanel(INSTANCE.view.grid , row);
				dialog.show();
				break;
			case 2:
				// 选择了删除渠道。
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
				edit_control.setColName(ChannelView.columns[col-2]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}			
			
		}
		
		public class ChannelEditControl extends EditController implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("你好");
				if( !dialog.isValid() ){
					return;
				}					
				//delRow(dialog.rowid, myCaller);
			}		
		}

	}

	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			Object obj = event.getSource();
			if(obj == ChannelView.addButton){
				INSTANCE.channelButtonDialog.addMainPanel();
				INSTANCE.channelButtonDialog.show();
			}else if(obj == ChannelView.queryButton){
				INSTANCE.channelButtonDialog.queryMainPanel();
				INSTANCE.channelButtonDialog.show();
			}
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