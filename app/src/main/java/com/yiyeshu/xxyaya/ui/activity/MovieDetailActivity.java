package com.yiyeshu.xxyaya.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.base.BaseActivity;
import com.yiyeshu.xxyaya.views.TitleBar;

import butterknife.BindView;

public class MovieDetailActivity extends BaseActivity {


    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.title)
    TitleBar title;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        final WebView webView = new WebView(getApplicationContext());
        linear.addView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        linear.addView(webView);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.loadUrl(url);
            }
        });


    }

    @Override
    protected void initData() {

    }

}
