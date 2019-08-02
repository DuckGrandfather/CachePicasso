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

    private Button mbtnBack;
    private WebView mwebView;

    private int mprogress;

    private static final int UPER_LIMIT = 70;
    private static final int MIDDLE_LIMIT = 60;
    private static final int LOW_LIMIT = 10;
    private static final int INCREASE_MANUFATUAL = 1;

    private ProgressBar mSmoothprogressbar;


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
                if(mSmoothprogressbar.getVisibility() == View.VISIBLE){
                    mSmoothprogressbar.setVisibility(View.INVISIBLE);
                }
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
                mprogress = newProgress;
                if(mSmoothprogressbar.getVisibility() ==  View.VISIBLE){
                    setmyProgress(mprogress);
                }
            }
        });

        mwebView.loadUrl("https://developer.android.google.cn/");

    }



    private void setmyProgress(int progress) {
        int gto = getSmoothprogress(progress);
        int gfrom = progress;
        while (gfrom < gto){
            gfrom += INCREASE_MANUFATUAL;
            mSmoothprogressbar.setProgress(gfrom);
        }
    }

    private int getSmoothprogress(int progress){
        if(progress<=LOW_LIMIT){
            return LOW_LIMIT;
        }else if(progress<=MIDDLE_LIMIT){
            return MIDDLE_LIMIT;
        }else if(progress<=UPER_LIMIT){
            return  UPER_LIMIT;
        }else {
            return 100;
        }
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
