package com.risetek.operation.platform.base.client.control;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.operation.platform.base.client.dialog.NoticeAddDialog;
import com.risetek.operation.platform.base.client.dialog.NoticeEditDialog;
import com.risetek.operation.platform.base.client.model.RoleConfigData;
import com.risetek.operation.platform.base.client.model.SystemNoticeData;
import com.risetek.operation.platform.base.client.service.NoticeService;
import com.risetek.operation.platform.base.client.service.NoticeServiceAsync;
import com.risetek.operation.platform.base.client.service.RoleService;
import com.risetek.operation.platform.base.client.service.RoleServiceAsync;
import com.risetek.operation.platform.base.client.view.SystemNoticeView;
import com.risetek.operation.platform.launch.client.config.UIConfig;
import com.risetek.operation.platform.launch.client.control.AController;
import com.risetek.operation.platform.launch.client.control.DialogControl;
import com.risetek.operation.platform.launch.client.dialog.CustomDialog;
import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.client.entry.Role;
import com.risetek.operation.platform.launch.client.model.OPlatformData;
import com.risetek.operation.platform.launch.client.view.OPlatformTableView;

public class SystemNoticeController extends AController {

	private final static NoticeServiceAsync ns = GWT.create(NoticeService.class);
	private final static RoleServiceAsync rs = GWT.create(RoleService.class);
	
	public static SystemNoticeController INSTANCE = new SystemNoticeController();
	public final SystemNoticeView view = new SystemNoticeView();
	
	final SystemNoticeData data = new SystemNoticeData();
	final RoleConfigData roleData = new RoleConfigData();
	private int pagePoint = 1;
	
	@Override
	public OPlatformTableView getView() {
		return view;
	}

	@Override
	public OPlatformData getData() {
		return data;
	}

	@Override
	public OPlatformData getChildData() {
		return null;
	}

	@Override
	public void load(int pagePoint) {
		rs.getAllRole(new AsyncCallback<Role[]>() {
			public void onSuccess(Role[] result) {
				INSTANCE.roleData.parseResult(result);
				ns.getNoticeDataCount(new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						INSTANCE.data.setSum(result);
						resetPagePanel(INSTANCE, UIConfig.TABLE_ROW_NORMAL, result);
					}
					public void onFailure(Throwable caught) {}
				});
				ns.getNoticePage(UIConfig.TABLE_ROW_NORMAL, new AsyncCallback<Notice[]>() {
					public void onSuccess(Notice[] result) {
						INSTANCE.data.parseResult(result);
						INSTANCE.view.render(INSTANCE.data);
					}
					public void onFailure(Throwable caught) {}
				});
			}
			public void onFailure(Throwable caught) {}
		});
	}

	@Override
	public void loadChild(String id, String value, int childPagePoint) {
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
	public void setChildPagePoint(int point) {}

	@Override
	public int getChildPagePoint() {return 0;}

	public static class AddNoticeAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			AddNoticeControl addNoticeCont = new AddNoticeControl();
			addNoticeCont.dialog.submit.addClickHandler(addNoticeCont);
			addNoticeCont.dialog.show();
		}
		
		public class AddNoticeControl extends DialogControl implements ClickHandler {
			NoticeAddDialog dialog = new NoticeAddDialog(INSTANCE.roleData);
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					Notice notice = dialog.getValue();
					ns.addNotice(notice, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							INSTANCE.load(INSTANCE.getPagePoint());
						}
						public void onFailure(Throwable caught) {}
					});
					dialog.hide();
				} else {
					dialog.submit.setEnabled(false);
				}
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}
	
	public static class EditNoticeAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Notice notice = new Notice();
			Grid grid = INSTANCE.view.grid;
			int flag = 0;
			for(int i=1;i<grid.getRowCount();i++){
				CheckBox box = (CheckBox)grid.getWidget(i, 0);
				if(box!=null){
					if(box.getValue()){
						String id = grid.getText(i, 2);
						notice.setId(Integer.parseInt(id));
						notice.setContent(grid.getText(i, 3));
						notice.setRoles(grid.getText(i, 4));
						notice.setEffectTime(getTimeStamp(grid.getText(i, 5)));
						notice.setFailureTime(getTimeStamp(grid.getText(i, 6)));
						flag++;
					}
				}
			}
			if(flag>1){
				Window.alert("只允许处理一条记录！");
				for(int i=1;i<grid.getRowCount();i++){
					CheckBox box = (CheckBox)grid.getWidget(i, 0);
					if(box!=null){
						if(box.getValue()){
							box.setValue(false);
						}
					}
				}
				return;
			} else if(flag==0){
				Window.alert("未选择任何记录！");
				return;
			}
			EditNoticeControl editNoticeCont = new EditNoticeControl(notice);
			editNoticeCont.dialog.submit.addClickHandler(editNoticeCont);
			editNoticeCont.dialog.show();
		}
		
		public class EditNoticeControl extends DialogControl implements ClickHandler {
			NoticeEditDialog dialog;
			public EditNoticeControl(Notice notice) {
				dialog = new NoticeEditDialog(INSTANCE.roleData, notice);
			}
			public void onClick(ClickEvent event) {
				if(dialog.isValid()){
					Notice notice = dialog.getValue();
					ns.editNotice(notice, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							INSTANCE.load(INSTANCE.getPagePoint());
						}
						public void onFailure(Throwable caught) {}
					});
					dialog.hide();
				} else {
					dialog.submit.setEnabled(false);
				}
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
			
		}
	}

	public static Timestamp getTimeStamp(String text) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(UIConfig.NOW_TIME_FORMAT);
		Date date = dateFormat.parse(text);
		Timestamp time = new Timestamp(date.getTime());
		return time;
	}
}
