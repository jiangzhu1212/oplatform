package com.risetek.operation.platform.base.client.control;

import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.view.RoleConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class RoleConfigController extends AController {

	public static RoleConfigController INSTANCE = new RoleConfigController();
	
	public final RoleConfigView view = new RoleConfigView();
	
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

}
