package com.duoduo.duoduocampus;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 应用shareprefences的存储
 * 
 * @author yutinglong
 * 
 */
public class DuoDuoPrefences {
	private Context mContext = null;
	
	private final static String settingFile = "meilishuo_settings";
	private final static String mAccessTokenString = "accesstoken";

			
	// private static String mDefaultToken = "8e574200349ddce86bd0c9d689338b42";
	private static String mDefaultToken = null;
	private static String mAccessToken = null;
	
	public DuoDuoPrefences(Context context) {
		mContext = context.getApplicationContext();

		SharedPreferences preferences = mContext.getSharedPreferences(settingFile, Context.MODE_PRIVATE);
		mAccessToken = preferences.getString(mAccessTokenString, mDefaultToken);
	}

	public static String getAccesstoken(Context context) {
		if (context != null && TextUtils.isEmpty(mAccessToken)) {
			SharedPreferences preferences = context.getSharedPreferences(settingFile, Context.MODE_PRIVATE);

			mAccessToken = preferences.getString(mAccessTokenString, mDefaultToken);
		}
		return mAccessToken;
	}

	public static void setAccesstoken(Context context, String accesstoken) {
		SharedPreferences preferences = context.getSharedPreferences(settingFile, Context.MODE_PRIVATE);
		mAccessToken = accesstoken;
		preferences.edit().putString(mAccessTokenString, mAccessToken).commit();
	}
	
	public static boolean isLogin(Context context) {
		return false;
	}
}
