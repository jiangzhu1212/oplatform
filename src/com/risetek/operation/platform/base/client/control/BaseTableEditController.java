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

public abstract class BaseTableEditController implements ClickHandler {
	
	private String actionName = "编辑表格";
	public ViewEditControl edit_control = new ViewEditControl();
	protected EditDialog dialog = edit_control.getDialog();
	protected Grid grid = null;
	String colName = null ;
	String optionName = null ;
	public BaseTableEditController() {
		edit_control.setColName(null);	
		edit_control.dialog.submit.addClickHandler(edit_control);
	}
	public String getActionName(){
		return actionName;
	}
	
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
		int col = Util.getColumNum(grid, colName);
        
		// 我们的操作都针对这个号码。
		String rowid = "" + row ;

		String tisp_value = grid.getText(row, col);
		if(tisp_value.length() == 1){
			int tvalue = (int)tisp_value.charAt(0);
			if(tvalue == 160){
				tisp_value = "";
			}
		}
		
		edit_control.setColName(colName);	
		edit_control.dialog.submit.setText(optionName);
		edit_control.dialog.show(rowid, tisp_value);
						
	}
	
	public class ViewEditControl extends EditController implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			submintHandler();			
		}
			
	}
	
	public abstract void setGrid();
	
	public abstract void submintHandler();
 }
