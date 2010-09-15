package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.EGoodsSink;
import com.risetek.operation.platform.base.client.control.EGoodsController;
import com.risetek.operation.platform.base.client.model.EGoodsData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class EGoodsView extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询电子货物",new EGoodsController.TableShowAction());
	public final static Button addButton = new Button("添加电子货物",new EGoodsController.TableShowAction());

	public final static String[] columns = {EGoodConstanst.E_GOODS_ID_ZH,EGoodConstanst.E_GOODS_SN_ZH,EGoodConstanst.TRANS_ID_ZH,EGoodConstanst.CUSTOMER_ID_ZH
											,EGoodConstanst.DESCRIPTION_ZH,EGoodConstanst.INFO_ZH,EGoodConstanst.CREATE_TIME_ZH,EGoodConstanst.BOLISH_TIME_ZH
											,EGoodConstanst.USED_TIME_ZH,EGoodConstanst.STATUS_ZH,EGoodConstanst.THIRD_STATUS_ZH,EGoodConstanst.ADDITION_ZH,
											EGoodConstanst.VALIDITY_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public EGoodsView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, EGoodsSink.Desc,EGoodsSink.Name);
//		setCellHeight(action, "38px");
		setLocation(EGoodsSink.Group + " -> " + EGoodsSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new EGoodsController.TableEditAction());

	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		EGoodsController.load();
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
//		grid.resize(2, 2);
//		grid.setText(0, 0, "00");
//		grid.setText(0, 1, "01");
//		grid.setText(1, 0, "10");
//		grid.setText(1, 1, "11");
		return grid;
	}

	public void render(EGoodsData data){
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public void render(OPlatformData data) {
		// TODO Auto-generated method stub
		
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
