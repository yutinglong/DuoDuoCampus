package com.duoduo.duoduocampus.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.DataCenter;
import com.duoduo.duoduocampus.DuoDuoPrefences;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.activity.BaseActivity;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.DuoDuoUser;
import com.duoduo.duoduocampus.utils.Constants;
import com.duoduo.duoduocampus.utils.DToast;
import com.google.gson.Gson;

/**
 *
 * @title:用户登录
 * @description:
 * @company: 美丽说（北京）网络科技有限公司
 * @author yinxinya
 * @version 1.0
 * @created
 * @changeRecord
 */
public class LoginFragment extends BaseFragment implements OnClickListener {
	private Gson gson = new Gson();
	private EditText mEditTextUserName;// 用户名
	private EditText mEditTextPwd;// 密码

	

	private BroadcastReceiver user_login_receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
  			getActivity().setResult(200);
			getActivity().finish();
			
			if (DuoDuoPrefences.isLogin(getActivity())) {
				if (LoginActivity.mLoginListener != null) {
					LoginActivity.mLoginListener.onLoginSuccess();
				}
				LoginActivity.mLoginListener = null;
			}
			
 		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().registerReceiver(user_login_receiver,
				new IntentFilter(Constants.USER_LOGIN));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_view, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(user_login_receiver);
	}

	private void initUI(final View view) {
		mEditTextUserName = (EditText) view.findViewById(R.id.login_username);
		mEditTextPwd = (EditText) view.findViewById(R.id.login_pwd);
		mEditTextPwd.setImeOptions(EditorInfo.IME_ACTION_GO);
		mEditTextPwd.setOnEditorActionListener(editorActionListener);
		
		mEditTextUserName.setText("15212345678");
		mEditTextPwd.setText("123456");
	}
	
	TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if(actionId == EditorInfo.IME_ACTION_GO){
				login();
			}
			return false;
		}
	};

	/**
	 * 登录帐号
	 */
	private void login() {
		String userName = mEditTextUserName.getText().toString();
		String pwd = mEditTextPwd.getText().toString();
		if (TextUtils.isEmpty(userName)) {
			DToast.toastShort("用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			DToast.toastShort("密码不能为空");
			return;
		}
		
		((BaseActivity) getActivity()).showMeilishuoDialog();
		
		DuoDuoUser mDuoDuoUser = new DuoDuoUser();
		mDuoDuoUser.username = userName;
		mDuoDuoUser.password = pwd;
		String jsonStr = gson.toJson(mDuoDuoUser);
		
		BaseAPI.getInstance().post(getActivity(), "utils/userlogin", jsonStr,
				new BaseAPI.RequestListener<DuoDuoUser>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
						DuoDuoPrefences.setNickName(getActivity(), null);
					}

					@Override
					public void onCompleted(DuoDuoUser result) {
						((BaseActivity) getActivity()).dismissDialog();

						if (result != null) {
							DuoDuoPrefences.setNickName(getActivity(),
									result.username);
							
							DataCenter.mDuoDuoUser = result;
							getActivity().sendBroadcast(
									new Intent(Constants.USER_LOGIN));
						} else {
							DToast.toastShort("用户名或密码错误");
						}
						
					}

					@Override
					public void onException(int status, DuoDuoUser result,
							String error) {
						((BaseActivity) getActivity()).dismissDialog();
						
						DToast.toastShort("登录失败，请重试!");
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;
		}
	}

}
