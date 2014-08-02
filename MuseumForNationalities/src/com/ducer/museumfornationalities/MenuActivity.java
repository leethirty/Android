package com.ducer.museumfornationalities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ducer.service.URLService;
import com.ducer.tools.MyApplication;

public class MenuActivity extends Activity {
	private String id = null;
	private TextView menu_video;
	private TextView menu_image;
	protected String video_path = null;
	private MyApplication myApplication;
	private Handler myHandler;
	private Thread myThread;
	private ImageButton menu_back;
	private TextView menu_title;

	private NotificationManager manager = null;
	private Notification notification = null;
	private String mac = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		findView();
		getMyAppLication();
		setListener();

		// ��ȡϵͳNotificationManager����
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.app_icon, "����Ƽ�",
				System.currentTimeMillis());
		// ��ʾ��ʽ�����𶯣�����,���ص�
		notification.defaults = Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mac = getMacFromWifi(myApplication);

		getId();
		sendId();
	}

	private void setTitle() {
		menu_title.setText(myApplication.getTitle());
	}

	private void setListener() {
		menu_video.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				video_path = myApplication.getVideoUrl();
				if (video_path != null) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(getResources().getString(R.string.serverPath));
					buffer.append("/" + video_path);
					Intent intent = new Intent(MenuActivity.this,
							VideoActivity.class);
					intent.putExtra("path", buffer.toString());
					intent.putExtra("title",
							((MyApplication) getApplication()).getTitle());
					startActivity(intent);
				} else {
					Toast.makeText(MenuActivity.this, "��Ǹ��������δ����",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		menu_image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (myApplication.getImageTable() != null) {
					Intent intent = new Intent(MenuActivity.this,
							ImageActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				} else {
					Toast.makeText(MenuActivity.this, "��Ǹ��������δ����",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		menu_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MenuActivity.this.finish();
			}
		});
	}

	private void getMyAppLication() {
		myApplication = (MyApplication) getApplication();
	}

	private void sendId() {
		myApplication.setId(id);

		myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:


					String audioUrl = msg.getData().getString("audioUrl");
					String imageTable = msg.getData().getString("imageTable");
					String videoUrl = msg.getData().getString("videoUrl");
					String messageTable = msg.getData().getString(
							"messageTable");
					String title = msg.getData().getString("title");

					String status = msg.getData().getString("STATUS");
					
					if (!TextUtils.equals(status, "0")) {

						// ��������֪ͨ��ʱҪչ�ֵ�������Ϣ
						Intent notificationIntent = new Intent();
						notificationIntent.setClass(getApplicationContext(),
								MenuActivity.class);
						PendingIntent contentIntent = PendingIntent
								.getActivity(getApplicationContext(), 0,
										notificationIntent, 0);
						notification
								.setLatestEventInfo(
										getApplicationContext(),
										"����Ƽ�",
										msg.getData().getString("position")
												+ "  "
												+ msg.getData().getString(
														"detail")
												+ "  "
												+ (msg.getData()
														.getString("advice")
														.equals("0") ? " "
														: msg.getData()
																.getString(
																		"advice")),
										contentIntent);
						manager.notify(1, notification);
					}
					MenuActivity.this.myApplication.setAudioUrl(audioUrl);
					MenuActivity.this.myApplication.setImageTable(imageTable);
					MenuActivity.this.myApplication.setVideoUrl(videoUrl);
					MenuActivity.this.myApplication
							.setMessageTable(messageTable);
					MenuActivity.this.myApplication.setTitle(title);
					setTitle();
					break;

				case -1:
					Toast.makeText(MenuActivity.this, "��Ǹ��������δ����",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		myThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String urlStr = getString(R.string.serverPath)
							+ "/HomeServlet";
					Bundle bundle = URLService.getURLInfo(urlStr, id, mac);
					if (bundle != null) {
						Message msg = new Message();
						msg.setData(bundle);
						msg.what = 0;
						myHandler.sendMessage(msg);
					} else {
						myHandler.sendEmptyMessage(-1);
					}
				} catch (Exception e) {
					myHandler.sendEmptyMessage(-1);
				}

			}
		});

		if (id != null) {
			myThread.start();
		}
	}

	private void findView() {
		menu_video = (TextView) findViewById(R.id.menu_video);
		menu_image = (TextView) findViewById(R.id.menu_image);
		menu_title = (TextView) findViewById(R.id.menu_title);
		menu_back = (ImageButton) findViewById(R.id.menu_back);
	}

	private void getId() {
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
	}

	/**
	 * ͨ��wifiManager��ȡmac��ַ
	 * 
	 * @attention Wifi
	 * @return Mac Address
	 */
	public String getMacFromWifi(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String mResult = wifiInfo.getMacAddress();
		return mResult;
	}

	/**
	 * �ж��Ƿ���winfi����
	 * 
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected(Context context) {
		ConnectivityManager cm;
		cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean result = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState() == State.CONNECTED ? true : false;
		return result;
	}
}
