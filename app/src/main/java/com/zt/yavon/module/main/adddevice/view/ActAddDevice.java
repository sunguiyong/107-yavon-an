package com.zt.yavon.module.main.adddevice.view;

import android.view.View;
import android.widget.CheckBox;

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

    @Override
    public int getLayoutId() {
        return R.layout.act_add_device_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("添加设备");
        setRightMenuText("完成");
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
