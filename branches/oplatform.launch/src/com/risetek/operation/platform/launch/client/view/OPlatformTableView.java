package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

public abstract class OPlatformTableView extends DockPanel {

	public Grid grid = getGrid();
	protected final VerticalPanel outer = new VerticalPanel();
	protected final DockPanel messagePanel = new DockPanel();
	private final Label locationLabel = new Label("");
	private final Label infoLabel = new Label("");
	private final Label statisticLabel = new Label("");
	
	public abstract String[] parseRow(Node node);
	public abstract Grid getGrid();
	
	public OPlatformTableView() {
		setWidth("100%");
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		outer.setBorderWidth(1);
		
		messagePanel.setWidth("100%");
		messagePanel.setHeight("30px");
		Grid location = new Grid(1, 1);
		location.setWidget(0, 0, locationLabel);
		messagePanel.add(location, DockPanel.WEST);
		messagePanel.setCellWidth(location, "40%");
		messagePanel.setCellHorizontalAlignment(location, HasHorizontalAlignment.ALIGN_LEFT);
		Grid total = new Grid(1, 1);
	    total.setWidget(0, 0, statisticLabel);
	    messagePanel.add(total, DockPanel.WEST);
	    messagePanel.setCellWidth(total, "20%");
	    messagePanel.setCellHorizontalAlignment(total, HasHorizontalAlignment.ALIGN_CENTER);
	    Grid info = new Grid(1, 1);
	    info.setWidget(0, 0, infoLabel);
		messagePanel.add(info, DockPanel.EAST);
		messagePanel.setCellWidth(info, "40%");
		messagePanel.setCellHorizontalAlignment(info, HasHorizontalAlignment.ALIGN_CENTER);
		outer.add(messagePanel);
		
	    outer.add(grid);
	    
	    add(outer, DockPanel.SOUTH);
	}
	
	public void formatGrid(Grid grid, int rowCount, String[] columns){
		grid.resize(rowCount+1, columns.length);
		for(int i=0;i<grid.getColumnCount();i++){
			grid.setText(0, i, columns[i]);
		}
	}
	
	public void setInfo(String text){
		infoLabel.setText(text);
	}

    public void setStatisticText(int total){
    	statisticLabel.setText("总数："+Integer.toString(total));
    }
    
    public void setLocation(String text){
    	locationLabel.setText(text);
    }
    
    public void renderLine(OPlatformData data, int index){
    	if(index<4){
    		for(int i=0;i<grid.getColumnCount();i++){
    			grid.setText(index+1, i, "123456");
    		}
    	}
    }
    
    public void renderStatistic(OPlatformData data){
    	setStatisticText(data.getSum());
    }
}
