package com.zt.yavon.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by hp on 2018/6/30.
 */

public class PakageUtil {

    public static String getAppVersion(Context mContext) {
        PackageInfo pi = null;
        try {
            PackageManager pm = mContext.getPackageManager();
            pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
    /**
     * 对比旧版本和新版本(字符串VersionName)
     *
     * @param newVersion 新的版本号
     * @return 1 旧版本高，-1 新版本高， 0 others
     */
    public static int compare(Context context, String newVersion) {
        String nowVersion = getAppVersion(context);
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
}
