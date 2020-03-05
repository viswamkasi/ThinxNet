package com.qa.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class GetAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpClient httpClient;
	HttpPost httppost;
	CloseableHttpResponse closeableHttpResponse; //hit the GET URL

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");

		url = serviceUrl + apiUrl;
		httppost = new HttpPost(url);
		httpClient = HttpClients.createDefault();

	}

	@Test
	public void getAPITest() throws ClientProtocolException, IOException{

		restClient = new RestClient();
		closeableHttpResponse = httpClient.execute(httppost);	
		Assert.assertEquals ( closeableHttpResponse.getStatusLine().getStatusCode(), 200);
	}


	@Test
	public void getAPITest2() throws Exception {

		String json_input = "{\"company\":{\"company_name\":\"Test1 company\", \"company_phone\":\"004912345789\", \"company_street\":\"First Strasse\", \"company_house_number\":\"1\", \"company_postcode\":\"10551\", \"company_city\":\"Berlin\", \"company_county\":\"Berlin\", \"company_country\":\"Germany\", \"company_account_status\":\"1\", \"payment_id\":\"1\", \"plan_id\":\"1\"}, \"user\":{\"email\":\"test2@test2.de\", \"password\":\"test1234\", \"role_id\":\"1\"}}";

		JSONObject json = new JSONObject();
		json.put("company", "{\"company_name\":\"Test1 company\", \"company_phone\":\"004912345789\", \"company_street\":\"First Strasse\", \"company_house_number\":\"1\", \"company_postcode\":\"10551\", \"company_city\":\"Berlin\", \"company_county\":\"Berlin\", \"company_country\":\"Germany\", \"company_account_status\":\"1\", \"payment_id\":\"1\", \"plan_id\":\"1\"}");    

		System.out.println("json---->"+ json.getString("company"));


		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("responseString---->"+ responseString);


		JSONObject responseJson = new JSONObject(responseString);

		String token = responseJson.getJSONObject("data").getJSONObject("token").getString("token");

		// Sending get request
		URL url = new URL("http://127.0.0.1:3333/api/v1/register");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		httppost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		System.out.println("responseString---->"+ token);


		conn.setRequestProperty("Authorization","Bearer "+ token );

		conn.setRequestProperty("Content-Type","application/json");
		conn.setRequestMethod("POST");


		BufferedReader in = new BufferedReader(new
				InputStreamReader(conn.getInputStream())); String output;

				StringBuffer response = new StringBuffer(); while ((output = in.readLine())
						!= null) { response.append(output); }

				in.close(); 
				// printing result from response 
				System.out.println("Response:-"+ response.toString());
	}  
}


