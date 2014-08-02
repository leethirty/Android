package com.ducer.custom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ducer.bean.Message;
import com.ducer.museumfornationalities.R;

public class PaginationAdapter extends BaseAdapter {

			List<Message> items;
			Activity context;

			public PaginationAdapter(List<Message> items) {
				this.items = items;
			}

			public PaginationAdapter(Context context) {
				this.context = (Activity) context;
				this.items = new ArrayList<Message>();
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
					view = context.getLayoutInflater().inflate(R.layout.list_item, null);
				}

				// 评论者姓名
				TextView textView_name = (TextView) view.findViewById(R.id.name);
				textView_name.setText(items.get(position).getName());
				// 评论时间
				TextView textView_time = (TextView) view.findViewById(R.id.time);
				textView_time.setText(items.get(position).getTime());
				// 新闻内容
				TextView textView_Content = (TextView) view
						.findViewById(R.id.content);
				textView_Content.setText(items.get(position).getContent());

				return view;
			}

			/**
			 * 添加数据列表项
			 * @param newsitem
			 */
			public void addMessages(List<Message> lists) {
				items.addAll(lists);
			}

}
