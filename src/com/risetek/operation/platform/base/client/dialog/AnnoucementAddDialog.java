package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * @ClassName: AnnoucementAddDialog 
 * @Description: 增公告信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:52:43 
 * @version 1.0
 */
public class AnnoucementAddDialog extends CustomDialog {
	
	public final TextBox typeBox = new TextBox();
	public final DateBox dateBox = new DateBox();
	public final TextBox addtionBox = new TextBox();
	public final DateBox stopTimeBox = new DateBox();
	public final TextBox TARGET_TYPE_Box = new TextBox();
	public final TextBox TARGET_ID_Box = new TextBox();
	public final ListBox validityBox = new ListBox();
	public final TextBox descBox = new TextBox();
	
	private final DateBox startDateBox = new DateBox();
	private final DateBox endDateBox = new DateBox();
	
	private final DateBox create_startTime_box = new DateBox();
	private final DateBox create_endTime_box = new DateBox();
	
	private final DateBox stop_startTime_box = new DateBox();
	private final DateBox stop_endTime_box = new DateBox();
	
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private boolean isSearch;// true 表示查询，false 表示增加

	private String[] listBoxValue = {"有效", "无效", "已注销", "已挂失"};

	/**
	 * Description: 构造器
	 */
	public AnnoucementAddDialog(boolean isSearch){
		this.isSearch = isSearch;
		if(isSearch){
			mainPanel.add(searchAnnoucement());
		}else{
			mainPanel.add(addAnnoucement());
		}
		
		for (int i = 0; i < listBoxValue.length; i++) {
			validityBox.addItem(listBoxValue[i]);
		}
	}
		
	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addAnnoucement(){	
		Grid gridFrame = new Grid(8, 2);
		gridFrame.setStyleName("notice");
		
		gridFrame.setWidget(0,0,new Label(AnnoucementConstanst.ACE_TYPE_ZH + "："));
		gridFrame.setWidget(1,0,new Label(AnnoucementConstanst.ACE_DATE_ZH + "："));
		gridFrame.setWidget(2,0,new Label(AnnoucementConstanst.ACE_ADDTION_ZH + "："));
		gridFrame.setWidget(3,0,new Label(AnnoucementConstanst.ACE_STOP_TIME_ZH + "："));
		gridFrame.setWidget(4,0,new Label(AnnoucementConstanst.ACE_TARGET_TYPE_ZH + "："));
		gridFrame.setWidget(5,0,new Label(AnnoucementConstanst.ACE_TARGET_ID_ZH + "："));
		gridFrame.setWidget(6,0,new Label(AnnoucementConstanst.ACE_VALIDITY_ZH + "："));
		gridFrame.setWidget(7,0,new Label(AnnoucementConstanst.ACE_DESCRIPTION_ZH + "："));
		
		gridFrame.setWidget(0,1,typeBox);
		gridFrame.setWidget(1,1,dateBox);
		gridFrame.setWidget(2,1,addtionBox);
		gridFrame.setWidget(3,1,stopTimeBox);
		gridFrame.setWidget(4,1,TARGET_TYPE_Box);
		gridFrame.setWidget(5,1,TARGET_ID_Box);
		gridFrame.setWidget(6,1,validityBox);
		gridFrame.setWidget(7,1,descBox);
		return gridFrame;
	}
	
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private VerticalPanel searchAnnoucement(){
		Grid searchFrame = new Grid(2, 2);
		searchFrame.setWidget(0,0,new Label(AnnoucementConstanst.ACE_TYPE_ZH + "："));
		searchFrame.setWidget(0,1,typeBox);
		
		searchFrame.setWidget(1,0,new Label(AnnoucementConstanst.ACE_VALIDITY_ZH + "："));
		searchFrame.setWidget(1,1,validityBox);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(searchFrame);
		verticalPanel.add(createDatePanel());
		verticalPanel.add(createStartPanel());
		verticalPanel.add(createStopPanel());
		
		return verticalPanel;
	}
	
	private Widget createDatePanel() {
	    DisclosurePanel dateDP = new DisclosurePanel(AnnoucementConstanst.ACE_DATE_ZH);
	    dateDP.setAnimationEnabled(true);

	    Grid dateGrid = new Grid(2, 2);
		dateGrid.setStyleName("notice");
		dateGrid.setWidget(0, 0, new Label("开始日期："));
		dateGrid.setWidget(1, 0, new Label("结束日期："));
		dateGrid.setWidget(0, 1, startDateBox);
		dateGrid.setWidget(1, 1, endDateBox);
	  
	    dateDP.setContent(dateGrid);
		return dateDP;
	}
	
	private Widget createStartPanel() {
		DisclosurePanel dp = new DisclosurePanel(AnnoucementConstanst.ACE_CREATE_TIME_ZH);
	    dp.setAnimationEnabled(true);
	    
		Grid create_startGrid = new Grid(2, 2);
		create_startGrid.setStyleName("notice");
		create_startGrid.setWidget(0, 0, new Label("开始日期："));
		create_startGrid.setWidget(1, 0, new Label("结束日期："));
		create_startGrid.setWidget(0, 1, create_startTime_box);
		create_startGrid.setWidget(1, 1, create_endTime_box);
		
	    dp.setContent(create_startGrid);
		return dp;
	}
	
	private Widget createStopPanel() {
		DisclosurePanel dp = new DisclosurePanel(AnnoucementConstanst.ACE_STOP_TIME_ZH);
		dp.setAnimationEnabled(true);
		
		Grid stopGrid = new Grid(2, 2);
		stopGrid.setStyleName("notice");
		stopGrid.setWidget(0, 0, new Label("开始日期："));
		stopGrid.setWidget(1, 0, new Label("结束日期："));
		stopGrid.setWidget(0, 1, stop_startTime_box);
		stopGrid.setWidget(1, 1, stop_endTime_box);

		dp.setContent(stopGrid);
		return dp;
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		if(isSearch){
			setText("查询公告信息");
			setDescript("请输入查询信息");
		}else{
			setText("增加公告信息");
			setDescript("请输入新的公告信息");
		}
		super.show();
		typeBox.setFocus(true);
	}
	
	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		/*String startdate = (startDateBox.getTextBox().getText()).trim();
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
		}*/
		return true;
	}

}
