package com.risetek.operation.platform.start.client.control;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.start.client.dialog.ChangUserInfoDialog;
import com.risetek.operation.platform.start.client.dialog.ChangUserPasswordDialog;
import com.risetek.operation.platform.start.client.service.LoginService;
import com.risetek.operation.platform.start.client.service.LoginServiceAsync;

public class LoginController extends AController {

	private final static LoginServiceAsync ls = GWT.create(LoginService.class);
	
	public static class LogoutAction implements ClickHandler {
		public void onClick(ClickEvent event) {
			ls.setUserLogout(OplatformLaunch.loginUser, new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					RootPanel.get("nowTime").remove(0);
					RootPanel.get("title").remove(0);
					RootPanel.get("main").remove(0);
					RootPanel.get("userinfo").remove(0);
					RootPanel.get("copyright").remove(0);
					History.fireCurrentHistoryState();
					Window.alert("注销成功！");
					Window.Location.reload();
				}
				public void onFailure(Throwable caught) {}
			});
		}
	}
	
	public static class ChangUserInfoAction implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			ChangUserInfoControl cuif = new ChangUserInfoControl();
			cuif.dialog.submit.addClickHandler(cuif);
			cuif.dialog.show();
		}
		
		public class ChangUserInfoControl extends DialogControl implements ClickHandler {
			ChangUserInfoDialog dialog;
			public ChangUserInfoControl(){
				dialog = new ChangUserInfoDialog(OplatformLaunch.loginUser);
			}
			@Override
			public void onClick(ClickEvent event) {
				if(dialog.isVaild()){
					dialog.hide();
					User user = dialog.getValue();
					ls.updateUserInfo(user, new AsyncCallback<User>() {
						public void onSuccess(User result) {
							OplatformLaunch.loginUser = result;
							Window.alert("修改成功！");
						}
						public void onFailure(Throwable caught) {}
					});
				} else {
					dialog.submit.setEnabled(false);
				}
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class ChangUserPasswordAction implements ClickHandler {
		User user;
		public ChangUserPasswordAction(User user){
			this.user = user;
		}
		
		public void onClick(ClickEvent event) {
			ChangUserPasswordControl cupc = new ChangUserPasswordControl(user);
			cupc.dialog.submit.addClickHandler(cupc);
			cupc.dialog.show();
		}
		
		public class ChangUserPasswordControl extends DialogControl implements ClickHandler {
			ChangUserPasswordDialog dialog;
			public ChangUserPasswordControl(User user){
				dialog = new ChangUserPasswordDialog(user);
			}
			public void onClick(ClickEvent event) {
				
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	@Override
	public OPlatformTableView getView() {return null;}

	@Override
	public OPlatformData getData() {return null;}

	@Override
	public OPlatformData getChildData() {return null;}

	@Override
	public void load(int pagePoint) {}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {}

	@Override
	public void setPagePoint(int point) {}

	@Override
	public int getPagePoint() {return 0;}

	@Override
	public void setChildPagePoint(int point) {}

	@Override
	public int getChildPagePoint() {return 0;}

}
