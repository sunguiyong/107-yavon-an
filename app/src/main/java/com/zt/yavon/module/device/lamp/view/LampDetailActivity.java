package com.zt.yavon.module.device.lamp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.common.base.utils.LoadingDialog;
import com.tuya.smart.sdk.TuyaDevice;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.lamp.contract.LampDetailContract;
import com.zt.yavon.module.device.lamp.presenter.LampDetailPresenter;
import com.zt.yavon.module.device.lock.contract.LockDetailContract;
import com.zt.yavon.module.device.lock.presenter.LockDetailPresenter;
import com.zt.yavon.module.device.lock.view.LockDetailActivity;
import com.zt.yavon.utils.DialogUtil;

import java.util.HashMap;

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
    public static final String STHEME_LAMP_DPID_1 = "1"; //灯开关
    private TabBean.MachineBean machineBean;
    private TuyaDevice mDevice;
    private DevDetailBean bean;
    private Dialog dialog;

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
        setTitle(getString(R.string.title_lamp));
        setRightMenuImage(R.mipmap.more_right);
        DeviceBean deviceBean = TuyaUser.getDeviceInstance().getDev(machineBean.asset_number);
        if (deviceBean != null) {
            Boolean isopen = (Boolean) deviceBean.getDps().get("1");
            updateView(isopen);
        }
        mDevice = new TuyaDevice(machineBean.asset_number);
        mDevice.registerDevListener(new IDevListener() {
            @Override
            public void onDpUpdate(String devId, String dpStr) {
//                LogUtil.d("=======dpstr:"+dpStr);
                DialogUtil.dismiss(dialog);
                boolean isOn = dpStr.contains("true");
                updateView(isOn);
                mPresenter.switchDev(machineBean.id+"",isOn);
                //dp数据更新:devId 和相应dp数据
            }

            @Override
            public void onRemoved(String devId) {
                //设备被移除
            }

            @Override
            public void onStatusChanged(String devId, boolean online) {
                //设备在线状态，online
            }

            @Override
            public void onNetworkStatusChanged(String devId, boolean status) {
                //网络状态监听
            }

            @Override
            public void onDevInfoUpdate(String devId) {
                //设备信息变更，目前只有设备名称变化，会调用该接口
            }
        });
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
                if (tvSwith.isSelected()) {
                    closeLamp();
                } else {
                    openLamp();
                }
                break;
            case R.id.tv_right_header:
                LampSettingActivity.startAction(this);
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
//        updateView("ON".equals(bean.getMachine_status()));
    }

    private void updateView(boolean isOn) {
        tvSwith.setSelected(isOn);
        ivLamp.setSelected(isOn);
    }

    public void openLamp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STHEME_LAMP_DPID_1, true);
        mDevice.publishDps(JSONObject.toJSONString(hashMap), null);
    }

    public void closeLamp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STHEME_LAMP_DPID_1, false);
        mDevice.publishDps(JSONObject.toJSONString(hashMap), null);
    }

    @Override
    protected void onDestroy() {
        if (mDevice != null) {
            mDevice.unRegisterDevListener();
            mDevice.onDestroy();
        }
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
