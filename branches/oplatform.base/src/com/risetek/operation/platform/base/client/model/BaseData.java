package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @author Amber
 * 功能：格式化并返回模块数据
 * 2010-8-23 下午11:50:37
 */
public class BaseData extends OPlatformData {

	/**
	 * 功能：格式化数据，并注入到模块的数据对象中（代码还未写完）
	 *
	 * void
	 * @param text
	 */
	public void parseData(String text){
		System.out.println("the text is :" + text);
		setSum(100);
	}
	
}
