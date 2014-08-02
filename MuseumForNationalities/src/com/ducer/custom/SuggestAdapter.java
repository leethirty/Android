package com.ducer.custom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ducer.bean.Suggest;
import com.ducer.museumfornationalities.R;

public class SuggestAdapter extends BaseAdapter {

			List<Suggest> items;
			Activity context;


			public SuggestAdapter(Context context) {
				this.context = (Activity) context;
				this.items = new ArrayList<Suggest>();
			}
			
			public SuggestAdapter(Context context,List<Suggest> lists) {
				this.context = (Activity) context;
				this.items = lists;
			}

			@Override
			public int getCount() {
				return items.size();
			}

			@Override
			public Object getItem(int position) {
				return items.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View view, ViewGroup parent) {
				if (view == null) {
					view = context.getLayoutInflater().inflate(R.layout.suggest_list_item, null);
				}

				TextView textView_time = (TextView) view.findViewById(R.id.suggest_time);
				textView_time.setText(items.get(position).getDatetime());
				TextView textView_Content = (TextView) view
						.findViewById(R.id.suggest_content);
				textView_Content.setText(items.get(position).getContent());

				return view;
			}

			/**
			 *
			 * @param newsitem
			 */
			public void addSuggests(List<Suggest> lists) {
				items.addAll(lists);
			}

}
