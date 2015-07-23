package com.duoduo.duoduocampus.profile.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DisplayUtil;
import com.duoduo.duoduocampus.utils.LogUtil;

public class OptionsItemView extends RelativeLayout {


	private int titleColor, contentColor, numColor;
	private int numBg;
	private String title, content, num;
	private int image;
	private int titleSize;

	private ImageView ico, dot, array;
	private TextView tvTitle,tvContent, tvNum;
	
	public OptionsItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(attrs);
	}

	public OptionsItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public OptionsItemView(Context context) {
		super(context);
		init(null);
	}

	private void init(AttributeSet attrs) {
		int padding = DisplayUtil.dipToPixels(getContext(), 5);
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_options_item, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(view, params);
		ico = (ImageView) view.findViewById(R.id.iv_ico);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvContent = (TextView) view.findViewById(R.id.tv_content);
		tvNum = (TextView) view.findViewById(R.id.tv_num);
		array = (ImageView) view.findViewById(R.id.iv_arrow);
		dot = (ImageView) view.findViewById(R.id.iv_dot);
		
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.OptionsItemView);
		titleColor = a.getColor(R.styleable.OptionsItemView_optionTitleColor, getResources().getColor(R.color.grey_d2));
		contentColor = a.getColor(R.styleable.OptionsItemView_optionContentColor, getResources().getColor(R.color.pink));
		numColor = a.getColor(R.styleable.OptionsItemView_optionNumColor, getResources().getColor(R.color.white));
		numBg = a.getResourceId(R.styleable.OptionsItemView_optionNumBg, R.drawable.shoplist_out_num);
		image = a.getResourceId(R.styleable.OptionsItemView_optionImage, 0);
		title = a.getString(R.styleable.OptionsItemView_optionTitle);
		content = a.getString(R.styleable.OptionsItemView_optionContent);
		num = a.getString(R.styleable.OptionsItemView_optionNum);
		int array_img = a.getResourceId(R.styleable.OptionsItemView_optionArray, 0);
		int titleSize = a.getInt(R.styleable.OptionsItemView_optionTitleSize, 0);
		
		tvTitle.setTextColor(titleColor);
		tvContent.setTextColor(contentColor);
		tvNum.setTextColor(numColor);
		tvNum.setBackgroundResource(numBg);
		tvNum.setPadding(2*padding, padding, padding, 2*padding);
		
		if(!TextUtils.isEmpty(num)){
			tvNum.setVisibility(View.VISIBLE);
			tvNum.setText(num);
		}
		tvTitle.setText(title);
		tvContent.setText(content);
		
		if(image > 0){
			ico.setVisibility(View.VISIBLE);
			ico.setImageResource(image);
		}
		
		if(array_img > 0){
			array.setImageResource(array_img);
		}
		
		if(titleSize > 0){
			tvTitle.setTextSize(titleSize);
		}
		
		a.recycle();
	}

	public void setTitle(String title){
		this.title = title;
		tvTitle.setText(title);
	}
	
	public void setContent(String content){
		this.content = content;
		tvContent.setText(content);
	}

    public void setContentColor(int color){
        tvContent.setTextColor(color);
    }

    public void setContent(int contentRes){
		this.content = getContext().getResources().getString(contentRes);
		tvContent.setText(contentRes);
	}
	
	public void setContentUrl(final String url){
		if(!TextUtils.isEmpty(url)){
			tvContent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("OptionsItemView onClick");
				}
			});
		}
	}
	
	public void showDot(){
		dot.setVisibility(View.VISIBLE);
	}
	
	public void hideDot(){
		dot.setVisibility(View.GONE);
	}
	
	public void setIconResource(int id){
		if(id > 0){
			ico.setImageResource(id);
			ico.setVisibility(View.VISIBLE);
		}else{
			ico.setVisibility(View.GONE);
		}
		
	}
	
	public void setNum(String num){
		this.num = num;
		if(!TextUtils.isEmpty(num)){
			tvNum.setVisibility(View.VISIBLE);
			tvNum.setText(num);
		}else{
			tvNum.setVisibility(View.GONE);
		}
	}

}
