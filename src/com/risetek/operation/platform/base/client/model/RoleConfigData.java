package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.base.client.entry.Role;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class RoleConfigData extends OPlatformData {

	private int sum;
	
	@Override
	public int getSum() {
		return sum;
	}

	@Override
	public void setSum(int sum) {
		this.sum = sum;
	}

	public void parseResult(Role[] result) {
		String[][] data = new String[result.length][2];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = result[i].getRoleName();
		}
		setData(data);
	}
}
