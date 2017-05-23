package com.yiyeshu.xxyaya;

import android.app.Application;

import com.hss01248.glidepicker.GlideIniter;
import com.hss01248.photoouter.PhotoUtil;
import com.squareup.leakcanary.LeakCanary;
import com.weavey.loading.lib.LoadingLayout;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by lhw on 2017/5/17.
 */
public class App extends Application {
    private static App INSTANCE;
    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化 LeakCanary，内存泄漏检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //滑动返回
        BGASwipeBackManager.getInstance().init(this);
        //选图拍照裁剪
        PhotoUtil.init(getApplicationContext(),new GlideIniter());//第二个参数根据具体依赖库而定
      //页面状态管理
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.define_error)
                .setEmptyImage(R.mipmap.define_empty)
                .setNoNetworkImage(R.mipmap.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40)
                .setAllPageBackgroundColor(R.color.background);
    }
}
