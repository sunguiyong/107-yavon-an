package com.zt.yavon.module.update;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.common.base.utils.LogUtil;
import com.zt.yavon.MyApplication;
import com.zt.yavon.R;
import com.zt.yavon.utils.PackageUtil;

import java.io.File;

/**
 * 更新版本
 */
public class UpdateUtils {


    /**
     * 获得当前安装PU的版本号
     * @return
     */
    public static String getVersion(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void update(final Context mContext, String newVersion, String newVersionUrl,
                              boolean isForceUpdate, Dialog progressDialog, DateChangeObserver observer) {

        // 如果文件存在，安装提示
        final File file = DownloadImpl.getDownloadFile(newVersionUrl);
        if(DownloadImpl.isDownloading(newVersionUrl)){
            long downloadId = DownloadImpl.getDownloadId(newVersionUrl);
            LogUtil.d("==============apk is downloading,id:"+downloadId);
            if(downloadId != -1 && observer != null){
                observer.registerDateChangeObserver(downloadId);
            }
        }else
        if (file != null && file.exists() && file.isFile() && file.getName().endsWith(".apk")) {
            LogUtil.d("=============file exist:"+file.getAbsolutePath());
            //最新文件提示安装，否则删除文件
            String oldVersion = getApkFileVersion(mContext, file.getAbsolutePath());
            LogUtil.d("=============oldVersion:"+oldVersion);

            if (PackageUtil.compareVersion(newVersion,oldVersion) == 0) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                //安装Apk
                PackageUtil.installPackage(mContext, file);
                return;
            } else {
                //删除旧的版本
                LogUtil.d("============= delete file:"+file.getAbsolutePath());
                file.delete();
                LogUtil.d("==========start download");
                download(mContext,newVersion,newVersionUrl,!isForceUpdate,observer);
            }
        }else{
            LogUtil.d("==========start download");
            download(mContext,newVersion,newVersionUrl,!isForceUpdate,observer);
        }
    }
    /**
     * 下载文件
     * @param newVersion 新版本号
     * @param newVersionUrl 新版本的下载地址
     * @param isOnlyWifi 是否仅在wifi下下载
     */
    private static void download(Context mContext, String newVersion, String newVersionUrl,
                                 boolean isOnlyWifi, DateChangeObserver observer) {
        //寻找本地是否有这个apk，有就直接返回
//        final File file = DownloadImpl.getDownloadFile(newVersionUrl);
//        if (file != null && file.exists() && file.getName().endsWith(".apk"))
//            return;
//        if (!DownloadImpl.isDownloading(newVersionUrl))
//        {
            String appName = String.format(mContext.getString(R.string.app_name)+"_%s.apk", newVersion);
            DownloadImpl download = new DownloadImpl(mContext, newVersionUrl,
                    appName, appName);
//            download.setOnlyWifiAvalible(isOnlyWifi);
            download.startDownload(observer);
//        }
    }

    /**
     * 对比旧版本和新版本(字符串VersionName)
     *
     * @param nowVersion 现在的版本号
     * @param newVersion 新的版本号
     * @return 1 旧版本高，-1 新版本高， 0 others
     */
    private static int compare(final String nowVersion, String newVersion) {
        if (TextUtils.isEmpty(nowVersion) || TextUtils.isEmpty(newVersion))
            return 0;
        // newVersion = "5.4.1";
        if (nowVersion.equals(newVersion))
            return 0;

        final String[] arrNowVersion = nowVersion.split("\\.");
        final String[] arrNewVersion = newVersion.split("\\.");

        if (arrNowVersion == null || arrNewVersion == null)
            return 0;
        //字符串被.分开，选取较长的作为长度，挨个比对
        final int minLen = arrNowVersion.length > arrNewVersion.length ? arrNewVersion.length
                : arrNowVersion.length;
        for (int i = 0; i < minLen; i++) 
        {
            int nowNum;
            int newNum;
            try {
                nowNum = Integer.parseInt(arrNowVersion[i]);
                newNum = Integer.parseInt(arrNewVersion[i]);
            } catch (NumberFormatException e) {
                continue;
            }
            if (nowNum == newNum)
                continue;
            if (nowNum > newNum)
                return 1;
            else
                return -1;
        }
        //假设上面循环都没有执行
        if (arrNowVersion.length < arrNewVersion.length)
            return -1;
        else if (arrNowVersion.length > arrNewVersion.length)
            return 1;
        else
            return 0;
    }

    /**
     * 
     * @param apkFilePath 已经下载好的apk在本地的路径
     * @return
     */
    public static String getApkFileVersion(Context mContext, String apkFilePath) {
        if (TextUtils.isEmpty(apkFilePath) || !new File(apkFilePath).exists())
            return "";
        //获取安卓包管理器
        PackageManager pm = mContext.getPackageManager();
        //获取安装包信息
        PackageInfo info = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
        /*if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            // 得到安装包名称
            String packageName = appInfo.packageName;
            // 得到版本信息
            String version = info.versionName;
            // 得到图标信息
            // Drawable icon = pm.getApplicationIcon(appInfo);
        }*/
       //获取wersionName
        if (info == null || TextUtils.isEmpty(info.versionName))
            return "";
        else
            return info.versionName;
    }
    public interface DateChangeObserver{
        void registerDateChangeObserver(long downloadId);
    }

}
