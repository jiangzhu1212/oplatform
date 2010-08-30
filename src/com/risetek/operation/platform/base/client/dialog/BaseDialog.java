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
public class BaseDialog extends CustomDialog implements ChangeHandler { 
	
	protected Label oldValueLabel = new Label();

	public TextBox newValueBox = new TextBox();

	protected ListBox listBox = new ListBox();

	protected Grid gridFrame = new Grid();

	protected static String[] bankListBoxValue = {"有效", "无效", "已注销", "已挂失"};
	
	protected static String[] AnnouceListBoxValue = {"有效", "无效"};

	protected static DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	public String validityValue = "有效";	
			
	protected String colName;
	
	protected boolean isSearch;// true 表示查询，false 表示增加

	private boolean flag;//true 表示查询，false 表示增加
	
	public BaseDialog(){
		
	}
	
	/**
	 * Description: 根据传入的标志初始化下拉框
	 * @param flag
	 */
	public BaseDialog(boolean flag){
		this.flag = flag;
		listBox.addChangeHandler(this);
		
		if(flag){
			for (int i = 0; i < bankListBoxValue.length; i++) {
				listBox.addItem(bankListBoxValue[i]);
			}
		}else{
			for (int i = 0; i < AnnouceListBoxValue.length; i++) {
				listBox.addItem(AnnouceListBoxValue[i]);
			}
		}
	}
	
	/** 
	 * (非 Javadoc) 
	 * Description: 获取下拉框时值
	 * @param event 
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent) 
	 */ 
	@Override
	public void onChange(ChangeEvent event) {
		int index = listBox.getSelectedIndex();
		if(flag){
			validityValue = bankListBoxValue[index];
		}else{
			validityValue = AnnouceListBoxValue[index];
		}
	}
	
	/**
	 * @Description: 格式化grid并居中显示
	 * @param gridFrame
	 * @param columns  参数 
	 * @return void 返回类型
	 */
	protected static void formatGrid(Grid gridFrame, String[] columns){
		for (int i = 0; i < gridFrame.getRowCount(); i++) {
			gridFrame.setText(i,0,columns[i] + "：");
			gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}
}
