package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.risetek.operation.platform.base.client.view.UserConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class UserConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	public static UserConfigController INSTANCE = new UserConfigController();
	public final UserConfigView view = new UserConfigView();
	
	public UserConfigController(){
	}
	
	@Override
	public OPlatformTableView getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public OPlatformData getData() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setChildPagePoint(int point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getChildPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OPlatformData getChildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
		// TODO Auto-generated method stub
		
	}
}
