package com.risetek.test.appeng.file.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServiceImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private FileObject rootFolder;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
//		GaeVFS.setRootPath(this.getServletContext().getRealPath("/"));
//		try {
//			rootFolder = GaeVFS.resolveFile("gae://main");
//			if(!rootFolder.exists()){
//				rootFolder.createFolder();
//			}
//		} catch (FileSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
//		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//		GWT.log("is multipart? " + Boolean.toString(isMultipart), null);
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();
		req.setCharacterEncoding("utf-8");
		PrintWriter jout = resp.getWriter();
		S3Action s3a = new S3Action();
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) { 
				FileItemStream item = iter.next();
				s3a.uploadFile(item);
//				String fileName = item.getName();
//				InputStream stream = item.openStream();
//				BufferedInputStream inputStream = new BufferedInputStream(stream);// 获得输入流
//				FileObject fileObject = rootFolder.resolveFile(fileName);
//				FileObject fileObject = GaeVFS.resolveFile("/" + fileName);
//				OutputStream out = fileObject.getContent().getOutputStream();
//				BufferedOutputStream outputStream = new BufferedOutputStream(out);// 获得文件输出流
//				Streams.copy(inputStream, outputStream, true); // 开始把文件写到你指定的上传文件夹
//				out.close();
//				String name = item.getFieldName();
				
//				if (item.isFormField()) {
//					System.out.println("Form field " + name + " with value "
//							+ Streams.asString(stream) + " detected.");
//				} else {
//					System.out.println("File field " + name
//							+ " with file name " + item.getName()
//							+ " detected.");
					
//				}
//				stream.close();
			}
//			System.out.println("上传成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			jout.print("exception");
		} finally{
//			GaeVFS.clearFilesCache();
		}
//		try {
//			FileObject fileObject = GaeVFS.resolveFile("gae://");
//			fileObject.getChildren();
//		} catch (FileSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		PrintWriter jout = resp.getWriter();
//		if (ok) {
//			jout.print("file ok");
//		} else {
//			jout.print("404");
//		}
	}
}
