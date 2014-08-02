package com.ducer.museumfornationalities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.ducer.bean.Suggest;
import com.ducer.custom.SuggestAdapter;
import com.ducer.service.SuggestService;
import com.ducer.tools.DatabaseHelper;

public class SuggestActivity extends Activity {
	private Button btn_suggest;
	private EditText et_suggest;
	private EditText qq;
	private ImageButton suggest_home;
	private ListView suggest_list;
	private SuggestAdapter adapter;
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		findView();
		setSuggestList();
		setListener();
	}

	private void setSuggestList() {
		List<Suggest> lists = new ArrayList<Suggest>();
		databaseHelper = new DatabaseHelper(this);
		db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query("SuggestTable", null, null, null, null, null, null);
		int count = cursor.getCount();
		cursor.moveToFirst();
		while(count >0 && cursor != null){
			Suggest suggest = new Suggest();
			suggest.setContent(cursor.getString(cursor.getColumnIndex("content")));
			suggest.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
			lists.add(suggest);
			cursor.moveToNext();
			count--;
		}
		db.close();
		cursor.close();
		adapter = new SuggestAdapter(this,lists);
		suggest_list.setAdapter(adapter);
	}

	private void setListener() {
		suggest_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SuggestActivity.this.finish();

			}
		});

		btn_suggest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_suggest.getText().toString().length() != 0) {
					try {
						String insrtUrlStr = getResources().getString(
								R.string.serverPath)
								+ "/SuggestServlet";
						new SuggestAsyncTask().execute(insrtUrlStr, et_suggest
								.getText().toString(), qq.getText().toString());

					} catch (Exception e) {
						Toast.makeText(SuggestActivity.this, "ÆÀÂÛ·¢ËÍÊ§°Ü£¬Çë¼ì²éÍøÂç¡£",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(SuggestActivity.this, "·´À¡ÄÚÈÝ²»ÄÜÎª¿Õ¡£",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

	}

	class SuggestAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			try {
				return SuggestService.insertSuggest(params[0], params[1],
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
			if (result) {
				List<Suggest> lists = new ArrayList<Suggest>();
				Suggest item = new Suggest();
				String content = et_suggest.getText().toString();
				item.setContent(content);
				java.text.DateFormat dataFormat = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String s = dataFormat.format(new Date());
				item.setDatetime(s);
				lists.add(item);
				
				qq.setText("");
				et_suggest.setText("");
				adapter.addSuggests(lists);
				adapter.notifyDataSetChanged();
				saveSuggest(content, s);
			} else {
				Toast.makeText(SuggestActivity.this, "ÆÀÂÛ·¢ËÍÊ§°Ü£¬Çë¼ì²éÍøÂç¡£",
						Toast.LENGTH_SHORT).show();
			}
		}

		private void saveSuggest(String content, String s) {
			db = databaseHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put("content", content);
			cv.put("datetime", s);
			db.insert("SuggestTable", null, cv);
			db.close();
		}

	}

	private void findView() {
		suggest_home = (ImageButton) findViewById(R.id.suggest_home);
		btn_suggest = (Button) findViewById(R.id.btn_suggest);
		suggest_list = (ListView)findViewById(R.id.suggest_list);
		et_suggest = (EditText) findViewById(R.id.et_suggest);
		et_suggest.clearFocus();
		qq = (EditText) findViewById(R.id.qq);
		qq.clearFocus();
	}
}
