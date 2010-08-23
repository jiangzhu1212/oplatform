package com.risetek.operation.platform.launch.client.sink;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.control.AController;

/**
 * @author Amber
 * 功能：模块框架
 * 2010-8-23 下午11:28:12
 */
public abstract class Sink {

	/**
	 * 功能：得到模块的控制器
	 *
	 * AController
	 * @return
	 */
	public abstract AController getController();
	/** 
	 * 功能： 得到模块视图
	 *(non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Composite#getWidget()
	 */
	public abstract Widget getWidget();
}
