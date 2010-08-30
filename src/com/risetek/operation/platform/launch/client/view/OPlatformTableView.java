package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
	
	public abstract String[] parseRow(Node node);
	public abstract Grid getGrid();
	
	public OPlatformTableView() {
		setWidth("100%");
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.setStyleName("tableouter");
		outer.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		messagePanel.setWidth("100%");
		messagePanel.setHeight("20px");
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
		outer.add(messagePanel);
		
		grid.setStyleName("optable");
	    outer.add(grid);
	    
	    add(outer, DockPanel.SOUTH);
	}
	
	public void addActionPanel(Widget widget, String descript){
		HorizontalPanel blank = new HorizontalPanel();
		blank.setHeight("5px");
		add(blank, DockPanel.SOUTH);
		HorizontalPanel border = new HorizontalPanel();
		border.setWidth("100%");
		border.setHeight("32px");
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
		double total = 0.0000;
		for(int i=0;i<columnsWidth.length;i++){
			double temp = Double.parseDouble(Integer.toString(columnsWidth[i]));
			total += temp;
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
				grid.setText(0, i, columns[i-2]);
				grid.getCellFormatter().setStyleName(0, i, "optable-title");
				double temp = Double.parseDouble(Integer.toString(columnsWidth[i-2]));
				double percent = temp/total;
				percent *= 100;
				String p = Double.toString(percent);
				int index = p.indexOf(".");
				p = p.substring(0, index);
				grid.getCellFormatter().setWidth(0, i, p + "%");
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
    	if(index<data.getSum()){
//    		if(index==grid.getRowCount()-1) {
//    			for(int i=0;i<grid.getColumnCount();i++){
//    				if(i==0){
//    					grid.setWidget(index+1, i, new RadioButton("aa"));
//    				} else if (i==1){
//    					grid.setText(index+1, i, Integer.toString(i));
//    				} else {
//    					grid.setText(index+1, i, "123456789012345678901234567890123456789012345678901234567890");
//    				}
//	    			setTableBottomStyle(index, i);
//	    		}
//    		} else {
    			for(int i=0;i<grid.getColumnCount();i++){
    				if(i==0){
    					grid.setWidget(index+1, i, new CheckBox());
    				} else if (i==1){
    					grid.setText(index+1, i, Integer.toString(index+1));
    					grid.getCellFormatter().setHorizontalAlignment(index+1, i, HasHorizontalAlignment.ALIGN_CENTER);
    				} else {
    					grid.setText(index+1, i, "界面测试数据-界面测试数据-界面测试数据-界面测试数据-界面测试数据-界面测试数据-界面测试数据-界面测试数据");
    				}
	    			setTableLineStyle(grid, index, i);
	    		}
//    		}
    	} else {
    		if(index==grid.getRowCount()-1) {
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
    
    private void setTableLineStyle(Grid grid, int index, int i){
    	if(i==grid.getColumnCount()-1){
			grid.getCellFormatter().setStyleName(index+1, i, "optable-line-end");
		} else {
			grid.getCellFormatter().setStyleName(index+1, i, "optable-line");
		}
    }
    
    private void setTableBottomStyle(Grid grid, int index, int i){
    	if(i==grid.getColumnCount()-1){
			grid.getCellFormatter().setStyleName(index+1, i, "optable-bottom-end");
		} else {
			grid.getCellFormatter().setStyleName(index+1, i, "optable-bottom");
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

		String[] bannerText;
		boolean isChild;
		
		public GreenMouseEventGrid(String[] bannerText){
			this.bannerText = bannerText;
			this.isChild = false;
		}
		
		public GreenMouseEventGrid(String[] bannerText, boolean isChild){
			this.bannerText = bannerText;
			this.isChild = isChild;
		}
		
		@Override
		public void onMouseOver(Element td, int column) {
			DOM.removeElementAttribute(td, "title");
			String text = "";
			if(column<2){
				if(column==1){
					text = "点击删除本条记录";
				}
			} else {
				text = bannerText[column-2];
			}
			if(!isChild){
				setInfo(text);
			} else {
				Grid child = (Grid)outer.getWidget(2);
				child.setText(0, 1, text);
			}
            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
//            renderLine(AController.this.getData(), row-1);
		}

		@Override
		public void onMouseOut(Element td, int column) {
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
//				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
			
//			if(mayColor == true) {
//				td.getStyle().setColor("red");
			if(!isChild){
				setInfo("");
			} else {
				Grid child = (Grid)outer.getWidget(2);
				child.setText(0, 1, "");
			}
//				td.getStyle().setCursor(Cursor.POINTER);
//			}
		}
	}
	
	public Widget getBlank5Panel(){
		VerticalPanel vp = new VerticalPanel();
		HTML blank = new HTML("");
		blank.setStyleName("blank5");
		vp.add(blank);
		return vp;
	}
	
	
}
