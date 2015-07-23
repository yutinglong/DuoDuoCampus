package com.duoduo.duoduocampus.profile.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duoduo.duoduocampus.DuoDuoPrefences;
import com.duoduo.duoduocampus.R;
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
		mCoinText = (TextView) view.findViewById(R.id.profile_login_coin_text);// 金币
		mCoinView = view.findViewById(R.id.profile_login_coin_layout);
		layoutPurse = (OptionsItemView) view.findViewById(R.id.layout_purse);
	}

	private void initListener() {
		findViewById(R.id.login_nologin).setOnClickListener(this);
		
		findViewById(R.id.layout_login).setOnClickListener(this);
		findViewById(R.id.layout_purse).setOnClickListener(this);
		findViewById(R.id.register_nologin).setOnClickListener(this);
		findViewById(R.id.profile_login_coin_layout).setOnClickListener(this);
	}

	// 未登录状态
	private void changeUnloginView() {
		layoutPurse.setContent("");
		findViewById(R.id.layout_login).setVisibility(View.GONE);
		findViewById(R.id.layout_nologin).setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.login_nologin:
			Intent loginIntent = new Intent(context, LoginActivity.class);
			context.startActivity(loginIntent);
			break;
		}
	}
}
