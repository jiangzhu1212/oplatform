package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.TradeSink;
import com.risetek.operation.platform.base.client.control.TradeController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.TradeConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class TradeView extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询交易",new TradeController.TableShowAction());
	public final static Button addButton = new Button("添加交易",new TradeController.TableShowAction());

	public final static String[] columns = {TradeConstanst.TRADE_ID_ZH,TradeConstanst.CUSTOMER_ID_ZH,TradeConstanst.TRADE_ID_ZH,TradeConstanst.AMOUNT_ZH,
											TradeConstanst.STATUS_ZH , TradeConstanst.THIRD_STATUS_ZH ,TradeConstanst.CREATE_TIME_ZH ,TradeConstanst.BOLISH_TIME_ZH,
											TradeConstanst.DESCRIPTION_ZH ,TradeConstanst.VALIDITY_ZH ,TradeConstanst.ADDITION_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public TradeView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, TradeSink.Desc,TradeSink.Name);
//		setCellHeight(action, "38px");
		setLocation(TradeSink.Group + " -> " + TradeSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new TradeController.TableEditAction());

	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		TradeController.load();
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
			grid = new GreenMouseEventGrid();
		}
		formatGrid(grid, rowCount, columns,columnsWidth);

		return grid;
	}

	@Override
	public void render(OPlatformData data) {
		// TODO Auto-generated method stub
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public HorizontalPanel getPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
