package com.risetek.operation.platform.launch.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("deprecation")
public abstract class OplatformLaunch implements EntryPoint {
	
	public void onModuleLoad() {
		displayNowTime();
		Widget mainPanel = initMainPanel();
		RootPanel.get("main").add(mainPanel);
	}
	
	public abstract Tree registerTreeMenu(Tree userMenu);
	
	private Widget initMainPanel(){
		HorizontalPanel main = new HorizontalPanel();
		main.setWidth("100%");
		main.setHeight("100%");
		
		DecoratedStackPanel menuList = new DecoratedStackPanel();
		menuList.setWidth("200px");
		menuList.setHeight("100%");
		
		final VerticalPanel body = new VerticalPanel();
		body.setStyleName("body");
		body.setWidth("100%");
		body.setHeight("100%");
		
		Tree userMenu = new Tree();
		userMenu.setWidth("100%");
		userMenu = registerTreeMenu(userMenu);
//		TreeItem userMenuItem = new TreeItem();
//		userMenuItem.setText("dddd");
//		TreeItem item = new TreeItem();
//		item.setText("tteesstt");
//		item.setTitle("ttteeesssttt");
//		VerticalPanel vp = new VerticalPanel();
//		vp.add(new HTML("haha"));
//		vp.add(new HTML("hehe"));
//		item.setUserObject(vp);
//		userMenuItem.addItem(item);
//		userMenu.addItem(userMenuItem);
		userMenu.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				if(event.getSource() instanceof Tree){
					Tree tree = (Tree) event.getSource();
					TreeItem item = tree.getSelectedItem();
					if(item.getChildCount()>0){
						if(item.getState()){
							item.setState(false);
						} else {
							item.setState(true);
						}
					}
				}
			}
		});
		userMenu.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				if(item.getChildCount()<1){
					Object obj = item.getUserObject();
					if(obj instanceof Widget){
						body.clear();
						body.add((Widget)obj);
					}
				}
			}
		});
		
		
		Tree baseMenu = new Tree();
		
		Hyperlink repws = new Hyperlink("更改密码", "repws");
		repws.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				final DialogBox db = new DialogBox();
				db.center();
				db.show();
				Button clo = new Button("close");
				clo.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						db.hide();
					}
				});
				db.add(clo);
			}
		});
		baseMenu.add(repws);
		
		menuList.add(userMenu, "功能操作");
		menuList.add(baseMenu, "基本操作");
		
		main.add(menuList);
		main.add(body);
		main.setCellWidth(menuList, "203px");
		
		return main;
	}
	
	private void displayNowTime(){
		final Label nowTime = new Label();
		Timer timer = new Timer() {
			public void run() {
				Date now = new Date();
				String time = DateTimeFormat.getFormat("当前系统时间是： yyyy年 MM月 dd日 E HH时 mm分 ss秒").format(now);
				nowTime.setText(time);
			}
		};
//		timer.schedule(1000);
		timer.scheduleRepeating(1000);
		timer.run();
		RootPanel.get("nowTime").add(nowTime);
	}
}
