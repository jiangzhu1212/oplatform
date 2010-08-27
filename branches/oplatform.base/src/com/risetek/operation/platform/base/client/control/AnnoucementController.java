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
import com.risetek.operation.platform.base.client.dialog.AnnoucementAddDialog;
import com.risetek.operation.platform.base.client.dialog.AnnoucementModifyDialog;
import com.risetek.operation.platform.base.client.model.AnnoucementData;
import com.risetek.operation.platform.base.client.view.AcountView;
import com.risetek.operation.platform.base.client.view.AnnoucementView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/**
 * @ClassName: AnnoucementController 
 * @Description: 公告模块控制器实体
 * @author JZJ 
 * @date 2010-8-27 上午11:02:51 
 * @version 1.0
 */
public class AnnoucementController extends AController {

	public static AnnoucementController INSTANCE = new AnnoucementController();
	
	private final AnnoucementData data = new AnnoucementData();
	
	public final AnnoucementView view = new AnnoucementView();
	
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
	 * Description: 构造器
	 */
	private AnnoucementController() {
		// String name = new TableEditAction().getActionName();
		// System.out.println(name);
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
	public AnnoucementData getData() {
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
			if (obj == AnnoucementView.addButton) {
				INSTANCE.addBank();
				return;
			}
			INSTANCE.gridOnclick(event);
		}
	}
	
	/**
	 * @Description: 执行提交操作
	 * @return void 返回类型
	 */
	public void addBank(){
		final AnnoucementAddDialog addDialog = new AnnoucementAddDialog();
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
	
	/**
	 * @Description: 处理所有grid事件(修改、删除)
	 * @param event  参数 
	 * @return void 返回类型
	 */
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
		String keyid = table.getText(row, 2);
		String rowid = table.getText(row, 1);
		
		AnnoucementModifyControl control = new AnnoucementModifyControl();
		switch (col) {
		case 1:
			break;
		case 2:
			// 删除公告信息。
			control.init(null, 1);
			control.dialog.submit.setText("删除");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show_del(rowid, keyid, tisp_value);
			break;
		case 3:
			// 修改公告类型。
			control.init(AnnoucementView.columns[1], 2);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 4:
			// 修改公告日期。
			control.init(AnnoucementView.columns[2], 3);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 5:
			// 修改ADDITON。
			control.init(AnnoucementView.columns[3], 4);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 6:
			break;
		case 7:
			// 修改停止时间。
			control.init(AnnoucementView.columns[5], 5);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 8:
			// 修改TARGET_TYPE。
			control.init(AnnoucementView.columns[6], 6);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 9:
			// 修改TARGET_ID。
			control.init(AnnoucementView.columns[7], 7);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 10:
			// 修改有效期。
			control.init(AnnoucementView.columns[8], 8);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		case 11:
			// 修改备注。
			control.init(AnnoucementView.columns[9], 9);
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(rowid, keyid, tisp_value);
			break;
		default:
			break;
		}
	}
	
	/** 
	 * @ClassName: AcountModifyControl 
	 * @Description: 修改银行卡信息控制类，根据传入的tag来调用对应的方法进行处理
	 * @author JZJ 
	 * @date 2010-8-27 上午10:11:17 
	 * @version
	 */
	private static class AnnoucementModifyControl implements ClickHandler {
		
		private int tag = 0;
	
		private AnnoucementModifyDialog dialog;
		
		public void init(String colName, int tag) {
			this.tag = tag;
			dialog = new AnnoucementModifyDialog(colName);
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(!dialog.isValid()) return;
			dialog.submit.setEnabled(false);
			if(tag == 1){
				delRow(dialog.keyid, AnnoucementController.RemoteCaller);
			}else if(tag == 2){
				modifyBankCode(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 3){
				modifyValidity(dialog.keyid, dialog.dateBox.getTextBox().getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 4){
				modifyAddtion(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 5){
				modifyDesc(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 6){
				modifyDesc(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 7){
				modifyDesc(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 8){
				modifyDesc(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
			}else if(tag == 9){
				modifyDesc(dialog.keyid, dialog.newValueBox.getText(), AnnoucementController.RemoteCaller);			
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
