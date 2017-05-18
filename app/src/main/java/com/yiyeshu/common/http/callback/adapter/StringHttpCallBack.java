package com.yiyeshu.common.http.callback.adapter;


import com.yiyeshu.common.http.callback.HttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/13
 * Time:11:43
 */
public abstract class StringHttpCallBack extends HttpCallBack<String> {
    @Override
    public String parseData(String result) {
        return result;
    }
}
