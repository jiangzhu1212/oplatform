package com.risetek.test.appeng.file.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.test.appeng.file.client.service.FileOperationService;
import com.risetek.test.appeng.file.client.service.FileOperationServiceAsync;

public class TestFileUpload implements EntryPoint {
	
	FileUpload selectFile = new FileUpload();
	Grid fileList = new Grid(1, 6);
	private final static FileOperationServiceAsync fo = GWT.create(FileOperationService.class);
	
	public void onModuleLoad() {
		Grid grid = new Grid(1, 2);
		RootPanel.get("maintable").add(grid);
		grid.getCellFormatter().setWidth(0, 0, "80%");
		grid.getCellFormatter().setWidth(0, 1, "20%");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		grid.setWidth("100%");
		Button upload = new Button("上传文件");
		grid.setWidget(0, 1, upload);
		final FormPanel form = new FormPanel();
		form.setStyleName("form");
		form.add(selectFile);
		selectFile.setName("selectFile");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setAction(GWT.getModuleBaseURL() + "uploadFile");
		
		grid.setWidget(0, 0, form);
		
		upload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();				
			}
		});
		form.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				boolean upload = true;
				if (selectFile.getFilename().isEmpty()) {
					upload = false;
//					Window.alert("未选择上传文件！");
				} else {
//					String fileName = selectFile.getFilename();
//					if(!fileName.endsWith("xml")&&!fileName.endsWith("dbc")){
//						upload = false;
//						Window.alert("文件格式不正确！");
//					}
				}
				if(!upload){
					event.cancel();
				}
			}
		});

		// 成功上传
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String ret = event.getResults();
				if (ret.indexOf("exception") != -1) { // error
					Window.alert("文件上传错误");
					// labmsg.setText("文件上传错误");
				} else if (ret.indexOf("exists") != -1) {
//					Window.alert("上传的文件已经存在！\n\t请删除旧文件后，再次上传。");
				} else {
//					Window.alert("文件上传成功");
//					Window.Location.reload();
				}
				
				// labmsg.setText("文件上传成功");
				selectFile.removeFromParent();
				selectFile = new FileUpload();
				selectFile.setName("selectFile");
				form.add(selectFile);
				fo.listFile(new AsyncCallback<FileEntry[]>() {
					
					@Override
					public void onSuccess(FileEntry[] result) {
						refshFileList(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		Button refsh = new Button("刷新");
		refsh.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fo.listFile(new AsyncCallback<FileEntry[]>() {
					
					@Override
					public void onSuccess(FileEntry[] result) {
						refshFileList(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(refsh);
		vp.add(fileList);
		vp.setWidth("100%");
		vp.setCellHorizontalAlignment(refsh, HasHorizontalAlignment.ALIGN_CENTER);
		vp.setCellHorizontalAlignment(fileList, HasHorizontalAlignment.ALIGN_CENTER);
		formatGridTitle();
		
		RootPanel.get("fileList").add(vp);
		
		fo.listFile(new AsyncCallback<FileEntry[]>() {
			
			@Override
			public void onSuccess(FileEntry[] result) {
				refshFileList(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void formatGridTitle(){
		fileList.setText(0, 0, "编号");
		fileList.setText(0, 1, "文件名");
		fileList.setText(0, 2, "文件大小");
		fileList.setText(0, 3, "所属目录");
		fileList.setText(0, 4, "上传日期");
		fileList.setText(0, 5, "操作");
		fileList.getCellFormatter().setWidth(0, 0, "40px");
		fileList.getCellFormatter().setWidth(0, 2, "100px");
		fileList.getCellFormatter().setWidth(0, 5, "100px");
		for(int i=0;i<fileList.getColumnCount();i++){
			fileList.getCellFormatter().setHorizontalAlignment(0, i, HasHorizontalAlignment.ALIGN_CENTER);
		}
		fileList.setWidth("90%");
		fileList.setBorderWidth(1);
	}
	
	private void refshFileList(FileEntry[] result){
		fileList.resize(result.length+1, 6);
		formatGridTitle();
		for(int i=0;i<result.length;i++){
			final FileEntry entry = result[i];
			fileList.setText(i+1, 0, Integer.toString(i+1));
			fileList.setText(i+1, 1, entry.getName());
			fileList.setText(i+1, 2, entry.getSize()+"字节");
			fileList.setText(i+1, 3, entry.getFloder());
			fileList.setText(i+1, 4, entry.getCreatTime());
			
			Button delFile = new Button("删除");
			Button down = new Button("下载");
			
			Grid operationGrid = new Grid(1, 3);
			operationGrid.setWidget(0, 0, delFile);
			operationGrid.setWidget(0, 1, down);
			
			final Hidden hidden = new Hidden("path", entry.getFloder() + "/" + entry.getName());
			
			operationGrid.setWidget(0, 2, hidden);
			
			final FormPanel listForm = new FormPanel();
			listForm.setEncoding(FormPanel.ENCODING_URLENCODED);
			listForm.setMethod(FormPanel.METHOD_POST);
			listForm.setAction(GWT.getModuleBaseURL() + "down");
			listForm.add(operationGrid);
			
			fileList.setWidget(i+1, 5, listForm);
			fileList.getCellFormatter().setHorizontalAlignment(i+1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			fileList.getCellFormatter().setHorizontalAlignment(i+1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
			fileList.getCellFormatter().setHorizontalAlignment(i+1, 3, HasHorizontalAlignment.ALIGN_CENTER);
			fileList.getCellFormatter().setHorizontalAlignment(i+1, 4, HasHorizontalAlignment.ALIGN_CENTER);
			fileList.getCellFormatter().setHorizontalAlignment(i+1, 5, HasHorizontalAlignment.ALIGN_CENTER);
			
			fileList.getCellFormatter().getElement(i+1, 1).setId("name");
			fileList.getCellFormatter().getElement(i+1, 3).setId("floder");
			
			down.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String url = GWT.getModuleBaseURL() + "down";
					url+= "?path=" + entry.getFloder() + "/" + entry.getName();
					Window.open(url, "_blank", "");
//					listForm.submit();
				}
			});
			
			delFile.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					String path = hidden.getValue();
					fo.deleteFile(path, new AsyncCallback<FileEntry[]>() {
						
						@Override
						public void onSuccess(FileEntry[] result) {
							refshFileList(result);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
		}
	}
}
