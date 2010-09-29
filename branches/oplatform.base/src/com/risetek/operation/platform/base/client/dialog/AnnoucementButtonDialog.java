package com.risetek.operation.platform.base.client.dialog;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.risetek.operation.platform.base.client.control.AnnoucementController;
import com.risetek.operation.platform.base.client.model.AnnoucementData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.AnnoucementConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

/**
 * @ClassName: AnnoucementAddDialog 
 * @Description: 增公告信息事件实体类 
 * @author JZJ 
 * @date 2010-8-27 上午10:52:43 
 * @version 1.0
 */
public class AnnoucementButtonDialog extends BaseDialog {    
	
	public final TextBox ACE_ID = new TextBox();
	public final TextBox TYPE = new TextBox();
	public final TextBox DATA = new TextBox();
	public final TextBox ADDTION = new TextBox();

	public final TextBox TARGET_TYPE = new TextBox();
	public final TextBox TARGET_ID = new TextBox();
	public final TextBox DESCRIPTION = new TextBox();
	
	public final ListBox VALIDITY = Util.getValidity() ;
	
	private final DateBox START_DATE = new DateBox();
	private final DateBox END_DATE = new DateBox();
	
	private final DateBox CREATE_STARTTIME = new DateBox();
	private final DateBox CREATE_ENDTIME = new DateBox();
	
	private final DateBox STOP_STARTTIME = new DateBox();
	private final DateBox STOP_ENDTIME = new DateBox();
	
	private String ACTION_NAME = null;
	
	RequestFactory factory = new RequestFactory();
			
	private String[] searchColumns = {
			AnnoucementConstanst.ACE_ID_ZH, 
			AnnoucementConstanst.TYPE_ZH, 
			AnnoucementConstanst.DATA_ZH,
			AnnoucementConstanst.VALIDITY_ZH,
			AnnoucementConstanst.CREATE_TIME_MIN_ZH,
			AnnoucementConstanst.CREATE_TIME_MAX_ZH,
			AnnoucementConstanst.STOP_TIME_MIN_ZH,
			AnnoucementConstanst.STOP_TIME_MAX_ZH
	};

	private String[] addColumns = { 
		AnnoucementConstanst.TYPE_ZH,
		AnnoucementConstanst.DATA_ZH,
		AnnoucementConstanst.ADDITION_ZH,
		AnnoucementConstanst.CREATE_TIME_ZH,
		AnnoucementConstanst.STOP_TIME_ZH,
		AnnoucementConstanst.TARGET_TYPE_ZH,
		AnnoucementConstanst.TARGET_ID_ZH,
		AnnoucementConstanst.DESCRIPTION_ZH,
		AnnoucementConstanst.VALIDITY_ZH 
	};

	/**
	 * Description: 构造器
	 */
	public AnnoucementButtonDialog(){
		ClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
		
	/**
	 * @Description: 创建添加组件 
	 * @return Grid 返回类型
	 */
	public void addMainPanel(){	
		ACTION_NAME = Constanst.ACTION_NAME_ADD_ANNOUCEMENT_INFO ;
		gridFrame.resize(9, 2);
		gridFrame.setStyleName("table");
		formatGrid(gridFrame, addColumns);
		gridFrame.setWidget(0,1,TYPE);
		gridFrame.setWidget(1,1,DATA);
		gridFrame.setWidget(2,1,ADDTION);
		gridFrame.setWidget(3,1,START_DATE);
		gridFrame.setWidget(4,1,END_DATE);
		gridFrame.setWidget(5,1,TARGET_TYPE);
		gridFrame.setWidget(6,1,TARGET_ID);
		gridFrame.setWidget(7,1,DESCRIPTION);
		gridFrame.setWidget(8,1,VALIDITY);
		setText("增加公告信息");
		setDescript("请输入新的公告信息");
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	/**
	 * @Description: 创建查询组件
	 * @return VerticalPanel 返回类型
	 */
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_ANNOUCEMENT_INFO ;
		gridFrame.resize(8, 2);
		gridFrame.setStyleName("table");
		formatGrid(gridFrame, searchColumns);
		gridFrame.setWidget(0,1,ACE_ID);
		gridFrame.setWidget(1,1,TYPE);
		gridFrame.setWidget(2,1,DATA);
		gridFrame.setWidget(3,1,VALIDITY);
		gridFrame.setWidget(4,1,CREATE_STARTTIME);
		gridFrame.setWidget(5,1,CREATE_ENDTIME);
		gridFrame.setWidget(6,1,STOP_STARTTIME);
		gridFrame.setWidget(7,1,STOP_ENDTIME);
		setText("查询公告信息");
		setDescript("请输入查询信息");
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}
	
	
	public void show(){
		super.show();
		TYPE.setFocus(true);
	}
	
	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {

			String id = ACE_ID.getText();
			String type = TYPE.getText();
			String data = DATA.getText();
			String addtion = ADDTION.getText();
			String target_type = TARGET_TYPE.getText();
			String target_id = TARGET_ID.getText();
			String description = DESCRIPTION.getText();
			
			int validityIndext = VALIDITY.getSelectedIndex() ;
			String validity = VALIDITY.getValue(validityIndext) ;	
			
			AnnoucementData annoucementData = new AnnoucementData();
			annoucementData.setACTION_NAME(ACTION_NAME);
			annoucementData.setType(type);
			annoucementData.setAce_data(data);
			annoucementData.setAddtion(addtion);
			annoucementData.setTarget_type(target_type);
			annoucementData.setTarget_id(target_id);
			annoucementData.setDescription(description);
			annoucementData.setValidity(validity);			
			
			if(Constanst.ACTION_NAME_ADD_ANNOUCEMENT_INFO.equals(ACTION_NAME)){
				if(type == null || "".equals(type.trim())){
					setMessage("公告类型不能为空");
					return ;
				}
				Date start_time = START_DATE.getValue();
				String start_date = Util.formatDateToJsonString(start_time);
				
				Date end_time = END_DATE.getValue();
				String end_date = Util.formatDateToJsonString(end_time);		
				if(!Util.checkStringBulk(start_date, end_date)){
					setMessage("公告创建时间不能超过停止时间");
					return ;
				}
				annoucementData.setCreate_time(start_date);
				annoucementData.setStop_time(end_date);
				String jsonStr = annoucementData.toHttpPacket();
				EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
 				String json = EPay2Packet.listToString(epay2Packet);
 				String packet = RequestFactory.PACKET + "="+ json ;
				factory.getBill(packet, AnnoucementController.RemoteCaller);
				hide() ;
			}else if(Constanst.ACTION_NAME_QUERY_ANNOUCEMENT_INFO.equals(ACTION_NAME)){				
				if(id != null && !"".equals(id.trim())){
					if(!Util.isNum(id)){
						setMessage("公告索引必须为数字");
						return ;
					}
					annoucementData.setAce_id(Integer.parseInt(id));
				}
				String create_startTime = Util.formatDateToJsonString(CREATE_STARTTIME.getValue());
				String create_endTime = Util.formatDateToJsonString(CREATE_ENDTIME.getValue());
				if(!Util.checkStringBulk(create_startTime, create_endTime)){
					setMessage("公告创建时间最小值不能大于最大值");
					return ;
				}
				annoucementData.setCreate_time_min(create_startTime);
				annoucementData.setCreate_time_max(create_endTime);
				
				String stop_starttime = Util.formatDateToJsonString(STOP_STARTTIME.getValue());
				String stop_endtime = Util.formatDateToJsonString(STOP_ENDTIME.getValue());
				if(!Util.checkStringBulk(stop_starttime, stop_endtime)){
					setMessage("公告结束时间最小值不能大于最大值");
					return ;
				}
				annoucementData.setStop_time_min(stop_starttime);
				annoucementData.setStop_time_max(stop_endtime);
				
				AnnoucementController.queryData = annoucementData ;
				String jsonStr = annoucementData.toHttpPacket();
				EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
 				String json = EPay2Packet.listToString(epay2Packet);
 				String packet = RequestFactory.PACKET + "="+ json ;
				factory.getBill(packet, AnnoucementController.RemoteCaller);
				hide() ;
			}
			
		}
	}
}
