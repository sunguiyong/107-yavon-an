package com.zt.yavon.module.mine.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.common.base.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zt.yavon.BuildConfig;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.account.view.LoginRegisterActivity;
import com.zt.yavon.module.account.view.ResetPasswordActivity;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.widget.GlideCircleTransfrom;
import com.zt.yavon.module.mine.contract.PersonalInfoContract;
import com.zt.yavon.module.mine.presenter.PersonalInfoPresenter;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.PakageUtil;
import com.zt.yavon.utils.SPUtil;

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
//    private Uri destinationUri;
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
//                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
                    .dontAnimate()
                    .into(ivAvatar);
        }
//        File file = new File(FileUtil.getDiskCacheDir(this));
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        LogUtil.d("==================getAbsolutePath："+file.getAbsolutePath());
//        destinationUri = UriToPathUtil.getUri(this,file.getAbsolutePath()+"/tempavatar.jpg");
//        destinationUri = Uri.parse(destinationUri.toString().replace("/root",""));
//        LogUtil.d("==================destinationUri："+destinationUri.toString());
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
                    mPresenter.setAvatar(pathList.get(0));
//                    LogUtil.d("===============image path:"+pathList.get(0));
//                    startPhotoZoom(UriToPathUtil.getUri(this,pathList.get(0)),500);
//                    Glide.with(this)
//                            .load(UriToPathUtil.getUri(this,pathList.get(0)))
//                            .placeholder(R.mipmap.avatar_default)
//                            .error(R.mipmap.avatar_default)
//                            .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
//                            .dontAnimate()
//                            .into(ivAvatar);
//                    UCrop uCrop = UCrop.of(uriList.get(0), destinationUri);
//                    Intent intent = uCrop.getIntent(this);
//                    intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
//                    uCrop.withAspectRatio(1, 1)
//                            .withMaxResultSize(500, 500)
//                            .start(this);
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
    public void uploadSuccess(String filePath) {
        Glide.with(this)
                .load(filePath)
                .placeholder(R.mipmap.avatar_default)
                .error(R.mipmap.avatar_default)
                .transform(new CenterCrop(this),new GlideCircleTransfrom(this))
                .dontAnimate()
                .into(ivAvatar);
    }
}
