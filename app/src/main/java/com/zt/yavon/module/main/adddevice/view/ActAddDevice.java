package com.zt.yavon.module.main.adddevice.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.frame.model.DeviceEnum;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActAddDevice extends BaseActivity {
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.cb_3)
    CheckBox cb3;
    @BindView(R.id.cb_4)
    CheckBox cb4;
    @BindView(R.id.cb_group_1)
    CheckBox cbGroup1;
    @BindView(R.id.cb_group_2)
    CheckBox cbGroup2;

    @Override
    public int getLayoutId() {
        return R.layout.act_add_device_layout;
    }

    @Override
    public void initPresenter() {

    }

    private CompoundButton.OnCheckedChangeListener mCbGroup1Listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            cb1.setChecked(isChecked);
            cb2.setChecked(isChecked);
        }
    };
    private CompoundButton.OnCheckedChangeListener mCbGroup2Listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            cb3.setChecked(isChecked);
            cb4.setChecked(isChecked);
        }
    };

    @Override
    public void initView() {
        setTitle("添加设备");
        setRightMenuText("完成");
        cbGroup1.setOnCheckedChangeListener(mCbGroup1Listener);
        cbGroup2.setOnCheckedChangeListener(mCbGroup2Listener);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbGroup1.setOnCheckedChangeListener(null);
                    cbGroup1.setChecked(false);
                    cbGroup1.setOnCheckedChangeListener(mCbGroup1Listener);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbGroup1.setOnCheckedChangeListener(null);
                    cbGroup1.setChecked(false);
                    cbGroup1.setOnCheckedChangeListener(mCbGroup1Listener);
                }
            }
        });
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbGroup2.setOnCheckedChangeListener(null);
                    cbGroup2.setChecked(false);
                    cbGroup2.setOnCheckedChangeListener(mCbGroup2Listener);
                }
            }
        });
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbGroup2.setOnCheckedChangeListener(null);
                    cbGroup2.setChecked(false);
                    cbGroup2.setOnCheckedChangeListener(mCbGroup2Listener);
                }
            }
        });
        findViewById(R.id.tv_right_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DeviceItemBean> checkedBeans = new ArrayList<>();
                if (cb1.isChecked())
                    checkedBeans.add(new DeviceItemBean("", "办公室", true, DeviceEnum.Lamp));
                if (cb2.isChecked())
                    checkedBeans.add(new DeviceItemBean("", "办公室", true, DeviceEnum.Lamp));
                if (cb3.isChecked())
                    checkedBeans.add(new DeviceItemBean("", "会议室", true, DeviceEnum.Lamp));
                if (cb4.isChecked())
                    checkedBeans.add(new DeviceItemBean("", "会议室", true, DeviceEnum.Lamp));
                setResult(RESULT_OK, (Serializable) checkedBeans);
                finish();
            }
        });
    }

}
