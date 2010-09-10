package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.operation.platform.base.client.dialog.AddUserDialog;
import com.risetek.operation.platform.base.client.dialog.ChangUserStatusDialog;
import com.risetek.operation.platform.base.client.dialog.ResetUserPasswordDialog;
import com.risetek.operation.platform.base.client.dialog.ShowUserInfoDialog;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.base.client.model.UserConfigData;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.base.client.service.RoleServiceAsync;
import com.risetek.operation.platform.base.client.service.UserService;
import com.risetek.operation.platform.base.client.service.UserServiceAsync;
import com.risetek.operation.platform.base.client.view.UserConfigView;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.Role;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class UserConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	private final static UserServiceAsync us = GWT.create(UserService.class);
	private final static RoleServiceAsync rs = GWT.create(RoleService.class);
	
	public static UserConfigController INSTANCE = new UserConfigController();
	final UserConfigData data = new UserConfigData();
	final RoleConfigData roleData = new RoleConfigData();
	public final UserConfigView view = new UserConfigView();
	private int pagePoint = 1;
	
	private UserConfigController(){
	}
	
	@Override
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public OPlatformData getData() {
		return data;
	}
	
	public OPlatformData getRoleData() {
		return roleData;
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
		rs.getAllRole(new AsyncCallback<Role[]>() {
			public void onSuccess(Role[] result) {
				INSTANCE.roleData.parseResult(result);
				us.getUserDataCount(new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						INSTANCE.data.setSum(result);
						resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result);
					}
					public void onFailure(Throwable caught) {}
				});
				us.getUserPage(UIConfig.TABLE_ROW_NORMAL, new AsyncCallback<User[]>() {
					public void onSuccess(User[] result) {
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			}
			public void onFailure(Throwable caught) {}
		});
	}

	@Override
	public void setPagePoint(int point) {
		pagePoint = point;
	}

	@Override
	public int getPagePoint() {
		return pagePoint;
	}

	public static class AddUserAction implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			AddUserControl addUserCont = new AddUserControl();
			addUserCont.dialog.submit.addClickHandler(addUserCont);
			addUserCont.dialog.show();
		}
		
		public class AddUserControl extends DialogControl implements ClickHandler{
			AddUserDialog dialog = new AddUserDialog(INSTANCE.roleData);
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					User user = dialog.getNewValue();
					us.registUser(user, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							INSTANCE.load(INSTANCE.getPagePoint());
						}
						public void onFailure(Throwable caught) {
						}
					});
					dialog.hide();
					us.getUserDataCount(new AsyncCallback<Integer>() {
						public void onSuccess(Integer result) {
							INSTANCE.resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result);
						}
						public void onFailure(Throwable caught) {}
					});
					us.sendPasswordToEmail(user, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {}
						public void onFailure(Throwable caught) {}
					});
					ShowUserInfoDialog infod = new ShowUserInfoDialog(user, INSTANCE.roleData);
					infod.show();
				} else {
					dialog.submit.setEnabled(false);
				}
			}

			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class TableAction implements ClickHandler {
		String id;
		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            String style = table.getRowFormatter().getStyleName(row);
            if(style.equals("white")){
            	Window.alert("该条数据已失效，不能操作！");
            	return;
            }
			int index = col;
			String tisp_value = table.getText(row, 3);
			id = table.getText(row, 2);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			switch (index) {
			case 1:
				OffUserControl duc = INSTANCE.new OffUserControl(tisp_value, id);
				duc.dialog.submit.addClickHandler(duc);
				duc.dialog.show();
				break;
			case 6:
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 7:
			case 8:
			case 9:
				CheckBox box = (CheckBox)table.getWidget(row, 0);
				if(box.getValue()){
					box.setValue(false);
				} else {
					box.setValue(true);
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	public class OffUserControl extends DialogControl implements ClickHandler {
		ChangUserStatusDialog dialog;
		String id;
		public OffUserControl(String value, String id){
			dialog = new ChangUserStatusDialog(value, UIConfig.CHANG_USER_STATUS_TYPE_OFF);
			this.id = id;
		}
		public void onClick(ClickEvent event) {
			us.offUser(id, new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					INSTANCE.load(INSTANCE.getPagePoint());
				}
				public void onFailure(Throwable caught) {}
			});
			dialog.hide();
		}
		@Override
		protected CustomDialog getDialog() {
			return dialog;
		}
	}
	
	public static class OffUserAction implements ClickHandler {
		public void onClick(ClickEvent event) {
			String tisp_value = "";
			String id = "";
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						tisp_value = grid.getText(i, 3);
						id = grid.getText(i, 2);
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			OffUserControl duc = INSTANCE.new OffUserControl(tisp_value, id);
			duc.dialog.submit.addClickHandler(duc);
			duc.dialog.show();
		}
	}
	
	public static class ResetPasswordAction implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			String value = "";
			String id = "";
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						value = grid.getText(i, 3);
						id = grid.getText(i, 2);
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			ResetPasswordControl rpc = new ResetPasswordControl(value, id);
			rpc.dialog.submit.addClickHandler(rpc);
			rpc.dialog.show();
		}
		
		public class ResetPasswordControl extends DialogControl implements ClickHandler{
			ResetUserPasswordDialog dialog;
			String id;
			public ResetPasswordControl(String value, String id){
				dialog = new ResetUserPasswordDialog(value);
				this.id = id;
			}
			
			public void onClick(ClickEvent event) {
				us.resetUserPassword(id, new AsyncCallback<User[]>() {
					public void onSuccess(User[] result) {
						dialog.hide();
						User user = result[0];
						ShowUserInfoDialog suid = new ShowUserInfoDialog(user, INSTANCE.roleData);
						suid.show();
					}
					public void onFailure(Throwable caught) {}
				});
			}

			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class HangUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String value = "";
			String id = "";
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						value = grid.getText(i, 3);
						id = grid.getText(i, 2);
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			HangUserControl huc = new HangUserControl(value, id);
			huc.dialog.submit.addClickHandler(huc);
			huc.dialog.show();
		}
		
		public class HangUserControl extends DialogControl implements ClickHandler {
			ChangUserStatusDialog dialog;
			String id;
			public HangUserControl(String value, String id){
				dialog = new ChangUserStatusDialog(value, UIConfig.CHANG_USER_STATUS_TYPE_HANG);
				this.id = id;
			}
			public void onClick(ClickEvent event) {
				us.hangUser(id, new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						dialog.hide();
						INSTANCE.load(INSTANCE.getPagePoint());
					}
					public void onFailure(Throwable caught) {}
				});
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class KickUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String value = "";
			String id = "";
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						value = grid.getText(i, 3);
						id = grid.getText(i, 2);
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			KickUserControl huc = new KickUserControl(value, id);
			huc.dialog.submit.addClickHandler(huc);
			huc.dialog.show();
		}
		
		public class KickUserControl extends DialogControl implements ClickHandler {
			ChangUserStatusDialog dialog;
			String id;
			public KickUserControl(String value, String id){
				dialog = new ChangUserStatusDialog(value, UIConfig.CHANG_USER_STATUS_TYPE_KICK);
				this.id = id;
			}
			public void onClick(ClickEvent event) {
				us.kickUser(id, new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						dialog.hide();
						INSTANCE.load(INSTANCE.getPagePoint());
					}
					public void onFailure(Throwable caught) {}
				});
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class ResetUserStatucAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String value = "";
			String id = "";
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						value = grid.getText(i, 3);
						id = grid.getText(i, 2);
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			ResetUserStatusControl huc = new ResetUserStatusControl(value, id);
			huc.dialog.submit.addClickHandler(huc);
			huc.dialog.show();
		}
		
		public class ResetUserStatusControl extends DialogControl implements ClickHandler {
			ChangUserStatusDialog dialog;
			String id;
			public ResetUserStatusControl(String value, String id){
				dialog = new ChangUserStatusDialog(value, UIConfig.CHANG_USER_STATUS_TYPE_RESET);
				this.id = id;
			}
			public void onClick(ClickEvent event) {
				us.resetUserStatuc(id, new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						dialog.hide();
						INSTANCE.load(INSTANCE.getPagePoint());
					}
					public void onFailure(Throwable caught) {}
				});
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
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
