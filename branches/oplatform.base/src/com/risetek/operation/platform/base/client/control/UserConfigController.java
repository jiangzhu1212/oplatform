package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.view.UserConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class UserConfigController extends AController {

	public static UserConfigController INSTANCE = new UserConfigController();
	public final UserConfigView view = new UserConfigView();
	@Override
	public Widget getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPlatformData getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getActionNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
