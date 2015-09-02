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
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.model.TeachPlan;

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
			convertView = mInflater.inflate(R.layout.teach_plan_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.new_title);
			holder.datatime = (TextView) convertView
					.findViewById(R.id.datatime);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText((String) data.get(position).workComment);
		holder.content.setText((String) data.get(position).workContent);
		holder.datatime.setText((String) data.get(position).createDate.substring(0, 10));

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView datatime;
		public TextView content;
	}

}
