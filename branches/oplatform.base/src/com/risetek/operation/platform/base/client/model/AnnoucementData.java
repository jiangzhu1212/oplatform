package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: AnnoucementData 
 * @Description: 公告格式化数据并返回模块数据 
 * @author JZJ 
 * @date 2010-8-27 上午10:42:54 
 * @version 1.0
 */
public class AnnoucementData extends OPlatformData {

	private int sum;
	
	/**
	 * @Description: 格式化数据，并注入到模块的数据对象中 
	 * @param text  参数 
	 * @return void 返回类型 
	 */
	public void parseData(String text){
		System.out.println("the text is :" + text);
		setSum(100);
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
