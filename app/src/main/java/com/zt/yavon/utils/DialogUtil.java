package com.zt.yavon.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.common.base.utils.NetWorkUtils;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.module.data.CustomHeightBean;
import com.zt.yavon.module.device.desk.adapter.CustomHeightAdapter;

import java.util.List;

/**
 * Created by hp on 2017/7/4.
 */
public class DialogUtil {

    /**
     * 全局弹窗提示
     *
     * @param context
     * @param info
     * @param cancel
     * @param confirm
     * @param listener
     * @return
     */
    public static Dialog create2BtnInfoDialog(Context context, String info, String cancel, String confirm, final OnComfirmListening listener) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View parent = inflaterDl.inflate(R.layout.layout_dialog_info, null);
        if (!TextUtils.isEmpty(info))
            ((TextView) parent.findViewById(R.id.title_tv)).setText(info);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        TextView confirmBt = (TextView) parent.findViewById(R.id.confirm_bt);
        TextView cancelBt = (TextView) parent.findViewById(R.id.cancel_bt);
        if (TextUtils.isEmpty(cancel)){
            cancelBt.setText("取消");
        }else {
            cancelBt.setText(cancel);
        }
        if (TextUtils.isEmpty(confirm)){
            confirmBt.setText("确定");
        }else {
            confirmBt.setText(confirm);
        }
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.confirm();
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public static Dialog createSitTimeDialog(final Context context, String defaultValue, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_sit_time, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        if (!TextUtils.isEmpty(defaultValue))
            etTime.setText(defaultValue);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        parent.findViewById(R.id.btn_cancle_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        parent.findViewById(R.id.btn_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etTime.getText().toString().trim();
                if (TextUtils.isEmpty(data)) {
                    ToastUtil.showShort(context, context.getString(R.string.tip2_time));
                    return;
                }
                dialog.dismiss();
                if (listener != null) {
                    listener.confirm(data);
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createWifiDialog(final Context context,  final OnComfirmListening listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_wifi, null);
         TextView tv_current_wifi= (TextView) parent.findViewById(R.id.tv_current_wifi);
         TextView tv_change_wifi=(TextView) parent.findViewById(R.id.tv_change_wifi);

        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        if (!TextUtils.isEmpty(NetWorkUtils.getConnectWifiSsid(context))){
            tv_current_wifi.setText(NetWorkUtils.getConnectWifiSsid(context));
        }
        tv_change_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                dialog.dismiss();
            }
        });
        parent.findViewById(R.id.btn_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createCustomHeightDialog(final Context context, final List<CustomHeightBean> defaultList, final OnComfirmListening listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_set_height, null);
        final EditText etName = (EditText) parent.findViewById(R.id.et_name_dialog);
        final EditText etHeight = (EditText) parent.findViewById(R.id.et_height_dialog);
        final GridView gridView = (GridView) parent.findViewById(R.id.grid_desk_dialog);
        final CustomHeightAdapter adapter = new CustomHeightAdapter(context,defaultList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int lastSelectPosition = 0;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == lastSelectPosition){
                    return;
                }
                lastSelectPosition = position;
                for (int i = 0; i < adapterView.getCount(); i++) {
                    CustomHeightBean item = adapter.getItem(i);
                    if (i == position) {
                        item.setSelect(true);
                        etName.setText(item.getName());
                        etHeight.setText(item.getHeight());
                    } else {
                        item.setSelect(false);
                    }
                    adapter.notifyDataSetChanged();
                }
//                LogUtil.d("===============posiont:" + position + ",ischeckd:" + gridView.isItemChecked(position) + ",checked ids:" + Arrays.toString(gridView.getCheckedItemIds()) + ",checked position:" + gridView.getCheckedItemPosition());
            }
        });

        if(adapter.getCount() > 0){
            CustomHeightBean item =adapter.getItem(0);
            etName.setText(item.getName());
            etHeight.setText(item.getHeight());
            item.setSelect(true);
            gridView.setItemChecked(0,true);
            adapter.notifyDataSetChanged();
        }
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int position = gridView.getCheckedItemPosition();
                if(position != -1){
                    LogUtil.d("===================setName:"+etName.getText().toString().trim()+",position:"+position);
                    defaultList.get(position).setName(etName.getText().toString().trim());
                }
            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int position = gridView.getCheckedItemPosition();
                if(position != -1){
                    LogUtil.d("===================setHeight:"+etHeight.getText().toString().trim()+",position:"+position);
                    defaultList.get(position).setHeight(etHeight.getText().toString().trim());
                }
            }
        });
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        parent.findViewById(R.id.btn_cancle_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        parent.findViewById(R.id.btn_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.confirm();
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public interface OnComfirmListening {
        void confirm();
    }

    public interface OnComfirmListening2 {
        void confirm(String data);
    }

    public static void dismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
