package com.zt.igreen.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.common.base.utils.LogUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.DevTypeBean;
import com.zt.igreen.module.device.share.view.ApplyDevActivity;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.utils.PackageUtil;

import butterknife.BindView;

public class ScanCodeActivity extends BaseActivity {
    public static final int TYPE_ADD_LOCK = 1;
    public static final int TYPE_APPLY_DEV = 4;

    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    private Dialog dialog;
    private CaptureFragment captureFragment;
    private DevTypeBean.TYPE typeData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initPresenter() {
        typeData = (DevTypeBean.TYPE) getIntent().getSerializableExtra("typeData");
    }

    @Override
    public void initView() {
        sethead(R.color.touming);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("扫描二维码");
        initPermission();
    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(permissions -> {
                    initcamera();
                })
                .onDenied(permissions -> {
                    dialog = DialogUtil.create2BtnInfoDialog(ScanCodeActivity.this, getString(R.string.scan_permission), "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            PackageUtil.startAppSettings(ScanCodeActivity.this);
                        }
                    });
                })
                .start();
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ScanCodeActivity.class);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, DevTypeBean.TYPE typeData) {
        Intent intent = new Intent(activity, ScanCodeActivity.class);
        intent.putExtra("typeData", typeData);
        activity.startActivity(intent);
    }

    private void initcamera() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);

        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            LogUtil.d("============result:" + result);
            if (!TextUtils.isEmpty(result)) {
                if (result.startsWith("yisuobao://add") && typeData != null) {//yisuobao://add/b47bRunwNufjUuHXzODbJwiLMSmYtp1u
                    //绑定电池琐
//                    if(typeData != null){
                    typeData.sn = result;
                    Log.e("xuxinyimacres", result + "");
                    DeviceTypeActivity.start(ScanCodeActivity.this, typeData);
//                    }else{
//                        ApplyDevActivity.startAction(ScanCodeActivity.this,result,result);
//                    }
                } else if (typeData != null) {
                    if ("WATER_DISPENSER".equals(typeData.type) || "NFC_COASTER".equals(typeData.type)) {
                        typeData.sn = result;
                        DeviceTypeActivity.startAction(ScanCodeActivity.this, typeData, result, result);
                    }
                } else {
                    ApplyDevActivity.startAction(ScanCodeActivity.this, result, result, "","1");
                }
            }
            finish();
        }

        @Override
        public void onAnalyzeFailed() {

        }
    };

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
