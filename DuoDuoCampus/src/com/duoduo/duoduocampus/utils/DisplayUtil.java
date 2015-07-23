package com.duoduo.duoduocampus.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class DisplayUtil {

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue, float scale) {
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue, float scale) {
		return (int) (dipValue * scale + 0.5f);
	}

	public static int dipToPixels(Context context, float dip) {
		if (context == null) {
			return 0;
		}
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
				r.getDisplayMetrics());
		return (int) px;
	}

	public static int spToPixels(Context context, int sp) {
		if (context == null) {
			return 0;
		}
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				r.getDisplayMetrics());
		return (int) px;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue, float fontScale) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue, float fontScale) {
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 根据图片宽高获取等比例的宽度
	 * 
	 * @param nowWidth
	 * @param bmpWidth
	 * @param bmpHeight
	 */
	public static int getReSizeHeight(int nowWidth, int bmpWidth, int bmpHeight) {
		return (int) (nowWidth * (bmpHeight * 1.0 / bmpWidth));
	}

	public static int getReSizeWidth(int nowHeight, int bmpWidth, int bmpHeight) {
		return (int) (nowHeight * (bmpWidth * 1.0 / bmpHeight));
	}

	/*
	 * 隐藏键盘
	 */
	public static void hideSolftInput(Context context, View v) {
		if (v == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	// 打开虚拟键盘
	public static void openSoftInput(Context context, View v) {
		if (v == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		imm.showSoftInput(v, 0);
		// imm.showSoftInputFromInputMethod(v.getWindowToken(), 0);
	}

	/**
	 * 解决重新设置背景导致padding失效问题
	 * 
	 * @param view
	 *            需要重新设置背景的view
	 * @param resid
	 *            背景资源id
	 */
	public static void setBackgroundKeepPadding(View view, int resid) {
		int bottom = view.getPaddingBottom();
		int top = view.getPaddingTop();
		int right = view.getPaddingRight();
		int left = view.getPaddingLeft();
		view.setBackgroundResource(resid);
		view.setPadding(left, top, right, bottom);
	}

	public static void setBackgroundColorKeepPadding(View view, int color) {
		int bottom = view.getPaddingBottom();
		int top = view.getPaddingTop();
		int right = view.getPaddingRight();
		int left = view.getPaddingLeft();
		view.setBackgroundColor(color);
		view.setPadding(left, top, right, bottom);
	}

	/**
	 * 解决重新设置背景导致padding失效问题
	 * 
	 * @param view
	 *            需要重新设置背景的view
	 * @param drawable
	 *            背景图片
	 */
	@SuppressLint("NewApi")
	public static void setBackgroundKeepPadding(View view, Drawable drawable) {
		int bottom = view.getPaddingBottom();
		int top = view.getPaddingTop();
		int right = view.getPaddingRight();
		int left = view.getPaddingLeft();
		if (Build.VERSION.SDK_INT >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
		view.setPadding(left, top, right, bottom);
	}

	/**
	 * 获取.9图的padding值
	 * 
	 * @param context
	 * @param drawalbeId
	 * @return
	 */
	public static Rect getNinePatchPading(Context context, int drawalbeId) {
		Rect rect = new Rect();
		Drawable drawable = context.getResources().getDrawable(drawalbeId);
		if (drawable instanceof NinePatchDrawable) {
			NinePatchDrawable ninePatchDrawable = (NinePatchDrawable) drawable;
			ninePatchDrawable.getPadding(rect);
		}
		return rect;

	}

	/**
	 * 文字的高，不包括上下留白
	 * 
	 * @param fontSize
	 * @return
	 */
	public static float getFontHeightOnlyText(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return fm.descent - fm.ascent;
	}

	/**
	 * 对喜欢 回复 删除 分享按钮进行调整
	 * 
	 * @param size
	 * @param top
	 * @param textView
	 */
	public static void adjustLeftDrawable(int size, int top, TextView textView) {
		Drawable drawable = textView.getCompoundDrawables()[0];
		drawable.setBounds(0, top, size, top + size);
		textView.setCompoundDrawables(drawable, null, null, null);
	}

	public static void increaseHitRegion(final View view, final int top,
			final int bottom, final int left, final int right) {
		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						Rect rect = new Rect();
						view.getHitRect(rect);
						rect.top -= top;
						rect.bottom += bottom;
						rect.left -= left;
						rect.right += right;
						View parent = (View) view.getParent();
						parent.setTouchDelegate(new TouchDelegate(rect, view));
					}
				});

	}

	/**
	 * 创建一个图片
	 * 
	 * @param contentColor
	 *            内部填充颜色
	 * @param strokeColor
	 *            描边颜色
	 * @param radius
	 *            圆角
	 */
	public static GradientDrawable createDrawable(Context context,
			int contentColor, int strokeColor, int radius) {
		GradientDrawable drawable = new GradientDrawable(); // 生成Shape
		drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
		if (contentColor != -1) {
			drawable.setColor(contentColor);// 内容区域的颜色
		}
		if (strokeColor != -1) {
			drawable.setStroke(DisplayUtil.dipToPixels(context, 1), strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
		}
		drawable.setCornerRadius(radius); // 设置四角都为圆角
		return drawable;
	}

	/**
	 * 创建一个图片选择器
	 * 
	 * @param normalState
	 *            普通状态的图片
	 * @param pressedState
	 *            按压状态的图片
	 */
	public static StateListDrawable createSelector(Drawable normalState,
			Drawable pressedState) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressedState);
		bg.addState(new int[] { android.R.attr.state_enabled }, normalState);
		bg.addState(new int[] {}, normalState);
		return bg;
	}

	public static void setBackgroundWithColor(View view, String normalColor,
			String normalStroke, String pressColor, String pressStroke,
			int radius) {
		try {
			if (view == null) {
				return;
			}
			setBackgroundWithColor(view, Color.parseColor(normalColor),
					Color.parseColor(normalStroke),
					Color.parseColor(pressColor),
					Color.parseColor(pressStroke), radius);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void setBackgroundWithColor(View view, int normalColor,
			int normalStroke, int pressColor, int pressStroke, int radius) {
		try {
			if (view == null) {
				return;
			}
			Drawable normalDrawable = DisplayUtil.createDrawable(
					view.getContext(), normalColor, normalStroke, radius);
			Drawable pressDrawable = DisplayUtil.createDrawable(
					view.getContext(), pressColor, pressStroke, radius);
			Drawable drawable = createSelector(normalDrawable, pressDrawable);
			setBackgroundKeepPadding(view, drawable);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 获取圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
}