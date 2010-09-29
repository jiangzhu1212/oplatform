package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;

/**
 * @author Amber
 * 功能：重载表格类，实现表格的鼠标移动事件
 * 2010-8-23 下午11:33:42
 */
public abstract class MouseEventGrid extends Grid {

	public MouseEventGrid () {
		super();
		setCellPadding(2);
		setCellSpacing(0);
		setSize("100%", "100%");
		sinkEvents(Event.MOUSEEVENTS);
	}
	
	/** 
	 * 功能： 我理解为事件响应入口，具体怎么移动里面有代码判断
	 *(non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event) {
		Element td = getEventTargetCell(event);
		if (null == td){
			return;
		}

		Element tr = DOM.getParent(td);
		if (null == tr){
			return;
		}

		Element body = DOM.getParent(tr);
		if (null == body){
			return;
		}

		int row = DOM.getChildIndex(body, tr);
		// 开始行是标题
		if (row == 0){
			return;
		}

		// GWT.log("Row: " +row);
		// 这是为了确保该行存在数据。
		String index = getText(row, 2);
		index = index.trim();
		if(index.length()>0){
			if(index.length()==1){
				if(((int)index.charAt(0))==160){
					return;
				}
			}
		} else {
			return;
		}

		int column = DOM.getChildIndex(tr, td);
		switch (DOM.eventGetType(event)) {
		case Event.ONMOUSEOVER:
			String s = getRowFormatter().getElement(row).getClassName();
			if(s.length()>0&&s.equals("white")){
				return;
			}
			getRowFormatter().getElement(row).getStyle().setBackgroundColor("#C5E9FC");
			getRowFormatter().getElement(row).getStyle().setCursor(Style.Cursor.POINTER);
			onMouseOver(td, column);
			break;
		case Event.ONMOUSEOUT:
			// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
		case Event.ONCLICK:
			s = getRowFormatter().getElement(row).getClassName();
			if(s.length()>0&&s.equals("white")){
				return;
			}
			getRowFormatter().getElement(row).getStyle().clearBackgroundColor();
			getRowFormatter().getElement(row).getStyle().clearCursor();
			onMouseOut(td, column);
			break;
		}
		super.onBrowserEvent(event);
	}

	/**
	 * 功能：鼠标移开事件响应
	 *
	 * void
	 * @param td
	 * @param column
	 */
	public abstract void onMouseOver(Element td, int column);

	/**
	 * 功能：鼠标移入事件响应
	 *
	 * void
	 * @param td
	 * @param column
	 */
	public abstract void onMouseOut(Element td, int column);
}