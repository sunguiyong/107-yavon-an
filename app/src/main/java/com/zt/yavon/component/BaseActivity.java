package com.zt.yavon.component;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.rx.RxManager;
import com.common.base.utils.LoadingDialog;
import com.zt.yavon.R;
import com.zt.yavon.utils.StatusBarCompat;
import com.common.base.utils.TUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * 基类
 */

/***************
 * 使用例子
 *********************/
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public static final int TIME_DELAY_BTN = 500;
    public T mPresenter;
    public RxManager mRxManager;
    private Unbinder mUnBinder;
    private long lastClickTime;
    private Dialog progressDialog;
    @Nullable
    @BindView(R.id.tv_title_header)
    TextView tvTitle;
    @Nullable
    @BindView(R.id.tv_right_header)
    TextView tvRight;
    @Nullable
    @BindView(R.id.btn_close_header)
    ImageView ivCloseWeb;
    @Nullable
    @BindView(R.id.btn_back_header)
    ImageView ivBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        setThemeStyle();
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
//        mRxManager.on(Constants.TAG_EVENT_ERROR_TOKEN, new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                finish();
//            }
//        });
        initPresenter();
        initView();
    }

    public void setThemeStyle() {

    }


    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();


    public void doClick(View view){};


    @Optional
    @OnClick({R.id.btn_back_header, R.id.btn_close_header})
    public void doubleClickFilter(View view){
        long curTime = System.currentTimeMillis();
        if(curTime-lastClickTime > TIME_DELAY_BTN){
            lastClickTime = curTime;
            switch (view.getId()){
                case R.id.btn_back_header:
                    onHeadBack();
                    break;
                case R.id.btn_close_header:
                    closeWebview();
                    break;
                default:
                    doClick(view);
            }
        }
    }
    public void hideBackButton(){
        if(ivBack != null){
            ivBack.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //debug版本不统计crash
//        if(!BuildConfig.LOG_DEBUG) {
//            //友盟统计
//            MobclickAgent.onResume(this);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //debug版本不统计crash
//        if(!BuildConfig.LOG_DEBUG) {
//            //友盟统计
//            MobclickAgent.onPause(this);
//        }
    }
    public void showProgress(String msg){
        if(TextUtils.isEmpty(msg))
           msg = "处理中..." ;
        dismissProgress();
        progressDialog = LoadingDialog.showDialogForLoading(this,msg,false,false);
    }
    public void dismissProgress(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    @Override
    protected void onDestroy() {
        dismissProgress();
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        mUnBinder.unbind();
    }



    public void setTitle(String text){
        if(tvTitle != null && !TextUtils.isEmpty(text)){
            tvTitle.setText(text);
        }
    }
    public void closeWebview(){
        finish();
    }

    public void onHeadBack(){
        finish();
    }

    public void setRightMenuText(String text){
        if(tvRight != null && !TextUtils.isEmpty(text)){
            tvRight.setText(text);
        }
    }

    public void setRightMenuImage(int resId){
        if(tvRight != null ){
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvRight.setCompoundDrawables(drawable,null,null,null);
        }
    }
    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this,true);
    }
    protected void setStatusBarColor(int color){
        StatusBarCompat.setStatusBarColor(this,color);
    }
//    protected void showStatusBar() {
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.appThem));
//    }
//    protected void hideStatusBar() {
//        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
//    }

    /**
     * 沉浸式的文字颜色改变（4.4以上系统有效）
     */
    protected void SetStateTextColorDark(){
//        StatusBarCompat.setStatusBarDarkMode(true, this);//false 状态栏字体颜色是白色 true 颜色是黑色
        StatusBarCompat.setStatusBarLightMode(this,Color.BLACK);
    }
    protected void SetStateTextColorWhite(){
//        StatusBarCompat.setStatusBarDarkMode(false, this);//false 状态栏字体颜色是白色 true 颜色是黑色
        StatusBarCompat.setStatusBarLightMode(this,Color.WHITE);

    }

}