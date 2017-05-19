package com.yiyeshu.xxyaya.ui.fragment;

import android.widget.TextView;

import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by lhw on 2017/5/19.
 */
public class StringFragment extends BaseFragment {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_book_detail_fragment;
    }

    @Override
    protected void setUpView() {
        String text = getArguments().getString("text");
        tv.setText(text);
    }


    @Override
    protected void setUpData() {
    }


}
