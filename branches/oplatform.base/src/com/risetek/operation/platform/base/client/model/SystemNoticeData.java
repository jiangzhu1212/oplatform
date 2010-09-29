package com.risetek.operation.platform.base.client.model;

import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.util.Util;

public class SystemNoticeData extends OPlatformData {

	public void parseResult(Notice[] result){
		String[][] data = new String[result.length][6];
		for(int i=0;i<result.length;i++){
			data[i][0] = result[i].getId().toString();
			data[i][1] = result[i].getContent();
			data[i][2] = result[i].getRoles();
			data[i][3] = Util.formatTimestampToString(result[i].getEffectTime());
			data[i][4] = Util.formatTimestampToString(result[i].getFailureTime());
			data[i][5] = Util.formatTimestampToString(result[i].getAddTime());
		}
		setData(data);
	}
	
}
