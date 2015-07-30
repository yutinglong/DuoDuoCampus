package com.duoduo.duoduocampus.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.duoduo.duoduocampus.R;

public class CustomPageLoading extends ProgressBar {

	public CustomPageLoading(Context context) {
		super(context);
		init(context);
	}

	public CustomPageLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	

	public CustomPageLoading(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	private void init(Context context) {
		setIndeterminateDrawable(context.getResources().getDrawable(R.anim.diy_progress_bar));
		((AnimationDrawable)getIndeterminateDrawable()).start();
	}
}
