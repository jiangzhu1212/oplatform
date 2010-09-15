package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.CupCertificateSink;
import com.risetek.operation.platform.base.client.control.CupCertificateController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.CupCertificateConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class CupCertificateView extends OPlatformTableView implements IOPlatformView{


	public final static Button queryButton = new Button("查询交易凭证",new CupCertificateController.TableShowAction());
	public final static Button addButton = new Button("添加交易凭证",new CupCertificateController.TableShowAction());

	public final static String[] columns = {CupCertificateConstanst.CERTIFICATE_ID_ZH ,CupCertificateConstanst.BILL_ID_ZH ,CupCertificateConstanst.TIME_ZH,
		CupCertificateConstanst.AMOUNT_ZH , CupCertificateConstanst.TYPE_ZH , CupCertificateConstanst.ACCOUNT_NUMBER_ZH};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25 };
	String banner_tips = "";
	
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public CupCertificateView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, CupCertificateSink.Desc,CupCertificateSink.Name);
//		setCellHeight(action, "38px");
		setLocation(CupCertificateSink.Group + " -> " + CupCertificateSink.Name);
		setStatisticText(100);
		grid.addClickHandler(new CupCertificateController.TableEditAction());

	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		
		actionPanel.setStyleName("aa");		
		
		return actionPanel;
	}
	
	public void onLoad(){
		CupCertificateController.load();
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
//		grid.resize(2, 2);
//		grid.setText(0, 0, "00");
//		grid.setText(0, 1, "01");
//		grid.setText(1, 0, "10");
//		grid.setText(1, 1, "11");
		return grid;
	}
	
	@Override
	public void render(OPlatformData data) {
		// TODO Auto-generated method stub
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
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
