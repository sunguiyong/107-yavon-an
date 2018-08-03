package com.zt.yavon.module.device.share.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.tuya.smart.sdk.TuyaDeviceShare;
import com.tuya.smart.sdk.api.share.IAddShareForDevsCallback;
import com.tuya.smart.sdk.bean.AddShareInfoBean;
import com.tuya.smart.sdk.bean.ShareIdBean;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.data.TimeBean;
import com.zt.yavon.module.device.share.contract.ShareDevContract;
import com.zt.yavon.module.device.share.presenter.ShareDevPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.calendar.DateSelectActivity;
import com.zt.yavon.widget.wheelview.adapter.MyWheelAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ShareDevActivity extends BaseActivity<ShareDevPresenter> implements ShareDevContract.View{
    @BindView(R.id.et_phone_share)
    EditText etPhone;
    @BindView(R.id.tv_num_share)
    TextView tvNum;
    @BindView(R.id.tv_unit_share)
    TextView tvUnit;
    @BindViews({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply,R.id.tv_need_share,R.id.tv_forever_share})
    List<TextView> tabList;
    private Dialog dialog;
    private List<Integer> dataList;
//    private List<Integer> countList,dayList,monthList,yearList;
    private int defaultCount,defaultDay,defaultMonth,defaultYear;
    private TimeBean customTime = new TimeBean();
    private String type;
    private MineRoomBean.Machine machine;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_share;
    }

    @Override
    public void initPresenter() {
        machine = (MineRoomBean.Machine) getIntent().getSerializableExtra("machine");
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle("共享设备");
//        setRightMenuImage(R.mipmap.more_right);
        if(dataList == null){
            dataList = new ArrayList<>();
            for(int i = 1;i<1000;i++){
                dataList.add(i);
            }
        }
    }

    @OnClick({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply,R.id.tv_need_share,R.id.tv_forever_share,R.id.tv_submit_share})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        int viewId = view.getId();
        switch (view.getId()){
            case R.id.tv_count_apply:
                type = "NUMBER";
                updateTabSelection(viewId);
                tvNum.setText(defaultCount+"");
                tvUnit.setText("次");
                dialog = DialogUtil.createTimeWheelViewDialog(this, "次",defaultCount , dataList, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        defaultCount = data;
                    }
                });
                break;
            case R.id.tv_day_apply:
                type = "DAY";
                updateTabSelection(viewId);
                tvNum.setText(defaultDay+"");
                tvUnit.setText("天");
                dialog = DialogUtil.createTimeWheelViewDialog(this, "天", defaultDay, dataList, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        defaultDay = data;
                    }
                });
                break;
            case R.id.tv_month_apply:
                type = "MONTH";
                updateTabSelection(viewId);
                tvNum.setText(defaultMonth+"");
                tvUnit.setText("个月");
                dialog = DialogUtil.createTimeWheelViewDialog(this, "个月", defaultMonth, dataList, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        defaultMonth = data;
                    }
                });
                break;
            case R.id.tv_year_apply:
                type = "YEAR";
                updateTabSelection(viewId);
                tvNum.setText(defaultYear+"");
                tvUnit.setText("年");
                dialog = DialogUtil.createTimeWheelViewDialog(this, "年", defaultYear, dataList, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvNum.setText(data+"");
                        defaultYear = data;
                    }
                });
                break;
            case R.id.tv_need_share:
                type = "NEED";
                updateTabSelection(viewId);
                tvNum.setText("按需");
                tvUnit.setText("");
                updateTabSelection(R.id.tv_need_share);
                break;
            case R.id.tv_forever_share:
                type = "FOREVER";
                updateTabSelection(viewId);
                tvNum.setText("永久");
                tvUnit.setText("");
                updateTabSelection(R.id.tv_forever_share);
                break;
            case R.id.tv_custom_apply:
                type = "CUSTOM";
                updateTabSelection(viewId);
                if(customTime.getStart() != null){
                    tvNum.setText(customTime.getStart()+"-"+customTime.getEnd());
                }else{
                    tvNum.setText("");
                }
                tvUnit.setText("");
                DateSelectActivity.startAction(this,0x11);
                break;
            case R.id.tv_submit_share:
                submit();
                break;
        }
    }

    private void submit() {
        String value = tvNum.getText().toString().trim();
        if(TextUtils.isEmpty(value) || "0".equals(value)){
            ToastUtil.showShort(this,"请选择使用时间");
            return;
        }
        if("NEED".equals(type) || "FOREVER".equals(type)){
            value = null;
        }else if("CUSTOM".equals(type)){
            value = customTime.toString();
        }
        String phone = etPhone.getText().toString().trim();
        mPresenter.shareDev(machine.getMachine_id(),phone,type,value);
//        if(machine.get){
//
//        }
        tuyaShare(phone);
    }

    private void tuyaShare(String phone) {
        List<String> ids = new ArrayList<>();
        ids.add(machine.getAsset_number());
        TuyaDeviceShare.getInstance().addShareUserForDevs("86", phone, ids, new IAddShareForDevsCallback() {
            @Override
            public void onSuccess(AddShareInfoBean bean) {
                LogUtil.d("===========share success");
            }

            @Override
            public void onError(String code, String error) {
            }
        });
    }

    //    public static void startAction(Activity context, String machine_id,int reqCode){
//        Intent intent = new Intent(context, ShareDevActivity.class);
//        intent.putExtra("machine_id",machine_id);
//        context.startActivityForResult(intent,reqCode);
//    }
    public static void startAction(Activity context,MineRoomBean.Machine bean,int reqCode){
        Intent intent = new Intent(context, ShareDevActivity.class);
        intent.putExtra("machine",bean);
        context.startActivityForResult(intent,reqCode);
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x11 && resultCode == RESULT_OK){
            Date startDate = (Date) data.getSerializableExtra("startDate");
            Date endDate = (Date) data.getSerializableExtra("endDate");
            customTime.setStart(startDate);
            customTime.setEnd(endDate);
            tvNum.setText(customTime.getStart()+"-"+customTime.getEnd());
            tvUnit.setText("");
        }
    }
    private void updateTabSelection(int viewId){
        for(View view:tabList){
            if(view.getId() == viewId){
                view.setSelected(true);
            }else{
                view.setSelected(false);
            }
        }
    }

    @Override
    public void shareSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
