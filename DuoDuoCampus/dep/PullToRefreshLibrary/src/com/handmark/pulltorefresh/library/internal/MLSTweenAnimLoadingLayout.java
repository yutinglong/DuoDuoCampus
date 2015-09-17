package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

/**
 * 
 * @title: MLSTweenAnimLoadingLayout.java
 * @description: 下拉刷新动画封装
 * @company: 美丽说（北京）网络科技有限公司
 * @author tinglongyu
 * @version: 7.1.0
 * @created：2015年8月24日
 */
public class MLSTweenAnimLoadingLayout extends LoadingLayout {

	private AnimationDrawable animationDrawable;

	public MLSTweenAnimLoadingLayout(Context context, Mode mode,
			Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);
		// 初始化
		mHeaderImage.setImageResource(R.anim.diy_downpull);
		animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
	}

	// 默认图片
	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.loading_00016;
	}

	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {
	}

	@Override
	protected void onPullImpl(float scaleOfLayout) {
		Drawable drawable = getCurrentDrawable(scaleOfLayout);
		mHeaderImage.setImageDrawable(drawable);
	}

	// 下拉以刷新
	@Override
	protected void pullToRefreshImpl() {
	}

	// 正在刷新时回调
	@Override
	protected void refreshingImpl() {
		// 开始播放动画
		mHeaderImage.setImageResource(R.anim.diy_downpull);
		animationDrawable.start();
	}

	// 释放以刷新
	@Override
	protected void releaseToRefreshImpl() {
	}

	// 重新设置
	@Override
	protected void resetImpl() {
		mHeaderImage.setVisibility(View.VISIBLE);
		mHeaderImage.clearAnimation();
	}

	private Drawable getCurrentDrawable(float percent) {
		int index = (int) (15 * percent);
		index = Math.min(15, index);
		String name = String.valueOf(index).length() > 1 ? "loading_000"
				+ index : "loading_0000" + index;
		int indentify = getResources().getIdentifier(name, "drawable",
				this.getContext().getPackageName());
		return getResources().getDrawable(indentify);
	}
}