package com.ducer.museumfornationalities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ducer.bean.Message;
import com.ducer.custom.PaginationAdapter;
import com.ducer.service.InsertMessageService;
import com.ducer.service.MessageService;
import com.ducer.tools.MyApplication;
public class TalkActivity extends Activity implements OnScrollListener {

	final int MAX_LENGTH = 140;
	int restLength = MAX_LENGTH;
	private Button btn_send;
	private EditText et_talk;
	private TextView tv_num;
	private ListView listView;
	private View loadMoreView;
	private ProgressBar loading_bar;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int datasize = 0; // 评论总数
	private PaginationAdapter adapter;
	private String messageTableName;
	private ImageButton talk_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talk);
		findView();
		setTableName();
		getTalks();
		setListener();
	}

	private void getTalks() {
		adapter = new PaginationAdapter(this);
		listView.addFooterView(loadMoreView); // 设置列表底部视图
		String urlPath = getResources().getString(R.string.serverPath)
				+ "/RestMessageServlet";
		new RestMessageAsyncTask().execute(urlPath, messageTableName, 0 + "");
		listView.setAdapter(adapter);
	}

	private void setTableName() {
		messageTableName =((MyApplication)getApplication()).getMessageTable();
	}

	private void findView() {
		talk_back = (ImageButton) findViewById(R.id.talk_back);
		tv_num = (TextView) findViewById(R.id.tv_num);
		et_talk = (EditText) findViewById(R.id.et_talk);
		et_talk.clearFocus();
		btn_send = (Button) findViewById(R.id.btn_send);
		listView = (ListView) findViewById(R.id.lvNews);
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		loading_bar = (ProgressBar) loadMoreView.findViewById(R.id.loading_bar);
	}

	private void setListener() {
		listView.setOnScrollListener(TalkActivity.this);
		talk_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		et_talk.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				restLength = MAX_LENGTH - et_talk.getText().length();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tv_num.setText(restLength + "/140");

			}

			@Override
			public void afterTextChanged(Editable s) {
				tv_num.setText(restLength + "/140");
			}

		});
		btn_send.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_talk.getText().toString().length() > 0) {
					String insrtUrlStr = getResources().getString(
							R.string.serverPath)
							+ "/InsertMessageServlet";
					new InsertMessageAsyncTask().execute(insrtUrlStr,
							messageTableName, et_talk.getText().toString());
				}
			}
		});
	}

	class InsertMessageAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			try {
				return InsertMessageService.insertMeassge(params[0], params[1],
						params[2]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				et_talk.setText("");
				restLength = MAX_LENGTH;
				LoadMessages();
				Toast.makeText(TalkActivity.this, "评论成功", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int lastIndex = adapter.getCount();// 数据集最后一条的序号
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex  == lastIndex && lastIndex < datasize) {
			LoadMessages();
		}else if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex + 1>= lastIndex && lastIndex >= datasize){
			// 如果所有的记录选项等于数据集的条数，则移除列表底部视图  
				listView.removeFooterView(loadMoreView);
				Toast.makeText(this, "已经到底啦", Toast.LENGTH_SHORT).show();
		}
	}

	public void LoadMessages() {
		loading_bar.setVisibility(View.VISIBLE);
		String urlPath = getResources().getString(R.string.serverPath)
				+ "/RestMessageServlet";
		new RestMessageAsyncTask().execute(urlPath,
				messageTableName, adapter.getCount() + "");
	}

	public List<Message> parserMessages(String result) {
		if (result != null) {
			try {
				List<Message> lists = new ArrayList<Message>();
				JSONObject jsonObject = new JSONObject(result);
				datasize = jsonObject.getInt("Totalcount");
				int count = jsonObject.getInt("count");
				JSONArray jsonObjectArray = jsonObject.getJSONArray("all");

				for (int i = 0; i < count; i++) {
					JSONObject jsonObjectItem = jsonObjectArray
							.getJSONObject(i);
					String contentStr = jsonObjectItem.getString("content")
							.toString();
					String timeStr = jsonObjectItem.getString("contenttime")
							.toString();
					Message message = new Message();
					message.setContent(contentStr);
					message.setTime(timeStr);
					message.setName("博物馆游客");
					lists.add(message);
				}
				return lists;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		visibleLastIndex =firstVisibleItem + visibleItemCount -1;
	}

	class RestMessageAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			try {
				return MessageService.getRestMessage(params[0], params[1],
						params[2]);
			} catch (ClientProtocolException e) {

				return null;
			} catch (IOException e) {

				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			List<Message> lists = parserMessages(result);
			if (lists != null) {
				adapter.addMessages(lists);
				adapter.notifyDataSetChanged();
			}else{
				Toast.makeText(TalkActivity.this, "获取评论失败", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
