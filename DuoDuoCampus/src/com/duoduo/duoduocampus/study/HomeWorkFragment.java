package com.duoduo.duoduocampus.study;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.activity.HomeWorkDetailActivity;
import com.duoduo.duoduocampus.adapter.HomeWorkAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.HomeWork;
import com.duoduo.duoduocampus.model.net.HomeWorks;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HomeWorkFragment extends BaseFragment implements OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener {
	private PullToRefreshListView mPullRefreshListView;
	private HomeWorkAdapter mAdapter;
	private List<HomeWork> dataList = new ArrayList<HomeWork>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onRefresh(null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.homework_view, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initUI(final View view) {
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.main_homework_list);
		
		mAdapter = new HomeWorkAdapter(getActivity(), dataList); 
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mPullRefreshListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			break;
		}
	}

	private void getHomeWorksData() {
		BaseAPI.getInstance().get(getActivity(), "homeworks", null,
				new BaseAPI.RequestListener<HomeWorks>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(HomeWorks result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						
						mPullRefreshListView.onRefreshComplete();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (HomeWork mHomeWork : result.items) {
									dataList.add(mHomeWork);
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
					public void onException(int status, HomeWorks result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
						
						mPullRefreshListView.onRefreshComplete();
					}
				});
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getHomeWorksData();		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mPullRefreshListView != null && mPullRefreshListView.getRefreshableView() != null) {
    		position = position - mPullRefreshListView.getRefreshableView().getHeaderViewsCount();
    	}
        if (mAdapter.getCount() > position && position > -1) {
        	HomeWork mHomeWork = (HomeWork) mAdapter.getItem(position);
        	
        	// 本地详情页
        	Intent mIntent = new Intent(this.getActivity(), HomeWorkDetailActivity.class);
        	mIntent.putExtra("currentHomeWork", mHomeWork);
        	startActivity(mIntent);
        }
	}
}
