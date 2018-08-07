package com.zt.yavon.module.main.frame.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.common.base.utils.DensityUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.mall.MallFragment;
import com.zt.yavon.module.mine.view.MineFragment;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.widget.MyFragmentTabHost;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_CODE_ADD_ROOM = 20001;
    public static final int REQUEST_CODE_ADD_DEVICE = 20002;
    @BindView(android.R.id.tabhost)
    MyFragmentTabHost fragmentTabHost;
    public static final String texts[] = new String[3];
    private Class fragmentArray[] = {HomeFragment.class, MallFragment.class, MineFragment.class};
    private int[] imageButton = {R.drawable.selector_hometab_home, R.drawable.selector_hometab_mall, R.drawable.selector_hometab_mine};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mRxManager.post(Constants.EVENT_LOGIN_SUCCESS, 1);
    }

    @Override
    public void initView() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        texts[0] = getString(R.string.tab_main);
        texts[1] = getString(R.string.tab_mall);
        texts[2] = getString(R.string.tab_mine);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.mainContainer);
        for (int i = 0; i < texts.length; i++) {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));
            fragmentTabHost.addTab(spec, fragmentArray[i], null);
        }
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//                LogUtil.d("=============tab select:"+tabId);
//                mPresenter.getUnreadCount();
//                if(parentPageIndex == 1){
//                    if(childPageIndex == 0){
//                        parentPageIndex = 0;
//                    }else{
//                        mHandler.sendEmptyMessageDelayed(WHAT_JUMP_PAGE,500);
//                    }
//                }
            }
        });
    }

    private View getView(int i) {
//       取得布局实例
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(texts[i]);
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.tab_text_select);
        textView.setTextColor(csl);
        textView.setTextSize(11);
        Drawable drawable = getResources().getDrawable(imageButton[i]);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
        textView.setCompoundDrawablePadding(DensityUtil.dp2px(this, 3));
        textView.setPadding(0, DensityUtil.dp2px(this, 7), 0, DensityUtil.dp2px(this, 7));
        textView.setClickable(true);
//        View view = getLayoutInflater().inflate(R.layout.item_tab_home,null);
//        TextView textView = (TextView) view.findViewById(R.id.tv_tab_home);
//        textView.setText(texts[i]);
//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_home);
//        imageView.setImageResource(imageButton[i]);
        return textView;
    }
//    @OnClick({R.id.layout_switch})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.layout_switch:
//                switchAll(false);
//                break;
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD_DEVICE) {
//                List<TabBean.MachineBean> beans = (List<TabBean.MachineBean>) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
//                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                FmtDevice fmtDevice = (FmtDevice) ((FragmentPagerAdapter) fmtHome.viewPager.getAdapter()).getItem(fmtHome.viewPager.getCurrentItem());
//                fmtDevice.addData(beans);
                // TODO refresh ttt
            } else if (requestCode == REQUEST_CODE_ADD_ROOM) {
//                RoomItemBean item = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                TabItemBean bean = new TabItemBean("", item.mName, item.mCheckedResId, item.mUncheckedResId);
                fmtHome.addTab();
            }
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
