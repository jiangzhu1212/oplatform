package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.base.client.UserConfigSink;
import com.risetek.operation.platform.base.client.control.UserConfigController;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.IOPlatformView;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

public class UserConfigView extends OPlatformTableView implements IOPlatformView {

	public final static String[] columns = {"ID", "用户名", "状态", "角色", "邮箱地址", "最后登录时间", "最后登录地址", "注册时间"};
	public final static int[] columnsWidth = {4, 16, 4, 12, 16, 16, 16, 16};
	public final static int rowCount = UIConfig.TABLE_ROW_NORMAL;
	public static String descript = "";
	public Grid grid = getGrid();
	
	public Button addUser = new Button("添加用户");
	public Button deleteUser = new Button("删除用户");
	public Button resetPws = new Button("重置用户密码");
	public Button hangUser = new Button("挂起用户");
	public Button kickUser = new Button("强制用户下线");
	
	private final static String[] banner_text = {
		"选择该用户",
		"选择该用户",
		"选择该用户",
		"选择该用户",
		"修改用户邮箱地址",
		"选择该用户",
		"选择该用户",
		"选择该用户"
	};
	
	public UserConfigView(){
		addActionPanel(initPromptGrid(), UserConfigSink.Desc);
		setLocation(RoleConfigSink.Group + "->" + RoleConfigSink.Name);
	}
	
	private Widget initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addUser);
		actionPanel.add(deleteUser);
		actionPanel.add(resetPws);
		actionPanel.add(hangUser);
		actionPanel.add(kickUser);
		return actionPanel;
	}
	
	public void onLoad(){
		UserConfigController.INSTANCE.load(1);
	}
	
	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] parseRow(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grid getGrid() {
		if(grid == null){
			grid = new GreenMouseEventGrid(banner_text);
		}
		formatGrid(grid, rowCount, columns, columnsWidth);
		return grid;
	}

	@Override
	public void render(OPlatformData data) {
		grid.resizeRows(rowCount+1);
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	@Override
	public HorizontalPanel getPagePanel() {
		return page;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lastPageAction(PageLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
