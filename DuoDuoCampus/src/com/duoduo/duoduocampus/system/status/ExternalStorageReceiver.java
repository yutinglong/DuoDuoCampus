
package com.duoduo.duoduocampus.system.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.duoduo.duoduocampus.utils.LogUtil;

/**
 * @description: SD状态的监听广播，用于整个应用SD状态的监听， 通过isSDCardMounted属性即可知道当前SD卡的状态
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午6:29:02
 * @changeRecord
 */
public class ExternalStorageReceiver extends BroadcastReceiver {
    /**
     * 项目中其他位置如需知道SD卡的状态，只需判断该属性即可
     */
    public static boolean isSDCardMounted = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
            isSDCardMounted = true;
        } else if (intent.getAction().equals("android.intent.action.MEDIA_REMOVED")
                || intent.getAction().equals("android.intent.action.ACTION_MEDIA_UNMOUNTED")
                || intent.getAction().equals("android.intent.action.ACTION_MEDIA_BAD_REMOVAL")) {
            isSDCardMounted = false;
        } else {
            ExternalStorageReceiver.isSDCardMounted = Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState());
        }

        LogUtil.v(LogUtil.getTraceInfo() + "isSDCardMounted = " + isSDCardMounted);
    }
}
