package com.duoduo.duoduocampus.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.duoduo.duoduocampus.utils.NetImageUtil;

/**
 * @title: BulletinActivity.java
 * @description: 校园公告
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class BulletinActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bulletin);
		blockBackKey();
		
		testPicasso();
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
	
	private void testPicasso() {
//		String pic_url = "http://d06.res.meilishuo.net/img/_o/6d/31/265e349998e66157f45d959fe8cc_640_340.ch.jpg_f125009a_s0_640_340.jpg";
		String pic_url = "http://d02.res.meilishuo.net/ap/a/e8/41/ea366c6b640d614a2d4b4a48d726_484_484.cf.jpg.80.webp";
		ImageView mImageView = (ImageView) findViewById(R.id.test_imageview);
		NetImageUtil.with_load_info(this, pic_url, mImageView);
	}
}
