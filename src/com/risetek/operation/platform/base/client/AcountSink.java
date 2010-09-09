package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.AcountController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

/**
 * @ClassName: AcountSink
 * @Description: 银行卡列表的框架实例
 * @author JZJ
 * @date 2010-8-26 下午02:01:21
 * @version 1.0
 */
public class AcountSink extends Sink {

	public static final String Group = "基本功能";
	public static final String Tag = "acount";
	public static final String Name = "账户管理";
	public static final String Desc = "银行卡列表基本信息管理";

	/**
	 * @Description: 初始化模块
	 * @return SinkInfo 返回类型
	 */
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new AcountSink();
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
		return AcountController.INSTANCE;
	}

	/**
	 * (非 Javadoc) Description: 得到模块视图对象
	 * @return
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getWidget()
	 */
	@Override
	public Widget getWidget() {
		return AcountController.INSTANCE.view;
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
