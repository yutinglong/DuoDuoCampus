package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.api.BaseAPI;
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
	
	private Dialog mWaitingDialog;
	private boolean isWaitingDialogCancelable = false;
	
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
	
	public void showWaitingDialog() {
		showMeilishuoDialog("正在加载...");
	}

	public void showMeilishuoDialog() {
		showMeilishuoDialog(null);
	}

	public void showMeilishuoDialog(String msg) {
		showMeilishuoDialog(msg, -1);
	}

	public void showMeilishuoDialog(int rId) {
		showMeilishuoDialog(getResources().getString(rId));
	}
	
	public void showMeilishuoDialog(String msg, final long requestId, OnCancelListener mOnCancelListener) {
		View contentView = null;
		if (mWaitingDialog == null) {
			mWaitingDialog = new Dialog(this, R.style.DialogNoBgStyle);
			contentView = View.inflate(getApplicationContext(),
					R.layout.waitting_dialog, null);
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			mWaitingDialog.addContentView(contentView, lp);
			mWaitingDialog.setCancelable(true);
			mWaitingDialog.setCanceledOnTouchOutside(isWaitingDialogCancelable);
		} else {
			contentView = mWaitingDialog.findViewById(R.id.content);
		}
		TextView loading_msg = (TextView) contentView
				.findViewById(R.id.loading_msg);
		if (!TextUtils.isEmpty(msg)) {
			loading_msg.setVisibility(View.VISIBLE);
			loading_msg.setText(msg);
			contentView.setBackgroundResource(R.drawable.progressbar_bg);
		} else {
			loading_msg.setVisibility(View.GONE);
			contentView.setBackgroundColor(Color.TRANSPARENT);
		}
		if (!isFinishing() && !mWaitingDialog.isShowing()) {
			mWaitingDialog.show();
		}
		mWaitingDialog.setOnCancelListener(mOnCancelListener);
	}
	
	public void showMeilishuoDialog(String msg, final long requestId) {
		showMeilishuoDialog(msg, requestId, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				BaseAPI.getInstance().abort(requestId);
			}
		});
 	}
	public void dismissDialog() {
		if (!isFinishing() && mWaitingDialog != null
				&& mWaitingDialog.isShowing()) {
			mWaitingDialog.dismiss();
		}
	}
	public void setDialogOnDismissListener(OnDismissListener listener){
		if(mWaitingDialog != null) {
			mWaitingDialog.setOnDismissListener(listener);
		}
	}
}
