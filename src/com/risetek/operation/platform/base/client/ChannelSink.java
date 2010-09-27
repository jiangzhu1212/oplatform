package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.ChannelController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class ChannelSink extends Sink {
	public static final String Group = "后台管理";
	public static final String Tag = "ChannelManage";
	public static final String Name = "渠道管理";
	public static final String Desc = "这是一个渠道管理功能";

	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new ChannelSink();
			}
		};
	}

	@Override
	public AController getController() {
		return ChannelController.INSTANCE;
	}
	
	public Widget getWidget(){
		return ChannelController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		// TODO Auto-generated method stub
		return init();
	}
}
