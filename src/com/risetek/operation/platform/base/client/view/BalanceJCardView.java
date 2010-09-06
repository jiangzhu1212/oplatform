package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.JCardQuerySink;
import com.risetek.operation.platform.base.client.control.JCardQueryContorller;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.JCardConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class BalanceJCardView  extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询骏卡",new JCardQueryContorller.TableShowAction());
	public final static Button addButton = new Button("添加骏卡",new JCardQueryContorller.TableShowAction());

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
	public static String descript = "";
	String banner_tips = "";
	private final static String[] banner_text = {
		"",
		"",
		"",
		"",
		"" ,
		"点击修改" + columns[5],
		"",
		"",
		"",
	};
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public BalanceJCardView(){
		Widget action = initPromptGrid();
		addActionPanel(action, descript);
//		setCellHeight(action, "38px");
		setLocation(JCardQuerySink.Group + " -> " + JCardQuerySink.Name);
		setStatisticText(100);
		grid.addClickHandler(new JCardQueryContorller.TableEditAction());

	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		JCardQueryContorller.load();
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
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

}
