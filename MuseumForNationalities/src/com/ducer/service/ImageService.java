package com.ducer.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ImageService {
	public static String getImageURL(String image_urlStr, String imageTable) throws ClientProtocolException, IOException{
		String urlStr = image_urlStr + "?imageTableName="+imageTable;
		HttpGet request = new HttpGet( urlStr);
		
		HttpResponse  response = new DefaultHttpClient().execute(request);
		System.out.println(response.getStatusLine().getStatusCode());
		
		if (response.getStatusLine().getStatusCode() == 200) {
			String content = EntityUtils.toString(response.getEntity());
			return content;
		}
		return null;
	}
}
