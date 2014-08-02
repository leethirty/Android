package com.ducer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class SuggestService {

	public static boolean insertSuggest(String insrtUrlStr,
			String suggest, String qq) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(insrtUrlStr);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("suggest",
				suggest));
		params.add(new BasicNameValuePair("qq", qq));
		java.text.DateFormat dataFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String s = dataFormat.format(new Date());
		params.add(new BasicNameValuePair("time", s));
		request.setEntity(new UrlEncodedFormEntity(params,
				HTTP.UTF_8));
		HttpResponse response = new DefaultHttpClient()
				.execute(request);
		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}
		return false;
	}
}
