package com.zt.yavon.module.main.frame.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.yavon.MyApplication;
import com.zt.yavon.R;
import com.zt.yavon.baidumap.LocationService;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.data.VersionBean;
import com.zt.yavon.module.data.WeatherBean;
import com.zt.yavon.module.deviceconnect.view.DeviceAddActivity;
import com.zt.yavon.module.deviceconnect.view.ScanCodeActivity;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.module.main.frame.presenter.HomePresenter;
import com.zt.yavon.module.main.roommanager.list.view.RoomActivity;
import com.zt.yavon.module.main.widget.MenuWidget;
import com.zt.yavon.module.message.view.MessageListActivity;
import com.zt.yavon.module.update.DownloadObserver;
import com.zt.yavon.module.update.UpdateUtils;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.LocationUtil;
import com.zt.yavon.utils.PackageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

import static com.tuya.smart.sdk.TuyaSdk.getApplication;

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
    @BindView(R.id.tv_air_weather)
    TextView tvAir;
    @BindView(R.id.tv_tmp_weather)
    TextView tvTmp;
    @BindView(R.id.tv_con_weather)
    TextView tvCon;
    Unbinder unbinder1;
    private LinearLayoutManager layoutManager;
    private int curPage = 1;

    public List<TabBean> mTabData;
    public ArrayList<Fragment> fmts;
    private MainActivity mActivity;
    private Dialog dialog;
    private DownloadObserver observer;
    private LeakSafeHandler<HomeFragment> mHandler = new LeakSafeHandler<HomeFragment>(this) {
        @Override
        public void onMessageReceived(HomeFragment mActivity, Message msg) {
            if(msg.what == DownloadObserver.WHAT_PROGRESS){
                if(msg.arg1 >= 100 && mActivity.dialog != null){
                    DialogUtil.dismiss(mActivity.dialog);
                }
                if(mActivity.tvProgress != null){
                    mActivity.tvProgress.setText("正在下载："+msg.arg1+"%");
                }
                if(mActivity.progressBar != null){
                    mActivity.progressBar.setProgress(msg.arg1);
                }
            }
        }
    };
    private TextView tvProgress;
    private ProgressBar progressBar;
    private LocationService locationService;
    private String city;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

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
                if((Integer)o == MessageListActivity.TYPE_INTERNAL){
                    mPresenter.getInternalMsgUnreadCount();
                }
            }
        });
    }

    @Override
    protected void initView() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        ivSetting.setOnClickListener(new View.OnClickListener() {
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
                                    drawable.setBounds(0, 0, 60, 60);
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
                .permission(Permission.Group.LOCATION,Permission.Group.STORAGE)
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
                    LogUtil.d("=========denied permissions:"+ Arrays.toString(permissions.toArray()));
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
                            getContext().getContentResolver().registerContentObserver(Uri.parse("content://downloads/"),true,observer);
                        }
                    });
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:"+ Arrays.toString(permissions.toArray()));
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
//            Fragment fmt = new FmtDevice();
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
            int textColor = i == mSelectIndex ? Color.parseColor("#3eac9b") : Color.parseColor("#AAffffff");
            int finalI = i;
            Glide.with(getActivity()).load(resUrl).asBitmap().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                            drawable.setBounds(0, 0, 60, 60);
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
        if(bean != null){
            if(PackageUtil.compareVersion(bean.version,PackageUtil.getAppVersion(getContext())) > 0){
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
        if(count > 99){
            tvMsgCount.setVisibility(View.VISIBLE);
            tvMsgCount.setText("99+");
            tvMsgCount.setTextSize(7.5f);
        }else if(count > 0){
            tvMsgCount.setVisibility(View.VISIBLE);
            tvMsgCount.setText(count+"");
            tvMsgCount.setTextSize(9);
        }else{
            tvMsgCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateWeather(WeatherBean bean) {
        List<WeatherBean.HeWeather> list = bean.result.HeWeather5;
        if(list != null && !list.isEmpty()){
            WeatherBean.HeWeather weather = list.get(0);
            tvTmp.setText(weather.now.tmp);
            tvCon.setText(weather.now.cond.txt);
            tvAir.setText("室外空气 "+weather.aqi.city.qlty);
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

    @OnClick({R.id.iv_scan, R.id.iv_add, R.id.layout_msg, R.id.title_ok, R.id.title_select_all})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }


    public void doClick(View view) {
        switch (view.getId()) {
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
        if(!TextUtils.isEmpty(city)){
            mPresenter.getWeather(city);
        }else {
            initPermission();
        }
        mPresenter.getTabData(false);
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismiss(dialog);
        mHandler.clean(DownloadObserver.WHAT_PROGRESS);
        if(observer != null){
            getContext().getContentResolver().unregisterContentObserver(observer);
        }
        super.onDestroy();
    }
    /**
     * 下载进度提示框
     * @param isForce
     */
    private void showDownloadProgressDialog(final boolean isForce){
        DialogUtil.dismiss(dialog);
        View view = getLayoutInflater().inflate(R.layout.layout_progress_download, null);
        tvProgress = (TextView)view.findViewById(R.id.tv_progress_download);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_download);
        dialog = new Dialog(getContext(),R.style.CustomProgressDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels*0.88);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view,params);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(observer != null){
                    getContext().getContentResolver().unregisterContentObserver(observer);
                }
                tvProgress = null;
                progressBar = null;
                if(isForce)
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
            LogUtil.d("==========result:"+location.getLocType());
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                city = location.getCity();
                if(!TextUtils.isEmpty(city))
                mPresenter.getWeather(city);
                locationService.unregisterListener(this);
                locationService.stop();
                locationService = null;
            }
        }

    };
    private void startLocation(){
        // -----------location config ------------
        locationService = new LocationService(getActivity().getApplicationContext());
        locationService.registerListener(mListener);
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }
}
