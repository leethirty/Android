package com.ducer.museumfornationalities;

import java.util.Formatter;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ducer.tools.MyApplication;

public class VideoActivity extends Activity implements OnTouchListener {
	private String path;
	protected boolean played = false;
	private VideoView video_view;
	private RelativeLayout video_banner;
	private LinearLayout video_banner_contorller;
	private ImageButton video_pause;
	private TextView video_time;
	private TextView video_time_current;
	private SeekBar video_progress;
	StringBuilder mFormatBuilder;
	Formatter mFormatter;
	private static final int SHOW_PROGRESS = 1;
	private boolean mDragging = false;
	private boolean mShowing = true;
	private LinearLayout video_loading;
	private ImageButton video_stop;
	private ImageButton video_home;
	private ImageButton video_rotate;
	private TextView video_title;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int pos;
			switch (msg.what) {
			case SHOW_PROGRESS:
				pos = setProgress();
				if (!mDragging && mShowing && video_view.isPlaying()) {
					msg = obtainMessage(SHOW_PROGRESS);
					sendMessageDelayed(msg, 1000 - (pos % 1000));
				}
				break;
			}
		}
	};
	

	/**
	 * 设置进度条
	 * @return
	 */
	private int setProgress() {
		if (video_view == null || mDragging) {
			return 0;
		}
		int position = video_view.getCurrentPosition();
		int duration = video_view.getDuration();
		if (video_progress != null) {
			if (duration > 0) {
				/** 设置视频播放进度 */
				long pos = 1000L * position / duration;
				video_progress.setProgress((int) pos);
			}
			/** 设置视频进度，第二进度条 */
			int percent = video_view.getBufferPercentage();
			video_progress.setSecondaryProgress(percent * 10);
		}

		if (video_time != null)
			video_time.setText(stringForTime(duration));
		if (video_time_current != null)
			video_time_current.setText(stringForTime(position));

		return position;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		findView();
		getPath();
		setMedia();
		setListenerAndText();
	}

	private void setListenerAndText() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		video_view.setOnTouchListener(this);
		video_view.setKeepScreenOn(true);
		video_view.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				video_view.start();
				Toast.makeText(VideoActivity.this,
						getResources().getString(R.string.error_net),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		video_view.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				video_loading.setVisibility(View.GONE);
				setTime();
			}
		});

		video_pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (video_view.isPlaying()) {
					video_view.pause();
				} else {
					mHandler.sendEmptyMessage(SHOW_PROGRESS);
					video_view.start();
				}
				if (video_pause == null)
					return;

				if (video_view.isPlaying()) {
					video_pause.setImageResource(R.drawable.pause_selector);
				} else {
					video_pause.setImageResource(R.drawable.play_selector);
				}
			}
		});

		video_stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				video_view.seekTo(0);
				mHandler.sendEmptyMessage(SHOW_PROGRESS);
			}
		});

		video_progress.setEnabled(false);

		video_home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				VideoActivity.this.finish();
			}
		});
		video_rotate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (VideoActivity.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
					VideoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
					VideoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}else{
					VideoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		((MyApplication)getApplication()).setVideoTime(video_view.getCurrentPosition());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		video_view.seekTo(((MyApplication)getApplication()).getVideoTime());
	}

	private String stringForTime(int timeMs) {
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		int totalSeconds = timeMs / 1000;
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;
		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	private void setMedia() {
		if (path != null) {
			video_view.setVideoPath(path);
			video_view.start();
		} else {
			Toast.makeText(this, getResources().getString(R.string.error_url),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void getPath() {
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		String title = intent.getStringExtra("title");
		video_title.setText(title);
	}

	private void findView() {
		video_banner = (RelativeLayout) findViewById(R.id.video_banner);
		video_view = (VideoView) findViewById(R.id.video_view);
		video_banner_contorller = (LinearLayout) findViewById(R.id.video_banner_contorller);
		video_pause = (ImageButton) findViewById(R.id.video_pause);
		video_stop = (ImageButton) findViewById(R.id.video_stop);
		video_home = (ImageButton) findViewById(R.id.video_home);
		video_rotate = (ImageButton) findViewById(R.id.video_rotate);
		video_time = (TextView) findViewById(R.id.video_time);
		video_time_current = (TextView) findViewById(R.id.video_time_current);
		video_progress = (SeekBar) findViewById(R.id.video_progress);
		video_loading = (LinearLayout) findViewById(R.id.video_loading);
		video_title = (TextView) findViewById(R.id.video_title);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (video_banner.getVisibility() == View.VISIBLE) {
			video_banner.setVisibility(View.INVISIBLE);
			video_banner_contorller.setVisibility(View.INVISIBLE);
			mShowing = false;
		} else {
			video_banner.setVisibility(View.VISIBLE);
			video_banner_contorller.setVisibility(View.VISIBLE);
			setTime();
			mShowing = true;
		}
		return false;
	}

	private void setTime() {
		mHandler.sendEmptyMessage(SHOW_PROGRESS);
		long duration = video_view.getDuration();
		video_time.setText(stringForTime((int) duration));
		video_time_current.setText(stringForTime(video_view
				.getCurrentPosition()));
	}
}
