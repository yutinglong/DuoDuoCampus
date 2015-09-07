package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.NewsListAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.api.BaseAPI.RequestState;
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.model.net.NewModel;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.system.status.NetStatusReceiver;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullRefreshListView;
import com.handmark.pulltorefresh.library.PullRefreshListView.OnLoadMoreListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * @title: BulletinActivity.java
 * @description: 校园公告
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class BulletinActivity extends BaseActivity implements OnClickListener, OnRefreshListener , OnLoadMoreListener{
	private static final int DEFAULT_PAGE_SIZE = 30;
	
	private PullRefreshListView mPullRefreshListView;
	private NewsListAdapter mAdapter;
	
	private RequestState mNewsState = new RequestState("news");
	
	private List<News> dataList = new ArrayList<News>();
	
	private View mView;
	private View mRefresh;
	private View mLoadingView;

	private View netErrorView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bulletin);
		blockBackKey();

		initView();

		mLoadingView.setVisibility(View.VISIBLE);
		mPullRefreshListView.setVisibility(View.GONE);
		
    	if (!NetStatusReceiver.isNetOk()) {
    		netErrorView.setVisibility(View.VISIBLE);
    	}
    	
		if (netErrorView.getVisibility() != View.VISIBLE) {
			onRefresh(null);
		}
	}

	private void initView() {
		netErrorView = findViewById(R.id.neterror);
		netErrorView.setOnClickListener(this);
		
		mLoadingView = findViewById(R.id.loading);
		
		mPullRefreshListView = (PullRefreshListView) findViewById(R.id.main_new_list);
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
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnLoadListener(this);
		
		LayoutInflater inflater = LayoutInflater.from(this); 
        View tempView = inflater.inflate(R.layout.empty_textview, null);
        mPullRefreshListView.setEmptyView(tempView);
	}

	private int count = 0;
	private void getNewsData(int offset, int limit) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("offset", String.valueOf(offset)));
//		params.add(new BasicNameValuePair("limit", String.valueOf(limit)));
		
		BaseAPI.getInstance().get(this, "news", null,
				new BaseAPI.RequestListener<NewModel>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(NewModel result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						if (count >= 2) {
							result.hasMore = false;
						}
						else {
							result.hasMore = true;
						}
						count++;
						
						
						
						
						mPullRefreshListView.onRefreshComplete();
						
						hideLoadingView();
						if (result != null) {
							if (result.items.size() > 0) {
								for (News mNews : result.items) {
									dataList.add(mNews);
								}
							} else {

							}
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
						} else {
						}
						
						mNewsState.offset = result.offset;
						mNewsState.isBottom = !result.hasMore;
						netErrorView.setVisibility(View.GONE);
						
						if (!mNewsState.isBottom) {
							mPullRefreshListView.setCanLoadMore(true);
						}
						else {
							mPullRefreshListView.setCanLoadMore(false);
						}
					}

					@Override
					public void onException(int status, NewModel result,
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
		case R.id.tv_refresh:// 刷新
			toTop();
			mPullRefreshListView.setRefreshing(false);
			break;
		case R.id.neterror:
			onRefresh(null);
			break;
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
		mNewsState.isBottom = false;
		mNewsState.isRefresh = true;
		mNewsState.offset = 0;
		
		count = 0;
		dataList.clear();
		getNewsData(0, DEFAULT_PAGE_SIZE);		
	}

	@Override
	public void onLoadMore() {
		if (mNewsState.isBottom) {
			return;
		}
		
		getNewsData(mNewsState.offset, DEFAULT_PAGE_SIZE);
	}
}
