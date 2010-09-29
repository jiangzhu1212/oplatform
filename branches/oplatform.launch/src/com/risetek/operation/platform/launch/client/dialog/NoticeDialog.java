package com.risetek.operation.platform.launch.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.risetek.operation.platform.launch.client.entry.Notice;

public class NoticeDialog extends CustomDialog {

	public NoticeDialog(Notice[] notice){
		Grid grid = new Grid(notice.length, 2);
		grid.setStyleName("notice");
		grid.getColumnFormatter().setWidth(0, "15px");
		grid.getColumnFormatter().setWidth(1, "285px");
		for(int i=0;i<notice.length;i++){
			grid.setText(i, 0, (i+1) + "、");
			grid.setText(i, 1, notice[i].getContent());
			grid.getCellFormatter().setVerticalAlignment(i, 0, HasVerticalAlignment.ALIGN_TOP);
		}
		grid.setWidth("300px");
		mainPanel.add(grid);
		setText("通知公告");
		setDescript("查看通知公告详细内容");
	}
	
	public void show(){
		cancel.setText("关闭");
		submit.setVisible(false);
		cancel.getElement().getParentElement().setAttribute("align", "center");
		super.show();
	}
}
