package com.yiyeshu.xxyaya.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.adapter.MainFraViewAdapter;
import com.yiyeshu.xxyaya.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lhw on 2017/5/18.
 */
public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;    //选项卡标题
    @BindView(R.id.viewpager)
    ViewPager mViewpager;     //vierpager页面

    private List<String> titles=new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_main_fragment;
    }

    @Override
    protected void setUpView() {
        titles.add("书籍");
        titles.add("电影");
    }

    @Override
    protected void setUpData() {
        MainFraViewAdapter mainFraViewAdapter=new MainFraViewAdapter(getChildFragmentManager(),getContext(),titles);
       mViewpager.setAdapter(mainFraViewAdapter);
        mSlidingTabs.setTabMode(TabLayout.MODE_FIXED);
        mSlidingTabs.setupWithViewPager(mViewpager);
    }


}
