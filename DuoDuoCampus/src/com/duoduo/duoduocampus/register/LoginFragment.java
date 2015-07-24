package com.duoduo.duoduocampus.register;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.utils.DToast;
import com.duoduo.duoduocampus.utils.LogUtil;

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

	private EditText mEditTextUserName;// 用户名
	private EditText mEditTextPwd;// 密码

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_view, container, false);
		initUI(view);
		return view;
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
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("passWord", pwd));
		BaseAPI.getInstance().post(getActivity(), "utils/userlogin", params,
				new BaseAPI.RequestListener<String>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
						LogUtil.d(LogUtil.YTL_TAG, "onStart----------");
					}

					@Override
					public void onCompleted(String result) {
						LogUtil.d(LogUtil.YTL_TAG, "onCompleted----------" + result);
						DToast.toastShort("result: " + result);
					}

					@Override
					public void onException(int status, String result,
							String error) {
						LogUtil.d(LogUtil.YTL_TAG, "onException----------" + status);
						DToast.toastShort("onException status: " + status);
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
