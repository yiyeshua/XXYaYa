package com.yiyeshu.xxyaya.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yiyeshu.common.utils.GsonUtil;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.adapter.BookDetailPagerAdapter;
import com.yiyeshu.xxyaya.base.BaseActivity;
import com.yiyeshu.xxyaya.bean.Book;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;
import okhttp3.Call;

public class BookDetailActivity extends BaseActivity {
    private static final String TAG = "BookDetailActivity";
    @BindView(R.id.vp)
    ViewPager mViewpager;
    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout mCoordinatortablayout;

    private Book.BookBean mBookBeen;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;
    private int[] mColorArray;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void beforeInit() {

    }

    @Override
    protected void setUpView(Bundle savedInstanceState) {
        mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light};

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        OkHttpUtils.get().url(url).build().connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e(TAG, "onError: 加载书籍信息错误");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        mBookBeen = GsonUtil.gsonToBean(response, Book.BookBean.class);
                        Log.e(TAG, "onResponse: " + mBookBeen.getSubtitle());
                        mTitles=new ArrayList<>();
                        mTitles.add("作者信息");
                        mTitles.add("章节");
                        mTitles.add("书籍简介");
                        mViewpager.setOffscreenPageLimit(3);
                        mViewpager.setAdapter(new BookDetailPagerAdapter(getSupportFragmentManager(), mBookBeen,mTitles));
                        mCoordinatortablayout.setBackEnable(true)
                                .setTitle(mBookBeen.getSubtitle())
                                .setContentScrimColorArray(mColorArray)
                                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                                    @Override
                                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                                        Glide.with(BookDetailActivity.this).load(mBookBeen.getImage().toString()).into(imageView);
                                    }
                                }).setupWithViewPager(mViewpager);
                    }
                });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
