package com.duoduo.duoduocampus.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.duoduo.duoduocampus.DataCenter;
import com.duoduo.duoduocampus.DuoDuoPrefences;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.profile.views.MessageBoxDialog;
import com.duoduo.duoduocampus.profile.views.RoundImageView;
import com.duoduo.duoduocampus.utils.DToast;

/**
 * 个人信息页面。
 */
public class ProfileSettingActivity extends BaseActivity implements
		OnClickListener {
	private final static int MSG_LOGIN_OUT_SUCCESS = 10036;
	
	private TextView nickNameText;
	private TextView genderText;
	
	private TextView exitText;
	
	private RoundImageView mHeadviewPic;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isFinishing()) {
				return;
			}
			switch (msg.what) {
			case MSG_LOGIN_OUT_SUCCESS:
				DToast.toastShort("登出成功");
				dismissDialog();
				finish();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_profile_view);
		
		initView();
		refleshUserInfo();
	}

	private void initView() {
		findViewById(R.id.setting_profile_btn_back).setOnClickListener(this);
		
		nickNameText = (TextView) findViewById(R.id.setting_profile_nickname_tip);
		genderText = (TextView) findViewById(R.id.setting_profile_gender_tip);
		exitText = (TextView) findViewById(R.id.setting_profile_exit);
		mHeadviewPic = (RoundImageView) findViewById(R.id.setting_profile_headview_pic);
		
		exitText.setOnClickListener(this);
	}

	private void refleshUserInfo() {
		if (DataCenter.mDuoDuoUser != null) {
			nickNameText.setText(DataCenter.mDuoDuoUser.realName);
			genderText.setText(DataCenter.mDuoDuoUser.gender);
			
			
//			Drawable mDrawable = getResources().getDrawable(R.drawable.head_default_big);
//			BitmapDrawable mBitmap = (BitmapDrawable)mDrawable;
//			mHeadviewPic.setRoundImage(mBitmap.getBitmap());
		}
	}
	
	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.setting_profile_btn_back:// 返回
			this.finish();
			break;
		case R.id.setting_profile_exit:// 退出登录
			MessageBoxDialog.Builder builder = new MessageBoxDialog.Builder(
					this, 2);
			builder.setMessageAndStyle("您确定要退出多多校园吗?",
					R.style.MessageBoxMessageStyle)
					.setOtherButton("确定", R.style.DoneButtonStyle,
							R.drawable.btn_warn_bg,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									if (!ProfileSettingActivity.this
											.isFinishing()) {
										showMeilishuoDialog();
										logout();
										dialog.dismiss();
									}
								}
							})
					.setCancelButton("取消",
							R.style.CancelButtonStyle, R.drawable.btn_done_bg,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create().show();
			break;
		}
	}

	private void logout() {
		DataCenter.clearUserData();
		DuoDuoPrefences.setNickName(this, null);
		handler.sendEmptyMessage(MSG_LOGIN_OUT_SUCCESS);
	}
}
