package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.PBabyController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class PBabySink extends Sink{

	public static final String Group = "基础管理";
	public static final String Tag = "pBaby";
	public static final String Name = "票宝宝异常管理";
	public static final String Desc = "票宝宝异常出票管理";
	
	/**
	 * 功能：初始化模块
	 *
	 * SinkInfo
	 * @return
	 */
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new PBabySink();
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
		return PBabyController.INSTANCE;
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
		return PBabyController.INSTANCE.view;
	}

	@Override
	public SinkInfo getSinkInfo() {
		// TODO Auto-generated method stub
		return init();
	}

}
