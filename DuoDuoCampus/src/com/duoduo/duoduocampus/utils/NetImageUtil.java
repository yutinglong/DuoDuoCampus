package com.duoduo.duoduocampus.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 
 * @title: NetImageUtil.java
 * @description: Glide的封装类，需要Glide时候，请调用该类，为了统一入口，之后切换实现等扔可用
 * @company: 多多校园
 * @author: 于庭龙
 * @version: 1.0.0
 * @created：2015年7月17日
 */
public class NetImageUtil {
	public static void with_load_info(Context context, String pic_url,
			ImageView target) {
		Glide.with(context).load(pic_url).into(target);
	}
}