package com.zt.yavon.module.update;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.MyApplication;
import com.zt.yavon.utils.Storage;

import java.io.File;


/**
 * 自带系统下载
 */
public class DownloadImpl {
    private String mUrl;
    private Context mContext;
    private String mFileDescription;
    private String mFileName;
    private int mAllowedNetworkTypes = ~0; // default to all network types
    // allowed

    public DownloadImpl(Context context, String url, String fileDescription,
                        String fileName) {
        mContext = context;
        mUrl = url;
        mFileDescription = fileDescription;
        mFileName = fileName;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void startDownload(UpdateUtils.DateChangeObserver observer) {
        //先校验当前内存空间是否足够
        if (!storageAvailableForDownload()) {
            ToastUtil.showShort(mContext, "sd卡当前不可用或者空间不足");
            return;
        }
        //小于2.3采用默认下载器
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            downloadAsDefault();
        } else {
            Request request = new Request(Uri.parse(mUrl));
            //通知栏出现的消息提示
            request.setDescription(String.format("正在下载%s,请稍候", mFileDescription));
            request.setTitle(mFileDescription);

            String downloaddir = Environment.DIRECTORY_DOWNLOADS;
            if (downloaddir.contains("://")) {
                downloaddir = "download";
            }

            LogUtil.d("url=%s dir=%s filename=%s", mUrl, downloaddir, mFileName);

            Environment.getExternalStoragePublicDirectory(downloaddir).mkdir();
            request.setDestinationInExternalPublicDir(downloaddir, mFileName);
            DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            //设置下载需要的网络环境
            request.setAllowedNetworkTypes(mAllowedNetworkTypes);
            try {
                //加入下载队列
                long downloadId = manager.enqueue(request);
                if(observer != null){
                    observer.registerDateChangeObserver(downloadId);
                }
//                ToastUtil.showShort(mContext, "开始下载" + mFileName);
            } catch (Exception e) {
                LogUtil.e(e, "");
                //出现错误就采用默认下载
                downloadAsDefault();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void setOnlyWifiAvalible(boolean avalible) {
        if (avalible
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mAllowedNetworkTypes = Request.NETWORK_WIFI;
        }
    }

    /**
     * 判断当前uri是否正在下载或者已经下载完成 (2.3以下版本直接返回false)
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isDownloading(String url) {
        if (TextUtils.isEmpty(url))
            return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            // 2.3以下版本一律返回false
            return false;
        }
        //获取下载服务器
        DownloadManager downloadManager = (DownloadManager) MyApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        //获取游标
        Cursor cursor = downloadManager.query(new Query());

        if (cursor == null) {
            return false;
        }
        while (cursor.moveToNext()) {
            String szDownloadUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (!url.equals(szDownloadUri))
                continue;
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_FAILED:
                    cursor.close();
                    return false;
//                case DownloadManager.STATUS_PAUSED:
//                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    cursor.close();
                    return true;
                case DownloadManager.STATUS_SUCCESSFUL:
                    cursor.close();
                    return false;
                default:
                    cursor.close();
                    return false;
            }
        }

        cursor.close();
        return false;
    }

    /**
     * 获取下载过的apk所在的本地位置
     *
     * @param url 根据所给的url
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static File getDownloadFile(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            // 2.3以下版本一律返回false
            return null;
        }

        DownloadManager downloadManager = (DownloadManager) MyApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(new Query());

        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String szDownloadUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (!url.equals(szDownloadUri))
                continue;
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    String szLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    cursor.close();
                    return new File(Uri.parse(szLocalUri).getPath());
            }
        }
        cursor.close();
        return null;

    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getDownloadId(String url) {
        if (TextUtils.isEmpty(url))
            return -1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            // 2.3以下版本一律返回false
            return -1;
        }

        DownloadManager downloadManager = (DownloadManager) MyApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(new Query());

        if (cursor == null) {
            return -1;
        }
        while (cursor.moveToNext()) {
            String szDownloadUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (!url.equals(szDownloadUri))
                continue;
            return cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
        }
        cursor.close();
        return -1;

    }
//    private void resumeDownload(String uriString,String downloadId){
////        Uri uri= ContentUris.withAppendedId(android.app.DownloadManager.Request.CONTENT_URI,downloadId);
//        Uri uri= Uri.parse(uriString);
//        ContentValues values = new ContentValues();
//        values.put(android.provider.Downloads.Impl.COLUMN_ALLOWED_NETWORK_TYPES, android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
//        values.put(android.provider.Downloads.Impl.COLUMN_CONTROL,android.provider.Downloads.Impl.CONTROL_RUN);
//        values.put(android.provider.Downloads.Impl.COLUMN_STATUS, android.provider.Downloads.Impl.STATUS_RUNNING);
//        mContext.getContentResolver().update(uri, values, null, null);
//    }
    /**
     * 低于9的采用默认下载器下载
     */
    private void downloadAsDefault() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mUrl));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * Checks whether the space is enough for download.
     * 检测内存空间是否可用，便于下载
     * the download needs to check whether the space is enough
     *
     * @return
     */
    private boolean storageAvailableForDownload() {
        // Make sure the space can satisfy the current download, so
        // we will add the size of running downloads.
        long neededspace = Storage.APK_SIZE + Storage.RESERVED_SPACE;

        // Calculate the needed space for current download.
        long cacheSpace = Storage.cachePartitionAvailableSpace();
        long dataSpace = Storage.dataPartitionAvailableSpace();
        long externalStorageSpace = Storage.externalStorageAvailableSpace();

        LogUtil.d("[Needed space : %s], Cache partition space : %s, "
                        + "Data partition space : %s, External storage space : %s",
                Storage.readableSize(neededspace),
                Storage.readableSize(cacheSpace),
                Storage.readableSize(dataSpace),
                Storage.readableSize(externalStorageSpace));

        if (Storage.externalStorageAvailable()) {
            if (externalStorageSpace < neededspace) {
                return false;
            }
        } else if (dataSpace < neededspace) {
            return false;
        }

        return true;
    }
}
