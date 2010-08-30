package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.constanst.TdbcConstanst;
import com.risetek.operation.platform.launch.client.util.Util;

/** 
 * @ClassName: TdbcDialog 
 * @Description: 二维码框架事件实体类 
 * @author JZJ 
 * @date 2010-8-30 上午10:49:43 
 * @version 1.0 
 */
public class TdbcDialog extends BaseDialog {  

	public final TextBox E_GOODS_SN_Box = new TextBox();

	/**
	 * Description: 构造器
	 */
	public TdbcDialog(boolean isSearch){
		super();
		if(isSearch){
			Grid gridFrame = new Grid(1,2);
			gridFrame.setStyleName("notice");
			gridFrame.setWidget(0,0,new Label(TdbcConstanst.E_GOODS_SN_ZH + "："));
			gridFrame.setWidget(0,1,E_GOODS_SN_Box);
			E_GOODS_SN_Box.setWidth("200px");
			mainPanel.add(gridFrame);
		}
	}
	

	/**
	 * (非 Javadoc) 
	 * Description:  显示窗体 
	 * @see com.risetek.operation.platform.launch.client.dialog.CustomDialog#show()
	 */
	public void show(){
		setText("查询二维码信息");
		setDescript("请输入二维码");
		super.show();
		E_GOODS_SN_Box.setFocus(true);
	}

	
	/** 
	 * @Description: 验证 
	 * @return boolean 返回类型 
	 */ 
	public boolean isValid() {
		String check;
		check = Util.commValidity((E_GOODS_SN_Box.getText()).trim(), TdbcConstanst.E_GOODS_SN_ZH);
		if (null != check) {
			setMessage(check);
			E_GOODS_SN_Box.setFocus(true);
			return false;
		}
		return true;
	}
}
