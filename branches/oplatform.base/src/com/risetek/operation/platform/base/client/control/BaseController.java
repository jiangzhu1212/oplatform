package com.risetek.operation.platform.base.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.risetek.operation.platform.base.client.model.BaseData;
import com.risetek.operation.platform.base.client.view.BaseView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;

public class BaseController extends AController {

	public static BaseController INSTANCE = new BaseController();
	final BaseData data = new BaseData();
	
	public final BaseView view = new BaseView();
	
	private BaseController(){
		
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
	}
	
	public BaseData getData() {
		return data;
	}
	
	@Override
	public void disablePrivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePrivate() {
		// TODO Auto-generated method stub
		
	}

	public static class TableEditAction implements ClickActionHandler {
		
		private String actionName = "编辑表格";

		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
		}
	}
	
	public static class TableShowAction implements ClickActionHandler {
		
		private String actionName = "查看表格行";
		
		public String getActionName(){
			return actionName;
		}
		
		public void onClick(ClickEvent event) {
			
		}
	}
}
