package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.BankSink;
import com.risetek.operation.platform.base.client.constanst.BankConstanst;
import com.risetek.operation.platform.base.client.control.BankController;
import com.risetek.operation.platform.base.client.model.BankData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: BankView 
 * @Description: 发卡行模块的视图，也是模块主要的表现内容 
 * @author JZJ 
 * @date 2010-8-26 下午02:05:11 
 * @version 1.0
 */
public class BankView extends OPlatformTableView implements IOPlatformView {
	
	public final static Button addButton = new Button("增加", new BankController.TableEditAction());
	public final static Button searchButton = new Button("查询", new BankController.TableEditAction());
	
	public final static int[] columnsWidth = {25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	
	public final static String[] columns = { 
			BankConstanst.BANK_CODE_ZH,
			BankConstanst.BANK_NAME_ZH, 
			BankConstanst.BANK_VALIDITY_ZH,
			BankConstanst.BANK_DESCRIPTION_ZH 
	};
	private final static String[] banner_text = {
		"点击删除本条记录",
		"点击修改"+columns[1],
		"点击修改"+columns[2],
		"点击修改"+columns[3],
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
		Widget action = initPromptGrid();
		addActionPanel(action, BankSink.Desc);
		setLocation(BankSink.Group + " -> " + BankSink.Name);
		//detailGrid();
	}
	
	private void detailGrid(){
		Grid detailGrid = new Grid();
		detailGrid.setWidth("100%");
		detailGrid.resize(4, 5);
		detailGrid.setText(0, 0, "内容");
		detailGrid.setStyleName("optable");
		HTML title = new HTML("这是上面一张表的扩展内容");
		title.setStyleName("tableordertitle");
		outer.add(title);
		outer.add(detailGrid);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return Widget 返回类型 
	 */
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(searchButton);
		return actionPanel;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 加载数据方法
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	@Override
	public void onLoad(){
		BankController.load();
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
			grid = new GreenMouseEventGrid(banner_text);
		}
		super.formatGrid(grid, rowCount, columns, columnsWidth);
		grid.addClickHandler(new BankController.TableEditAction());
		return grid;
	}

	/**
	 * @Description: 向数据表中注入数据 
	 * @param data  参数 
	 * @return void 返回类型 
	 */
	public void render(BankData data){
		for(int index=0;index<rowCount;index++){
			renderLine(data, index);
		}
		renderStatistic(data);
	}
}