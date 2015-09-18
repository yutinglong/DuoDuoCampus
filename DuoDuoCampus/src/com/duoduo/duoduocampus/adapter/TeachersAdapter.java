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
			holder.username = (TextView) convertView.findViewById(R.id.username);
			holder.gender = (TextView) convertView
					.findViewById(R.id.gender);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Teacher mTeacher = data.get(position);
		
		if (mTeacher != null) {
			holder.username.setText((String) mTeacher.teaName);
			holder.gender.setText("(" + mTeacher.gender + ")");
			holder.content.setText(mTeacher.title);
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView username;
		public TextView gender;
		public TextView content;
	}

}
