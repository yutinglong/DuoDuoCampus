package com.duoduo.duoduocampus.profile.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duoduo.duoduocampus.DataCenter;
import com.duoduo.duoduocampus.DuoDuoPrefences;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.activity.AboutActivity;
import com.duoduo.duoduocampus.activity.ParentSentrustsActivity;
import com.duoduo.duoduocampus.activity.ProfileSettingActivity;
import com.duoduo.duoduocampus.activity.StudentAppraisalsActivity;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.DuoDuoUser;
import com.duoduo.duoduocampus.register.LoginActivity;

public class ProfileSlideLayout extends RelativeLayout implements
		OnClickListener {

	private ImageView myPicture;
	private OptionsItemView layoutPurse;
	private TextView myNameWithVip;
	private ImageView loginNameRedTip;
	private ImageView ivDaren;
	private ImageView ivVip;
	private ImageView iconImg;
	private View mAddressView;
	private TextView mCoinText;
	private View mCoinView;
	
	private View mAboutView;

	private Context context;

	public ProfileSlideLayout(Context context) {
		this(context, null);
	}

	public ProfileSlideLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProfileSlideLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_profile_slide, null);
		addView(view);
		initView(view);
		initListener();
		if (isInEditMode()) {
			return;
		}
	}

	public void onResume() {
		if (DuoDuoPrefences.isLogin(context)) {
			// 数据同步
			// 获取头像
			getUserInfo();
			getUserIcon();
		
			refreshUserInfo();
			
			changeLoginView();
		} else {
			changeUnloginView();
		}
	}

	private void initView(View view) {
		myPicture = (ImageView) view.findViewById(R.id.profile_login_round_pic);
		ivDaren = (ImageView) view.findViewById(R.id.iv_daren);
		ivVip = (ImageView) view.findViewById(R.id.iv_vip);
		iconImg = (ImageView) view.findViewById(R.id.icon_img);
		myNameWithVip = (TextView) view.findViewById(R.id.profile_login_name);
		loginNameRedTip = (ImageView) view
				.findViewById(R.id.login_name_red_tip);
		mAddressView = view.findViewById(R.id.profile_login_address_text);
		mCoinText = (TextView) view.findViewById(R.id.profile_login_coin_text);
		mCoinView = view.findViewById(R.id.profile_login_coin_layout);
		layoutPurse = (OptionsItemView) view.findViewById(R.id.layout_purse);
		
		mAboutView = view.findViewById(R.id.layout_purse3);
	}

	private void initListener() {
		findViewById(R.id.login_nologin).setOnClickListener(this);
		
		findViewById(R.id.layout_login).setOnClickListener(this);
		findViewById(R.id.layout_purse).setOnClickListener(this);
		findViewById(R.id.register_nologin).setOnClickListener(this);
		findViewById(R.id.profile_login_coin_layout).setOnClickListener(this);
		
		findViewById(R.id.layout_purse1).setOnClickListener(this);
		findViewById(R.id.layout_purse2).setOnClickListener(this);
		mAboutView.setOnClickListener(this);
	}

	// 未登录状态
	private void changeUnloginView() {
		layoutPurse.setContent("");
		findViewById(R.id.layout_login).setVisibility(View.GONE);
		findViewById(R.id.layout_nologin).setVisibility(View.VISIBLE);
	}
	
	// 登录状态
	private void changeLoginView() {
		findViewById(R.id.layout_login).setVisibility(View.VISIBLE);
		findViewById(R.id.layout_nologin).setVisibility(View.GONE);
	}
	
	private void getUserIcon() {
//		BaseAPI.getInstance().get((Activity) context, "users/"+DuoDuoPrefences.getNickName(context)+"/portrait/", null,
//				new BaseAPI.RequestListener<DuoDuoUser>() {
//
//					@Override
//					public void onException(int status, DuoDuoUser result,
//							String error) {
//					}
//
//					@Override
//					public void onCompleted(DuoDuoUser result) {
//					}
//				});
	}
	
	private void refreshUserInfo() {
		if (DataCenter.mDuoDuoUser != null) {
			myNameWithVip.setText(DataCenter.mDuoDuoUser.realName);
		}
	}
	
	// 获取用户信息
	private void getUserInfo() {
		BaseAPI.getInstance().get((Activity) context, "users/"+DuoDuoPrefences.getNickName(context), null,
				new BaseAPI.RequestListener<DuoDuoUser>() {

					@Override
					public void onException(int status, DuoDuoUser result,
							String error) {
					}

					@Override
					public void onCompleted(DuoDuoUser result) {
						if (result != null) {
							DataCenter.mDuoDuoUser = result;
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.login_nologin:// 登录
			Intent loginIntent = new Intent(context, LoginActivity.class);
			context.startActivity(loginIntent);
			break;
		case R.id.register_nologin:// 注册
			Intent registerIntent = new Intent(context, LoginActivity.class);
			registerIntent.putExtra("type", "register");
			context.startActivity(registerIntent);
			break;
		case R.id.layout_login:// 个人信息
			Intent settingProfile = new Intent(context,
					ProfileSettingActivity.class);
			context.startActivity(settingProfile);
			break;
		case R.id.layout_purse3:// 关于
			Intent mAboutIntent = new Intent(context, AboutActivity.class);
			context.startActivity(mAboutIntent);
			break;
		case R.id.layout_purse2:// 家长嘱托
			Intent mParentSentrustsIntent = new Intent(context, ParentSentrustsActivity.class);
			context.startActivity(mParentSentrustsIntent);
			break;
		case R.id.layout_purse1:// 在校情况
			Intent mStudengAppraisalIntent = new Intent(context, StudentAppraisalsActivity.class);
			context.startActivity(mStudengAppraisalIntent);
			break;
			
			
		}
	}
}
