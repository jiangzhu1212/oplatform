package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.base.client.entry.BillCard007;
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
	public void parseResult(BillCard007[] re) {
		String[][] data = new String[re.length][7];
		for (int i = 0; i < re.length; i++) {
			data[i][0] = re[i].getCHARGE_PHONE_NUMBER();
			data[i][1] = re[i].getBILL_EXTERN_ID();
			data[i][2] = re[i].getAmount();
			data[i][3] = getStatus(re[i].getSTATUS());
			data[i][4] = re[i].getDatetime();
			data[i][5] = re[i].getRetInfo();			
			if (re[i].getPayState().equals("paid")) {
				data[i][6] = "已支付";
			} else {
				data[i][6] = "未支付";
			}
		}
		setData(data);
		setSum(re.length);
	}
	
	
	private String getStatus(int ret){
		String status = "";
		if (ret == 0) {
			status = "已充值";
		} else if (ret == 1) {
			status = "未充值";
		} else if (ret == 2) {
			status = "充值失败";
		} else if (ret == 3) {
			status = "已委托";
		} else if (ret == 4) {
			status = "未委托";
		}
		return status;
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
