
package com.duoduo.duoduocampus;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.duoduo.duoduocampus.system.status.CrashHandler;
import com.duoduo.duoduocampus.system.status.ExternalStorageReceiver;
import com.duoduo.duoduocampus.system.status.NetStatusReceiver;
import com.duoduo.duoduocampus.utils.ChannelUtil;
import com.duoduo.duoduocampus.utils.Constants;
import com.duoduo.duoduocampus.utils.LocalApiUtil;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.duoduo.duoduocampus.utils.MobileUtil;

/**
 * @description: 项目的Application
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午5:20:37
 * @changeRecord
 */
public class BaseApplication extends Application {
    private static BaseApplication instance = null;
    private Handler handler = new Handler();
    
	public static String imei = "";
	public static String mac = "";
	private static String version_name = "0.0.0";
	
	private static String channel_id;// 渠道ID
	
    public BaseApplication() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 日志输入与写入的开关
        LogUtil.initLog(getApplicationContext());

        // 初始化并注册网络状态广播与SD卡状态广播
        initAndRegisterNetAndSDCard();

        // 全局的异常处理，输出Log到SD中
        if (LogUtil.ISSAVECRASH) {
        	CrashHandler mCrashHandler = CrashHandler.getInstance();
        	Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
        }
        
        imei = MobileUtil.getDeviceId();
		mac = MobileUtil.getMacAddress();
		
		initLocalAPI();
        
        LogUtil.d(LogUtil.YTL_TAG, "Log存储的位置为: " + Constants.LOG_PATH);
    }

    public static BaseApplication getInstance() {
        return instance;
    }
    
    private void initLocalAPI() {
        if (LogUtil.isDebug() && "com.duoduo.duoduocampus".equals(getCurProcessName(getApplicationContext()))) {
            LocalApiUtil.initLocalApi();
        }
    }

    /**
     * 初始化并注册网络状态广播与SD卡状态广播
     */
    private void initAndRegisterNetAndSDCard() {
        // 首次初始化当前网络的状态
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo allNetInfo = cm.getActiveNetworkInfo();

        if (allNetInfo == null) {
            if (mobileNetInfo != null
                    && (mobileNetInfo.isConnected() || mobileNetInfo.isConnectedOrConnecting())) {
                NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_MOBILE;
            } else if (wifiNetInfo != null
                    && (wifiNetInfo.isConnected() || wifiNetInfo.isConnectedOrConnecting())) {
                NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_WIFI;
            } else {
                NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_INAVAILABLE;
            }
        } else {
            if (allNetInfo.isConnected() || allNetInfo.isConnectedOrConnecting()) {
                if (mobileNetInfo != null
                        && (mobileNetInfo.isConnected() || mobileNetInfo.isConnectedOrConnecting())) {
                    NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_MOBILE;
                } else {
                    NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_WIFI;
                }
            } else {
                NetStatusReceiver.netStatus = NetStatusReceiver.NETSTATUS_INAVAILABLE;
            }
        }

        // 首次初始化当前SD卡的状态
        ExternalStorageReceiver.isSDCardMounted = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());

        // 为整个应用注册网络状态监听
        IntentFilter filterNet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetStatusReceiver netReceiver = new NetStatusReceiver();
        this.registerReceiver(netReceiver, filterNet);

        // 为整个应用注册SD状态监听
        ExternalStorageReceiver storageReceiver = new ExternalStorageReceiver();
        IntentFilter filterStorage = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
        filterStorage.addDataScheme("file");
        filterStorage.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filterStorage.addAction(Intent.ACTION_MEDIA_REMOVED);
        filterStorage.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
        this.registerReceiver(storageReceiver, filterStorage);
    }

    public static String getChannelID() {
		if (TextUtils.isEmpty(channel_id)) {
			channel_id = ChannelUtil.getChannel(instance, Constants.DEFAULT_CHANNEL);
		}
		return channel_id;
	}
    
	public static String getVersionName() {
		if (TextUtils.isEmpty(version_name))
			version_name = ChannelUtil.getVersionName(instance);
		return version_name;
	}
    
	/**
	 * 自定义UserAgent（网络请求 特定web页面【https的】需要带上）
	 * @return
	 */
	public static String getUserAgent() {
		String userAgent = "duoduo android "
				+ BaseApplication.getChannelID() + " "
				+ BaseApplication.getVersionName();
		return userAgent;
	}
    
    /**
     * 隐藏虚拟键盘
     * 
     * @param token 通过View.getWindowToken()获取的
     */
    public void hideSoftInputFromWindow(IBinder token) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(token, 0);
        }
    }

    /**
     * 显示虚拟键盘
     * 
     * @param view 某个接受软键盘输入的视图
     */
    public void showSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    public void runOnUIThread(Runnable r) {
        handler.post(r);
    }
    
    public void runOnUIThread(Runnable r, long delayMillis) {
    	handler.postDelayed(r, delayMillis);
    }
    
    /**
     * 获取当前进程名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
