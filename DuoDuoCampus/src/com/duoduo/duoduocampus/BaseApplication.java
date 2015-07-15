
package com.duoduo.duoduocampus;

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
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.duoduo.duoduocampus.system.status.CrashHandler;
import com.duoduo.duoduocampus.system.status.ExternalStorageReceiver;
import com.duoduo.duoduocampus.system.status.NetStatusReceiver;
import com.duoduo.duoduocampus.utils.Constants;
import com.duoduo.duoduocampus.utils.LogUtil;

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
        
        LogUtil.d(LogUtil.YTL_TAG, "Log存储的位置为: " + Constants.LOG_PATH);
    }

    public static BaseApplication getInstance() {
        return instance;
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

    /**
     * 使得某个Runnable在UI线程中执行
     * 
     * @param r
     */
    public void runOnUIThread(Runnable r) {
        handler.post(r);
    }
}
