
package com.duoduo.duoduocampus.system.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.duoduo.duoduocampus.utils.LogUtil;

/**
 * @description: 网络状态的监听，项目中其他位置直接通过netStatus属性即可获取当前网络的状态
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午5:26:10
 * @changeRecord
 */
public class NetStatusReceiver extends BroadcastReceiver {
    /**
     * 无网络连接
     */
    public static final int NETSTATUS_INAVAILABLE = 0;

    /**
     * 当前网络连接状态为WIFI
     */
    public static final int NETSTATUS_WIFI = 1;

    /**
     * 当前网络连接状态为移动网络
     */
    public static final int NETSTATUS_MOBILE = 2;

    /**
     * 当前网络的状态
     */
    public static int netStatus = 0;
    
    public static boolean isNetOk() {
    	if (netStatus == 0) {
    		return false;
    	}
    	
    	return true;
    }

    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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

        LogUtil.v(LogUtil.getTraceInfo() + " netStatus = " + netStatus);
    }
}
