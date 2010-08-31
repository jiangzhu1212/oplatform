package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.CardTerminalController;
import com.risetek.operation.platform.base.client.view.CardTerminalView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class CardTerminalSink extends Sink {

	public static final String Group = "后台管理";
	public static final String Tag = "terminalManage";
	public static final String Name = "终端管理";
	public static final String Desc = "这是一个终端管理功能";
	
	/**
	 * 功能：初始化模块
	 *
	 * SinkInfo
	 * @return
	 */
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new CardTerminalSink();
			}
		};
	}
	
	/** 
	 * 功能： 得到模块控制器
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getController()
	 */
	@Override
	public AController getController() {
		// TODO Auto-generated method stub
		return CardTerminalController.INSTANCE;
	}

	/**
	 * 功能：得到模块事件列表
	 *
	 * void
	 */
	public void getActionList(){
//		BaseController.INSTANCE.
	}
	
	/** 
	 * 功能： 得到模块视图对象
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getWidget()
	 */
	public Widget getWidget(){
		CardTerminalView.descript = Desc;
		return CardTerminalController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		return init();
	}
}
