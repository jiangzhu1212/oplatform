package com.risetek.operation.platform.log.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;
import com.risetek.operation.platform.log.client.LogSink;
import com.risetek.operation.platform.log.client.control.LogController;

public class LogView extends OPlatformTableView implements IOPlatformView {

	public final static String[] columns = {"ID", "记录者", "操作内容", "操作时间"};
	public final static int[] columnsWidth = {5, 10, 65, 20};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public static String descript = "";
	
	public Button filterLog = new Button("过滤日志内容", new LogController.FilterLogAction());
	public Button filterLogByKey = new Button("过滤日志记录者", new LogController.FilterLogByKeyAction());
	public Button showHistory = new Button("查看历史日志", new LogController.ShowHistoryAction());
	public Button exportLog = new Button("导出日志");
	public Button clearHistory = new Button("清除日志");
	
	public LogView(){
		addActionPanel(initPromptGrid(), LogSink.Desc, LogSink.Name);
		setLocation(LogSink.Group + "->" + LogSink.Name);
	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(filterLog);
		actionPanel.add(showHistory);
		actionPanel.add(exportLog);
		actionPanel.add(clearHistory);
		return actionPanel;
	}
	
	Timer refreshTimer = new Timer() {
		public void run() {
			GWT.log("刷新日志");
			LogController.INSTANCE.load(1);
		}
	};
	
	public void onLoad(){
		refreshTimer.run();
	}
	
	public void onUnload(){
		refreshTimer.cancel();
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
	public void render(OPlatformData data) {
		grid.resizeRows(rowCount+1);
		clearGridStyle(grid, rowCount);
		clearGrid(grid, rowCount);
		showHistory.setText(data.autoRefresh?"查看历史":"自动更新");
		if(!data.autoRefresh){
			refreshTimer.cancel();
		} else {
			refreshTimer.schedule(15000);
		}
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

}
