package com.risetek.operation.platform.launch.client.view;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class GreenMouseEventGrid extends MouseEventGrid {

	private boolean mayColor = false;
	
	@Override
	public void onMouseOver(Element td, int column) {
		td.getStyle().setOverflow(Overflow.VISIBLE);
		String title = td.getInnerText();
		
		if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
			DOM.setElementAttribute(td, "title", td.getInnerText());			
		}
		
		if(mayColor == true) {
			td.getStyle().setColor("red");
//			setInfo("ssss");//banner_text[column]);
			td.getStyle().setCursor(Cursor.POINTER);
		}
	}

	@Override
	public void onMouseOut(Element td, int column) {
		DOM.removeElementAttribute(td, "title");			

		td.getStyle().setOverflow(Overflow.HIDDEN);
		td.getStyle().clearColor();
		td.getStyle().setCursor(Cursor.DEFAULT);
//		setInfo(banner_tips);

        Element tr = DOM.getParent(td);
        Element body = DOM.getParent(tr);
        int row = DOM.getChildIndex(body, tr);
        if(row == 0) {
        	return;
        }
//        renderLine(RadiusUserController.INSTANCE.getTable(), row-1);
	}

}
