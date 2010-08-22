package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.launch.client.view.GreenMouseEventGrid;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class BaseView extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("action1");
	
	public BaseView(){
		Widget action = initPromptGrid();
		add(action, DockPanel.SOUTH);
		setCellHeight(action, "38px");
		setLocation(BaseSink.Group + " -> " + BaseSink.Name);
		setStatisticText(100);
		setInfo("this is info");
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		actionPanel.setHeight("35px");
		actionPanel.setWidth("100%");
		actionPanel.setBorderWidth(1);
		actionPanel.add(action1);
		return actionPanel;
	}
	
	@Override
	public void disablePrivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePrivate() {
		// TODO Auto-generated method stub
		
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
		grid.resize(2, 2);
		grid.setText(0, 0, "00");
		grid.setText(0, 1, "01");
		grid.setText(1, 0, "10");
		grid.setText(1, 1, "11");
		return grid;
	}

}
