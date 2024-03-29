package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.BankSink;
import com.risetek.operation.platform.base.client.control.BankController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

/**
 * @ClassName: BankView 
 * @Description: 发卡行模块的视图，也是模块主要的表现内容 
 * @author JZJ 
 * @date 2010-8-26 下午02:05:11 
 * @version 1.0
 */
public class BankView extends OPlatformTableView implements IOPlatformView {
	
	public final static Button addButton = new Button("增加发卡行", new BankController.TableShowAction());
	public final static Button queryButton = new Button("查询发卡行", new BankController.TableShowAction());
	
	public final static int[] columnsWidth = {5, 25, 25, 25,10};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	
	public final static String[] columns = { 
			BankConstanst.BANK_ID_ZH,
			BankConstanst.PREFIX_ZH, 
			BankConstanst.NAME_ZH,
			BankConstanst.DESCRIPTION_ZH ,
			BankConstanst.VALIDITY_ZH
	};
		
	/**	
	 * @Description: 设置表格内鼠标事件的名称 
	 * @param tips  参数 
	 * @return void 返回类型 
	 */
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	/**
	 * Description: 构造器
	 */
	public BankView(){
		HorizontalPanel action = initPromptGrid();
		setLocation(BankSink.Group + " -> " + BankSink.Name);
		addActionPanel(action, BankSink.Desc, BankSink.Name);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return Widget 返回类型 
	 */
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		for (int i = 1; i < columns.length; i++) {
			actionPanel.add(new Button(Constanst.EDIT_ZH+columns[i],new BankController.TableEditAction()));
		}
		return actionPanel;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 加载数据方法
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	@Override
	public void onLoad(){
		BankController.INSTANCE.load(1);
	}

	/**
	 * (非 Javadoc) 
	 * Description: 按键事件处理方法
	 * @param keyCode
	 * @param alt 
	 * @see com.risetek.operation.platform.launch.client.view.IOPlatformView#ProcessControlKey(int, boolean)
	 */
	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {

	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 格式化行，不一定会用的，暂时写起备用
	 * @param node
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.view.OPlatformTableView#parseRow(com.google.gwt.xml.client.Node)
	 */
	@Override
	public String[] parseRow(Node node) {
		return null;
	}

	/**
	 * (非 Javadoc) 
	 * Description: 创建带鼠标事件的表格
	 * @return 
	 * @see com.risetek.operation.platform.launch.client.view.OPlatformTableView#getGrid()
	 */
	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid();
		}
		super.formatGrid(grid, rowCount, columns, columnsWidth);
		grid.addClickHandler(new BankController.TableEditAction());
		return grid;
	}

	/**
	 * (非 Javadoc) 
	 * Description:  向数据表中注入数据 
	 * @param data 
	 * @see com.risetek.operation.platform.launch.client.view.OPlatformTableView#render(com.risetek.operation.platform.launch.client.model.OPlatformData)
	 */
	@Override
	public void render(OPlatformData data) {
		for(int index=1;index<rowCount;index++){
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
		BankController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		BankController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		BankController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		BankController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}
}