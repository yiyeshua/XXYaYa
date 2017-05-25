package com.yiyeshu.xxyaya.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yiyeshu.common.utils.GsonUtil;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.adapter.MovieAdapter;
import com.yiyeshu.xxyaya.base.BaseFragment;
import com.yiyeshu.xxyaya.bean.Movie;
import com.yiyeshu.xxyaya.constant.Apis;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by lhw on 2017/5/18.
 */
public class MovieFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MovieFragment";
    @BindView(R.id.mrecyclerview)
    XRecyclerView mRecyclerview;
    @BindView(R.id.moive_search)
    FloatingActionButton moiveSearch;

    private static final int ACTION_REFRESH = 1;    //刷新
    private static final int ACTION_LOAD_MORE = 2;  //加载更多
    private int mCurrentAction = ACTION_REFRESH;   //刷新状态
    private int mPageSize = 20;                      //单页加载个数
    private int mCurrentPageIndex = 1;             //当前页

    private String mCurrentKeyWord;   //搜索关键字
    private EditText mETInput;
    private AlertDialog mInputDialog;
    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter mMovieAdapter;
    private String reqUrl;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_movie_fragment;
    }

    @Override
    protected void setUpView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mMovieAdapter = new MovieAdapter(getContext(), new ArrayList<Movie.MovieBean>());
        mRecyclerview.setAdapter(mMovieAdapter);
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
        moiveSearch.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        //默认选择一种图书加载列表
        String[] keyWords = {"喜剧","纪录片","动作","武侠","科幻","战争","悬疑","犯罪","爱情","剧情","奇幻","古装"};
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
                mMovieAdapter.clear();
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
        reqUrl = Apis.MovieSearch + "?tag=" + mCurrentKeyWord + "&start=" + (mCurrentPageIndex - 1) * mPageSize +
                "&count=" + mPageSize;
        Log.d(TAG, "getDataFromNet: " + reqUrl);
        OkHttpUtils.get().url(reqUrl).build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        loadComplete();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        List<Movie.MovieBean> subjects = GsonUtil.gsonToBean(response, Movie.class).getSubjects();
                        Log.e("TAG", subjects.size()+"");
                        mMovieAdapter.addAll(subjects);
                        loadComplete();
                    }
                });
    }


    /**
     * 加载数据完成
     */
    private void loadComplete() {
        if(mRecyclerview==null){
            return;
        }
        switch (mCurrentAction) {
            case ACTION_REFRESH :
                mRecyclerview.refreshComplete();
                break;
            case ACTION_LOAD_MORE:
                mRecyclerview.loadMoreComplete();
                break;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.moive_search :
                mETInput=new EditText(getContext());
                mInputDialog=new AlertDialog.Builder(getContext())
                        .setView(mETInput)
                        .setTitle("请输入搜索关键字")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCurrentKeyWord = mETInput.getText().toString();
                                mRecyclerview.setRefreshing(true);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }


}
