package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.constanst.BILL_INFOMATION;
import com.risetek.operation.platform.base.client.dialog.BankAddDialog;
import com.risetek.operation.platform.base.client.model.Card007Data;
import com.risetek.operation.platform.base.client.view.Card007View;
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
public class Card007Controller extends AController {
	
	private static int col;    //列序号
	
	private static String keyid; //主键
	
	public static Card007Controller INSTANCE = new Card007Controller();
	
	private final Card007Data data = new Card007Data();
	
	public final Card007View view = new Card007View();

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
			//data.parseData(response.getText());
			view.render(data);
		}
	}
	
	/**
	 * @Description: 加载数据，会实现一个回调函数 
	 * @return void 返回类型 
	 */
	public static void load(){
		INSTANCE.view.render(INSTANCE.data);
	}
	
	public void aa(){
		BILL_INFOMATION info = new BILL_INFOMATION();
		if(view.dateBox != null){
			String dateTimeMIN = view.dateBox.getTextBox().getText()+"000000";
			String dateTimeMAX = view.dateBox.getTextBox().getText()+"235959";
			info.setPAY_DATETIME_MIN(dateTimeMIN);
			info.setPAY_DATETIME_MAX(dateTimeMAX);
		}
		
		info.setCHARGE_STATUS(""+view.statusValue);
		//PacketParser parser = new PacketParser();
		//String packet = parser.toHttpPacket(info,Constanst.ACTION_NAME_SELECT_CARD_007);	
		
		//request.get(packet, parent);
	}
	

	/**
	 * (非 Javadoc) 
	 * Description: 得到模块数据资源
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.control.AController#getData()
	 */
	@Override
	public Card007Data getData() {
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
			if (obj == Card007View.chargeButton) {
				INSTANCE.processFuc(null);
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
	private void processFuc(final String processTag) {
		final BankAddDialog addDialog = new BankAddDialog(processTag);
		addDialog.submit.setText("提交");
		addDialog.show();

		addDialog.submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (addDialog.isValid()) {
					addDialog.submit.setEnabled(false);
					Window.alert(processTag);
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
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
		case 4:
		case 5:
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
