package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.BaseSink;
import com.risetek.operation.platform.base.client.control.BaseController;
import com.risetek.operation.platform.base.client.model.BaseData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @author Amber
 * 功能：模块的视图，也是模块主要的表现内容
 * 2010-8-23 下午11:51:41
 */
public class BaseView extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("action1");
	private final Button action2 = new Button("action2");
	public final static String[] columns = {"列1", "列2", "列3", "列4"};
	public final static int[] columnsWidth = {25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public static String descript = "";
	
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击修改列1.",
		"点击修改列2.",
		"点击修改列3.",
		"点击修改列4.",
		
	};
	
	/**
	 * 功能：设置表格内鼠标事件的名称
	 *
	 * void
	 * @param tips
	 */
	public void setBannerTips(String tips) {
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public BaseView(){
		Widget action = initPromptGrid();
		addActionPanel(action, descript);
		setLocation(BaseSink.Group + " -> " + BaseSink.Name);
		setStatisticText(100);
	}
	
	/**
	 * 功能：实现工具栏按钮
	 *
	 * Widget
	 * @return
	 */
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
//		actionPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		actionPanel.setHeight("32px");
//		actionPanel.setWidth("100%");
//		actionPanel.setBorderWidth(1);
		actionPanel.add(action1);
		actionPanel.add(action2);
		return actionPanel;
	}
	
	/** 
	 * 功能： 加载数据方法
	 *(non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	public void onLoad(){
		BaseController.load();
	}

	/** 
	 * 功能： 按键事件处理方法
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.view.IOPlatformView#ProcessControlKey(int, boolean)
	 */
	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * 功能： 格式化行，不一定会用的，暂时写起备用
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.view.OPlatformTableView#parseRow(com.google.gwt.xml.client.Node)
	 */
	@Override
	public String[] parseRow(Node node) {
		return null;
	}

	/** 
	 * 功能： 创建带鼠标事件的表格
	 *(non-Javadoc)
	 * @see com.risetek.operation.platform.launch.client.view.OPlatformTableView#getGrid()
	 */
	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid(banner_text);
		}
		formatGrid(grid, rowCount, columns, columnsWidth);
		return grid;
	}

	/**
	 * 功能：向数据表中注入数据
	 *
	 * void
	 * @param data
	 */
	public void render(BaseData data){
		for(int index=0;index<rowCount;index++){
			renderLine(data, index);
		}
		renderStatistic(data);
	}
}
