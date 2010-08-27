package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.constanst.AcountConstanst;
import com.risetek.operation.platform.base.shared.FieldVerifier;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: AcountAddDialog 
 * @Description: 增加银行卡信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午09:40:05 
 * @version 1.0
 */
public class AcountAddDialog extends CustomDialog {
	
	public final TextBox numberBox = new TextBox();
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox addtionBox = new TextBox();
	public final DateBox validityBox = new DateBox();
	public final TextBox descBox = new TextBox();
		
	private final DateBox startDateBox = new DateBox();
	private final DateBox endDateBox = new DateBox();
	
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 

	private Grid gridFrame = new Grid();
	
	/**
	 * Description: 构造器
	 */
	public AcountAddDialog(String tag){
		gridFrame.setStyleName("notice");
		if(null != tag){
			gridFrame.resize(2, 2);
			mainPanel.add(searchAccount());
		}else{
			gridFrame.resize(5, 2);
			mainPanel.add(addAccount());
		}
		initWith();
	}
	
	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addAccount(){	
		validityBox.setFormat(new DateBox.DefaultFormat(format));

		gridFrame.setWidget(0,0,new Label(AcountConstanst.ACCOUNT_NUMBER_ZH + "："));
		gridFrame.setWidget(1,0,new Label(AcountConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(2,0,new Label(AcountConstanst.ACCOUNT_VALIDITY_ZH + "："));
		gridFrame.setWidget(3,0,new Label(AcountConstanst.ACCOUNT_ADDTION_ZH + "："));
		gridFrame.setWidget(4,0,new Label(AcountConstanst.ACCOUNT_DESCRIPTION_ZH + "："));
		
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		gridFrame.setWidget(2,1,validityBox);
		gridFrame.setWidget(3,1,addtionBox);
		gridFrame.setWidget(4,1,descBox);
						
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private VerticalPanel searchAccount(){
		gridFrame.setWidget(0,0,new Label(AcountConstanst.ACCOUNT_NUMBER_ZH + "："));
		gridFrame.setWidget(1,0,new Label(AcountConstanst.BANK_CODE_ZH + "："));
		
		gridFrame.setWidget(0,1,numberBox);
		gridFrame.setWidget(1,1,bankCodeBox);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		
		startDateBox.setFormat(new DateBox.DefaultFormat(format));
		endDateBox.setFormat(new DateBox.DefaultFormat(format));
		
		Grid validityGrid = new Grid(2, 2);
		validityGrid.setStyleName("notice");
		validityGrid.setWidget(0, 0, new Label("开始日期："));
		validityGrid.setWidget(0, 1, startDateBox);
		validityGrid.setWidget(1, 0, new Label("结束日期："));
		validityGrid.setWidget(1, 1, endDateBox);
		
	    DisclosurePanel advancedDisclosure = new DisclosurePanel(AcountConstanst.ACCOUNT_VALIDITY_ZH);
		advancedDisclosure.setAnimationEnabled(true);
		advancedDisclosure.setContent(validityGrid);
				
		verticalPanel.add(gridFrame);
		verticalPanel.add(advancedDisclosure);

		return verticalPanel;
	}
	
	/**
	 * @Description: 设置文本框宽度
	 * @return void 返回类型
	 */
	private void initWith(){
		bankCodeBox.setWidth("240px");
		numberBox.setWidth("240px");
		addtionBox.setWidth("240px");
		validityBox.setWidth("240px");
		descBox.setWidth("240px");
		startDateBox.setWidth("200px");
		endDateBox.setWidth("200px");
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(String tag){
		if(null != tag){
			setText("查询银行卡信息");
			setDescript("请输入查询信息");
		}else{
			setText("增加银行卡信息");
			setDescript("请输入新的银行卡信息");
		}
		super.show();
		numberBox.setFocus(true);
	}

	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid(String tag) {
		//如果tag不为空且为serach就验证开始日期和结束日期
		if (null != tag) {
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
		} else {
			String check;
//			check = Util.getCheckedRow(gridFrame);
//			if (null != check) {
//				setMessage(check);
//				numberBox.setFocus(true);
//				return false;
//			}
			check = Util.commValidity((bankCodeBox.getText()).trim(), AcountConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				bankCodeBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((validityBox.getTextBox().getText()).trim(), AcountConstanst.ACCOUNT_VALIDITY_ZH);
			if (null != check) {
				setMessage(check);
				validityBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}
