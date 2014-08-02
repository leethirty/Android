package com.ducer.tools;

import android.app.Application;

public class MyApplication extends Application {

	private String id;
	private String audioUrl;
	private String imageTable;
	private String videoUrl;
	private String messageTable;
	private String title;
	private int videoTime;

	public int getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(int videoTime) {
		this.videoTime = videoTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void onCreate() {
		title = null;
		id = null;
		audioUrl = null;
		imageTable = null;
		videoUrl = null;
		messageTable = null;
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getImageTable() {
		return imageTable;
	}

	public void setImageTable(String imageTable) {
		this.imageTable = imageTable;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getMessageTable() {
		return messageTable;
	}

	public void setMessageTable(String messageTable) {
		this.messageTable = messageTable;
	}
}
