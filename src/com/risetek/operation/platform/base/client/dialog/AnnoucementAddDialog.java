package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: AnnoucementAddDialog 
 * @Description: 增公告信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:52:43 
 * @version 1.0
 */
public class AnnoucementAddDialog extends BaseDialog {    
	
	public final TextBox typeBox = new TextBox();
	public final DateBox dateBox = new DateBox();
	public final TextBox addtionBox = new TextBox();
	public final DateBox stopTimeBox = new DateBox();
	public final TextBox TARGET_TYPE_Box = new TextBox();
	public final TextBox TARGET_ID_Box = new TextBox();
	public final TextBox descBox = new TextBox();
	
	private final DateBox startDateBox = new DateBox();
	private final DateBox endDateBox = new DateBox();
	
	private final DateBox create_startTime_box = new DateBox();
	private final DateBox create_endTime_box = new DateBox();
	
	private final DateBox stop_startTime_box = new DateBox();
	private final DateBox stop_endTime_box = new DateBox();
			
	private String[] searchColumns = {AnnoucementConstanst.ACE_TYPE_ZH, AnnoucementConstanst.ACE_VALIDITY_ZH };

	private String[] addColumns = { 
		AnnoucementConstanst.ACE_TYPE_ZH,
		AnnoucementConstanst.ACE_DATE_ZH,
		AnnoucementConstanst.ACE_ADDTION_ZH,
		AnnoucementConstanst.ACE_STOP_TIME_ZH,
		AnnoucementConstanst.ACE_TARGET_TYPE_ZH,
		AnnoucementConstanst.ACE_TARGET_ID_ZH,
		AnnoucementConstanst.ACE_DESCRIPTION_ZH,
		AnnoucementConstanst.ACE_VALIDITY_ZH 
	};

	/**
	 * Description: 构造器
	 */
	public AnnoucementAddDialog(boolean isSearch){
		super.isSearch = isSearch;
		if(isSearch){
			mainPanel.add(searchAnnoucement());
		}else{
			mainPanel.add(addAnnoucement());
		}
		init();
	}
	
		
	/**
	 * @Description: 创建添加组件 
	 * @return Grid 返回类型
	 */
	private Widget addAnnoucement(){	
		gridFrame.resize(8, 2);
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,typeBox);
		gridFrame.setWidget(1,1,dateBox);
		gridFrame.setWidget(2,1,addtionBox);
		gridFrame.setWidget(3,1,stopTimeBox);
		gridFrame.setWidget(4,1,TARGET_TYPE_Box);
		gridFrame.setWidget(5,1,TARGET_ID_Box);
		gridFrame.setWidget(6,1,descBox);
		gridFrame.setWidget(7,1,createListBox(AnnouceListBoxValue));
		setText("增加公告信息");
		setDescript("请输入新的公告信息");
		return gridFrame;
	}
	
	/**
	 * @Description: 创建查询组件
	 * @return VerticalPanel 返回类型
	 */
	private Widget searchAnnoucement(){
		gridFrame.resize(2, 2);
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,typeBox);
		gridFrame.setWidget(1,1,createListBox(AnnouceListBoxValue));
		VerticalPanel datePanel = new VerticalPanel();
		datePanel.add(gridFrame);
		datePanel.add(createDatePanel());
		datePanel.add(createStartPanel());
		datePanel.add(createStopPanel());
		setText("查询公告信息");
		setDescript("请输入查询信息");
		return datePanel;
	}
	
	/**
	 * @Description: 创建公告日期组件
	 * @return Widget 返回类型
	 */
	private Widget createDatePanel() {
	    Grid dateGrid = new Grid(2, 2);
		dateGrid.setStyleName(styleName);
		dateGrid.setWidget(0, 0, new Label("开始日期："));
		dateGrid.setWidget(1, 0, new Label("结束日期："));
		dateGrid.setWidget(0, 1, startDateBox);
		dateGrid.setWidget(1, 1, endDateBox);
	  
		DisclosurePanel dp = new DisclosurePanel(AnnoucementConstanst.ACE_DATE_ZH);
		dp.setAnimationEnabled(true);
		dp.setContent(dateGrid);
		return dp;
	}
	
	/**
	 * @Description: 创建 创建公告日期组件 
	 * @return Widget 返回类型
	 */
	private Widget createStartPanel() {
		Grid startGrid = new Grid(2, 2);
		startGrid.setStyleName(styleName);
		startGrid.setWidget(0, 0, new Label("开始日期："));
		startGrid.setWidget(1, 0, new Label("结束日期："));
		startGrid.setWidget(0, 1, create_startTime_box);
		startGrid.setWidget(1, 1, create_endTime_box);
		
		DisclosurePanel dp = new DisclosurePanel(AnnoucementConstanst.ACE_CREATE_TIME_ZH);
		dp.setAnimationEnabled(true);
	    dp.setContent(startGrid);
		return dp;
	}
	
	/**
	 * @Description: 创建 停止公告日期组件 
	 * @return Widget 返回类型
	 */
	private Widget createStopPanel() {
		Grid stopGrid = new Grid(2, 2);
		stopGrid.setStyleName(styleName);
		stopGrid.setWidget(0, 0, new Label("开始日期："));
		stopGrid.setWidget(1, 0, new Label("结束日期："));
		stopGrid.setWidget(0, 1, stop_startTime_box);
		stopGrid.setWidget(1, 1, stop_endTime_box);

		DisclosurePanel dp = new DisclosurePanel(AnnoucementConstanst.ACE_STOP_TIME_ZH);
		dp.setAnimationEnabled(true);
		dp.setContent(stopGrid);
		return dp;
	}
	
	/**
	 * @Description: 设置文本框宽度以及日期格式化
	 * @return void 返回类型
	 */
	private void init(){
		dateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		stopTimeBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		startDateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		endDateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		create_startTime_box.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		create_endTime_box.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		stop_startTime_box.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		stop_endTime_box.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		
		typeBox.setWidth("200px");
		dateBox.setWidth("200px");
		stopTimeBox.setWidth("200px");
		addtionBox.setWidth("200px");
		TARGET_TYPE_Box.setWidth("200px");
		TARGET_ID_Box.setWidth("200px");
		descBox.setWidth("200px");
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		super.show();
		typeBox.setFocus(true);
	}
	
	/**
	 * @Description: 验证
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		if (isSearch) {
			String startdate = (startDateBox.getTextBox().getText()).trim();
			String enddate = (endDateBox.getTextBox().getText()).trim();

			int start = 0, end = 0;
			if (null != startdate && !"".equals(startdate)) {
				start = 1;
			}
			if (null != enddate && !"".equals(enddate)) {
				end = 1;
			}
			if (start == 1 && end == 1) {
				boolean status = Util.lessOrEqualThanDate(startdate, enddate);
				if (!status) {
					setMessage("开始日期不能大于结束日期！");
					return false;
				}
			}
		}else{
			String check;
			check = Util.commValidity((typeBox.getText()).trim(), AnnoucementConstanst.ACE_TYPE_ZH);
			if (null != check) {
				setMessage(check);
				typeBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((dateBox.getTextBox().getText()).trim(), AnnoucementConstanst.ACE_DATE_ZH);
			if (null != check) {
				setMessage(check);
				dateBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((stopTimeBox.getTextBox().getText()).trim(), AnnoucementConstanst.ACE_STOP_TIME_ZH);
			if (null != check) {
				setMessage(check);
				stopTimeBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((TARGET_TYPE_Box.getText()).trim(), AnnoucementConstanst.ACE_TARGET_TYPE_ZH);
			if (null != check) {
				setMessage(check);
				TARGET_TYPE_Box.setFocus(true);
				return false;
			}
			check = Util.commValidity((TARGET_ID_Box.getText()).trim(), AnnoucementConstanst.ACE_TARGET_ID_ZH);
			if (null != check) {
				setMessage(check);
				TARGET_ID_Box.setFocus(true);
				return false;
			}
		}
		return true;
	}
}
