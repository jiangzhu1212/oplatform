package com.risetek.operation.platform.base.client.dialog;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.entry.RoleOperation;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.sink.Sink;

public class AddRoleOperationDialog extends CustomDialog {

	private Tree tree;
	private String id;
	
	public AddRoleOperationDialog(String id, String roleName){
		this.id = id;
		setText("向角色\"" + roleName + "\"添加模块和操作");
		setDescript("向指定的角色添加实际操作内容");
		ArrayList<Sink> list = OplatformLaunch.sinkList;
		tree = new Tree();
		for(int i=0;i<list.size();i++){
			Sink sink = list.get(i);
			String name = sink.getSinkInfo().getName();
			TreeItem item = new TreeItem(name);
			ArrayList<String> actions = sink.getController().getActionNames();
			if(actions==null){
				TreeItem childItem = new TreeItem("无操作内容");
				item.addItem(childItem);
				tree.addItem(item);
				continue;
			}
			for(int a=0;a<actions.size();a++){
				String action = actions.get(a);
				CheckBox box = new CheckBox(action);
				item.addItem(box);
			}
			tree.addItem(item);
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
	
	public void show(){
		submit.setText("添加");
		super.show();
	}
	
	public RoleOperation[] getRoleOperation(){
		ArrayList<RoleOperation> list = new ArrayList<RoleOperation>();
		RoleOperation[] result = null;
		int l1 = tree.getItemCount();
		for(int i=0;i<l1;i++){
			TreeItem item = tree.getItem(i);
			int l2 = item.getChildCount();
			for(int a=0;a<l2;a++){
				TreeItem childItem = item.getChild(a);
				Widget w = childItem.getWidget();
				if(w!=null){
					CheckBox box = (CheckBox)w;
					if(box.getValue()){
						RoleOperation ro = new RoleOperation();
						ro.setOperationMode(item.getText());
						ro.setOperationAction(box.getText());
						int roleId = Integer.parseInt(id);
						ro.setRoleId(roleId);
						list.add(ro);
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
