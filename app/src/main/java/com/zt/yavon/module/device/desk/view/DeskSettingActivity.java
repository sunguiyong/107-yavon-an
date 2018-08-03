package com.zt.yavon.module.device.desk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.device.desk.contract.DeskSettingContract;
import com.zt.yavon.module.device.desk.presenter.DeskSettingPresenter;
import com.zt.yavon.module.device.lamp.view.LampRecordActivity;
import com.zt.yavon.module.device.lamp.view.LampUseActivity;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskSettingActivity extends BaseActivity<DeskSettingPresenter> implements DeskSettingContract.View{
    @BindView(R.id.switch_remind_desk)
    SwitchCompat switchRemind;
    @BindView(R.id.tv_time_set)
    TextView tvTime;
    private Dialog dialog;
    private DevDetailBean machine;
    @Override
    public int getLayoutId() {
        return R.layout.activity_more_setting_desk;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machine = (DevDetailBean) getIntent().getSerializableExtra("detailbean");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_more));
//        setRightMenuImage(R.mipmap.more_right);
        tvTime.setText(machine.getSedentary_time());
        switchRemind.setChecked(machine.isSedentary_reminder());
    }

    @OnClick({R.id.tv_time_set,R.id.tv_use_direct_desk,R.id.tv_use_record_desk})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_time_set:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createSitTimeDialog(this, tvTime.getText().toString(), new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        mPresenter.setSeatTime(machine.getMachine_id(),data);
                    }
                });
                break;
            case R.id.tv_use_direct_desk:
                DeskUseActivity.startAction(this);
                break;
            case R.id.tv_use_record_desk:
                DeskRecordActivity.startAction(this);
                break;
        }
    }
    @OnCheckedChanged(R.id.switch_remind_desk)
    public void check(CompoundButton view, boolean checked){
        if(checked){
            if(TextUtils.isEmpty(tvTime.getText().toString().trim())){
                switchRemind.setChecked(false);
            }else{
                mPresenter.setSeatSwitch(machine.getMachine_id(),true);
            }
        }else{
            mPresenter.setSeatSwitch(machine.getMachine_id(),false);
        }
    }
    public static void startAction(Activity context, DevDetailBean devBean,int reqCode){
        Intent intent = new Intent(context,DeskSettingActivity.class);
        intent.putExtra("detailbean",devBean);
        context.startActivityForResult(intent,reqCode);
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }

    @Override
    public void setTimeSuccess(String hour) {
        tvTime.setText(hour);
    }

    private void setResultData(){
        Intent intent = new Intent();
        intent.putExtra("time", tvTime.getText().toString().trim());
        intent.putExtra("reminder", switchRemind.isChecked());
        setResult(RESULT_OK);
    }

    @Override
    public void onHeadBack() {
        setResultData();
        super.onHeadBack();
    }

    @Override
    public void onBackPressed() {
        setResultData();
        super.onBackPressed();
    }
}
