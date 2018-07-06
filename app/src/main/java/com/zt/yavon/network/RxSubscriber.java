package com.zt.yavon.network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.common.base.R;
import com.common.base.utils.LoadingDialog;
import com.common.base.utils.NetWorkUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * des:订阅封装
 * 从common里面拿过来的 因为要做抢登把baseactivity拿过来了，不然还用原来的会导致加载弹框无法显示
 */

/********************使用例子********************/
public abstract class RxSubscriber<T> implements Observer<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=true;
    private boolean canTouchCancle=true;
    private Disposable disposable;
    private Dialog dialog;
    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, context.getString(R.string.loading),true);
    }
    public RxSubscriber(Context context, boolean showDialog) {
        this(context, context.getString(R.string.loading),showDialog);
    }
//    public RxSubscriber(Context context, boolean showDialog, boolean canTouchCancle) {
//        this(context, PuApp.getInstance().getString(R.string.loading),showDialog);
//        this.canTouchCancle = canTouchCancle;
//    }

    @Override
    public void onComplete() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading(dialog);
    }
    public Disposable getDisposable(){
        return disposable;
    }
    @Override
    public void onSubscribe(@NonNull final Disposable disposable) {
        this.disposable = disposable;
        if (showDialog) {
            try {
                dialog = LoadingDialog.showDialogForLoading((Activity) mContext, msg, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        disposable.dispose();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading(dialog);
//        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(mContext)) {
            //2017年8月14日修改不弹网络错误
//            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        else if(e instanceof TokenException){
//            ToastUtil.showShort(mContext,"用户过期，请重新登录");
//            RxBus.getInstance().post(Constants.TAG_EVENT_ERROR_TOKEN,0);
//            Intent intent =new Intent("com.zt.wz.OFF_LINE");
//            mContext.sendBroadcast(intent1);
        }
        else if(e instanceof RuntimeException){
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError("网络异常，请重新尝试");
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);



}
