package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.TdbcSink;
import com.risetek.operation.platform.base.client.control.TdbcController;
import com.risetek.operation.platform.base.client.model.TdbcData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.TdbcConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

/**
 * @ClassName: TdbcView 
 * @Description: 二维码模块的视图，也是模块主要的表现内容  
 * @author JZJ 
 * @date 2010-8-26 下午03:20:06 
 * @version 1.0
 */
public class TdbcView extends OPlatformTableView implements IOPlatformView {

	public final static Button addButton = new Button("添加二维码", new TdbcController.TableShowAction());
	public final static Button queryButton = new Button("查询二维码", new TdbcController.TableShowAction());

	public final static String[] columns = {TdbcConstanst.TDBC_ID_ZH, TdbcConstanst.E_GOODS_SN_ZH, TdbcConstanst.IMAGE_ZH };
	public final static int[] columnsWidth = {25, 25, 35};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	
	public String banner_tips = "";
	
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
	public TdbcView(){
		HorizontalPanel action = initPromptGrid();
		addActionPanel(action, TdbcSink.Desc, TdbcSink.Name);
		setLocation(TdbcSink.Group + " -> " + TdbcSink.Name);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return Widget 返回类型 
	 */
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addButton);
		actionPanel.add(queryButton);
		return actionPanel;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 加载数据方法
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	@Override
	public void onLoad(){
		TdbcController.load();
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
		return grid;
	}

	/**
	 * @Description: 向数据表中注入数据 
	 * @param data  参数 
	 * @return void 返回类型 
	 */
	public void render(TdbcData data){
		for(int index=0;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public void render(OPlatformData data) {
		// TODO Auto-generated method stub
		
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