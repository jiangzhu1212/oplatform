package com.risetek.operation.platform.launch.client.control;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.launch.client.view.PageLabel;

/**
 * @author Amber
 * 功能：控制器接口
 * 2010-8-23 下午11:24:56
 */
public abstract class AController {
	/**
	 * 功能：用于加载模块时获取视图接口
	 *
	 * Widget
	 * @return
	 */
	public abstract OPlatformTableView getView();
	public abstract OPlatformData getData();
	public abstract OPlatformData getChildData();
	public abstract ArrayList<String> getActionNames();
	public abstract void load(final int pagePoint);
	public abstract void loadChild(final String id, String value, final int childPagePoint);
	public abstract void setPagePoint(int point);
	public abstract int getPagePoint();
	public abstract void setChildPagePoint(int point);
	public abstract int getChildPagePoint();
	
	public void resetPagePanel(AController INSTANCE, final int rowCount, int result){
		INSTANCE.getData().setSum(result);
		int pageNumber = result/rowCount;
		if(result%rowCount==0){
			pageNumber--;
		}
		int count = INSTANCE.getView().getPagePanel().getWidgetCount();
		if(count == pageNumber+5){
			return;
		}
		for(int i=0;i<count;i++){
			if(i<3||i>count-3){
				continue;
			}
			INSTANCE.getView().getPagePanel().remove(3);
			if(INSTANCE.getView().getPagePanel().getWidgetCount()==5){
				break;
			}
		}
		for(int i=0;i<pageNumber;i++){
			String labelText = Integer.toString(i+2);
			PageLabel label = new PageLabel(labelText);
			INSTANCE.getView().getPagePanel().insert(label, i+3);
			label.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					PageLabel label = (PageLabel)event.getSource();
					label.addStyleName("select");
					String labelName = label.getText();
					int point = Integer.parseInt(labelName);
					setPagePoint(point);
					HorizontalPanel page = (HorizontalPanel)label.getParent();
					for(int i=0;i<page.getWidgetCount();i++){
						Widget w = page.getWidget(i);
						if(!w.equals(label)){
							w.removeStyleName("select");
						}
					}
					int count = page.getWidgetCount();
					if(point==1){
						((PageLabel)page.getWidget(0)).setEnable(false);
						((PageLabel)page.getWidget(1)).setEnable(false);
						((PageLabel)page.getWidget(count-1)).setEnable(true);
						((PageLabel)page.getWidget(count-2)).setEnable(true);
					} else if(point==count-4){
						((PageLabel)page.getWidget(0)).setEnable(true);
						((PageLabel)page.getWidget(1)).setEnable(true);
						((PageLabel)page.getWidget(count-1)).setEnable(false);
						((PageLabel)page.getWidget(count-2)).setEnable(false);
					} else {
						((PageLabel)page.getWidget(0)).setEnable(true);
						((PageLabel)page.getWidget(1)).setEnable(true);
						((PageLabel)page.getWidget(count-1)).setEnable(true);
						((PageLabel)page.getWidget(count-2)).setEnable(true);
					}
					load(point);
				}
			});
		}
		int point = getPagePoint();
		if(point==0){
			point = 1;
		} else if (point>pageNumber+1){
			point = pageNumber+1;
			setPagePoint(point);
		}
		HorizontalPanel pagePanel = INSTANCE.getView().getPagePanel();
		if(pageNumber>1&&point==1){
			((PageLabel)pagePanel.getWidget(0)).setEnable(false);
			((PageLabel)pagePanel.getWidget(1)).setEnable(false);
			((PageLabel)pagePanel.getWidget(pageNumber+3)).setEnable(true);
			((PageLabel)pagePanel.getWidget(pageNumber+4)).setEnable(true);
		}
		if(point<=pageNumber){
			((PageLabel)pagePanel.getWidget(pageNumber+3)).setEnable(true);
			((PageLabel)pagePanel.getWidget(pageNumber+4)).setEnable(true);
		}
		PageLabel label = (PageLabel)INSTANCE.getView().getPagePanel().getWidget(point+1);
		label.addStyleName("select");
		load(point);
	}
	
	public void firstPageAction(PageLabel label){
		int point = 1;
		setPagePoint(point);
		refreshLabelStyle(point, label);
		setLabelVisable(label, point);
		load(point);
	}
	
	public void beforePageAction(PageLabel label){
		int point = getPagePoint();
		point--;
		setLabelVisable(label, point);
		setPagePoint(point);
		refreshLabelStyle(point, label);
		load(point);
	}
	
	public void afterPageAction(PageLabel label){
		int point = getPagePoint();
		point++;
		setLabelVisable(label, point);
		setPagePoint(point);
		refreshLabelStyle(point, label);
		load(point);
	}
	
	public void lastPageAction(PageLabel label){
		HorizontalPanel panel = (HorizontalPanel)label.getParent();
		int point = panel.getWidgetCount()-4;
		setPagePoint(point);
		refreshLabelStyle(point, label);
		setLabelVisable(label, point);
		load(point);
	}
	
	private void refreshLabelStyle(int point, PageLabel label){
		HorizontalPanel panel = (HorizontalPanel)label.getParent();
		int count = panel.getWidgetCount();
		for(int i=0;i<count;i++){
			PageLabel la = (PageLabel)panel.getWidget(i);
			String ln = la.getText();
			int number = 0;
			try{
				number = Integer.parseInt(ln);
			} catch (NumberFormatException e) {
				continue;
			}
			if(number==point){
				la.addStyleName("select");
			} else {
				la.removeStyleName("select");
			}
		}
	}
	
	private void setLabelVisable(PageLabel label, int point) {
		HorizontalPanel panel = (HorizontalPanel)label.getParent();
		int count = panel.getWidgetCount();
		if(point==1){
			((PageLabel)panel.getWidget(0)).setEnable(false);
			((PageLabel)panel.getWidget(1)).setEnable(false);
			((PageLabel)panel.getWidget(count-1)).setEnable(true);
			((PageLabel)panel.getWidget(count-2)).setEnable(true);
		} else if(point==count-4) {
			((PageLabel)panel.getWidget(0)).setEnable(true);
			((PageLabel)panel.getWidget(1)).setEnable(true);
			((PageLabel)panel.getWidget(count-1)).setEnable(false);
			((PageLabel)panel.getWidget(count-2)).setEnable(false);
		} else {
			((PageLabel)panel.getWidget(0)).setEnable(true);
			((PageLabel)panel.getWidget(1)).setEnable(true);
			((PageLabel)panel.getWidget(count-1)).setEnable(true);
			((PageLabel)panel.getWidget(count-2)).setEnable(true);
		}
	}
	
	public void resetChildPagePanel(AController INSTANCE, final int rowCount, int result, final String id, final String value){
		INSTANCE.getChildData().setSum(result);
		int pageNumber = result/rowCount;
		if(result%rowCount==0){
			if(pageNumber!=0){
				pageNumber--;
			}
		} else {
			
		}
		int count = INSTANCE.getView().getChildPagePanel().getWidgetCount();
		if(count == pageNumber+5){
			return;
		}
		for(int i=0;i<count;i++){
			if(i<3||i>count-3){
				continue;
			}
			INSTANCE.getView().getChildPagePanel().remove(3);
			if(INSTANCE.getView().getChildPagePanel().getWidgetCount()==5){
				break;
			}
		}
		for(int i=0;i<pageNumber;i++){
			String labelText = Integer.toString(i+2);
			PageLabel label = new PageLabel(labelText);
			INSTANCE.getView().getChildPagePanel().insert(label, i+3);
			label.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					PageLabel label = (PageLabel)event.getSource();
					label.addStyleName("select");
					String labelName = label.getText();
					int point = Integer.parseInt(labelName);
					setChildPagePoint(point);
					HorizontalPanel page = (HorizontalPanel)label.getParent();
					for(int i=0;i<page.getWidgetCount();i++){
						Widget w = page.getWidget(i);
						if(!w.equals(label)){
							w.removeStyleName("select");
						}
					}
					int count = page.getWidgetCount();
					if(point==1){
						((PageLabel)page.getWidget(0)).setEnable(false);
						((PageLabel)page.getWidget(1)).setEnable(false);
						((PageLabel)page.getWidget(count-1)).setEnable(true);
						((PageLabel)page.getWidget(count-2)).setEnable(true);
					} else if(point==count-4){
						((PageLabel)page.getWidget(0)).setEnable(true);
						((PageLabel)page.getWidget(1)).setEnable(true);
						((PageLabel)page.getWidget(count-1)).setEnable(false);
						((PageLabel)page.getWidget(count-2)).setEnable(false);
					} else {
						((PageLabel)page.getWidget(0)).setEnable(true);
						((PageLabel)page.getWidget(1)).setEnable(true);
						((PageLabel)page.getWidget(count-1)).setEnable(true);
						((PageLabel)page.getWidget(count-2)).setEnable(true);
					}
					loadChild(id, value, point);
				}
			});
		}
		int point = getChildPagePoint();
		if(point==0){
			point = 1;
		} else if (point>pageNumber+1){
			point = pageNumber+1;
			setChildPagePoint(point);
		}
		HorizontalPanel pagePanel = INSTANCE.getView().getChildPagePanel();
		if(pageNumber>1&&point==1){
			((PageLabel)pagePanel.getWidget(0)).setEnable(false);
			((PageLabel)pagePanel.getWidget(1)).setEnable(false);
			((PageLabel)pagePanel.getWidget(pageNumber+3)).setEnable(true);
			((PageLabel)pagePanel.getWidget(pageNumber+4)).setEnable(true);
		}
		if(point<=pageNumber){
			((PageLabel)pagePanel.getWidget(pageNumber+3)).setEnable(true);
			((PageLabel)pagePanel.getWidget(pageNumber+4)).setEnable(true);
		}
		if(pageNumber<1){
			((PageLabel)pagePanel.getWidget(pageNumber+3)).setEnable(false);
			((PageLabel)pagePanel.getWidget(pageNumber+4)).setEnable(false);
		}
		PageLabel label = (PageLabel)INSTANCE.getView().getChildPagePanel().getWidget(point+1);
		label.addStyleName("select");
		loadChild(id, value, point);
	}
	
	public void firstChildPageAction(String id, String value, PageLabel label){
		int point = 1;
		setChildPagePoint(point);
		refreshLabelStyle(point, label);
		setLabelVisable(label, point);
		loadChild(id, value, point);
	}
	
	public void beforeChildPageAction(String id, String value, PageLabel label){
		int point = getChildPagePoint();
		point--;
		setLabelVisable(label, point);
		setChildPagePoint(point);
		refreshLabelStyle(point, label);
		loadChild(id, value, point);
	}

	public void afterChildPageAction(String id, String value, PageLabel label){
		int point = getChildPagePoint();
		point++;
		setLabelVisable(label, point);
		setChildPagePoint(point);
		refreshLabelStyle(point, label);
		loadChild(id, value, point);
	}

	public void lastChildPageAction(String id, String value, PageLabel label){
		HorizontalPanel panel = (HorizontalPanel)label.getParent();
		int point = panel.getWidgetCount()-4;
		setChildPagePoint(point);
		refreshLabelStyle(point, label);
		setLabelVisable(label, point);
		loadChild(id, value, point);
	}
}
