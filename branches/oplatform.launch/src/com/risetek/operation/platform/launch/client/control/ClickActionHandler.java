package com.risetek.operation.platform.launch.client.control;

import com.google.gwt.event.dom.client.ClickHandler;

/**
 * @author Amber
 * 功能：重载了点击时间句柄，增加返回创建时间名称方法
 * 2010-8-23 下午11:26:10
 */
public interface ClickActionHandler extends ClickHandler {

	/**
	 * 功能：返回当前响应事件的名称
	 *
	 * String
	 * @return
	 */
	public String getActionName();
	
}
