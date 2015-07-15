
package com.duoduo.duoduocampus.system.status;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.duoduo.duoduocampus.utils.Constants;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.duoduo.duoduocampus.utils.FileUtil;
import com.duoduo.duoduocampus.utils.MobileUtil;

/**
 * @description: 全局处理异常类
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午6:26:02
 * @changeRecord
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler myCrashHandler;

    // 私有化构造方法
    private CrashHandler() {
    }

    public static synchronized CrashHandler getInstance() {
        if (myCrashHandler != null) {
            return myCrashHandler;
        } else {
            myCrashHandler = new CrashHandler();
            return myCrashHandler;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String mobileInfo = MobileUtil.getMobileInfo();
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        String errorinfo = writer.toString();

        SimpleDateFormat dataFormatFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        final String s = new String(
                "------------------------------------------------------------------------\n"
                        + mobileInfo + "\n\n"
                        + errorinfo + "\n"
                );

        LogUtil.e(s);

        // 存储log日志到SD卡中命名规则为："errorlog+日期.txt"
        if (MobileUtil.isSDCardExists()) {
            File file = new File(Constants.LOG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtil.writeString(
                    Constants.LOG_PATH + "errorlog" + dataFormatFileName.format(new Date())
                            + ".txt", s);
        }

        // 干掉当前的程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
