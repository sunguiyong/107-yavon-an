package com.zt.igreen.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.igreen.R;

import butterknife.BindView;

public class DeskRememberDialog extends Dialog implements View.OnClickListener {
    int x = R.layout.desk_remember_dialog;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.ok_tv)
    TextView okTv;
    DialogListener dialogListener;

    public interface DialogListener {
        void onClick(View view);
    }

    ;

    public DeskRememberDialog(Context context, DialogListener dialogListener) {
        super(context);
        setContentView(R.layout.desk_remember_dialog);
        this.dialogListener = dialogListener;
        bindListener();
    }

    public DeskRememberDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.desk_remember_dialog);
        bindListener();
    }

    private void bindListener() {


    }

    @Override
    public void onClick(View v) {
        dialogListener.onClick(v);
    }

    private void tvChange(TextView textView) {
        tv1.setBackgroundResource(R.drawable.desk_remember);
        tv2.setBackgroundResource(R.drawable.desk_remember);
        tv3.setBackgroundResource(R.drawable.desk_remember);
        tv4.setBackgroundResource(R.drawable.desk_remember);
        tv5.setBackgroundResource(R.drawable.desk_remember);
        textView.setBackgroundResource(R.drawable.desk_remember_select);
    }


}
