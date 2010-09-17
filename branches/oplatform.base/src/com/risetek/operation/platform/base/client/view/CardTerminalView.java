package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.CardTerminalSink;
import com.risetek.operation.platform.base.client.control.CardTerminalController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.CardTerminalConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class CardTerminalView extends OPlatformTableView implements IOPlatformView {

	public final static Button addButton = new Button("添加终端",new CardTerminalController.TableShowAction());
	public final static Button queryButton = new Button("查询终端",new CardTerminalController.TableShowAction());
	public final static String[] columns = {CardTerminalConstanst.TERMINAL_ID_ZH, CardTerminalConstanst.SN_ZH,CardTerminalConstanst.DESCRIPTION_ZH,CardTerminalConstanst.ADDITION_ZH,CardTerminalConstanst.VALIDITY_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25};
	public static String descript = "";
	String banner_tips = "";
		
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public CardTerminalView(){
		HorizontalPanel action = initPromptGrid();
		setLocation(CardTerminalSink.Group + " -> " + CardTerminalSink.Name);
		addActionPanel(action, descript, CardTerminalSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new CardTerminalController.TableEditAction());
	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();	
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		for (int i = 1; i < columns.length; i++) {
			actionPanel.add(new Button(Constanst.EDIT_ZH+columns[i],new CardTerminalController.TableEditAction()));
		}
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		CardTerminalController.load();
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
		formatGrid(grid, rowCount, columns,columnsWidth);

		return grid;
	}

	public void render(OPlatformData data){
		for(int index=1;index<rowCount;index++){
			renderLine(grid,data, index);
		}
		renderStatistic(data);
	}

	@Override
	public HorizontalPanel getPagePanel() {
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
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
