package com.zt.yavon.module.device.lock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LockRecordActivity extends BaseActivity{
//    @BindView(R.id.iv_lock)
//    ImageView ivLock;
//    @BindView(R.id.tv_switch_lock)
//    TextView tvSwith;
    @Override
    public int getLayoutId() {
        return R.layout.activity_record_use_lock;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setStatusBarColor(ContextCompat.getColor(this,R.color.black2));
        setTitleBackgroudColor(R.color.black2);
        setTitle(getString(R.string.more_record));
//        setRightMenuImage(R.mipmap.more_right);
    }

//    @OnClick({R.id.tv_switch_lock,R.id.tv_right_header})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.tv_switch_lock:
//                if(tvSwith.isSelected()){
//                    ivLock.setSelected(false);
//                    tvSwith.setSelected(false);
//                }else{
//                    ivLock.setSelected(true);
//                    tvSwith.setSelected(true);
//                }
//                break;
//            case R.id.tv_right_header:
//                break;
//        }
//    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,LockRecordActivity.class);
        context.startActivity(intent);
    }
}
