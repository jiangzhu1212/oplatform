package com.risetek.operation.platform.start.client;


import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.process.client.ProcessSink;

/**
 * @author Amber
 * 功能：整个项目的入口
 * 2010-8-23 下午11:56:43
 */
public class StartUp extends OplatformLaunch {
	
	/** 
	 * 功能： 菜单注册
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.OplatformLaunch#registerTreeMenu(com.google.gwt.user.client.ui.Tree)
	 */
	@Override
	public Tree registerTreeMenu(Tree userMenu) {
		userMenu.addItem(addTreeItem(userMenu, BaseSink.init()));
		userMenu.addItem(addTreeItem(userMenu, ProcessSink.init()));
		return userMenu;
	}
	
	/**
	 * 功能：添加节点
	 *
	 * TreeItem
	 * @param tree
	 * @param info
	 * @return
	 */
	private TreeItem addTreeItem(Tree tree, SinkInfo info){
		TreeItem addItem = null;
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
				addItem = creatGrpupTreeItem(group, info);
			}
		} else {
			addItem = creatGrpupTreeItem(group, info);
		}
		return addItem;
	}
	
	/**
	 * 功能：创建分组节点
	 *
	 * TreeItem
	 * @param group
	 * @param info
	 * @return
	 */
	private TreeItem creatGrpupTreeItem(String group, SinkInfo info){
		TreeItem item = new TreeItem(group);
		TreeItem childItem = creatChildTreeItem(info);
		item.addItem(childItem);
		return item;
	}
	
	/**
	 * 功能：创建功能节点
	 *
	 * TreeItem
	 * @param info
	 * @return
	 */
	private TreeItem creatChildTreeItem(SinkInfo info){
		TreeItem childItem = new TreeItem();
		childItem.setText(info.getName());
		childItem.setTitle(info.getDescription());
		childItem.setUserObject(info.getInstance().getWidget());
		return childItem;
	}
}
