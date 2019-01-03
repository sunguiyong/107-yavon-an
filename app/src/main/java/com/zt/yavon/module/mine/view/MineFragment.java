package com.zt.yavon.module.mine.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.data.DocBean;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.WebviewActivity;
import com.zt.yavon.module.main.widget.GlideCircleTransfrom;
import com.zt.yavon.module.message.view.MessageCenterActivity;
import com.zt.yavon.module.mine.contract.MineContract;
import com.zt.yavon.module.mine.presenter.MinePresenter;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/6/11.
 */

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View{
    @BindView(R.id.iv_avatar_mine)
    ImageView ivAvatar;
    @BindView(R.id.tv_name_mine)
    TextView tvName;
    @BindView(R.id.tv_phone_mine)
    TextView tvPhone;
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
        setTitle(getString(R.string.tab_mine));
        setRightMenuImage(R.mipmap.ic_email);
        LoginBean bean = SPUtil.getAccount(getContext());
        updatePersonalView(bean);
    }

    private void updatePersonalView(LoginBean bean) {
        if(bean != null){
            tvName.setText(bean.getNick_name());
//            LogUtil.d("================avatar:"+bean.getAvatar());
            tvPhone.setText(bean.getMobile());
            Glide.with(this)
                    .load(bean.getAvatar())
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(getContext()),new GlideCircleTransfrom(getContext()))
                    .dontAnimate()
                    .into(ivAvatar);
        }
    }

    @OnClick({R.id.layout_avatar,R.id.tv_setting_mine,R.id.tv_about_setting,R.id.tv_right_header,R.id.tv_dev_all_mine})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
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
//                AboutActivity.startAction(getContext());
                break;
            case R.id.tv_dev_all_mine:
                AllDevActivity.startAction(getContext());
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
        if(bean != null){
            bean.setPwd(SPUtil.getAccount(getContext()).getPwd());
            SPUtil.saveAccount(getContext(),bean);
            updatePersonalView(bean);
        }
    }

    @Override
    public void returnDoc(DocBean bean) {
        WebviewActivity.start(getActivity(),bean.url,null,null);
    }
}
