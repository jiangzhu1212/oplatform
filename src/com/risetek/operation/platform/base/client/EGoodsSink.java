package com.risetek.operation.platform.base.client;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.control.EGoodsController;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class EGoodsSink extends Sink{


	public static final String Group = "基本功能";
	public static final String Tag = "eGoods";
	public static final String Name = "电子货物管理";
	public static final String Desc = "电子货物的基本信息管理";

	/**
	 * @Description: 初始化模块
	 * @return SinkInfo 返回类型
	 */
	public static SinkInfo init() {
		return new SinkInfo(Group, Tag, Name, Desc) {
			public Sink createInstance() {
				return new EGoodsSink();
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
		return EGoodsController.INSTANCE;
	}

	/**
	 * (非 Javadoc) Description: 得到模块视图对象
	 * @return
	 * @see com.risetek.operation.platform.launch.client.sink.Sink#getWidget()
	 */
	@Override
	public Widget getWidget() {
		return EGoodsController.INSTANCE.view;
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
