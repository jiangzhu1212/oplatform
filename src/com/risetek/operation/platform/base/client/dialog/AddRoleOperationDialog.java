package com.risetek.operation.platform.base.client.dialog;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.RoleOperation;
import com.risetek.operation.platform.launch.client.sink.Sink;

public class AddRoleOperationDialog extends CustomDialog {

	private Tree tree;
	private String id;
	private String[][] childData;
	
	public AddRoleOperationDialog(String id, String roleName, String[][] childData){
		this.childData = childData;
		this.id = id;
		setText("向角色\"" + roleName + "\"添加模块和操作");
		setDescript("向指定的角色添加实际操作内容");
		ArrayList<Sink> list = OplatformLaunch.sinkList;
		tree = new Tree();
		for(int i=0;i<list.size();i++){
			Sink sink = list.get(i);
			String group = sink.getSinkInfo().getGroup();
			int count = tree.getItemCount();
			boolean add = true;
			TreeItem groupItem = new TreeItem(group);
			for(int a=0;a<count;a++){
				TreeItem item = tree.getItem(a);
				if(item.getText().equals(group)){
					groupItem = item;
					add = false;
					break;
				}
			}
			if(add){
				tree.addItem(groupItem);
			}
			String name = sink.getSinkInfo().getName();
			TreeItem modeItem = new TreeItem(name);
			ArrayList<String> actions = sink.getController().getActionNames();
			if(actions==null){
				TreeItem childItem = new TreeItem("无操作内容");
				modeItem.addItem(childItem);
				tree.addItem(modeItem);
				continue;
			}
			for(int a=0;a<actions.size();a++){
				String action = actions.get(a);
				CheckBox box = new CheckBox(action);
				box.setValue(getRoleOperation(action));
				modeItem.addItem(box);
			}
			groupItem.addItem(modeItem);
		}
		Grid grid = new Grid(2, 2);
		grid.setStyleName("table");
		grid.setWidth("380px");
		
		grid.setText(0, 0, "选择操作：");
		grid.setWidget(1, 1, tree);
		grid.getColumnFormatter().setWidth(0, "80px");
		grid.getColumnFormatter().setWidth(1, "300px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		mainPanel.add(grid);
	}
	
	private Boolean getRoleOperation(String action) {
		for(int i=0;i<childData.length;i++){
			String name = childData[i][3];
			if(name.equals(action)){
				return true;
			}
		}
		return false;
	}

	public void show(){
		submit.setText("添加");
		super.show();
	}
	
	public RoleOperation[] getRoleOperation(){
		ArrayList<RoleOperation> list = new ArrayList<RoleOperation>();
		RoleOperation[] result = null;
		int groupCount = tree.getItemCount();
		for(int i=0;i<groupCount;i++){
			TreeItem groupItem = tree.getItem(i);
			int modeCount = groupItem.getChildCount();
			for(int a=0;a<modeCount;a++){
				TreeItem modeItem = groupItem.getChild(a);
				int actionCount = modeItem.getChildCount();
				for(int b=0;b<actionCount;b++){
					TreeItem actionItem = modeItem.getChild(b);
					Widget w = actionItem.getWidget();
					if(w!=null){
						CheckBox box = (CheckBox)w;
						if(box.getValue()){
							if(getRoleOperation(box.getText())){
								continue;
							}
							RoleOperation ro = new RoleOperation();
							ro.setOperationMode(modeItem.getText());
							ro.setOperationAction(box.getText());
							int roleId = Integer.parseInt(id);
							ro.setRoleId(roleId);
							list.add(ro);
						}
					}
				}
			}
		}
		if(list.size()>0){
			result = new RoleOperation[list.size()];
			for(int i=0;i<result.length;i++){
				result[i] = list.get(i);
			}
		} else {
			result = new RoleOperation[0];
		}
		return result;
	}
}
