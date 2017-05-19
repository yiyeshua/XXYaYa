package com.yiyeshu.xxyaya.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yiyeshu.common.utils.ViewUtil;
import com.yiyeshu.xxyaya.bean.Book;
import com.yiyeshu.xxyaya.ui.fragment.StringFragment;

import java.util.ArrayList;

/**
 * Created by lhw on 2017/5/19.
 */
public class BookDetailPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitles;
    private  Book.BookBean mBookBean;
    public BookDetailPagerAdapter(FragmentManager fm,Book.BookBean bookBean ,ArrayList<String> mTitles) {
        super(fm);
        this.mBookBean=bookBean;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtil.createFragment(StringFragment.class, false);
        Bundle bundle = new Bundle();
        switch (mTitles.get(position)) {
            case "作者信息" :
                bundle.putString("text", mBookBean.getAuthor_intro());
                break;
            case "章节":
                bundle.putString("text", mBookBean.getCatalog());
                break;
            case "书籍简介":
                bundle.putString("text", mBookBean.getSummary());
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

}
