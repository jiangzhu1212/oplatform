package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class UserConfigData extends OPlatformData {

	public void parseResult(User[] result){
		String[][] data = new String[result.length][8];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = result[i].getUserName();
			data[i][2] = result[i].getStatus().toString();
			data[i][3] = result[i].getRole().toString();
			data[i][4] = result[i].getEmail();
			data[i][5] = Util.formatTimestampToString(result[i].getLastLoginTime());
			data[i][6] = result[i].getLastLoginAddress();
			data[i][7] = Util.formatTimestampToString(result[i].getRegistTime());
		}
		setData(data);
	}
	
	
	
}
