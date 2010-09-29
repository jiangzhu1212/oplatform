package com.risetek.operation.platform.base.client.dialog;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.client.ui.RisetekDateBox;
import com.risetek.operation.platform.launch.client.util.Util;

public class NoticeAddDialog extends CustomDialog {

	TextArea notice = new TextArea();
	VerticalPanel rolePanel = new VerticalPanel();
	RisetekDateBox effect = new RisetekDateBox(UIConfig.DATE_TIME_FORMAT, true, 4);
	RisetekDateBox failure = new RisetekDateBox(UIConfig.DATE_TIME_FORMAT, true, 4);
	
	public NoticeAddDialog(RoleConfigData roleData){
		setText("添加通知公告");
		setText("添加通知公告");
		setDescript("添加一条内部通知公告");
		Grid grid = new Grid(4, 2);
		grid.setStyleName("table");
		grid.getColumnFormatter().setWidth(0, "70px");
		grid.getColumnFormatter().setWidth(1, "250px");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.setText(0, 0, " 内容：");
		grid.setWidget(0, 1, notice);
		grid.setText(1, 0, "发送范围：");
		grid.setWidget(1, 1, rolePanel);
		grid.setText(2, 0, "生效时间：");
		grid.setWidget(2, 1, effect);
		grid.setText(3, 0, "失效时间：");
		grid.setWidget(3, 1, failure);
		grid.setWidth("320px");
		mainPanel.add(grid);
		notice.setFocus(true);
		notice.setHeight("60px");
		
		notice.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		
		String[][] data = roleData.getData();
		for(int i=0;i<data.length;i++){
			String[] item = data[i];
			CheckBox role = new CheckBox(item[1]);
			rolePanel.add(role);
			role.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if(!submit.isEnabled()){
						submit.setEnabled(true);
						setMessage("");
					}
				}
			});
		}
		
		effect.getDateBox().addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
		
		failure.getDateBox().addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				if(!submit.isEnabled()){
					submit.setEnabled(true);
					setMessage("");
				}
			}
		});
	}
	
	public void show(){
		submit.setText("添加");
		super.show();
	}
	
	public boolean isValid(){
		String check = Util.isInputStringEmpty(notice.getText());
		if(check!=null){
			setMessage("通知公告内容" + check);
			return false;
		}
		int count = rolePanel.getWidgetCount();
		boolean select = false;
		for(int i=0;i<count;i++){
			CheckBox box = (CheckBox)rolePanel.getWidget(i);
			if(box.getValue()){
				select = true;
				break;
			}
		}
		if(!select){
			setMessage("没有选择发送范围！");
			return false;
		}
		check = Util.isInputStringEmpty(effect.getDateBox().getTextBox().getText());
		if(check!=null){
			setMessage("没有指定生效日期！");
			return false;
		}
		check = Util.isInputStringEmpty(failure.getDateBox().getTextBox().getText());
		if(check!=null){
			setMessage("没有指定失效日期！");
			return false;
		}
		Date et = effect.getDateValue();
		Date ft = failure.getDateValue();
		Date now = new Date();
		if(now.compareTo(et)>0){
			setMessage("生效日期不能早于当前时间！");
			return false;
		}
		
		if(et.after(ft)){
			setMessage("失效日期不能早于生效日期！");
			return false;
		} else {
			if(ft.compareTo(et)<1){
				setMessage("有效日期至少1天！");
				return false;
			}
		}
		return true;
	}
	
	public Notice getValue(){
		Notice nt = new Notice();
		nt.setContent(notice.getText());
		Timestamp time = new Timestamp(effect.getDateValue().getTime());
		nt.setEffectTime(time);
		time = new Timestamp(failure.getDateValue().getTime());
		nt.setFailureTime(time);
		String roles = "";
		int count = rolePanel.getWidgetCount();
		for(int i=0;i<count;i++){
			CheckBox r = (CheckBox)rolePanel.getWidget(i);
			if(r.getValue()){
				roles += r.getText() + ",";
			}
		}
		roles = roles.substring(0, roles.length()-1);
		nt.setRoles(roles);
		return nt;
	}
}
