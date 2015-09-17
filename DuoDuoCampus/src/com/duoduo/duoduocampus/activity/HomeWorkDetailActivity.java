package com.duoduo.duoduocampus.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.model.HomeWork;
import com.duoduo.duoduocampus.utils.DToast;

/**
 * @description: 家庭作业
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-9-17 上午9:20:37
 * @changeRecord
 */
public class HomeWorkDetailActivity extends BaseActivity implements OnClickListener {
	private View back;
	
	private TextView mHomeWorkTitle;
	private TextView mHomeWorkContent;
	private TextView mHomeWorkAnalysis;
	private TextView mHomeWorkComment;
	private HomeWork mHomeWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_home_work_detail);
		initView(); 
		
		
		mHomeWork = (HomeWork) getIntent().getSerializableExtra("currentHomeWork");
		refreshData(mHomeWork);
	}

	private void initView() {
		back = findViewById(R.id.back);
		mHomeWorkTitle = (TextView) findViewById(R.id.home_word_title);
		mHomeWorkContent = (TextView) findViewById(R.id.work_content);
		mHomeWorkAnalysis = (TextView) findViewById(R.id.work_analysis);
		mHomeWorkComment = (TextView) findViewById(R.id.work_comment);
		
		findViewById(R.id.news_detail_share).setOnClickListener(this);
		back.setOnClickListener(this);
	}
	
	private void refreshData(HomeWork mHomeWork) {
		if (mHomeWork.course != null && mHomeWork.course.couName != null) {
			mHomeWorkTitle.setText(mHomeWork.course.couName);
		}
		if (mHomeWork.workContent != null) {
			mHomeWorkContent.setText(mHomeWork.workContent);
		}
		if (mHomeWork.workAnalysis != null) {
			mHomeWorkAnalysis.setText(mHomeWork.workAnalysis);
		}
		if (mHomeWork.workComment != null) {
			mHomeWorkComment.setText(mHomeWork.workComment);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.news_detail_share:
			DToast.toastShort("分享待开发，敬请期待");
			break;
		}
	}
}
