package com.zt.igreen.module.mine.view;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseFragment;
import com.zt.igreen.module.data.DocBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.main.frame.view.WebviewActivity;
import com.zt.igreen.module.main.widget.GlideCircleTransfrom;
import com.zt.igreen.module.message.view.MessageCenterActivity;
import com.zt.igreen.module.mine.contract.MineContract;
import com.zt.igreen.module.mine.presenter.MinePresenter;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/6/11.
 */

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.iv_avatar_mine1)
    ImageView ivAvatar1;
    @BindView(R.id.iv_avatar_mine)
    ImageView ivAvatar;
    @BindView(R.id.tv_name_mine)
    TextView tvName;
    @BindView(R.id.tv_phone_mine)
    TextView tvPhone;
    @BindView(R.id.root_header)
    FrameLayout rootHeader;
    @BindView(R.id.layout_avatar)
    LinearLayout layoutAvatar;
    @BindView(R.id.tv_setting_mine)
    TextView tvSettingMine;
    @BindView(R.id.tv_about_setting)
    TextView tvAboutSetting;
    @BindView(R.id.tv_dev_all_mine)
    TextView tvDevAllMine;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {
        hideBackButton();
        rootHeader.setBackgroundResource(R.color.qingse);
        setTitle(getString(R.string.tab_mine));
        setcolor(Color.parseColor("#ffffff"));
        setRightMenuImage(R.mipmap.ic_email);
        LoginBean bean = SPUtil.getAccount(getContext());
        updatePersonalView(bean);
    }

    private void updatePersonalView(LoginBean bean) {
        if (bean != null) {
            tvName.setText(bean.getNick_name());
//            LogUtil.d("================avatar:"+bean.getAvatar());
            tvPhone.setText(bean.getMobile());
            Glide.with(this)
                    .load(bean.getAvatar())
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(getContext()), new GlideCircleTransfrom(getContext()))
                    .dontAnimate()
                    .into(ivAvatar1);
        }
    }

    @OnClick({R.id.msg_img, R.id.iv_avatar_mine1, R.id.tv_about_data, R.id.layout_avatar, R.id.tv_setting_mine, R.id.tv_about_setting, R.id.tv_right_header, R.id.tv_dev_all_mine})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_about_data:
                PersionDataActivity.startAction(getContext());
                break;
            case R.id.msg_img:
            case R.id.tv_right_header:
                MessageCenterActivity.startAction(getContext());
                break;
            case R.id.layout_avatar:
                PersonalInfoActivity.startAction(getContext());
                break;
            case R.id.tv_setting_mine:
                SettingActivity.startAction(getContext());
                break;
            case R.id.tv_about_setting:
                mPresenter.getDoc(Constants.DOC_TYPE_ABOUT);
                // AboutActivity.startAction(getContext());
                break;
            case R.id.tv_dev_all_mine:
                AllDevActivity.startAction(getContext());
                break;
            case R.id.iv_avatar_mine1:
                PersonalInfoActivity.startAction(getContext());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPersonalInfo();
    }

    @Override
    public void returnPersonalInfo(LoginBean bean) {
        if (bean != null) {
            bean.setPwd(SPUtil.getAccount(getContext()).getPwd());
            SPUtil.saveAccount(getContext(), bean);
            updatePersonalView(bean);
        }
    }

    @Override
    public void returnDoc(DocBean bean) {
        WebviewActivity.start(getActivity(), bean.url, null, null);
    }


}
