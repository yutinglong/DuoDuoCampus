package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.FragmentViewPagerAdapter;
import com.duoduo.duoduocampus.msg.Messenger;
import com.duoduo.duoduocampus.study.CoursesFragment;
import com.duoduo.duoduocampus.study.HomeWorkFragment;
import com.duoduo.duoduocampus.study.TeachPlanFragment;
import com.duoduo.duoduocampus.views.HomeTabView;
import com.duoduo.duoduocampus.views.HomeTabView.TabIndicator;
import com.duoduo.duoduocampus.views.MyViewPager;

/**
 * @title: StudyActivity.java
 * @description: 学习
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月23日
 */
public class StudyActivity extends BaseActivity implements OnPageChangeListener, OnClickListener {
	private HomeTabView tabView;
	private MyViewPager viewPager;
	private List<MyIndicator> titleList = new ArrayList<MyIndicator>();
	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	
	private TeachPlanFragment mTeachPlanFragment;
	private CoursesFragment mCoursesFragment;
	private HomeWorkFragment mHomeWorkFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_study);
		blockBackKey();
		
		initView();
		initUI();
	}
	
	private void initUI() {
		hideSoftInputOutsideEditText();
		tabView = (HomeTabView) findViewById(R.id.study_tab);
		MyIndicator teachplans = new MyIndicator(getString(R.string.study_teachplans));
		titleList.add(teachplans);
		
		MyIndicator courses = new MyIndicator(
				getString(R.string.study_courses));
		titleList.add(courses);
		
		MyIndicator homeworks = new MyIndicator(
				getString(R.string.study_homeworks));
		titleList.add(homeworks);
		
		tabView.notifyPageCountChanged(0, titleList);
		tabView.setOnIndicatorClick(new HomeTabView.OnIndicatorClick() {

			@Override
			public void onIndicatorClick(int index, View view) {
				viewPager.setCurrentItem(index);
			}
		});
		viewPager = (MyViewPager) findViewById(R.id.viewpager);
		
		mTeachPlanFragment = new TeachPlanFragment();
		mCoursesFragment = new CoursesFragment();
		mHomeWorkFragment = new HomeWorkFragment();
		
		fragments.add(mTeachPlanFragment);
		fragments.add(mCoursesFragment);
		fragments.add(mHomeWorkFragment);
		
		
		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(
				getSupportFragmentManager(), fragments);
		adapter.setCurrentPosition(0);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		
		viewPager.setCurrentItem(0);
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
	
	private class MyIndicator implements TabIndicator {
		private String name;
		public MyIndicator(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		tabView.onPageSelected(arg0);
	}

	
	@Override
	public void onClick(View v) {
		BaseFragment fragment = fragments.get(viewPager.getCurrentItem());
		fragment.onClick(v);
	}

	@Override
	public void onBackPressed() {
		BaseFragment fragment = fragments.get(viewPager.getCurrentItem());
		fragment.onBackPressed();
	}
}
