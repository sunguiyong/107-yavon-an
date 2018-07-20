package com.zt.yavon.widget.wheelview.graphics;

/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.common.base.utils.DensityUtil;
import com.zt.yavon.widget.wheelview.common.WheelConstants;
import com.zt.yavon.widget.wheelview.widget.WheelView;


/**
 * holo滚轮样式
 *
 * @author venshine
 */
public class HoloDrawable extends WheelDrawable {

    private Context mContext;
    private Paint mHoloBgPaint, mHoloPaint;

    private int mWheelSize, mItemH;

    public HoloDrawable(int width, int height, WheelView.WheelViewStyle style, int wheelSize, int itemH) {
        super(width, height, style);
        mWheelSize = wheelSize;
        mItemH = itemH;
        init();
    }
    public HoloDrawable(Context context, int width, int height, WheelView.WheelViewStyle style, int wheelSize, int itemH) {
        super(width, height, style);
        mContext = context;
        mWheelSize = wheelSize;
        mItemH = itemH;
        init();
    }

    private void init() {
        mHoloBgPaint = new Paint();
        mHoloBgPaint.setColor(mStyle.backgroundColor != -1 ? mStyle.backgroundColor : WheelConstants
                .WHEEL_SKIN_HOLO_BG);

        mHoloPaint = new Paint();
        mHoloPaint.setStrokeWidth(3);
        mHoloPaint.setColor(mStyle.holoBorderColor != -1 ? mStyle.holoBorderColor : WheelConstants
                .WHEEL_SKIN_HOLO_BORDER_COLOR);
    }


    @Override
    public void draw(Canvas canvas) {
        // draw background
        canvas.drawRect(0, 0, mWidth, mHeight, mHoloBgPaint);

        // draw select border
        if (mItemH != 0) {
            int delta = mStyle.selectedTextSize-mStyle.textSize;
            if(delta > 0){
                delta = (int) (DensityUtil.dp2px(mContext, delta)*1.4);
            }
            canvas.drawLine(0, mItemH * (mWheelSize / 2), mWidth, mItemH
                    * (mWheelSize / 2), mHoloPaint);
            canvas.drawLine(0, mItemH * (mWheelSize / 2 + 1)+delta, mWidth, mItemH
                    * (mWheelSize / 2 + 1)+delta, mHoloPaint);
        }
    }
}
