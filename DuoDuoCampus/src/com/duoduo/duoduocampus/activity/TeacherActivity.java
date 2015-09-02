package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.TeachersAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.Teacher;
import com.duoduo.duoduocampus.model.net.Teachers;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @title: TeacherActivity.java
 * @description: 教师
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class TeacherActivity extends BaseActivity implements OnClickListener, OnRefreshListener {
	private View mMenuView;
	private View mLoadingView;
	
	private PullToRefreshListView mPullRefreshListView;
	private TeachersAdapter mAdapter;
	private List<Teacher> dataList = new ArrayList<Teacher>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_teacher);
		blockBackKey();
		
		initView();
		
		mLoadingView.setVisibility(View.VISIBLE);
		mPullRefreshListView.setVisibility(View.GONE);
		onRefresh(null);
	}
	
	private void initView() {
		mLoadingView = findViewById(R.id.loading);
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.main_teacher_list);
		mMenuView = findViewById(R.id.iv_slidebar);
		
		mMenuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Messenger.getInstance().sendMessage(Messenger.MSG_DRAWER_CHANGE);
			}
		});
		
		mAdapter = new TeachersAdapter(this, dataList); 
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
	}

	private void getTeachersData() {
		BaseAPI.getInstance().get(this, "teachers", null,
				new BaseAPI.RequestListener<Teachers>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(Teachers result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						mPullRefreshListView.onRefreshComplete();
						
						hideLoadingView();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (Teacher mTeacher : result.items) {
									dataList.add(mTeacher);
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
					public void onException(int status, Teachers result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
						hideLoadingView();
						mPullRefreshListView.onRefreshComplete();
					}
				});
	}
	
    private void hideLoadingView() {
    	mPullRefreshListView.setVisibility(View.VISIBLE);
    	mLoadingView.setVisibility(View.GONE);
    }
    
	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getTeachersData();
	}

	@Override
	public void onClick(View v) {
		
	}
}
