package com.yiyeshu.common.utils.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;


/*import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;*/

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;


/**
 * OkHttpClientManager
 * Created by Administrator on 2016/3/7.
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;
    //https相关
    private HttpsDelegate mHttpsDelegate = new HttpsDelegate();
    //下载相关
    private DownloadDelegate mDownloadDelegate = new DownloadDelegate();
    //显示图片
    private DisplayImageDelegate mDisplayImageDelegate = new DisplayImageDelegate();
    //get请求
    private GetDelegate mGetDelegate = new GetDelegate();
    //上传文件
    private UploadDelegate mUploadDelegate = new UploadDelegate();
    //post
    private PostDelegate mPostDelegate = new PostDelegate();

    public static final String TYPE = "application/octet-stream";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .hostnameVerifier(new TrustAllHostnameVerifier())    //信任所有证书
                .sslSocketFactory(createSSLSocketFactory())          //信任所有证书
                .connectTimeout(30, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        //cookie enabled

        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();

     /*   *//*
        just for test !!!*//*
        mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });*/

    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 根据tag取消一个请求
     *
     * @param tag
     */
    public void cancelRequest(Object tag) {
        //checkInit();

        if (tag == null) {
            return;
        }

        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }

            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
       // mOkHttpClient.cancel(tag);
    }

    /**
     * get请求
     *
     * @return
     */
    public GetDelegate getGetDelegate() {
        return mGetDelegate;
    }

    /**
     * post请求
     *
     * @return
     */
    public PostDelegate getPostDelegate() {
        return mPostDelegate;
    }


    private HttpsDelegate _getHttpsDelegate() {
        return mHttpsDelegate;
    }


    private DownloadDelegate _getDownloadDelegate() {
        return mDownloadDelegate;
    }

    private DisplayImageDelegate _getDisplayImageDelegate() {
        return mDisplayImageDelegate;
    }

    private UploadDelegate _getUploadDelegate() {
        return mUploadDelegate;
    }


    /**
     * 显示图片
     *
     * @return
     */
    public static DisplayImageDelegate getDisplayImageDelegate() {
        return getInstance()._getDisplayImageDelegate();
    }

    /**
     * 下载
     *
     * @return
     */
    public static DownloadDelegate getDownloadDelegate() {
        return getInstance()._getDownloadDelegate();
    }

    /**
     * 上传
     *
     * @return
     */
    public static UploadDelegate getUploadDelegate() {
        return getInstance()._getUploadDelegate();
    }

    /**
     * https
     *
     * @return
     */
    public static HttpsDelegate getHttpsDelegate() {
        return getInstance()._getHttpsDelegate();
    }


    // ============Get方便的访问方式============

    /**
     * 同步get
     *
     * @param url
     * @param params
     * @param tag
     * @return
     * @throws IOException
     */
    public static Response get(String url, Param[] params, Object tag) throws IOException {
        return getInstance().getGetDelegate().get(url, params, tag);
    }

    /**
     * 同步string get
     *
     * @param url
     * @param params
     * @param tag
     * @return
     * @throws IOException
     */
    public static String getAsString(String url, Param[] params, Object tag) throws IOException {
        return getInstance().getGetDelegate().getAsString(url, params, tag);
    }

    /**
     * get异步并带回调
     *
     * @param url
     * @param tag
     * @param params
     * @param callback
     */
    public static void getAsyn(String url, Object tag, Param[] params, ResultCallback callback) {
        getInstance().getGetDelegate().getAsyn(url, tag, params, callback);
    }


    //============POST方便的访问方式============

    /**
     * 同步post
     *
     * @param url
     * @param tag
     * @param params
     * @return
     * @throws IOException
     */
    public static Response post(String url, Object tag, Param... params) throws IOException {
        return getInstance().getPostDelegate().post(url, params);
    }

    /**
     * 同步string post
     *
     * @param url
     * @param tag
     * @param params
     * @return
     * @throws IOException
     */
    public static String postAsString(String url, Object tag, Param... params) throws IOException {
        return getInstance().getPostDelegate().postAsString(url, params);
    }

    /**
     * 异步post Param参数
     *
     * @param url
     * @param tag
     * @param params
     * @param callback
     */
    public static void postAsyn(String url, Object tag, Param[] params, final ResultCallback callback) {
        getInstance().getPostDelegate().postAsyn(url, tag, params, callback);
    }

    /**
     * 异步 post Map参数
     *
     * @param url
     * @param tag
     * @param params
     * @param callback
     */
    public static void postAsyn(String url, Object tag, ArrayMap<String, String> params, final ResultCallback callback) {
        getInstance().getPostDelegate().postAsyn(url, tag, params, callback);
    }

    /**
     * 异步post 直接将bodyStr以写入请求体
     *
     * @param url
     * @param tag
     * @param bodyStr
     * @param callback
     */
    public static void postAsyn(String url, Object tag, String bodyStr, final ResultCallback callback) {
        getInstance().getPostDelegate().postAsyn(url, tag, bodyStr, callback);
    }


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 验证参数
     *
     * @param params
     * @return
     */
    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    /**
     * 便利map获取params
     *
     * @param params
     * @return
     */
    private Param[] map2Params(ArrayMap<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<ArrayMap.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (ArrayMap.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * 结果回调
     *
     * @param callback
     * @param request
     */
    private void deliveryResult(ResultCallback callback, Request request) {
        if (callback == null) callback = DEFAULT_RESULT_CALLBACK;
        final ResultCallback resCallBack = callback;
        //UI thread
        callback.onBefore();//请求开始

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(call.request(), e, resCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    if (resCallBack.mType == String.class) {
                        sendSuccessResultCallback(string, resCallBack);
                    } else {
                        Object o = mGson.fromJson(string, resCallBack.mType);
                        sendSuccessResultCallback(o, resCallBack);
                    }


                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, resCallBack);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, resCallBack);
                }
            }
        });

    }

    /**
     * 发送Error结果回调
     *
     * @param request
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, e);
                callback.onAfter();
            }
        });
    }

    /**
     * 发送泛型结果回调
     *
     * @param object
     * @param callback
     */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResponse(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onAfter();
            }
        });
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 根据url和参数构建Form请求
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildPostFormRequest(String url, Object tag, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        //FormEncodingBuilder builder = new FormEncodingBuilder();
        FormBody.Builder requestBody=new FormBody.Builder();
        for (Param param : params) {
            requestBody.add(param.key, param.value);
        }


        return new Request.Builder()
//                .addHeader("key","value") 请求添加header
                .url(url)
                .tag(tag)
                .post(requestBody.build())
                .build();
    }

    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
        mHttpsDelegate.setCertificates(certificates, bksFile, password);
    }

    /**
     * 设置证书，传入cer文件的inputStream即可
     *
     * @param certificates
     */
    public void setCertificates(InputStream... certificates) {
        mHttpsDelegate.setCertificates(certificates, null, null);
    }


    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public void onBefore() {
        }

        public void onAfter() {
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response) throws JSONException;
    }

    /**
     * 默认结果回调
     */
    private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    };


    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }

        String key;
        String value;
    }

    //====================PostDelegate=======================
    public class PostDelegate {
        /**
         * 同步的Post请求
         */
        public Response post(String url, Object tag, Param... params) throws IOException {
            Request request = buildPostFormRequest(url, tag, params);
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        }

        public void postAsyn(String url, Object tag, ArrayMap<String, String> params, final ResultCallback callback) {
            Param[] paramsArr = map2Params(params);
            postAsyn(url, tag, paramsArr, callback);
        }

        /**
         * 同步的Post请求
         */
        public String postAsString(String url, Object tag, Param... params) throws IOException {
            Response response = post(url, tag, params);
            return response.body().string();
        }

        /**
         * 异步的post请求
         */
        public void postAsyn(String url, Object tag, Param[] params, final ResultCallback callback) {
            Request request = buildPostFormRequest(url, tag, params);
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyStr以写入请求体
         */
        public void postAsyn(String url, Object tag, String bodyStr, final ResultCallback callback) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), bodyStr);

            Request request = new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .post(body)
                    .build();
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyFile以写入请求体
         */
        public void postAsyn(String url, Object tag, File bodyFile, final ResultCallback callback) {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), bodyFile);
            Request request = new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .post(body)
                    .build();
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyBytes以写入请求体
         */
        public void postAsyn(String url, Object tag, byte[] bodyBytes, final ResultCallback callback) {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), bodyBytes);
            Request request = new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .post(body)
                    .build();
            deliveryResult(callback, request);
        }

        /**
         * JSON
         * 需要json对象参数时，用户嵌套的对象参数
         * 直接将bodyStr以写入请求体
         */
        public void postJsonAsyn(String url, String token, Object tag, String jsonString, final ResultCallback callback) {
            Log.e("url", url);
            Log.e("jsonString", jsonString);
            Log.e("token", token);

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url(url)
                    .header("token", token)
                    .tag(tag)
                    .post(body)
                    .build();
            deliveryResult(callback, request);
        }

    }

    //====================GetDelegate=======================
    public class GetDelegate {

        /**
         * 拼接get请求URL
         *
         * @param url
         * @param params
         * @return
         */
        private String parseGetUrl(String url, Param[] params) {
            if (params == null) {
                params = new Param[0];
            }
            StringBuffer sb = new StringBuffer();

            sb.append(url);
            sb.append("?");
            for (Param param : params) {
                //builder.add(param.key, param.value);
                try {
                    sb.append("&" + param.key + "=" + URLEncoder.encode(param.value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        /**
         * 同步的Get请求
         */
        public Response get(String url, Param[] params, Object tag) throws IOException {
            final Request request = new Request.Builder()
                    .url(parseGetUrl(url, params))
                    .tag(tag)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            Response execute = call.execute();
            return execute;
        }

        /**
         * 同步的Get请求
         */
        public String getAsString(String url, Param[] params, Object tag) throws IOException {
            Response execute = get(url, params, tag);
            return execute.body().string();
        }


        /**
         * 异步的get请求
         */
        public void getAsyn(String url, Object tag, Param[] params, final ResultCallback callback) {
            final Request request = new Request.Builder()
                    .tag(tag)
                    .url(parseGetUrl(url, params))
                    .build();
            deliveryResult(callback, request);
        }
    }


    //====================DisplayImageDelegate=======================

    /**
     * 加载图片相关
     */
    public class DisplayImageDelegate {
        /**
         * 加载图片
         */
        public void displayImage(final ImageView view, final String url, final Object tag, final int errorResId) {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    setErrorResId(view, errorResId);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    try {
                        is = response.body().byteStream();
                        ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                        ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                        int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                        try {
                            is.reset();
                        } catch (IOException e) {
                            response = mGetDelegate.get(url, null, tag);
                            is = response.body().byteStream();
                        }

                        BitmapFactory.Options ops = new BitmapFactory.Options();
                        ops.inJustDecodeBounds = false;
                        ops.inSampleSize = inSampleSize;
                        final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                view.setImageBitmap(bm);
                            }
                        });
                    } catch (Exception e) {
                        setErrorResId(view, errorResId);

                    } finally {
                        if (is != null) try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            });


        }

        public void displayImage(final ImageView view, String url, Object tag) {
            displayImage(view, url, tag, -1);
        }

        private void setErrorResId(final ImageView view, final int errorResId) {
            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageResource(errorResId);
                }
            });
        }
    }


    //====================UploadDelegate=======================

    /**
     * 上传相关的模块
     */
    public class UploadDelegate {
        /**
         * 同步基于post的文件上传:上传单个文件
         */
        public Response post(String url, String fileKey, File file, Object tag) throws IOException {
            return post(url, new String[]{fileKey}, new File[]{file}, null, tag);
        }

        /**
         * 同步基于post的文件上传:上传多个文件以及携带key-value对：主方法
         */
        public Response post(String url, String[] fileKeys, File[] files, Param[] params, Object tag) throws IOException {
            Request request = buildMultipartFormRequest(url, files, fileKeys, params, tag);
            return mOkHttpClient.newCall(request).execute();
        }

        /**
         * 同步单文件上传
         */
        public Response post(String url, String fileKey, File file, Object tag, Param... params) throws IOException {
            return post(url, new String[]{fileKey}, new File[]{file}, params, tag);
        }

        /**
         * 异步基于post的文件上传:主方法
         */
        public void postAsyn(String url, String[] fileKeys, File[] files, Param[] params, Object tag, ResultCallback callback) {
            Request request = buildMultipartFormRequest(url, files, fileKeys, params, tag);

            deliveryResult(callback, request);
        }

        /**
         * 异步基于post的文件上传:单文件不带参数上传
         */
        public void postAsyn(String url, String fileKey, File file, Object tag, ResultCallback callback, CustomRequestBody.ProgressListener progressListener) throws IOException {
            //postAsyn(url, new String[]{fileKey}, new File[]{file}, null, tag, callback);
            deliveryResult(callback, buildSingleFormRequest(url, file, fileKey, null, tag, progressListener));
        }

        /**
         * 异步基于post的文件上传，单文件且携带其他form参数上传
         */
        public void postAsyn(String url, String fileKey, File file, Param[] params, Object tag, ResultCallback callback, CustomRequestBody.ProgressListener progressListener) {
            //postAsyn(url, new String[]{fileKey}, new File[]{file}, params, tag, callback);
            deliveryResult(callback, buildSingleFormRequest(url, file, fileKey, params, tag, progressListener));
        }


        /**
         * @param url
         * @param file
         * @param fileKey
         * @param params
         * @param tag
         * @param progressListener 上传进度
         * @return
         */
        private Request buildSingleFormRequest(String url, File file, String fileKey, Param[] params, Object tag, CustomRequestBody.ProgressListener progressListener) {
            params = validateParam(params);


            MultipartBody.Builder builder= new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart(fileKey, file.getName(), new CustomRequestBody(file, TYPE, progressListener));
            for (Param param : params) {
                builder.addFormDataPart(param.key, param.value);
            }
            RequestBody requestBody=builder.build();
            return new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .post(requestBody)
                    .build();
        }

        /**
         * 多文件请求
         *
         * @param url
         * @param files
         * @param fileKeys
         * @param params
         * @param tag
         * @return
         */
        private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params, Object tag) {
            params = validateParam(params);


            MultipartBody.Builder builder= new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (Param param : params) {
                builder.addFormDataPart(param.key, param.value);

                /*builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                        RequestBody.create(null, param.value));*/
            }
            if (files != null) {
                RequestBody fileBody = null;
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    String fileName = file.getName();
                    fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                    /*//TODO 根据文件名设置contentType
                    builder.addPart(Headers.of("Content-Disposition",
                                    "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                            fileBody);*/

                    builder.addFormDataPart(fileKeys[i], fileName, fileBody);
                }
            }

            RequestBody requestBody = builder.build();
            return new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .post(requestBody)
                    .build();
        }

    }

    //====================DownloadDelegate=======================

    /**
     * 下载相关的模块
     */
    public class DownloadDelegate {
        /**
         * 异步下载文件
         *
         * @param url
         * @param destFileDir 本地文件存储的文件夹
         * @param callback
         */
        public void downloadAsyn(final String url, final String destFileDir, Object tag, final ResultCallback callback, final CustomRequestBody.ProgressListener listener) {
            final Request request = new Request.Builder()
                    .url(url)
                    .tag(tag)
                    .build();
            //通过设置拦截器的方式回调更新下载进度
            mOkHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()

                            .body(new ProgressResponseBody(originalResponse.body(), (ProgressListener) listener, mDelivery))
                            .build();
                }
            });

            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailedStringCallback(call.request(), e, callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {

                        File dir = new File(destFileDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, getFileName(url));

                        BufferedSink sink = Okio.buffer(Okio.sink(file.getAbsoluteFile()));
                        sink.writeAll(response.body().source());
                        sink.close();
                        //如果下载文件成功，第一个参数为文件的绝对路径
                        sendSuccessResultCallback(file.getAbsolutePath(), callback);
                    } catch (IOException e) {
                        sendFailedStringCallback(response.request(), e, callback);
                    }

                }

            });
        }
    }

    //====================HttpsDelegate=======================

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    /**
     * Https相关模块
     */
    public class HttpsDelegate {
        public TrustManager[] prepareTrustManager(InputStream... certificates) {
            if (certificates == null || certificates.length <= 0) return null;
            try {

                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null);
                int index = 0;
                for (InputStream certificate : certificates) {
                    String certificateAlias = Integer.toString(index++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                    try {
                        if (certificate != null)
                            certificate.close();
                    } catch (IOException e)

                    {
                    }
                }
                TrustManagerFactory trustManagerFactory = null;

                trustManagerFactory = TrustManagerFactory.
                        getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);

                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

                return trustManagers;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        public KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
            try {
                if (bksFile == null || password == null) return null;

                KeyStore clientKeyStore = KeyStore.getInstance("BKS");
                clientKeyStore.load(bksFile, password.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(clientKeyStore, password.toCharArray());
                return keyManagerFactory.getKeyManagers();

            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {

            try {
                TrustManager[] trustManagers = prepareTrustManager(certificates);
                KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
                SSLContext sslContext = SSLContext.getInstance("TLS");

                sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(chooseTrustManager(trustManagers))}, new SecureRandom());
                //mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
                mOkHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

        }
        private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    return (X509TrustManager) trustManager;
                }
            }
            return null;
        }


        public class MyTrustManager implements X509TrustManager {
            private X509TrustManager defaultTrustManager;
            private X509TrustManager localTrustManager;

            public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
                TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                var4.init((KeyStore) null);
                defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
                this.localTrustManager = localTrustManager;
            }


            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    defaultTrustManager.checkServerTrusted(chain, authType);
                } catch (CertificateException ce) {
                    localTrustManager.checkServerTrusted(chain, authType);
                }
            }


            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }

    }

    //====================ImageUtils=======================
    public static class ImageUtils {
        /**
         * 根据InputStream获取图片实际的宽度和高度
         *
         * @param imageStream
         * @return
         */
        public static ImageSize getImageSize(InputStream imageStream) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(imageStream, null, options);
            return new ImageSize(options.outWidth, options.outHeight);
        }

        public static class ImageSize {
            int width;
            int height;

            public ImageSize() {
            }

            public ImageSize(int width, int height) {
                this.width = width;
                this.height = height;
            }

            @Override
            public String toString() {
                return "ImageSize{" +
                        "width=" + width +
                        ", height=" + height +
                        '}';
            }
        }

        public static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize) {
            // 源图片的宽度
            int width = srcSize.width;
            int height = srcSize.height;
            int inSampleSize = 1;

            int reqWidth = targetSize.width;
            int reqHeight = targetSize.height;

            if (width > reqWidth && height > reqHeight) {
                // 计算出实际宽度和目标宽度的比率
                int widthRatio = Math.round((float) width / (float) reqWidth);
                int heightRatio = Math.round((float) height / (float) reqHeight);
                inSampleSize = Math.max(widthRatio, heightRatio);
            }
            return inSampleSize;
        }

        /**
         * 计算合适的inSampleSize
         */
        public static int computeImageSampleSize(ImageSize srcSize, ImageSize targetSize, ImageView imageView) {
            final int srcWidth = srcSize.width;
            final int srcHeight = srcSize.height;
            final int targetWidth = targetSize.width;
            final int targetHeight = targetSize.height;

            int scale = 1;

            if (imageView == null) {
                scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
            } else {
                switch (imageView.getScaleType()) {
                    case FIT_CENTER:
                    case FIT_XY:
                    case FIT_START:
                    case FIT_END:
                    case CENTER_INSIDE:
                        scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
                        break;
                    case CENTER:
                    case CENTER_CROP:
                    case MATRIX:
                        scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // min
                        break;
                    default:
                        scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
                        break;
                }
            }

            if (scale < 1) {
                scale = 1;
            }

            return scale;
        }

        /**
         * 根据ImageView获适当的压缩的宽和高
         *
         * @param view
         * @return
         */
        public static ImageSize getImageViewSize(View view) {

            ImageSize imageSize = new ImageSize();

            imageSize.width = getExpectWidth(view);
            imageSize.height = getExpectHeight(view);

            return imageSize;
        }

        /**
         * 根据view获得期望的高度
         *
         * @param view
         * @return
         */
        private static int getExpectHeight(View view) {

            int height = 0;
            if (view == null) return 0;

            final ViewGroup.LayoutParams params = view.getLayoutParams();
            //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
            if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = view.getWidth(); // 获得实际的宽度
            }
            if (height <= 0 && params != null) {
                height = params.height; // 获得布局文件中的声明的宽度
            }

            if (height <= 0) {
                height = getImageViewFieldValue(view, "mMaxHeight");// 获得设置的最大的宽度
            }

            //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
            if (height <= 0) {
                DisplayMetrics displayMetrics = view.getContext().getResources()
                        .getDisplayMetrics();
                height = displayMetrics.heightPixels;
            }

            return height;
        }

        /**
         * 根据view获得期望的宽度
         *
         * @param view
         * @return
         */
        private static int getExpectWidth(View view) {
            int width = 0;
            if (view == null) return 0;

            final ViewGroup.LayoutParams params = view.getLayoutParams();
            //如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = view.getWidth(); // 获得实际的宽度
            }
            if (width <= 0 && params != null) {
                width = params.width; // 获得布局文件中的声明的宽度
            }

            if (width <= 0)

            {
                width = getImageViewFieldValue(view, "mMaxWidth");// 获得设置的最大的宽度
            }
            //如果宽度还是没有获取到，憋大招，使用屏幕的宽度
            if (width <= 0)

            {
                DisplayMetrics displayMetrics = view.getContext().getResources()
                        .getDisplayMetrics();
                width = displayMetrics.widthPixels;
            }

            return width;
        }

        /**
         * 通过反射获取imageview的某个属性值
         *
         * @param object
         * @param fieldName
         * @return
         */
        private static int getImageViewFieldValue(Object object, String fieldName) {
            int value = 0;
            try {
                Field field = ImageView.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                int fieldValue = field.getInt(object);
                if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                    value = fieldValue;
                }
            } catch (Exception e) {
            }
            return value;

        }
    }


}

