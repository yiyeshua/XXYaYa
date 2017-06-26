package com.yiyeshu.common.http.provider;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.yiyeshu.common.http.provider.base.IImageLoaderProvider;
import com.yiyeshu.common.http.request.ImageRequest;
import com.yiyeshu.xxyaya.App;


/**
 * Created by _SOLID
 * Date:2016/5/13
 * Time:10:27
 */
public class GlideImageLoaderProvider implements IImageLoaderProvider {
    @Override
    public void loadImage(ImageRequest request) {
        Glide.with(App.getInstance()).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }

    @Override
    public void loadImage(Context context, ImageRequest request) {
        Glide.with(context).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }
}
