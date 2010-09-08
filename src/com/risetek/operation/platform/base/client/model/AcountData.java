package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: AcountData
 * @Description: 银行卡列格式化并返回模块数据
 * @author JZJ
 * @date 2010-8-26 下午02:03:46
 * @version 1.0
 */
public class AcountData extends OPlatformData {

	/**
	 * @Description: 格式化数据，并注入到模块的数据对象中
	 * @param text  参数
	 * @return void 返回类型
	 */
	public void parseData(String text) {
		System.out.println("the text is :" + text);
		setSum(100);
	}
}
