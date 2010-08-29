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
	
	private static int col;    //列序号
	
	private static String keyid; //主键
	
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

		public String getActionName() {
			return actionName;
		}

		@Override
		public void onClick(ClickEvent event) {
			Object obj = event.getSource();
			if (obj == BankView.addButton) {
				INSTANCE.processBank(false);// false 表示增加
				return;
			} else if (obj == BankView.searchButton) {
				INSTANCE.processBank(true); // true 表示查询
				return;
			} else {
				INSTANCE.gridOnclick(event);
			}
		}
	}
	
	/**
	 * @Description: 执行add/search操作
	 * @return void 返回类型
	 */
	private void processBank(final boolean isSearch) {
		final BankAddDialog addDialog = new BankAddDialog(isSearch);
		addDialog.submit.setText("提交");
		addDialog.show();

		addDialog.submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (addDialog.isValid()) {
					addDialog.submit.setEnabled(false);
					Window.alert("" + isSearch);
				}
			}
		});
	}
	
	/**
	 * @Description: 处理所有grid事件(修改、删除)
	 * @param event  参数 
	 * @return void 返回类型
	 */
	public void gridOnclick(ClickEvent event) {
		HTMLTable table = (HTMLTable) event.getSource();
		Cell Mycell = table.getCellForEvent(event);
		if (Mycell == null) return;
		int row = Mycell.getRowIndex();
		col = Mycell.getCellIndex();
		keyid = table.getText(row, 2);// 我们的操作都针对这个号码。
		String rowid = table.getText(row, 1);
		String colName = table.getText(0, col);

		String tisp_value = table.getText(row, col);
		if (tisp_value.length() == 1) {
			int tvalue = (int) tisp_value.charAt(0);
			if (tvalue == 160) {
				tisp_value = "";
			}
		}

		switch (col) {
		case 2:
			// 删除发卡行信息。第一个参数必须为 null
			caseUtils(null, rowid, tisp_value);
			break;
		case 3:
			// 修改发卡行名称。
			caseUtils(colName, rowid, tisp_value);
			break;
		case 4:
			// 修改有效期。
			caseUtils(colName, rowid, tisp_value);
			break;
		case 5:
			// 修改备注。
			caseUtils(colName, rowid, tisp_value);
			break;
		default:
			break;
		}
	}
	
	/**
	 * @Description: case处理工具
	 * @param colName 列名称
	 */
	private void caseUtils(String colName, String rowid, String tisp_value){
		BankModifyControl bank_control = new BankModifyControl(colName);
		bank_control.dialog.submit.addClickHandler(bank_control);
		bank_control.dialog.submit.setText(colName == null ? "删除" : "修改");
		bank_control.dialog.show(rowid, tisp_value);
	}
	
	/** 
	 * @ClassName: AcountModifyControl 
	 * @Description: 修改发卡行信息控制类，根据传入的tag来调用对应的方法进行处理
	 * @author JZJ 
	 * @date 2010-8-27 上午10:11:17 
	 * @version
	 */
	private static class BankModifyControl implements ClickHandler {
			
		private BankModifyDialog dialog;
		
		public BankModifyControl(String colName) {
			dialog = new BankModifyDialog(colName);
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(!dialog.isValid()) return;
			dialog.submit.setEnabled(false);
			switch (col) {
			case 2:
				delRow(keyid, BankController.RemoteCaller);
				break;
			case 3:
				modifyName(keyid, dialog.newValueBox.getText(), BankController.RemoteCaller);			
				break;
			case 4:
				modifyValidity(keyid, dialog.validityValue, BankController.RemoteCaller);			
				break;
			case 5:
				modifyDesc(keyid, dialog.newValueBox.getText(), BankController.RemoteCaller);			
				break;
			default:
				break;
			}
		}
	}
	
	// ----------------- 处理对应请求  -----------------------//
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
