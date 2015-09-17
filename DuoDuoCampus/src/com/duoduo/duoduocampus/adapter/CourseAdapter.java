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
import com.duoduo.duoduocampus.model.Course;
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.model.TeachPlan;

public class CourseAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<Course> data = new ArrayList<Course>();

	public CourseAdapter(Context context, List<Course> dataList) {
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
			convertView = mInflater.inflate(R.layout.course_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.new_title);
			holder.data_content = (TextView) convertView
					.findViewById(R.id.data_content);
			holder.data_classhour = (TextView) convertView.findViewById(R.id.data_classhour);
			holder.datateacher = (TextView) convertView.findViewById(R.id.datateacher);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Course mCourse = data.get(position);
		if (mCourse != null) {
			holder.title.setText((String) mCourse.couName);
			holder.data_content.setText((String) mCourse.couDesc);
			holder.datateacher.setText("教师: " + "王坤");
			holder.data_classhour.setText("星期三" + "-" + "第三节");
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView data_content;
		public TextView data_classhour;
		public TextView datateacher;
	}

}
