package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.BankController;
import com.risetek.operation.platform.base.client.view.BankView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

/**
 * @ClassName: BankSink
 * @Description: 发卡行模块的框架实例
 * @author JZJ
 * @date 2010-8-26 下午02:03:16
 * @version 1.0
 */
public class BankSink extends Sink {

	public static final String Group = "basegroup";
	public static final String Tag = "base";
	public static final String Name = "发卡行管理";
	public static final String Desc = "发卡行的基本信息管理";

	/**
	 * @Description: 初始化模块
	 * @return SinkInfo 返回类型
	 */
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new BankSink();
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
		return BankController.INSTANCE;
	}

	/**
	 * @Description: 得到模块事件列表
	 * @return void 返回类型
	 */
	public void getActionList() {
		// BankController.INSTANCE;
	}

	/**
	 * (非 Javadoc) Description: 得到模块视图对象
	 * @return
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getWidget()
	 */
	@Override
	public Widget getWidget() {
		BankView.descript = Desc;
		return BankController.INSTANCE.view;
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
