package com.zt.yavon.module.mine.view;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zt.yavon.BuildConfig;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.account.view.LoginRegisterActivity;
import com.zt.yavon.module.account.view.ResetPasswordActivity;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.module.main.widget.GlideCircleTransfrom;
import com.zt.yavon.module.mine.contract.PersonalInfoContract;
import com.zt.yavon.module.mine.presenter.PersonalInfoPresenter;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.FileUtil;
import com.zt.yavon.utils.ImageUtils;
import com.zt.yavon.utils.PakageUtil;
import com.zt.yavon.utils.SPUtil;
import com.zt.yavon.utils.UriToPathUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements PersonalInfoContract.View{
    @BindView(R.id.iv_avatar_info)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname_info)
    TextView tvNickName;
    @BindView(R.id.tv_email_info)
    TextView tvEmail;
    @BindView(R.id.tv_phone_info)
    TextView tvPhone;
    @BindView(R.id.tv_account_info)
    TextView tvAccount;
    @BindView(R.id.switch_email_info)
    SwitchCompat switchEmail;
    private Dialog dialog;
    private final int REQ_EMAIL = 0x10;
    private final int REQ_PHONE = 0x20;
    private final int REQ_CHOOSE = 0x30;
    private final int REQ_CROP_PHOTO = 0x40;
    private File cacheFile;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.personal_info));
        LoginBean bean = SPUtil.getAccount(this);
        if(bean != null){
            tvAccount.setText(bean.getAccount());
            tvNickName.setText(bean.getNick_name());
            tvPhone.setText(bean.getMobile());
            String email = bean.getEmail();
            if(TextUtils.isEmpty(email)){
                switchEmail.setChecked(false);
            }else{
                tvEmail.setText(email);
                switchEmail.setVisibility(View.GONE);
            }
            Glide.with(this)
                    .load(bean.getAvatar())
                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
                    .dontAnimate()
                    .into(ivAvatar);
        }
        cacheFile = new File(FileUtil.getDiskCacheDir(this),"tempavatar.jpg");
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,PersonalInfoActivity.class);
        context.startActivity(intent);
    }
    @OnClick({R.id.iv_avatar_info,R.id.iv2_avatar_info,R.id.tv_nickname_info,R.id.tv_email_info,R.id.key_email_info,R.id.tv_phone_info,R.id.tv_pwd_info,R.id.btn_exit_info})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }
    @OnCheckedChanged(R.id.switch_email_info)
    public void onCheckedChanged(boolean isChecked) {
        if(isChecked && TextUtils.isEmpty(tvEmail.getText().toString())){
            BindEmailActivity.startAction(this,REQ_EMAIL);
        }
    }
    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.iv_avatar_info://修改头像
            case R.id.iv2_avatar_info:
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.STORAGE,Permission.Group.CAMERA)
                        .onGranted(permissions -> {
                            choosePic();
                        })
                        .onDenied(permissions -> {
                            // Storage permission are not allowed.
                            DialogUtil.dismiss(dialog);
                            String msg = "";
                            if(permissions.contains(Permission.Group.STORAGE[0]) || permissions.contains(Permission.Group.STORAGE[1])){
                                msg = "缺少储存卡读写权限,立即去授权?";
                            }else if(permissions.contains(Permission.Group.CAMERA[0])){
                                msg = "缺少相机权限,立即去授权?";
                            }
                            dialog = DialogUtil.create2BtnInfoDialog(this, msg, null, null, new DialogUtil.OnComfirmListening() {
                                @Override
                                public void confirm() {
                                    PakageUtil.startAppSettings(PersonalInfoActivity.this);
                                }
                            });
                        })
                        .start();

                break;
            case R.id.key_email_info://绑定邮箱
            case R.id.tv_email_info:
                if(!TextUtils.isEmpty(tvEmail.getText().toString())){
                    ModityEmailActivity.startAction(this,REQ_EMAIL);
                }
                break;
            case R.id.tv_phone_info://绑定手机号
                ModityPhoneActivity.startAction(this,REQ_PHONE);
                break;
            case R.id.tv_pwd_info://修改密码
                ResetPasswordActivity.start(this,"");
                break;
            case R.id.tv_nickname_info://修改昵称
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createNickNameDialog(this, tvNickName.getText().toString(), new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        mPresenter.modifyNickname(data);
                    }
                });
                break;
            case R.id.btn_exit_info://注销
//                SPUtil.clearPreferences(this);
                LoginRegisterActivity.start(this,"login");
                mRxManager.post(Constants.EVENT_ERROR_TOKEN,"");
                break;
        }
    }
    private void choosePic(){
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileprovider"))
                .showSingleMediaType(true)
//                .countable(true)
//                .maxSelectable(1)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dark)
                .imageEngine(new GlideEngine())
                .forResult(REQ_CHOOSE);
        // Storage permission are allowed.
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQ_EMAIL:
                    if(data == null || TextUtils.isEmpty(data.getStringExtra("email"))){
                        if(TextUtils.isEmpty(tvEmail.getText().toString())){
                            switchEmail.setVisibility(View.VISIBLE);
                            switchEmail.setChecked(false);
                        }
                    }else{
                        switchEmail.setChecked(false);
                        switchEmail.setVisibility(View.GONE);
                        tvEmail.setText(data.getStringExtra("email"));
                    }
                    break;
                case REQ_PHONE:
                    if(data != null && !TextUtils.isEmpty(data.getStringExtra("phone"))){
                        tvPhone.setText(data.getStringExtra("phone"));
                    }
                    break;
                case REQ_CHOOSE:
//                    List<Uri> uriList = Matisse.obtainResult(data);
                    List<String> pathList = Matisse.obtainPathResult(data);
//                    mPresenter.setAvatar(pathList.get(0));
                    LogUtil.d("===============image path:"+pathList.get(0));
                    startPhotoZoom(UriToPathUtil.getUri(this,pathList.get(0)),500);
                    Glide.with(this)
                            .load(UriToPathUtil.getUri(this,pathList.get(0)))
                            .placeholder(R.mipmap.avatar_default)
                            .error(R.mipmap.avatar_default)
                            .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
                            .dontAnimate()
                            .into(ivAvatar);
                    break;
                case REQ_CROP_PHOTO:
                    LogUtil.d("===============cacheFile path:"+cacheFile.getAbsolutePath());
                    mPresenter.setAvatar(cacheFile);
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }

    @Override
    public void returnPersonalInfo(LoginBean bean) {

    }

    @Override
    public void modifyNickNameSuccess(String nickName) {
        ToastUtil.showShort(this,"修改成功");
        tvNickName.setText(nickName);
    }

    @Override
    public void uploadSuccess(File cacheFile) {
        Glide.with(this)
                .load(cacheFile)
                .placeholder(R.mipmap.avatar_default)
                .error(R.mipmap.avatar_default)
                .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
                .dontAnimate()
                .into(ivAvatar);
    }
    /**
     * 剪裁图片
     */
    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,UriToPathUtil.getUri(this,cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, REQ_CROP_PHOTO);
            LogUtil.d("===============startActivityForResult:"+cacheFile.getAbsolutePath());
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
