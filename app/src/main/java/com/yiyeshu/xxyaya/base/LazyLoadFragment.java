package com.yiyeshu.xxyaya.base;

import android.util.Log;

/**
 * Created by lhw on 2017/5/23.
 */
public abstract class LazyLoadFragment extends BaseFragment {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    @Override
    protected abstract int getContentViewLayoutID();

    @Override
    protected void setUpView() {
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
    }
    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        Log.e(TAG, "isCanLoadData: " + getUserVisibleHint());
        if (getUserVisibleHint()) {
           setUpData();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }
    @Override
    protected void setUpData() {

    }

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }
    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;

    }

}
