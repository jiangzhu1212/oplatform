package com.risetek.operation.platform.start.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;

public class StartUp extends OplatformLaunch {

	
	
	@Override
	public Tree registerTreeMenu(Tree userMenu) {
		Tree tree = new Tree();
		TreeItem root = new TreeItem("root");
		
		SinkInfo info = BaseSink.init();
		int count = root.getChildCount();
		if(count>0){
			boolean exite = false;
			for(int i=0;i<count;i++){
				TreeItem groupItem = root.getChild(i);
				if(groupItem.getText().equals(info.getGroup())){
					exite = true;
					TreeItem viewItem = createViewTreeItem(info);
					groupItem.addItem(viewItem);
					break;
				}
			}
			if(!exite){
				TreeItem groupItem = new TreeItem(info.getGroup());
				TreeItem viewItem = createViewTreeItem(info);
				groupItem.addItem(viewItem);
				root.addItem(groupItem);
			}
		} else {
			TreeItem groupItem = new TreeItem(info.getGroup());
			TreeItem viewItem = createViewTreeItem(info);
			groupItem.addItem(viewItem);
			root.addItem(groupItem);
		}
		tree.addItem(root);
		
		userMenu = addTreeItem(userMenu, BaseSink.init());
		return userMenu;
	}
	
	private TreeItem createViewTreeItem(SinkInfo info){
		TreeItem viewItem = new TreeItem(info.getName());
		IOPlatformView view = BaseSink.getView();
		ArrayList<ClickActionHandler> list = view.getActionList();
		for(int i=0;i<list.size();i++){
			String name = list.get(i).getActionName();
			TreeItem child = new TreeItem(name);
			viewItem.addItem(child);
		}
		return viewItem;
	}
	
	private Tree addTreeItem(Tree tree, SinkInfo info){
		String group = info.getGroup();
		int count = tree.getItemCount();
		boolean added = false;
		if(count>0){
			for(int i=0;i<count;i++){
				TreeItem item = tree.getItem(i);
				if(group.equals(item.getText())){
					TreeItem childItem = creatChildTreeItem(info);
					item.addItem(childItem);
					added = true;
					break;
				}
			}
			if(!added){
				TreeItem item = creatGrpupTreeItem(group, info);
				tree.addItem(item);
			}
		} else {
			TreeItem item = creatGrpupTreeItem(group, info);
			tree.addItem(item);
		}
		return tree;
	}
	
	private TreeItem creatGrpupTreeItem(String group, SinkInfo info){
		TreeItem item = new TreeItem(group);
		TreeItem childItem = creatChildTreeItem(info);
		item.addItem(childItem);
		return item;
	}
	
	private TreeItem creatChildTreeItem(SinkInfo info){
		TreeItem childItem = new TreeItem();
		childItem.setText(info.getName());
		childItem.setTitle(info.getDescription());
		childItem.setUserObject(info.getWidget());
		return childItem;
	}
}
