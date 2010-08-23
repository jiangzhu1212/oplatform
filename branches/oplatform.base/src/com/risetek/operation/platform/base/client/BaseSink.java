package com.risetek.operation.platform.base.client;

import com.risetek.operation.platform.base.client.control.BaseController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;

public class BaseSink extends Sink {

	public static final String Group = "basegroup";
	public static final String Tag = "base";
	public static final String Name = "基本";
	public static final String Desc = "这是一个基本功能";
	
	public static SinkInfo init(){
		
		return new SinkInfo(Group, Tag, Name, Desc, BaseController.INSTANCE.view) {
			
			@Override
			public Sink createInstance() {
				// TODO Auto-generated method stub
				return new BaseSink();
			}
		};
	}
	
	public static IOPlatformView getView(){
		return BaseController.INSTANCE.view;
	}
	
	public BaseSink(String flag) {
	}
	
	public BaseSink() {
		initWidget(BaseController.INSTANCE.view);//new HTML("haha"));
	}
	
	@Override
	public AController getController() {
		// TODO Auto-generated method stub
		return BaseController.INSTANCE;
	}

	public void getActionList(){
//		BaseController.INSTANCE.
	}
}
