package com.zt.yavon.widget;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hp on 2018/6/12.
 */

public class MySwitch extends SwitchCompat{
    private float x1,x2;

    public MySwitch(Context context) {
        super(context);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
        }
        if (Math.abs(x1 - x2) < 20) {
            return false;// 距离较小，当作click事件来处理
        }
        return super.onTouchEvent(event);
    }
}
