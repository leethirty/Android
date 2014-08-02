package com.ducer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MessageService {
	
	public static String getRestMessage(String urlPath, String messageTableName,
			String  clientTotalCount) throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost(urlPath);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("messageTableName", messageTableName));
		params.add(new BasicNameValuePair("clientTotalCount", clientTotalCount));
		
		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse response = new DefaultHttpClient().execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		}
		return null;
	}

}
