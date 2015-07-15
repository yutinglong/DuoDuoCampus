package com.duoduo.duoduocampus.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.widget.Toast;

import com.duoduo.duoduocampus.BaseApplication;

/**
 * @description: Toast管理类
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午2:23:02
 * @changeRecord
 */
public class DToast {
    public static void toast(String text, int duration) {
        if(isRunning()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, duration).show();
        }
    }
    
    public static void toastIgnoreRunning(String text) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    
    public static void toastIgnoreRunning(int resId) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }
    
    public static void toastShort(String text) {
        if(isRunning()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void toastLong(String text) {
        if(isRunning()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }
    }
    
    public static void toastShort(int nResId){
        if(isRunning()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), nResId, Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void toastLong(int nResId){
        if(isRunning()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), nResId, Toast.LENGTH_LONG).show();
        }
    }
    
    public static void toastPosition (int gravity, int xOffset, int yOffset, String text) {
        Toast toastNew = Toast.makeText(BaseApplication.getInstance().getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toastNew.setGravity(gravity, xOffset, yOffset);
        if(isRunning()) {
            toastNew.show();
        }
    }
    
    /**
     * 
     * @return
     */
    private static boolean isRunning(){
        ActivityManager activityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if(tasksInfo.size() > 0){
            if(BaseApplication.getInstance().getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())){
                return true;
            }
        }
        return false;
    }
}
