package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class RoleConfigView extends OPlatformTableView implements IOPlatformView {

	public final static String[] columns = {"ID", "角色名称", "可操作内容"};
	public final static int[] columnsWidth = {10, 30, 60};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public static String descript = "";
	
	private final static String[] banner_text = {
		"点击修改列1.",
		"点击修改列2.",
		"点击修改列3.",
		"点击修改列4.",
		
	};
	
	public RoleConfigView(){
		
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
