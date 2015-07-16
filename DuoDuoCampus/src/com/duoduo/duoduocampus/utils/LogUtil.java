package com.duoduo.duoduocampus.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: Log管理类，项目中必须全部使用该类做log的输出
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午5:13:23
 * @changeRecord
 */
public class LogUtil {
    /**
     * 全局crash捕获开关
     */
    public static boolean ISSAVECRASH = false;
	
	/**
	 * 打印调试信息总开关
	 */
	private static boolean ISDEBUG = false;
	
    /**
     * 写文件调试信息开关
     */
    private static boolean ISSAVE = false;
	
	/**
	 * 默认的TAG
	 */
	private static final String DEFAULT_TAG = "DuoDuo_LOG";

    /**
     * 个人专用Bug标识
     * @author yutinglong
     */
	public static final String YTL_TAG = "YTL";

	private LogUtil() {
	}
	
	public static boolean isDebug() {
		return ISDEBUG;
	}

	public static void d(String msg) {
		if (ISDEBUG) {
			android.util.Log.d(DEFAULT_TAG, msg);
		}
	}

	public static void e(String msg) {
		if (ISDEBUG) {
			android.util.Log.e(DEFAULT_TAG, msg);
		}
	}

	public static void e(String msg, Throwable tr) {
		if (ISDEBUG) {
			android.util.Log.e(DEFAULT_TAG, msg, tr);
		}
	}

	public static void i(Object msg) {
		if (ISDEBUG) {
			android.util.Log.i(DEFAULT_TAG, "" + msg);
		}
	}

	public static void v(String msg) {
		if (ISDEBUG) {
			if(msg != null) {
				android.util.Log.v(DEFAULT_TAG, msg);
			} else{
				android.util.Log.v(DEFAULT_TAG, getTraceInfo() + "null");
			}
		}
	}

	public static void w(String msg) {
		if (ISDEBUG) {
			android.util.Log.w(DEFAULT_TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (ISDEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (ISDEBUG) {
			android.util.Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (ISDEBUG) {
			android.util.Log.e(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (ISDEBUG) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (ISDEBUG) {
			android.util.Log.v(tag, msg);
			
			if (tag.equalsIgnoreCase(YTL_TAG)) {
				if (MobileUtil.isSDCardExists()) {
					FileOutputStream fos = null;
					try {
						File file = new File(Constants.LOG_PATH);
						if (!file.exists()) {
							file.mkdirs();
						}
						fos = new FileOutputStream(Constants.LOG_PATH + YTL_TAG + ".txt", true);
						msg = new SimpleDateFormat("HH:mm:ss").format(new Date()) + msg + "\r\n";
						byte[] m = msg.getBytes();
						fos.write(m);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							fos.close();
							fos = null;
						} catch (Exception e) {
						    e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static void w(String tag, String msg) {
		if (ISDEBUG) {
			android.util.Log.w(tag, msg);
		}
	}

	/**
	 * 将日志写入文件
	 * @param tag
	 * @param msg
	 * @author yutinglong
	 * @since 2015-07-14
	 */
	public static void f(String tag, String msg, String fileName) {
		if (ISSAVE) {
		    if(ISDEBUG){
		        android.util.Log.d(tag, "Log写入: " + msg);    
		    }
			
			if (MobileUtil.isSDCardExists()) {
				FileOutputStream fos = null;
				try {
					File file = new File(Constants.LOG_PATH);
					if (!file.exists()) {
						file.mkdirs();
					}
					fos = new FileOutputStream(fileName, true);
					msg = tag + "\r\n" + new SimpleDateFormat("yyyy_MM_dd HH:mm:ss ").format(new Date()) + msg + "\r\n";
					byte[] m = msg.getBytes();
					fos.write(m);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fos.close();
						fos = null;
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public static boolean isLoggable(String tag, int level) {
		return android.util.Log.isLoggable(tag, level);
	}

	/**
	 * 输出程序运行时信息,在调试现场调用
	 * 
	 * @return [file:当前运行文件;method:当前运行方法;LineNumber:当前运行代码行]
	 * @author yutinglong
	 * @since 2015-07-14
	 */
	public static String getTraceInfo() {
		if (!ISDEBUG) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("[file:")
		    .append(stacks[1].getFileName())
		    .append(",line:")
		    .append(stacks[1].getLineNumber())
		    .append(",method:")
		    .append(stacks[1].getMethodName()+ "] ");
		return sb.toString();
	}
	
	/**
	 * 输出程序运行时信息,在调试现场调用  -- 上面方法的简化版
	 * 
	 * @return [当前运行文件--当前运行方法]
	 * @author yutinglong
	 * @since 2015-07-14
	 */
	public static String getTraceInfoSimple() {
		if (!ISDEBUG) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("[" + stacks[1].getFileName())
		    .append("----")
		    .append(stacks[1].getMethodName() + "];");
		return sb.toString();
	}

    /**
     * 控制log的打开与关闭，以AndroidManifest.xml文件logger的属性为主，请在项目启动时调用
     * @author yutinglong
     * @since 2015-07-14 下午04:43:10
     */
    public static void initLog(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData.containsKey("logger")) {
                ISDEBUG = appInfo.metaData.getBoolean("logger");
            }
            if (appInfo.metaData.containsKey("saveLogger")) {
                ISSAVE = appInfo.metaData.getBoolean("saveLogger");
            }
            if (appInfo.metaData.containsKey("saveCrash")) {
            	ISSAVECRASH = appInfo.metaData.getBoolean("saveCrash");
            }
            
        } catch (Exception e) {
            Log.v(context.getClass().getName(), e.getMessage(), e);
        }
    }
}
