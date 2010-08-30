package com.risetek.operation.platform.base.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.base.client.view.RoleConfigView;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public class RoleConfigController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	@Override
	public ArrayList<String> getActionNames() {
		return actionNames;
	}

	public static RoleConfigController INSTANCE = new RoleConfigController();
	final RoleConfigData data = new RoleConfigData();
	
	public final RoleConfigView view = new RoleConfigView();
	
	public RoleConfigController(){
		actionNames.add("添加用户角色");
		actionNames.add("删除用户角色");
		actionNames.add("修改用户角色名称");
		actionNames.add("添加用户角色操作");
		actionNames.add("删除用户角色操作");
		actionNames.add("修改用户角色操作");
	}
	
	@Override
	public Widget getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPlatformData getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void load(){
		INSTANCE.data.setSum(10);
		INSTANCE.view.render(INSTANCE.data);
		INSTANCE.view.renderChild(INSTANCE.data);
	}
	
	public static class TableAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
//			String rowid = table.getText(row, 0);
			int index = col;
			String tisp_value = table.getText(row, col);
			if(tisp_value.length() == 1){
				int tvalue = (int)tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			switch (index) {
			case 1:
				
				break;
			case 2:
			case 3:
				break;
			default:
				break;
			}
		}
	}
}
