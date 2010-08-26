package com.risetek.operation.platform.base.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.CustomerSink;
import com.risetek.operation.platform.base.client.control.CustomerController;
import com.risetek.operation.platform.base.client.entry.CustomerConstanst;
import com.risetek.operation.platform.base.client.model.CustomerData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.util.Util;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class CustomerView extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("查询客户");
	private final Button action2 = new Button("绑定客户");
	public final static String[] columns = {CustomerConstanst.CUSTOMER_ID_ZH, CustomerConstanst.NAME_ZH, CustomerConstanst.PHONE_ZH, CustomerConstanst.ADDRESS_ZH,CustomerConstanst.ADDRESS_2_ZH,CustomerConstanst.EMAIL_ZH,CustomerConstanst.CARD_ID_ZH,CustomerConstanst.CREATE_TIME_ZH,CustomerConstanst.VALIDITY_ZH,CustomerConstanst.ADDITION_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除客户.",
		"点击修改客户名.",
		"点击修改电话号码.",
		"点击修改地址1.",
		"点击修改地址2.",
		"点击修改email.",
		"点击修改银行卡.",
		"点击修改创建日期.",
		"点击修改vslidity.",
		"点击修改描述."
	};
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public CustomerView(){
		Widget action = initPromptGrid();
		add(action, DockPanel.SOUTH);
		setCellHeight(action, "38px");
		setLocation(CustomerSink.Group + " -> " + CustomerSink.Name);
		setStatisticText(100);
		setInfo("this is info");
		grid.addClickHandler(new CustomerController.TableEditAction());
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		actionPanel.setHeight("35px");
		actionPanel.setWidth("100%");
		actionPanel.setBorderWidth(1);
		HorizontalPanel buttonPanel = new HorizontalPanel();		
		buttonPanel.add(action1);
		buttonPanel.add(action2);
		
		actionPanel.add(buttonPanel);
		
		action1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		action2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				int row = Util.getCheckedRow(grid);
				if(row != 0){
					Window.alert(""+row);
				}
			}
		});
		
		return actionPanel;
	}
	
	public void onLoad(){
		CustomerController.load();
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] parseRow(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid(banner_text);
		}
		formatGrid(grid, rowCount, columns,columnsWidth);
//		grid.resize(2, 2);
//		grid.setText(0, 0, "00");
//		grid.setText(0, 1, "01");
//		grid.setText(1, 0, "10");
//		grid.setText(1, 1, "11");
		return grid;
	}

	public void render(CustomerData data){
		for(int index=0;index<rowCount;index++){
			renderLine(data, index);
		}
		renderStatistic(data);
	}
}
