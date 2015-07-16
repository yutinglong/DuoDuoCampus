package com.duoduo.duoduocampus.utils;

import android.os.Environment;

/**
 * @description: 
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午4:16:02
 * @changeRecord
 */
public class Constants {
    public static final String UTF8 = "utf-8";
    
	public final static String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath() + "/duoduo/";
	public final static String PHOTO_DIR = Environment.getExternalStorageDirectory().getPath() + "/duoduo_photo/";
	public final static String SOUND_DIR = SDCARD_DIR + "sound/";
    
	// 默认的渠道号
	public static final String DEFAULT_CHANNEL = "10000";
    /**
     * Log的存储位置
     */
    public static final String LOG_PATH = MobileUtil.getSdCardPath() + "Android/DuoDuo/ErrorLog/";
    
    /**
     * 网络访问打印URL的TAG
     */
    public static final String URL_TAG = "DuoDuo_APIAgent_Url";
    
    /**
     * Post请求
     */
    public static final String REQUEST_METHOD_POST = "POST";
    
    /**
     * Get请求
     */
    public static final String REQUEST_METHOD_GET = "GET";
    
    public static final int READ_BUFFER = 1024;
}
