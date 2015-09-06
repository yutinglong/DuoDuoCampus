package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.ParentSentrustsAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.ParentSentrust;
import com.duoduo.duoduocampus.model.net.ParentSentrusts;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @title: ParentSentrustsActivity.java
 * @description: 家长嘱托
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class ParentSentrustsActivity extends BaseActivity implements OnClickListener, OnRefreshListener {
	private PullToRefreshListView mPullRefreshListView;
	private ParentSentrustsAdapter mAdapter;
	private List<ParentSentrust> dataList = new ArrayList<ParentSentrust>();
	
	private View mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parent_sentrusts);

		initView();

		mLoadingView.setVisibility(View.VISIBLE);
		mPullRefreshListView.setVisibility(View.GONE);
		onRefresh(null);
	}

	private void initView() {
		mLoadingView = findViewById(R.id.loading);
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.main_new_list);
		
		mAdapter = new ParentSentrustsAdapter(this, dataList); 
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
		
		findViewById(R.id.back).setOnClickListener(this);
	}


	private void getParentSentrustData() {
		BaseAPI.getInstance().get(this, "parentsentrusts", null,
				new BaseAPI.RequestListener<ParentSentrusts>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(ParentSentrusts result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						mPullRefreshListView.onRefreshComplete();
						
						hideLoadingView();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (ParentSentrust mParentSentrust : result.items) {
									dataList.add(mParentSentrust);
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
					public void onException(int status, ParentSentrusts result,
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
		}
	}
	
    public void toTop() {
        if (mPullRefreshListView != null
        		&& mPullRefreshListView.getRefreshableView() != null 
        		&& mPullRefreshListView.getRefreshableView().getChildCount() > 0) {
        	mPullRefreshListView.getRefreshableView().setSelection(0);
        }
    }
    
	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getParentSentrustData();		
	}
}
