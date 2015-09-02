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
import com.duoduo.duoduocampus.adapter.TeachPlanAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.TeachPlan;
import com.duoduo.duoduocampus.model.net.TeacherPlans;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TeachPlanFragment extends BaseFragment implements OnClickListener, OnRefreshListener {
	private PullToRefreshListView mPullRefreshListView;
	private TeachPlanAdapter mAdapter;
	private List<TeachPlan> dataList = new ArrayList<TeachPlan>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onRefresh(null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.teachplan_view, container, false);
		initUI(view);
		
		return view;
	}
	
	private void getTeachPlansData() {
		BaseAPI.getInstance().get(getActivity(), "teachplans", null,
				new BaseAPI.RequestListener<TeacherPlans>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(TeacherPlans result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						mPullRefreshListView.onRefreshComplete();
						
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (TeachPlan mTeachPlan : result.items) {
									dataList.add(mTeachPlan);
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
					public void onException(int status, TeacherPlans result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
						
						mPullRefreshListView.onRefreshComplete();
					}
				});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initUI(final View view) {
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.main_teachplan_list);
		
		mAdapter = new TeachPlanAdapter(getActivity(), dataList); 
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

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getTeachPlansData();
	}

}
