package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.RoleConfigController;
import com.risetek.operation.platform.base.client.view.RoleConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class RoleConfigSink extends Sink {

	public static final String Group = "系统管理";
	public static final String Tag = "roleconfig";
	public static final String Name = "角色管理";
	public static final String Desc = "制定管理用户角色";
	
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new RoleConfigSink();
			}
		};
	}
	
	@Override
	public AController getController() {
		return RoleConfigController.INSTANCE;
	}

	@Override
	public Widget getWidget() {
		RoleConfigView.descript = Desc;
		return RoleConfigController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		// TODO Auto-generated method stub
		return init();
	}

}
