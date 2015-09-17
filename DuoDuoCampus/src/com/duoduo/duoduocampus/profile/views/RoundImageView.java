package com.duoduo.duoduocampus.profile.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DisplayUtil;
import com.duoduo.duoduocampus.utils.NetImageUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Transformation;

/**
 * 圆角ImageView
 * 
 * @title: RoundImageView.java
 * @description:
 * @company: 多多校园
 * @author tinglongyu
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class RoundImageView extends ImageView {

	private Paint borderPaint;
	private float borderW = 1;
	private boolean hasBorder;
	private boolean clickDark = false;
	private int oneDip = 0;
	private int roundR = 5;
	private Paint mPaint;

	private int bolderColor;

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);
		hasBorder = styledAttrs.getBoolean(
				R.styleable.RoundImageView_hasBorder, true);

		bolderColor = styledAttrs.getColor(
				R.styleable.RoundImageView_borderColor, 0xFFFFFF);
		float defaultBorderW = 2;
		borderW = styledAttrs.getDimension(
				R.styleable.RoundImageView_borderSize, defaultBorderW);
		styledAttrs.recycle();
		init();
	}

	public RoundImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		borderPaint = new Paint();
		borderPaint.setColor(bolderColor);
		borderPaint.setAntiAlias(true);
		borderPaint.setStrokeWidth(borderW);
		borderPaint.setStyle(Style.STROKE);
		oneDip = DisplayUtil.dip2px(1,
				getResources().getDisplayMetrics().density);
		roundR = 2 * oneDip;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
	}

	private Transformation transformation = new Transformation() {

		@Override
		public Bitmap transform(Bitmap source) {
			Bitmap bitmap = prepareBitmap(source);
			if (bitmap != null) {
				if (!source.isRecycled()) {
					source.recycle();
				}
				return bitmap;
			}
			return source;
		}

		@Override
		public String key() {
			return "RoundImageView";
		}
	};

	public void setImageViewUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		NetImageUtil.with_load(getContext(), url).transform(transformation).into(this);
	}

	public void setImageViewUrl(String url, Transformation aTransformation) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		NetImageUtil.with_load(getContext(), url).transform(aTransformation).into(this);
	}

	public void setImageViewUrl(String url, int defaultResId) {
		setImageViewUrl(url, defaultResId, null);
	}

	public void setImageViewUrl(String url, int defaultResId,
			final Animation anim) {
		this.setBackgroundResource(defaultResId);
		if (TextUtils.isEmpty(url)) {
			return;
		}
		final ImageView img = this;
		NetImageUtil.with_load(getContext(), url).transform(transformation)
				.into(this, new Callback() {

					@Override
					public void onSuccess() {
						img.setBackgroundColor(Color.TRANSPARENT);
						if (anim != null) {
							startAnimation(anim);
						}
					}

					@Override
					public void onError() {
					}
				});
	}

	public void setRoundImage(Bitmap bmp) {
		if (bmp == null)
			return;
		Bitmap bitmap = prepareBitmap(bmp);
		this.setImageBitmap(bitmap);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(prepareBitmap(bm));
	}

	private Bitmap prepareBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			int w = Math.min(bitmap.getWidth(), bitmap.getHeight());
			Bitmap b = Bitmap.createBitmap(w, w, Config.ARGB_8888);
			Canvas canvas = new Canvas(b);
			Paint paint = new Paint();
			paint.setColor(Color.GRAY);
			Rect rect = new Rect(0, 0, w, w);
			RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			canvas.drawCircle(w / 2.f, w / 2.f, w / 2.f, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rectF, paint);

			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			return b;
		} else {
			return null;
		}
	}

	/**
	 * 点击变暗效果的圆角半径 单位 dip
	 * 
	 * @param r
	 */
	public void setRoundR(int r) {
		this.roundR = r * oneDip;
	}

	public void setClickDark(boolean isDark) {
		this.clickDark = isDark;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		if (getBackground() != null) {
			invalidate();
		}
	}

	private RectF rectF;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (clickDark) {
			if (this.isPressed()) {
				int w = this.getWidth();
				int h = this.getHeight();
				if (rectF == null) {
					rectF = new RectF();
				}
				rectF.left = 0;
				rectF.top = 0;
				rectF.right = w;
				rectF.bottom = h;

				mPaint.setAlpha(60);
				canvas.drawRoundRect(rectF, roundR, roundR, mPaint);
				mPaint.setAlpha(255);
			}
		}

		int w = getWidth();
		if (hasBorder) {
			canvas.drawCircle(w / 2.f, w / 2.f, (w - borderW) / 2.f,
					borderPaint);
		}
	}

}
