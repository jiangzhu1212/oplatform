package com.risetek.operation.platform.start.client;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

public class StartUp extends OplatformLaunch {

	@Override
	public Tree registerTreeMenu(Tree userMenu) {
		userMenu = addTreeItem(userMenu, BaseSink.init());
		return userMenu;
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
