package com.risetek.operation.platform.launch.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.dialog.NoticeDialog;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.launch.client.sink.SinkInfo;

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
	
	private HTML noticeHtml = new HTML();
	final VerticalPanel body = new VerticalPanel();
	Tree userMenu = new Tree();
	private SinkInfo curInfo;
	public static ArrayList<Sink> sinkList = null;
	
	public void onModuleLoad() {
		displayNowTime();
		Widget mainPanel = initMainPanel();
		RootPanel.get("main").add(mainPanel);
		Grid userInfoGrid = new Grid(1, 8);
		userInfoGrid.setText(0, 0, "登录用户:");
		userInfoGrid.setText(0, 1, "测试");
		userInfoGrid.setText(0, 2, "权限:");
		userInfoGrid.setText(0, 3, "值班技术");
		userInfoGrid.setText(0, 4, "上次登录时间:");
		userInfoGrid.setText(0, 5, "2010-08-24 22:48:30");
		userInfoGrid.setText(0, 6, "上次登录地址:");
		userInfoGrid.setText(0, 7, "125.69.69.135");
		for(int i=0;i<userInfoGrid.getColumnCount();i++){
			if(i%2==0){
				userInfoGrid.getCellFormatter().setStyleName(0, i, "userinfo-name");
			} else {
				userInfoGrid.getCellFormatter().setStyleName(0, i, "userinfo-content");
			}
		}
		RootPanel.get("userinfo").add(userInfoGrid);
		VerticalPanel pubAction = new VerticalPanel();
		Button repws = new Button("更改密码");
		repws.setWidth("90px");
		Button logout = new Button("注销登录");
		logout.setWidth("90px");
		HTML blank = new HTML();
		blank.setStyleName("blank5");
		pubAction.add(repws);
		pubAction.add(blank);
		pubAction.add(logout);
		RootPanel.get("public").add(pubAction);
		
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
	
	public void onHistoryChanged(String token){
		SinkInfo info = findSinkInfo(token);
		if(info == null){
			showInfo();
			return;
		}
		show(info, false);
	}
	
	private SinkInfo findSinkInfo(String token){
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
	
	private void showInfo(){
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
	
	/**
	 * 功能：初始化主框架视图部分
	 *
	 * Widget
	 * @return
	 */
	private Widget initMainPanel(){
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
//					if(obj instanceof Widget){
//						body.clear();
//						Widget w = (Widget)obj;
//						body.add(w);
//					}
				}
			}
		});
		
		Widget menuList = creatStackPanel("功能操作", userMenu);
		menuList.setWidth("180px");
		menuList.setHeight("100%");
		
		Grid ng = new Grid(1, 1);
		noticeHtml.setText("1目前没有通知公告2目前没有通知公告3目前没有通知公告4目前没有通知公告5目前没有通知公告6目前没有通知公告7目前没有通知公告8目前没有通知公告9目前没有通知公告10目前没有通知公告");
		noticeHtml.setStyleName("notice");
		noticeHtml.setTitle("点击查看详情");
		noticeHtml.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NoticeDialog nd = new NoticeDialog(noticeHtml.getText());
				nd.show();
			}
		});
		ng.setWidget(0, 0, noticeHtml);
		
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
	private void displayNowTime(){
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
