package com.risetek.operation.platform.log.client.model;

import com.risetek.operation.platform.launch.client.entry.Log;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class LogData extends OPlatformData {

	public void parseResult(Log[] result){
		String[][] data = new String[result.length][4];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = result[i].getKey();
			data[i][2] = result[i].getContent();
			data[i][3] = Util.formatTimestampToString(result[i].getLogTime());
		}
		setData(data);
	}
	
}
