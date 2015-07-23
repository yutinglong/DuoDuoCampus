package com.duoduo.duoduocampus.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;

public class RegisterFragment extends BaseFragment implements OnClickListener {

	private View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.register_view, container, false);
		initUI();
		return mView;
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
	}
}
