package com.duoduo.duoduocampus.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.msg.Messenger;

/**
 * @title: StudyActivity.java
 * @description: 学习
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class StudyActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_study);
		blockBackKey();
		
		initView();
	}
	
	private void initView() {
		View mView = findViewById(R.id.iv_slidebar);
		mView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Messenger.getInstance().sendMessage(Messenger.MSG_DRAWER_CHANGE);
			}
		});
	}
}
