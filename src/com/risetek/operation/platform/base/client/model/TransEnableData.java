package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransEnableData extends OPlatformData {
	
	private int sum;
	
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
