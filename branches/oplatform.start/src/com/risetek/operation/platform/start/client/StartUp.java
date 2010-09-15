package com.risetek.operation.platform.start.client;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.dialog.LoadingDialog;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.start.client.service.LoginService;
import com.risetek.operation.platform.start.client.service.LoginServiceAsync;

/**
 * @author Amber
 * 功能：整个项目的入口
 * 2010-8-23 下午11:56:43
 */
public class StartUp extends OplatformLaunch {
	
	LoginDialog dialog = new LoginDialog();
	private final static LoginServiceAsync ls = GWT.create(LoginService.class);
	
	/** 
	 * 功能： 菜单注册
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.OplatformLaunch#registerTreeMenu(com.google.gwt.user.client.ui.Tree)
	 */
	@Override
	public Tree registerTreeMenu(Tree userMenu) {
		SinkInfo info = null;
		ArrayList<String> ro = loginUser.getRoleOperation();
		for(int i=0;i<SinkList.getSinkList().size();i++){
			Sink sink = SinkList.getSinkList().get(i);
			info = sink.getSinkInfo();
			String an = info.getName();
//			boolean add = false;
//			for(int a=0;a<ro.size();a++){
//				String[] op = ro.get(a).split(",");
//				if(an.equals(op[0])){
//					add = true;
//					break;
//				}
//			}
//			if(!add){
//				continue;
//			}
			TreeItem item = searchGroupItem(userMenu, info.getGroup());
			if(item!=null){
				TreeItem child = creatChildTreeItem(info);
				item.addItem(child);
			} else {
				TreeItem group = creatGrpupTreeItem(info.getGroup(), info);
				userMenu.addItem(group);
			}
		}
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
	
	@Override
	public void loginAction() {
		ls.getLoginUser(new AsyncCallback<User>() {
			public void onSuccess(User result) {
				if(result==null){
					dialog.show();
				} else {
					loginUser = result;
					initWieget();
					return;
				}
			}
			public void onFailure(Throwable caught) {}
		});
		dialog.submit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					dialog.hide();
					final LoadingDialog ld = new LoadingDialog("登录中");
					ld.run();
					final User user = dialog.getValue();
					ls.getUserInfo(user, new AsyncCallback<User>() {
						public void onSuccess(User result) {
							boolean loginStatus = false;
							if(result.getUserName()==null){
								ld.cancel();
								Window.alert("用户不存在！");
								return;
							} else if (!result.getUserPassword().equals(user.getUserPassword())){
								ld.cancel();
								Window.alert("用户密码错误！");
								return;
							} else if (result.getStatus()<-1){
								ld.cancel();
								Window.alert("用户状态不正常！\n请联系管理员。");
								return;
							} else if(result.getStatus()>0){
								ld.cancel();
								Window.alert("该账户已在别处登录，别处登录会立刻强迫下线。\n如不是已知登录请联系管理员。");
								loginStatus = true;
							} else {
								loginStatus = true;
							}
							if(loginStatus){
								loginUser = result;
								ls.setLoginUser(result, new AsyncCallback<Void>() {
									public void onSuccess(Void result) {}
									public void onFailure(Throwable caught) {}
								});
								ls.refreshUserInfo(result, new AsyncCallback<Void>() {
									public void onSuccess(Void result) {
										ld.cancel();
										initWieget();
									}
									public void onFailure(Throwable caught) {}
								});
							}
						}
						public void onFailure(Throwable caught) {}
					});
				} else {
					dialog.submit.setEnabled(false);
				}
			}
		});
	}
	
	private void initWieget(){
		displayNowTime();
		Widget titlePanel = initTitlePanel();
		RootPanel.get("title").add(titlePanel);
		Widget mainPanel = initMainPanel();
		RootPanel.get("main").add(mainPanel);
		Widget userInfoPanel = initUserInfoPanel();
		RootPanel.get("userinfo").add(userInfoPanel);
		Widget copyRightPanel = initCopyRightPanel();
		RootPanel.get("copyright").add(copyRightPanel);
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				SinkInfo info = findSinkInfo(token);
				if(info == null){
					showInfo();
					return;
				}
				show(info, false);
			}
		});
		
		String initToken = History.getToken();
		if(initToken.length()>0){
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
	}
}
