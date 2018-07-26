package com.zt.yavon.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.base.utils.LogUtil;
import com.common.base.utils.NetWorkUtils;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.module.data.CustomHeightBean;
import com.zt.yavon.module.device.desk.adapter.CustomHeightAdapter;
import com.zt.yavon.module.main.frame.model.TabItemBean;
import com.zt.yavon.widget.RvDialogTab;
import com.zt.yavon.widget.wheelview.adapter.MyWheelAdapter;
import com.zt.yavon.widget.wheelview.widget.WheelView;

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
        if (TextUtils.isEmpty(cancel)) {
            cancelBt.setText("取消");
        } else {
            cancelBt.setText(cancel);
        }
        if (TextUtils.isEmpty(confirm)) {
            confirmBt.setText("确定");
        } else {
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
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(R.string.title_dialog_sitdown);
        etTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        etTime.setHint(R.string.tip2_time);
        parent.findViewById(R.id.tv_unit_dialog).setVisibility(View.VISIBLE);
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

    public static Dialog createEtDialog(final Context context, String title, String hint, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_edit_text, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(title);
        etTime.setHint(hint);
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
                    ToastUtil.showShort(context, "请输入内容");
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

    public static Dialog createNickNameDialog(final Context context, String defaultValue, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_sit_time, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(R.string.nickname_modify);
        etTime.setInputType(InputType.TYPE_CLASS_TEXT);
        etTime.setHint(R.string.nickname_hint);
        parent.findViewById(R.id.tv_unit_dialog).setVisibility(View.GONE);
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
                    ToastUtil.showShort(context, R.string.nickname_hint);
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

    public static Dialog createWifiDialog(final Context context, final OnComfirmListening listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_wifi, null);
        TextView tv_current_wifi = (TextView) parent.findViewById(R.id.tv_current_wifi);
        TextView tv_change_wifi = (TextView) parent.findViewById(R.id.tv_change_wifi);

        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        if (!TextUtils.isEmpty(NetWorkUtils.getConnectWifiSsid(context))) {
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
        final CustomHeightAdapter adapter = new CustomHeightAdapter(context, defaultList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int lastSelectPosition = 0;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == lastSelectPosition) {
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

        if (adapter.getCount() > 0) {
            CustomHeightBean item = adapter.getItem(0);
            etName.setText(item.getName());
            etHeight.setText(item.getHeight());
            item.setSelect(true);
            gridView.setItemChecked(0, true);
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
                if (position != -1) {
                    LogUtil.d("===================setName:" + etName.getText().toString().trim() + ",position:" + position);
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
                if (position != -1) {
                    LogUtil.d("===================setHeight:" + etHeight.getText().toString().trim() + ",position:" + position);
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

    public static Dialog createTimeWheelViewDialog(Context context, String unit, Integer defaultData, final List<Integer> data, final OnSelectCompleteListening listener) {
        if (data == null || data.size() < 1) {
            Log.e("error", "data is null");
            return null;
        }
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View relativeLayout = inflaterDl.inflate(R.layout.layout_dialog_wheel, null);
        final TextView tvUnit = (TextView) relativeLayout.findViewById(R.id.tv_unit_dialog);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.weight = 1;
        if (!TextUtils.isEmpty(unit))
            tvUnit.setText(unit);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.TRANSPARENT;
        style.holoBorderColor = Color.TRANSPARENT;
        style.textSize = 14;
        style.textColor = context.getResources().getColor(R.color.gray_text);
        style.selectedTextColor = context.getResources().getColor(R.color.colorPrimary);
        style.selectedTextSize = 18;
        final Integer[] results = new Integer[1];
//        final List<WheelView> wheelViewsList = new ArrayList<>();
//        for(int i = 0;i<data.size();i++){
//        int result = defaultData;
//        final WheelView<Integer> wheelView = new WheelView<Integer>(context, style);
        final WheelView<Integer> wheelView = (WheelView) relativeLayout.findViewById(R.id.wheelview_dialog);
        wheelView.setStyle(style);
        final MyWheelAdapter adapter = new MyWheelAdapter();
        wheelView.setWheelAdapter(adapter); // 文本数据源
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelSize(5);
        wheelView.setLoop(true);
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<Integer>() {
            @Override
            public void onItemSelected(int position, Integer data) {
                results[0] = data;
//                String dataString = data+"";
//                tvUnit.setPadding(dataString.length()*DensityUtil.dp2px(context,40),DensityUtil.dp2px(context,3),0,0);
            }
        });
//        container.addView(wheelView, params);
        wheelView.setWheelData(data);  // 数据集合
//        wheelView.setSelectedItem(defaultData);
//            wheelViewsList.add(wheelView);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(relativeLayout, params2);
//        dialog.setContentView(relativeLayout);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        final ViewGroup.LayoutParams wheelParams = wheelView.getLayoutParams();
//        if(type == MyWheelAdapter.TYPE_SUBSCRIBE || type == MyWheelAdapter.TYPE_FAN){
//            wheelParams.width = DensityUtil.dp2px(context,100);
//        }else if(type == MyWheelAdapter.TYPE_INTERVAL){
//            wheelParams.width = DensityUtil.dp2px(context,200);
//        }
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cancleBt = (TextView) relativeLayout.findViewById(R.id.btn_cancle_wheel);
        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView confirmBt = (TextView) relativeLayout.findViewById(R.id.btn_confirm_wheel);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(WheelView wheelView:wheelViewsList){
                if (wheelView.isScrolling()) {
//                        L.d("=======正在滑动中");
                    return;
                }
//                }
//                L.d("=======可以点击");
                dialog.dismiss();
                if (listener != null) {
                    listener.onSelectComplete(results[0]);
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createTimeWheelViewDialog2(Context context, String unit, Integer defaultData, final List<Integer> data, final OnSelectCompleteListening listener) {
        if (data == null || data.size() < 1) {
            Log.e("error", "data is null");
            return null;
        }
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View relativeLayout = inflaterDl.inflate(R.layout.dialog_wheel_mdh, null);
        final TextView tvUnit = (TextView) relativeLayout.findViewById(R.id.tv_unit_dialog);
        if (!TextUtils.isEmpty(unit))
            tvUnit.setText(unit);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.TRANSPARENT;
        style.holoBorderColor = Color.TRANSPARENT;
        style.textSize = 14;
        style.textColor = context.getResources().getColor(R.color.gray_text);
        style.selectedTextColor = context.getResources().getColor(R.color.colorPrimary);
        style.selectedTextSize = 18;
        final Integer[] results = new Integer[1];
        final WheelView<Integer> wheelView = (WheelView) relativeLayout.findViewById(R.id.wheelview_dialog);
        wheelView.setStyle(style);
        final WheelView<Integer> wheelView2 = (WheelView) relativeLayout.findViewById(R.id.wheelview2_dialog);
        wheelView.setStyle(style);
        final WheelView<Integer> wheelView3 = (WheelView) relativeLayout.findViewById(R.id.wheelview3_dialog);
        wheelView.setStyle(style);
        final MyWheelAdapter adapter = new MyWheelAdapter();
        wheelView.setWheelAdapter(adapter); // 文本数据源
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelSize(5);
        wheelView.setLoop(true);
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<Integer>() {
            @Override
            public void onItemSelected(int position, Integer data) {
                results[0] = data;
//                String dataString = data+"";
//                tvUnit.setPadding(dataString.length()*DensityUtil.dp2px(context,40),DensityUtil.dp2px(context,3),0,0);
            }
        });
//        container.addView(wheelView, params);
        wheelView.setWheelData(data);  // 数据集合
//        wheelView.setSelectedItem(defaultData);
//            wheelViewsList.add(wheelView);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(relativeLayout, params2);
//        dialog.setContentView(relativeLayout);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        final ViewGroup.LayoutParams wheelParams = wheelView.getLayoutParams();
//        if(type == MyWheelAdapter.TYPE_SUBSCRIBE || type == MyWheelAdapter.TYPE_FAN){
//            wheelParams.width = DensityUtil.dp2px(context,100);
//        }else if(type == MyWheelAdapter.TYPE_INTERVAL){
//            wheelParams.width = DensityUtil.dp2px(context,200);
//        }
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cancleBt = (TextView) relativeLayout.findViewById(R.id.btn_cancle_wheel);
        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView confirmBt = (TextView) relativeLayout.findViewById(R.id.btn_confirm_wheel);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(WheelView wheelView:wheelViewsList){
                if (wheelView.isScrolling()) {
//                        L.d("=======正在滑动中");
                    return;
                }
//                }
//                L.d("=======可以点击");
                dialog.dismiss();
                if (listener != null) {
                    listener.onSelectComplete(results[0]);
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createMoveDeviceDialog(Context context, List<TabItemBean> tabData, OnComfirmListening listener) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View parent = inflaterDl.inflate(R.layout.dialog_move_device_layout, null);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        TextView confirmBt = (TextView) parent.findViewById(R.id.confirm_bt);
        TextView cancelBt = (TextView) parent.findViewById(R.id.cancel_bt);
        cancelBt.setText("取消");
        confirmBt.setText("确定");
        RvDialogTab rvDialogTab = parent.findViewById(R.id.rv_dialog_tab);
        rvDialogTab.setData(tabData);
        rvDialogTab.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                rvDialogTab.setSelection(position);
            }
        });
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

    public interface OnSelectCompleteListening {
        void onSelectComplete(int data);
    }
}
