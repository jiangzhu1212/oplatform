package com.risetek.operation.platform.base.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
import com.risetek.operation.platform.launch.client.view.MouseEventGrid;
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
	
	public RoleConfigView(){
		grid.setHeight("40%");
		setLocation(RoleConfigSink.Group + "->" + RoleConfigSink.Name);
		addActionPanel(initPromptGrid(), RoleConfigSink.Desc, RoleConfigSink.Name);
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
		final PageLabel first = new PageLabel("|<");
		final PageLabel before = new PageLabel("<<");
		final PageLabel after = new PageLabel(">>");
		final PageLabel last = new PageLabel(">|");
		final PageLabel page1 = new PageLabel("1");
		page1.addClickHandler(new ClickHandler() {
			String tisp_value;
			String id;
			public void onClick(ClickEvent event) {
				tisp_value = getChildGridTitle();
				id = getSelectRoleId();
				PageLabel label = (PageLabel)event.getSource();
				label.addStyleName("select");
				RoleConfigController.INSTANCE.setChildPagePoint(1);
				HorizontalPanel page = (HorizontalPanel)label.getParent();
				for(int i=0;i<page.getWidgetCount();i++){
					Widget w = page.getWidget(i);
					if(!w.equals(label)){
						w.removeStyleName("select");
					}
				}
				if(page.getWidgetCount()>5){
					first.setEnable(false);
					before.setEnable(false);
					after.setEnable(true);
					last.setEnable(true);
				}
				RoleConfigController.INSTANCE.loadChild(id, tisp_value, 1);
			}
		});
		first.setEnable(false);
		before.setEnable(false);
		after.setEnable(false);
		last.setEnable(false);
		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				RoleConfigController.INSTANCE.firstChildPageAction(getSelectRoleId(), getChildGridTitle(), label);
			}
		});
		before.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				RoleConfigController.INSTANCE.beforeChildPageAction(getSelectRoleId(), getChildGridTitle(), label);
			}
		});
		after.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				RoleConfigController.INSTANCE.afterChildPageAction(getSelectRoleId(), getChildGridTitle(), label);
			}
		});
		last.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				RoleConfigController.INSTANCE.lastChildPageAction(getSelectRoleId(), getChildGridTitle(), label);
			}
		});
		childPage.add(first);
		childPage.add(before);
		childPage.add(page1);
		childPage.add(after);
		childPage.add(last);
		return childPage;
	}

	private HorizontalPanel initPromptGrid(){
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
		RoleConfigController.INSTANCE.load(1);
	}

	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid();
		}
		formatGrid(grid, mainRowCount, columns, columnsWidth);
		grid.addClickHandler(new RoleConfigController.TableAction());
		return grid;
	}
	
	public Grid getChildGrid() {
		if(childGrid == null){
			childGrid = new GreenMouseEventGrid(true);
		}
		formatGrid(childGrid, childRowCount, childColumns, childColumnsWidth);
		childGrid.addClickHandler(new RoleConfigController.ChildTableAction());
		return childGrid;
	}

	@Override
	public void render(OPlatformData data) {
		grid.resizeRows(mainRowCount+1);
		clearGrid(grid, mainRowCount);
		clearGrid(childGrid, childRowCount);
		String title = "角色\"未选择\"详细操作内容";
		childTitle.setText(title);
		if(childPage.getWidgetCount()>3){
			PageLabel label = (PageLabel)childPage.getWidget(2);
			label.setEnable(false);
		}
		for(int index=1;index<mainRowCount+1;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}
	
	public void renderChild(OPlatformData data) {
		clearGrid(childGrid, childRowCount);
		childGrid.resizeRows(childRowCount+1);
		for(int index=1;index<childRowCount+1;index++){
			renderLine(childGrid, data, index);
		}
	}
	
	public void setChildGridTitle(String childGridTitle){
		String title = "角色\"" + childGridTitle + "\"详细操作内容";
		childTitle.setText(title);
		PageLabel label = (PageLabel)childPage.getWidget(2);
		if(!label.isEnable()) {
			label.setEnable(true);
		}
		int point = RoleConfigController.INSTANCE.getChildPagePoint();
		if(point==1){
			label.addStyleName("select");
		}
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

	@Override
	public HorizontalPanel getPagePanel() {
		return page;
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		return childPage;
	}
	
	@Override
	public void firstPageAction(PageLabel label) {
		RoleConfigController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		RoleConfigController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		RoleConfigController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		RoleConfigController.INSTANCE.lastPageAction(label);
	}
	
	/**
	 * @author Amber
	 * 功能：数据表鼠标移动样式事件处理子类
	 * 2010-8-23 下午11:55:34
	 */
	public class GreenMouseEventGrid extends MouseEventGrid {

		boolean isChild;
		
		public GreenMouseEventGrid(){
			this.isChild = false;
		}
		
		public GreenMouseEventGrid(boolean isChild){
			this.isChild = isChild;
		}
		
		@Override
		public void onMouseOver(Element td, int column) {
			DOM.removeElementAttribute(td, "title");
			String text = "";
			if(column<2){
				if(column==1){
					text = "点击删除本条记录";
				}
			} else {
				text = "";//bannerText[column-2];
			}
			if(!isChild){
				setInfo(text);
			} else {
				VerticalPanel child = (VerticalPanel)outer.getWidget(1);
				Grid childTitle = (Grid)child.getWidget(0);
				childTitle.setText(0, 1, text);
			}
            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
		}

		@Override
		public void onMouseOut(Element td, int column) {
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
//				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
			
			if(!isChild){
				setInfo("");
			} else {
				VerticalPanel child = (VerticalPanel)outer.getWidget(1);
				Grid childTitle = (Grid)child.getWidget(0);
				childTitle.setText(0, 1, "");
			}
		}
	}
}
