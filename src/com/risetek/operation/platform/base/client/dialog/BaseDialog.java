package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/** 
 * @ClassName: BaseDialog 
 * @Description: Dialog公共类 
 * @author JZJ 
 * @date 2010-8-30 下午03:49:57 
 * @version 1.0 
 */
public class BaseDialog extends CustomDialog { 
	
	protected static DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");

	protected Label oldValueLabel = new Label();

	public TextBox newValueBox = new TextBox();

	protected Grid gridFrame = new Grid();
	
	protected boolean isSearch;// true 表示查询，false 表示增加
	
	protected String colName; //列名

	protected static final String[] bankListBoxValue = {"有效", "无效", "已注销", "已挂失"};
	
	protected static final String[] AnnouceListBoxValue = {"有效", "无效"};

	protected static final String styleName = "notice";//CSS
	
	public String validityValue = "有效";	
			
	
	public BaseDialog() {
		gridFrame.setStyleName(styleName);	
	}
	
	/**
	 * @Description: 创建ListBox 
	 * @param flag  (true 表示银行相关的ListBox, false表示公告相关ListBox )
	 * @return ListBox 返回类型
	 */
	protected ListBox createListBox(final String[] boxValue) {
		final ListBox listBox = new ListBox();
		for (int i = 0; i < boxValue.length; i++) {
			listBox.addItem(boxValue[i]);
		}

		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = listBox.getSelectedIndex();
				validityValue = boxValue[index];
			}
		});
		return listBox;
	}
	
	/**
	 * @Description: 格式化grid并居中显示
	 * @param gridFrame
	 * @param columns  参数 
	 * @return void 返回类型
	 */
	protected static void formatGrid(Grid gridFrame, String[] columns){
		for (int i = 0; i < gridFrame.getRowCount(); i++) {
			gridFrame.setText(i, 0, columns[i] + "：");
			gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}
}
