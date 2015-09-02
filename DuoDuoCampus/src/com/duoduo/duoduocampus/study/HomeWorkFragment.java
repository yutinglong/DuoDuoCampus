package com.duoduo.duoduocampus.study;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.duoduo.duoduocampus.BaseFragment;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.adapter.HomeWorkAdapter;
import com.duoduo.duoduocampus.api.BaseAPI;
import com.duoduo.duoduocampus.model.HomeWork;
import com.duoduo.duoduocampus.model.net.HomeWorks;
import com.duoduo.duoduocampus.utils.LogUtil;

public class HomeWorkFragment extends BaseFragment implements OnClickListener {
	private ListView mListView;
	private HomeWorkAdapter mAdapter;
	private List<HomeWork> dataList = new ArrayList<HomeWork>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onRefresh();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.homework_view, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initUI(final View view) {
		mListView = (ListView) view.findViewById(R.id.main_homework_list);
		
		mAdapter = new HomeWorkAdapter(getActivity(), dataList); 
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			break;
		}
	}

	private void onRefresh() {
		getHomeWorksData();
	}
	
	private void getHomeWorksData() {
		BaseAPI.getInstance().get(getActivity(), "homeworks", null,
				new BaseAPI.RequestListener<HomeWorks>() {
					@Override
					public void onStart(long requestId) {
						super.onStart(requestId);
					}

					@Override
					public void onCompleted(HomeWorks result) {
						LogUtil.d("YTL", "onCompleted : " + result);
						if (result != null) {
							if (result.items.size() > 0) {
								dataList.clear();
								for (HomeWork mHomeWork : result.items) {
									dataList.add(mHomeWork);
								}
							} else {

							}
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
						} else {
						}
					}

					@Override
					public void onException(int status, HomeWorks result,
							String error) {
						LogUtil.d("YTL", "onException : " + result + "");
					}
				});
	}
}
