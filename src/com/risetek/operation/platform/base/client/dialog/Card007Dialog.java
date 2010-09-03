package com.risetek.operation.platform.base.client.dialog;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.launch.client.json.constanst.BillCard007;

/**
 * @author asus
 *
 */
public class Card007Dialog extends BaseDialog { 
	
	public final static DateBox dateBox = new DateBox();
	private final ListBox timeBox = new ListBox();
	public static String timeValue = "";
	private final static String[] timeVale = { "", "12:00", "12:15", "12:30",
			"12:45", "1:00", "1:15", "1:30", "1:45", "2:00", "2:15", "2:30",
			"2:45", "3:00", "3:15", "3:30", "3:45", "4:00", "4:15", "4:30",
			"4:45", "5:00", "5:15", "5:30", "5:45", "6:00", "6:15", "6:30",
			"6:45", "7:00", "7:15", "7:30", "7:45", "8:00", "8:15", "8:30",
			"8:45", "9:00", "9:15", "9:30", "9:45", "10:00", "10:15", "10:30",
			"10:45", "11:00", "11:15", "11:30", "11:45" };
	
	public Card007Dialog(){}
	
	public Card007Dialog(Grid grid, int row){
		makeMainPanel(grid , row);
		createListBox();
	}
	
	public void makeMainPanel(Grid grid, int row) {
		int colCount = grid.getColumnCount();
		if (colCount < 3) {
			Window.alert("错误");
		}
		
		gridFrame.resize(colCount-1, 2);
		int i = 0;
		for (; i < colCount - 2; i++) {
			gridFrame.setWidget(i, 0, new Label(grid.getText(0, i + 2) + "："));
			gridFrame.setWidget(i, 1, new Label(grid.getText(row, i + 2)));
			gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(dateBox);
		hp.add(timeBox);
		gridFrame.setWidget(i, 0, new Label("充值时间："));
		gridFrame.setWidget(i, 1, hp);
		gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		dateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		dateBox.setValue(new Date());
		dateBox.setWidth("80px");
		timeBox.setWidth("70px");
		gridFrame.setWidth("350px");
		setText("全国充值");
		setDescript("请输入充值时间");
		mainPanel.add(gridFrame);
	}
	
	
	private void createListBox(){
		for(int i=0;i<timeVale.length;i++){
			timeBox.addItem(timeVale[i]);
		}
		timeBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = timeBox.getSelectedIndex();
				timeValue = timeVale[index];
			}
		});
	}
	
	public BillCard007 getData(Grid grid, int row){
		BillCard007 card = new BillCard007();
		card.setCHARGE_PHONE_NUMBER(grid.getText(row, 2));
		card.setBILL_EXTERN_ID(grid.getText(row, 3));
		card.setAmount(grid.getText(row, 4));
		card.setSTATUS(Integer.parseInt(grid.getText(row, 5)));
		card.setDatetime(grid.getText(row, 6));
		card.setRetInfo(grid.getText(row, 7));
		card.setPayState(grid.getText(row, 8));
		return card;
	}
	
	public boolean isValid() {
		String date = dateBox.getTextBox().getText().trim();
		if(null == date || "".equals(date)){
			setMessage("请输入充值日期!");
			return false;
		}
		if(null == timeValue || "".equals(timeValue)){
			setMessage("请输入充值时间!");
			return false;
		}
		return true;
	}
}
