package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class TransBindData extends OPlatformData {
	
	private int sum;
	
	public void parseData(String text){
		System.out.println("the text is :" + text);
		setSum(100);
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	
}
