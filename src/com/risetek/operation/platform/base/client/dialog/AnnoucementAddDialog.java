package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
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
	public final DateBox validityBox = new DateBox();
	public final TextBox descBox = new TextBox();
		
	/**
	 * Description: 构造器
	 */
	public AnnoucementAddDialog(){
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
		
		typeBox.setWidth("240px");
		dateBox.setWidth("240px");
		addtionBox.setWidth("240px");
		stopTimeBox.setWidth("240px");
		TARGET_TYPE_Box.setWidth("240px");
		TARGET_ID_Box.setWidth("240px");
		validityBox.setWidth("240px");
		descBox.setWidth("240px");
		
		mainPanel.add(gridFrame);
	}
	
	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		setText("增加公告信息");
		setDescript("请输入新的公告信息");
		super.show();
		typeBox.setFocus(true);
	}

	/**
	 * @Description: 验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {

		return true;
	}

}
