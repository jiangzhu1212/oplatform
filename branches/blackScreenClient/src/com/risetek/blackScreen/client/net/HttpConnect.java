package com.risetek.blackScreen.client.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class HttpConnect {
	
	private HttpURLConnection hconn = null;
	private OutputStream os = null;
	private InputStream is = null;
	
	public HttpConnect(String connectUrl) throws MalformedURLException, IOException{
		hconn = (HttpURLConnection)new URL(connectUrl).openConnection();
		hconn.setRequestMethod("POST"); //设置为post请求
		hconn.setDoInput(true);
		hconn.setDoOutput(true);
		hconn.setRequestProperty("Content-Type", "application/json");
	}
	
	public void SendData(String data) throws IOException{
		byte [] f = data.getBytes("UTF-8");
		os = hconn.getOutputStream();
		os.write(f,0,f.length);
		os.flush();
		os.close();
	}
	
	public String ReceiveData() throws IOException{
		is = new DataInputStream(hconn.getInputStream());
		StringBuffer reMsg = new StringBuffer();   
		int   ch;
		while((ch=is.read()) != -1){
			reMsg.append((char)ch);
		}
		return reMsg.toString();
	}
	
	public String ReceiveUTFString() throws IOException{
		is = new DataInputStream(hconn.getInputStream());
		Vector<byte[]> bufLst = new Vector<byte[]>();
		
		int len = 0;
		while (true) {
			byte[] buf = new byte[256];
			int l = is.read(buf, 1, 255);
			if ( l == -1) {
				break;
			}
			buf[0] = (byte) ((byte)l&0xFF);
			len += (int)buf[0] & 0xFF;
			bufLst.add(buf);
		}
		byte[] bigBuf = new byte[len];
		int off = 0;
		for (int i = 0; i < bufLst.size(); i++) {
			System.arraycopy(bufLst.get(i), 1, bigBuf, off, (int)bufLst.get(i)[0] & 0xFF);
			off += (int)bufLst.get(i)[0] & 0xFF;
		}
		return new String(bigBuf,"UTF-8");
	}
	
	public void releaseHttpConnect(){
		try{
			if (hconn != null){
				hconn.disconnect();
			}
			if (os != null){
				os.close();
			}
			if (is != null){
				is.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String BytesToString(byte[] rec) {
		 ByteArrayInputStream bais = new ByteArrayInputStream(rec);
		 DataInputStream dis = new DataInputStream(bais);
		 String BTS=null;
		 try {
			 BTS=new String(rec,"UTF-8");
			 bais.close();
			 dis.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return  BTS;
	}
	private void copy(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] buffer = new byte[4096];
			int nrOfBytes = -1;
			while ((nrOfBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
			} catch (IOException e) {
				
			}finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
					
				}try {
					if (out != null) {
						out.close();
					}
				} catch (IOException ex) {
					
				}
			}
	}
	public byte[] getFileByte(String fileName) {
		InputStream fileInputStream = getClass().getResourceAsStream(fileName);
		return getFileByte(fileInputStream);
	}
	
	public String getFileString(String fileName) {
		InputStream fileInputStream = getClass().getResourceAsStream(fileName);
		return getFileString(fileInputStream);
	}

	public String getFileString(InputStream in){
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try {
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
	public byte[] getFileByte(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try {
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	} 
}
