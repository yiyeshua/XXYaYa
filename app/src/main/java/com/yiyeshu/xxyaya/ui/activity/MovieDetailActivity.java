package com.yiyeshu.xxyaya.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.base.BaseActivity;
import com.yiyeshu.xxyaya.views.ProgressWebView;
import com.yiyeshu.xxyaya.views.TitleBar;

import butterknife.BindView;

public class MovieDetailActivity extends BaseActivity {


    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.title)
    TitleBar mTitle;
    @BindView(R.id.web_content)
    FrameLayout web_content;
    private WebView webView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        webView = new ProgressWebView(getApplicationContext());
        web_content.removeAllViews();
        web_content.addView(webView);
        WebSettings settings = webView.getSettings();
        webView.loadUrl(url);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);

        webView.setWebViewClient(new WebViewClient(){
            //重写该方法返回true，设置网页在本应用内打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setTitle(title);
            }
        });

        mTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailActivity.this, "分享未做", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    //处理webview点击返回效果
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一浏览页面
                return true;
            } else {
                finish();//关闭Activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //防止webview导致内存泄漏
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
