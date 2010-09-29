package com.risetek.operation.platform.launch.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.client.entry.User;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @author Amber
 * 功能：项目主框架类
 * 2010-8-23 下午11:21:54
 */
/**
 * @author Amber
 *
 */
/**
 * @author Amber
 *
 */
public abstract class OplatformLaunch implements EntryPoint {
	/** 
	 * 功能： 项目起始点
	 *(non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	
	public HTML noticeHtml = new HTML();
	public Grid ng = new Grid(1, 1);
	final VerticalPanel body = new VerticalPanel();
	Tree userMenu = new Tree();
	private SinkInfo curInfo;
	public static ArrayList<Sink> sinkList = null;
	public static User loginUser = new User();
	public static Notice[] notices;
	protected Button changUserInfo = new Button("更改个人信息");
	protected Button logout = new Button("注销登录");
	protected Button repws = new Button();
	
	public abstract void loginAction();
	
	public void onModuleLoad() {
		loginAction();		
	}
	
	public void onHistoryChanged(String token){
		SinkInfo info = findSinkInfo(token);
		if(info == null){
			showInfo();
			return;
		}
		show(info, false);
	}
	
	public SinkInfo findSinkInfo(String token){
		int count = userMenu.getItemCount();
		if(count>0){
			for(int i=0;i<count;i++){
				TreeItem child = userMenu.getItem(i);
				int cc = child.getChildCount();
				if(cc>0){
					for(int a=0;a<cc;a++){
						TreeItem cchild = child.getChild(a);
						Object object = cchild.getUserObject();
						SinkInfo info = (SinkInfo)object;
						if(info.getTag().equals(token)){
							TreeItem parent = cchild.getParentItem();
							if(!parent.getState()){
								parent.setState(true);
							}
							cchild.setSelected(true);
							return info;
						}
					}
				}
			}
		}
		return null;
	}
	
	public void showInfo(){
		selectDefaultWidget();
	}
	
	/**
	 * 功能：功能模块注册方法
	 *
	 * Tree
	 * @param userMenu
	 * @return
	 */
	public abstract Tree registerTreeMenu(Tree userMenu);
	
	protected Widget initTitlePanel(){
		HorizontalPanel titlePanel = new HorizontalPanel();
		Image img = new Image("logo.png");
		img.setSize("240px", "47px");
		titlePanel.add(img);
		HTML title = new HTML("中联信通&nbsp;&nbsp;&nbsp;手机支付&nbsp;&nbsp;&nbsp;运维平台");
		titlePanel.add(title);
		title.setStyleName("h1");
		VerticalPanel pubAction = new VerticalPanel();
		changUserInfo.setWidth("90px");
		logout.setWidth("90px");
		HTML blank = new HTML();
		blank.setStyleName("blank5");
		pubAction.add(changUserInfo);
		pubAction.add(blank);
		pubAction.add(logout);
		pubAction.setWidth("100px");
		titlePanel.add(pubAction);
		titlePanel.setCellWidth(title, "100%");
		titlePanel.setWidth("100%");
		return titlePanel;
	}
	
	protected Widget initUserInfoPanel(){
		HorizontalPanel userInfoPanel = new HorizontalPanel();
		Grid userInfoGrid = new Grid(1, 8);
		userInfoGrid.setText(0, 0, "登录用户:");
		userInfoGrid.setText(0, 1, loginUser.getUserName());
		userInfoGrid.setText(0, 2, "角色:");
		userInfoGrid.setText(0, 3, loginUser.getRoleName());
		userInfoGrid.setText(0, 4, "上次登录时间:");
		userInfoGrid.setText(0, 5, Util.formatTimestampToString(loginUser.getLastLoginTime()));
		userInfoGrid.setText(0, 6, "上次登录地址:");
		userInfoGrid.setText(0, 7, loginUser.getLastLoginAddress());
		for(int i=0;i<userInfoGrid.getColumnCount();i++){
			if(i%2==0){
				userInfoGrid.getCellFormatter().setStyleName(0, i, "userinfo-name");
			} else {
				userInfoGrid.getCellFormatter().setStyleName(0, i, "userinfo-content");
			}
		}
		userInfoPanel.setHeight("20px");
		userInfoPanel.add(userInfoGrid);
		userInfoPanel.setWidth("100%");
		userInfoPanel.setCellHorizontalAlignment(userInfoGrid, HasHorizontalAlignment.ALIGN_RIGHT);
		userInfoPanel.setStyleName("userinfo");
		return userInfoPanel;
	}
	
	protected Widget initCopyRightPanel(){
		Grid grid = new Grid(1, 1);
		grid.setStyleName("contact-box");
		HTML copyRight = new HTML("<font size=\"4\">&copy;</font>&nbsp;2009-2010 成都中联信通科技有限公司");
		copyRight.setStyleName("contact");
		grid.setWidget(0, 0, copyRight);
		grid.setSize("100%", "20px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		return grid;
	}
	
	/**
	 * 功能：初始化主框架视图部分
	 *
	 * Widget
	 * @return
	 */
	protected Widget initMainPanel(){
		HorizontalPanel main = new HorizontalPanel();
		main.setWidth("100%");
		main.setHeight("100%");
		
		VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.setWidth("180px");
		leftPanel.setHeight("100%");
		
		body.setStyleName("body");
		body.setWidth("100%");
		body.setHeight("100%");
		
		userMenu = registerTreeMenu(userMenu);
		userMenu.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				if(item.getChildCount()<1){
					Object obj = item.getUserObject();
					SinkInfo info = (SinkInfo)obj;
					show(info, true);
					onHistoryChanged(info.getTag());
				}
			}
		});
		
		Widget menuList = creatStackPanel("功能操作", userMenu);
		menuList.setWidth("180px");
		menuList.setHeight("100%");
		
		HorizontalPanel blank = new HorizontalPanel();
		blank.setStyleName("blank5");
		
		Widget notice = creatStackPanel("内部通知公告", ng);
		notice.setWidth("180px");
		
		leftPanel.add(notice);
		leftPanel.add(blank);
		leftPanel.add(menuList);
		leftPanel.setCellHeight(notice, "10px;");
		leftPanel.setCellHeight(blank, "5px;");
		
		main.add(leftPanel);
		main.add(body);
		main.setCellWidth(leftPanel, "185px");
		return main;
	}
	
	/**
	 * 加载页面时，默认显示第一个节点的视图
	 * @param userMenu
	 * @param body
	 */
	private void selectDefaultWidget() {
		TreeItem item = userMenu.getItem(0);
		if(item==null){
			return;
		}
		item.setState(true);
		if(item.getChildCount()>0){
			item = item.getChild(0);
			if(item.getChildCount()>0){
				item = item.getChild(0);
			}
		}
		item.getUserObject();
		body.clear();
		Object obj = item.getUserObject();
		SinkInfo info = (SinkInfo)obj;
		show(info, true);
	}

	/**
	 * 功能：主页面右上角实时显示时间的方法
	 *
	 * void
	 */
	protected void displayNowTime(){
		final Label nowTime = new Label();
		Timer timer = new Timer() {
			public void run() {
				Date now = new Date();
				String time = DateTimeFormat.getFormat("当前系统时间是： yyyy年 MM月 dd日 E HH时 mm分 ss秒").format(now);
				nowTime.setText(time);
			}
		};
		timer.scheduleRepeating(1000);
		timer.run();
		RootPanel.get("nowTime").add(nowTime);
	}
	
	private Widget creatStackPanel(String title, Widget w){
		VerticalPanel stack = new VerticalPanel();
		stack.setStyleName("stackPanel");
		Grid grid = new Grid(1, 1);
		HTML tit = new HTML(title);
		tit.setStyleName("stackPanelItem");
		grid.setWidget(0, 0, tit);
		grid.setStyleName("stackPanelItem-border");
		grid.setWidth("100%");
		w.setStyleName("stackPanelContent");
		stack.add(grid);
		stack.add(w);
		stack.setCellHeight(grid, "30px");
		stack.setCellHeight(w, "100%");
		w.setWidth("178px");
		stack.setWidth("100%");
		return stack;
	}
	
	public void show(SinkInfo info, boolean affectHistory){
		if(info == curInfo){
			return;
		}
		curInfo = info;
		body.clear();
		Widget w = info.getInstance().getWidget();
		body.add(w);
		if(affectHistory){
			History.newItem(info.getTag());
		}
	}
}
