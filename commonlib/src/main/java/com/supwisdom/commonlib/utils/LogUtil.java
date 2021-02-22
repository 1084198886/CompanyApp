package com.supwisdom.commonlib.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/24.
 * @desc 日志
 */

public class LogUtil {
    public static final int DEBUG = 4;
    public static final int INFO = 3;
    public static final int WARN = 2;
    public static final int ERROR = 1;
    public static int level = DEBUG;

    public static void d(String tag, String msg) {
        if (level >= DEBUG) {
            Log.d(tag, msg);
//            FileUtil.writeLogFile("Debug-->" + tag + "\n" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level >= INFO) {
            Log.i(tag, msg);
//            FileUtil.writeLogFile("Info-->" + tag + "\n" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level >= ERROR) {
            Log.e(tag, msg);
//            FileUtil.writeLogFile("Error-->" + tag + "\n" + msg);
        }
    }

    public static void d(String tag, Exception ex) {
        if (level >= DEBUG) {
            Log.d(tag, ex.getMessage());
//            FileUtil.writeLogFile("Debug-->" + tag + "\n" + getExceptionStack(ex));
        }
    }

    public static void i(String tag, Exception ex) {
        if (level >= INFO) {
            Log.i(tag, ex.getMessage());
//            FileUtil.writeLogFile("Info-->" + tag + "\n" + getExceptionStack(ex));
        }
    }

    public static void e(String tag, Exception ex) {
        if (level >= ERROR) {
            Log.e(tag, ex.getMessage());
//            FileUtil.writeLogFile("Error-->" + tag + "\n" + getExceptionStack(ex));
        }
    }

    private static String getExceptionStack(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
