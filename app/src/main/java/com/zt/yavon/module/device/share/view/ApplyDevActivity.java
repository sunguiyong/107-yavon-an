package com.zt.yavon.module.device.share.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TimeBean;
import com.zt.yavon.module.device.share.contract.ApplyDevContract;
import com.zt.yavon.module.device.share.presenter.ApplyDevPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.calendar.DateSelectActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ApplyDevActivity extends BaseActivity<ApplyDevPresenter> implements ApplyDevContract.View{
    @BindView(R.id.tv_name_dev)
    TextView tvName;
    @BindView(R.id.tv_sn_apply)
    TextView tvSn;
    @BindView(R.id.tv_num_apply)
    TextView tvNum;
    @BindView(R.id.tv_unit_apply)
    TextView tvUnit;
    @BindViews({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply})
    List<TextView> tabList;
    private Dialog dialog;
    private List<Integer> dataList;
    //    private List<Integer> countList,dayList,monthList,yearList;
    private int defaultCount,defaultDay,defaultMonth,defaultYear;
    private TimeBean customTime = new TimeBean();
    private String type;
    private String name,sn;
    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_dev;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        name = getIntent().getStringExtra("name");
        sn = getIntent().getStringExtra("sn");
    }

    @Override
    public void initView() {
        setTitle("申请设备");
//        setRightMenuImage(R.mipmap.more_right);
        tvName.setText(name);
        tvSn.setText(sn);
        if(dataList == null){
            dataList = new ArrayList<>();
            for(int i = 1;i<1000;i++){
                dataList.add(i);
            }
        }
    }

    @OnClick({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply,R.id.tv_submit_apply})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        int viewId = view.getId();
        switch (viewId){
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
            case R.id.tv_submit_apply:
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
//        if("NEED".equals(type) || "FOREVER".equals(type)){
//            value = null;
//        }else
        if("CUSTOM".equals(type)){
            value = customTime.toString();
        }
        mPresenter.applyDev(name,sn,type,value,null);
    }
    public static void startAction(Context context,String name,String sn){
        Intent intent = new Intent(context, ApplyDevActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("sn",sn);
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
    public void applySuccess() {
        ToastUtil.showShort(this,"申请成功");
        finish();
    }
}
