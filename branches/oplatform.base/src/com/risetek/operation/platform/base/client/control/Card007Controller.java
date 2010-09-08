package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.dialog.Card007Dialog;
import com.risetek.operation.platform.base.client.model.Card007Data;
import com.risetek.operation.platform.base.client.model.Card007Data.PacketParser;
import com.risetek.operation.platform.base.client.view.Card007View;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.BillCard007;
import com.risetek.operation.platform.launch.client.json.constanst.Card007Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

/**
 * @ClassName: Card007Controller 
 * @Description: 全国充值模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:02:30 
 * @version 1.0
 */
public class Card007Controller extends AController {
			
	public static Card007Controller INSTANCE = new Card007Controller();
	
	private final Card007Data data = new Card007Data();
	
	public final Card007View view = new Card007View();

	public static RequestFactory remoteRequest = new RequestFactory();

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
			Window.alert("请求发送失败！");
		}
		@Override
		public void onResponseReceived(Request request, Response response) {
			String ret = response.getText();
			System.out.println("return code:  "+ response.getStatusCode());
			if (response.getStatusCode() == Response.SC_OK) {
				PacketParser parser = data.new PacketParser();
				BillCard007[] array = (BillCard007[]) parser.packetParser(ret, Constanst.ACTION_NAME_SELECT_CARD_007);			
				INSTANCE.data.parseResult(array);
				INSTANCE.view.render(INSTANCE.data);
			}
		}
	}
	
	/**
	 * @Description: 加载数据，会实现一个回调函数 
	 * @return void 返回类型 
	 */
	public static void load() {
		INSTANCE.data.new PacketParser().initializedata(remoteRequest, RemoteCaller, Constanst.ACTION_NAME_SELECT_CARD_007);
		//INSTANCE.data.setSum(10);
		//INSTANCE.view.render(INSTANCE.data);
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
	public OPlatformTableView getView() {
		return view;
	}
	
	@Override
	public ArrayList<String> getActionNames() {
		
		return null;
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
			
		}
	}
	
	/**
	 * @ClassName: chargeAction 
	 * @Description:  响应充值按扭的事件实体
	 * @author JZJ 
	 * @date 2010-9-6 上午09:13:01 
	 * @version 1.0
	 */
	public static class chargeAction implements ClickHandler {
		Grid grid;
		public chargeAction(Grid grid){
			this.grid = grid;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			int i = 0, j = 1, row = 0;
			for (; j < grid.getRowCount(); j++) {
				CheckBox box = (CheckBox) grid.getWidget(j, 0);
				if (box == null) continue;
				if (box.getValue()) {
					i++;
					row = Integer.parseInt(grid.getText(j, 1));
				}
			}
			if(i==0){
				Window.alert("请选择一个007卡执行操作！");
				return;
			} else if (i>1){
				Window.alert("只能选择一条007卡执行操作！");
				return;
			} 
			
			String payState = grid.getText(row, 8);
			if(payState.equals("已支付")){
				charge007CardControl control = new charge007CardControl(grid, row);
				control.dialog.submit.addClickHandler(control);
				control.dialog.submit.setText("确认充值");
				control.dialog.show();
			}else{
				Window.alert("该笔账单：" + grid.getText(row, 3) + "未支付");
			}
		}
	}
	
	/**
	 * @ClassName: charge007CardControl 
	 * @Description: 获取007card窗体和数据并发送充值数据 
	 * @author JZJ 
	 * @date 2010-9-6 上午09:13:09 
	 * @version 1.0
	 */
	public static class charge007CardControl extends DialogControl implements ClickHandler {
		Card007Dialog dialog;
		BillCard007 card;
		public charge007CardControl(Grid grid, int row) {
			this.dialog = new Card007Dialog(grid, row);
			card = dialog.getData(grid, row);
		}

		@Override
		protected CustomDialog getDialog() {
			return dialog;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if (dialog.isValid()) {
				dialog.submit.setEnabled(false);
				card.setChargeDateTime(dialog.getChargeDateTime());
				final PacketParser parser = INSTANCE.data.new PacketParser();
				String packet = parser.chargePacket(card, Card007Constanst.BILL_RESULT);
				//System.out.println("add packet:  "+ packet);
				remoteRequest.send007(packet, new RequestCallback() {
					@Override
					public void onError(Request request, Throwable exception) {
						Window.alert(request.toString() + "请求发送失败！");
						if (request != null && request.isPending()) {
							request.cancel();
						}
					}
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						INSTANCE.processResponseText(parser, response);
						dialog.hide();
					}
				});
			}
		}
	}
	
	/**
	 * @Description: 发送充值数据成功后解析返回的JSON数据
	 * @param parser
	 * @param response  参数 
	 * @return void 返回类型
	 */
	private void processResponseText(PacketParser parser, Response response){
		String ret = response.getText();
		if (response.getStatusCode() == Response.SC_OK) {
			if (ret != null && !"".equals(ret)) {
				int result = parser.dealResponsePacket(ret);
				if (result == 0) {
					load();
					Window.alert("充值成功！" );
				} else {
					Window.alert("请求异常Code:" + result);
				}
			}else {
				Window.alert("请求没有返回任何数据！");
			}
		}else{
			Window.alert("请求异常,code:" + response.getStatusCode());
		}
	}

	@Override
	public void load(int pagePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setChildPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getChildPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OPlatformData getChildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
		// TODO Auto-generated method stub
		
	}
}
