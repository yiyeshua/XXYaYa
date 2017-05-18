package com.yiyeshu.xxyaya.adapter;

import android.content.Context;

import com.yiyeshu.common.adapter.SolidRVBaseAdapter;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.bean.BookBean;

import java.util.List;

/**
 * Created by lhw on 2017/5/18.
 */
public class BookAdapter extends SolidRVBaseAdapter {
    public BookAdapter(Context context, List<BookBean> beans) {
        super(context,beans);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, Object bean, int position) {

    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.item_layout_book;
    }

}
