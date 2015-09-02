package com.duoduo.duoduocampus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.model.Teacher;

public class TeachersAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<Teacher> data = new ArrayList<Teacher>();

	public TeachersAdapter(Context context, List<Teacher> dataList) {
		this.mInflater = LayoutInflater.from(context);
		this.data = dataList;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.teacher_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.new_title);
			holder.datatime = (TextView) convertView
					.findViewById(R.id.datatime);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText((String) data.get(position).username);
//		holder.datatime.setText((String) data.get(position).newDate.substring(0, 10));
//		holder.content.setText((String) data.get(position).newContent);

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView datatime;
		public TextView content;
	}

}
