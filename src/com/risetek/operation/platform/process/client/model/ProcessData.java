package com.risetek.operation.platform.process.client.model;

import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class ProcessData extends OPlatformData {

	private int sum;
	
	public void parseData(String text){
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
