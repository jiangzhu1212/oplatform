package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.AcountSink;
import com.risetek.operation.platform.base.client.constanst.AcountConstanst;
import com.risetek.operation.platform.base.client.control.AcountController;
import com.risetek.operation.platform.base.client.model.AcountData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

/**
 * @ClassName: AcountView 
 * @Description: 银行卡模块的视图，也是模块主要的表现内容
 * @author JZJ 
 * @date 2010-8-26 下午02:04:41 
 * @version 1.0
 */
public class AcountView extends OPlatformTableView implements IOPlatformView {

	public final static Button addButton = new Button("增加", new AcountController.TableEditAction());
	public final static Button searchButton = new Button("查询", new AcountController.TableEditAction());

	public final static int[] columnsWidth = {25, 25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	
	public final static String[] columns = {
			AcountConstanst.ACCOUNT_NUMBER_ZH, 
			AcountConstanst.BANK_CODE_ZH,
			AcountConstanst.ACCOUNT_VALIDITY_ZH,
			AcountConstanst.ACCOUNT_ADDTION_ZH,
			AcountConstanst.ACCOUNT_DESCRIPTION_ZH 
	};
	private final static String[] banner_text = {
		"点击删除本条记录",
		"点击修改"+columns[1],
		"点击修改"+columns[2],
		"点击修改"+columns[3],
		"点击修改"+columns[4],
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
	public AcountView(){
		Widget action = initPromptGrid();
		addActionPanel(action, AcountSink.Desc);
		setLocation(AcountSink.Group + " -> " + AcountSink.Name);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return  参数 
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
		AcountController.load();
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
		grid.addClickHandler(new AcountController.TableEditAction());
		return grid;
	}

	/**
	 * @Description: 向数据表中注入数据 
	 * @param data  参数 
	 * @return void 返回类型 
	 */
	public void render(AcountData data){
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