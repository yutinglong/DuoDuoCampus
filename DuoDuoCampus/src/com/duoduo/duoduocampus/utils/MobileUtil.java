package com.duoduo.duoduocampus.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;

import com.duoduo.duoduocampus.BaseApplication;

/**
 * @description: 手机相关的工具方法 
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午10:26:23
 * @changeRecord
 */
public class MobileUtil {
    private static String sdCardPath = null;
    private static int dataSize = 20 * 1024 * 1024;
     
    private static int screenWidthIntPx = -1;
    private static int screenHeightIntPx = -1;
    
    /**
     * 判断SD卡是否存在
     * @return 
     */
    public static boolean isSDCardExists() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
    
    /**
     * 获取SD卡路径，以'/'结束
     * @return
     * @author yutinglong
     * @since 2013-01-24
     */
    public static String getSdCardPath() {
        if ((sdCardPath == null) || (sdCardPath.equals(""))) {
            sdCardPath = Environment.getExternalStorageDirectory().getPath();
            if (sdCardPath.substring(sdCardPath.length() - 1).equals(File.separator) == false) {
                sdCardPath += File.separator;
            }
        }
        return sdCardPath;
    }
    
    /**
     * 获取出错时的环境
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        sb.append("MANUFACTURER:" + MobileUtil.getManufacturer());
        sb.append("\n");
        sb.append("MODEL:" + MobileUtil.getProductType());
        sb.append("\n");
        sb.append("AndroidSDK:" + MobileUtil.getSystemSdk());
        sb.append("\n");
        sb.append("ClientVer:" + MobileUtil.getVersionCode());
        sb.append("\n");
        sb.append("Time:" + dataFormat.format(new Date()));
        sb.append("\n");
        
        return sb.toString();
    }
    
    /**
     * 获得生产厂商
     * @return
     * @author yutinglong
     * @since 2013-01-24
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }
    
    /**
     * 获取手机型号
     * @return
     * @author yutinglong
     * @since 2013-01-24
     */
    public static String getProductType() {
        return android.os.Build.MODEL;
    }
    
    /**
     * 获取手机android操作系统版本
     * @return
     * @author yutinglong
     * @since 2013-01-24
     */
    public static int getSystemSdk() {
        return android.os.Build.VERSION.SDK_INT;
    }
    
    /**
     * 获取当前应用的版本号
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        
        String packageName = BaseApplication.getInstance().getPackageName();
        PackageInfo pm = null;
        try {
            pm = BaseApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            versionCode = pm.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    
    /**
     * 获取本应用的包名
     * @return
     */
    public static String getPackageName() {
        String packageName = BaseApplication.getInstance().getPackageName();
        return packageName;
    }
    
    /**
     * 判断SD卡是否已满
     * @return
     */
    public static boolean isSDCardFull() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = blocks * blockSize;
            if (dataSize > availableSpare) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
    
    /**
     * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. 
     * Return null if device ID is not available.
     * @author yutinglong
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String id = tm.getDeviceId();
        return id;
    }
    
    /**
     * 获取mac地址
     * @return
     */
    public static String getMacAddress() {
    	String mac = "";
    	WifiManager wifi = (WifiManager) BaseApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
    	WifiInfo info = wifi.getConnectionInfo();
    	mac = info.getMacAddress();
    	return mac;
    }
    
    /**
     * 获取运营商名称
     * @return
     */
    public static String getProvidersName() {
    	String providersName = "未知";
		TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI = telephonyManager.getSubscriberId();
		if (!StringUtil.isNullOrEmpty(IMSI)) {
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				providersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				providersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				providersName = "中国电信";
			}
		}
		
		return providersName;
	}
    
    public static int getScreenWidthIntPx() {
    	if (screenWidthIntPx == -1) {
    		setScreenSize();
    	}
        return screenWidthIntPx;
    }
    
    public static int getScreenHeightIntPx() {
    	if (screenHeightIntPx == -1) {
    		setScreenSize();
    	}
    	
        return screenHeightIntPx;
    }
    
    private static void setScreenSize() {
        android.view.WindowManager windowsManager = (android.view.WindowManager) BaseApplication.getInstance().getSystemService(
                Context.WINDOW_SERVICE);
        android.view.Display display = windowsManager.getDefaultDisplay();
        screenWidthIntPx = display.getWidth();	// outMetrics.widthPixels;
        screenHeightIntPx = display.getHeight();// outMetrics.heightPixels;
    }
}
