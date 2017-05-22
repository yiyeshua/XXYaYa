package com.yiyeshu.xxyaya.adapter;

import android.content.Context;
import android.content.Intent;

import com.yiyeshu.common.adapter.SolidRVBaseAdapter;
import com.yiyeshu.common.utils.StringUtil;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.bean.Movie;
import com.yiyeshu.xxyaya.ui.activity.BookDetailActivity;

import java.util.List;

/**
 * Created by lhw on 2017/5/18.
 */
public class MovieAdapter extends SolidRVBaseAdapter<Movie.MovieBean> {
    public MovieAdapter(Context context, List<Movie.MovieBean> beans) {
        super(context,beans);
    }
    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtra("url", mBeans.get(position - 1).getAlt());
        mContext.startActivity(intent);
    }
    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, Movie.MovieBean bean, int position) {
        holder.setText(R.id.movie_title, bean.getTitle());
        holder.setText(R.id.movie_price, "分" + bean.getRating().getAverage());
        holder.setText(R.id.movie_author, "导演:" + bean.getDirectors().get(0).getName() + "");
        holder.setText(R.id.movie_date, "上映日期:" + bean.getYear());
        holder.setText(R.id.movie_tags, "类型:" + StringUtil.convert2String(bean.getGenres()));
        holder.setText(R.id.movie_num_rating, bean.getRating().getStars() + "人评分");
        holder.setImageFromInternet(R.id.iv_image, bean.getImages().getMedium());
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.item_layout_movie;
    }

}
