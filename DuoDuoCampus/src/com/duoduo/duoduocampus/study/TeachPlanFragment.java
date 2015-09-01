package com.duoduo.duoduocampus.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;

public class TeachPlanFragment extends BaseFragment implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.teachplan_view, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initUI(final View view) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			break;
		}
	}

}
