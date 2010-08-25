package com.risetek.operation.platform.process.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.MouseEventGrid;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.process.client.ProcessSink;
import com.risetek.operation.platform.process.client.control.ProcessController;
import com.risetek.operation.platform.process.client.model.ProcessData;

public class ProcessView extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("action222");
	public final static String[] columns = {"列1", "列2", "列3", "列4"};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击修改列1.",
		"点击修改列2.",
		"点击修改列3.",
		"点击修改列4.",
		
	};
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public ProcessView(){
		Widget action = initPromptGrid();
		addActionPanel(action);
		setLocation(ProcessSink.Group + " -> " + ProcessSink.Name);
		setStatisticText(100);
		setInfo("this is info");
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		actionPanel.add(action1);
		return actionPanel;
	}
	
	public void onLoad(){
		ProcessController.load();
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
		formatGrid(grid, rowCount, columns);
//		grid.resize(2, 2);
//		grid.setText(0, 0, "00");
//		grid.setText(0, 1, "01");
//		grid.setText(1, 0, "10");
//		grid.setText(1, 1, "11");
		return grid;
	}

	public void render(ProcessData data){
		for(int index=0;index<rowCount;index++){
			renderLine(data, index);
		}
		renderStatistic(data);
	}
	
	class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOver(Element td, int column) {
			DOM.removeElementAttribute(td, "title");			

			setInfo(banner_text[column]);

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            renderLine(ProcessController.INSTANCE.getData(), row-1);
		}

		@Override
		public void onMouseOut(Element td, int column) {
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
//				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
			
//			if(mayColor == true) {
//				td.getStyle().setColor("red");
				setInfo("");
//				td.getStyle().setCursor(Cursor.POINTER);
//			}
		}
		
	}
}
