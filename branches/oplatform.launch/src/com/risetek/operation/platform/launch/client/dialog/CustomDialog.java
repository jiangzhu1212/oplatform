package com.risetek.operation.platform.launch.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CustomDialog extends DialogBox {

	private static CustomDialogUiBinder uiBinder = GWT
			.create(CustomDialogUiBinder.class);

	interface CustomDialogUiBinder extends UiBinder<Widget, CustomDialog> {
	}

	@UiField protected Label label;

	@UiField protected VerticalPanel mainPanel;
	
	@UiField protected Label message;
	
	@UiField public Button submit;
	
	@UiField Button cancel;
	
	public CustomDialog() {
		setWidget(uiBinder.createAndBindUi(this));
		setGlassEnabled(true);
		submit.setStyleName("dialogButton");
		cancel.setStyleName("dialogButton");
		submit.setTabIndex(1001);
		cancel.setTabIndex(1002);
		setStyleName("dialog");
		label.setStyleName("description");
		mainPanel.setStyleName("main");
		message.setStyleName("errmsg");
	}
	
	protected void setDescript(String desc) {
		label.setText(desc);
	}
	
	protected void setMessage(String msg) {
		message.setText(msg);
	}

	public void show(){
		super.show();
		center();
	}
	
	@UiHandler("cancel")
	void onClickCancel(ClickEvent e){
		hide();
	}
	
	public boolean onKeyDownPreview(char key, int modifiers){
		return true;
	}
}