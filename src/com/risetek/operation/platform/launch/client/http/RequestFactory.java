package com.risetek.operation.platform.launch.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.risetek.operation.platform.launch.client.util.Util;


public class RequestFactory {

	private final String baseUrl;
	private Request request;
	public static final String CTI_PACKET="CTI_PACKET";
	
	private final String commandJCard = "JCardServer/jcardServer!process.do";
	
	private String SIGNATURE = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
	
	private final String base007Url;
	
	public RequestFactory(){
//		this.baseUrl = "http://192.168.6.9:8089/billServer/billCenter!request.do";
		this.baseUrl = "http://125.69.69.135:8089/billServer/billCenter!request.do";
		this.base007Url = "http://125.69.69.135:8089/007ka/kaServer!process.do";
	}
	
	private class hookRequestCallback implements RequestCallback {

		private RequestCallback hooker;
		
		public hookRequestCallback(RequestCallback handler){
			hooker = handler;
		}
		
		@Override
		public void onResponseReceived(Request request, Response response) {
			hooker.onResponseReceived(request, response);
		}

		@Override
		public void onError(Request request, Throwable exception) {
			hooker.onError(request, exception);
		}
		
	}
	
	public void get( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}
		RequestBuilder builder;
		
		if( query != null )
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path+"?"+query);
		else
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path);

		builder.setTimeoutMillis(3000);
		builder.setHeader("Content-Type", "text/plain; charset=GB2312" );
		
		try{
			request = builder.sendRequest( null, handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
	
	public void post(String path, String query, RequestCallback callback) {
		if (request != null && request.isPending()) {
			request.cancel();
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				baseUrl + "/" + path);

		if (query != null) {
			builder.setHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
		}
		try {
			request = builder.sendRequest(query, callback);
		} catch (RequestException e) {
			GWT.log("error", e);
		}
	}
	
	/**
	 * 骏网请求
	 */
	public void getJCard(String text, RequestCallback callback) {
		get(commandJCard, Util.string2unicode(text), callback);
	}
	
	public void postJCard(String text, RequestCallback callback) {
		post(commandJCard, Util.string2unicode(text), callback);
	}
	//*********************************************************//
	public void get007(String query, RequestCallback handler) {
		if (request != null && request.isPending()) {
			request.cancel();
		}
		RequestBuilder builder;
		if (query != null) {
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl + "?" + query);
		} else {
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl);
		}
		
		builder.setTimeoutMillis(3000);
		builder.setHeader("Content-Type", "text/plain; charset=UTF-8");

		try {
			request = builder.sendRequest(null, handler);
		} catch (RequestException e) {
			GWT.log("error", e);
		}
	}
	
	public void send007(String query,RequestCallback handler){
		if (request != null && request.isPending()) {
			request.cancel();
		}
		RequestBuilder builder;
		if (query != null) {
			builder = new RequestBuilder(RequestBuilder.GET, base007Url +"?" + query);
		} else {
			builder = new RequestBuilder(RequestBuilder.GET, base007Url);
		}
		
		builder.setTimeoutMillis(3000);
		builder.setHeader("Content-Type", "text/plain; charset=UTF-8");

		try {
			request = builder.sendRequest(null, handler);
		} catch (RequestException e) {
			GWT.log("error", e);
		}
	}
}
