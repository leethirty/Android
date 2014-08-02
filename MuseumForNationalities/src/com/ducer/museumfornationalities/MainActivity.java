package com.ducer.museumfornationalities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ducer.qrcode.CaptureActivity;

public class MainActivity extends Activity implements OnTouchListener {
	/** 左边布局 */
	private LinearLayout layout_left;
	/** 右边布局 */
	private LinearLayout layout_right;
	/** 移动的范围 */
	private int MAX_WIDTH;
	/** 移动的速度 */
	private final static int SPEED = 10;
	/** 延迟时间 */
	private final static int SLEEP_TIME = 5;
	/** 滑块滑动距离 */
	private float scrollX = 0;
	/** 屏幕宽度 */
	private int window_width;
	/** 初始化视图位置，是否完成 */
	private boolean isFinished = false;

	PointF startPoint = new PointF();
	private ImageView i_setting;
	private ImageButton main_qrcode;
	private ImageView main_qrcode_banner;
	private ImageButton main_share;
	private ImageButton main_suggest;
	private ImageButton main_quit;
	private ImageButton zang_video_main;
	private ImageButton museum_main;
	private ScrollView about_museum;
	private ImageButton yi_video_main;
	private ImageButton qiang_video_main;
	private ImageButton naxi_video_main;
	private ImageButton main_about;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		setAnimation();
		setListener();
		initViewPosition();
	}

	private void setAnimation() {
		zang_video_main.setVisibility(View.INVISIBLE);
		yi_video_main.setVisibility(View.INVISIBLE);
		qiang_video_main.setVisibility(View.INVISIBLE);
		naxi_video_main.setVisibility(View.INVISIBLE);
		museum_main.setVisibility(View.INVISIBLE);
		 Animation animation_zang_main = AnimationUtils.loadAnimation(this, R.anim.animation_zang_main);
		 Animation animation_yi_main = AnimationUtils.loadAnimation(this, R.anim.animation_yi_main);
		 Animation animation_qiang_main = AnimationUtils.loadAnimation(this, R.anim.animation_qiang_main);
		 Animation animation_naxi_main = AnimationUtils.loadAnimation(this, R.anim.animation_naxi_main);
		 Animation animation_museum_main = AnimationUtils.loadAnimation(this, R.anim.animation_museum_main);
		 new AnimationZangAsyncTask().execute(animation_zang_main);
		 new AnimationYiAsyncTask().execute(animation_yi_main);
		 new AnimationQiangAsyncTask().execute(animation_qiang_main);
		 new AnimationNaxiAsyncTask().execute(animation_naxi_main);
		 new AnimationMuseumAsyncTask().execute(animation_museum_main);
	}

	class AnimationZangAsyncTask extends AsyncTask<Animation, Void, Animation> {

		@Override
		protected Animation doInBackground(Animation... params) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		
		@Override
		protected void onPostExecute(Animation result) {
			zang_video_main.setVisibility(View.VISIBLE);
			zang_video_main.startAnimation(result);
		}
	}
	
	class AnimationYiAsyncTask extends AsyncTask<Animation, Void, Animation> {

		@Override
		protected Animation doInBackground(Animation... params) {
			try {
				Thread.sleep(125);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		
		@Override
		protected void onPostExecute(Animation result) {
			yi_video_main.setVisibility(View.VISIBLE);
			 yi_video_main.startAnimation(result);
		}
	}
	class AnimationQiangAsyncTask extends AsyncTask<Animation, Void, Animation> {

		@Override
		protected Animation doInBackground(Animation... params) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		
		@Override
		protected void onPostExecute(Animation result) {
			qiang_video_main.setVisibility(View.VISIBLE);
			qiang_video_main.startAnimation(result);
		}
	}
	class AnimationNaxiAsyncTask extends AsyncTask<Animation, Void, Animation> {

		@Override
		protected Animation doInBackground(Animation... params) {
			try {
				Thread.sleep(375);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}
		
		@Override
		protected void onPostExecute(Animation result) {
			naxi_video_main.setVisibility(View.VISIBLE);
			 naxi_video_main.startAnimation(result);
		}
	}
	class AnimationMuseumAsyncTask extends AsyncTask<Animation, Void, Animation> {

		@Override
		protected Animation doInBackground(Animation... params) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(Animation result) {
			museum_main.setVisibility(View.VISIBLE);
			museum_main.startAnimation(result);
		}
	}

	private void findView() {
		layout_left = (LinearLayout) findViewById(R.id.layout_left);
		layout_right = (LinearLayout) findViewById(R.id.layout_right);
		main_qrcode_banner = (ImageView) findViewById(R.id.main_qrcode_banner);
		i_setting = (ImageView) findViewById(R.id.i_setting);
		main_qrcode = (ImageButton) findViewById(R.id.main_qrcode);
		zang_video_main = (ImageButton) findViewById(R.id.zang_video_main);
		yi_video_main = (ImageButton) findViewById(R.id.yi_video_main);
		qiang_video_main = (ImageButton) findViewById(R.id.qiang_video_main);
		naxi_video_main = (ImageButton) findViewById(R.id.naxi_video_main);
		main_share = (ImageButton) findViewById(R.id.main_share);
		main_suggest = (ImageButton) findViewById(R.id.main_suggest);
		main_about = (ImageButton) findViewById(R.id.main_about);
		main_quit = (ImageButton) findViewById(R.id.main_quit);
		museum_main = (ImageButton) findViewById(R.id.museum_main);
		about_museum = (ScrollView) findViewById(R.id.about_museum);
	}

	private void setListener() {
		main_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,AboutActivity.class);
				startActivity(intent);
			}
		});
		museum_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (about_museum.getVisibility() == View.GONE) {
					about_museum.setVisibility(View.VISIBLE);
				} else {
					about_museum.setVisibility(View.GONE);
				}
			}
		});

		about_museum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (about_museum.getVisibility() == View.GONE) {
					about_museum.setVisibility(View.VISIBLE);
				} else {
					about_museum.setVisibility(View.GONE);
				}
			}
		});
		main_share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.putExtra("sms_body",
						getResources().getString(R.string.sms_share));
				it.setType("vnd.android-dir/mms-sms");
				startActivity(it);
			}
		});
		main_suggest.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SuggestActivity.class);
				startActivity(intent);
			}
		});
		main_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		zang_video_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VideoActivity.class);
				intent.putExtra("path",
						getResources().getString(R.string.zang_path));
				intent.putExtra("title", "藏族馆简介");
				startActivity(intent);
			}
		});
		yi_video_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VideoActivity.class);
				intent.putExtra("path",
						getResources().getString(R.string.yi_path));
				intent.putExtra("title", "彝族馆简介");
				startActivity(intent);
			}
		});
		qiang_video_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VideoActivity.class);
				intent.putExtra("path",
						getResources().getString(R.string.qiang_path));
				intent.putExtra("title", "羌族馆简介");
				startActivity(intent);
			}
		});
		naxi_video_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VideoActivity.class);
				intent.putExtra("path",
						getResources().getString(R.string.naxi_path));
				intent.putExtra("title", "纳西族馆简介");
				startActivity(intent);
			}
		});
		layout_right.setOnTouchListener(this);
		layout_left.setOnTouchListener(this);
		main_qrcode_banner.setOnTouchListener(this);
		main_qrcode.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivity(intent);
			}
		});

		i_setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
						.getLayoutParams();
				if (layoutParams_l.leftMargin >= 0) {
					/** 左移动 */
					new AsynMove().execute(-SPEED);
				} else {
					/** 右移动 */
					new AsynMove().execute(SPEED);
				}
			}
		});
	}

	/** 初始化屏幕宽度，左边视图、右边视图位置及宽度。 */
	private void initViewPosition() {
		ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				/** 当初始化没有被完成时 */
				if (!isFinished) {
					/** 得到屏幕宽度 */
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth();

					FrameLayout.LayoutParams museum_layout_params = (FrameLayout.LayoutParams) museum_main
							.getLayoutParams();
					museum_layout_params.width = window_width / 3;
					museum_layout_params.height = window_width / 3;
					museum_main.setLayoutParams(museum_layout_params);

					RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
							.getLayoutParams();
					/** 设置layout_left的宽度，防止被在移动的时候控件被挤压 */
					layoutParams_l.width = window_width;
					layout_left.setLayoutParams(layoutParams_l);
					RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
							.getLayoutParams();
					MAX_WIDTH = (int) (window_width * 0.6);
					/** 设置layout_right的初始位置 */
					layoutParams_r.width = (int) (window_width * 0.6);
					layoutParams_r.leftMargin = window_width;
					layout_right.setLayoutParams(layoutParams_r);
					isFinished = true;
				}
				return true;
			}
		});

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startPoint.set(event.getX(), event.getX());
			break;
		case MotionEvent.ACTION_MOVE:
			doScrolling(startPoint.x - event.getX());
			startPoint.set(event.getX(), event.getX());
			break;
		case MotionEvent.ACTION_UP:
			RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			/** 左边布局的左边距小于负的右边布局的一半，则全部向左。否则，则全部向右 */
			if (layoutParams_l.leftMargin < -MAX_WIDTH / 2) {
				new AsynMove().execute(-SPEED);
			} else {
				new AsynMove().execute(SPEED);
			}
			break;
		}
		return true;
	}

	/**
	 * 滑动过程基本都是在这个方法里面执行。只有当滑动没有结束就UP，那时在onTouch里面解决
	 */
	void doScrolling(float distanceX) {
		/** distanceX，向左为正，右为负 */
		scrollX += distanceX;
		RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
				.getLayoutParams();
		/** 设置左边布局参数的左边距 */
		layoutParams_l.leftMargin -= scrollX;
		/** 设置右边布局参数的左边距 */
		layoutParams_r.leftMargin = window_width + layoutParams_l.leftMargin;
		/** 当左边布局参数的左边距>=0或者<-200时，表示拖过头了，立即停止 */
		if (layoutParams_l.leftMargin >= 0) {
			layoutParams_l.leftMargin = 0;
			layoutParams_r.leftMargin = window_width;

		} else if (layoutParams_l.leftMargin <= -MAX_WIDTH) {
			layoutParams_l.leftMargin = -MAX_WIDTH;
			layoutParams_r.leftMargin = window_width - MAX_WIDTH;
		}

		layout_left.setLayoutParams(layoutParams_l);
		layout_right.setLayoutParams(layoutParams_r);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (about_museum.getVisibility() == View.VISIBLE) {
			about_museum.setVisibility(View.GONE);
			return false;
		}
		/** 当返回键被按一次时 */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			/** 并且左边布局参数的左边距小于零时移动 */
			if (layoutParams_l.leftMargin < 0) {
				new AsynMove().execute(SPEED);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 第一个泛型类型Integer ，即SPEED，即params[0]为SPEED
	 */
	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0)
				times = MAX_WIDTH / Math.abs(params[0]);
			else
				times = MAX_WIDTH / Math.abs(params[0]) + 1;

			for (int i = 0; i < times; i++) {
				publishProgress(params);
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * 更新UI
		 * 
		 * @param values
		 *            可变数组。实际上就是SPEED
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
					.getLayoutParams();
			/** 右移动 */
			if (values[0] > 0) {
				layoutParams_l.leftMargin = Math.min(layoutParams_l.leftMargin
						+ values[0], 0);
				layoutParams_r.leftMargin = Math.min(layoutParams_r.leftMargin
						+ values[0], window_width);
			} else {
				/** 左移动 */
				layoutParams_l.leftMargin = Math.max(layoutParams_l.leftMargin
						+ values[0], -MAX_WIDTH);
				layoutParams_r.leftMargin = Math.max(layoutParams_r.leftMargin
						+ values[0], window_width - MAX_WIDTH);
			}
			layout_right.setLayoutParams(layoutParams_r);
			layout_left.setLayoutParams(layoutParams_l);
		}
	}

	/**
	 * 菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		if (layoutParams_l.leftMargin >= 0) {
			/** 左移动 */
			new AsynMove().execute(-SPEED);
		} else {
			/** 右移动 */
			new AsynMove().execute(SPEED);
		}
		return false;
	}
}