package com.duoduo.duoduocampus.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 
 * @title:RefreshView.java
 * @description:
 * @company: 美丽说（北京）网络科技有限公司
 * @author wanyuyong
 * @version 4.0
 * @created Aug 30, 2013 重构于2015-5-20 by tinglongyu
 */
public class RefreshView extends PullToRefreshBase<LinearLayout> {
	private View slidablyView = null;

	public RefreshView(Context context) {
		super(context);
	}

	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RefreshView(Context context, Mode mode) {
		super(context, mode);
	}

	public RefreshView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	public void refresh() {
		setRefreshing(false);
	}
	
	/**
	 * 一定要调用此方法, 会通过该方法计算屏幕是否到最顶上
	 * @param slidablyView
	 */
	public void setSlidablyView(View slidablyView) {
		this.slidablyView = slidablyView;
	}
	
	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected LinearLayout createRefreshableView(Context context, AttributeSet attrs) {
		return new LinearLayout(context);
	}

	@Override
	protected boolean isReadyForPullStart() {
		if(slidablyView == null) {
			return true;
		}
		if (slidablyView instanceof ListView) {
			ListView view = (ListView) slidablyView;
			int position = view.getFirstVisiblePosition();
			if (position == 0 && view.getChildCount() != 0) {
				View child = (View) view.getChildAt(0);
				Rect outRect = new Rect();
				child.getHitRect(outRect);
				if (outRect.top == view.getPaddingTop()) {
					return true;
				}
			}
			
			if (view.getChildCount() == 0) {
				return true;
			}

			return false;
		}
		
//		else if(slidablyView instanceof RecyclerView) {
//			RecyclerView view = (RecyclerView) slidablyView;
//			if (view.getChildCount() > 0) {
//				View firstVisibleView = view.getChildAt(0);
//				if (view.getChildPosition(firstVisibleView) == 0) {
//					Rect outRect = new Rect();
//					firstVisibleView.getHitRect(outRect);
//					if (outRect.top == view.getPaddingTop()) {
//						return true;
//					}
//				}
//			}
//			return false;
//		}
		
		return slidablyView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		return true;
	}

}
