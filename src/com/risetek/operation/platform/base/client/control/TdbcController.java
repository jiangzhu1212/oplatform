package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.dialog.TdbcDialog;
import com.risetek.operation.platform.base.client.model.TdbcData;
import com.risetek.operation.platform.base.client.view.TdbcView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/** 
 * @ClassName: TdbcController 
 * @Description: 二维码模块控制器实体  
 * @author JZJ 
 * @date 2010-8-26 下午03:24:12 
 * @version 1.0
 */
public class TdbcController extends AController {

	public static TdbcController INSTANCE = new TdbcController();
	
	private final TdbcData data = new TdbcData();
	
	public final TdbcView view = new TdbcView();
	
	private static RequestFactory remoteRequest = new RequestFactory();
	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	/**
	 * @ClassName: RemoteRequestCallback 
	 * @Description: 远程回调函数 ,格式化返回数据
	 * @author JZJ 
	 * @date 2010-8-26 下午03:24:12 
	 * @version 1.0
	 */
	class RemoteRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			int code = response.getStatusCode();
			System.out.println(code);
			data.parseData(response.getText());
			view.render(data);
		}

		@Override
		public void onError(Request request, Throwable exception) {
			
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
	@Override
	public TdbcData getData() {
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

		public String getActionName(){
			return actionName;
		}

		@Override
		public void onClick(ClickEvent event) {
			Object obj = event.getSource();
			if (obj == TdbcView.searchButton) {
				INSTANCE.processFuc(true); // true 表示查询
				return;
			}
		}		
	}
	
	/**
	 * @Description: 执行search操作
	 * @param isSearch  参数 
	 * @return void 返回类型
	 */
	private void processFuc(final boolean isSearch){
		final TdbcDialog dialog = new TdbcDialog(isSearch);
		dialog.submit.setText("提交");
		dialog.show();

		dialog.submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					dialog.submit.setEnabled(false);
					searchTdbc((dialog.E_GOODS_SN_Box.getText()).trim(), TdbcController.RemoteCaller);
				}
			}
		});
	}
	
	//-------------------------执行提交事件---------------------------//
	private void searchTdbc(String E_GOODS_SN, RequestCallback callback) {
		String query = "function=searchTdbc&E_GOODS_SN=" + E_GOODS_SN;
		Window.alert(query);
	}

	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
