package com.risetek.operation.platform.launch.client.model;

/**
 * @author Amber
 * 功能：此类用于数据格式化框架，暂时摆起。不一定会用到
 * 2010-8-23 下午11:27:06
 */
public abstract class OPlatformData {

	private int sum;

	/**
	 * 功能：获取数据总条数
	 *
	 * int
	 * @return
	 */
	public int getSum() {
		return sum;
	}

	/**
	 * 功能：设置数据总条数
	 *
	 * void
	 * @param sum
	 */
	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
