package com.ducky.cachepicasso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {

    private ProgressBar mSmoothprogressbar;
    private Button mbtnBack;
    private WebView mwebView;
    private SmoothProgress msmoothProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        initView();
    }

    private void initView(){
        mbtnBack = findViewById(R.id.btn_back);
        mwebView = findViewById(R.id.wv_web);
        mSmoothprogressbar = findViewById(R.id.pgbar_loadweb);
        msmoothProgress = new SmoothProgress(mSmoothprogressbar);

        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mwebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                msmoothProgress.hideProgress();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        mwebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("wq","progress int:"+ newProgress);
//                msmoothProgress.setProgressBySmoothValue(newProgress);
                msmoothProgress.setProgressByAnimation(newProgress);
            }
        });

        mwebView.loadUrl("https://coolshell.cn/articles/4990.html");

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
