package com.yiyeshu.common.utils.manager;

/**
 * 下载进度
 * Created by Administrator on 2016/3/7.
 */
public interface ProgressListener {

    void update(long bytesRead, long contentLength, boolean done);

}
