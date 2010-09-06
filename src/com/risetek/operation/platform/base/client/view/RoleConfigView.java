package com.risetek.operation.platform.base.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.base.client.control.RoleConfigController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

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
	private String childGridTitle = "";
	private String selectRoleId = "";
	private HorizontalPanel childPage = new HorizontalPanel();
	
	public Button addRole = new Button("添加角色", new RoleConfigController.AddRoleAction());
	public Button delManyRole = new Button("删除多个角色", new RoleConfigController.DeleteManyRoleAction(grid));
	public Button addRoleOperation = new Button("添加角色操作", new RoleConfigController.AddRoleOperationAction());
	public Button delManyRoleOperation = new Button("删除多个操作", new RoleConfigController.DeleteManyRoleOperationAction(childGrid));
	public Button showRole = new Button("查看角色操作内容", new RoleConfigController.ShowRoleContentAction(grid));
	
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
		childTableTitle.setHeight("15px");
		String title = "角色\"未选择角色\"详细操作内容";
		childTitle.setText(title);
		childTitle.setStyleName("childtabletitle");
		childTableTitle.setWidget(0, 0, childTitle);
		VerticalPanel childPanel = new VerticalPanel();
		HorizontalPanel childTableActionPanel = new HorizontalPanel();
		childTableActionPanel.setStyleName("childtableAction");
		childTableActionPanel.add(addRoleOperation);
		childTableActionPanel.add(delManyRoleOperation);
		childTableTitle.setWidget(0, 2, childTableActionPanel);
		childTableTitle.getColumnFormatter().setWidth(0, "25%");
		childTableTitle.getColumnFormatter().setWidth(1, "25%");
		childTableTitle.getColumnFormatter().setWidth(2, "50%");
		childTableTitle.getCellFormatter().setStyleName(0, 1, "childtabledescript");
		childTableTitle.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		childTableTitle.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		Widget childPagePanel = createChildPagePanel();
		childPanel.add(childTableTitle);
		childPanel.add(childGrid);
		childPanel.add(childPagePanel);
		childPanel.setCellHorizontalAlignment(childPagePanel, HasHorizontalAlignment.ALIGN_CENTER);
		outer.add(childPanel);
	}
	
	private Widget createChildPagePanel() {
		PageLabel first = new PageLabel("|<");
		PageLabel before = new PageLabel("<<");
		PageLabel page1 = new PageLabel("1");
		page1.addClickHandler(new ClickHandler() {
			String tisp_value;
			String id;
			@Override
			public void onClick(ClickEvent event) {
				tisp_value = getChildGridTitle();
				id = getSelectRoleId();
				RoleConfigController.loadChild(id, tisp_value);
			}
		});
		PageLabel after = new PageLabel(">>");
		PageLabel last = new PageLabel(">|");
		first.setEnable(false);
		before.setEnable(false);
		page1.setEnable(false);
		after.setEnable(false);
		last.setEnable(false);
		childPage.add(first);
		childPage.add(before);
		childPage.add(page1);
		childPage.add(after);
		childPage.add(last);
		return childPage;
	}

	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addRole);
		actionPanel.add(delManyRole);
		actionPanel.add(showRole);
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
		childGrid.addClickHandler(new RoleConfigController.ChildTableAction());
		return childGrid;
	}

	@Override
	public void render(OPlatformData data) {
		if(data.getSum()<mainRowCount){
			grid.resizeRows(mainRowCount+1);
		} else {
			grid.resizeRows(data.getSum()+1);
		}
		clearGrid(grid, mainRowCount);
		clearGrid(childGrid, childRowCount);
		String title = "角色\"未选择\"详细操作内容";
		childTitle.setText(title);
		if(childPage.getWidgetCount()>3){
			PageLabel label = (PageLabel)childPage.getWidget(2);
			label.setEnable(false);
		}
		for(int index=0;index<mainRowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}
	
	private void clearGrid(Grid grid, int mainRowCount) {
		for(int i=1;i<mainRowCount;i++){
			for(int a=0;a<grid.getColumnCount();a++){
				grid.clearCell(i, a);
			}
		}
	}

	public void renderChild(OPlatformData data) {
		clearGrid(childGrid, childRowCount);
		childGrid.resizeRows(childRowCount+1);
		for(int index=0;index<childRowCount;index++){
			renderLine(childGrid, data, index);
		}
	}
	
	public void setChildGridTitle(String childGridTitle){
		String title = "角色\"" + childGridTitle + "\"详细操作内容";
		childTitle.setText(title);
		PageLabel label = (PageLabel)childPage.getWidget(2);
		label.setEnable(true);
		this.childGridTitle = childGridTitle;
	}
	
	public String getChildGridTitle(){
		return childGridTitle;
	}

	public String getSelectRoleId() {
		return selectRoleId;
	}

	public void setSelectRoleId(String selectRoleId) {
		this.selectRoleId = selectRoleId;
	}
}
