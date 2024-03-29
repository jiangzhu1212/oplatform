package com.risetek.operation.platform.launch.client.view;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.operation.platform.launch.client.OplatformLaunch;
import com.risetek.operation.platform.launch.client.model.OPlatformData;

/**
 * @author Amber
 * 功能：数据表框架，基本都写完了，具体用的时候只需要注入表头和数据内容即可
 * 2010-8-23 下午11:35:29
 */
public abstract class OPlatformTableView extends DockPanel {

	public Grid grid = getGrid();
	protected final VerticalPanel outer = new VerticalPanel();
	protected final DockPanel messagePanel = new DockPanel();
	private final Label locationLabel = new Label("");
	private final Label infoLabel = new Label("");
	private final Label statisticLabel = new Label("");
	public VerticalPanel main = new VerticalPanel();
	public HorizontalPanel page = new HorizontalPanel();
	public HorizontalPanel actionPanel;
	public HorizontalPanel childTableActionPanel;// = new HorizontalPanel();
	
	public abstract String[] parseRow(Node node);
	public abstract Grid getGrid();
	public abstract HorizontalPanel getPagePanel();
	public abstract HorizontalPanel getChildPagePanel();
	
	public OPlatformTableView() {
		setWidth("100%");
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.setStyleName("tableouter");
		outer.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
//		outer.setBorderWidth(1);
		
		messagePanel.setWidth("100%");
		messagePanel.setHeight("25px");
//		messagePanel.setBorderWidth(1);
		messagePanel.setStyleName("tableMessagePanel");
		Grid location = new Grid(1, 1);
		location.setWidget(0, 0, locationLabel);
		location.getCellFormatter().setStyleName(0, 0, "tableMessagePanel-content");
		messagePanel.add(location, DockPanel.WEST);
		messagePanel.setCellWidth(location, "40%");
		messagePanel.setCellHorizontalAlignment(location, HasHorizontalAlignment.ALIGN_LEFT);
		Grid total = new Grid(1, 1);
	    total.setWidget(0, 0, statisticLabel);
	    total.getCellFormatter().setStyleName(0, 0, "tableMessagePanel-content");
	    messagePanel.add(total, DockPanel.WEST);
	    messagePanel.setCellWidth(total, "20%");
	    messagePanel.setCellHorizontalAlignment(total, HasHorizontalAlignment.ALIGN_CENTER);
	    Grid info = new Grid(1, 1);
	    info.setWidget(0, 0, infoLabel);
	    info.getCellFormatter().setStyleName(0, 0, "tableMessagePanel-content-action");
		messagePanel.add(info, DockPanel.EAST);
		messagePanel.setCellWidth(info, "40%");
		messagePanel.setCellHorizontalAlignment(info, HasHorizontalAlignment.ALIGN_RIGHT);
		main.add(messagePanel);
		main.add(grid);
		Widget pagePanel = createPagePanel();
		main.add(pagePanel);
		main.setCellHorizontalAlignment(pagePanel, HasHorizontalAlignment.ALIGN_CENTER);
		outer.add(main);
		grid.setStyleName("optable");
	    HTML empty = new HTML("");
		empty.setStyleName("blank3");
	    add(outer, DockPanel.SOUTH);
	    add(empty, DockPanel.SOUTH);
	}
	
	public void addActionPanel(HorizontalPanel widget, String descript, String name){
		actionPanel = widget;
		checkAddAction(widget, name);
		HorizontalPanel border = new HorizontalPanel();
		border.setWidth("100%");
		border.setHeight("30px");
		border.add(widget);
		border.setStyleName("actionPanel");
		border.setCellVerticalAlignment(widget, HasVerticalAlignment.ALIGN_MIDDLE);
		HTML desc = new HTML(descript);
		desc.setStyleName("descript");
		border.add(desc);
		border.setCellWidth(desc, "25%");
		border.setCellHorizontalAlignment(desc, HasHorizontalAlignment.ALIGN_RIGHT);
		add(border, DockPanel.SOUTH);
	}
	
	public HorizontalPanel getActionPanel(){
		return actionPanel;
	}
	
	public HorizontalPanel getChildActionPanel(){
		return childTableActionPanel;
	}
	
	private Widget createPagePanel() {
		final PageLabel first = new PageLabel("|<");
		final PageLabel before = new PageLabel("<<");
		final PageLabel after = new PageLabel(">>");
		final PageLabel last = new PageLabel(">|");
		first.setEnable(false);
		before.setEnable(false);
		after.setEnable(false);
		last.setEnable(false);
		PageLabel page1 = new PageLabel("1");
		page1.addStyleName("select");
		page1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				label.addStyleName("select");
				HorizontalPanel page = (HorizontalPanel)label.getParent();
				for(int i=0;i<page.getWidgetCount();i++){
					Widget w = page.getWidget(i);
					if(!w.equals(label)){
						w.removeStyleName("select");
					}
				}
				if(page.getWidgetCount()>5){
					first.setEnable(false);
					before.setEnable(false);
					after.setEnable(true);
					last.setEnable(true);
				}
				onLoad();
			}
		});
		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				firstPageAction(label);
			}
		});
		before.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				beforePageAction(label);
			}
		});
		after.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				afterPageAction(label);
			}
		});
		last.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageLabel label = (PageLabel)event.getSource();
				lastPageAction(label);
			}
		});
		page.add(first);
		page.add(before);
		page.add(page1);
		page.add(after);
		page.add(last);
		return page;
	}
	
	public abstract void firstPageAction(PageLabel label);
	public abstract void beforePageAction(PageLabel label);
	public abstract void afterPageAction(PageLabel label);
	public abstract void lastPageAction(PageLabel label);
	
	/**
	 * 功能：格式化表格样式
	 *
	 * void
	 * @param grid
	 * @param rowCount
	 * @param columns
	 * @param columnswidth 
	 */
	public void formatGrid(Grid grid, int rowCount, String[] columns, int[] columnsWidth){
		grid.resize(rowCount+1, columns.length+2);
		double maxWidth = 0;
    	for(int i=0;i<columns.length;i++){
    		int width = columnsWidth[i];
    		maxWidth = maxWidth + width;
    	}
		for(int i=0;i<grid.getColumnCount();i++){
			if(i<2){
				if(i==1){
					grid.setText(0, i, "序号");
					grid.getColumnFormatter().setWidth(i, "40px");
				} else {
					grid.getColumnFormatter().setWidth(i, "30px");
				}
				grid.getCellFormatter().setStyleName(0, i, "optable-title");
			} else {
				double width = columnsWidth[i-2];
				double temp = width/maxWidth * 100;
	    		String per = Double.toString(temp);
	    		if(per.length()<8) {
	    			per = per + "0000";
	    		}
	    		int dot = per.indexOf(".");
	    		String miu = per.substring(dot+ 1, dot+5);
	    		int tmiu = Integer.parseInt(miu);
	    		if(tmiu>4445){
	    			temp = temp + 1;
	    		}
	    		per = Double.toString(temp);
	    		if(per.indexOf(".")!=-1){
	    			per = per.substring(0, per.indexOf("."));
	    		}
				grid.setText(0, i, columns[i-2]);
				grid.getCellFormatter().setStyleName(0, i, "optable-title");
				grid.getCellFormatter().setWidth(0, i, per + "%");
			}
		}
	}
	
	/**
	 * 功能：显示鼠标事件名称
	 *
	 * void
	 * @param text
	 */
	public void setInfo(String text){
		infoLabel.setText(text);
	}

    /**
     * 功能：显示数据表内总条数
     *
     * void
     * @param total
     */
    public void setStatisticText(int total){
    	statisticLabel.setText("总数："+Integer.toString(total));
    }
    
    /**
     * 功能：显示当前所打开页面在系统中的位置，个人认为放在这里不妥，暂时没有找到更好的位置
     *
     * void
     * @param text
     */
    public void setLocation(String text){
    	locationLabel.setText(text);
    }
    
    public abstract void render(OPlatformData data);
    
    /**
     * 功能：按行读取并填写数据
     *
     * void
     * @param data
     * @param index
     */
    public void renderLine(Grid grid, OPlatformData data, int index){
    	if(data.getData()!=null){
	    	if(index<=data.getData().length){
				for(int i=0;i<grid.getColumnCount();i++){
					if(i==0){
						grid.setWidget(index, i, new CheckBox());
					} else if (i==1){
						grid.setText(index, i, Integer.toString(index));
						grid.getCellFormatter().setHorizontalAlignment(index, i, HasHorizontalAlignment.ALIGN_CENTER);
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
    
    public void setTableLineStyle(Grid grid, int index, int i){
    	if(i==grid.getColumnCount()-1){
			grid.getCellFormatter().setStyleName(index, i, "optable-line-end");
		} else {
			grid.getCellFormatter().setStyleName(index, i, "optable-line");
		}
    }
    
    public void setTableBottomStyle(Grid grid, int index, int i){
    	if(i==grid.getColumnCount()-1){
			grid.getCellFormatter().setStyleName(index, i, "optable-bottom-end");
		} else {
			grid.getCellFormatter().setStyleName(index, i, "optable-bottom");
		}
    }
    
    /**
     * 功能：填写数据总条数
     *
     * void
     * @param data
     */
    public void renderStatistic(OPlatformData data){
    	setStatisticText(data.getSum());
    }
    
    /**
	 * @author Amber
	 * 功能：数据表鼠标移动样式事件处理子类
	 * 2010-8-23 下午11:55:34
	 */
	public class GreenMouseEventGrid extends MouseEventGrid {

		boolean isChild;
		
		public GreenMouseEventGrid(){
			this.isChild = false;
		}
		
		public GreenMouseEventGrid(boolean isChild){
			this.isChild = isChild;
		}
		
		@Override
		public void onMouseOver(Element td, int column) {
			DOM.removeElementAttribute(td, "title");
            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) {
            	return;
            }
		}

		@Override
		public void onMouseOut(Element td, int column) {
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
//				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
			
			if(!isChild){
				setInfo("");
			} else {
				VerticalPanel child = (VerticalPanel)outer.getWidget(1);
				Grid childTitle = (Grid)child.getWidget(0);
				childTitle.setText(0, 1, "");
			}
		}
	}
	
	public Widget getBlank5Panel(){
		VerticalPanel vp = new VerticalPanel();
		HTML blank = new HTML("");
		blank.setStyleName("blank5");
		vp.add(blank);
		return vp;
	}
	
	public void clearGrid(Grid grid, int rowCount) {
		for(int i=1;i<=rowCount;i++){
			for(int a=0;a<grid.getColumnCount();a++){
				grid.clearCell(i, a);
			}
		}
	}
	
	public void clearGridStyle(Grid grid, int rowCount) {
		for(int i=1;i<=rowCount;i++){
			grid.getRowFormatter().removeStyleName(i, "green");
			grid.getRowFormatter().removeStyleName(i, "red");
			grid.getRowFormatter().removeStyleName(i, "white");
			for(int a=0;a<grid.getColumnCount();a++){
				grid.clearCell(i, a);
			}
		}
	}
	
	protected ArrayList<String> getLocalActionList(ArrayList<String> ro, String name) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<ro.size();i++){
			String[] temp = ro.get(i).split(",");
			if(temp[0].equals(name)){
				list.add(temp[1]);
			}
		}
		return list;
	}
	
	protected boolean checkAction(Button btn, ArrayList<String> actions) {
		String name = btn.getText();
		for(int i=0;i<actions.size();i++){
			String ac = actions.get(i);
			if(ac.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	private void checkAddAction(HorizontalPanel widget, String name) {
		ArrayList<String> ro = OplatformLaunch.loginUser.getRoleOperation();
		ArrayList<String> actions = getLocalActionList(ro, name);
		for(int i=0;i<widget.getWidgetCount();i++){
			Widget w = widget.getWidget(i);
			if(w instanceof Button){
				if(!checkAction((Button)w, actions)){
					w.setVisible(false);
				}
			}
		}
	}
	
}
