package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.view.AnnoucementView;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: AnnoucementModifyDialog 
 * @Description: 管理公告信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午11:01:36 
 * @version 1.0
 */
public class AnnoucementModifyDialog extends BaseDialog {   
	
	public DateBox newDateBox = new DateBox();

	/**
	 * Description: 构造器
	 */
	public AnnoucementModifyDialog(String colName){
		super.colName = colName;
		if(null != colName){
			mainPanel.add(AcountModifyClick(colName));
		}else{
			createDelView();
		}
	}
	
	/**
	 * @Description: 处理修改操作 
	 * @param titleMsg
	 * @return  参数 
	 * @return Grid 返回类型
	 */
	private Grid AcountModifyClick(String titleMsg){
		setDescript("请输入新的"+titleMsg);
		gridFrame.resize(2, 2);
		gridFrame.setWidget(0, 0, new Label("当前"+titleMsg+"："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的"+titleMsg+"："));
		if(titleMsg.equals(AnnoucementView.columns[2]) || titleMsg.equals(AnnoucementView.columns[5])){
			DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd"); 
			newDateBox.setFormat(new DateBox.DefaultFormat(format));
			newDateBox.setWidth("200px");
			gridFrame.setWidget(1, 1, newDateBox);
		}else if(titleMsg.equals(AnnoucementView.columns[8])){
			ListBox listBox = createListBox(AnnouceListBoxValue);
			gridFrame.setWidget(1,1,listBox);
		}else{
			gridFrame.setWidget(1, 1, newValueBox);
		}
		return gridFrame;
	}
	
	/**
	 * @Description: 删除操作 
	 * @return void 返回类型
	 */
	private void createDelView(){
		setDescript("删除选中的公告信息");
		label.setWidth("270px");
		mainPanel.setWidth("260px");
		mainPanel.add(delInfo);
	}
	
	
	/**
	 * @Description: 显示窗体 (修改操作) 
	 * @param tips_id
	 * @param tips_value  参数 
	 * @return void 返回类型
	 */
	public void show(String tips_id, String tips_value) {
		setText("记录序号：" + tips_id);
		if(null != colName){
			oldValueLabel.setText(tips_value);
			newValueBox.setText(tips_value);
			super.show();
			newValueBox.setFocus(true);
		}else{
			delInfo.setText("您确定删除该公告\"" + tips_value + "\"全部内容？");
			super.show();
		}
	}
	
	/**
	 * @Description: 验证 
	 * @return  参数 
	 * @return boolean 返回类型
	 */
	public boolean isValid() {
		//colum为null,表示执行的是删除操作
		if (null == colName) {
			return true;
		}
		
		String check = Util.commValidity((newValueBox.getText()).trim(), "");
		if (null != check) {
			setMessage(check);
			newValueBox.setFocus(true);
			return false;
		}
		return true;
	}
}
