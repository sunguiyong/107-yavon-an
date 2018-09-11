package com.zt.yavon.module.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.utils.PackageUtil;

/**
 * 接收downloadmanager下载完成广播
 * Created lfj hp on 2018/3/31.
 */

public class InstallApkBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        LogUtil.d("=====下载的IDonReceive: "+completeDownloadId);
//        context.unregisterReceiver(this);
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if(c.moveToFirst()) {
                //获取文件下载路径
                int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String fileUri = c.getString(fileUriIdx);
                String filename = null;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (fileUri != null) {
                        filename = Uri.parse(fileUri).getPath();
                    }
                } else {
                    //Android 7.0以上的方式：请求获取写入权限，这一步报错
                    //过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME
                    int fileNameIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    filename = c.getString(fileNameIdx);
                }
                LogUtil.d("===========下载完成的文件名是:"+filename);
//                String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if(filename != null && filename.endsWith(".apk") && filename.contains(context.getString(R.string.app_name))){
                    LogUtil.d("=====下载完成的文件名为："+filename);
                    //     /storage/emulated/0/zhnet/T台魅影.apk
                    //执行安装
                    PackageUtil.installPackage(context,filename);
//                    Intent intent_ins = new Intent(Intent.ACTION_VIEW);
//                    intent_ins.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent_ins.setDataAndType(Uri.parse("file://" + filename),"application/vnd.android.package-archive");
//                    context.getApplicationContext().startActivity(intent_ins);
                }
            }
        }
//        else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())){
//            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
//            //点击通知栏取消下载
////            manager.remove(ids);
////            Toast.makeText(context, "已经取消下载", Toast.LENGTH_SHORT).show();
//
//        }
//    }
    }
}
