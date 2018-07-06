package com.zt.yavon.utils;

/**
 * Created by hp on 2017/7/4.
 */
public class DialogUtil {

//    /**
//     * 信息提示
//     * @param context
//     * @param listener
//     * @return
//     */
//    public static Dialog create2BtnInfoDialog(Context context,String info, final OnComfirmListening listener) {
//            LayoutInflater inflaterDl = LayoutInflater.from(context);
//            View parent = inflaterDl.inflate(R.layout.dialog_info, null);
//            if(!TextUtils.isEmpty(info))
//            ((TextView)parent.findViewById(R.id.tv_tip_info)).setText(info);
//            final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
//            dialog.setCancelable(true);
//            dialog.setCanceledOnTouchOutside(false);
//            int width = context.getResources().getDisplayMetrics().widthPixels;
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.setContentView(parent, params);
//            parent.findViewById(R.id.btn_cancle_info).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.cancel();
//                }
//            });
//            TextView confirmBt = (TextView) parent.findViewById(R.id.btn_confirm_info);
//            confirmBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    if (listener != null) {
//                        listener.confirm();
//                    }
//                }
//            });
//            dialog.show();
//            return dialog;
//        }
    public interface OnComfirmListening{
        void confirm();
    }
}
