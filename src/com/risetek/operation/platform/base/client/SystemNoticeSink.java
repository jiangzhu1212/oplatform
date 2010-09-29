package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.SystemNoticeController;
import com.risetek.operation.platform.base.client.view.SystemNoticeView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class SystemNoticeSink extends Sink {

	public static final String Group = "系统管理";
	public static final String Tag = "systemnotice";
	public static final String Name = "内部通知公告";
	public static final String Desc = "发布内部通知公告";
	
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new SystemNoticeSink();
			}
		};
	}
	
	@Override
	public AController getController() {
		return SystemNoticeController.INSTANCE;
	}
	@Override
	public Widget getWidget() {
		SystemNoticeView.descript = Desc;
		return SystemNoticeController.INSTANCE.view;
	}
	@Override
	public SinkInfo getSinkInfo() {
		return init();
	}
}
