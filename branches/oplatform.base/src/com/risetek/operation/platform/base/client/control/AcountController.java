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
import com.risetek.operation.platform.base.client.dialog.AcountAddDialog;
import com.risetek.operation.platform.base.client.dialog.AcountModifyDialog;
import com.risetek.operation.platform.base.client.model.AcountData;
import com.risetek.operation.platform.base.client.view.AcountView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/**
 * @ClassName: AcountController 
 * @Description: 银行卡列表模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:00:43 
 * @version 1.0
 */
public class AcountController extends AController {

	private static int col;    //列序号
	
	private static String keyid;//主键
	
	public static AcountController INSTANCE = new AcountController();
	
	private final AcountData data = new AcountData();
	
	public final AcountView view = new AcountView();
	
	private static RequestFactory remoteRequest = new RequestFactory();
	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	/**
	 * @ClassName: RemoteRequestCallback 
	 * @Description: 远程回调函数 ,格式化返回数据
	 * @author JZJ 
	 * @date 2010-8-26 下午02:46:20 
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
	 * Description:  得到模块数据资源
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.control.AController#getData()
	 */
	public AcountData getData() {
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
	 * @date 2010-8-26 下午02:36:17 
	 * @version 1.0
	 */
	public static class TableShowAction implements ClickActionHandler {

		private String actionName = "查看表格行";

		public String getActionName() {
			return actionName;
		}

		@Override
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
			if (obj == AcountView.addButton) {
				INSTANCE.processBank(false); // false 表示增加
				return;
			} else if (obj == AcountView.searchButton) {
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
		final AcountAddDialog addDialog = new AcountAddDialog(isSearch);
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
		col = Mycell.getCellIndex();//列序号
		keyid = table.getText(row, 2);// 我们的操作都针对这个号码。
		String rowid = table.getText(row, 1);//行序号
		String colName = table.getText(0, col);//列名称

		String tisp_value = table.getText(row, col);
		if (tisp_value.length() == 1) {
			int tvalue = (int) tisp_value.charAt(0);
			if (tvalue == 160) {
				tisp_value = "";
			}
		}

		switch (col) {
		case 2:
			// 删除银行卡信息。
			caseUtils(null, rowid, tisp_value);
			break;
		case 3:
			// 修改发卡行代码值。
			caseUtils(colName, rowid, tisp_value);
			break;
		case 4:
			// 修改有效期。
			caseUtils(colName, rowid, tisp_value);
			break;
		case 5:
			// 修改ADDITON。
			caseUtils(colName, rowid, tisp_value);
			break;
		case 6:
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
		AcountModifyControl acout_control = new AcountModifyControl(colName);
		acout_control.dialog.submit.addClickHandler(acout_control);
		acout_control.dialog.submit.setText(colName == null ? "删除" : "修改");
		acout_control.dialog.show(rowid, tisp_value);
	}
	
	/** 
	 * @ClassName: AcountModifyControl 
	 * @Description: 修改银行卡信息控制类，根据传入的processNum来调用对应的方法进行处理
	 * @author JZJ 
	 * @date 2010-8-27 上午10:11:17 
	 * @version
	 */
	private static class AcountModifyControl implements ClickHandler {
		
		private AcountModifyDialog dialog;
		
		public AcountModifyControl(String colName) {
			dialog = new AcountModifyDialog(colName);
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(!dialog.isValid()) return;
			dialog.submit.setEnabled(false);
			switch (col) {
			case 2:
				delRow(keyid, AcountController.RemoteCaller);
				break;
			case 3:
				modifyBankCode(keyid, dialog.newValueBox.getText(), AcountController.RemoteCaller);			
				break;
			case 4:
				modifyValidity(keyid, dialog.validityValue, AcountController.RemoteCaller);			
				break;
			case 5:
				modifyAddtion(keyid, dialog.newValueBox.getText(), AcountController.RemoteCaller);			
				break;
			case 6:
				modifyDesc(keyid, dialog.newValueBox.getText(), AcountController.RemoteCaller);			
				break;
			default:
				break;
			}
		}
	}
	
	// ----------------- 处理对应请求  -----------------------//
	public static void delRow(String keyID, RequestCallback callback) {
		String query = "function=deluser&id=" + keyID;
		Window.alert(query);
	}
	
	private static void modifyBankCode(String keyID, String bankCode, RequestCallback callback) {
		String query = "function=moduser&id=" + keyID + "&bankCode=" + bankCode;
		Window.alert(query);
	}
	
	private static void modifyValidity(String keyID, String validity, RequestCallback callback) {
		String query = "function=moduser&id=" + keyID + "&validity=" + validity;
		Window.alert(query);
	}
	
	private static void modifyAddtion(String keyID, String addtion, RequestCallback callback) {
		String query = "function=moduser&id=" + keyID + "&addtion=" + addtion;
		Window.alert(query);
	}
	
	private static void modifyDesc(String keyID, String desc, RequestCallback callback) {
		String query = "function=moduser&id=" + keyID + "&desc=" + desc;
		Window.alert(query);
	}
}
