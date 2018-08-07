package com.zt.yavon.module.main.roommanager.detail;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.roommanager.list.model.RoomBean;
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
        RoomBean bean = (RoomBean) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        tvRoomName.setText(bean.mName);
        ivRoom.setImageResource(bean.mResId);
        // TODO setData
//        rvRoomDevice.setData(DeviceItemBean.data);
        tvRoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.createEtDialog(ActRoomDetail.this,false, "重命名", bean.mName, new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
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
