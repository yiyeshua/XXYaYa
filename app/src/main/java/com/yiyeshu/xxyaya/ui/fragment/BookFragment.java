package com.yiyeshu.xxyaya.ui.fragment;

import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.adapter.BookAdapter;
import com.yiyeshu.xxyaya.base.BaseFragment;
import com.yiyeshu.xxyaya.bean.BookBean;
import com.yiyeshu.xxyaya.constant.Apis;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by lhw on 2017/5/18.
 */
public class BookFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "BookFragment";
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @BindView(R.id.fab_search)
    FloatingActionButton mFabSearch;


    private static final int ACTION_REFRESH = 1;    //刷新
    private static final int ACTION_LOAD_MORE = 2;  //加载更多
    private int mCurrentAction = ACTION_REFRESH;   //刷新状态
    private int mPageSize = 20;                      //单页加载个数
    private int mCurrentPageIndex = 1;             //当前页

    private BookAdapter mBookAdapter;

    private String mCurrentKeyWord;   //搜索关键字
    private EditText mETInput;
    private AlertDialog mInputDialog;
    private LinearLayoutManager linearLayoutManager;
    private BookAdapter bookAdapter;
    private Object dataFromNet;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_book_fragment;
    }

    @Override
    protected void setUpView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(linearLayoutManager);
        bookAdapter = new BookAdapter(getContext(), new ArrayList<BookBean>());
        mRecyclerview.setAdapter(bookAdapter);
        //设置下拉刷新，上拉加载更多的监听
        mRecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mFabSearch.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        //默认选择一种图书加载列表
        String[] keyWords = {"Android", "文艺青年", "科技", ".NET", "创业之路"};
        Random random = new Random();
        int n = random.nextInt(keyWords.length);
        mCurrentKeyWord = keyWords[n];
        //首次进入执行刷新操作----》switchAction(ACTION_REFRESH)
        mRecyclerview.setRefreshing(true);
    }

    /**
     * 根据刷新状态同意处理数据请求
     *
     * @param actionRefresh
     */
    private void switchAction(int actionRefresh) {
        switch (actionRefresh) {
            //下拉刷新，每次都将数据清空，重新加载第一页
            case ACTION_REFRESH:
                bookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            //上拉加载更多时，页数加1
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getDataFromNet();

    }

    /**
     * 加载数据
     */
    public void getDataFromNet() {
        String reqUrl = Apis.SearchBookApi + "?q=" + mCurrentKeyWord + "&start=" + (mCurrentPageIndex - 1) * mPageSize +
                "&count=" + mPageSize;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_search :

                break;
        }
    }


}
