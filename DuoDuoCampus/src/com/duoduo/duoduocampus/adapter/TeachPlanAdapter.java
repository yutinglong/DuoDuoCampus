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
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.model.TeachPlan;

public class TeachPlanAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<TeachPlan> data = new ArrayList<TeachPlan>();

	public TeachPlanAdapter(Context context, List<TeachPlan> dataList) {
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
		holder.title.setText((String) data.get(position).tpContent);
		holder.content.setText("课时: " + (String) data.get(position).classHour);
		holder.datatime.setText("状态: " + (String) data.get(position).tpStatus);

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView datatime;
		public TextView content;
	}

}
