package com.duoduo.duoduocampus.study;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.CourseAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.Course;
import com.duoduo.duoduocampus.model.net.Courses;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @title: CoursesFragment.java
 * @description: 课程信息
 * @company: 多多校园
 * @author tinglongyu
 * @version: 1.0.0
 * @created：2015年9月2日
 */
public class CoursesFragment extends BaseFragment implements OnClickListener, OnRefreshListener {
	private PullToRefreshListView mPullRefreshListView;
	private CourseAdapter mAdapter;
	private List<Course> dataList = new ArrayList<Course>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onRefresh(null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.courses_view, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initUI(final View view) {
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.main_course_list);

		mAdapter = new CourseAdapter(getActivity(), dataList); 
		mPullRefreshListView.setAdapter(mAdapter);
		
		mPullRefreshListView.setOnRefreshListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			break;
		}
	}

	private void getTeachPlansData() {
		BaseAPI.getInstance().get(getActivity(), "courses", null,
				new BaseAPI.RequestListener<Courses>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(Courses result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						
						mPullRefreshListView.onRefreshComplete();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (Course mCourse : result.items) {
									dataList.add(mCourse);
								}
							} else {

							}
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
						} else {
						}
					}

					@Override
					public void onException(int status, Courses result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
						
						mPullRefreshListView.onRefreshComplete();
					}
				});
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getTeachPlansData();
	}
}
