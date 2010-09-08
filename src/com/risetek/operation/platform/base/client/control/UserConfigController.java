package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.risetek.operation.platform.base.client.model.UserConfigData;
import com.risetek.operation.platform.base.client.view.UserConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class UserConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	public static UserConfigController INSTANCE = new UserConfigController();
	final UserConfigData data = new UserConfigData();
	public final UserConfigView view = new UserConfigView();
	
	private int pagePoint = 1;
	
	public UserConfigController(){
	}
	
	@Override
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public OPlatformData getData() {
		return null;
	}

	@Override
	public ArrayList<String> getActionNames() {
		if(actionNames.isEmpty()){
			return null;
		} else {
			return actionNames;
		}
	}

	@Override
	public void load(int pagePoint) {
		INSTANCE.data.parseResult();
		INSTANCE.view.render(INSTANCE.data);
	}

	@Override
	public void setPagePoint(int point) {
		pagePoint = point;
	}

	@Override
	public int getPagePoint() {
		return pagePoint;
	}

	@Override
	public void setChildPagePoint(int point) {
		
	}

	@Override
	public int getChildPagePoint() {
		return 0;
	}

	@Override
	public OPlatformData getChildData() {
		return null;
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
	}
}
