package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.AnnoucementController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

/**
 * @ClassName: AnnoucementSink 
 * @Description: 公告的框架实例 
 * @author JZJ 
 * @date 2010-8-27 上午10:44:04 
 * @version 1.0
 */
public class AnnoucementSink extends Sink {

	public static final String Group = "基本功能";
	public static final String Tag = "annoucement";
	public static final String Name = "公告管理";
	public static final String Desc = "公告基本信息管理";

	/**
	 * @Description: 初始化模块
	 * @return SinkInfo 返回类型
	 */
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new AnnoucementSink();
			}
		};
	}

	/**
	 * (非 Javadoc) Description: 得到模块控制器
	 * @return
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getController()
	 */
	@Override
	public AController getController() {
		return AnnoucementController.INSTANCE;
	}

	/**
	 * @Description: 得到模块事件列表
	 * @return void 返回类型
	 */
	public void getActionList() {
		// AcountController.INSTANCE.
	}

	/**
	 * (非 Javadoc) Description: 得到模块视图对象
	 * @return
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getWidget()
	 */
	@Override
	public Widget getWidget() {
		return AnnoucementController.INSTANCE.view;
	}

	/** 
	 * (非 Javadoc) 
	 * Description:  TODO(这里用一句话描述这个方法的作用)
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getSinkInfo() 
	 */ 
	
	@Override
	public SinkInfo getSinkInfo() {
		return init();
	}
}
