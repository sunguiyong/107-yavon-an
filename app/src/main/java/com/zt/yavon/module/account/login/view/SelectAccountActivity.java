package com.zt.yavon.module.account.login.view;

import android.view.View;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class SelectAccountActivity extends BaseActivity {

    @BindView(R.id.tv_create_new)
    TextView tvCreateNew;
    @BindView(R.id.tv_use_have)
    TextView tvUseHave;
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_account;
    }

    @Override
    public void initPresenter() {
        mRxManager.on(Constants.EVENT_LOGIN_SUCCESS, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                    finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_create_new, R.id.tv_use_have})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create_new:
                LoginRegisterActivity.start(this,"register");
                break;
            case R.id.tv_use_have:
                LoginRegisterActivity.start(this,"login");
                break;
        }
    }
}
