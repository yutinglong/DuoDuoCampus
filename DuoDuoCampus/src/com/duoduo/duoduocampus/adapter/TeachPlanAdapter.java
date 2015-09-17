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
import com.duoduo.duoduocampus.model.TeachPlan;

public class TeachPlanAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<TeachPlan> data = new ArrayList<TeachPlan>();
	private Context mContext;

	public TeachPlanAdapter(Context context, List<TeachPlan> dataList) {
		this.mInflater = LayoutInflater.from(context);
		this.data = dataList;
		mContext = context;
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
			holder.plan_status = (TextView) convertView
					.findViewById(R.id.plan_status);
			holder.data_content = (TextView) convertView.findViewById(R.id.data_content);
			holder.data_classhour = (TextView) convertView.findViewById(R.id.data_classhour);
			holder.datateacher = (TextView) convertView.findViewById(R.id.datateacher);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		TeachPlan mTeachPlan = data.get(position);
		
		if (mTeachPlan != null) {
			if (mTeachPlan.course != null && mTeachPlan.course.couName != null) {
				holder.title.setText((String) data.get(position).course.couName);
			}
			
			if (mTeachPlan.getStatusStr() != null) {
				holder.plan_status.setText("(" + data.get(position).getStatusStr() + ")");
				if (mTeachPlan.getStatusStr().equals("已停课")) {
					holder.plan_status.setTextColor(mContext.getResources().getColor(R.color.red));
				}
				else {
					holder.plan_status.setTextColor(mContext.getResources().getColor(R.color.grey_d1));
				}
			}
			
			holder.data_content.setText((String) data.get(position).tpContent);
			holder.data_classhour.setText("共 " + (String) data.get(position).classHour + " 课时");
			holder.datateacher.setText("教师：" + data.get(position).teacher.teaName);
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView plan_status;
		public TextView data_content;
		public TextView data_classhour;
		public TextView datateacher;
	}

}
