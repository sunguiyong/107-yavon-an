package com.zt.igreen.module.main.frame.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizWifiDeviceListener;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;
import com.google.gson.Gson;
import com.qmjk.qmjkcloud.manager.QmjkNetworkManager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.igreen.R;
import com.zt.igreen.baidumap.LocationService;
import com.zt.igreen.component.BaseFragment;
import com.zt.igreen.component.LeakSafeHandler;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.data.VersionBean;
import com.zt.igreen.module.data.WeatherBean;
import com.zt.igreen.module.data.XinfengjiBean;
import com.zt.igreen.module.deviceconnect.view.DeviceAddActivity;
import com.zt.igreen.module.deviceconnect.view.ScanCodeActivity;
import com.zt.igreen.module.main.frame.contract.HomeContract;
import com.zt.igreen.module.main.frame.presenter.HomePresenter;
import com.zt.igreen.module.main.roommanager.list.view.RoomActivity;
import com.zt.igreen.module.main.widget.MenuWidget;
import com.zt.igreen.module.message.view.MessageListActivity;
import com.zt.igreen.module.update.DownloadObserver;
import com.zt.igreen.module.update.UpdateUtils;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.utils.PackageUtil;
import com.zt.igreen.utils.SPUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2018/6/11.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.sliding_tab_layout)
    public SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.swipe_home)
    public SwipeRefreshLayout refreshLayout;
    MenuWidget mMenuWidget;
    Unbinder unbinder;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_count_msg)
    TextView tvMsgCount;
    /* @BindView(R.id.tv_air_weather)
     TextView tvAir;*/
    @BindView(R.id.tv_tmp_weather)
    TextView tvTmp;
    @BindView(R.id.tv_con_weather)
    TextView tvCon;
    @BindView(R.id.iv_adding)
    LinearLayout ivadding;
    @BindView(R.id.tv_Pm25)
    TextView tvPm25;
    @BindView(R.id.tv_co2)
    TextView tvCo2;
    @BindView(R.id.layout_msg)
    FrameLayout layoutMsg;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_roomnei)
    TextView tvRoomnei;
    @BindView(R.id.tv_roomwai)
    TextView tvRoomwai;
    @BindView(R.id.lin_roomnei)
    LinearLayout linRoomnei;
    @BindView(R.id.lin_roomwai)
    LinearLayout linRoomwai;
    private LinearLayoutManager layoutManager;
    private int curPage = 1;

    public List<TabBean> mTabData;
    public ArrayList<Fragment> fmts;
    private MainActivity mActivity;
    private Dialog dialog;
    private DownloadObserver observer;
    WeatherBean bean;
    private LeakSafeHandler<HomeFragment> mHandler = new LeakSafeHandler<HomeFragment>(this) {
        @Override
        public void onMessageReceived(HomeFragment mActivity, Message msg) {
            if (msg.what == DownloadObserver.WHAT_PROGRESS) {
                if (msg.arg1 >= 100 && mActivity.dialog != null) {
                    DialogUtil.dismiss(mActivity.dialog);
                }
                if (mActivity.tvProgress != null) {
                    mActivity.tvProgress.setText("正在下载：" + msg.arg1 + "%");
                }
                if (mActivity.progressBar != null) {
                    mActivity.progressBar.setProgress(msg.arg1);
                }
            }
        }
    };
    ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    private TextView tvProgress;
    private ProgressBar progressBar;
    private LocationService locationService;
    private String city;
    private GizWifiSDK gizWifiSDK;
    GizWifiDevice mDevice_two = null;
    GizWifiDevice mDevice = null;
    private final int GET_WEhTER = 1;
    private final int GET_WEhData = 2;
    private List<GizWifiDevice> wifidevices = new ArrayList<>();
    private ConcurrentHashMap<String, String> appInfo;
    private List<ConcurrentHashMap<String, String>> productInfo;
    private ConcurrentHashMap<String, String> product;
    String [] str=new String[5];
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_WEhTER:
                    Gson gson = new Gson();
                    XinfengjiBean bean = gson.fromJson(gson.toJson(map), XinfengjiBean.class);
                    int ico2 = bean.getData().getIco2();
                    str[0]=String.valueOf(ico2);

                    int itm = bean.getData().getItm();
                    str[1]=String.valueOf(itm);
                    int irh = bean.getData().getIrh();
                    str[2]=String.valueOf(irh);

                    int ipm2d5 = bean.getData().getIpm2d5();
                    str[3]=String.valueOf(ipm2d5);

                    EventBus.getDefault().post(new XinfengjiBean.DataBean(itm,ipm2d5,ico2,irh));
                    break;
                case GET_WEhData:

                    if (wifidevices != null) {

                        mDevice = wifidevices.get((wifidevices.size() - 1));
                        mDevice.setSubscribe(true);
                        mDevice.setListener(mListener_dingyue);
                    }
                    break;

            }
        }
    };

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mRxManager.on(Constants.EVENT_REFRESH_HOME, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.getTabData(false);
            }
        });
        mRxManager.on(Constants.EVENT_MSG_COUNT_UPDATE, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if ((Integer) o == MessageListActivity.TYPE_INTERNAL) {
                    mPresenter.getInternalMsgUnreadCount();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gizWifiSDK = GizWifiSDK.sharedInstance();
        appInfo = new ConcurrentHashMap<>();
        appInfo.put("appId", "cc8ea48ea06b4893a7ce3ab8bc3b5133");
        appInfo.put("appSecret", "184eb86a5a774ad08547792985252b8a");
        productInfo = new ArrayList<>();
        product = new ConcurrentHashMap<>();
        product.put("productKey", "4ffef0ef18604b719ef4fff9ca3212a3");
        product.put("productSecret", "d8fe6dff9efe4fe7a3eb701bf01ee206");
        productInfo.add(product);
       // gizWifiSDK.setListener(basemListener);
        gizWifiSDK.startWithAppInfo(getActivity(), appInfo, productInfo, null, false);
        wifidevices = gizWifiSDK.getDeviceList();
        gizWifiSDK.setListener(mListener_user);
        gizWifiSDK.userLoginAnonymous();
        gizWifiSDK.setListener(mListener1);
    }

    @Override
    protected void initView() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary1);
        refreshLayout.setOnRefreshListener(this);
        ivadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startActForResult(RoomActivity.class, MainActivity.REQUEST_CODE_ADD_ROOM, (Serializable) mTabData);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((FmtDevice) fmts.get(position)).exitMultiSelectMode();
                for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                    String resUrl = (i == position ? mTabData.get(i).icon_select : mTabData.get(i).icon);
                    int finalI = i;
                    Glide.with(getActivity()).load(resUrl).asBitmap().
                            into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                                    drawable.setBounds(0, 0, 70, 70);
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawables(null, drawable, null, null);
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(16);
                                }
                            });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mActivity = (MainActivity) getActivity();
        mMenuWidget = mActivity.findViewById(R.id.menu_widget);
        mActivity.findViewById(R.id.menu_recent).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_move).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_rename).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_share).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_more).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_del).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_report).setOnClickListener(mMenuWidget);
        mMenuWidget.setOnItemClickListener(new MenuWidget.OnItemClickListener() {
            @Override
            public void onRecentClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onRecentClick();
            }

            @Override
            public void onMoveClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onMoveClick();
            }

            @Override
            public void onRenameClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onRenameClick();
            }

            @Override
            public void onShareClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onShareClick();
            }

            @Override
            public void onDelClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onDelClick();
            }

            @Override
            public void onReportClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onReportClick();
            }
        });
        initPermission();
        mPresenter.loginTuYa();
        mPresenter.getTabData(true);
        mPresenter.getInternalMsgUnreadCount();
        mPresenter.getVersion();
    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION,Permission.Group.STORAGE, Permission.Group.STORAGE)
                .onGranted(permissions -> {
//                    LocationUtil locationUtil = new LocationUtil(getActivity());
//                    locationUtil.setListener(new LocationUtil.LocationChangedListener() {
//                        @Override
//                        public void onLocationChanged(String location) {
//                            mPresenter.getCity(location);
//                        }
//                    });
//                    locationUtil.getLocation();
                    //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
                    startLocation();
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:" + Arrays.toString(permissions.toArray()));
                    dialog = DialogUtil.create2BtnInfoDialog(getActivity(), "需要蓝牙和定位权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            PackageUtil.startAppSettings(getActivity());
                        }
                    });
                })
                .start();
    }

    private void initPermission2(VersionBean bean) {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    showDownloadProgressDialog(bean.is_force);
                    UpdateUtils.update(getContext(), bean.version, bean.url, bean.is_force, dialog, new UpdateUtils.DateChangeObserver() {
                        @Override
                        public void registerDateChangeObserver(long downloadId) {
                            observer = new DownloadObserver(getContext(), downloadId, mHandler);
                            getContext().getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, observer);
                        }
                    });
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:" + Arrays.toString(permissions.toArray()));
                    dialog = DialogUtil.create2BtnInfoDialog(getActivity(), "需要SD写入权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            PackageUtil.startAppSettings(getActivity());
                        }
                    });
                })
                .start();
    }

    private int mSelectIndex = 0;

    @Override
    public void returnTabData(List<TabBean> data) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (data == null || data.isEmpty()) {
            ToastUtil.showShort(getContext(), "数据加载失败，请重试！");
            return;
        }
        mSelectIndex = viewPager.getCurrentItem();
        mTabData = data;
        if (viewPager.getAdapter() != null) {
            FragmentManager fm = mActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                Fragment item = ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(i);
                ft.remove(item);
            }
            ft.commitNowAllowingStateLoss();
        }

        if (fmts == null) {
            fmts = new ArrayList<>();
        } else if (fmts.size() > 0) {
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//            for(Fragment fragment:fmts){
//                transaction.remove(fragment);
//            }
//            transaction.commitNowAllowingStateLoss();
            fmts.clear();
        }
        String[] titles = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            String showName = data.get(i).name;
            if (showName.length() > 3) {
                showName = showName.substring(0, 3) + "...";
            }
            titles[i] = showName;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN, data.get(i));
            Fragment fmt = Fragment.instantiate(getActivity(), FmtDevice.class.getName(), bundle);
//            Fragment fmt = new FmtIntellDevice();
//            fmt.setArguments(bundle);
            fmts.add(fmt);
        }
        slidingTabLayout.setViewPager(viewPager, titles, getActivity(), fmts);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(mSelectIndex);
            }
        });
        for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
            String resUrl = (i == mSelectIndex ? mTabData.get(i).icon_select : mTabData.get(i).icon);
            int textColor = i == mSelectIndex ? Color.parseColor("#9D9D9D") : Color.parseColor("#8B8B8B");
            int finalI = i;
            Glide.with(getActivity()).load(resUrl).asBitmap().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                            drawable.setBounds(0, 0, 70, 70);
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawables(null, drawable, null, null);
                            // slidingTabLayout.getTitleView(finalI).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(16);
                            slidingTabLayout.getTitleView(finalI).setTextColor(textColor);
                        }
                    });
        }
    }

    @Override
    public void errorTabData(String message) {
        ToastUtil.showLong(getActivity(), message);
    }

    @Override
    public void returnVersion(VersionBean bean) {
        if (bean != null) {
            if (PackageUtil.compareVersion(bean.version, PackageUtil.getAppVersion(getContext())) > 0) {
                //有更新
                dialog = DialogUtil.createUpdateDialog(getActivity(), "发现新版本：" + bean.version, !bean.is_force, "以后再说", "立即更新", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        initPermission2(bean);
                    }
                });
            }
        }
    }

    @Override
    public void unreadMsgCount(int count) {
        if (count > 99) {
            tvMsgCount.setVisibility(View.VISIBLE);
            tvMsgCount.setText("99+");
            tvMsgCount.setTextSize(7.5f);
        } else if (count > 0) {
            tvMsgCount.setVisibility(View.VISIBLE);
            tvMsgCount.setText(count + "");
            tvMsgCount.setTextSize(9);
        } else {
            tvMsgCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateWeather(WeatherBean bean) {
        this.bean = bean;
        List<WeatherBean.HeWeather> list = bean.result.HeWeather5;
        if (list != null && !list.isEmpty()) {
            WeatherBean.HeWeather weather = list.get(0);
            tvCon.setText(weather.now.tmp + "");
            tvTmp.setText(weather.now.hum + "");
            tvPm25.setText(weather.aqi.city.pm25 + "");
            tvCo2.setText(weather.aqi.city.co + "");
        }
    }




    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.tv_roomnei, R.id.tv_roomwai, R.id.iv_scan, R.id.iv_add, R.id.layout_msg, R.id.title_ok, R.id.title_select_all})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_roomwai:
                if (str[0]!=null){
                    tvCo2.setText(str[0] + "");
                } else{
                    tvCo2.setText("");
                }
                if (str[1]!=null){
                    tvCon.setText(str[1] + "");
                }else {
                    tvCon.setText("");
                }
                if (str[2]!=null) {
                    tvTmp.setText(str[2] + "");
                }else {
                    tvTmp.setText("");
                }
                if (str[3]!=null){
                    tvPm25.setText(str[3] + "");
                }else {
                    tvPm25.setText("");
                }
                linRoomnei.setVisibility(View.GONE);
                linRoomwai.setVisibility(View.VISIBLE);
                tvRoomnei.setTextColor(Color.parseColor("#C4DC95"));
                tvRoomwai.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.tv_roomnei:
                tvRoomwai.setTextColor(Color.parseColor("#C4DC95"));
                tvRoomnei.setTextColor(Color.parseColor("#ffffff"));
                linRoomnei.setVisibility(View.VISIBLE);
                linRoomwai.setVisibility(View.GONE);
                List<WeatherBean.HeWeather> list = bean.result.HeWeather5;
                if (list != null && !list.isEmpty()) {
                    WeatherBean.HeWeather weather = list.get(0);
                    tvCon.setText(weather.now.hum + "");
                    tvTmp.setText(weather.now.tmp + "");
                    tvPm25.setText(weather.aqi.city.pm25 + "");
                    tvCo2.setText(weather.aqi.city.co + "");
                    // tvAir.setText("室外空气 "+weather.aqi.city.qlty);
                }
                break;
            case R.id.iv_scan:
                ScanCodeActivity.start(getActivity());
                break;
            case R.id.iv_add:
                DeviceAddActivity.start(getActivity());
                break;
            case R.id.layout_msg:
                MessageListActivity.startAction(getActivity(), MessageListActivity.TYPE_INTERNAL);
                break;
            case R.id.title_ok:
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onSelectCompleteClick();
                break;
            case R.id.title_select_all:
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onSelectAllClick();
                break;
        }
    }


    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(city)) {
            mPresenter.getWeather(city);
        } else {
            initPermission();
        }
        mPresenter.getTabData(false);
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismiss(dialog);
        mHandler.clean(DownloadObserver.WHAT_PROGRESS);
        if (observer != null) {
            getContext().getContentResolver().unregisterContentObserver(observer);
        }
        if (mDevice!=null){
        mDevice.setSubscribe(false);
        mDevice.isDisabled();}
        handler.removeMessages(GET_WEhData);
        handler.removeMessages(GET_WEhTER);
        super.onDestroy();
    }

    /**
     * 下载进度提示框
     *
     * @param isForce
     */
    private void showDownloadProgressDialog(final boolean isForce) {
        DialogUtil.dismiss(dialog);
        View view = getLayoutInflater().inflate(R.layout.layout_progress_download, null);
        tvProgress = (TextView) view.findViewById(R.id.tv_progress_download);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_download);
        dialog = new Dialog(getContext(), R.style.CustomProgressDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.88);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (observer != null) {
                    getContext().getContentResolver().unregisterContentObserver(observer);
                }
                tvProgress = null;
                progressBar = null;
                if (isForce)
                    getActivity().finish();
            }
        });
        tvProgress.setText("正在下载：0%");
        progressBar.setProgress(1);
        dialog.show();
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            LogUtil.d("==========result:" + location.getLocType());
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                city = location.getCity();

                if (!TextUtils.isEmpty(city)) {
                    SPUtil.putLocation(getContext(), "loaction", city);
                    mPresenter.getWeather(city);
                }
                locationService.unregisterListener(this);
                locationService.stop();
                locationService = null;
            }
        }

    };
    GizWifiSDKListener basemListener = new GizWifiSDKListener() {
        @Override
        public void didRegisterUser(GizWifiErrorCode result, String uid, String token) {
            super.didRegisterUser(result, uid, token);
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {

            } else {

            }
        }
    };
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
            wifidevices.clear();
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                for (GizWifiDevice gizWifiDevice : deviceList) {
                    wifidevices.add(gizWifiDevice);
                }
                handler.sendEmptyMessageDelayed(GET_WEhData, 2000);

            } else {
            }

        }
    };
    // 实现回调
    GizWifiDeviceListener mListener_dingyue = new GizWifiDeviceListener() {
        @Override
        public void didSetSubscribe(GizWifiErrorCode result, GizWifiDevice device, boolean isSubscribed) {
            super.didSetSubscribe(result, device, isSubscribed);
            Log.e("xuxinyi3335",result+"");
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                mDevice_two = device;
                mDevice_two.setListener(mListener_xianshi);
            }
        }
    };
    // 实现回调
    GizWifiDeviceListener mListener_xianshi = new GizWifiDeviceListener() {
        @Override
        public void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> hashMap, int sn) {
            super.didReceiveData(result, device, hashMap, sn);
            Log.e("xuxinyi336",result+"");
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS && hashMap != null) {
                Log.e("xuxinyi336", hashMap.toString());
                map = hashMap;
                handler.sendEmptyMessage(GET_WEhTER);
            }
        }
    };

    private void startLocation() {
        // -----------location config ------------
        locationService = new LocationService(getActivity().getApplicationContext());
        locationService.registerListener(mListener);
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDevice!=null){
        mDevice.setSubscribe(false);
        mDevice.isDisabled();}
        handler.removeMessages(GET_WEhData);
        handler.removeMessages(GET_WEhTER);
    }


}
