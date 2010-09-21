package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.TransEnableSink;
import com.risetek.operation.platform.base.client.control.TransEnableController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.MouseEventGrid;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class TransEnableView  extends OPlatformTableView implements IOPlatformView {

	public final static Button queryButton = new Button("查询业务使能", new TransEnableController.TableShowAction());
	public final static Button addButton = new Button("添加业务使能", new TransEnableController.TableShowAction());
	public final static String[] columns = {TransactionConstanst.TRANS_ID_ZH, TransactionConstanst.ALIAS_ZH, TransactionConstanst.NAME_ZH, TransactionConstanst.BINDABLE_ZH, TransactionConstanst.TYPE_ZH};
	public final static int[] columnsWidth = {25, 25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;;
	public static String descript = "";
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击修改业务绑定.",
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
	
	public TransEnableView(){
		addActionPanel(initPromptGrid(), TransEnableSink.Desc, TransEnableSink.Name);
		setLocation(TransEnableSink.Group + "->" + TransEnableSink.Name);
//		grid1.setText(1, 0, "内容");
		HTML title = new HTML("这是上面一张表的扩展内容");
		title.setStyleName("tableordertitle");
		outer.add(title);
		grid.addClickHandler(new TransEnableController.TableEditAction());
	}
	
	/**
	 * 功能：实现工具栏按钮
	 *
	 * Widget
	 * @return
	 */
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(queryButton);
		actionPanel.add(addButton);
		for (int i = 1; i < columns.length; i++) {
			actionPanel.add(new Button(Constanst.EDIT_ZH+columns[i],new TransEnableController.TableEditAction()));
		}
		return actionPanel;
	}
	
	/** 
	 * 功能： 加载数据方法
	 *(non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	public void onLoad(){
		TransEnableController.load();
		TransEnableController.INSTANCE.load(1);
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
	
	public class GreenMouseEventGrid extends MouseEventGrid {

		String[] bannerText;
		
		public GreenMouseEventGrid(String[] bannerText){
			this.bannerText = bannerText;
		}
		
		@Override
		public void onMouseOver(Element td, int column) {
			DOM.removeElementAttribute(td, "title");			
			if(column==1){
					setInfo("点击查看本条记录");
			}else {
				setInfo(bannerText[0]);
			}

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
		}

		@Override
		public void onMouseOut(Element td, int column) {
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {		
			}
			setInfo("");
		}
	}

	/**
	 * 功能：向数据表中注入数据
	 *
	 * void
	 * @param data
	 */
	public void render(OPlatformData data){
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
		TransEnableController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		TransEnableController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		TransEnableController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		TransEnableController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
