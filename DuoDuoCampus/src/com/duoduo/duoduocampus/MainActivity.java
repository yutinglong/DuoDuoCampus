package com.duoduo.duoduocampus;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.duoduo.duoduocampus.activity.BulletinActivity;
import com.duoduo.duoduocampus.activity.StudyActivity;
import com.duoduo.duoduocampus.activity.TabChangeListener;
import com.duoduo.duoduocampus.activity.TeacherActivity;
import com.duoduo.duoduocampus.profile.views.ProfileSlideLayout;

/**
 * @title: MainActivity.java
 * @description:
 * @company: 多多校园
 * @author tinglongyu
 * @version: 1.0.0
 * @created：2015年7月13日
 */
public class MainActivity extends ActivityGroup implements OnClickListener {
	private ProfileSlideLayout menu;
	private ViewGroup container;
	private LocalActivityManager manager;
	
	private ImageView home_icon1, home_icon2, home_icon3;
	private TextView home_text1, home_text2, home_text3;
	private Intent[] intents = new Intent[3];
	private int currentTabIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initTabIntent(savedInstanceState);
	}

	public void onResume() {
		super.onResume();
		menu.onResume();
	}
	
	private void initView(){
		menu = (ProfileSlideLayout)this.findViewById(R.id.layout_menu);
		int width = this.getWindowManager().getDefaultDisplay().getWidth();
		width = width * 8 / 10;
		menu.getLayoutParams().width = width;
		
		this.findViewById(R.id.home_tab1).setOnClickListener(this);
		this.findViewById(R.id.home_tab2).setOnClickListener(this);
		this.findViewById(R.id.home_tab3).setOnClickListener(this);
		
		container = (ViewGroup) this.findViewById(R.id.act_container);
		home_icon1 = (ImageView) findViewById(R.id.home_icon1);
		home_icon2 = (ImageView) findViewById(R.id.home_icon2);
		home_icon3 = (ImageView) findViewById(R.id.home_icon3);
		
		home_text1 = (TextView) findViewById(R.id.home_text1);
		home_text2 = (TextView) findViewById(R.id.home_text2);
		home_text3 = (TextView) findViewById(R.id.home_text3);
	}
	
	private void initTabIntent(Bundle savedInstanceState) {
		Intent homeIntent1 = new Intent(this, BulletinActivity.class);
		Intent homeIntent2 = new Intent(this, StudyActivity.class);
		Intent homeIntent3 = new Intent(this, TeacherActivity.class);
		manager = this.getLocalActivityManager();
		intents[0] = homeIntent1;
		intents[1] = homeIntent2;
		intents[2] = homeIntent3;

		if (savedInstanceState != null) {
			currentTabIndex = savedInstanceState.getInt("init_tab_pos");
		}

		changeTab(currentTabIndex);
	}
	
	public void changeTab(int index) {
		Activity curActivity = manager.getCurrentActivity();
		if(curActivity!=null && curActivity instanceof TabChangeListener) {
			((TabChangeListener)curActivity).onTabChange(false);
		}
		
 		Window window = manager.startActivity("mainTab:" + index, intents[index]);
		int childNum = container.getChildCount();
		boolean isAdded = false;
		for (int i = 0; i < childNum; i++) {
			View child = container.getChildAt(i);
			if (child == window.getDecorView()) {
				isAdded = true;
				child.setVisibility(View.VISIBLE);
			}
			else {
				child.setVisibility(View.INVISIBLE);
			}
		}
		
		if (!isAdded) {
			container.addView(window.getDecorView());
		}
		 
		curActivity = manager.getCurrentActivity();
		if(curActivity!=null && curActivity instanceof TabChangeListener) {
			((TabChangeListener)curActivity).onTabChange(true);
		}
 		
		window.getDecorView().setFocusableInTouchMode(true);
		window.getDecorView().requestFocus();
		resetTabStyle(index);
		currentTabIndex = index;
	}
	
	private void resetTabStyle(int index) {
		home_icon1.setImageResource(R.drawable.home_ico_home);
		home_icon2.setImageResource(R.drawable.home_ico_home);
		home_icon3.setImageResource(R.drawable.home_ico_home);
		home_text1.setTextColor(getResources().getColor(R.color.grey_d2));
		home_text2.setTextColor(getResources().getColor(R.color.grey_d2));
		home_text3.setTextColor(getResources().getColor(R.color.grey_d2));

		switch (index) {
		case 0:
			home_icon1.setImageResource(R.drawable.home_ico_home_press);
			home_text1.setTextColor(getResources().getColor(R.color.pink_l2));
			break;
		case 1:
			home_icon2.setImageResource(R.drawable.home_ico_home_press);
			home_text2.setTextColor(getResources().getColor(R.color.pink_l2));
			break;
		case 2:
			home_icon3.setImageResource(R.drawable.home_ico_home_press);
			home_text3.setTextColor(getResources().getColor(R.color.pink_l2));
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		int targetIndex = -1;
		switch (v.getId()) {
		case R.id.home_tab1:
			targetIndex = 0;
			break;
		case R.id.home_tab2:
			targetIndex = 1;
			break;
		case R.id.home_tab3:
			targetIndex = 2;
 			break;
		}

		changeTab(targetIndex);
	}
}
