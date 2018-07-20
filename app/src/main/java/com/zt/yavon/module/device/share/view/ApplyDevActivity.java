package com.zt.yavon.module.device.share.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.calendar.DateSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ApplyDevActivity extends BaseActivity{
    @BindView(R.id.tv_num_apply)
    TextView tvNum;
    @BindView(R.id.tv_unit_apply)
    TextView tvUnit;
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
        setTitle("申请设备");
//        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply,R.id.tv_submit_apply})
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

                dialog = DialogUtil.createTimeWheelViewDialog(this, "次", null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        tvUnit.setText("次");
                    }
                });
                break;
            case R.id.tv_day_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, "天", null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        tvUnit.setText("天");
                    }
                });
                break;
            case R.id.tv_month_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, "月", null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        tvUnit.setText("个月");
                    }
                });
                break;
            case R.id.tv_year_apply:
                dialog = DialogUtil.createTimeWheelViewDialog(this, "年", null, list, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        tvUnit.setText("年");
                    }
                });
                break;
            case R.id.tv_custom_apply:
                DateSelectActivity.startAction(this,0x10);
                break;
            case R.id.tv_submit_apply:
                EditDevActivity.startAction(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x10 && resultCode == RESULT_OK){
            String date = data.getStringExtra("date");
            tvNum.setText(date);
            tvUnit.setText("");
        }
    }
}
