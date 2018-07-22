package com.zt.yavon.component;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.rx.RxManager;
import com.common.base.utils.TUtil;
import com.zt.yavon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

public abstract  class BaseFragment<T extends BasePresenter> extends Fragment {
    public static final int TIME_DELAY_BTN = 500;
    public View rootView;
    public T mPresenter;
    public RxManager mRxManager;
    private Unbinder mUnBinder;
    private long lastClickTime;
    @Nullable
    @BindView(R.id.tv_title_header)
    TextView tvTitle;
    @Nullable
    @BindView(R.id.btn_close_header)
    ImageView ivCloseWeb;
    @Nullable
    @BindView(R.id.btn_back_header)
    TextView ivBack;
    @Nullable
    @BindView(R.id.tv_right_header)
    TextView tvRight;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager=new RxManager();
        mUnBinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        if(mPresenter!=null){
            mPresenter.mContext=this.getActivity();
        }
        initPresenter();
        initView();
        return rootView;
    }
    //获取布局文件
    protected abstract int getLayoutResource();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    protected abstract void initView();

    public void doClick(View view){};
    @Optional
    @OnClick({R.id.btn_back_header,R.id.btn_close_header})
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
//        if (mPresenter != null)
//            mPresenter.onDestroy();
        mRxManager.clear();
    }
    public void showWebviewCloseButton(){
        ivCloseWeb.setVisibility(View.VISIBLE);
    }
    public void setTitle(String text){
        if(tvTitle != null && !TextUtils.isEmpty(text)){
            tvTitle.setText(text);
        }
    }
    public void finishActivity(){
        getActivity().finish();
    }
    public void closeWebview(){
        getActivity().finish();
    }
    public void hideBackButton(){
        if(ivBack != null){
            ivBack.setVisibility(View.GONE);
        }
    }
    public void onHeadBack() {
        getActivity().finish();
    }

    public void setRightMenuText(String text) {
        if (tvRight != null ) {
            tvRight.setText(text);
        }
    }
    public void setLeftMenuText(String text) {
        if (ivBack != null ) {
            ivBack.setText(text);
        }
    }

    public void setRightMenuImage(int resId) {
        if (tvRight != null) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvRight.setCompoundDrawables(drawable, null, null, null);
        }
    }
    public void setLeftButtonImage(int resId) {
        if (ivBack != null) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ivBack.setCompoundDrawables(drawable, null, null, null);
        }
    }
}
