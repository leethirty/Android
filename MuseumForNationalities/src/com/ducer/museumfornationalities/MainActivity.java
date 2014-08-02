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
	/** ��߲��� */
	private LinearLayout layout_left;
	/** �ұ߲��� */
	private LinearLayout layout_right;
	/** �ƶ��ķ�Χ */
	private int MAX_WIDTH;
	/** �ƶ����ٶ� */
	private final static int SPEED = 10;
	/** �ӳ�ʱ�� */
	private final static int SLEEP_TIME = 5;
	/** ���黬������ */
	private float scrollX = 0;
	/** ��Ļ��� */
	private int window_width;
	/** ��ʼ����ͼλ�ã��Ƿ���� */
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
				intent.putExtra("title", "����ݼ��");
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
				intent.putExtra("title", "����ݼ��");
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
				intent.putExtra("title", "Ǽ��ݼ��");
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
				intent.putExtra("title", "������ݼ��");
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
					/** ���ƶ� */
					new AsynMove().execute(-SPEED);
				} else {
					/** ���ƶ� */
					new AsynMove().execute(SPEED);
				}
			}
		});
	}

	/** ��ʼ����Ļ��ȣ������ͼ���ұ���ͼλ�ü���ȡ� */
	private void initViewPosition() {
		ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				/** ����ʼ��û�б����ʱ */
				if (!isFinished) {
					/** �õ���Ļ��� */
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth();

					FrameLayout.LayoutParams museum_layout_params = (FrameLayout.LayoutParams) museum_main
							.getLayoutParams();
					museum_layout_params.width = window_width / 3;
					museum_layout_params.height = window_width / 3;
					museum_main.setLayoutParams(museum_layout_params);

					RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
							.getLayoutParams();
					/** ����layout_left�Ŀ�ȣ���ֹ�����ƶ���ʱ��ؼ�����ѹ */
					layoutParams_l.width = window_width;
					layout_left.setLayoutParams(layoutParams_l);
					RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
							.getLayoutParams();
					MAX_WIDTH = (int) (window_width * 0.6);
					/** ����layout_right�ĳ�ʼλ�� */
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
			/** ��߲��ֵ���߾�С�ڸ����ұ߲��ֵ�һ�룬��ȫ�����󡣷�����ȫ������ */
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
	 * �������̻��������������������ִ�С�ֻ�е�����û�н�����UP����ʱ��onTouch������
	 */
	void doScrolling(float distanceX) {
		/** distanceX������Ϊ������Ϊ�� */
		scrollX += distanceX;
		RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
				.getLayoutParams();
		/** ������߲��ֲ�������߾� */
		layoutParams_l.leftMargin -= scrollX;
		/** �����ұ߲��ֲ�������߾� */
		layoutParams_r.leftMargin = window_width + layoutParams_l.leftMargin;
		/** ����߲��ֲ�������߾�>=0����<-200ʱ����ʾ�Ϲ�ͷ�ˣ�����ֹͣ */
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
		/** �����ؼ�����һ��ʱ */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			/** ������߲��ֲ�������߾�С����ʱ�ƶ� */
			if (layoutParams_l.leftMargin < 0) {
				new AsynMove().execute(SPEED);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ��һ����������Integer ����SPEED����params[0]ΪSPEED
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
		 * ����UI
		 * 
		 * @param values
		 *            �ɱ����顣ʵ���Ͼ���SPEED
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			RelativeLayout.LayoutParams layoutParams_r = (RelativeLayout.LayoutParams) layout_right
					.getLayoutParams();
			/** ���ƶ� */
			if (values[0] > 0) {
				layoutParams_l.leftMargin = Math.min(layoutParams_l.leftMargin
						+ values[0], 0);
				layoutParams_r.leftMargin = Math.min(layoutParams_r.leftMargin
						+ values[0], window_width);
			} else {
				/** ���ƶ� */
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
	 * �˵�
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		RelativeLayout.LayoutParams layoutParams_l = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		if (layoutParams_l.leftMargin >= 0) {
			/** ���ƶ� */
			new AsynMove().execute(-SPEED);
		} else {
			/** ���ƶ� */
			new AsynMove().execute(SPEED);
		}
		return false;
	}
}