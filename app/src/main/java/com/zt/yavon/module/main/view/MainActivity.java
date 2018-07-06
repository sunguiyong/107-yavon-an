package com.zt.yavon.module.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.common.base.utils.DensityUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.mine.MineFragment;
import com.zt.yavon.widget.MyFragmentTabHost;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(android.R.id.tabhost)
    MyFragmentTabHost fragmentTabHost;
    private String texts[] = new String[2];
    private Class fragmentArray[] = {HomeFragment.class,MineFragment.class};
    private int[] imageButton = {R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        texts[0] = getString(R.string.tab_main);
        texts[1] = getString(R.string.tab_mine);
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
//        ColorStateList csl=(ColorStateList)getResources().getColorStateList(R.color.tab_text_select);
//        textView.setTextColor(Co);
        textView.setTextSize(11);
        Drawable drawable= getResources().getDrawable(imageButton[i]);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null,drawable,null,null);
        textView.setCompoundDrawablePadding(DensityUtil.dp2px(this,3));
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

    public static void startAction(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
