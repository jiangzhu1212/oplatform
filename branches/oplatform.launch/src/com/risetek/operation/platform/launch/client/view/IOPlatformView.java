package com.risetek.operation.platform.launch.client.view;

import java.util.ArrayList;

import com.risetek.operation.platform.launch.client.control.ClickActionHandler;

public interface IOPlatformView {
	public void disablePrivate();
	public void enablePrivate();
	public void ProcessControlKey(int keyCode, boolean alt);
	public ArrayList<ClickActionHandler> getActionList();
}
