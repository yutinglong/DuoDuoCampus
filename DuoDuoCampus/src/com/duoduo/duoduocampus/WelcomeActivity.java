package com.duoduo.duoduocampus;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * @title: MainActivity.java
 * @description:
 * @company: 多多校园
 * @author tinglongyu
 * @version: 1.0.0
 * @created：2015年7月13日
 */
public class WelcomeActivity extends Activity implements OnClickListener {
	public static final int CODE_SPLASHPAGE_RETURN = 1000;
	private static final int SPLASH_TASK_RESULT = 1012010;
	
	protected Handler handler = new MyHandler(this);
	
	private static class MyHandler extends Handler {
		private final WeakReference<WelcomeActivity> referenceActivity;

		private MyHandler(WelcomeActivity activity) {
			this.referenceActivity = new WeakReference<WelcomeActivity>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			WelcomeActivity mActivity = referenceActivity.get();
			if (mActivity != null && !mActivity.isFinishing()) {
				switch (msg.what) {
				case SPLASH_TASK_RESULT:
					mActivity.splashResult();
					break;
				}
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		handler.sendEmptyMessageDelayed(SPLASH_TASK_RESULT, 2000);
	}

	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
	}
	
	private void splashResult() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}
}
