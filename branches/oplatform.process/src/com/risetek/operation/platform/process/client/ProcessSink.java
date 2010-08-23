package com.risetek.operation.platform.process.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.process.client.control.ProcessController;

public class ProcessSink extends Sink {

	public static final String Group = "processgroup";
	public static final String Tag = "process";
	public static final String Name = "处理";
	public static final String Desc = "这是一个处理功能";
	
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new ProcessSink();
			}
		};
	}
	
	public static IOPlatformView getView(){
		return ProcessController.INSTANCE.view;
	}
	
	@Override
	public AController getController() {
		return ProcessController.INSTANCE;
	}

	public void getActionList(){
//		BaseController.INSTANCE.
	}
	
	public Widget getWidget(){
		return ProcessController.INSTANCE.view;
	}
}
