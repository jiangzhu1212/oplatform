package com.risetek.operation.platform.base.client.dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: Card007Dialog 
 * @Description: 全国充值 （007）事件实体类 
 * @author JZJ 
 * @date 2010-9-6 上午09:17:05 
 * @version 1.0
 */
public class Card007Dialog extends BaseDialog { 
	
	public DateBox dateBox = new DateBox();
	public String timeValue = "";
	
	public Card007Dialog(Grid grid, int row){
		createMainPanel(grid , row);
	}
	
	/**
	 * @Description: 创建充值面板
	 * @param grid
	 * @param row  参数 
	 * @return void 返回类型
	 */
	public void createMainPanel(Grid grid, int row) {
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
		hp.add(createListBox());
		gridFrame.setWidget(i, 0, new Label("充值日期："));
		gridFrame.setWidget(i, 1, hp);
		gridFrame.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		dateBox.setFormat(new DateBox.DefaultFormat(BaseDialog.format));
		dateBox.setWidth("80px");
		createListBox().setWidth("70px");
		gridFrame.setWidth("350px");
		setText("全国充值");
		setDescript("请输入充值时间");
		mainPanel.add(gridFrame);
	}
	
	/**
	 * @Description: 创建时间选择下拉框
	 * @return  参数 
	 * @return ListBox 返回类型
	 */
	@SuppressWarnings("rawtypes")
	private ListBox createListBox() {
		final ListBox timeBox = new ListBox();
		final List list = getTime();
		for (Object obj : list) {
			timeBox.addItem((obj.toString()));
		}
		
		timeBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = timeBox.getSelectedIndex();
				timeValue = (list.get(index)).toString();
				Window.alert(timeValue);
			}
		});
		return timeBox;
	}
	
	/**
	 * @Description: 组合时间数据
	 * @return  参数 
	 * @return List 返回类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getTime() {
		List time = new ArrayList();
		time.add("");
		for (int h = 1; h <= 12; h++) {
			time.add(h + ":00");
			for (int m = 15; m <= 45; m += 15) {
				time.add(h + ":" + m);
			}
		}
		return time;
	}
	
	/**
	 * @Description: 返回需要发送的JSON数据
	 * @param grid
	 * @param row
	 * @return  参数 
	 * @return BillCard007 返回类型
	 */
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
	
	public String getChargeDateTime(){
		String dateTime = "";
		Date date = dateBox.getValue();
		if(null != date && null != timeValue && !"".equals(timeValue)){
			dateTime = Util.formatDateToJsonString(dateBox.getValue()) + Util.formatHmStringTime(timeValue);
		}
		return dateTime;
	}
	
	/**
	 * @Description:  验证
	 * @return  参数 
	 * @return boolean 返回类型
	 */
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
