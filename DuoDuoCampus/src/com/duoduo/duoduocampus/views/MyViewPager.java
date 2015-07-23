package com.duoduo.duoduocampus.views;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MyViewPager extends ViewPager {

	private float lastX, lastY,beginX;
	private float mTouchSlop ;
 
	public static interface OnSizeChange {
		void onSizeChanged(int w, int h, int oldw, int oldh);
	}

	public static interface OnScrollChanged {
		void onScrollChanged(int l, int t, int oldl, int oldt);
	}
 

	private OnSizeChange mOnSizeChange;
	private OnScrollChanged mOnScrollChanged;

	public OnSizeChange getOnSizeChange() {
		return mOnSizeChange;
	}

	public void setOnScrollChanged(OnScrollChanged mOnScrollChanged) {
		this.mOnScrollChanged = mOnScrollChanged;
	}

	public void setOnSizeChange(OnSizeChange mOnSizeChange) {
		this.mOnSizeChange = mOnSizeChange;
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
	}

	public MyViewPager(Context context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager#onAttachedToWindow()
	 */
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mOnSizeChange != null) {
			mOnSizeChange.onSizeChanged(w, h, oldw, oldh);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (mOnScrollChanged != null)
			mOnScrollChanged.onScrollChanged(l, t, oldl, oldt);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float y = ev.getY();
		float x = ev.getX();	
 		if(ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			beginX = x;
			lastY = y;
			lastX = x;
 		}
		else if(ev.getAction() == MotionEvent.ACTION_MOVE)
		{
			boolean isHorizontalFlip = Math.abs(x - lastX) * 0.5f > Math.abs(y - lastY);
			lastY = y;
			lastX = x;

			if(isHorizontalFlip && Math.abs(x-beginX)>mTouchSlop)
				return true;
		}
		return super.onInterceptTouchEvent(ev);
 
	}

  
}
