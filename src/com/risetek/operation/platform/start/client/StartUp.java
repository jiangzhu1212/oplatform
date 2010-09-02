package com.risetek.operation.platform.start.client;


import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

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
		SinkInfo info = null;
		for(int i=0;i<SinkList.getSinkList().size();i++){
			Sink sink = SinkList.getSinkList().get(i);
			info = sink.getSinkInfo();
			TreeItem item = searchGroupItem(userMenu, info.getGroup());
			if(item!=null){
				TreeItem child = creatChildTreeItem(info);
				item.addItem(child);
			} else {
				TreeItem group = creatGrpupTreeItem(info.getGroup(), info);
				userMenu.addItem(group);
			}
		}
//		TreeItem groupItem = searchGroupItem()
//		userMenu.addItem(addTreeItem(userMenu, BaseSink.init()));
//		userMenu.addItem(addTreeItem(userMenu, ProcessSink.init()));
		sinkList = SinkList.getSinkList();
		return userMenu;
	}
	
	private TreeItem searchGroupItem(Tree userMenu, String name){
		int count = userMenu.getItemCount();
		if(count>0){
			for(int i=0;i<count;i++){
				TreeItem group = userMenu.getItem(i);
				if(group.getText().equals(name)){
					return group;
				}
			}
		} else {
			return null;
		}
		return null;
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
		childItem.setUserObject(info);
		return childItem;
	}
}
