package com.zt.igreen.module.device.health.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wing on 16/3/30.
 */
public class PathView extends View {
    protected Path mPath ;
    protected Paint mPaint;
    protected int mLineColor = Color.parseColor("#C8DBA0");
    protected int mWidth,mHeight;
    private ValueAnimator animator;
    private float startAngle = 0;
    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void drawPath(Canvas canvas) {
        // 重置path
        mPath.reset();

        //用path模拟一个心电图样式
        mPath.moveTo(0,mHeight/2);
        MathDisc(canvas);
        //canvas.drawPath(mPath,mPaint);
    }
    private void MathDisc(Canvas canvas) { //angle 角度
        int tmp = 0;
        for(int i = 0;i<8;i++) {
            mPath.lineTo(startAngle+20, 200);
            mPath.lineTo(startAngle+40, 50);
            mPath.lineTo(startAngle+70, mHeight / 2 + 50);
            mPath.lineTo(startAngle+80, mHeight / 2);
            mPath.lineTo(startAngle+200, mHeight / 3);
            startAngle = startAngle+200;
        }
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath,mPaint);
    }
    //属性动画
    public void startAnimation() {
        if (animator != null) {
            return;
        }
        animator = ValueAnimator.ofFloat(50, 0f);
        animator.setDuration(8000); //动画时长
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(-1);//表示不循环，-1表示无限循环

        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //实时刷新view，这样我们的进度条弧度就动起来了
                startAngle =(float) animation.getAnimatedValue();
                startAngle=startAngle+10;
                postInvalidateDelayed(10);
            }
        });
        //开启动画
        animator.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawPath(canvas);
    }
}
