package com.risetek.operation.platform.base.client.control;

import com.risetek.operation.platform.base.client.dialog.EditDialog;

/**
 * 
 * @author 杨彬
 * gridPanel 修改 删除 作废 内容的控制器
 */
public class EditController {
	
	protected EditDialog dialog = null;
	public void setColName(String colName) {
		dialog = EditDialog.INSTANCE;
		dialog.makeMainPanel(colName);
	}

	protected EditDialog getDialog() {
		return dialog;
	}

}
