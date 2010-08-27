package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class RoleConfigView extends OPlatformTableView implements IOPlatformView {

	public final static String[] columns = {"ID", "角色名称", ""};
	public final static int[] columnsWidth = {10, 30, 60};
	public final static int rowCount = 2;//UIConfig.TABLE_ROW_NORMAL;
	public static String descript = "";
	public Grid grid1 = new Grid();
	
	private final static String[] banner_text = {
		"点击修改列1.",
		"点击修改列2.",
		"点击修改列3.",
		"点击修改列4.",
		
	};
	
	public RoleConfigView(){
		grid.setHeight("40%");
		addActionPanel(initPromptGrid(), RoleConfigSink.Desc);
		setLocation(RoleConfigSink.Group + "->" + RoleConfigSink.Name);
		grid1.setWidth("100%");
		grid1.resize(4, 5);
		grid1.setText(0, 0, "内容");
		grid1.setStyleName("optable");
		HTML title = new HTML("这是上面一张表的扩展内容");
		title.setStyleName("tableordertitle");
		outer.add(title);
		outer.add(grid1);
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		return actionPanel;
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
		formatGrid(grid, rowCount, columns, columnsWidth);
		return grid;
	}

}
