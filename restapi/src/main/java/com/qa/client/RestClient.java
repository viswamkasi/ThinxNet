package com.qa.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	
	//1. GET Method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		HttpPost httppost = new HttpPost(url);
		System.out.println("URL--->"+ url);

		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost); //hit the GET URL
		
		//a. Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+ statusCode);
		
		System.out.println("Status Code--->"+ closeableHttpResponse.toString());

		
		//b. Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("Response JSON from API---->"+ responseString);

		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API---->"+ responseJson.getJSONObject("data").getJSONObject("token").getString("token"));
	
		//c. All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array--->"+allHeaders);
		
		return closeableHttpResponse;
	}

}
