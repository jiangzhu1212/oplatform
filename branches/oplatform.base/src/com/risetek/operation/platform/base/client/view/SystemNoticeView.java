package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.SystemNoticeSink;
import com.risetek.operation.platform.base.client.control.SystemNoticeController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class SystemNoticeView extends OPlatformTableView implements	IOPlatformView {

	public static String descript = "";
	public final static String[] columns = {"ID", "内容", "范围", "生效时间", "失效时间", "发布时间"};
	public final static int[] columnsWidth = {5, 32, 12, 17, 17, 17};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public Grid grid = getGrid();
	
	public Button addNotice = new Button("添加通知公告", new SystemNoticeController.AddNoticeAction());
	public Button editNotice = new Button("修改通知公告", new SystemNoticeController.EditNoticeAction());
	
	public SystemNoticeView(){
		setLocation(SystemNoticeSink.Group + "->" + SystemNoticeSink.Name);
		addActionPanel(initPromptGrid(), SystemNoticeSink.Desc, SystemNoticeSink.Name);
	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addNotice);
		actionPanel.add(editNotice);
		return actionPanel;
	}
	
	public void onLoad(){
		SystemNoticeController.INSTANCE.load(1);
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
		formatGrid(grid, rowCount, columns, columnsWidth);
		return grid;
	}

	@Override
	public HorizontalPanel getPagePanel() {
		return page;
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		SystemNoticeController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		SystemNoticeController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		SystemNoticeController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		SystemNoticeController.INSTANCE.lastPageAction(label);
	}

	@Override
	public void render(OPlatformData data) {
		grid.resizeRows(rowCount + 1);
		clearGridStyle(grid, rowCount);
		clearGrid(grid, rowCount);
		for(int index=1;index<=rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

}
