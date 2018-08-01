package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.common.base.utils.LogUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.share.view.ApplyDevActivity;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.PakageUtil;

import butterknife.BindView;

public class ScanCodeActivity extends BaseActivity {
    public static final int TYPE_ADD_LOCK = 1;
    public static final int TYPE_APPLY_DEV = 4;

    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    private Dialog dialog;
    private CaptureFragment captureFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
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
                            PakageUtil.startAppSettings(ScanCodeActivity.this);
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
            LogUtil.d("============result:"+result);
            if(!TextUtils.isEmpty(result)){
                if(result.startsWith("yisuobao://add")){//yisuobao://add/b47bRunwNufjUuHXzODbJwiLMSmYtp1u
                    //绑定电池琐
                }else{
                    ApplyDevActivity.startAction(ScanCodeActivity.this,result,result);
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
