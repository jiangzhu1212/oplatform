package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.base.client.entry.Bank;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @ClassName: BankData 
 * @Description: 发卡行格式化数据并返回模块数据
 * @author JZJ 
 * @date 2010-8-26 下午02:04:13 
 * @version 1.0
 */
public class Card007Data extends OPlatformData {

	private int sum;
	
	/**
	 * @Description: 格式化数据，并注入到模块的数据对象中 
	 * @param text  参数 
	 * @return void 返回类型 
	 */
	public void parseResult(Bank[] re) {
		String[][] data = new String[re.length][4];
		for (int i = 0; i < re.length; i++) {
			data[i][0] = re[i].getBank_code();
			data[i][1] = re[i].getName();
			data[i][2] = re[i].getValidity();
			data[i][3] = re[i].getDescription();
		}
		setData(data);
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
