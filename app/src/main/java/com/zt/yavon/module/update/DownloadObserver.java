package com.zt.yavon.module.update;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

import com.common.base.utils.LogUtil;


/**
 * 观察apk下载进度
 * Created lfj hp on 2018/3/31.
 */

public class DownloadObserver extends ContentObserver {
    public static int WHAT_PROGRESS = 0x666;
    private Handler mHandler;
    private Context mContext;
    private int progress;
    private DownloadManager mDownloadManager;
    private DownloadManager.Query query;
    private Cursor cursor;
    @SuppressLint("NewApi")
    public DownloadObserver(Context context, long downId, Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
        this.mHandler = handler;
        this.mContext = context;
        mDownloadManager =  (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        query = new DownloadManager.Query().setFilterById(downId);
    }
    @SuppressLint("NewApi")
    @Override
    public void onChange(boolean selfChange) {
        // 每当/data/data/com.android.providers.download/database/database.db变化后，触发onCHANGE，开始具体查询
        super.onChange(selfChange);
        //
        LogUtil.d("===================data onChange");
//        boolean downloading = true;
//        while (downloading) {
            cursor  = mDownloadManager.query(query);
            cursor.moveToFirst();
            long bytes_downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            long bytes_total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            progress = (int) ((bytes_downloaded * 100) / bytes_total);
            LogUtil.d("===================data onChange,progress:"+progress);
            if(progress > 0){
                Message message = mHandler.obtainMessage(WHAT_PROGRESS);
                message.arg1 = progress;
                mHandler.sendMessageDelayed(message,100);
            }
        if (cursor.getInt(
                    cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
            mContext.getContentResolver().unregisterContentObserver(this);
//                downloading = false;
//            RxBus.getInstance().post(Constants.TAG_EVENT_DOWNLOAD_COMPLETE,1);
                LogUtil.d("================unregisterContentObserver");
            }
            cursor.close();
        }
//    }
}