package com.duoduo.duoduocampus.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.duoduo.duoduocampus.R;

public class TileDrawable extends Drawable {
	private Drawable mDrawable;
	private int grey_l16;
	private Paint p;

	public TileDrawable(Context mContext) {
		super();
		mDrawable = mContext.getResources().getDrawable(R.drawable.tile_default_bg);
		grey_l16 = mContext.getResources().getColor(R.color.grey_l16);
		
		p = new Paint();
		p.setColor(grey_l16);
		p.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mContext.getResources().getDisplayMetrics()));
	}

	@Override
	public void draw(Canvas canvas) {
		if (mDrawable == null || getBounds() == null) {
			return;
		}
		
		int l = (getBounds().width() - mDrawable.getIntrinsicWidth()) / 2;
		int t = (getBounds().height() - mDrawable.getIntrinsicHeight()) / 2;
		mDrawable.setBounds(l, t, l + mDrawable.getIntrinsicWidth(), t + mDrawable.getIntrinsicHeight());
		mDrawable.draw(canvas);
		
		// 根据设计要求，此版本开始去掉所有的这种线 7.0.0版
//		if (showLine) {
//			canvas.drawLine(0, getBounds().height(), getBounds().width(), getBounds().height(), p);
//		}
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	@Override
	public int getOpacity() {
		return 0;
	}
}
