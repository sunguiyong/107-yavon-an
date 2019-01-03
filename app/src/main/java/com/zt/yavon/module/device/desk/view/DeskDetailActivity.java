package com.zt.yavon.module.device.desk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketOpcode;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.desk.contract.DeskDetailContract;
import com.zt.yavon.module.device.desk.presenter.DeskDetailPresenter;
import com.zt.yavon.network.Api;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.MyTextView;
import com.zt.yavon.widget.VerticalSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnTouch;


/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskDetailActivity extends BaseActivity<DeskDetailPresenter> implements DeskDetailContract.View{
    public static final int WHAT_MAC = 0x35;
    public static final int HEIGHT_BOTTOM = 76;
    public static final int HEIGHT_TOP = 116;
    private int WHAT_SET = 0x21;
    @BindView(R.id.progress_desk)
    VerticalSeekBar seekBar;
    @BindView(R.id.guideline1)
    TextView tvTop;
    @BindView(R.id.guideline2)
    TextView tvLow;
    @BindView(R.id.tv_progress_desk)
    TextView tvProgress;
    @BindViews({R.id.tv_zdy1_desk,R.id.tv_zdy2_desk,R.id.tv_zdy3_desk,R.id.tv_zdy4_desk,R.id.tv_zdy5_desk})
    List<MyTextView> zdyList;
    private Dialog dialog;
    private List<DeskBean> heightList;
    private DevDetailBean bean;
    private int REQ_SETTING = 0x100;
    private boolean updateHeight = true;
    private LeakSafeHandler<DeskDetailActivity> mHandler = new LeakSafeHandler<DeskDetailActivity>(this){
        @Override
        public void onMessageReceived(DeskDetailActivity activity, Message msg) {
            if(activity.timer == null) return;
            if(msg.what == activity.WHAT_SET){
                //延时提交
                activity.timer.cancel();
                activity.timer.start();
            }else if(msg.what == activity.WHAT_MAC){
                if(activity.socket != null && activity.socket.isOpen()){
                    socket.sendText(activity.bean.getWifi_mac());
                }else{
                    sendEmptyMessageDelayed(activity.WHAT_MAC,1000);
                }
            }
        }
    };
    private CountDownTimer timer = new CountDownTimer(1000,50) {
        int lastProgress = -1;
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //设置桌子高度
            updateHeight = true;
            int curProgress = seekBar.getProgress();
            if(seekBar.getProgress() != lastProgress){
                lastProgress = curProgress;
                mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
//                LogUtil.d("=====================send data");
            }
        }
    };
    private MyTimer2 timer2 = new MyTimer2(1000,50);
    private TabBean.MachineBean machineBean;
    private long curMills;
    private int delta = 1;
    private WebSocket socket;
    private WebSocketAdapter listener = new WebSocketAdapter(){
        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
        {
            LogUtil.d("===============连接成功");
        }
        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception
        {
            LogUtil.d("===============连接出错:"+exception.getMessage());
        }
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception
        {
//            LogUtil.d("===============收到消息:"+text);
        }

        @Override
        public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
//            LogUtil.d("===============收到frame:"+frame.toString());
            if(frame.getOpcode() == WebSocketOpcode.BINARY){
                String height = frame.getPayloadText();
                if(updateHeight)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            setSeekBarProgress(Integer.parseInt(height));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                LogUtil.d("===============收到桌子高度:"+height);
            }
        }

        @Override
        public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {
            LogUtil.d("===============发送消息:"+frame.toString());
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            LogUtil.d("===============连接断开");
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_desk_detail;
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
        tvProgress.setText(seekBar.getProgress()+"");
        tvTop.setText(HEIGHT_TOP+"");
        tvLow.setText(HEIGHT_BOTTOM+"");
        seekBar.setMax(HEIGHT_TOP-HEIGHT_BOTTOM);
        seekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(VerticalSeekBar verticalSeekBar, int p, boolean fromUser) {
                LogUtil.d("==============onProgressChanged:"+(HEIGHT_BOTTOM+p));
                tvProgress.setText(HEIGHT_BOTTOM+p+"");
                updateCustomButtonName();
            }

            @Override
            public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
                updateHeight = false;
            }

            @Override
            public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
//                mHandler.sendEmptyMessage(WHAT_SET);
                updateHeight = true;
                if(isOnline()){
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
            }
        });
        initSocket();
        heightList = new ArrayList<>();
        mPresenter.getDevDetail(machineBean.id+"");
    }

    private void initSocket() {
        try {
            socket = new WebSocketFactory().createSocket(Api.HOST+":5001/", 10000);
            socket.addListener(listener);
            socket.setPingInterval(30 * 1000);
            socket.connectAsynchronously();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, DeskDetailActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }
    @OnClick({R.id.tv_coulometry_desk,R.id.iv_setting_height_desk,R.id.tv_zdy1_desk,R.id.tv_zdy2_desk,R.id.tv_zdy3_desk,R.id.tv_zdy4_desk,R.id.tv_zdy5_desk,R.id.tv_switch_desk})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_right_header:
                if(bean != null)
                DeskSettingActivity.startAction(this,bean,REQ_SETTING);
                break;
            case R.id.tv_coulometry_desk:
                ElectricityStatisticsActivity.startAction(this,machineBean.id+"");
                break;
            case R.id.tv_switch_desk:
                if(isOnline()){

                }
                break;
            case R.id.tv_zdy1_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(0).height);
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
                break;
            case R.id.tv_zdy2_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(1).height);
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
                break;
            case R.id.tv_zdy3_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(2).height);
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
                break;
            case R.id.tv_zdy4_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(3).height);
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
                break;
            case R.id.tv_zdy5_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(4).height);
                    mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
                }
                break;
            case R.id.iv_setting_height_desk:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createCustomHeightDialog(this, heightList, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        updateCustomButtonName();
                        mPresenter.setDeskCustomHeightTag(machineBean.id+"",heightList);
                    }
                });
                break;
        }
    }

    private void updateCustomButtonName() {
        if(heightList == null || heightList.isEmpty()){
            return;
        }
        int curProgress = seekBar.getProgress()+HEIGHT_BOTTOM;
        int index = 0;
        for(DeskBean bean:heightList){
            MyTextView mTextView = zdyList.get(index);
            mTextView.setText(bean.name);
            mTextView.setSelected(bean.height == curProgress);
            index++;
        }
    }

    @OnTouch({R.id.btn_up_desk,R.id.btn_down_desk})
    public boolean doTouch(MotionEvent event,View view) {
        if(!isOnline()){
            return false;
        }
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            updateHeight = false;
            curMills = SystemClock.elapsedRealtime();
                switch (view.getId()){
                    case R.id.btn_up_desk:
                        delta = 1;
                        break;
                    case R.id.btn_down_desk:
                        delta = -1;
                        break;
                }
                timer2.setDelta(delta);
                timer2.start();
        }else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
            timer2.cancel();
            if(SystemClock.elapsedRealtime() - curMills <1000){
                //click
                int curProgress = seekBar.getProgress()+delta;
                if(curProgress >= 0 && curProgress <= 40){
                    seekBar.setProgress(curProgress);
                    mHandler.sendEmptyMessage(WHAT_SET);
                }
            }else{
                //stop move
                updateHeight = true;
                mPresenter.stopDeskMove(machineBean.id+"");
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void returnDevDetail(DevDetailBean bean) {
        if(bean != null){
            this.bean = bean;
            setSeekBarProgress(bean.getHeight());
            heightList.clear();
            heightList.addAll(bean.getAdjust_table_height());
            updateCustomButtonName();
            mHandler.sendEmptyMessage(WHAT_MAC);
        }
    }

    @Override
    public void returnHeiht(DeskBean bean) {
//        setSeekBarProgress(bean.height);
    }

    public class MyTimer2 extends CountDownTimer{
        boolean isFistTime = true;
        int delta = 1;
        public MyTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //设置桌子高度
            int curProgress = seekBar.getProgress()+delta;
            seekBar.setProgress(curProgress);
            if(isFistTime){
                isFistTime = false;
                mPresenter.startDeskMove(machineBean.id+"",delta > 0?true:false);
            }
            if(curProgress <=0 || curProgress >=40){
                mPresenter.stopDeskMove(machineBean.id+"");
            }else{
                start();
            }
        }
        public void setDelta(int delta){
            this.delta = delta;
            isFistTime = true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SETTING && resultCode == RESULT_OK){
            if(bean != null && data != null){
                bean.setSedentary_time(data.getStringExtra("time"));
                bean.setSedentary_reminder(data.getBooleanExtra("reminder",false));
            }
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        if(socket != null){
            socket.removeListener(listener);
            socket.disconnect();
        }
        mHandler.clean(WHAT_SET);
        mHandler.clean(WHAT_MAC);
        super.onDestroy();
    }
    private void setSeekBarProgress(int progress){
        int realProgress = -1;
        if(progress <HEIGHT_BOTTOM){
            realProgress = 0;
        }else if(progress > HEIGHT_TOP){
            realProgress = HEIGHT_TOP-HEIGHT_BOTTOM;
        }else{
            realProgress = progress-HEIGHT_BOTTOM;
        }
        if(realProgress == seekBar.getProgress()){
            tvProgress.setText(HEIGHT_BOTTOM+seekBar.getProgress()+"");
            updateCustomButtonName();
        }else{
            seekBar.setProgress(realProgress);
        }
    }

    private boolean isOnline(){
        if(machineBean != null && "ONLINE".equals(machineBean.online_status)){
            return true;
        }else{
            ToastUtil.showShort(this,"设备离线状态不能操作");
            return false;
        }
    }
}
