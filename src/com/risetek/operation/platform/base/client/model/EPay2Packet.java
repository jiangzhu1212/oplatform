package com.risetek.operation.platform.base.client.model;



import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;



/**
 * epay2系统基础通信报文结构: <br>
 * +-key-------------------|value---------+<br>
 * |ACTION_NAME------------|指令名---------|<br>
 * |ACTION_MODULE----------|接收指令模块---|<br>
 * |ACTION_INVOKER---------|指令发起者-----|<br>
 * |ACTION_INFO------------|指令参数-------|<br>
 * |ACTION_RETURN_CODE-----|接口执行结果---|<br>
 * +----------------------------------+
 * 
 * @author majun
 * 
 */
public class EPay2Packet {

	private static final String ACTION_RETURN_MESSAGE = "ACTION_RETURN_MESSAGE";

	private static final String ACTION_RETURN_CODE = "ACTION_RETURN_CODE";

	private static final String ACTION_INFO = "ACTION_INFO";

	private static final String ACTION_MODULE = "ACTION_MODULE";

	private static final String ACTION_INVOKER = "ACTION_INVOKER";

	private static final String ACTION_NAME = "ACTION_NAME";

	/**
	 * 调用的接口名称
	 */
	protected String actionName = "";

	/**
	 * 调用接口者ID
	 */
	protected String actionInvoker = "";

	/**
	 * 被调用的模块名称
	 */
	protected String actionModule = "";

	/**
	 * 接口执行结果
	 */
	protected int actionReturnCode = 0;

	/**
	 * 接口执行结果说明
	 */
	protected String actionReturnMessage = "";

	/**
	 * 接口参数
	 */
	protected String actionInfo = "";
	
	/**
	 * 根据字符串生成包列表
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static List<EPay2Packet> listfromString(String jsonStr) throws JSONException {
		List<EPay2Packet> lst = new ArrayList<EPay2Packet>();
		JSONArray jsa = JSONParser.parse(jsonStr).isArray();
		
		for (int i = 0; i < jsa.size(); i++) {
			EPay2Packet epp = new EPay2Packet(jsa.get(i).toString());
			lst.add(epp);
		}
		
		return lst;
	}
	
	/**
	 * 根据包列表生成字符串
	 * @param acLst
	 * @return
	 * @throws JSONException 
	 */
	public static String listToString(List<EPay2Packet> acLst) throws JSONException{
		JSONArray jsa = new JSONArray();
		for (int i = 0; i < acLst.size(); i++) {
			EPay2Packet epp = acLst.get(i);
			jsa.set(i,epp.toJsonObject());
		}
		return jsa.toString();
	}
	
	/**
	 *  根据单个指令生成字符串
	 * @param pack
	 * @return
	 * @throws JSONException
	 */
	public static String listToString(EPay2Packet pack) throws JSONException{
		List<EPay2Packet> acLst = new ArrayList<EPay2Packet>();
		acLst.add(pack);
		return listToString(acLst);
	}

	/**
	 * 构造函数
	 */
	public EPay2Packet(){
	}

	/**
	 * 构造函数
	 * 
	 * @param jsonStr
	 *            报文字符串
	 * @throws JSONException
	 * @throws Exception
	 */
	public EPay2Packet(String jsonStr) throws JSONException {
		JSONObject root = JSONParser.parse(jsonStr).isObject();
		actionName = root.get(ACTION_NAME).isString().stringValue();
		actionInvoker = root.get(ACTION_INVOKER).isString().stringValue();
		actionModule = root.get(ACTION_MODULE).isString().stringValue();
		try {
			actionInfo = root.get(ACTION_INFO).isObject().toString();
		} catch (Exception e) {
			actionInfo = root.get(ACTION_INFO).isString().toString();
		}
		try {
			actionReturnCode = Integer.parseInt(root.get(ACTION_RETURN_CODE)
					.isNumber().toString());
		} catch (Exception e) {
			actionReturnCode = -1 ;
		}
		try {
			actionReturnMessage = root.get(ACTION_RETURN_MESSAGE).isString()
					.stringValue();
		} catch (Exception e) {
			actionReturnMessage = "" ;
		}
		handleActionInfo(actionInfo);
	}

	/**
	 * 请求包构造函数
	 * 
	 * @param actionName
	 * @param actionInvoker
	 * @param actionModule
	 * @param actionInfo
	 */
	public EPay2Packet(String actionName, String actionInvoker,
			String actionModule, String actionInfo) {
		this.actionName = actionName;
		this.actionInvoker = actionInvoker;
		this.actionModule = actionModule;
		this.actionInfo = actionInfo;
	}

	/**
	 * 返回报构造函数
	 * 
	 * @param actionName
	 * @param actionInvoker
	 * @param actionModule
	 * @param returnCode
	 * @param returnMsg
	 * @param actionInfo
	 */
	public EPay2Packet(String actionName, String actionInvoker,
			String actionModule, int returnCode, String returnMsg,
			String actionInfo) {
		this.actionName = actionName;
		this.actionInvoker = actionInvoker;
		this.actionModule = actionModule;
		this.actionReturnCode = returnCode;
		this.actionReturnMessage = returnMsg;
		this.actionInfo = actionInfo;
	}
	
	/**
	 * 返回报构造函数
	 * @param actionName
	 * @param actionInvoker
	 * @param actionModule
	 * @param returnCode
	 * @param returnMsg
	 */
	public EPay2Packet(String actionName, String actionInvoker,
			String actionModule, int returnCode, String returnMsg){
		this(actionName, actionInvoker, actionModule, returnCode, returnMsg, "");
	}
	
	/**
	 * 根据请求生成应答构造函数
	 * @param requestPack
	 * @param returnCode
	 * @param returnMsg
	 * @param actionInfo
	 */
	public EPay2Packet(EPay2Packet requestPack, int returnCode, String returnMsg, String actionInfo){
		setPackHead(requestPack);
		setActionReturnCode(returnCode);
		setActionReturnMessage(returnMsg);
		setActionInfo(actionInfo);
	}
	
	/**
	 * 根据请求生成应答构造函数
	 * @param requestPack
	 * @param returnCode
	 * @param returnMsg
	 */
	public EPay2Packet(EPay2Packet requestPack, int returnCode, String returnMsg){
		this(requestPack, returnCode, returnMsg, "");
	}

	/**
	 * 拷贝设置报文头内容，包括ACTION_NAME、ACTION_MODULE和ACTION_INVOKER； 不会涉及ACTION_INFO
	 * 
	 * @param pac
	 */
	public void setPackHead(EPay2Packet pac) {
		actionName = pac.getActionName();
		actionInvoker = pac.getActionInvoker();
		actionModule = pac.getActionModule();
	}

	/**
	 * 转化成JSONObject
	 * @return
	 */
	public JSONObject toJsonObject(){
		JSONObject ret = null;
		try {
			serialize();
			JSONObject root = new JSONObject();
			root.put(ACTION_NAME, new JSONString(actionName));
			root.put(ACTION_INFO, new JSONString(actionInfo));
			root.put(ACTION_MODULE, new JSONString(actionModule));
			root.put(ACTION_INVOKER, new JSONString(actionInvoker));
			root.put(ACTION_RETURN_CODE, new JSONNumber(actionReturnCode));
			root.put(ACTION_RETURN_MESSAGE, new JSONString(actionReturnMessage));
			ret = root;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/**
	 * 序列化包
	 */
	public String toString() {
		JSONObject obj = toJsonObject();
		if (obj == null) {
			return null;
		}else{
			return obj.toString();
		}
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionInvoker() {
		return actionInvoker;
	}

	public void setActionInvoker(String actionInvoker) {
		this.actionInvoker = actionInvoker;
	}

	public String getActionModule() {
		return actionModule;
	}

	public void setActionModule(String actionModule) {
		this.actionModule = actionModule;
	}

	public int getActionReturnCode() {
		return actionReturnCode;
	}

	public void setActionReturnCode(int actionReturnCode) {
		this.actionReturnCode = actionReturnCode;
	}

	public String getActionReturnMessage() {
		return actionReturnMessage;
	}

	public void setActionReturnMessage(String actionReturnMessage) {
		this.actionReturnMessage = actionReturnMessage;
	}

	public String getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
	
	/**
	 * 解析actionInfo的内容，继承以解析action info
	 * 
	 * @param actionInfo
	 * @throws JSONException
	 * @throws Exception
	 */
	protected void handleActionInfo(String actionInfo) throws JSONException {
	}

	/**
	 * 序列化actionInfo，继承以实现action info的构建
	 * 
	 * @throws JSONException
	 * @throws Exception
	 */
	protected void serialize() throws JSONException {
	}
}

