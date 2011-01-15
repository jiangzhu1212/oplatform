package com.risetek.test.appeng.file.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;

import com.newatlanta.commons.vfs.provider.gae.GaeVFS;

public class FileDownServiceImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rootPath = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		if(rootPath==null){
			rootPath = this.getServletContext().getRealPath("/");
			GaeVFS.setRootPath(rootPath);
		}
		try {
			String path = req.getParameter("path");
			FileObject file = GaeVFS.resolveFile("gae:/" + path);
//			String contentType = getServletContext().getMimeType(file.getName().getBaseName());
//			resp.setContentType(contentType != null ? contentType : file.getContent().getContentInfo().getContentType());
//			OutputStream out = resp.getOutputStream();
			InputStream in = file.getContent().getInputStream();
//			Streams.copy(in, out, true);
//			resp.flushBuffer();
			
			String name = path.substring(path.lastIndexOf("/")+1, path.length());
			resp.setContentType("application/x-download");
			resp.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(name,"UTF-8"));  
			   
			ServletOutputStream sout = resp.getOutputStream(); 

			byte b[] = new byte[1024 * 8];
			for (int i = in.read(b); i != -1;) {
				sout.write(b);
				i = in.read(b);
			}
			sout.flush();
			sout.close();
			in.close(); 

		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GaeVFS.clearFilesCache();
		}
	}

}
