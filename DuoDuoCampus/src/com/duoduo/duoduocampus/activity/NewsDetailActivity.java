package com.duoduo.duoduocampus.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.utils.DToast;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {
	private View back;
	
	private TextView mNewsTitle;
	private TextView mNewsDate;
	private TextView mNewsContent;
	private News mNews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_news_detail);
		initView(); 
		
		
		mNews = (News) getIntent().getSerializableExtra("currentNews");
		refreshData(mNews);
	}

	private void initView() {
		back = findViewById(R.id.back);
		mNewsTitle = (TextView) findViewById(R.id.news_detail_title);
		mNewsDate = (TextView) findViewById(R.id.news_detail_date);
		mNewsContent = (TextView) findViewById(R.id.news_detail_content);
		
		findViewById(R.id.news_detail_share).setOnClickListener(this);
		back.setOnClickListener(this);
	}
	
	private void refreshData(News mNews) {
		if (mNews.newTitle != null) {
			mNewsTitle.setText(mNews.newTitle);
		}
		if (mNews.newDate != null) {
			mNewsDate.setText(mNews.newDate.substring(0, 10));
		}
		
		if (mNews.newDetailContent != null) {
			mNewsContent.setText(Html.fromHtml(mNews.newDetailContent));
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
