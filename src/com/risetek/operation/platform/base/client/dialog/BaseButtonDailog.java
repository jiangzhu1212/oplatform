package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.http.RequestFactory;

/**
 * 
 * @author 杨彬
 * 2010-9-14
 * 查询添加等的基本弹出窗口
 */
public abstract class BaseButtonDailog extends CustomDialog {
	
	protected String ACTION_NAME = null ;
	
	protected RequestFactory request = new RequestFactory() ;
	
	protected Grid gridFrame = new Grid(1,2);
	
	public BaseButtonDailog() {
		gridFrame.setStyleName("table");
		gridFrame.getColumnFormatter().setWidth(0, "80px");
		gridFrame.getColumnFormatter().setWidth(1, "220px");
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	public abstract void addMainPanel();
	
	public abstract void queryMainPanel();
	
	public class SubmitButtonClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			subminHandler();		
		}
		
	};
	
	public abstract void subminHandler();
}
