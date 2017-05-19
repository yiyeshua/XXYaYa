package com.yiyeshu.xxyaya.adapter;

import android.content.Context;
import android.content.Intent;

import com.yiyeshu.common.adapter.SolidRVBaseAdapter;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.bean.Book;
import com.yiyeshu.xxyaya.ui.activity.BookDetailActivity;

import java.util.List;

/**
 * Created by lhw on 2017/5/18.
 */
public class BookAdapter extends SolidRVBaseAdapter<Book.BookBean> {
    public BookAdapter(Context context, List<Book.BookBean> beans) {
        super(context,beans);
    }
    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtra("url", mBeans.get(position - 1).getUrl());
        mContext.startActivity(intent);
    }
    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, Book.BookBean bean, int position) {
        holder.setText(R.id.tv_title, bean.getTitle());
        holder.setText(R.id.tv_price, "￥" + bean.getPrice());
        holder.setText(R.id.tv_author, "作者:" + bean.getAuthor() + "");
        holder.setText(R.id.tv_date, "出版日期:" + bean.getPubdate());
        holder.setText(R.id.tv_publisher, "出版社:" + bean.getPublisher());
        holder.setText(R.id.tv_num_rating, bean.getRating().getNumRaters() + "人评分");
        holder.setImageFromInternet(R.id.iv_image, bean.getImage());
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.item_layout_book;
    }

}
