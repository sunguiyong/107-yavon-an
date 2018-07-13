package com.zt.yavon.module.main.view;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2017/11/25.
 */
public class WebviewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    private String url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initPresenter() {
        initIntentData(getIntent());
    }

    private void initIntentData(Intent intent) {
        if(intent != null){
            url = intent.getStringExtra("web_url");
        }
    }
    public static void start(Activity activity, String url){
        Intent intent = new Intent(activity,WebviewActivity.class);
        intent.putExtra("web_url",url);
        activity.startActivity(intent);
    }
    @Override
    public void initView() {

        initWebView();
    }

    private void initWebView() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                if(!TextUtils.isEmpty(title)){
                    if(title.length()>10){
                        title = title.substring(0,7)+"...";
                    }
                    setTitle(title);
                }
                super.onPageFinished(view, url);
            }
        });
        if(!TextUtils.isEmpty(url))
        mWebView.loadUrl(url);
    }
    @OnClick({R.id.btn_back_header})
    public void doClick(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView!=null){
            mWebView.clearHistory();
        }
    }

    @Override
    public void onBackPressed() {
        doClick();
    }
}
