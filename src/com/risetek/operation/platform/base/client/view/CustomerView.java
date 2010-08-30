package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.CustomerSink;
import com.risetek.operation.platform.base.client.control.CustomerController;
import com.risetek.operation.platform.base.client.entry.CustomerConstanst;
import com.risetek.operation.platform.base.client.model.CustomerData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class CustomerView extends OPlatformTableView implements IOPlatformView {

	public final static Button queryCustomer = new Button("查询客户",new CustomerController.TableShowAction());
	public final static Button bindCustomer = new Button("绑定客户",new CustomerController.TableShowAction());
	public final static Button addCustomer = new Button("添加客户",new CustomerController.TableShowAction());
	public final static String[] columns = {CustomerConstanst.CUSTOMER_ID_ZH, CustomerConstanst.NAME_ZH, CustomerConstanst.PHONE_ZH, CustomerConstanst.ADDRESS_ZH,CustomerConstanst.ADDRESS_2_ZH,CustomerConstanst.EMAIL_ZH,CustomerConstanst.CARD_ID_ZH,CustomerConstanst.CREATE_TIME_ZH,CustomerConstanst.VALIDITY_ZH,CustomerConstanst.ADDITION_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	public static String descript = "";
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除客户.",
		"点击修改" + columns[1],
		"点击修改" + columns[2],
		"点击修改" + columns[3],
		"点击修改" + columns[4],
		"点击修改" + columns[5],
		"点击修改" + columns[6],
		"点击修改" + columns[7],
		"点击修改" + columns[8],
		"点击修改" + columns[9]
	};
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public CustomerView(){
		Widget action = initPromptGrid();
		addActionPanel(action, descript);
//		setCellHeight(action, "38px");
		setLocation(CustomerSink.Group + " -> " + CustomerSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new CustomerController.TableEditAction());

	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();	
		actionPanel.add(queryCustomer);
		actionPanel.add(bindCustomer);
		actionPanel.add(addCustomer);
		actionPanel.setStyleName("aa");		
		
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

		return grid;
	}

	public void render(CustomerData data){
		for(int index=0;index<rowCount;index++){
			renderLine(data, index);
		}
		renderStatistic(data);
	}
}
