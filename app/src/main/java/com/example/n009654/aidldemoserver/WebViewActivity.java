package com.example.n009654.aidldemoserver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webview;
    private Button showjs;
    /**
     * webview的使用容易产生内存泄漏
     * 1.可以动态加载webview
     * 2.在ondestory的时候移除webview
     *
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webview = (WebView) findViewById(R.id.webview);
        showjs = (Button) findViewById(R.id.showjs);
        showjs.setOnClickListener(this);
        //解决网页不能弹框的问题
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //加载的是网页上的资源
        //webview.loadUrl("http://news.baidu.com/guonei");
        webview.loadUrl("file:///android_asset/study.html");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showjs:
            // 只需要将第一种方法的loadUrl()换成下面该方法即可
                Log.i("ccc","hhhhhh");
                webview.evaluateJavascript("study:show()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        Log.i("ccc",value);
                    }
                });
                break;
        }
    }
}
