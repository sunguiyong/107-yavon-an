package com.zt.igreen.module.main.frame.view;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.common.base.utils.DensityUtil;
import com.common.base.utils.ToastUtil;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;
import com.gyf.barlibrary.ImmersionBar;
import com.qmjk.qmjkcloud.listener.OnResponseNetworkListener;
import com.qmjk.qmjkcloud.manager.QmjkNetworkManager;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.Intelligence.view.IntellFragment;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.main.frame.contract.MainContract;
import com.zt.igreen.module.main.frame.presenter.MainPresenter;
import com.zt.igreen.module.mall.view.MallFragment;
import com.zt.igreen.module.message.view.MessageListActivity;
import com.zt.igreen.module.mine.view.MineFragment;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.SPUtil;
import com.zt.igreen.widget.MyFragmentTabHost;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_CODE_ADD_ROOM = 20001;
    public static final int REQUEST_CODE_ADD_DEVICE = 20002;
    @BindView(android.R.id.tabhost)
    MyFragmentTabHost fragmentTabHost;
    public static final String texts[] = new String[4];
    private Class fragmentArray[] = {HomeFragment.class, MallFragment.class, IntellFragment.class, MineFragment.class};
    private int[] imageButton = {R.drawable.selector_hometab_home, R.drawable.selector_hometab_mall, R.drawable.selector_hometab_intell, R.drawable.selector_hometab_mine};
    private String selectTab;
    private long lastBack;
    private GizWifiSDK gizWifiSDK;
    private List<GizWifiDevice> wifidevices = new ArrayList<>();
    private QmjkNetworkManager mNetworkManager;
    private LoginBean beans;

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
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
        gizWifiSDK = GizWifiSDK.sharedInstance();
        wifidevices = gizWifiSDK.getDeviceList();
        gizWifiSDK.setListener(mListener_user);
        gizWifiSDK.userLoginAnonymous();
        gizWifiSDK.setListener(mListener1);

        parseIntentData();
        ImmersionBar.with(this)
                .statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        // setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        texts[0] = getString(R.string.tab_main);
        texts[1] = getString(R.string.tab_mall);
        texts[2] = getString(R.string.zhineng);
        texts[3] = getString(R.string.tab_mine);

        selectTab = texts[0];
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.mainContainer);
        for (int i = 0; i < texts.length; i++) {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));
            fragmentTabHost.addTab(spec, fragmentArray[i], null);
        }
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//                LogUtil.d("=============tab select:" + tabId);
                selectTab = tabId;
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
            onRefresh();
//            if (requestCode == REQUEST_CODE_ADD_DEVICE) {
//                List<TabBean.MachineBean> beans = (List<TabBean.MachineBean>) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
//                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                FmtIntellDevice fmtDevice = (FmtIntellDevice) ((FragmentPagerAdapter) fmtHome.viewPager.getAdapter()).getItem(fmtHome.viewPager.getCurrentItem());
//                fmtDevice.addData(beans);
//                onRefresh();
            // TODO refresh ttt
//            } else if (requestCode == REQUEST_CODE_ADD_ROOM) {
//                RoomItemBean item = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
//                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                TabItemBean bean = new TabItemBean("", item.mName, item.mCheckedResId, item.mUncheckedResId);
//                fmtHome.addTab();
//                onRefresh();
//                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                fmtHome.refreshData();
//            } else if (requestCode == REQUEST_CODE_ADD_ROOM) {
//                HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
//                fmtHome.refreshData();
//            }
        }
    }

    public void onRefresh() {
        mRxManager.post(Constants.EVENT_REFRESH_HOME, 0);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startAction(Context context, String type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("type", type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        HomeFragment fmtHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag(texts[0]);
        if (fmtHome != null && fmtHome.isVisible()) {
            if (fmtHome.fmts != null) {
                FmtDevice fmtDev = (FmtDevice) fmtHome.fmts.get(fmtHome.viewPager.getCurrentItem());
                if (fmtDev != null && fmtDev.isMenuShown()) {
                    fmtDev.exitMultiSelectMode();
                    return;
                }
            }
        }
        long curMilSec = SystemClock.elapsedRealtime();
        if (curMilSec - lastBack > 1000) {
            lastBack = curMilSec;
            ToastUtil.showShort(this, "再按一次退出应用");
            return;
        }
        super.onBackPressed();
    }

    GizWifiSDKListener mListener_user = new GizWifiSDKListener() {
        @Override
        public void didUserLogin(GizWifiErrorCode result, String uid, String token) {
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {

            } else {

            }
        }
    };
    GizWifiSDKListener mListener1 = new GizWifiSDKListener() {
        @Override
        public void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
            super.didDiscovered(result, deviceList);
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                for (GizWifiDevice gizWifiDevice : deviceList) {
                    wifidevices.add(gizWifiDevice);
                }
            } else {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntentData();
    }

    private void parseIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
//        LogUtil.d("====jpush,onNewIntent");
        String type = intent.getStringExtra("type");
        if (TextUtils.isEmpty(type)) {
            return;
        }
//        LogUtil.d("====jpush,parseIntentData,type:"+type);
        switch (type) {// type(消息类型，SYSTEM系统消息、INSIDE内部消息、FAULT故障消息、SHARE共享消息)
            case "SYSTEM":
                MessageListActivity.startAction(this, MessageListActivity.TYPE_SYS);
                break;
            case "SHARE":
                MessageListActivity.startAction(this, MessageListActivity.TYPE_SHARE);
                break;
            case "FAULT":
                MessageListActivity.startAction(this, MessageListActivity.TYPE_ERROR);
                break;
            case "INSIDE":
                MessageListActivity.startAction(this, MessageListActivity.TYPE_INTERNAL);
                break;
        }
    }
}
