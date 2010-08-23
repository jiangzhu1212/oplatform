package com.risetek.operation.platform.process.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.process.client.model.ProcessData;
import com.risetek.operation.platform.process.client.view.ProcessView;

public class ProcessController extends AController {

	public static ProcessController INSTANCE = new ProcessController();
	final ProcessData data = new ProcessData();
	
	public final ProcessView view = new ProcessView();
	
	private ProcessController(){
		String name = new TableEditAction().getActionName();
		System.out.println(name);
	}
	
	public static void load(){
		INSTANCE.data.setSum(100);
		INSTANCE.view.render(INSTANCE.data);
	}
	
	public ProcessData getData() {
		return data;
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

	@Override
	public Widget getView() {
		return view;
	}
}
