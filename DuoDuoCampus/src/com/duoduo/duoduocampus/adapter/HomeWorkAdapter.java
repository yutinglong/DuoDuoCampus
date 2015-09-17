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
import com.duoduo.duoduocampus.model.HomeWork;

public class HomeWorkAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<HomeWork> data = new ArrayList<HomeWork>();

	public HomeWorkAdapter(Context context, List<HomeWork> dataList) {
		this.mInflater = LayoutInflater.from(context);
		this.data = dataList;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
			convertView = mInflater.inflate(R.layout.home_work_item, null);
			
			holder.title = (TextView) convertView.findViewById(R.id.new_title);
			holder.content = (TextView) convertView.findViewById(R.id.home_work_content);
			holder.datatime = (TextView) convertView.findViewById(R.id.datatime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		HomeWork mHomeWork = data.get(position);
		if (mHomeWork != null) {
			holder.title.setText((String) mHomeWork.course.couName);
			holder.content.setText((String) mHomeWork.workContent);
			holder.datatime.setText("作业日期: " + mHomeWork.createDate.substring(0, 10));
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView datatime;
		public TextView content;
	}
}
