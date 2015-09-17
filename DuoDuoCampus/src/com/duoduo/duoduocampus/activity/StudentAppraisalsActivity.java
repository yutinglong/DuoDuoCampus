package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.StudentAppraisalsAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.StudentAppraisal;
import com.duoduo.duoduocampus.model.net.StudentAppraisals;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @title: ParentSentrustsActivity.java
 * @description: 学生评价信息
 * @company: 多多校园
 * @author: tinglongyu
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class StudentAppraisalsActivity extends BaseActivity implements OnClickListener, OnRefreshListener {
	private PullToRefreshListView mPullRefreshListView;
	private StudentAppraisalsAdapter mAdapter;
	private List<StudentAppraisal> dataList = new ArrayList<StudentAppraisal>();
	
	private View mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_student_appraisal);

		initView();

		mLoadingView.setVisibility(View.VISIBLE);
		mPullRefreshListView.setVisibility(View.GONE);
		onRefresh(null);
	}

	private void initView() {
		mLoadingView = findViewById(R.id.loading);
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.main_new_list);
		
		mAdapter = new StudentAppraisalsAdapter(this, dataList); 
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
		
		findViewById(R.id.back).setOnClickListener(this);
	}


	private void getStudentAppraisalsData() {
		BaseAPI.getInstance().get(this, "studentappraisals", null,
				new BaseAPI.RequestListener<StudentAppraisals>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(StudentAppraisals result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						mPullRefreshListView.onRefreshComplete();
						
						hideLoadingView();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (StudentAppraisal mStudentAppraisal : result.items) {
									dataList.add(mStudentAppraisal);
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
					public void onException(int status, StudentAppraisals result,
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
		getStudentAppraisalsData();		
	}
}
