package com.erickogi14gmail.study20.Main.Revision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.erickogi14gmail.study20.Main.Configs.api;

/**
 * Created by kimani kogi on 5/31/2017.
 */

public class MyPdfViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String u = intent.getStringExtra(api.KEY_ARTICLE_URL);
        WebView mWebView = new WebView(MyPdfViewActivity.this);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl(u);
        setContentView(mWebView);
    }
}