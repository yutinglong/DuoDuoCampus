package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.profile.views.CustomDrawerLayout;
import com.duoduo.duoduocampus.utils.DToast;

/**
 * 
 * Activity基类 activity通用的内容
 * 
 */
public class BaseActivity extends FragmentActivity {
	private boolean mHideSoftOutsideEditText = false;
	private boolean mBlockBackKey = false;
	
	protected boolean isExit = false;
	protected Timer timer;
	protected MyTimerTask task;

	private ArrayList<View> mIgnoreViews = new ArrayList<View>();

	/**
	 * 当touch非输入框Edittext区域自动隐藏键盘
	 */
	protected void hideSoftInputOutsideEditText() {
		mHideSoftOutsideEditText = true;
	}
	/**
	 * 当touch非输入框Edittext区域自动隐藏键盘
	 */
	protected void hideSoftInputOutsideEditText(View ignoreView) {
		mHideSoftOutsideEditText = true;
		if (ignoreView != null && !mIgnoreViews.contains(ignoreView)) {
			mIgnoreViews.add(ignoreView);
		}
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		try {
			boolean result = super.dispatchTouchEvent(ev);
			if (mHideSoftOutsideEditText && getCurrentFocus() != null) {
				boolean flag = false;
				switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					// 从ACTION_DOWN的到currentFocus变化 有一定的延迟
					if (ev.getEventTime() - ev.getDownTime() > 100) {
						flag = true;
					}
					break;
				case MotionEvent.ACTION_UP:
					flag = true;
					break;
				}
				if (!flag) {
					return result;
				}
				for (View view : mIgnoreViews) {
					if (view != null) {
						if (isTouchInsideView(ev, view)) {
							return super.dispatchTouchEvent(ev);
						}
					}
				}
				final View currentFocus = getCurrentFocus();
				if (!(currentFocus instanceof EditText)
						|| !isTouchInsideView(ev, currentFocus)) {
					((InputMethodManager) getApplicationContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(getCurrentFocus()
									.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					onHideSoftInput();
				}

			}
			return result;
		} catch (Exception e) {
		}
		return false;

	}

	/**
	 * 非edittext区域滑动隐藏软键盘
	 */
	public void onHideSoftInput() {

	}

	/**
	 * 判断touch事件MotionEvent 是否发生在currentFocus上面
	 * 
	 * @param ev
	 * @param currentFocus
	 * @return
	 */
	private boolean isTouchInsideView(final MotionEvent ev,
			final View currentFocus) {
		final int[] loc = new int[2];
		currentFocus.getLocationOnScreen(loc);
		return ev.getRawX() > loc[0] && ev.getRawY() > loc[1]
				&& ev.getRawX() < (loc[0] + currentFocus.getWidth())
				&& ev.getRawY() < (loc[1] + currentFocus.getHeight());

	}

	/**
	 * 对返回事件拦截
	 */
	protected void blockBackKey() {
		mBlockBackKey = true;
	}

	public boolean isBlockBackKey() {
		return mBlockBackKey;
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			isExit = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mBlockBackKey && keyCode == KeyEvent.KEYCODE_BACK) {
			if (CustomDrawerLayout.isOpenDrawer) {
				Messenger.getInstance().sendMessage(Messenger.MSG_DRAWER_CHANGE);
				isExit = false;
				return true;
			} else if (isExit) {
				if (timer != null) {
					timer.cancel();
				}
				finish();
			} else {
				isExit = true;
				if (task != null) {
					task.cancel();
				}
				DToast.toastShort(R.string.exit_app_tip);
				task = new MyTimerTask();
				timer = new Timer();
				timer.schedule(task, 3000);
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
