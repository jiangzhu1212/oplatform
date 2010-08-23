package com.risetek.operation.platform.base.client.view;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.base.client.control.BaseController;
import com.risetek.operation.platform.base.client.model.BaseData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.ClickActionHandler;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.MouseEventGrid;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class BaseView extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("action1");
	ClickActionHandler tableEditAction = new BaseController.TableEditAction();
	ClickActionHandler tableShowAction = new BaseController.TableShowAction();
	public static ArrayList<ClickActionHandler> handlerList = new ArrayList<ClickActionHandler>();
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
	
	public BaseView(){
		Widget action = initPromptGrid();
		add(action, DockPanel.SOUTH);
		setCellHeight(action, "38px");
		setLocation(BaseSink.Group + " -> " + BaseSink.Name);
		setStatisticText(100);
		setInfo("this is info");
		handlerList.add(tableEditAction);
		handlerList.add(tableShowAction);
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
	
	public void onLoad(){
		BaseController.load();
	}

	@Override
	public void disablePrivate() {
		action1.setVisible(false);
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
		formatGrid(grid, rowCount, columns);
//		grid.resize(2, 2);
//		grid.setText(0, 0, "00");
//		grid.setText(0, 1, "01");
//		grid.setText(1, 0, "10");
//		grid.setText(1, 1, "11");
		return grid;
	}

	public void render(BaseData data){
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
            renderLine(BaseController.INSTANCE.getData(), row-1);
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

	@Override
	public ArrayList<ClickActionHandler> getActionList() {
		return handlerList;
	}
}
