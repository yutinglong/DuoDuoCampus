package com.duoduo.duoduocampus.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DisplayUtil;
import com.duoduo.duoduocampus.utils.LogUtil;

/**
 * 自定义tab
 * 
 * @author wanyuyong
 * 
 */
public class HomeTabView extends LinearLayout {

	private final static String TAG = "HomeTabView";

	protected int mCurrentIndex;
	private int mCount;
	private Paint mPaint;
	protected float mGapWidth;
	private float mLineBeyond;
	protected int mTextColor;
	private int mTextSelectedColor;
	protected float mTextSize;
	private int mLineColor;
	private boolean animation = true;
	private float zoom_scale;
	private boolean showMiddleLine;

	protected List<TextView> tabs = new ArrayList<TextView>();
	private List objects;
	private TextView currentTab;
	protected OnIndicatorClick mOnIndicatorClick;

	private float mLineWidth;

	private int mCustomDrawableResId;
	private int mCustomIndex;
	private static final int DEFAULT_LINE_COLOR = 0XFFF2F23D;
	private static final int DEFAULT_PAINT_WIDTH = 1;
	private static final int DEFAULT_GAP_WIDTH = 20;
	private static final int DEFAULT_LINE_BEYOND = 10;
	private static final int DEFAULT_TEXT_SIZE = 15;
	private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
	private static final int DEFAULT_TEXT_SELECTED_COLOR = Color.WHITE;
	private static final float DEFAULT_ZOOM_SCALE = 1.1f;
	private static final int DEFAULT_CUSTOM_DRAWABLE_ID = 0;
	private static final int DEFAULT_CUSTOM_INDEX = 0;

	private View customView;

	private boolean showBottomLine;

	public HomeTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(attrs);
	}

	public HomeTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public HomeTabView(Context context) {
		super(context);
		init(null);
	}

	public OnIndicatorClick getOnIndicatorClick() {
		return mOnIndicatorClick;
	}

	public void setOnIndicatorClick(OnIndicatorClick mOnIndicatorClick) {
		this.mOnIndicatorClick = mOnIndicatorClick;
	}

	private void init(AttributeSet attrs) {

		setOrientation(HORIZONTAL);

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.HomeTabView);
		mGapWidth = a.getDimension(R.styleable.HomeTabView_gapWidth,
				DisplayUtil.dipToPixels(getContext(), DEFAULT_GAP_WIDTH));
		mLineBeyond = a.getDimension(R.styleable.HomeTabView_lineBeyond,
				DisplayUtil.dipToPixels(getContext(), DEFAULT_LINE_BEYOND));
		mTextColor = a.getColor(R.styleable.HomeTabView_defaultColor,
				DEFAULT_TEXT_COLOR);
		mTextSelectedColor = a.getColor(R.styleable.HomeTabView_selectColor,
				DEFAULT_TEXT_SELECTED_COLOR);
		mTextSize = a.getDimension(R.styleable.HomeTabView_tabTextSize,
				DisplayUtil.spToPixels(getContext(), DEFAULT_TEXT_SIZE));
		mLineColor = a.getColor(R.styleable.HomeTabView_lineColor,
				DEFAULT_LINE_COLOR);

		mLineWidth = a.getDimension(R.styleable.HomeTabView_lineWidth,
				DisplayUtil.dipToPixels(getContext(), DEFAULT_PAINT_WIDTH));
		animation = a.getBoolean(R.styleable.HomeTabView_animation, true);
		zoom_scale = a.getFloat(R.styleable.HomeTabView_zoomScale,
				DEFAULT_ZOOM_SCALE);

		mCustomDrawableResId = a
				.getResourceId(R.styleable.HomeTabView_customResId,
						DEFAULT_CUSTOM_DRAWABLE_ID);
		mCustomIndex = a.getInteger(R.styleable.HomeTabView_customIndex,
				DEFAULT_CUSTOM_INDEX);
		showMiddleLine=a.getBoolean(R.styleable.HomeTabView_showMiddleLine,
				false);
		showBottomLine = a.getBoolean(R.styleable.HomeTabView_showBottomLine,
				true);
		a.recycle();
		mPaint = new Paint();
		mPaint.setColor(mLineColor);
		mPaint.setStrokeWidth(mLineWidth);
		setGravity(Gravity.CENTER);
	}

	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (showBottomLine) {
			if (tabs.size() == 0)
				return;
			TextView tab = tabs.get(mCurrentIndex);
			float tabLen = getTabTextLen(tab);
			int tabLeft = 0;
			if (mCurrentIndex == mCustomIndex) {
				if (mCustomDrawableResId > 0) {
					int[] lt = new int[2];
					tab.getLocationInWindow(lt);
					tabLeft = lt[0];
				} else {
					tabLeft = getTabLeft(mCurrentIndex);
				}
			} else {
				tabLeft = getTabLeft(mCurrentIndex);
			}
			float startX = tabLeft + (tab.getWidth() - tabLen) / 2
					- mLineBeyond;
			canvas.drawLine(startX, getHeight() - getPaddingBottom()
					- mLineWidth, startX + tabLen + mLineBeyond * 2,
					getHeight() - getPaddingBottom() - mLineWidth, mPaint);
		}
	}

	private float getTabTextLen(TextView tab) {
		return tab.getPaint().measureText(tab.getText().toString());
	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		LogUtil.d(TAG, "position : " + position + ", positionOffset : "
				+ positionOffset + ", positionOffsetPixels : "
				+ positionOffsetPixels);
	}

	public void onPageSelected(int position) {
		mCurrentIndex = position;
		changeCurrentTab();
		invalidate();
	}

	public void resetCurrentTab() {
		if (currentTab != null) {
			currentTab = null;
		}
	}
	
	protected void changeCurrentTab() {
		if (currentTab != null) {
			currentTab.setTextColor(mTextColor);
			currentTab.clearAnimation();
		}
		currentTab = tabs.get(mCurrentIndex);
		currentTab.setTextColor(mTextSelectedColor);
		if (animation) {
			Animation animation = new ScaleAnimation(1, zoom_scale, 1,
					zoom_scale, Animation.RELATIVE_TO_SELF, .5f,
					Animation.RELATIVE_TO_SELF, .5f);
			animation.setFillAfter(true);
			animation.setDuration(150);
			currentTab.startAnimation(animation);

		}
	}

	public void changeCurrentTab(int position) {
		mCurrentIndex = position;
		invalidate();
		changeCurrentTab();
	}

	public void onPageScrollStateChanged(int state) {
	}

	public void notifyPageCountChanged(int current, List objects) {
		this.objects = objects;
		tabs.clear();
 
		removeAllViews();
		if (objects != null) {
			int index = -1;
			for (Object o : objects) {

				index++;
				if (o instanceof TabIndicator) {
					if (mCustomIndex == index) {
						if (mCustomDrawableResId > 0) {
							addTab(((TabIndicator) o).getName(),
									mCustomDrawableResId);
						} else {
							addTab(((TabIndicator) o).getName());
						}
					} else {
						addTab(((TabIndicator) o).getName());
					}
				} else if (o instanceof String) {
					if (mCustomIndex == index) {
						if (mCustomDrawableResId > 0) {
							addTab((String) o, mCustomDrawableResId);
						} else {
							addTab((String) o);
						}
					} else {
						addTab((String) o);
					}
				}
			}
			index = -1;
			mCurrentIndex = current;
			mCount = objects.size();
			invalidate();
			changeCurrentTab();
		}
	}

	public void refreshTabName() {
		if (objects != null && tabs != null && !tabs.isEmpty()
				&& tabs.size() == objects.size()) {
			for (int i = 0; i < tabs.size(); i++) {
				TextView tab = tabs.get(i);
				Object o = objects.get(i);
				if (o instanceof TabIndicator) {
					tab.setText(Html.fromHtml(((TabIndicator) o).getName()));
				}
			}
		}
	}

	public void setTextColor(int color) {
		mTextColor = color;
		for (TextView tab : tabs) {
			tab.setTextColor(mTextColor);
		}
	}

	public void showAnimation(boolean show) {
		animation = show;
	}

	public void setTextSelectedColor(int color) {
		mTextSelectedColor = color;
		if (currentTab != null) {
			currentTab.setTextColor(color);
		}
	}

	public void setLineColor(int color) {
		mLineColor = color;
		mPaint.setColor(mLineColor);
		invalidate();
	}

	public void setGapWidth(int width) {
		mGapWidth = DisplayUtil.dipToPixels(getContext(), width);
		for (TextView tab : tabs) {
			tab.setPadding((int) (mGapWidth / 2), getPaddingTop(),
					(int) (mGapWidth / 2), getPaddingBottom());
		}
	}

	protected void addTab(String text) {
		if(getChildCount()!=0&&showMiddleLine){
			View view = new ImageView(getContext());
			view.setBackgroundResource(R.drawable.home_tab_middle_line);
			LayoutParams params = generateDefaultLayoutParams();
			
			params.height = (int)(DisplayUtil.getFontHeightOnlyText(mTextSize) + 2.0f);
			params.leftMargin = DisplayUtil.dipToPixels(getContext(), 0);
			addView(view, params);
		}
		final TextView tab = new TextView(
				getContext());
		tab.setGravity(Gravity.CENTER);
		tab.setText(Html.fromHtml(text));
		tab.setPadding((int) (mGapWidth / 2), getPaddingTop(),
				(int) (mGapWidth / 2), getPaddingBottom());
		tab.setTextColor(mTextColor);
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		tabs.add(tab);
		LayoutParams lp = generateDefaultLayoutParams();
		lp.weight = 1;
		addView(tab, lp);
		tab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mOnIndicatorClick != null) {
					mOnIndicatorClick.onIndicatorClick(tabs.indexOf(tab), tab);
				}
			}
		});

	}

	protected void addTab(String text, int imageResourceId) {
		final LinearLayout tabLayout = new LinearLayout(getContext());
		tabLayout.setId(R.id.icon_sort_by_what);
		tabLayout.setGravity(Gravity.CENTER);
		tabLayout.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams params = generateDefaultLayoutParams();
		params.weight = 1;
		tabLayout.setLayoutParams(params);
		final TextView tab = new TextView(getContext());
		tabs.add(tab);
		tab.setText(Html.fromHtml(text));
		tab.setGravity(Gravity.CENTER);
		tab.setPadding((int) (mGapWidth / 2), getPaddingTop(),
				(int) (mGapWidth / 2), getPaddingBottom());
		tab.setTextColor(mTextColor);
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		tabLayout.addView(tab, generateDefaultLayoutParams());

		View view = new ImageView(getContext());
		customView = view;
		view.setBackgroundResource(imageResourceId);
		params = generateDefaultLayoutParams();
		params.leftMargin = DisplayUtil.dipToPixels(getContext(), 0);
		tabLayout.addView(view, params);
		addView(tabLayout);
		tabLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnIndicatorClick != null) {
					mOnIndicatorClick.onIndicatorClick(tabs.indexOf(tab),
							tabLayout);
				}
			}
		});
	}

	public View getCustomView() {
		return this.customView;
	}

	public interface TabIndicator {
		String getName();
	}

	public interface OnIndicatorClick{
		void onIndicatorClick(int index, View view);
 	}
	
	protected int getTabLeft(int index)
	{
 		TextView tab = tabs.get(mCurrentIndex);
		return tab.getLeft();
	}
	
}
