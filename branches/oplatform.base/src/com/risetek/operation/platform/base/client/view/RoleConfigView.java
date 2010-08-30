package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.base.client.control.RoleConfigController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class RoleConfigView extends OPlatformTableView implements IOPlatformView {

	public final static String[] columns = {"ID", "角色名称"};
	public final static int[] columnsWidth = {5, 95};
	public final static int mainRowCount = UIConfig.MAIN_TABLE_ROW_NORMAL;
	public final static int childRowCount = UIConfig.CHILD_TABLE_ROW_NORMAL;
	public final static String[] childColumns = {"ID", "角色名称", "操作模块", "操作名称"};
	public final static int[] childColumnsWidth = {5, 20, 30, 45};
	public static String descript = "";
	public Grid childGrid = getChildGrid();
	public Grid childTableTitle = new Grid(1, 3);
	private HTML childTitle = new HTML();
	
	public Button addRole = new Button("添加角色");
	
	private final static String[] banner_text = {
		"点击查看用户角色内容",
		"点击修改用户角色名称"
	};
	
	private final static String[] childBanner_text = {
		"点击选中本条记录",
		"点击选中本条记录",
		"点击选中本条记录",
		"点击选中本条记录"
	};
	
	public RoleConfigView(){
		grid.setHeight("40%");
		addActionPanel(initPromptGrid(), RoleConfigSink.Desc);
		setLocation(RoleConfigSink.Group + "->" + RoleConfigSink.Name);
		childGrid.setStyleName("optable");
		
		childTableTitle.setWidth("100%");
		setChildGridTitle("未选择角色");
		childTitle.setStyleName("childtabletitle");
		childTableTitle.setWidget(0, 0, childTitle);
		HorizontalPanel childTableActionPanel = new HorizontalPanel();
		childTableActionPanel.setStyleName("childtableAction");
		Button aa = new Button();
		aa.setText("添加模块和操作");
		Button ac = new Button();
		ac.setText("删除多项操作");
		childTableActionPanel.add(aa);
		childTableActionPanel.add(ac);
		childTableTitle.setWidget(0, 2, childTableActionPanel);
		childTableTitle.getColumnFormatter().setWidth(1, "50%");
		childTableTitle.getCellFormatter().setStyleName(0, 1, "childtabledescript");
		childTableTitle.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		childTableTitle.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		outer.add(childTableTitle);
		outer.add(childGrid);
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addRole);
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
	protected void onLoad() {
		RoleConfigController.load();
	}

	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid(banner_text);
		}
		formatGrid(grid, mainRowCount, columns, columnsWidth);
		grid.addClickHandler(new RoleConfigController.TableAction());
		return grid;
	}
	
	public Grid getChildGrid() {
		if(childGrid == null){
			childGrid = new GreenMouseEventGrid(childBanner_text, true);
		}
		formatGrid(childGrid, childRowCount, childColumns, childColumnsWidth);
		return childGrid;
	}

	@Override
	public void render(OPlatformData data) {
		if(data.getSum()<mainRowCount){
			grid.resizeRows(mainRowCount+1);
		} else {
			grid.resizeRows(data.getSum()+1);
		}
		for(int index=0;index<mainRowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}
	
	public void renderChild(OPlatformData data) {
		childGrid.resizeRows(childRowCount+1);
		for(int index=0;index<childRowCount;index++){
			renderLine(childGrid, data, index);
		}
	}
	
	public void setChildGridTitle(String childGridTitle){
		String title = "角色\"" + childGridTitle + "\"详细操作内容";
		childTitle.setText(title);
	}
}
