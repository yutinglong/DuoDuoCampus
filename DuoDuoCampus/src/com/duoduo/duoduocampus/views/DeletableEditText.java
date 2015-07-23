package com.duoduo.duoduocampus.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.duoduo.duoduocampus.R;

public class DeletableEditText extends EditText implements
		OnFocusChangeListener {
	private Drawable mDelImg;
	private Context mContext;
	private OnFocusChangeListener mFocusChangeListener;

	public DeletableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public DeletableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public DeletableEditText(Context context) {
		super(context);
		mContext = context;
		init();
		
	}

	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener l) {
		if (l != this) {
			mFocusChangeListener = l;
		}
	}

	private void init() {
		mDelImg = mContext.getResources().getDrawable(R.drawable.edittext_icon);

		this.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				setDrawable();
			}
		});

		super.setOnFocusChangeListener(this);
	}

	private void setDrawable() {
		if (length() >= 1) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, mDelImg, null);
			int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources()
							.getDisplayMetrics());

			this.setPadding(this.getPaddingLeft(), this.getPaddingTop(),
					padding, this.getPaddingBottom());
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mDelImg != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (mFocusChangeListener != null) {
			mFocusChangeListener.onFocusChange(v, hasFocus);
		}
		if (length() > 1 && hasFocus) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, mDelImg, null);
			int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources()
							.getDisplayMetrics());

			this.setPadding(this.getPaddingLeft(), this.getPaddingTop(),
					padding, this.getPaddingBottom());
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}
}
