package com.duoduo.duoduocampus;

import android.app.Activity;
import android.os.Bundle;

import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.utils.LogUtil;

/**
 * @title: MainActivity.java
 * @description:
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月13日
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		testMain();
	}

	private void testMain() {
		BaseAPI.getInstance().get(this, "twitter/fashion?offset=0&limit=30&trace=popular_2015-07-14_180&imei=865931025027285&mac=a0%3A86%3Ac6%3A81%3A64%3Adb&qudaoid=10006&access_token=edb5df863d7c413420fd883d2246d595&device_id=imei_865931025027285&st=1436952575", null,
				new BaseAPI.RequestListener<String>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
						LogUtil.d(LogUtil.YTL_TAG, "onStart----------");
					}

					@Override
					public void onCompleted(String result) {
						LogUtil.d(LogUtil.YTL_TAG, "onCompleted----------" + result);
					}

					@Override
					public void onException(int status, String result,
							String error) {
						LogUtil.d(LogUtil.YTL_TAG, "onException----------" + status);
					}
				});
	}
}
