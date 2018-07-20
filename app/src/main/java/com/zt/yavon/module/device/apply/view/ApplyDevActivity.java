package com.zt.yavon.module.device.apply.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.calendar.DateSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ApplyDevActivity extends BaseActivity{
//    @BindView(R.id.iv_lock)
//    ImageView ivLock;
//    @BindView(R.id.tv_switch_lock)
//    TextView tvSwith;
    private Dialog dialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_dev;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.more_derection));
//        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0;i<50;i++){
            list.add(i);
        }
        switch (view.getId()){
            case R.id.tv_count_apply:

                dialog = DialogUtil.createTimeWheelViewDialog(this, 0, null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {

                    }
                });
                break;
            case R.id.tv_day_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, 0, null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {

                    }
                });
                break;
            case R.id.tv_month_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, 0, null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {

                    }
                });
                break;
            case R.id.tv_year_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, 0, null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {

                    }
                });
                break;
            case R.id.tv_custom_apply:
                DateSelectActivity.startAction(this);
                break;
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context, ApplyDevActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
