package com.zt.yavon.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.common.base.utils.LogUtil;

/**
 * Created by lifujun on 2018/7/17.
 */

public class MyTextView extends TextView{
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        LogUtil.d("==========setText:"+text.toString());
        if(!TextUtils.isEmpty(text.toString()) && text.toString().indexOf("\n") != 4){
            StringBuilder sb = new StringBuilder(text.toString());
            for(int i = 0;i<sb.length();i++){
                if(i==4){
                    sb.insert(i,"\n");
                }
            }
            text = sb.toString();
        }
        super.setText(text, type);
    }
}
