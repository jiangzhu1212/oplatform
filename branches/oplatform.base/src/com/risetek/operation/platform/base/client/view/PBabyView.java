package com.risetek.operation.platform.base.client.view;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.PBabySink;
import com.risetek.operation.platform.base.client.control.PBabyController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.PBabyConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class PBabyView  extends OPlatformTableView implements IOPlatformView {


	public final static Button queryButton = new Button("查询异常票宝宝",new PBabyController.TableShowAction());
	public final static Button checkTicket = new Button("出票",new PBabyController.TableEditAction());
	
	public final static TextBox phoneNumber = new TextBox();
	
	public final static DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
	
	public final static DateBox createDataTime = new DateBox();
	
	public final static String[] columns = {
		PBabyConstanst.ORDID_ZH,
		PBabyConstanst.KEYID_ZH,
		PBabyConstanst.IS_OK_ZH,
		PBabyConstanst.CODE_ZH,
		PBabyConstanst.SEAT_ZH,
		PBabyConstanst.PNR_ZH
	};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25};
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public PBabyView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, PBabySink.Desc, PBabySink.Name);
//		setCellHeight(action, "38px");
		setLocation(PBabySink.Group + " -> " + PBabySink.Name);
		setStatisticText(100);
		grid.addClickHandler(new PBabyController.TableEditAction());

	}
	
	private HorizontalPanel initPromptGrid(){
		
		createDataTime.setFormat(new DateBox.DefaultFormat(format));
		
		HorizontalPanel actionPanel = new HorizontalPanel();
		Panel searchPanel = new HorizontalPanel();
		searchPanel.setStyleName("tableMessagePanel-content");
		phoneNumber.setWidth("180px");
		searchPanel.add(new Label("用户电话号码"));
		searchPanel.add(phoneNumber);
		searchPanel.add(new Label("生成日期"));
		searchPanel.add(createDataTime);
		searchPanel.add(queryButton);
		searchPanel.add(checkTicket);
		actionPanel.add(searchPanel);
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		phoneNumber.setText("");
		createDataTime.setValue(null);
		PBabyController.INSTANCE.load(1);
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
		for(int index=0;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public HorizontalPanel getPagePanel() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		PBabyController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		PBabyController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		PBabyController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		PBabyController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}


}
