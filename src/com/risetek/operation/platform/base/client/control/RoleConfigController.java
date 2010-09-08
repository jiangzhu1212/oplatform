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
import com.risetek.operation.platform.base.client.dialog.AddRoleDialog;
import com.risetek.operation.platform.base.client.dialog.AddRoleOperationDialog;
import com.risetek.operation.platform.base.client.dialog.DeleteRoleDialog;
import com.risetek.operation.platform.base.client.dialog.DeleteRoleOperationDialog;
import com.risetek.operation.platform.base.client.dialog.EditRoleNameDialog;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.base.client.service.RoleServiceAsync;
import com.risetek.operation.platform.base.client.view.RoleConfigView;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.json.constanst.Role;
import com.risetek.operation.platform.launch.client.json.constanst.RoleOperation;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class RoleConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	private final static RoleServiceAsync rs = GWT.create(RoleService.class);
	
	public static RoleConfigController INSTANCE = new RoleConfigController();
	final RoleConfigData data = new RoleConfigData();
	final RoleConfigData childData = new RoleConfigData();
	public final RoleConfigView view = new RoleConfigView();
	private int pagePoint = 1;
	private int childPagePoint = 1;
	
	private RoleConfigController(){
		actionNames.add("添加用户角色");
		actionNames.add("删除用户角色");
		actionNames.add("修改用户角色名称");
		actionNames.add("添加用户角色操作");
		actionNames.add("删除用户角色操作");
		actionNames.add("删除多条用户角色操作");
		actionNames.add("修改用户角色操作");
	}
	
	@Override
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public OPlatformData getData() {
		return data;
	}

	@Override
	public ArrayList<String> getActionNames() {
		return actionNames;
	}
	
	public void load(){
		rs.getRoleDataCount(new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				INSTANCE.data.setSum(result);
				resetPagePanel(INSTANCE, UIConfig.MAIN_TABLE_ROW_NORMAL, result);
			}
			public void onFailure(Throwable caught) {}
		});
		rs.getRolePage(UIConfig.MAIN_TABLE_ROW_NORMAL, new AsyncCallback<Role[]>() {
			public void onSuccess(Role[] result) {
				INSTANCE.data.parseResult(result);
				INSTANCE.view.render(INSTANCE.data);
			}
			public void onFailure(Throwable caught) {}
		});
		INSTANCE.view.renderChild(INSTANCE.childData);
	}
	
	public void load(final int pagePoint){
		rs.getRoleDataCount(new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				INSTANCE.resetPagePanel(INSTANCE, UIConfig.MAIN_TABLE_ROW_NORMAL, result);
			}
			public void onFailure(Throwable caught) {}
		});
		rs.getRolePageToPoint(UIConfig.MAIN_TABLE_ROW_NORMAL, pagePoint, new AsyncCallback<Role[]>() {
			public void onSuccess(Role[] result) {
				INSTANCE.data.parseResult(result);
				INSTANCE.view.render(INSTANCE.data);
			}
			public void onFailure(Throwable caught) {}
		});
		INSTANCE.view.renderChild(INSTANCE.childData);
	}
	
	public void loadChild(final String id, final String value, int childPagePoint){
		INSTANCE.view.setChildGridTitle(value);
		INSTANCE.view.setSelectRoleId(id);
		rs.getRoleOperationDataCount(id, new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				resetChildPagePanel(INSTANCE, UIConfig.CHILD_TABLE_ROW_NORMAL, result, id, value);
			}
			public void onFailure(Throwable caught) {}
		});
		rs.getRoleOperationByIdPage(UIConfig.CHILD_TABLE_ROW_NORMAL, id, childPagePoint, new AsyncCallback<RoleOperation[]>() {
			public void onSuccess(RoleOperation[] result) {
				INSTANCE.childData.parseChildResult(value, result);
				INSTANCE.view.renderChild(INSTANCE.childData);
			}
			public void onFailure(Throwable caught) {}
		});
	}
	
	public static class TableAction implements ClickHandler {
		
		String tisp_value;
		String id;
		
		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			int index = col;
			tisp_value = table.getText(row, 3);
			id = table.getText(row, 2);
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
				INSTANCE.loadChild(id, tisp_value, 1);
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
			public void onClick(ClickEvent event) {
				rs.deleteRole(id, new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						INSTANCE.load(INSTANCE.getPagePoint());
					}
					public void onFailure(Throwable caught) {}
				});
				rs.getRoleDataCount(new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						INSTANCE.resetPagePanel(INSTANCE, UIConfig.MAIN_TABLE_ROW_NORMAL, result);
					}
					public void onFailure(Throwable caught) {}
				});
				dialog.hide();
			}
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
		
		public class EditRoleNameControl extends DialogControl implements ClickHandler {
			EditRoleNameDialog dialog;
			public EditRoleNameControl(String value){
				dialog = new EditRoleNameDialog(value);
			}
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					String name = dialog.getNewValue();
					rs.editRoleName(id, name, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							INSTANCE.load(INSTANCE.getPagePoint());
						}
						public void onFailure(Throwable caught) {}
					});
					dialog.hide();
				} else {
					dialog.submit.setEnabled(false);
				}
			}
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}
	
	public static class ChildTableAction implements ClickHandler {
		String tisp_value = "";
		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			int index = col;
			tisp_value = table.getText(row, 3);
			String id = table.getText(row, 2);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			String msg = "模块：\"" + table.getText(row, 4) + "\"下的\"" + table.getText(row, 5) + "\"操作？";
			switch (index) {
			case 1:
				DeleteOneRoleOperationControl dooControl = new DeleteOneRoleOperationControl(id, tisp_value, msg);
				dooControl.dialog.submit.addClickHandler(dooControl);
				dooControl.dialog.show();
				break;
			case 2:
			case 3:
			case 4:
			case 5:
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
		
		public class DeleteOneRoleOperationControl extends DialogControl implements ClickHandler {
			DeleteRoleOperationDialog dialog;
			String id = "";
			RoleOperation ro = new RoleOperation();
			public DeleteOneRoleOperationControl (String id, String value, String msg){
				dialog = new DeleteRoleOperationDialog(value, msg);
				String tid = INSTANCE.view.getSelectRoleId();
				int rid = Integer.parseInt(tid);
				ro.setId(Integer.parseInt(id));
				ro.setRoleId(rid);
				this.id = id;
			}

			@Override
			public void onClick(ClickEvent event) {
				rs.deleteRoleOperationById(ro, new AsyncCallback<RoleOperation[]>() {
					public void onSuccess(RoleOperation[] result) {
						INSTANCE.childData.parseChildResult(tisp_value, result);
						INSTANCE.view.renderChild(INSTANCE.childData);
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
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					String roleName = dialog.getNewValue();
					rs.addRole(roleName, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							INSTANCE.load(INSTANCE.getPagePoint());
						}
						public void onFailure(Throwable caught) {}
					});
					dialog.hide();
					rs.getRoleDataCount(new AsyncCallback<Integer>() {
						public void onSuccess(Integer result) {
							INSTANCE.resetPagePanel(INSTANCE, UIConfig.MAIN_TABLE_ROW_NORMAL, result);
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
	
	public static class AddRoleOperationAction implements ClickHandler {
		public void onClick(ClickEvent event) {
			String name = INSTANCE.view.getChildGridTitle();
			String id = INSTANCE.view.getSelectRoleId();
			AddRoleOperationControl addROCont = new AddRoleOperationControl(id, name);
			addROCont.dialog.submit.addClickHandler(addROCont);
			if(name.trim().length()<1){
				Window.alert("未选择角色，不能执行\"添加角色操作\"");
				return;
			}
			addROCont.dialog.show();
		}
		public class AddRoleOperationControl extends DialogControl implements ClickHandler {
			AddRoleOperationDialog dialog;
			private String roleName = "";
			public AddRoleOperationControl(String id, String roleName){
				dialog = new AddRoleOperationDialog(id, roleName);
				this.roleName = roleName;
			}
			
			public void onClick(ClickEvent event) {
				RoleOperation[] ros = dialog.getRoleOperation();
				rs.addRoleOperation(ros, new AsyncCallback<RoleOperation[]>() {
					public void onSuccess(RoleOperation[] result) {
						INSTANCE.childData.parseChildResult(roleName, result);
						INSTANCE.view.renderChild(INSTANCE.childData);
					}
					public void onFailure(Throwable caught) {}
				});
				dialog.hide();
			}

			protected CustomDialog getDialog() {
				return null;
			}
		}
	}
	
	public static class DeleteManyRoleOperationAction implements ClickHandler {
		Grid grid;
		public DeleteManyRoleOperationAction(Grid grid){
			this.grid = grid;
		}
		
		public void onClick(ClickEvent event) {
			DeleteManyRoleOperationControl dmro = new DeleteManyRoleOperationControl(grid);
			dmro.dialog.submit.addClickHandler(dmro);
			dmro.dialog.show();
		}
		
		public class DeleteManyRoleOperationControl extends DialogControl implements ClickHandler {
			DeleteRoleOperationDialog dialog;
			ArrayList<RoleOperation> list = new ArrayList<RoleOperation>();
			String roleName = "";
			String tid = "";
			public DeleteManyRoleOperationControl(Grid grid){
				roleName = INSTANCE.view.getChildGridTitle();
				tid = INSTANCE.view.getSelectRoleId();
				for(int i=1;i<grid.getRowCount();i++){
					CheckBox box = (CheckBox)grid.getWidget(i, 0);
					if(box==null){
						continue;
					}
					if(box.getValue()){
						RoleOperation ro = new RoleOperation();
						String id = grid.getText(i, 2);
						ro.setId(Integer.parseInt(id));
						ro.setRoleId(Integer.parseInt(tid));
						list.add(ro);
					}
				}
				String value = INSTANCE.view.getChildGridTitle();
				dialog = new DeleteRoleOperationDialog(value, list.size());
			}
			public void onClick(ClickEvent event) {
				RoleOperation[] ros = new RoleOperation[list.size()];
				for(int i=0;i<ros.length;i++){
					ros[i] = list.get(i);
				}
				rs.deleteRoleOperation(ros, new AsyncCallback<RoleOperation[]>() {
					public void onSuccess(RoleOperation[] result) {
						INSTANCE.childData.parseChildResult(roleName, result);
						INSTANCE.view.renderChild(INSTANCE.childData);
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
	}
	
	public static class DeleteManyRoleAction implements ClickHandler {
		Grid grid;
		public DeleteManyRoleAction(Grid grid){
			this.grid = grid;
		}
		
		public void onClick(ClickEvent event) {
			int si = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box==null){
					continue;
				}
				if(box.getValue()){
					si++;
				}
			}
			if(si<1){
				Window.alert("未选择任何记录！");
				return;
			}
			DeleteManyRoleControl dmro = new DeleteManyRoleControl(grid);
			dmro.dialog.submit.addClickHandler(dmro);
			dmro.dialog.show();
		}
		
		public class DeleteManyRoleControl extends DialogControl implements ClickHandler {
			DeleteRoleDialog dialog;
			ArrayList<Role> list = new ArrayList<Role>();
			String roleName = "";
			String tid = "";
			public DeleteManyRoleControl(Grid grid){
				roleName = INSTANCE.view.getChildGridTitle();
				tid = INSTANCE.view.getSelectRoleId();
				for(int i=1;i<grid.getRowCount();i++){
					CheckBox box = (CheckBox)grid.getWidget(i, 0);
					if(box==null){
						continue;
					}
					if(box.getValue()){
						Role role = new Role();
						String id = grid.getText(i, 2);
						role.setId(Integer.parseInt(id));
						list.add(role);
					}
				}
				String value = INSTANCE.view.getChildGridTitle();
				dialog = new DeleteRoleDialog(value, list.size());
			}
			public void onClick(ClickEvent event) {
				Role[] ros = new Role[list.size()];
				for(int i=0;i<ros.length;i++){
					ros[i] = list.get(i);
				}
				rs.deleteManyRole(ros, new AsyncCallback<Void>() {
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
	}
	
	public static class ShowRoleContentAction implements ClickHandler {
		Grid grid;
		String name = "";
		public ShowRoleContentAction(Grid grid){
			this.grid = grid;
		}
		@Override
		public void onClick(ClickEvent event) {
			String id = "";
			int ci = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box==null){
					continue;
				}
				if(box.getValue()){
					ci++;
					id = grid.getText(i, 2);
					name = grid.getText(i, 3);
				}
			}
			if(ci==0){
				Window.alert("未选择任何记录！");
				return;
			} else if (ci>1){
				Window.alert("只能选择一条记录来查看详细操作内容！");
				return;
			} else {
				INSTANCE.view.setChildGridTitle(name);
				INSTANCE.view.setSelectRoleId(id);
			}
			rs.getRoleOperationById(id, new AsyncCallback<RoleOperation[]>() {
				public void onSuccess(RoleOperation[] result) {
					INSTANCE.childData.parseChildResult(name, result);
					INSTANCE.view.renderChild(INSTANCE.childData);
				}
				public void onFailure(Throwable caught) {}
			});
		}
	}

	public int getPagePoint() {
		return pagePoint;
	}

	public void setPagePoint(int pagePoint) {
		this.pagePoint = pagePoint;
	}

	@Override
	public void setChildPagePoint(int point) {
		childPagePoint = point;
	}

	@Override
	public int getChildPagePoint() {
		return childPagePoint;
	}

	@Override
	public OPlatformData getChildData() {
		return childData;
	}
}
