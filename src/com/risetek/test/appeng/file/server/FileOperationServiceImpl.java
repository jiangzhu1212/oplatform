package com.risetek.test.appeng.file.server;

import java.util.ArrayList;

//import org.apache.commons.vfs.FileObject;
//import org.apache.commons.vfs.FileSystemException;
//import org.apache.commons.vfs.FileType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.newatlanta.commons.vfs.provider.gae.GaeVFS;
import com.risetek.test.appeng.file.client.FileEntry;
import com.risetek.test.appeng.file.client.service.FileOperationService;

public class FileOperationServiceImpl extends RemoteServiceServlet implements FileOperationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rootPath = null;
	
	@Override
	public FileEntry[] listFile() {
		if(rootPath==null){
			rootPath = this.getServletContext().getRealPath("/");
//			GaeVFS.setRootPath(rootPath);
		}
//		ArrayList<FileObject> list = new ArrayList<FileObject>();
//		try {
//			FileObject baseObject = GaeVFS.resolveFile("gae://");
//			FileObject[] fileList = baseObject.getChildren();
//			for(int i=0;i<fileList.length;i++){
//				FileObject temp = fileList[i];
//				if(temp.getType() == FileType.FILE){
//					list.add(temp);
//				} else if(temp.getType() == FileType.FOLDER){
//					FileObject[] tempList = getChildFile(temp);
//					for(int a=0;a<tempList.length;a++){
//						list.add(tempList[a]);
//					}
//				}
//			}
//		} catch (FileSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		FileObject[] result;
//		if(list.isEmpty()){
//			result = new FileObject[0];
//		} else {
//			result = new FileObject[list.size()];
//		}
//		for(int i=0;i<result.length;i++){
//			result[i] = list.get(i);
//		}
		FileEntry[] entryList = new FileEntry[0];//[result.length];
//		for(int i=0;i<entryList.length;i++){
//			FileObject fileObject = result[i];
//			FileEntry entry = new FileEntry();
//			String path = fileObject.getName().toString();
//			String name = path.substring(path.lastIndexOf("/")+1, path.length());
//			entry.setName(name);
//			try {
//				long size = fileObject.getContent().getSize();
//				entry.setSize(Long.toString(size));
//				String floderPath = fileObject.getParent().getName().toString();
//				String floder = floderPath.substring(floderPath.lastIndexOf("/"), floderPath.length());
//				if(floder.equals("/war")){
//					floder = "/";
//				}
//				entry.setFloder(floder);
//			} catch (FileSystemException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			double modTime = fileObject.getFileSystem().getLastModTimeAccuracy();
//			entry.setCreatTime(Double.toString(modTime));
//			entryList[i] = entry;
//		}
		return entryList;
	}

//	private FileObject[] getChildFile(FileObject tempFile) throws FileSystemException {
//		ArrayList<FileObject> list = new ArrayList<FileObject>();
//		FileObject[] fileList = tempFile.getChildren();
//		for(int i=0;i<fileList.length;i++){
//			FileObject temp = fileList[i];
//			if(temp.getType() == FileType.FILE){
//				list.add(temp);
//			} else if(temp.getType() == FileType.FOLDER){
//				FileObject[] tempList = getChildFile(temp);
//				for(int a=0;a<tempList.length;a++){
//					list.add(tempList[a]);
//				}
//			}
//		}
//		FileObject[] result;
//		if(list.isEmpty()){
//			result = new FileObject[0];
//		} else {
//			result = new FileObject[list.size()];
//		}
//		for(int i=0;i<result.length;i++){
//			result[i] = list.get(i);
//		}
//		return result;
//	}

	@Override
	public FileEntry[] deleteFile(String path) {
//		if(rootPath==null){
//			rootPath = this.getServletContext().getRealPath("/");
//			GaeVFS.setRootPath(rootPath);
//		}
//		try {
//			FileObject file = GaeVFS.resolveFile("gae:/" + path);
//			if(file.exists()){
//				file.delete();
//			}
//		} catch (FileSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			GaeVFS.clearFilesCache();
//		}
		return listFile();
	}

}
