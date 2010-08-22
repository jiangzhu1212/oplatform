package com.risetek.operation.platform.base.client.control;

import com.risetek.operation.platform.base.client.view.BaseView;
import com.risetek.operation.platform.launch.client.control.AController;

public class BaseController extends AController {

	public static BaseController INSTANCE = new BaseController();
	
	public final BaseView view = new BaseView();
	
	private BaseController(){
		
	}
	
	@Override
	public void disablePrivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePrivate() {
		// TODO Auto-generated method stub
		
	}

}
