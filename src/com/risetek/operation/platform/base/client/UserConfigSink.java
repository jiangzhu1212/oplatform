package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.UserConfigController;
import com.risetek.operation.platform.base.client.view.UserConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class UserConfigSink extends Sink {

	public static final String Group = "系统管理";
	public static final String Tag = "userconfig";
	public static final String Name = "用户管理";
	public static final String Desc = "管理登录本平台的内部用户";
	
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new UserConfigSink();
			}
		};
	}
	
	@Override
	public AController getController() {
		return UserConfigController.INSTANCE;
	}

	@Override
	public Widget getWidget() {
		UserConfigView.descript = Desc;
		return UserConfigController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		return init();
	}

}
