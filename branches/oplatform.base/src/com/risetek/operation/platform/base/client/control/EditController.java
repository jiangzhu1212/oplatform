package com.risetek.operation.platform.base.client.control;

import com.risetek.operation.platform.base.client.dialog.EditDialog;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;

/**
 * 
 * @author 杨彬
 * gridPanel 修改 删除 作废 内容的控制器
 */
public class EditController {
	
	String colName = null;
	protected EditDialog dialog = null;
	public void setColName(String colName) {
		this.colName = colName;
		dialog = EditDialog.INSTANCE;
		dialog.makeMainPanel(colName);
	}

	protected CustomDialog getDialog() {
		return dialog;
	}

}
