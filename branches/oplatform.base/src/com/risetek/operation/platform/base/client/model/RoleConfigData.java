package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.json.constanst.Role;
import com.risetek.operation.platform.launch.client.json.constanst.RoleOperation;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class RoleConfigData extends OPlatformData {

	public void parseResult(Role[] result) {
		String[][] data = new String[result.length][2];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = result[i].getRoleName();
		}
		setData(data);
	}

	public void parseChildResult(String tisp_value, RoleOperation[] result) {
		setSum(result.length);
		String[][] data = new String[result.length][4];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = tisp_value;
			data[i][2] = result[i].getOperationMode();
			data[i][3] = result[i].getOperationAction();
		}
		setData(data);
	}
}
