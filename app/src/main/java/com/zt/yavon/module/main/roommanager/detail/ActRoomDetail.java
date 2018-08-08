package com.zt.yavon.module.main.roommanager.detail;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;

public class ActRoomDetail extends BaseActivity {
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_room)
    ImageView ivRoom;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rv_room_device)
    RvRoomDevice rvRoomDevice;
    @BindView(R.id.btn_del)
    Button btnDel;

    @Override
    public int getLayoutId() {
        return R.layout.act_room_detail_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("房间详情");
        TabBean tabBean = (TabBean) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        tvRoomName.setText(tabBean.name);
        Glide.with(this).load(tabBean.icon_select).into(ivRoom);
        rvRoomDevice.setData(tabBean.machines);
        tvCount.setText("已移入" + tabBean.getMachineSize() + "个设备");
        tvRoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.createEtDialog(ActRoomDetail.this,false, "重命名", tabBean.name, new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        // TODO call api
                        tvRoomName.setText(data);
                    }
                });
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
