package com.risetek.operation.platform.launch.client.model;

import org.mortbay.util.ajax.JSON;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;


/**
 * @author Amber
 * 功能：此类用于数据格式化框架，暂时摆起。不一定会用到
 * 2010-8-23 下午11:27:06
 */
public abstract class OPlatformData {

	/**
	 * 功能：获取数据总条数
	 *
	 * int
	 * @return
	 */
	public abstract int getSum();

	/**
	 * 功能：设置数据总条数
	 *
	 * void
	 * @param sum
	 */
	public abstract void setSum(int sum);
	
	private String data[][];
	
	public void setData(String data[][]) {
		this.data = data;
	}
	
	public String[][] getData() {
		return data;
	}
	
	public ResolveResponseInfo[] retInfo(String retInfo) {
		ResolveResponseInfo opRetInfo[] = new ResolveResponseInfo[1];
		opRetInfo[0] = new ResolveResponseInfo();
		JSONObject jo = JSONParser.parse(retInfo).isObject();
		JSONObject actionInfo = (JSONObject) jo.get(Constanst.ACTION_INFO);
		opRetInfo[0].setActionInfo(actionInfo.toString());
		try {
			JSONNumber num = (JSONNumber)actionInfo.get(Constanst.RETURN_CODE);
			opRetInfo[0].setReturnCode(Integer.parseInt(num.toString()));
			opRetInfo[0].setReturnMessage(((JSONString)actionInfo.get(Constanst.RETURN_MESSAGE)).stringValue());
		} catch (Exception e) {
			opRetInfo[0].setReturnCode(-1);
		}
		
		return opRetInfo;
	}
	
}
