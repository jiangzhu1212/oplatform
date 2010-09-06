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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.dialog.JCardQueryButtonDialog;
import com.risetek.operation.platform.base.client.dialog.ViewDetailDialog;
import com.risetek.operation.platform.base.client.model.JCardData;
import com.risetek.operation.platform.base.client.view.JCardQueryView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.OpRetInfo;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;

public class JCardQueryContorller extends AController {

	public static JCardQueryContorller INSTANCE = new JCardQueryContorller();
	final JCardData data = new JCardData();
	
	public final JCardQueryView view = new JCardQueryView();
	
	public JCardQueryButtonDialog jCardQueryDialog = new JCardQueryButtonDialog();
	
	public static RequestFactory remoteRequest = new RequestFactory();
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	//修改操作的回调
	class RemoteRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			OpRetInfo opRetinfo = (OpRetInfo)data.retInfo(ret)[0];
			if (opRetinfo.getReturnCode()!=Constanst.OP_TRUE)  {
				Window.alert(opRetinfo.getReturnMessage());
			}else{
				if(jCardQueryDialog.packet != null){
					remoteRequest.getJCard(jCardQueryDialog.packet, QueryCaller);
				}else{
					String packet = data.toHttpPacket();
					remoteRequest.getBill(packet, QueryCaller);
				}
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
	public JCardData getData() {
		return data;
	}
	
	/**
	 * @author Amber
	 * 功能：以下子类分别是该模块事件的实体
	 * 2010-8-23 下午11:49:52
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";
		private TransactionEditControl edit_control = new TransactionEditControl();
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
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。(这里是行号)
			String rowid = table.getText(row, 1);

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
			case 7:			
				edit_control.setColName(JCardQueryView.columns[col-2]);	
				edit_control.dialog.submit.setText("修改");
				edit_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}			
			
		}
	
		
		public class TransactionEditControl extends EditController implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {
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
			INSTANCE.jCardQueryDialog = new JCardQueryButtonDialog();
			Object obj = event.getSource();			
			 if(obj == JCardQueryView.queryButton){
				INSTANCE.jCardQueryDialog.setAction_name(Constanst.ACTION_NAME_SELECT_JCARD);
				INSTANCE.jCardQueryDialog.queryMainPanel();
				INSTANCE.jCardQueryDialog.show();
			}else if(obj == JCardQueryView.addButton){
				INSTANCE.jCardQueryDialog.setAction_name(Constanst.ACTION_NAME_IMPORT_DATA);
				INSTANCE.jCardQueryDialog.addMainPanel();
				INSTANCE.jCardQueryDialog.show();
			}else if(obj == JCardQueryView.balanceButton){
				INSTANCE.jCardQueryDialog.setAction_name(Constanst.ACTION_NAME_BALANCE);
				INSTANCE.jCardQueryDialog.balancePanel();
				INSTANCE.jCardQueryDialog.show();
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
