package com.risetek.operation.platform.launch.client.sink;

/**
 * @author Amber
 * 功能：模块属性框架
 * 2010-8-23 下午11:29:05
 */
public abstract class SinkInfo {

	public Sink instance;
	
	private String name;	//模块名称，显示在菜单树中的字段
	private String desc;	//模块描述
	private String tag;		//模块表示，会出现在历史记录中
	private String group;	//模块所属组，用于归类在菜单节点中
	
	public SinkInfo(String group, String tag, String name, String desc){
		this.group = group;
		this.tag = tag;
		this.name = name;
		this.desc = desc;
	}
	
	/**
	 * 功能：创建或获取模块框架
	 *
	 * Sink
	 * @return
	 */
	public abstract Sink createInstance();
	
	/**
	 * 功能：获取模块框架
	 *
	 * Sink
	 * @return
	 */
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
}
