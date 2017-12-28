package com.yiyeshu.common.utils.manager;

import android.os.Handler;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 重写响应体实现下载进度
 * Created by Administrator on 2016/3/7.
 */
public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;
    private Handler handler;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener, Handler handler) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        this.handler = handler;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source()  {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) {
                long bytesRead = 0;
                try {
                    bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    final long finalBytesRead = bytesRead;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                                progressListener.update(totalBytesRead, responseBody.contentLength(), finalBytesRead == -1);

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bytesRead;
            }
        };
    }
}
