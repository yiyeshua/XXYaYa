package com.yiyeshu.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

/**
 * 手机网络变化切换广播 用于首页无网络连接时提醒
 * Created by Administrator on 2016/4/11.
 */
public class NetworkChangedReceiver extends BroadcastReceiver {

    public static final int NetLost = 404;
    public static final int NetConnect = 100;

    Handler handler;

    public NetworkChangedReceiver() {
    }

    public NetworkChangedReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // 不是网络状态变化的不做处理
            if (!(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)))
                return;

            if (!NetStatusUtil.isNetworkConnected(context)) {// 当前无网络连接
                if (this.handler == null) {
                    //EventBus.getDefault().post(new NetWorkEvent(false));
                } else {
                    this.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain(null, NetLost);
                            handler.sendMessage(msg);
                        }
                    }, 1000);
                }
            } else {
                if (this.handler == null) {
                    //EventBus.getDefault().post(new NetWorkEvent(true));
                } else {
                    this.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain(null, NetConnect);
                            handler.sendMessage(msg);
                        }
                    }, 1000);
                }

            }
        } catch (Exception e) {

        }
    }

}