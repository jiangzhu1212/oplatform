package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.CardBatchSink;
import com.risetek.operation.platform.base.client.control.CardBatchController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.CardBatchConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class CardBatchView extends OPlatformTableView implements IOPlatformView {

	public final static Button addButton = new Button("添加批次",new CardBatchController.TableShowAction());
	public final static Button queryButton = new Button("查询批次",new CardBatchController.TableShowAction());
	public final static String[] columns = {CardBatchConstanst.BATCH_ID_ZH,CardBatchConstanst.CHANNEL_ID_ZH,CardBatchConstanst.DESCRIPTION_ZH,CardBatchConstanst.PRICE_ZH,CardBatchConstanst.INDATE_ZH,CardBatchConstanst.ADDITION_ZH,CardBatchConstanst.PRO_BATCH_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25};
	public static String descript = "";
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除此批次.",
		"点击修改" + columns[1],
		"点击修改" + columns[2],
		"点击修改" + columns[3],
		"点击修改" + columns[4],
		"点击修改" + columns[5],
		"点击修改" + columns[6]
	};
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public CardBatchView(){
		Widget action = initPromptGrid();
		addActionPanel(action, descript);
		setLocation(CardBatchSink.Group + " -> " + CardBatchSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new CardBatchController.TableEditAction());

	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();	
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		CardBatchController.load();
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

	public void render(OPlatformData data){
		for(int index=0;index<rowCount;index++){
			renderLine(grid,data, index);
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
