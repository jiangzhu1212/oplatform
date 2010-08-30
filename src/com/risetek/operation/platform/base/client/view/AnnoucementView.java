package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.AnnoucementSink;
import com.risetek.operation.platform.base.client.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.base.client.control.AnnoucementController;
import com.risetek.operation.platform.base.client.model.AnnoucementData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: AnnoucementView 
 * @Description: 公告模块的视图，也是模块主要的表现内容 
 * @author JZJ 
 * @date 2010-8-27 上午10:36:24 
 * @version 1.0
 */
public class AnnoucementView extends OPlatformTableView implements IOPlatformView {
	
	public final static Button addButton = new Button("增加", new AnnoucementController.TableEditAction());
	public final static Button searchButton = new Button("查询", new AnnoucementController.TableEditAction());
	
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	public final static String[] columns = { 
			AnnoucementConstanst.ACE_ID_ZH,
			AnnoucementConstanst.ACE_TYPE_ZH,
			AnnoucementConstanst.ACE_DATE_ZH, 
			AnnoucementConstanst.ACE_ADDTION_ZH,
			AnnoucementConstanst.ACE_CREATE_TIME_ZH,
			AnnoucementConstanst.ACE_STOP_TIME_ZH,
			AnnoucementConstanst.ACE_TARGET_TYPE_ZH,
			AnnoucementConstanst.ACE_TARGET_ID_ZH,
			AnnoucementConstanst.ACE_VALIDITY_ZH,
			AnnoucementConstanst.ACE_DESCRIPTION_ZH 
	};
	private final static String[] banner_text = {
		"点击删除本条记录",
		"点击修改"+columns[1],
		"点击修改"+columns[2],
		"点击修改"+columns[3],
		"",
		"点击修改"+columns[5],
		"点击修改"+columns[6],
		"点击修改"+columns[7],
		"点击修改"+columns[8],
		"点击修改"+columns[9],
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
	public AnnoucementView(){
		Widget action = initPromptGrid();
		addActionPanel(action, AnnoucementSink.Desc);
		setLocation(AnnoucementSink.Group + " -> " + AnnoucementSink.Name);
		setStatisticText(100);
		//setInfo("this is info");
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
		AnnoucementController.load();
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
		grid.addClickHandler(new AnnoucementController.TableEditAction());
		return grid;
	}

	/**
	 * @Description: 向数据表中注入数据 
	 * @param data  参数 
	 * @return void 返回类型 
	 */
	public void render(AnnoucementData data){
		for(int index=0;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public void render(OPlatformData data) {
		// TODO Auto-generated method stub
		
	}
}