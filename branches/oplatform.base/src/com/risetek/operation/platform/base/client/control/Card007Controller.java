package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.constanst.Card007Constanst;
import com.risetek.operation.platform.base.client.dialog.Card007Dialog;
import com.risetek.operation.platform.base.client.entry.BillCard007;
import com.risetek.operation.platform.base.client.entry.Constanst;
import com.risetek.operation.platform.base.client.model.Card007Data;
import com.risetek.operation.platform.base.client.model.PacketParser;
import com.risetek.operation.platform.base.client.util.ServiceUtil;
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

		}
		@Override
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			String ret = response.getText();
			if (code == Response.SC_OK) {
				PacketParser parser = new PacketParser();
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
		new PacketParser().initializedata(Constanst.ACTION_NAME_SELECT_CARD_007);
		INSTANCE.view.render(INSTANCE.data);
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
			Object obj = event.getSource();
			if (obj == Card007View.chargeButton) {
				INSTANCE.processFuc(null);
				return;
			} 
		}
	}
	
	/**
	 * @Description: 执行add/search操作
	 * @return void 返回类型
	 */
	private void processFuc(final String processTag) {
		final Card007Dialog dialog = new Card007Dialog(processTag, INSTANCE.view.grid, 1);
		dialog.submit.setText("确认充值");
		dialog.show();

		dialog.submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					dialog.submit.setEnabled(false);
					final PacketParser parser = new PacketParser();
					String packet = parser.chargePacket(INSTANCE.view.grid, Card007Constanst.BILL_RESULT);
					remoteRequest.send007(ServiceUtil.string2unicode(packet), new RequestCallback() {
						@Override
						public void onError(Request request, Throwable exception) {
							Window.alert(request.toString() + ServiceUtil.REQUEST_ERROR);
							if (request != null && request.isPending()) {
								request.cancel();
							}
						}
						
						@Override
						public void onResponseReceived(Request request, Response response) {
							processResponseText(parser, response);
						}
					});
				}
			}
		});
	}
	
	private void processResponseText(PacketParser parser, Response response){
		int code = response.getStatusCode();
		System.out.println("Windos RESPONSE CODE=" + code);
		String ret = response.getText();
		System.out.println("Window Ret:  "+ ret);
		if (code == Response.SC_OK) {
			if (ret != null && !"".equals(ret)) {
				int result = parser.dealResponsePacket(ret);
				if (result == 0) {
					load();
				} else {
					Window.alert("请求异常" + result);
				}
			}
		}else{
			Window.alert("请求异常");
		}
	}
}
