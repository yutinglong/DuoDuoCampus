package com.duoduo.duoduocampus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment implements OnClickListener {

	protected String targetClassName = "";

	protected View mReloadView;
	protected int limit = 30;

	protected String r;

	/** 判断Fragment是否已与Activity分离，true已分离，false未分离 **/
	public boolean isDetached = true;

	public interface OnReloadViewClickListener {
		public void reload();
	}

	public void setR(String r) {
		this.r = r;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		isDetached = false;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		targetClassName = getActivity().getClass().getSimpleName();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tView = new TextView(getActivity());
		return tView;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 回收内存
	 */
	public void save() {

	}

	/**
	 * 图片重新展示
	 */
	public void show() {

	}

	public void hide() {

	}

	/**
	 * 控制显示或者隐藏重载布局
	 * 
	 * @author yanbing.ye
	 * @param needShow
	 */
	public void reloadViewSwitcher(boolean needShow) {
		if (mReloadView == null) {
			return;
		}
		if (needShow) {
			mReloadView.setVisibility(View.VISIBLE);
		} else {
			mReloadView.setVisibility(View.GONE);
		}
	}


	@Override
	public void onDetach() {
		super.onDetach();
		isDetached = true;
	}

	@Override
	public void onClick(View v) {
	}

	public void toTop() {
	}
	
	public void hideLoadingView(){}

	public void onBackPressed() {
		getActivity().finish();
	}

	@Override
	public void setArguments(Bundle args) {
		try {
			super.setArguments(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setInitialSavedState(SavedState state) {
		try {
			super.setInitialSavedState(state);
		} catch (Exception e) {
			e.printStackTrace();
		}

	};

}
