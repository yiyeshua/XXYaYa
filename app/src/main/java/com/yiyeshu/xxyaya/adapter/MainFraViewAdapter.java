package com.yiyeshu.xxyaya.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yiyeshu.xxyaya.base.BaseFragment;
import com.yiyeshu.xxyaya.ui.fragment.BookFragment;
import com.yiyeshu.xxyaya.ui.fragment.MovieFragment;
import com.yiyeshu.xxyaya.utils.ViewUtil;

import java.util.List;

/**
 * Created by lhw on 2017/5/18.
 */
public class MainFraViewAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;
    private Context mContext;
    public MainFraViewAdapter(FragmentManager fm,Context context,List titles) {
        super(fm);
        this.mTitles=titles;
        this.mContext=context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment=ViewUtil.createFragment(BookFragment.class,false);
        switch (mTitles.get(position)) {
            case "书籍" :
                fragment = ViewUtil.createFragment(BookFragment.class, false);
                break;
            case "电影":
                fragment =  ViewUtil.createFragment(MovieFragment.class,false);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
}
