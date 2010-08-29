package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @author Amber
 * 功能：格式化并返回模块数据
 * 2010-8-23 下午11:50:37
 */
public class BaseData extends OPlatformData {

	private int sum;
	
	/**
	 * 功能：格式化数据，并注入到模块的数据对象中（代码还未写完）
	 *
	 * void
	 * @param text
	 */
	public void parseData(String text){
	}

	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
