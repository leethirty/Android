package com.ducer.museumfornationalities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ducer.custom.ScaleAnimations;
import com.ducer.service.ImageService;
import com.ducer.tools.ImageLoader;
import com.ducer.tools.MyApplication;

@SuppressLint("HandlerLeak")
public class ImageActivity extends Activity implements OnTouchListener {

	private Gallery gallery;

	private MyApplication myApplication;
	/** 图片地址 */
	public String[] urls;
	/** 图片描述 */
	public String[] descriptions;

	private String imageTable;

	public Handler myHandler;

	private RelativeLayout media_controller_button_wrapper;

	private RelativeLayout add_bg;

	private ImageView add_icon;

	protected boolean areButtonsShowing = true;

	private TextView tv_content;

	private String audio_urlStr;

	private String image_urlStr;

	private MediaPlayer mediaPlayer;

	protected boolean played = false;

	protected int playPosition;

	private ImageButton image_talk;

	private ImageButton image_home;

	private RelativeLayout media_controller;

	private TextView image_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		/** 初始化播放器 */
		setMediaPlayer();
		findView();
		setListener();
		/** 初始化缩放按钮动画 */
		setScaleAndListener();
		/** 获取MyApplicaton */
		getMyApplication();
		/** 获取二维码Id */
		setTitle();
		getImage();
		getAudio();
	}

	private void setTitle() {
		image_title.setText(myApplication.getTitle());
	}

	@Override
	public void onBackPressed() {
		finish();
		mediaPlayer.reset();
		super.onBackPressed();
	}

	private void setListener() {
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				tv_content.setText(descriptions[arg2]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (media_controller.getVisibility() == View.VISIBLE) {
					media_controller.setVisibility(View.GONE);
				} else if (media_controller.getVisibility() == View.GONE) {
					media_controller.setVisibility(View.VISIBLE);
				}
			}
		});

		image_talk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ImageActivity.this,
						TalkActivity.class);
				startActivity(intent);
				mediaPlayer.pause();
			}
		});

		image_home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ImageActivity.this.finish();
				mediaPlayer.reset(); 
			}
		}); 

	}

	private void getImage() {
		synchronized (this) {
			if (myApplication.getImageTable() != null) {
				imageTable = myApplication.getImageTable();
				image_urlStr = getString(R.string.serverPath) + "/ImageServlet";
				try {
					new ImageURLAsyncTask().execute(image_urlStr, imageTable);
				} catch (Exception e) { 

				}
			}
		}
	}

	@Override
	protected void onPause() {
		playPosition = mediaPlayer.getCurrentPosition();
		super.onPause();
	}

	private void setMediaPlayer() {
		mediaPlayer = new MediaPlayer();
	}

	private void setScaleAndListener() {
		ScaleAnimations.initOffset(this);
		/** 加号的动画 */
		add_icon.startAnimation(ScaleAnimations
				.getRotateAnimation(0, -225, 300));
		/** 给大按钮设置点击事件 */
		add_bg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!areButtonsShowing) {
					/** 图标的动画 */
					ScaleAnimations.startAnimationsIn(
							media_controller_button_wrapper, 300);
					/** 加号的动画 */
					add_icon.startAnimation(ScaleAnimations.getRotateAnimation(
							0, -225, 300));
				} else {
					/** 图标的动画 */
					ScaleAnimations.startAnimationsOut(
							media_controller_button_wrapper, 300);
					/** 加号的动画 */
					add_icon.startAnimation(ScaleAnimations.getRotateAnimation(
							-225, 0, 300));
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});

		/** 给小图标设置点击事件 */
		for (int i = 0; i < media_controller_button_wrapper.getChildCount(); i++) {
			final ImageView smallIcon = (ImageView) media_controller_button_wrapper
					.getChildAt(i);
			final int position = i;
			smallIcon.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					/**
					 * 这里写各个item的点击事件 1.加号按钮缩小后消失 缩小的animation 2.其他按钮缩小后消失
					 * 缩小的animation 3.被点击按钮放大后消失 透明度渐变 放大渐变的animation
					 */
					if (areButtonsShowing) {
						add_icon.startAnimation(ScaleAnimations
								.getRotateAnimation(-225, 0, 300));
						smallIcon.startAnimation(ScaleAnimations
								.getMaxAnimation(400));
						for (int j = 0; j < media_controller_button_wrapper
								.getChildCount(); j++) {
							if (j != position) {
								final ImageView smallIcon = (ImageView) media_controller_button_wrapper
										.getChildAt(j);
								smallIcon.startAnimation(ScaleAnimations
										.getMiniAnimation(300));
							}
						}
						areButtonsShowing = !areButtonsShowing;
					}

					switch (position) {
					case 1:
						if (audio_urlStr != null) {
							if (mediaPlayer.isPlaying()) {
								mediaPlayer.pause();
							} else {
								if (played) {
									mediaPlayer.start();
								} else {
									play(0);
									played = true;
								}
							}
						} else {
							Toast.makeText(ImageActivity.this, "解说语音不存在",
									Toast.LENGTH_SHORT).show();
						}
						break;

					case 0:
						if (audio_urlStr != null) {
							if (played) {
								if (mediaPlayer.isPlaying()) {
									mediaPlayer.seekTo(0);
								} else {
									mediaPlayer.start();
									mediaPlayer.seekTo(0);
								}
							} else {
								play(0);
								played = true;
							}
						} else {
							Toast.makeText(ImageActivity.this, "解说语音不存在",
									Toast.LENGTH_SHORT).show();
						}
						break;

					}
				}
			});
		}

	}

	protected void play(int i) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(audio_urlStr);
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.start();
					media_controller.setVisibility(View.VISIBLE);
					if (playPosition > 0) {
						mediaPlayer.seekTo(playPosition);
					}
				}
			});
		} catch (Exception e) {
			Toast.makeText(this, "网络错误或者服务器未开启", Toast.LENGTH_SHORT).show();
		}
	}

	private void getMyApplication() {
		myApplication = (MyApplication) getApplication();
	}

	private void findView() {
		media_controller = (RelativeLayout) findViewById(R.id.media_controller);
		media_controller.setVisibility(View.INVISIBLE);
		image_home = (ImageButton) findViewById(R.id.image_home);
		gallery = (Gallery) findViewById(R.id.gallery_image);
		media_controller_button_wrapper = (RelativeLayout) findViewById(R.id.media_controller_button_wrapper);
		add_bg = (RelativeLayout) findViewById(R.id.add_bg);
		add_icon = (ImageView) findViewById(R.id.add_icon);
		tv_content = (TextView) findViewById(R.id.tv_content);
		image_title = (TextView) findViewById(R.id.image_title);
		image_talk = (ImageButton) findViewById(R.id.image_talk);
	}

	private void getAudio() {
		if (myApplication.getAudioUrl() != null) {
			audio_urlStr = getString(R.string.serverPath) + "/"
					+ myApplication.getAudioUrl();
			play(0);
		}
	}

	class ImageURLAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				return ImageService.getImageURL(image_urlStr, imageTable);
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					int count = jsonObject.getInt("number");

					urls = new String[count];
					descriptions = new String[count];

					JSONArray jsonObjectImage = jsonObject
							.getJSONArray("image");

					for (int i = 0; i < count; i++) {
						JSONObject jsonObjectImageItem = jsonObjectImage
								.getJSONObject(i);

						String imageUrl = jsonObjectImageItem.getString(
								"imageUrl").toString();

						urls[i] = getString(R.string.serverPath) + "/"
								+ imageUrl;
						
						descriptions[i] = jsonObjectImageItem.getString(
								"description").toString();

					}

					gallery.setAdapter(new ImageAdapter(ImageActivity.this,
							urls));
				} catch (JSONException e) {
				}
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (media_controller.getVisibility() == View.VISIBLE) {
			media_controller.setVisibility(View.GONE);
		} else if (media_controller.getVisibility() == View.GONE) {
			media_controller.setVisibility(View.VISIBLE);
		}
		return true;
	}

	public class ImageAdapter extends BaseAdapter {

		private Context mContext;

		private String[] URLS;

		private final ImageLoader mImageLoader = new ImageLoader(mContext);

		public ImageAdapter(Context context, String[] urls) {
			mContext = context;
			URLS = urls;
		}

		@Override
		public int getCount() {
			return URLS.length;
		}

		@Override
		public Object getItem(int position) {
			return URLS[position];
		}

		@Override
		public long getItemId(int position) {
			return URLS[position].hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				ImageView i = new ImageView(mContext);
				i.setScaleType(ImageView.ScaleType.FIT_XY);
				i.setLayoutParams(new Gallery.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				i.setBackgroundResource(R.drawable.gallery_bg);

				viewHolder = new ViewHolder();

				viewHolder.mImageView = i;
				convertView = i;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String url = URLS[position];

			mImageLoader.DisplayImage(url, viewHolder.mImageView, false);

			return convertView;
		}
	}

	static class ViewHolder {
		ImageView mImageView;
	}
}
