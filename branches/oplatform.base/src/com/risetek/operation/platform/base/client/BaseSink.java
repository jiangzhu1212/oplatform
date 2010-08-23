package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.BaseController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

/**
 * @author Amber
 * 功能：模块的框架实例
 * 2010-8-23 下午11:39:33
 */
public class BaseSink extends Sink {

	public static final String Group = "basegroup";
	public static final String Tag = "base";
	public static final String Name = "基本";
	public static final String Desc = "这是一个基本功能";
	
	/**
	 * 功能：初始化模块
	 *
	 * SinkInfo
	 * @return
	 */
	public static SinkInfo init(){
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new BaseSink();
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
		return BaseController.INSTANCE;
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
		return BaseController.INSTANCE.view;
	}
}
