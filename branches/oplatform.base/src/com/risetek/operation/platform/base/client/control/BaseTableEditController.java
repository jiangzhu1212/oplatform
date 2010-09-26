package com.risetek.operation.platform.base.client.control;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.base.client.dialog.EditDialog;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * 
 * @author 杨彬
 * 2010-9-21
 * 修改操作的基本弹出窗口
 */
public abstract class BaseTableEditController implements ClickHandler {
	
	private String actionName = "编辑表格";
	EditDialog dialog = null ;
	protected Grid grid = null;
	String colName = null ;
	String optionName = null ;
	ViewEditControl edit_control =  null ;
	public BaseTableEditController() {
		
	}
	public String getActionName(){
		return actionName;
	}
	/**
	 * 显示修改窗口
	 */
	public void onClick(ClickEvent event) {
		
		setGrid();
		Button button = (Button)event.getSource();
		String buttonName = button.getText();
		optionName = buttonName.substring(0,2);
		if(Constanst.EDIT_ZH.equals(optionName)){
			colName = buttonName.substring(2);
		}
		List<Integer> rowList = Util.getCheckedRow(grid);
		if(rowList.size() != 1){
			Window.alert("请选择一行数据执行操作");
			return ;
		}
		int row = rowList.get(0);
		int col = 1 ;
		if(colName != null){
			col = Util.getColumNum(grid, colName);
		}
        
		// 我们的操作都针对这个号码。
		String rowid = "" + row ;

		String tisp_value = grid.getText(row, col);
		if(tisp_value.length() == 1){
			int tvalue = (int)tisp_value.charAt(0);
			if(tvalue == 160){
				tisp_value = "";
			}
		}
		if(edit_control == null){
			edit_control = new ViewEditControl(colName , optionName);
		}
		dialog = edit_control.dialog;

		dialog.show(rowid, tisp_value);
						
	}
	/**
	 * 
	 * @author 杨彬
	 * 2010-9-21
	 * 提交修改窗口的监听
	 */
	public class ViewEditControl implements ClickHandler {
		
		protected EditDialog dialog = null;
		public ViewEditControl(String colName , String submitText) {
			dialog = new EditDialog(colName , submitText);
			dialog.submit.addClickHandler(this);
		}

		@Override
		public void onClick(ClickEvent event) {
			submintHandler();			
		}
			
	}
	
	public abstract void setGrid();
	
	/**
	 * 填写完成修改操作的监听事件
	 */
	public abstract void submintHandler();
 }
