package com.risetek.operation.platform.launch.client.control;

public class OpRetInfo {
	
	private int returnCode = -1;
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	private String returnMessage;
	
	private String actionInfo = "";

	
	public String getActionInfo() {
		return actionInfo;
	}
	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
}
