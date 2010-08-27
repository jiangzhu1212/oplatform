package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.CustomerController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;

public class CustomerSink extends Sink {
	public static final String Group = "基本功能";
	public static final String Tag = "customerManage";
	public static final String Name = "客户管理";
	public static final String Desc = "这是一个客户管理功能";

	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new CustomerSink();
			}
		};
	}
	
	public static IOPlatformView getView(){
		return CustomerController.INSTANCE.view;
	}
	
	@Override
	public AController getController() {
		return CustomerController.INSTANCE;
	}
	
	public void getActionList(){
	//	BaseController.INSTANCE.
	}
	
	public Widget getWidget(){
		return CustomerController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		// TODO Auto-generated method stub
		return init();
	}
}
