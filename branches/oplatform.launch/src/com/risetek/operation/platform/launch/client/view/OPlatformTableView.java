package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Node;

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
		messagePanel.setCellHorizontalAlignment(info, HasHorizontalAlignment.ALIGN_RIGHT);
//		toolPanel.setWidth("100%");
//		toolPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		outer.add(messagePanel);
		
//	    toolPanel.add(infoLabel, DockPanel.WEST);
//		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
//		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
//		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
//	    toolPanel.setCellWidth(infoLabel, "50%");

	    outer.add(grid);
	    
	    add(outer, DockPanel.SOUTH);
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
}
