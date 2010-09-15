package com.risetek.operation.platform.launch.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoadingDialog extends DialogBox {

	private static LoadingDialogUiBinder uiBinder = GWT
			.create(LoadingDialogUiBinder.class);

	interface LoadingDialogUiBinder extends UiBinder<Widget, LoadingDialog> {
	}

	@UiField
	protected Label label;

	@UiField
	protected VerticalPanel mainPanel;

	private int i = 0;
	private String cont = "";
	private Timer refresh;

	public LoadingDialog(String value) {
		setWidget(uiBinder.createAndBindUi(this));
		setGlassEnabled(true);
		setSize("160px", "60px");
		setStyleName("dialog");
		center();
		label.setStyleName("description");
		mainPanel.setStyleName("main");
		cont = value;
		setText("请稍后");
		final HTML content = new HTML(value);
		refresh = new Timer() {
			public void run() {
				if (i < 10) {
					cont += ".";
					i++;
				} else {
					i = 0;
					cont = cont.substring(0, cont.length() - 10);
				}
				content.setHTML(cont);
			}
		};
		refresh.schedule(300);
		refresh.scheduleRepeating(300);
		content.setWidth("120px");
		mainPanel.add(content);
		mainPanel.setCellHorizontalAlignment(content,
				HasHorizontalAlignment.ALIGN_LEFT);
	}

	public void run() {
		super.show();
		refresh.run();
	}

	public void cancel() {
		super.hide();
		refresh.cancel();
	}
}
