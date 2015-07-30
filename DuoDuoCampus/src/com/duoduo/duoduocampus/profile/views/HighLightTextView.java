package com.duoduo.duoduocampus.profile.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;

public class HighLightTextView extends TextView {

	private final int DEFAULT_HIGHLIGHT_COLOR = 0xFF0000;
	private int mHighLightTextColor;
	public HighLightTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData(attrs);
	}

	public HighLightTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HighLightTextView(Context context) {
		this(context,null);
	}

	private void initData(AttributeSet attrs) {
		
		if (attrs != null) {
			String content = (String) this.getText();
			TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.HighLightTextView);
			
			String highLightText = (String) a.getText(R.styleable.HighLightTextView_highLightText);
			mHighLightTextColor = a.getColor(R.styleable.HighLightTextView_highLightTextColor, DEFAULT_HIGHLIGHT_COLOR);
			
			if(content!=null && highLightText !=null){
				int start = content.indexOf(highLightText);
				int end = start + highLightText.length();
				setColorSpan(content, start, end);
			}
			a.recycle();
		}
	}

	private void setColorSpan(String text, int start, int end) {
		SpannableStringBuilder style=new SpannableStringBuilder(text); 
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(mHighLightTextColor);
		style.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		this.setText(style);
	}

	
	public void decorateText(String text, String highLightText, int colorId) {
		SpannableString ss = new SpannableString(text);
		if (text.contains(highLightText)) {
			
			int start = text.indexOf(highLightText);
			int end = start + highLightText.length();
			ss.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(colorId)), start, end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		setText(ss);
	}
}
