package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.EGoodsSink;
import com.risetek.operation.platform.base.client.control.EGoodsController;
import com.risetek.operation.platform.base.client.model.EGoodsData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.EGoodsConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class EGoodsView extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询电子货物",new EGoodsController.TableShowAction());
	public final static Button addButton = new Button("添加电子货物",new EGoodsController.TableShowAction());

	public final static String[] columns = {EGoodsConstanst.E_GOODS_ID_ZH,EGoodsConstanst.E_GOODS_SN_ZH,EGoodsConstanst.TRANS_ID_ZH,EGoodsConstanst.CUSTOMER_ID_ZH
											,EGoodsConstanst.DESCRIPTION_ZH,EGoodsConstanst.INFO_ZH,EGoodsConstanst.CREATE_TIME_ZH,EGoodsConstanst.BOLISH_TIME_ZH
											,EGoodsConstanst.USED_TIME_ZH,EGoodsConstanst.STATUS_ZH,EGoodsConstanst.THIRD_STATUS_ZH,EGoodsConstanst.ADDITION_ZH,
											EGoodsConstanst.VALIDITY_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public EGoodsView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, "",EGoodsSink.Name);
//		setCellHeight(action, "38px");
		setLocation(EGoodsSink.Group + " -> " + EGoodsSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new EGoodsController.TableEditAction());

	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		for (int i = 1; i < columns.length; i++) {
			actionPanel.add(new Button(Constanst.EDIT_ZH+columns[i],new EGoodsController.TableEditAction()));
		}
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
		return page;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		EGoodsController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		EGoodsController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		EGoodsController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		EGoodsController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
