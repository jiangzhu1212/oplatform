package com.risetek.operation.platform.base.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.model.AcountData;
import com.risetek.operation.platform.base.client.view.AcountView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/**
 * @ClassName: AcountController 
 * @Description: 银行卡列模块控制器实体 
 * @author JZJ 
 * @date 2010-8-26 下午02:00:43 
 * @version 1.0
 */
public class AcountController extends AController {

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
	 * Description: 构造器
	 */
	private AcountController() {
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
	public AcountData getData() {
		return data;
	}
	
	/**
	 * @ClassName: TableEditAction 
	 * @Description: 以下子类分别是该模块事件的实体 
	 * @author JZJ 
	 * @date 2010-8-26 下午02:36:17 
	 * @version 1.0
	 */
	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";

		public String getActionName(){
			return actionName;
		}

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("add");
		}		
	}
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			
		}
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
}
