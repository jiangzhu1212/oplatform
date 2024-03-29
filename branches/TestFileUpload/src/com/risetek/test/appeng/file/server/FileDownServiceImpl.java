package com.risetek.test.appeng.file.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;

import com.amazonaws.services.s3.model.S3Object;
import com.risetek.test.appeng.file.client.Util;

public class FileDownServiceImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private String rootPath = null;
	S3Action s3a = new S3Action();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		downFile(req, resp, null);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getParameter("path");
		downFile(req, resp, path);
	}
	
	private void downFile(HttpServletRequest req, HttpServletResponse resp, String path){
		String key = req.getParameter("path");
		S3Object object = s3a.downFile(key);
//		String type = object.getObjectMetadata().getContentType();
		long contentLength = object.getObjectMetadata().getContentLength();
		String length = Long.toString(contentLength);
		resp.setContentLength(Integer.parseInt(length));
//		resp.setContentType(type);
		String[] temp = key.split("_");
		String name = "";
		for(int i=0;i<temp.length-1;i++){
			name += temp[i];
		}
		String endName = name.substring(name.lastIndexOf(".")+1, name.length());
		if(endName.equals("apk")){
			resp.setContentType(Util.CONTENTTYPE_APK);
		} else if (endName.equals("mp3")){
			resp.setContentType(Util.CONTENTTYPE_MP3);
		} else {
			resp.setContentType("application/x-download");
		}
		try {
			InputStream in = object.getObjectContent();
			OutputStream out = resp.getOutputStream();
			Streams.copy(in, out, true);
			resp.flushBuffer();
			resp.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(name,"gb2312"));
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(rootPath==null){
//			rootPath = this.getServletContext().getRealPath("/");
//			GaeVFS.setRootPath(rootPath);
//		}
//		try {
//			if(path == null){
//				path = req.getParameter("path");
//			}
//			FileObject file = GaeVFS.resolveFile("gae:/" + path);
//			String contentType = getServletContext().getMimeType(file.getName().getBaseName());
//			resp.setContentType(contentType != null ? contentType : file.getContent().getContentInfo().getContentType());
//			OutputStream out = resp.getOutputStream();
//			InputStream in = file.getContent().getInputStream();
//			Streams.copy(in, out, true);
//			resp.flushBuffer();
			
//			String name = path.substring(path.lastIndexOf("/")+1, path.length());
//			String endName = name.substring(name.lastIndexOf(".")+1, name.length());
//			if(endName.equals("apk")){
//				resp.setContentType(Util.CONTENTTYPE_APK);
//			} else if (endName.equals("mp3")){
//				resp.setContentType(Util.CONTENTTYPE_MP3);
//			} else {
//				resp.setContentType("application/x-download");
//			}
//			resp.setContentType("audio/x-mpeg");
//			resp.setContentType("application/x-download");
//			resp.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(name,"gb2312"));
//			String temp = Long.toString(file.getContent().getSize());
//			int length = Integer.parseInt(temp);
//			resp.setContentLength(length);
			   
//			ServletOutputStream sout = resp.getOutputStream(); 
//
//			byte b[] = new byte[1024 * 8];
//			for (int i = in.read(b); i != -1;) {
//				sout.write(b);
//				i = in.read(b);
//			}
//			sout.flush();
//			sout.close();
//			in.close(); 

//		} catch (FileSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			GaeVFS.clearFilesCache();
//		}
	}
}
