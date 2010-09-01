package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.dialog.AddRoleDialog;
import com.risetek.operation.platform.base.client.dialog.DeleteRoleDialog;
import com.risetek.operation.platform.base.client.dialog.EditRoleNameDialog;
import com.risetek.operation.platform.base.client.entry.Role;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.base.client.service.RoleServiceAsync;
import com.risetek.operation.platform.base.client.view.RoleConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class RoleConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	private final static RoleServiceAsync rs = GWT.create(RoleService.class);
	
	public static RoleConfigController INSTANCE = new RoleConfigController();
	final RoleConfigData data = new RoleConfigData();
	
	public final RoleConfigView view = new RoleConfigView();
	
	@Override
	public ArrayList<String> getActionNames() {
		return actionNames;
	}
	
	public RoleConfigController(){
		actionNames.add("添加用户角色");
		actionNames.add("删除用户角色");
		actionNames.add("修改用户角色名称");
		actionNames.add("添加用户角色操作");
		actionNames.add("删除用户角色操作");
		actionNames.add("删除多条用户角色操作");
		actionNames.add("修改用户角色操作");
	}
	
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

	public static void load(){
		rs.getAllRole(new AsyncCallback<Role[]>() {
			
			@Override
			public void onSuccess(Role[] result) {
				INSTANCE.data.setSum(result.length);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("faild");
			}
		});
		INSTANCE.view.render(INSTANCE.data);
		INSTANCE.view.renderChild(INSTANCE.data);
	}
	
	public static class TableAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
//			String rowid = table.getText(row, 0);
			int index = col;
			String tisp_value = table.getText(row, 3);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			switch (index) {
			case 1:
				DeleteRoleControl delrole = new DeleteRoleControl(tisp_value);
				delrole.dialog.submit.addClickHandler(delrole);
				delrole.dialog.show();
				break;
			case 2:
				break;
			case 3:
				EditRoleNameControl editName = new EditRoleNameControl(tisp_value);
				editName.dialog.submit.addClickHandler(editName);
				editName.dialog.show();
				break;
			default:
				break;
			}
		}
		
		public class DeleteRoleControl extends DialogControl implements ClickHandler {
			public DeleteRoleDialog dialog;
			public DeleteRoleControl(String value){
				dialog = new DeleteRoleDialog(value);
			}
			
			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
		
		public class EditRoleNameControl extends DialogControl implements ClickHandler {
			EditRoleNameDialog dialog;
			
			public EditRoleNameControl(String value){
				dialog = new EditRoleNameDialog(value);
			}
			
			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}
	
	public static class ChildTableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
		}
	}
	
	public static class AddRoleAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			AddRoleControl addRoleCont = new AddRoleControl();
			addRoleCont.dialog.submit.addClickHandler(addRoleCont);
			addRoleCont.dialog.show();
		}
		
		public class AddRoleControl extends DialogControl implements ClickHandler {
			AddRoleDialog dialog = new AddRoleDialog();
			@Override
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					dialog.hide();
				}
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
}
