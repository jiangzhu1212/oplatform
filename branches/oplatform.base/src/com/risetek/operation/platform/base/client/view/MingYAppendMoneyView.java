package com.risetek.operation.platform.base.client.view;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.MingYAppendMoneySink;
import com.risetek.operation.platform.base.client.control.AcountController;
import com.risetek.operation.platform.base.client.control.MingYAppendMoneyController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.json.constanst.MINGYAppendMoneryConstanst;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class MingYAppendMoneyView extends OPlatformTableView implements IOPlatformView{
	
	public final static Button queryButton = new Button("查询", new MingYAppendMoneyController.TableShowAction());
	public final static Button noticeButton = new Button("通知", new MingYAppendMoneyController.TableEditAction());

	public final static DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 	
	public final static DateBox payDataTime = new DateBox();
	
	public final static ListBox noticeStatus = new ListBox();
	
	public final static int[] columnsWidth = {25, 25, 25, 25, 25, 25, 25, 25};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	String banner_tips = "";
	
	public final static String[] columns = {
		MINGYAppendMoneryConstanst.BILL_ID_ZH,
		MINGYAppendMoneryConstanst.ORDER_ID_ZH,
		MINGYAppendMoneryConstanst.SETTLEMENT_ACCOUNT_ZH,
		MINGYAppendMoneryConstanst.AMOUNT_ZH,
		MINGYAppendMoneryConstanst.DATE_TIME_ZH,
		MINGYAppendMoneryConstanst.USER_NAME_ZH,
		MINGYAppendMoneryConstanst.STATUS_ZH,
		MINGYAppendMoneryConstanst.BILL_INFO_ZH
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
	public MingYAppendMoneyView(){
		HorizontalPanel action = initPromptGrid();
		setLocation(MingYAppendMoneySink.Group + " -> " + MingYAppendMoneySink.Name);
		addActionPanel(action, MingYAppendMoneySink.Desc, MingYAppendMoneySink.Name);
	}
	
	/**
	 * @Description: 实现工具栏按钮 
	 * @return  参数 
	 * @return Widget 返回类型 
	 */
	private HorizontalPanel initPromptGrid(){
		noticeStatus.addItem("全选" , "");
		noticeStatus.addItem("通知失败","failed");
		noticeStatus.addItem("未通知","notNoticed");
		noticeStatus.addItem("已通知","noticed");
		noticeStatus.setWidth("100px");
		payDataTime.setFormat(new DateBox.DefaultFormat(format));
		
		HorizontalPanel actionPanel = new HorizontalPanel();		
		Panel searchPanel = new HorizontalPanel();
		
		searchPanel.setStyleName("tableMessagePanel-content");
		searchPanel.add(new Label("通知时间"));
		searchPanel.add(payDataTime);
		searchPanel.add(new Label("通知状态"));
		searchPanel.add(noticeStatus);
		actionPanel.add(searchPanel);
		actionPanel.add(queryButton);
		actionPanel.add(noticeButton);
		return actionPanel;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description: 加载数据方法 
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	@Override
	public void onLoad(){
		payDataTime.setValue(new Date());
		noticeStatus.setSelectedIndex(1);
		AcountController.INSTANCE.load(1);
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
		grid.addClickHandler(new AcountController.TableEditAction());
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
		MingYAppendMoneyController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		MingYAppendMoneyController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		MingYAppendMoneyController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		MingYAppendMoneyController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
