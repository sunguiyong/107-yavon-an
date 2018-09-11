package com.zt.yavon.module.device.lamp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.LoadingDialog;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.lamp.contract.LampDetailContract;
import com.zt.yavon.module.device.lamp.presenter.LampDetailPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.TuYaLampSDK;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LampDetailActivity extends BaseActivity<LampDetailPresenter> implements LampDetailContract.View {
    @BindView(R.id.iv_lamp)
    ImageView ivLamp;
    @BindView(R.id.tv_switch_lamp)
    TextView tvSwith;
    private TabBean.MachineBean machineBean;
    private Dialog dialog;
    private TuYaLampSDK tuyaSDK;
    private DevDetailBean bean;
    private TuYaLampSDK.TuYaListener listener = new TuYaLampSDK.TuYaListener(){
        @Override
        public void onSwitchChanged(boolean isOn) {
            DialogUtil.dismiss(dialog);
            updateView(isOn);
            mPresenter.switchDev(machineBean.id+"",isOn);
        }
    };
//    private Socket socket;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lamp_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
    }

    @Override
    public void initView() {
        setTitle(machineBean.name);
        setRightMenuImage(R.mipmap.more_right);
        tuyaSDK = new TuYaLampSDK(machineBean.light_device_id,listener);
        DeviceBean deviceBean = tuyaSDK.getDeviceBean();
        if (deviceBean != null) {
            Boolean isopen = (Boolean) deviceBean.getDps().get("1");
            updateView(isopen);
        }
//        initSocket();
//        updateView("ON".equals(machineBean.status));
        mPresenter.getDevDetail(machineBean.id+"");
    }
    @OnClick({R.id.tv_switch_lamp, R.id.tv_right_header})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_switch_lamp:
                DialogUtil.dismiss(dialog);
                dialog = LoadingDialog.showDialogForLoading(LampDetailActivity.this,"操作中...",true,null);
                try{
                    tuyaSDK.switchLamp(!tvSwith.isSelected());
                }catch (Exception e){
                    e.printStackTrace();
                    DialogUtil.dismiss(dialog);
                }
                break;
            case R.id.tv_right_header:
                LampSettingActivity.startAction(this,bean);
                break;
        }
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, LampDetailActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }

    @Override
    public void returnDevDetail(DevDetailBean bean) {
        this.bean = bean;
        ivLamp.setImageResource(R.drawable.selector_lamp_dev);
        updateView("ON".equals(bean.getMachine_status()));
    }

    private void updateView(boolean isOn) {
        tvSwith.setSelected(isOn);
        ivLamp.setSelected(isOn);
    }


    @Override
    protected void onDestroy() {
        tuyaSDK.release();
        DialogUtil.dismiss(dialog);
//        if(socket != null){
//            socket.disconnect();
//        }
        super.onDestroy();
    }
}
