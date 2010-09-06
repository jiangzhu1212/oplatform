package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Label;

public class PageLabel extends Label {

	private boolean enable = true;
	
	public PageLabel(String text, boolean enable){
		this(text);
		this.enable = enable;
	}
	
	public PageLabel(String text){
		super(text);
		setWidth("15px");
		setStyleName("pagelabel");
		addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				removeStyleName("active");
			}
		});
		addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				addStyleName("active");
			}
		});
	}
	
	public void setEnable(boolean enable){
		this.enable = enable;
		if(enable){
			setStyleName("pagelabel");
		} else {
			setStyleName("pagelabel-disabled");
		}
	}
	
	public boolean isEnable(){
		return enable;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ClickEvent){
			if(enable){
				super.fireEvent(event);
			}
		} else {
			super.fireEvent(event);
		}
	}
	
	
}
