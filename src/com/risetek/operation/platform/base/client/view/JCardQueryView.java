package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.JCardQuerySink;
import com.risetek.operation.platform.base.client.control.JCardQueryContorller;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class JCardQueryView  extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询骏卡",new JCardQueryContorller.TableShowAction());
	public final static Button addButton = new Button("添加骏卡",new JCardQueryContorller.TableShowAction());
	public final static Button balanceButton = new Button("骏卡对账",new JCardQueryContorller.TableShowAction());
	public final static String[] columns = {
		JCardConstanst.JCARDID_ZH, 
		JCardConstanst.BILL_EXTEND_ID_ZH,
		JCardConstanst.SN_ZH,
		JCardConstanst.NUMBER_ZH,
		JCardConstanst.PWD_ZH,
		JCardConstanst.STATUS_ZH,
		JCardConstanst.PAR_VALUE_ZH,
		JCardConstanst.CREATE_DATE_ZH,
		JCardConstanst.STATUS_TIMEOUT_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25};
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public JCardQueryView(){
		HorizontalPanel action = initPromptGrid();
//		setCellHeight(action, "38px");
		setLocation(JCardQuerySink.Group + " -> " + JCardQuerySink.Name);
		addActionPanel(action, JCardQuerySink.Desc, JCardQuerySink.Name);
		setStatisticText(100);
	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		actionPanel.add(balanceButton);
		Button queryButton = new Button(Constanst.EDIT_ZH+JCardConstanst.STATUS_ZH,new JCardQueryContorller.TableEditAction());
		actionPanel.add(queryButton);
		
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		JCardQueryContorller.INSTANCE.load(1);
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

	public void render(OPlatformData data){
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public HorizontalPanel getPagePanel() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		JCardQueryContorller.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		JCardQueryContorller.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		JCardQueryContorller.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		JCardQueryContorller.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
