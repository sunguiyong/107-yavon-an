package com.zt.igreen.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.R;
import com.zt.igreen.module.data.DeskBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.device.desk.adapter.CustomHeightAdapter;
import com.zt.igreen.module.device.desk.view.DeskDetailActivity;
import com.zt.igreen.widget.RvDialogTab;
import com.zt.igreen.widget.wheelview.adapter.MyWheelAdapter;
import com.zt.igreen.widget.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp on 2017/7/4.
 */
public class DialogUtil {


    public static Dialog createInfoDialogWithListener(Context context, String info, final OnComfirmListening listener) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View rootView = inflaterDl.inflate(R.layout.layout_dialog_info, null);
        TextView titleTv = (TextView) rootView.findViewById(R.id.title_tv);
        rootView.findViewById(R.id.cancel_bt).setVisibility(View.GONE);
        rootView.findViewById(R.id.divier_button).setVisibility(View.GONE);
        if(!TextUtils.isEmpty(info))
            titleTv.setText(info);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(rootView, params);
        TextView confirmBt = (TextView) rootView.findViewById(R.id.confirm_bt);
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
    public static Dialog create2BtnInfoDialog2(Context context, String info, String cancel, String confirm, final OnComfirmListening listener, final OnComfirmListening listener2) {
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
                if (listener != null) {
                    listener.confirm();
                }
            }
        });
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener2 != null) {
                    listener2.confirm();
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public static Dialog createUpdateDialog(Activity context, String info, boolean cancleable, String cancel, String confirm, final OnComfirmListening listener) {
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
//        View divider = (TextView) parent.findViewById(R.id.divier_button);
//        if(cancleable){
            if (TextUtils.isEmpty(cancel)) {
                cancelBt.setText("取消");
            } else {
                cancelBt.setText(cancel);
            }
//        }else{
//            cancelBt.setVisibility(View.GONE);
//            divider.setVisibility(View.GONE);
//        }
        if(!cancleable){
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        context.finish();
                    }
                });
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
//                dialog.dismiss();
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

    public static Dialog createSitTimeDialog1(final Context context,String hint,String title, final OnComfirmListening3 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_sit_time, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        final SwitchCompat switchemail_info=parent.findViewById(R.id.switch_email_info);
        final ImageView imgsun=parent.findViewById(R.id.imgsun);
        imgsun.setVisibility(View.VISIBLE);
        switchemail_info.setVisibility(View.VISIBLE);
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(title);
        etTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        etTime.setHint(hint);
        parent.findViewById(R.id.tv_unit_dialog).setVisibility(View.GONE);
       /* if (!TextUtils.isEmpty(defaultValue))
            etTime.setText(defaultValue);*/
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        String [] choose=new String[2];
        switchemail_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choose[0]=isChecked+"";
            }
        });
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
                    ToastUtil.showShort(context, "请输入温度");
                    return;
                }
                dialog.dismiss();
                if (listener != null) {
                    choose[1]=data;
                    listener.confirm(choose);
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public static Dialog createEtDialog(final Context context, boolean autoDismiss,String title, String hint, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_edit_text, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(title);
        if(!TextUtils.isEmpty(hint)){
            etTime.setText(hint);
            etTime.setSelection(hint.length());
        }
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
                if(autoDismiss)
                dialog.dismiss();
                if (listener != null) {
                    listener.confirm(data);
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public static Dialog createEtDialog1(final Context context, boolean autoDismiss,String title, String hint, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_edit_text, null);
        final EditText etTime = (EditText) parent.findViewById(R.id.et_time_dialog);
        ((TextView) parent.findViewById(R.id.title_dialog)).setText(title);

            etTime.setHintTextColor(Color.parseColor("#B8B8B8"));
            etTime.setHint(hint);

            //etTime.setSelection(hint.length());
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
                if(autoDismiss)
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
        if (!TextUtils.isEmpty(defaultValue)){
            etTime.setText(defaultValue);
            etTime.setSelection(defaultValue.length());
        }
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

    /**
     * 创建wifi配置dialog
     * @param context
     * @param ssid
     * @param pwd
     * @param listener
     * @return
     */
    public static Dialog createWifiDialog(final Context context,String ssid,String pwd, final OnComfirmListening2 listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_wifi, null);
        final EditText etPwd = (EditText) parent.findViewById(R.id.et_pwd_wifi);
        TextView tv_current_wifi = (TextView) parent.findViewById(R.id.tv_current_wifi);
        TextView tv_change_wifi = (TextView) parent.findViewById(R.id.tv_change_wifi);
//        etPwd.setText("zetadata2017");
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        if (!TextUtils.isEmpty(ssid)) {
            tv_current_wifi.setText(ssid);
        }
        if (!TextUtils.isEmpty(pwd)) {
            etPwd.setText(pwd);
            etPwd.setSelection(pwd.length());
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
                if(listener != null){
                    listener.confirm(etPwd.getText().toString().trim());
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createCustomHeightDialog(final Context context, final List<DeskBean> defaultList, final OnComfirmListening listener) {
        List<DeskBean> tempList = new ArrayList<>();
        for(DeskBean bean:defaultList){
            tempList.add(new DeskBean(bean.name,bean.height));
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.dialog_set_height, null);
        final EditText etName = (EditText) parent.findViewById(R.id.et_name_dialog);
        final EditText etHeight = (EditText) parent.findViewById(R.id.et_height_dialog);
        final GridView gridView = (GridView) parent.findViewById(R.id.grid_desk_dialog);
        final CustomHeightAdapter adapter = new CustomHeightAdapter(context, tempList);
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
                    DeskBean item = adapter.getItem(i);
                    if (i == position) {
                        adapter.setSelectPostion(position);
                        etName.setText(item.name);
                        etName.setSelection(item.name.length());
                        etHeight.setText(item.height+"");
                        etHeight.setSelection((item.height+"").length());
                    }
                }
                adapter.notifyDataSetChanged();
//                LogUtil.d("===============posiont:" + position + ",ischeckd:" + gridView.isItemChecked(position) + ",checked ids:" + Arrays.toString(gridView.getCheckedItemIds()) + ",checked position:" + gridView.getCheckedItemPosition());
            }
        });

        if (adapter.getCount() > 0) {
            DeskBean item = adapter.getItem(0);
            etName.setText(item.name);
            etName.setSelection(item.name.length());
            etHeight.setText(item.height+"");
            etHeight.setSelection((item.height+"").length());
//            adapter.setSelectPostion(true);
            gridView.setItemChecked(0, true);
//            adapter.notifyDataSetChanged();
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
//                    LogUtil.d("===================setName:" + etName.getText().toString().trim() + ",position:" + position);
                    tempList.get(position).setName(etName.getText().toString().trim());
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
                int height = 0;
                try {
                    height = Integer.parseInt(etHeight.getText().toString().trim());
                }catch (Exception e){
                    ToastUtil.showShort(context,"高度必须为数字："+DeskDetailActivity.HEIGHT_BOTTOM+"-"+DeskDetailActivity.HEIGHT_TOP);
                    return;
                }
                int position = gridView.getCheckedItemPosition();
                if (position != -1) {
//                    LogUtil.d("===================setHeight:" + etHeight.getText().toString().trim() + ",position:" + position);
                    tempList.get(position).setHeight(height);
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
                    for(int i = 0;i<tempList.size();i++){
                        DeskBean bean = tempList.get(i);
                        if(bean.height < DeskDetailActivity.HEIGHT_BOTTOM || bean.height > DeskDetailActivity.HEIGHT_TOP){
                            ToastUtil.showShort(context,"自定义"+(i+1)+"高度超出范围:"+DeskDetailActivity.HEIGHT_BOTTOM+"-"+DeskDetailActivity.HEIGHT_TOP);
                            return;
                        }
                    }
                    defaultList.clear();
                    defaultList.addAll(tempList);
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
        if(!TextUtils.isEmpty(unit)){
            tvUnit.setText(unit);
        }
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
        final MyWheelAdapter<Integer> adapter = new MyWheelAdapter<Integer>();
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
        wheelView.setSelectedItem(defaultData);
//            wheelViewsList.add(wheelView);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
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

    public static Dialog createTimeWheelViewDialog2(Context context, final String[] defaultData, final List<Object> data, final OnSelectCompleteListening2 listener) {
        if(data == null || data.size() < 1){
            Log.e("error","data is null");
            return null;
        }
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View relativeLayout = inflaterDl.inflate(R.layout.dialog_wheel_mdh, null);
        LinearLayout container = (LinearLayout) relativeLayout.findViewById(R.id.container_wheelview);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.TRANSPARENT;
        style.holoBorderColor = Color.TRANSPARENT;
        style.textSize = 14;
        style.textColor = context.getResources().getColor(R.color.gray_text);
        style.selectedTextColor = context.getResources().getColor(R.color.colorPrimary);
        style.selectedTextSize = 18;
//        final String[] results = new String[data.size()];
        final List<WheelView> wheelViewsList = new ArrayList<>();
        for(int i = 0;i<data.size();i++){
            final WheelView wheelView = new WheelView(context,style);
            if(i == 0){
                wheelView.setWheelAdapter(new MyWheelAdapter<String>(MyWheelAdapter.TYPE_MDH));
            }else{
                wheelView.setWheelAdapter(new MyWheelAdapter<String>());
            }
            wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelView.setWheelSize(5);
//            wheelView.setLoop(true);
            final int index = i;
            wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
//                    L.d("=========onItemSelected,index:"+index+",postion:"+position+",data:"+(String) o);
                    defaultData[index] = (String) o;
                }
            });
            container.addView(wheelView,params);
            if(i != 1){
                wheelView.setWheelData((List<String>)data.get(i));  // 数据集合
                if(defaultData != null && defaultData.length > i){
                    wheelView.setSelectedItem(defaultData[i]);
                }
            }else if(i == 1){
                WheelView wheelView0 = (WheelView) container.getChildAt(i-1);
                HashMap<String,List<String>> map = (HashMap<String, List<String>>) data.get(i);
                wheelView0.join(wheelView);
                wheelView0.joinDatas(map);
                if(defaultData != null && defaultData.length > i) {
                    wheelView.setWheelData(map.get(defaultData[i-1]));
                    wheelView.setSelectedItem(defaultData[i],true);
                }else{
                    wheelView.setWheelData(map.get(wheelView0.getSelectionItem()));
                }
            }
            wheelViewsList.add(wheelView);
        }
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(relativeLayout, params2);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        TextView cancleBt = (TextView) relativeLayout.findViewById(R.id.btn_cancle_wheel);
        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(dialog);
            }
        });
        TextView confirmBt = (TextView) relativeLayout.findViewById(R.id.btn_confirm_wheel);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(WheelView wheelView:wheelViewsList){
                    if(wheelView.isScrolling()){
//                        L.d("=======正在滑动中");
                        return;
                    }
                }
//                L.d("=======可以点击");
                dismiss(dialog);
                if (listener != null) {
                    listener.onSelectComplete();
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog createMoveDeviceDialog(Context context, List<TabBean> tabData, final OnComfirmListening2 listener) {
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
        final RvDialogTab rvDialogTab = parent.findViewById(R.id.rv_dialog_tab);
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
                if(rvDialogTab.getSelectPosition() == -1){
                    ToastUtil.showShort(context,"请选择房间");
                    return;
                }
                dialog.dismiss();
                if (listener != null) {
                    listener.confirm(tabData.get(rvDialogTab.getSelectPosition()).id+"");
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
    public interface OnComfirmListening3 {
        void confirm(String[] data);
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
    public interface OnSelectCompleteListening2 {
        void onSelectComplete();
    }

}
