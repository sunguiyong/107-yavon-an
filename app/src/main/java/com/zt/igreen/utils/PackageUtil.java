package com.zt.igreen.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.File;

/**
 * Created by hp on 2018/6/30.
 */

public class PackageUtil {

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
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param newVersion
     * @param oldVersion
     * @return
     */
    public static int compareVersion(String newVersion,String oldVersion) {
            if (newVersion == null || oldVersion == null) {
                return 0;
//            throw new Exception("compareVersion error:illegal params.");
            }

            if(newVersion.startsWith("v") || newVersion.startsWith("V")){
                newVersion = newVersion.substring(1);
            }
            if(oldVersion.startsWith("v") || oldVersion.startsWith("V")){
                oldVersion = oldVersion.substring(1);
            }
            //返回结果: -2 ,-1 ,0 ,1
            int result = 0;
            String matchStr = "[0-9]+(\\.[0-9]+)*";
            oldVersion = oldVersion.trim();
            newVersion = newVersion.trim();
            //非版本号格式,返回error
            if (!oldVersion.matches(matchStr) || !newVersion.matches(matchStr)) {
                return -2;
            }
            String[] oldVs = oldVersion.split("\\.");
            String[] newVs = newVersion.split("\\.");
            int oldLen = oldVs.length;
            int newLen = newVs.length;
            int len = oldLen > newLen ? oldLen : newLen;
            for (int i = 0; i < len; i++) {
                if (i < oldLen && i < newLen) {
                    int o = Integer.parseInt(oldVs[i]);
                    int n = Integer.parseInt(newVs[i]);
                    if (o > n) {
                        result = -1;
                        break;
                    } else if (o < n) {
                        result = 1;
                        break;
                    }
                } else {
                    if (oldLen > newLen) {
                        for (int j = i; j < oldLen; j++) {
                            if (Integer.parseInt(oldVs[j]) > 0) {
                                result = -1;
                            }
                        }
                        break;
                    } else if (oldLen < newLen) {
                        for (int j = i; j < newLen; j++) {
                            if (Integer.parseInt(newVs[j]) > 0) {
                                result = 1;
                            }
                        }
                        break;
                    }
                }
            }
            return result;
        }
    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
    public static Boolean installPackage(Context context, File mFile) {
        Uri uri = UriToPathUtil.getUri(context,mFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        context.startActivity(intent);
        return true;
    }
    public static Boolean installPackage(Context context, String fileName) {
        Uri uri = UriToPathUtil.getUri(context,fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        context.startActivity(intent);
        return true;
    }



    public boolean isInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        return true;
    }

}
