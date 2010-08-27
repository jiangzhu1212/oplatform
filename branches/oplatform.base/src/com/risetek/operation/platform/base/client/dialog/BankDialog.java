package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.constanst.BankConstanst;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: BankAddDialog 
 * @Description: 增加发卡行信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:16:14 
 * @version 1.0
 */
public class BankDialog extends CustomDialog {
	
	public final TextBox bankCodeBox = new TextBox();
	public final TextBox bankNameBox = new TextBox();
	public final DateBox validityBox = new DateBox();
	public final TextBox descBox = new TextBox();
	private final DateBox startDateBox = new DateBox();
	private final DateBox endDateBox = new DateBox();
	
	DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 

	private Grid gridFrame = new Grid();
	
	/**
	 * Description: 构造器
	 */
	public BankDialog(String tag){
		gridFrame.setStyleName("notice");
		if(null != tag){
			gridFrame.resize(2, 2);
			mainPanel.add(searchBank());
		}else{
			gridFrame.resize(4, 2);
			mainPanel.add(addBank());
		}
		initWith();
	}
	/**
	 * @Description: 创建添加组件 
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid addBank(){		
		gridFrame.setWidget(0,0,new Label(BankConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(1,0,new Label(BankConstanst.BANK_NAME_ZH + "："));
		gridFrame.setWidget(2,0,new Label(BankConstanst.BANK_VALIDITY_ZH + "："));
		gridFrame.setWidget(3,0,new Label(BankConstanst.BANK_DESCRIPTION_ZH + "："));
		
		validityBox.setFormat(new DateBox.DefaultFormat(format));
		
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		gridFrame.setWidget(2,1,validityBox);
		gridFrame.setWidget(3,1,descBox);
		
		return gridFrame;
	}
	/**
	 * @Description: 创建查询组件
	 * @return  参数 
	 * @return VerticalPanel 返回类型
	 */
	private VerticalPanel searchBank(){
		gridFrame.setWidget(0,0,new Label(BankConstanst.BANK_CODE_ZH + "："));
		gridFrame.setWidget(1,0,new Label(BankConstanst.BANK_NAME_ZH + "："));
		
		gridFrame.setWidget(0,1,bankCodeBox);
		gridFrame.setWidget(1,1,bankNameBox);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		
		startDateBox.setFormat(new DateBox.DefaultFormat(format));
		endDateBox.setFormat(new DateBox.DefaultFormat(format));
		
		Grid validityGrid = new Grid(2, 2);
		validityGrid.setStyleName("notice");
		validityGrid.setWidget(0, 0, new Label("开始日期："));
		validityGrid.setWidget(0, 1, startDateBox);
		validityGrid.setWidget(1, 0, new Label("结束日期："));
		validityGrid.setWidget(1, 1, endDateBox);
		
	    DisclosurePanel advancedDisclosure = new DisclosurePanel(BankConstanst.BANK_VALIDITY_ZH);
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
		bankNameBox.setWidth("240px");
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
			setText("查询发卡行信息");
			setDescript("请输入查询信息");
		}else{
			setText("增加发卡行信息");
			setDescript("请输入新的发卡行信息");
		}
		super.show();
		bankCodeBox.setFocus(true);
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
			check = Util.commValidity((bankCodeBox.getText()).trim(), BankConstanst.BANK_CODE_ZH);
			if (null != check) {
				setMessage(check);
				bankCodeBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((bankNameBox.getText()).trim(), BankConstanst.BANK_NAME_ZH);
			if (null != check) {
				setMessage(check);
				bankNameBox.setFocus(true);
				return false;
			}
			check = Util.commValidity((validityBox.getTextBox().getText()).trim(), BankConstanst.BANK_VALIDITY_ZH);
			if (null != check) {
				setMessage(check);
				validityBox.setFocus(true);
				return false;
			}
		}
		return true;
	}
}