package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.CardBatchController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;

public class CardBatchSink extends Sink {
	public static final String Group = "后台管理";
	public static final String Tag = "ChannelManage";
	public static final String Name = "批次管理";
	public static final String Desc = "这是一个批次管理功能";

	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new CardBatchSink();
			}
		};
	}
	
	public static IOPlatformView getView(){
		return CardBatchController.INSTANCE.view;
	}
	
	@Override
	public AController getController() {
		return CardBatchController.INSTANCE;
	}
	
	public void getActionList(){
	//	BaseController.INSTANCE.
	}
	
	public Widget getWidget(){
		return CardBatchController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		// TODO Auto-generated method stub
		return init();
	}
}
