package com.risetek.operation.platform.launch.client.sink;

import com.google.gwt.user.client.ui.Widget;

public abstract class SinkInfo {

	public Sink instance;
	
	private String name;
	private String desc;
	private String tag;
	private String group;
	private Widget widget;
	
	public SinkInfo(String group, String tag, String name, String desc, Widget widget){
		this.group = group;
		this.tag = tag;
		this.name = name;
		this.desc = desc;
		this.widget = widget;
	}
	
	public abstract Sink createInstance();
	
	public final Sink getInstance(){
		if(instance!=null){
			return instance;
		}
		return (instance = createInstance());
	}
	
	public String getDescription(){
		return desc;
	}
	
	public String getName(){
		return name;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getGroup(){
		return group;
	}
	
	public Widget getWidget(){
		return widget;
	}
}
