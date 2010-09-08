package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.TransEnableSink;
import com.risetek.operation.platform.base.client.control.TransEnableController;
import com.risetek.operation.platform.launch.client.json.constanst.ChannelConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.TransactionConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.MouseEventGrid;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class TransEnableView  extends OPlatformTableView implements IOPlatformView {

	private final Button action1 = new Button("action1");
	public final static String[] columns = {ChannelConstanst.DESCRIPTION_ZH, ChannelConstanst.FEE_ZH, ChannelConstanst.FEE_TYPE_ZH, ChannelConstanst.FEE_ADDITION_ZH, ChannelConstanst.ADDITION_ZH, ChannelConstanst.LOC_CODE_ZH};
	public final static String[] columns1 = {TransactionConstanst.TRANS_ID_ZH, TransactionConstanst.ALIAS_ZH, TransactionConstanst.NAME_ZH, TransactionConstanst.BINDABLE_ZH, TransactionConstanst.TYPE_ZH};
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25};
	public final static int[] columnsWidth1 = {25, 25, 25, 25, 25};
	public final static int rowCount = 10;
	public static String descript = "";
	Grid grid1 = new GreenMouseEventGrid(banner_text1);
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击查看终端绑定业务.",
	};
	private final static String[] banner_text1 = {
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
		addActionPanel(initPromptGrid(), TransEnableSink.Desc);
		setLocation(TransEnableSink.Group + "->" + TransEnableSink.Name);
		grid1.setWidth("100%");
		grid1.resize(2, columns1.length);
//		grid1.setText(1, 0, "内容");
		grid1.setStyleName("optable");
		HTML title = new HTML("这是上面一张表的扩展内容");
		title.setStyleName("tableordertitle");
		outer.add(title);
		outer.add(grid1);
		formatGrid(grid1, rowCount, columns1, columnsWidth1);
		grid.addClickHandler(new TransEnableController.TableEditAction());
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
		return actionPanel;
	}
	
	/** 
	 * 功能： 加载数据方法
	 *(non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	public void onLoad(){
		TransEnableController.load();
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
		for(int index=0;index<rowCount;index++){
			renderLine(grid1, data, index);
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
