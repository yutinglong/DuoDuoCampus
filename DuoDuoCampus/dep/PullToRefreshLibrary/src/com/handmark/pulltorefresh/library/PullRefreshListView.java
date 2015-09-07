package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class PullRefreshListView extends PullToRefreshListView implements OnScrollListener {
	private boolean mCanLoadMore = false;
	private View footerView;
	private View mHeadView;
	private LayoutInflater mInflater;
	
	protected ListView mListView;
	protected OnLoadMoreListener mLoadMoreListener;

	private boolean isAddFooderView = false;
	private Object lock = new Object();
	/**
	 * 加载更多监听接口
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if (pLoadMoreListener != null) {
			mLoadMoreListener = pLoadMoreListener;
		}
	}
	
    public PullRefreshListView(Context context) {
    	super(context);
    	mListView = getRefreshableView();
    	mInflater = LayoutInflater.from(context);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	mListView = getRefreshableView();
    	mInflater = LayoutInflater.from(context);
    }

	public PullRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		mListView = getRefreshableView();
		mInflater = LayoutInflater.from(context);
	}
    
    public void addColorHeadView(Context context) {
    	LayoutInflater mInflater = LayoutInflater.from(context);
		mHeadView = mInflater.inflate(R.layout.color_headview, null);
		mHeadView.findViewById(R.id.bottom_line).setVisibility(View.GONE);
		mHeadView.setBackgroundResource(R.color.grey_f8);
		mHeadView.setVisibility(View.GONE);
		if (mListView != null && mListView instanceof ListView) {
			mListView.addHeaderView(mHeadView);
    	}
    }
    
    public void showColorHeadView() {
    	if (mHeadView != null) {
    		mHeadView.setVisibility(View.VISIBLE);
    	}
    }
    
    public void hideColorHeadView() {
    	if (mHeadView != null) {
    		mHeadView.setVisibility(View.GONE);
    	}
    }
    
    public boolean isCanLoadMore() {
		return mCanLoadMore;
	}

	/**
	 * 设置是否可以底部获取更多 注：暂只支持ListView的底部刷新
	 * @param pCanLoadMore
	 */
	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if (mCanLoadMore && mListView != null && mListView instanceof ListView) {
			addFooterView();
		}
		else if (!mCanLoadMore && footerView != null) {
			removeFooterView();
		}
		
		// Add an end-of-list listener
		if (mCanLoadMore) {
			setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
				@Override
				public void onLastItemVisible() {
					if (mLoadMoreListener != null) {
						mLoadMoreListener.onLoadMore();
					}
				}
			});
		}
		else {
			setOnLastItemVisibleListener(null);
		}
	}

	private void removeFooterView() {
		footerView.setVisibility(View.GONE);
		
		synchronized (lock) {
			if (mListView instanceof ListView
					&& isAddFooderView) {
				((ListView) mListView).removeFooterView(footerView);
				isAddFooderView = false;
			}
		}
	}
	
	/**
	 * 添加加载更多FootView
	 */
	private void addFooterView() {
		if (isAddFooderView) {
			return;
		}
		footerView = mInflater.inflate(R.layout.getmore_footer, null);
		footerView.setVisibility(View.VISIBLE);
		footerView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
		
		synchronized (lock) {
			if (mListView != null && mListView instanceof ListView) {
				((ListView) mListView).addFooterView(footerView);
			}
			isAddFooderView = true;
		}
	}
	
	public View getFooterView() {
		return footerView;
	}
}
