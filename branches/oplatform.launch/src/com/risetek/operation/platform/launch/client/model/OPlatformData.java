package com.risetek.operation.platform.launch.client.model;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.ResolveResponseInfo;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;


/**
 * @author Amber
 * 功能：此类用于数据格式化框架。
 * 2010-8-23 下午11:27:06
 */
public abstract class OPlatformData {

	private int sum;
	
	/**
	 * 功能：获取数据总条数
	 *
	 * int
	 * @return
	 */
	public int getSum(){
		return sum;
	}

	/**
	 * 功能：设置数据总条数
	 *
	 * void
	 * @param sum
	 */
	public void setSum(int sum){
		this.sum = sum;
	}
	
	private String ACTION_NAME = null ;
	
	private String data[][];

	public boolean autoRefresh = true;
	
	protected int PAGE_SIZE = UIConfig.TABLE_ROW_NORMAL ;
	
	protected int PAGE_POS = 0 ;
	
	public ResolveResponseInfo retInfo(String retInfo) {
		ResolveResponseInfo opRetInfo = new ResolveResponseInfo();
		opRetInfo = new ResolveResponseInfo();
		JSONObject jo = JSONParser.parse(retInfo).isObject();
		JSONObject actionInfo = (JSONObject) jo.get(Constanst.ACTION_INFO);
		opRetInfo.setActionInfo(actionInfo.toString());
		try {
			JSONNumber num = (JSONNumber)actionInfo.get(Constanst.ACTION_RETRUN_CODE);
			opRetInfo.setReturnCode(Integer.parseInt(num.toString()));
			opRetInfo.setReturnMessage(((JSONString)actionInfo.get(Constanst.ACTION_RETRUN_MESSAGE)).stringValue());
		} catch (Exception e) {
			opRetInfo.setReturnCode(-1);
		}
		
		return opRetInfo;
	}
	
	public int getPAGE_SIZE() {
		return PAGE_SIZE;
	}

	public void setPAGE_SIZE(int pAGE_SIZE) {
		PAGE_SIZE = pAGE_SIZE;
	}

	public int getPAGE_POS() {
		return PAGE_POS;
	}

	public void setPAGE_POS(int pAGE_POS) {
		if(pAGE_POS > 0){
			PAGE_POS = pAGE_POS-1;
		}		
	}

	public void setData(String data[][]) {
		this.data = data;
	}
	
	public String[][] getData() {
		return data;
	}

	public String getACTION_NAME() {
		return ACTION_NAME;
	}

	public void setACTION_NAME(String ACTION_NAME) {
		this.ACTION_NAME = ACTION_NAME;
	}
	
}
