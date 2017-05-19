/**
 * created by lhw, 16/04/09
 */


package com.yiyeshu.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.List;

public class AppUtils {

    private static int sMyProcessId = 0;

    /**
     *  客户端渠道名称
     */
    public static String sChannelName;


    private static Context mContext;
    private static Thread mUiThread;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void init(Context context) { //在Application中初始化
        mContext = context;
        mUiThread = Thread.currentThread();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static AssetManager getAssets() {
        return mContext.getAssets();
    }

    public static Resources getResource() {
        return mContext.getResources();
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == mUiThread;
    }

    public static void runOnUI(Runnable r) {
        sHandler.post(r);
    }

    public static void runOnUIDelayed(Runnable r, long delayMills) {
        sHandler.postDelayed(r, delayMills);
    }

    public static void removeRunnable(Runnable r) {
        if (r == null) {
            sHandler.removeCallbacksAndMessages(null);
        } else {
            sHandler.removeCallbacks(r);
        }
    }

    public static String getPackageName() {
        if (mContext != null) {
            return mContext.getPackageName();
        } else {
            return "com.yiyeshu.plandroid";
        }
    }

    /**
     * 获取客户端版本号
     */
    private static String sVersionName = "";
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(sVersionName)) {
            try {
                sVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sVersionName;
    }

    /**
     * 获取客户端版本号
     */
    private static int sVersionCode;
    public static int getVersionCode() {
        // 只获取一次
        if (sVersionCode == 0) {
            try {
                PackageManager pm = mContext.getPackageManager();
                String packageName = mContext.getPackageName();
                PackageInfo pinfo = pm.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
                sVersionCode = pinfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                sVersionCode = 0; // 异常情况
            }
        }
        return sVersionCode;
    }


    /**
     * 获取当前创建的进程名称，区分主进程和消息推送进程
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> listInfo = am.getRunningAppProcesses();
        if (listInfo == null || listInfo.isEmpty()) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : listInfo) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断进程是否存在
     * @return
     */
    public static boolean isExistProcessName(Context context, String processName) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.processName.equals(processName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 根据进程名获取进程id
     * @return
     */
    public static int getProcessId(Context con, String processName) {
        if (sMyProcessId == 0) {
            ActivityManager am = (ActivityManager) con.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> apps = am.getRunningAppProcesses();// 返回进程列表信息
            for (ActivityManager.RunningAppProcessInfo p : apps) {
                if (p.processName.equals(processName)) {
                    sMyProcessId = p.pid;
                    break;
                }
            }
        }
        return sMyProcessId;
    }
    /**
     * 记录程序启动时间，在application创建的时候统一设置
     */
    private static long sAppStartTime = 0;
    public static void setAppStartTime() {
        sAppStartTime = System.currentTimeMillis();
    }

    /**
     * 获取程序当前运行时间点
     */
    public static long getAppRunTime() {
        return System.currentTimeMillis() - sAppStartTime;
    }

    public static boolean isWifiOpen() {
        ConnectivityManager cm = (ConnectivityManager) AppUtils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        if (!info.isAvailable() || !info.isConnected()) return false;
        if (info.getType() != ConnectivityManager.TYPE_WIFI) return false;
        return true;
    }
}
