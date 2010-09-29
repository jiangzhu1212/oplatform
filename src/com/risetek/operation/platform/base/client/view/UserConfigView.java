package com.risetek.operation.platform.base.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.xml.client.Node;
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
	
	public Button addUser = new Button("添加用户", new UserConfigController.AddUserAction());
	public Button deleteUser = new Button("注销用户", new UserConfigController.OffUserAction());
	public Button resetPws = new Button("重置用户密码", new UserConfigController.ResetPasswordAction());
	public Button hangUser = new Button("挂起用户", new UserConfigController.HangUserAction());
	public Button kickUser = new Button("强制用户下线", new UserConfigController.KickUserAction());
	public Button resetUserStatuc = new Button("复位用户状态", new UserConfigController.ResetUserStatucAction());
	public Button editUser = new Button("修改用户信息", new UserConfigController.ChangUserInfoAction());
	
	public UserConfigView(){
		addActionPanel(initPromptGrid(), UserConfigSink.Desc, UserConfigSink.Name);
		setLocation(UserConfigSink.Group + "->" + UserConfigSink.Name);
	}
	
	private HorizontalPanel initPromptGrid(){
		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(addUser);
		actionPanel.add(deleteUser);
		actionPanel.add(resetPws);
		actionPanel.add(hangUser);
		actionPanel.add(kickUser);
		actionPanel.add(resetUserStatuc);
		actionPanel.add(editUser);
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
			grid = new GreenMouseEventGrid();
		}
		formatGrid(grid, rowCount, columns, columnsWidth);
		grid.addClickHandler(new UserConfigController.TableAction());
		return grid;
	}

	@Override
	public void render(OPlatformData data) {
		grid.resizeRows(rowCount+1);
		clearGridStyle(grid, rowCount);
		clearGrid(grid, rowCount);
		for(int index=1;index<rowCount;index++){
			renderLine(grid, data, index);
		}
		renderStatistic(data);
	}

	public void renderLine(Grid grid, OPlatformData data, int index){
    	if(data.getData()!=null){
	    	if(index<=data.getData().length){
				for(int i=0;i<grid.getColumnCount();i++){
					if(i==0){
						grid.setWidget(index, i, new CheckBox());
					} else if (i==1){
						grid.setText(index, i, Integer.toString(index));
						grid.getCellFormatter().setHorizontalAlignment(index, i, HasHorizontalAlignment.ALIGN_CENTER);
					} else if (i==4){
						String status = data.getData()[index-1][i-2];
						int stat = Integer.parseInt(status);
						String style = "";
						String text = "离线";
						if(stat==-1){
							style = "red";
							text = "挂起";
						} else if (stat==-2){
							style = "gray";
							text = "失效";
						} else if (stat<-2){
							style = "white";
							text = "注销";
						} else if (stat>0){
							style = "green";
							text = "在线";
						}
						grid.setText(index, i, text);
						if(style.length()>0){
							grid.getRowFormatter().setStyleName(index, style);
						}
					} else if(i == 5) {
						String[][] roleData = UserConfigController.INSTANCE.getRoleData().getData();
						String role = "<角色已被删除>";
						String id = data.getData()[index-1][i-2];
						for(int a=0;a<roleData.length;a++){
							String[] temp = roleData[a];
							if(temp[0].equals(id)){
								role = temp[1];
								break;
							}
						}
						grid.setText(index, i, role);
					} else {
						if(data.getData()!=null){
							String text = data.getData()[index-1][i-2];
							grid.setText(index, i, text);
						} else {
							grid.clearCell(index, i);
						}
					}
					if(i==2){
						grid.getCellFormatter().setHorizontalAlignment(index, i, HasHorizontalAlignment.ALIGN_CENTER);
					}
	    			setTableLineStyle(grid, index, i);
	    		}
	    	} else {
	    		if(index==grid.getRowCount()) {
	    			for(int i=0;i<grid.getColumnCount();i++){
	    				setTableBottomStyle(grid, index, i);
		    		}
	    		} else {
	    			for(int i=0;i<grid.getColumnCount();i++){
	    				setTableLineStyle(grid, index, i);
		    		}
	    		}
	    	}
    	} else {
    		if(index==grid.getRowCount()) {
    			for(int i=0;i<grid.getColumnCount();i++){
    				setTableBottomStyle(grid, index, i);
	    		}
    		} else {
    			for(int i=0;i<grid.getColumnCount();i++){
    				setTableLineStyle(grid, index, i);
	    		}
    		}
    	}
    }
	
	@Override
	public HorizontalPanel getPagePanel() {
		return page;
	}

	@Override
	public void firstPageAction(PageLabel label) {
		UserConfigController.INSTANCE.firstPageAction(label);
	}

	@Override
	public void beforePageAction(PageLabel label) {
		UserConfigController.INSTANCE.beforePageAction(label);
	}

	@Override
	public void afterPageAction(PageLabel label) {
		UserConfigController.INSTANCE.afterPageAction(label);
	}

	@Override
	public void lastPageAction(PageLabel label) {
		UserConfigController.INSTANCE.lastPageAction(label);
	}

	@Override
	public HorizontalPanel getChildPagePanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
