package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.NewsListAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.model.net.NewModel;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.utils.LogUtil;

/**
 * @title: BulletinActivity.java
 * @description: 校园公告
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class BulletinActivity extends BaseActivity implements OnClickListener {
	private ListView mListView;
	private NewsListAdapter mAdapter;
	private List<News> dataList = new ArrayList<News>();
	
	private View mView;
	private View mRefresh;
	private View mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bulletin);
		blockBackKey();

		initView();

		onRefresh();
	}

	private void initView() {
		mLoadingView = findViewById(R.id.loading);
		
		mListView = (ListView) findViewById(R.id.main_new_list);
		mRefresh = findViewById(R.id.tv_refresh);
		mView = findViewById(R.id.iv_slidebar);
		
		
		mRefresh.setOnClickListener(this);
		mView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Messenger.getInstance().sendMessage(Messenger.MSG_DRAWER_CHANGE);
			}
		});
		
		mAdapter = new NewsListAdapter(this, dataList); 
		mListView.setAdapter(mAdapter);
	}


	private void getNewsData() {
		BaseAPI.getInstance().get(this, "news", null,
				new BaseAPI.RequestListener<NewModel>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(NewModel result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						
						hideLoadingView();
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (News mNews : result.items) {
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
									dataList.add(mNews);
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
					public void onException(int status, NewModel result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
						hideLoadingView();
					}
				});
	}

	private void onRefresh() {
		mLoadingView.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.GONE);
		
		getNewsData();
	}

    private void hideLoadingView() {
    	mListView.setVisibility(View.VISIBLE);
    	mLoadingView.setVisibility(View.GONE);
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_refresh:// 刷新
			onRefresh();
			break;
		}
	}
}
