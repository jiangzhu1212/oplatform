package com.risetek.operation.platform.launch.client.control;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Amber
 * 功能：控制器接口
 * 2010-8-23 下午11:24:56
 */
public abstract class AController {
	/**
	 * 功能：用于加载模块时获取视图接口
	 *
	 * Widget
	 * @return
	 */
	public abstract Widget getView(); 
}
