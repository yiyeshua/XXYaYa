package com.yiyeshu.xxyaya.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhw on 2017/5/17.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private View mContentView;
    private Context mContext;
    private Unbinder unbinder;


    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void setUpView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            mContentView= inflater.inflate(getContentViewLayoutID(), container, false);
        } else {
            mContentView= super.onCreateView(inflater, container, savedInstanceState);
        }
        mContext = getContext();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        Log.e(TAG, "onViewCreated: " );
        setUpView();
        setUpData();
    }


    protected abstract void setUpData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
