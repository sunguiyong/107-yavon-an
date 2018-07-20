package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.FrameLayout;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.share.view.ApplyDevActivity;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;

public class ScanCodeActivity extends BaseActivity {


    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initPermission();
        setTitle("扫描二维码");
    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(permissions -> {
                    setTitle("扫描二维码");
                    initcamera();
                })
                .onDenied(permissions -> {
                    setTitle("申请设备");
                    DialogUtil.create2BtnInfoDialog(ScanCodeActivity.this, getString(R.string.scan_permission), "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            initPermission();
                        }
                    });
                })
                .start();
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ScanCodeActivity.class);
        activity.startActivity(intent);
    }

    private void initcamera() {
        CaptureFragment captureFragment = new CaptureFragment();
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
            Log.e("result", result);
//            DeviceTypeActivity.start(ScanCodeActivity.this, DeviceTypeActivity.BATTERY_LOCK);
            ApplyDevActivity.startAction(ScanCodeActivity.this);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {

        }
    };

}
