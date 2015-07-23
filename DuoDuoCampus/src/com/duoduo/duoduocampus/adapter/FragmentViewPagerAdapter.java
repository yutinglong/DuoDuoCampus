package com.duoduo.duoduocampus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.duoduo.duoduocampus.BaseFragment;

public class FragmentViewPagerAdapter extends PagerAdapter {

	private List<BaseFragment> fragments;
	private FragmentManager fragmentManager;
	private List<Integer> stack = new ArrayList<Integer>();

	public FragmentViewPagerAdapter(FragmentManager fragmentManager,
			List<BaseFragment> fragments) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
	}

	@Override
	public int getCount() {
		return fragments.size() == 0 ? 0 : (fragments.size() <= 6 ? fragments.size() : 1000);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public void setCurrentPosition(int p) {
		stack.clear();
		stack.add(p);
		int pre = p == 0 ? fragments.size() - 1 : p - 1;
		stack.add(pre);
		int next = p == fragments.size() - 1 ? 0 : p + 1;
		stack.add(next);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// if(currentP == 0 && position == 0 || currentP == 5 && position == 0)
		// return;
		// position = position % fragments.size();
		// for(int i =0;i<container.getChildCount();i++){
		// View child = container.getChildAt(i);
		// int tag = (Integer) child.getTag();
		// Debug.error("jql", "tag->"+tag);
		// if(!stack.contains(tag)){
		// container.removeViewAt(i);
		// }
		// }
		removeView(container);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % fragments.size();
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commitAllowingStateLoss();
			fragmentManager.executePendingTransactions();
			fragment.getView().setTag(position);
		}
		
		removeView(container);
		
		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView());
		}
		
		return fragment.getView();
	}

	private void removeView(ViewGroup container) {
		for (int i = 0; i < fragments.size(); i++) {
			View child = fragments.get(i).getView();
			if (child != null && child.getTag() != null) {
				int tag = (Integer) child.getTag();
				if (!stack.contains(tag)) {
					container.removeView(child);
				}
			}
		}
	}

}
