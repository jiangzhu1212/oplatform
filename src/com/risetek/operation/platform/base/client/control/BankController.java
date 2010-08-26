package com.risetek.operation.platform.base.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.dialog.BankAddDialog;
import com.risetek.operation.platform.base.client.dialog.BankModifyDialog;
import com.risetek.operation.platform.base.client.model.BankData;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/**
 * @ClassName: BankController 
 * @Description: 发卡行模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:02:30 
 * @version 1.0
 */
public class BankController extends AController {

	public static BankController INSTANCE = new BankController();
	
	private final BankData data = new BankData();
	
	public final BankView view = new BankView();
	
	private static RequestFactory remoteRequest = new RequestFactory();
	
	public static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	/**
	 * @ClassName: RemoteRequestCallback 
	 * @Description: 远程回调函数 ,格式化返回数据 
	 * @author JZJ 
	 * @date 2010-8-26 下午02:48:04 
	 * @version 1.0
	 */
	class RemoteRequestCallback implements RequestCallback {
		@Override
		public void onError(Request request, Throwable exception) {

		}
		@Override
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			data.parseData(response.getText());
			view.render(data);
		}
	}
	
	/**
	 * Description: 构造器
	 */
	private BankController(){
		//String name = new TableEditAction().getActionName();
		//System.out.println(name);
	}
	
	/**
	 * @Description: 加载数据，会实现一个回调函数 
	 * @return void 返回类型 
	 */
	public static void load(){
		INSTANCE.data.setSum(10);
		INSTANCE.view.render(INSTANCE.data);
		//remoteRequest.get("", "", RemoteCaller);
	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 得到模块数据资源
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.control.AController#getData()
	 */
	@Override
	public BankData getData() {
		return data;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  事件接口方法，返回该模块视图
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.control.AController#getView()
	 */
	@Override
	public Widget getView() {
		return view;
	}
	
	/**
	 * @ClassName: TableEditAction 
	 * @Description: 以下子类分别是该模块事件的实体 
	 * @author JZJ 
	 * @date 2010-8-26 下午02:35:06 
	 * @version 1.0
	 */
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
		}
	}
	
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";

		public String getActionName(){
			return actionName;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			Object obj = event.getSource();
			if(obj == BankView.addButton){
				INSTANCE.addBank();
				return;
			}
			INSTANCE.gridOnclick(event);
		}
	}
	
	public void addBank(){
		final BankAddDialog addDialog = new BankAddDialog();
		addDialog.submit.setText("提交");
		addDialog.show();
		addDialog.submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addDialog.submit.setEnabled(false);
				Window.alert("add");
			}
		});
	}
	
	public void gridOnclick(ClickEvent event){
		HTMLTable table = (HTMLTable) event.getSource();
		Cell Mycell = table.getCellForEvent(event);
		if (Mycell == null) return;
		int row = Mycell.getRowIndex();
		int col = Mycell.getCellIndex();

		String tisp_value = table.getText(row, col);
		if (tisp_value.length() == 1) {
			int tvalue = (int) tisp_value.charAt(0);
			if (tvalue == 160) {
				tisp_value = "";
			}
		}

		// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
		String bankid = table.getText(row, 2);
		String rowid = table.getText(row, 1);
		
		BankModifyControl bank_control = new BankModifyControl();
		switch (col) {
		case 1:
			break;
		case 2:
			// 删除发卡行信息。
			bank_control.init(null, 1);
			bank_control.dialog.submit.setText("删除");
			bank_control.dialog.submit.addClickHandler(bank_control);
			bank_control.dialog.show_del(rowid, bankid, tisp_value);
			break;
		case 3:
			// 修改发卡行名称。
			bank_control.init(BankView.columns[1], 2);
			bank_control.dialog.submit.setText("修改");
			bank_control.dialog.submit.addClickHandler(bank_control);
			bank_control.dialog.show(rowid, bankid, tisp_value);
			break;
		case 4:
			// 修改有效期。
			bank_control.init(BankView.columns[2], 3);
			bank_control.dialog.submit.setText("修改");
			bank_control.dialog.submit.addClickHandler(bank_control);
			bank_control.dialog.show(rowid, bankid, tisp_value);
			break;
		case 5:
			// 修改备注。
			bank_control.init(BankView.columns[3], 4);
			bank_control.dialog.submit.setText("修改");
			bank_control.dialog.submit.addClickHandler(bank_control);
			bank_control.dialog.show(rowid, bankid, tisp_value);
			break;
		default:
			break;
		}
	}
	
	// ----------------- 修改发卡行
	private static class BankModifyControl implements ClickHandler {
		
		private int tag = 0;
	
		private BankModifyDialog dialog;
		
		public void init(String colName, int tag) {
			this.tag = tag;
			dialog = new BankModifyDialog(colName);
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(!dialog.isValid()) return;
			dialog.submit.setEnabled(false);
			if(tag == 1){
				delRow(dialog.bankid, BankController.RemoteCaller);
			}else if(tag == 2){
				modifyName(dialog.bankid, dialog.newValueBox.getText(), BankController.RemoteCaller);			
			}else if(tag == 3){
				modifyValidity(dialog.bankid, dialog.dateBox.getTextBox().getText(), BankController.RemoteCaller);			
			}else if(tag == 4){
				modifyDesc(dialog.bankid, dialog.newValueBox.getText(), BankController.RemoteCaller);			
			}
		}
	}
	
	public static void delRow(String bakID, RequestCallback callback) {
		String query = "function=deluser&id=" + bakID;
		Window.alert(query);
	}
	
	private static void modifyName(String bakID, String name, RequestCallback callback) {
		String query = "function=moduser&id=" + bakID + "&username=" + name;
		Window.alert(query);
	}
	
	private static void modifyValidity(String bakID, String validity, RequestCallback callback) {
		String query = "function=moduser&id=" + bakID + "&validity=" + validity;
		Window.alert(query);
	}
	
	private static void modifyDesc(String bakID, String desc, RequestCallback callback) {
		String query = "function=moduser&id=" + bakID + "&desc=" + desc;
		Window.alert(query);
	}
}
