package com.risetek.operation.platform.launch.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


public class RequestFactory {

	private final String baseUrl;
	private Request request;
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
	
	public void get(String path, String query, RequestCallback handler){
		if(request != null && request.isPending()){
			request.cancel();
		}
		String url = baseUrl + path;
		if(query != null){
			url += "?BILLS_REQUEST=" + query;
		}
		JSONObject root = new JSONObject();
		JSONString actionName = new JSONString("SELECT_CUSTOMER");
		root.put("ACTION_NAME", actionName);
		JSONObject info = new JSONObject();
		JSONString value = new JSONString("200");
		info.put("CUSTOMER_ID", value);
		root.put("ACTION_INFO", info);
		url += root.toString() + SIGNATURE;
		System.out.println(url);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setTimeoutMillis(30000);
		builder.setHeader("Content-Type", "text/plain; charset=UTF-8");
		try{
			request = builder.sendRequest(null, new hookRequestCallback(handler));
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
	
	public void get0(String path, String query, RequestCallback handler) {
		if (request != null && request.isPending()) {
			request.cancel();
		}
		RequestBuilder builder;

		String url = baseUrl + path;
		if(query != null){
			url += "?BILLS_REQUEST=" + query;
		}
		JSONObject root = new JSONObject();
		JSONString actionName = new JSONString("SELECT_CUSTOMER");
		root.put("ACTION_NAME", actionName);
		JSONObject info = new JSONObject();
		JSONString value = new JSONString("200");
		info.put("CUSTOMER_ID", value);
		root.put("ACTION_INFO", info);
		url += root.toString() + SIGNATURE;
		System.out.println(url);
		builder = new RequestBuilder(RequestBuilder.GET, url);

		builder.setTimeoutMillis(3000);
		builder.setHeader("Content-Type", "text/plain;");

		try {
			request = builder.sendRequest(null, handler);
		} catch (RequestException e) {
			GWT.log("error", e);
		}
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
