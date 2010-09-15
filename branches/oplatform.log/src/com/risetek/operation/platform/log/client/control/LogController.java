package com.risetek.operation.platform.log.client.control;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.Log;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;
import com.risetek.operation.platform.log.client.dialog.FilterLogDialog;
import com.risetek.operation.platform.log.client.model.LogData;
import com.risetek.operation.platform.log.client.service.LogService;
import com.risetek.operation.platform.log.client.service.LogServiceAsync;
import com.risetek.operation.platform.log.client.view.LogView;

public class LogController extends AController {

	private ArrayList<String> actionNames = new ArrayList<String>();
	
	private final static LogServiceAsync ls = GWT.create(LogService.class);
	
	public static LogController INSTANCE = new LogController();
	final LogData data = new LogData();
	public final LogView view = new LogView();
	private int pagePoint = 1;
	
	private LogController(){
	}
	
	@Override
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public OPlatformData getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public OPlatformData getChildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getActionNames() {
		if(actionNames.isEmpty()){
			return null;
		} else {
			return actionNames;
		}
	}

	@Override
	public void load(int pagePoint) {
		ls.getLogDataCount(new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				INSTANCE.data.setSum(result);
			}
			public void onFailure(Throwable caught) {}
		});
		ls.getLastLogPage(UIConfig.TABLE_ROW_NORMAL, new AsyncCallback<Log[]>() {
			public void onSuccess(Log[] result) {
				INSTANCE.data.parseResult(result);
				INSTANCE.view.render(INSTANCE.data);
			}
			public void onFailure(Throwable caught) {}
		});
	}

	public void writeLos(Log log){
		ls.addLog(log, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {}
			public void onFailure(Throwable caught) {}
		});
	}
	
	public static class ShowHistoryAction implements ClickHandler{
		public void onClick(ClickEvent event) {
			if(INSTANCE.data.autoRefresh){
				INSTANCE.data.autoRefresh = false;
				ls.getLogDataCount(new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						INSTANCE.data.setSum(result);
						INSTANCE.resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result);
					}
					public void onFailure(Throwable caught) {}
				});
				ls.getLogPage(UIConfig.TABLE_ROW_NORMAL, new AsyncCallback<Log[]>() {
					public void onSuccess(Log[] result) {
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			} else {
				INSTANCE.data.autoRefresh = true;
				ls.getLogDataCount(new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						INSTANCE.data.setSum(result);
						INSTANCE.resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, UIConfig.TABLE_ROW_NORMAL);
					}
					public void onFailure(Throwable caught) {}
				});
				ls.getLastLogPage(UIConfig.TABLE_ROW_NORMAL, new AsyncCallback<Log[]>() {
					public void onSuccess(Log[] result) {
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			}
			INSTANCE.view.showHistory.setText(INSTANCE.data.autoRefresh?"查看历史":"自动更新");
		}
	}
	
	public static class FilterLogAction implements ClickHandler{
		public void onClick(ClickEvent event) {
			FilterLogControl flc = new FilterLogControl();
			flc.dialog.submit.addClickHandler(flc);
			flc.dialog.show();
		}
		public class FilterLogControl extends DialogControl implements ClickHandler {
			FilterLogDialog dialog = new FilterLogDialog();
			public void onClick(ClickEvent event) {
				String cont = dialog.getValue();
				dialog.hide();
				ls.getLogByContent(cont, new AsyncCallback<Log[]>() {
					public void onSuccess(Log[] result) {
						INSTANCE.data.setSum(result.length);
						INSTANCE.resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result.length);
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			}
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}
	
	public static class FilterLogByKeyAction implements ClickHandler{
		public void onClick(ClickEvent event) {
			FilterLogByKeyControl flc = new FilterLogByKeyControl();
			flc.dialog.submit.addClickHandler(flc);
			flc.dialog.show();
		}
		public class FilterLogByKeyControl extends DialogControl implements ClickHandler {
			FilterLogDialog dialog = new FilterLogDialog();
			public void onClick(ClickEvent event) {
				String cont = dialog.getValue();
				dialog.hide();
				ls.getLogByKey(cont, new AsyncCallback<Log[]>() {
					public void onSuccess(Log[] result) {
						INSTANCE.data.setSum(result.length);
						INSTANCE.resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result.length);
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			}
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}
	
	@Override
	public void loadChild(String id, String value, int childPagePoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPagePoint(int point) {
		pagePoint = point;
	}

	@Override
	public int getPagePoint() {
		return pagePoint;
	}

	@Override
	public void setChildPagePoint(int point) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getChildPagePoint() {
		// TODO Auto-generated method stub
		return 0;
	}

}
