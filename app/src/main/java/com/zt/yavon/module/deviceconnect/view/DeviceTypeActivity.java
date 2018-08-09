package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.deviceconnect.contract.DevTypeContract;
import com.zt.yavon.module.deviceconnect.presenter.DevTypePresenter;
import com.zt.yavon.network.YSBResponse;
import com.zt.yavon.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class DeviceTypeActivity extends BaseActivity<DevTypePresenter> implements DevTypeContract.View{
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_dec)
    TextView tvDeviceDec;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.re_device)
    EasyRecyclerView reDevice;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.iv_dev_small)
    ImageView ivDevSmall;
    private DevTypeBean.TYPE bean;
    private String accessToken;
    private TabBean.MachineBean machineBean = new TabBean.MachineBean();
    @Override
    public int getLayoutId() {
        return R.layout.activity_device_type;
    }

    public static void start(Activity activity, DevTypeBean.TYPE type) {
        Intent intent = new Intent(activity, DeviceTypeActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        mRxManager.on(Constants.EVENT_BIND_DEV_SUCCESS, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
        bean = (DevTypeBean.TYPE) getIntent().getSerializableExtra("type");
//        LogUtil.d("==============DeviceTypeActivity,type:"+bean.type+",sn:"+bean.sn);
        machineBean.machine_type = bean.type;
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle(bean.name);
        tvDeviceName.setText(bean.name);
        tvDeviceDec.setText(bean.description);
        Glide.with(this)
                .load(bean.icon_big)
                .into(ivDevice);
        Glide.with(this)
                .load(bean.icon)
                .into(ivDevSmall);
        switch (bean.type) {
            case Constants.MACHINE_TYPE_BLUE_LOCK:
                tvDevice.setText(bean.sn);
                machineBean.asset_number = bean.sn;
//                        WifiDeviceActivity.start(DeviceAddActivity.this);
                break;
            case Constants.MACHINE_TYPE_BATTERY_LOCK:
                machineBean.from_room = bean.sn;//扫码字符串，添加易琐宝后台要用
                mPresenter.getToken();
                break;
            case Constants.MACHINE_TYPE_LIGHT:
                tvDevice.setText(bean.sn);
                machineBean.asset_number = bean.sn;
                if(!TextUtils.isEmpty(bean.sn) && bean.sn.length() >= 12){
                    String mac = bean.sn.substring(bean.sn.length() -12);
                    mPresenter.getAssetNumber(mac,bean.type);
                }else{
                    tvDevice.setText(bean.sn);
                }
                break;
            case Constants.MACHINE_TYPE_ADJUST_TABLE:
//                84f3ebb3a4b4
//                tvDevice.setText(bean.sn);
                machineBean.asset_number = bean.sn;
                mPresenter.getAssetNumber(bean.sn,bean.type);
                break;
        }
    }

    @OnClick({R.id.tv_right_header, R.id.tv_next})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_header:

                break;
            case R.id.tv_next:
//                if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(bean.type)){
//                    if(TextUtils.isEmpty(machineBean.password)){
//                        ToastUtil.showShort(this,"添加失败，请重试！");
//                        return;
//                    }
//                }
                EditDevActivity.startAction(this,machineBean);
                break;
        }
    }

    @Override
    public void getTokenSuccess(String header) {
        accessToken  = header;
//        mPresenter.addScanLock(accessToken,bean.sn);
//        mPresenter.getLockPwd(accessToken,response.getSn());
        mPresenter.getLockSN(accessToken,bean.sn);
    }
//
//    @Override
//    public void addScanLockSuccess(String lockId) {
//        machineBean.locker_id = lockId;
//        mPresenter.getLockSN(accessToken,bean.sn);
//    }

    @Override
    public void getLockSNSuccess(YSBResponse response) {
        tvDevice.setText(response.getSn());
        machineBean.asset_number = response.getSn();
//        mPresenter.getLockPwd(accessToken,response.getSn());
    }

    @Override
    public void returnAssetNumb(String assetNumb) {
        tvDevice.setText(assetNumb);
    }

//    @Override
//    public void returnLockPwd(YSBResponse response) {
//        machineBean.password = response.getData();
//    }
}
