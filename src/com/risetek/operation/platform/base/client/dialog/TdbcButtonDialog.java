package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.TdbcController;
import com.risetek.operation.platform.base.client.model.TdbcData;
import com.risetek.operation.platform.base.client.view.MyTextBox;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.json.constanst.TdbcConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: TdbcDialog 
 * @Description: 二维码框架事件实体类 
 * @author JZJ 
 * @date 2010-8-30 上午10:49:43 
 * @version 1.0 
 */
public class TdbcButtonDialog extends BaseDialog {  

	public final TextBox TDBC_ID = new MyTextBox() ;
	public final TextBox E_GOODS_SN = new MyTextBox() ;
	public final TextBox IMAGE = new MyTextBox();
	
	public String ACTION_NAME = null ;
	public RequestFactory request = new RequestFactory();
	
	/**
	 * Description: 构造器
	 */
	public TdbcButtonDialog(){
		SubmitButtonClickHandler handler = new SubmitButtonClickHandler();
		submit.addClickHandler(handler);
	}
	
	public void addMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_ADD_TDBC_INFO ;
		gridFrame.resize(2, 2);
		gridFrame.setWidget(0, 0, new Label(TdbcConstanst.E_GOODS_SN_ZH + "："));
		gridFrame.setWidget(0, 1, E_GOODS_SN);
		gridFrame.setWidget(1, 0, new Label(TdbcConstanst.IMAGE_ZH + "："));
		gridFrame.setWidget(1, 1, IMAGE);
		setText("添加二维码信息");
		setDescript("请输入二维码");
		submit.setText("添加");
		mainPanel.add(gridFrame);
		show();
	}
	
	public void queryMainPanel(){
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_TDBC_INFO ;
		gridFrame.resize(2, 2);
		gridFrame.setWidget(0, 0, new Label(TdbcConstanst.TDBC_ID_ZH + "："));
		gridFrame.setWidget(0, 1, TDBC_ID);
		gridFrame.setWidget(1, 0, new Label(TdbcConstanst.E_GOODS_SN_ZH + "："));
		gridFrame.setWidget(1, 1, E_GOODS_SN);
		setText("查询二维码信息");
		setDescript("请输入二维码");
		submit.setText("查询");
		mainPanel.add(gridFrame);
		show();
	}

	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		super.show();
		E_GOODS_SN.setFocus(true);
	}

	public class SubmitButtonClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String tdbc_id = TDBC_ID.getText() ;
			String e_goods_sn = E_GOODS_SN.getText() ;
			String image = IMAGE.getText() ;
			
			TdbcData tdbcData = new TdbcData() ;
			tdbcData.setACTION_NAME(ACTION_NAME);
			if(tdbc_id != null && !"".equals(tdbc_id.trim())){
				if(!Util.isNum(tdbc_id)){
					setMessage("二维码索引必须是数字") ;
					return ;
				}
				tdbcData.setTdbc_id(Integer.parseInt(tdbc_id));
			}
			if(e_goods_sn != null && !"".equals(e_goods_sn.trim())){
				if(!Util.isNum(e_goods_sn)){
					setMessage("货物编号必须是数字") ;
					return ;
				}
				tdbcData.setE_goods_sn(Integer.parseInt(e_goods_sn));
			}
			tdbcData.setImage(image);
			
			if(Constanst.ACTION_NAME_ADD_TDBC_INFO.equals(ACTION_NAME)){
				String packet = tdbcData.toHttpPacket();
				request.getBill(packet, TdbcController.RemoteCaller);
			}else if(Constanst.ACTION_NAME_ADD_TDBC_INFO.equals(ACTION_NAME)){
				TdbcController.queryData = tdbcData ;
				String packet = tdbcData.toHttpPacket();
				request.getBill(packet, TdbcController.QueryCaller);
			}
			
			hide();
		}
	}
}
