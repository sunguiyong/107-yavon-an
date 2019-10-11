package com.zt.igreen.module.deviceconnect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2019/3/13 0013.
 */

public class Scrollview extends HorizontalScrollView {
    public Scrollview(Context context) {
        super(context);
    }

    public Scrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Scrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
