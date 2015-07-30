package com.duoduo.duoduocampus;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 应用shareprefences的存储
 * 
 * @author yutinglong
 * 
 */
public class DuoDuoPrefences {
	private Context mContext = null;
	
	private final static String settingFile = "meilishuo_settings";
	private final static String mNickNameString = "nickname";
			
	// private static String mDefaultToken = "8e574200349ddce86bd0c9d689338b42";
	
	public DuoDuoPrefences(Context context) {
		mContext = context.getApplicationContext();
	}
	
	public static String getNickName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(settingFile, Context.MODE_PRIVATE);

		String mNickName = preferences.getString(mNickNameString, null);
		return mNickName;
	}

	public static void setNickName(Context context, String nickname) {
		SharedPreferences preferences = context.getSharedPreferences(settingFile, Context.MODE_PRIVATE);
		preferences.edit().putString(mNickNameString, nickname).commit();
	}
	
	public static boolean isLogin(Context context) {
		if (getNickName(context) == null) {
			return false;
		}
		return true;
	}
}
