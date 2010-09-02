package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * @author asus
 *
 */
public class Card007Dialog extends BaseDialog { 
	public static Card007Dialog dialog = new Card007Dialog();
	public final DateBox dateBox = new DateBox();
	public final DateBox timeBox = new DateBox();
	
	public Card007Dialog(){}
	
	public Card007Dialog(String processTag, Grid grid, int row){
		if(null == processTag){
			makeMainPanel(grid , row);
		}
	}
	
	public void makeMainPanel(Grid grid, int row) {
		int colCount = grid.getColumnCount();
		if (colCount < 3) {
			Window.alert("错误");
		}
		
		gridFrame.resize(colCount, 2);
		int i = 0;
		for (; i < colCount - 2; i++) {
			gridFrame.setWidget(i, 0, new Label(grid.getText(0, i + 2) + "："));
			gridFrame.setWidget(i, 1, new Label(grid.getText(row, i + 2)));
			gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		gridFrame.setWidget(i, 0, new Label("充值日期："));
		gridFrame.setWidget(i, 1, dateBox);
		gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		gridFrame.setWidget(i + 1, 0, new Label("充值时间："));
		gridFrame.setWidget(i + 1, 1, timeBox);
		gridFrame.getCellFormatter().setHorizontalAlignment(i + 1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		dateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		timeBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		setText("全国充值");
		setDescript("请输入全国充值的时间");
		mainPanel.add(gridFrame);
	}
	
	public boolean isValid() {
		String date = dateBox.getTextBox().getText().trim();
		String time = timeBox.getTextBox().getText().trim();
		if(null == date || "".equals(date)){
			setMessage("请输入充值日期!");
			return false;
		}else if(null == time || "".equals(time)){
			setMessage("请输入充值时间!");
			return false;
		}
		return true;
	}
}
