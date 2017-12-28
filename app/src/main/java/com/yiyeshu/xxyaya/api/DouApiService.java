package com.yiyeshu.xxyaya.api;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yiyeshu.common.http.interceptor.RequestInterceptor;
import com.yiyeshu.common.http.interceptor.ResponseInterceptor;
import com.yiyeshu.xxyaya.api.api.DoubanApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络操作类
 */

public class DouApiService {
    private static DoubanApi doubanApi;

    //单例
    private static class SingletonHolder {
        private static final DouApiService INSTANCE = new DouApiService();
    }

    //单例
    public static DouApiService getDouApiService() {
        return SingletonHolder.INSTANCE;
    }
    public static DoubanApi getDoubanApi() {
        //OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(new ResponseInterceptor());

        OkHttpClient mOkHttpClient = builder.build();
        if (doubanApi == null) {
            //Retrofit
            Retrofit mRetrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://api.douban.com/v2/")//替换为你自己的BaseUrl
                    .build();
            doubanApi = mRetrofit.create(DoubanApi.class);
        }
        return doubanApi;
    }

}

