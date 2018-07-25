package com.zt.yavon.module.guide;

import android.os.Message;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.module.account.view.SelectAccountActivity;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/11.
 */

public class GuideActivity extends BaseActivity {
    private int WHAT_DELAY = 1000;
    private LeakSafeHandler<GuideActivity> mHander = new LeakSafeHandler<GuideActivity>(this){
        @Override
        public void onMessageReceived(GuideActivity mActivity, Message msg) {
            boolean isAutoLogin = SPUtil.getBoolean(mActivity,SPUtil.AUTO_LOGIN,false);
            if(isAutoLogin){
                MainActivity.startAction(mActivity);
            }else{
                SelectAccountActivity.startAction(mActivity);
            }
            mActivity.finish();
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mHander.sendEmptyMessageDelayed(WHAT_DELAY,WHAT_DELAY);
    }

    @Override
    protected void onDestroy() {
        if(mHander.hasMessages(WHAT_DELAY)){
            mHander.removeMessages(WHAT_DELAY);
        }
        super.onDestroy();
    }

}
