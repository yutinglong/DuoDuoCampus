package com.duoduo.duoduocampus.profile.views;

import java.lang.reflect.Method;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.duoduo.duoduocampus.msg.DataSet;
import com.duoduo.duoduocampus.msg.MessageReciever;
import com.duoduo.duoduocampus.msg.Messenger;
import com.nineoldandroids.view.ViewHelper;

public class CustomDrawerLayout extends DrawerLayout implements MessageReciever {

	private Method moveDrawerToOffsetMethed;
	private Method findDrawerWithGravityMethed;
	private Method dispatchOnDrawerClosedMethed;

	public static boolean isOpenDrawer, isClickDrawer;
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		try {
			setDrawerListener(new DrawerListener() {

				@Override
				public void onDrawerStateChanged(int arg0) {
				}

				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					View mContent = getChildAt(0);
					View mMenu = drawerView;
					float scale = 1 - slideOffset;
					float rightScale = 0.9f + scale * 0.1f;
					float leftScale = 1 - 0.4f * scale;
					ViewHelper.setScaleX(mMenu, leftScale);
					ViewHelper.setScaleY(mMenu, leftScale);
					ViewHelper.setAlpha(mMenu, 0.3f + 0.7f * (1 - scale));
					ViewHelper.setTranslationX(mMenu, mMenu.getMeasuredWidth() * (1 - leftScale) / 2);
					ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
					ViewHelper.setPivotX(mContent, 0);
					ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				}

				@Override
				public void onDrawerOpened(View arg0) {
					isOpenDrawer = true;
					isClickDrawer = false;
				}

				@Override
				public void onDrawerClosed(View arg0) {
					isOpenDrawer = false;
				}
			});
			
			moveDrawerToOffsetMethed = DrawerLayout.class.getDeclaredMethod(
					"moveDrawerToOffset", View.class, float.class);
			findDrawerWithGravityMethed = DrawerLayout.class.getDeclaredMethod(
					"findDrawerWithGravity", int.class);
			dispatchOnDrawerClosedMethed = DrawerLayout.class
					.getDeclaredMethod("dispatchOnDrawerClosed", View.class);

			moveDrawerToOffsetMethed.setAccessible(true);
			findDrawerWithGravityMethed.setAccessible(true);
			dispatchOnDrawerClosedMethed.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomDrawerLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 */
	public CustomDrawerLayout(Context context) {
		this(context, null);
	}

	/**
	 * 添加的无动画关闭边栏
	 * 
	 * @param gravity
	 */
	public void closeDrawerWithoutAnimation(int gravity) {
		try {
			if (findDrawerWithGravityMethed == null || moveDrawerToOffsetMethed == null
					|| dispatchOnDrawerClosedMethed == null) {
				// 正常关闭边栏
				closeDrawer(gravity);
				return;
			}
			
			View drawerView = (View) findDrawerWithGravityMethed.invoke(this,
					gravity);
			moveDrawerToOffsetMethed.invoke(this, drawerView, 0);
			requestLayout();
			invalidate();
			dispatchOnDrawerClosedMethed.invoke(this, drawerView);
		} catch (Exception e) {
			e.printStackTrace();

			// 反射异常时，正常关闭
			closeDrawer(gravity);
		}
	}

	// 打开侧边栏
	public void openDrawerLayout() {
		isClickDrawer = true;
		openDrawer(Gravity.LEFT);
	}

	// 关闭侧边栏
	public void closeDrawerLayout() {
		closeDrawer(Gravity.LEFT);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		Messenger.getInstance().addListener(this, Messenger.MSG_DRAWER_CHANGE);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		Messenger.getInstance().removeListener(this);
	}
	
	@Override
	public void handleMessage(int msgType, DataSet data) {
		if (msgType == Messenger.MSG_DRAWER_CHANGE) {
			if (isOpenDrawer) {
				closeDrawerLayout();
			}
			else {
				openDrawerLayout();
			}
		}
	}
}
