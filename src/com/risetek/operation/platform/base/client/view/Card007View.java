package com.risetek.operation.platform.base.client.view;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.Card007Sink;
import com.risetek.operation.platform.base.client.control.Card007Controller;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.Card007Constanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

/** 
 * @ClassName: Card007View 
 * @Description: 全国直充模块的视图，也是模块主要的表现内容 
 * @author JZJ 
 * @date 2010-9-2 下午03:02:30 
 * @version 1.0 
 */
public class Card007View extends OPlatformTableView implements IOPlatformView {
	
	protected final static String[][] statusBoxValue = { {"已充值", "0"}, {"未充值","1"}, {"充值失败","2"}, {"已委托","3"}, {"未委托","4"}};
	public final static DateBox dateBox = new DateBox();
	private final static ListBox listBox = new ListBox();
	public static int statusValue = 2;
	
	public final Button chargeButton = new Button("充值", new Card007Controller.chargeAction(grid));
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	
	public final static String[] columns = {
		Card007Constanst.CHARGE_PHONE_NUMBER_ZH,
		Card007Constanst.BILL_EXTERN_ID_ZH,
		Card007Constanst.AMOUNT_ZH,
		Card007Constanst.STATUS_ZH,
		Card007Constanst.DATETIME_ZH,
		Card007Constanst.RETINFO_ZH,
		Card007Constanst.PAYSTATE_ZH
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
	public Card007View(){
		HorizontalPanel action = initPromptGrid();
		setLocation(Card007Sink.Group + " -> " + Card007Sink.Name);
		addActionPanel(action, Card007Sink.Desc, Card007Sink.Name);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return  参数 
	 * @return Widget 返回类型 
	 */
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(createSearchBar());
		actionPanel.add(chargeButton);
		return actionPanel;
	}
	
	/**
	 * @Description:  实现日期和状态搜索框，并显示在工具栏
	 * @return  参数 
	 * @return Widget 返回类型
	 */
	private Widget createSearchBar(){
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.setStyleName("tableMessagePanel-content");
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		dateBox.setWidth("80px");
		dateBox.setValue(new Date());

		for (int i = 0; i < statusBoxValue.length; i++) {
			listBox.addItem(statusBoxValue[i][0], statusBoxValue[i][1]);
		}
		listBox.setSelectedIndex(statusValue);
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = listBox.getSelectedIndex();
				statusValue = Integer.parseInt(statusBoxValue[index][1]);
				onLoad();
			}
		});

		searchPanel.add(new Label(Card007Constanst.PAY_DATETIME_ZH+":"));
		searchPanel.add(dateBox);
		searchPanel.add(new Label(Card007Constanst.STATUS_ZH+":"));
		searchPanel.add(listBox);
		return searchPanel;
	}
	
	
	/**
	 * (非 Javadoc) 
	 * Description: 加载数据方法 
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	@Override
	public void onLoad(){
		Card007Controller.load();
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
