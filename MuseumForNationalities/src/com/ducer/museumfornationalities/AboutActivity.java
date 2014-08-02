package com.ducer.museumfornationalities;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
