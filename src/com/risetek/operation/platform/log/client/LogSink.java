package com.risetek.operation.platform.log.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.log.client.control.LogController;
import com.risetek.operation.platform.log.client.view.LogView;

public class LogSink extends Sink {

	public static final String Group = "系统管理";
	public static final String Tag = "log";
	public static final String Name = "系统日志";
	public static final String Desc = "记录平台的各项操作信息";
	
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new LogSink();
			}
		};
	}
	
	@Override
	public AController getController() {
		return LogController.INSTANCE;
	}

	@Override
	public Widget getWidget() {
		LogView.descript = Desc;
		return LogController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		return init();
	}

}
