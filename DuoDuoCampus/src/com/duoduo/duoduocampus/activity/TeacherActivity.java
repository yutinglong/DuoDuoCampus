package com.duoduo.duoduocampus.activity;

import android.os.Bundle;
import android.view.Window;

import com.duoduo.duoduocampus.R;

/**
 * @title: TeacherActivity.java
 * @description: 学习
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class TeacherActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_teacher);
		blockBackKey();
		
	}
}
