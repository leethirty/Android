package com.ducer.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class URLService {
	public static Bundle getURLInfo(String urlStr, String id, String mac)
			throws ParseException, IOException, JSONException {
		StringBuffer buffer = new StringBuffer(urlStr);
		buffer.append("?id=");
		buffer.append(id);
		buffer.append("&mac=" + mac);
		HttpGet request = new HttpGet(buffer.toString());
		HttpResponse response = new DefaultHttpClient().execute(request);
		response.getStatusLine().getStatusCode();

		if (response.getStatusLine().getStatusCode() == 200) {
			String content = EntityUtils.toString(response.getEntity());

			if (content != null) {

				JSONObject jsonObject = new JSONObject(content);

				String audioUrl = jsonObject.getString("audioUrl").toString();
				String imageTable = jsonObject.getString("imageTable")
						.toString();
				String videoUrl = jsonObject.getString("videoUrl").toString();
				String messageTable = jsonObject.getString("messageTable")
						.toString();
				String title = jsonObject.getString("title_content").toString();
				Bundle bundle = new Bundle();
				bundle.putString("audioUrl", audioUrl);
				bundle.putString("imageTable", imageTable);
				bundle.putString("videoUrl", videoUrl);
				bundle.putString("messageTable", messageTable);
				bundle.putString("title", title);

				/*
				 * 导航 统计功能
				 */
				int status = jsonObject.getInt("STATUS");
				if (status == 100) {
					JSONArray jsonArray = jsonObject.getJSONArray("array");
					for (int i = 0; i < jsonArray.length() - 1; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						String position = obj.getString("position");
						String detail = obj.getString("detail");
						String advice = obj.getString("advice");

						bundle.putString("position", position);
						bundle.putString("detail", detail);
						bundle.putString("advice", advice);
					}
				} else {
					bundle.putString("STATUS", "0");
				}

				return bundle;
			}
		}
		return null;
	}
}
