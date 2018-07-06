package com.zt.yavon.widget;/**
 * Created by xiehehe on 16/7/19.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.common.base.utils.DensityUtil;

import java.util.ArrayList;


/**
 * User: xiehehe
 * Date: 2016-07-19
 * Time: 22:45
 * FIXME
 */
public class VerticalTextview extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 0;
    private static final int FLAG_STOP_AUTO_SCROLL = 1;

    private float mTextSize = 14 ;
    private int mPadding = 0;
    private int textColor = Color.WHITE;

    /**
     * @param textSize 字号
     * @param padding 内边距
     * @param textColor 字体颜色
     */
    public void setText(float textSize,int padding,int textColor) {
        mTextSize = textSize;
        mPadding = padding;
        this.textColor = textColor;
    }

    private OnItemClickListener itemClickListener;
    private Context mContext;
    private int currentId = -1;
    private ArrayList<String> textList;
    private ArrayList<String> resList;
    private Handler handler;

    public VerticalTextview(Context context) {
        this(context, null);
        mContext = context;
        setFactory(this);
    }

    public VerticalTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<String>();
        resList = new ArrayList<>();
        setFactory(this);
    }

    public void setAnimTime(long animDuration) {
//        LogUtil.d("=========setAnimTime,duration:"+animDuration+",getHeight:"+getMeasuredHeight());
        Animation in = new TranslateAnimation(0, 0, getMeasuredHeight(), 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -getMeasuredHeight());
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    /**
     * 间隔时间
     * @param time
     */
    public void setTextStillTime(final long time){
       handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 0) {
                            currentId++;
                            currentId = currentId % textList.size();
                            setText(textList.get(currentId));
                        }
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL,time);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        if(handler.hasMessages(FLAG_START_AUTO_SCROLL))
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };
    }
    /**
     * 设置数据源
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
        if(titles != null && titles.size() == 1){
            currentId = 0;
            setText(textList.get(0));
        }
    }
    public void setTextList(ArrayList<String> titles,ArrayList<String> res) {
        textList.clear();
        textList.addAll(titles);
        resList.clear();
        resList.addAll(res);
        currentId = -1;
        if(titles != null && titles.size() == 1){
            currentId = 0;
            setAnimTime(0);
            setText(textList.get(0));
        }else{
            setAnimTime(500);
        }
    }

    @Override
    public void setText(CharSequence text) {
        final TextView t = (TextView) getNextView();
        Drawable drawable = getResources().getDrawable(Integer.parseInt(resList.get(currentId)));
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        t.setCompoundDrawables(drawable,null,null,null);
        setDataTextColor(text,t);
        t.setText(text);
        showNext();
//        super.setText(text);
    }
    private void setDataTextColor(CharSequence text,TextView t){
        if("低".equals(text)){
            t.setTextColor(Color.parseColor("#01E400"));
        }else if("中".equals(text)){
            t.setTextColor(Color.parseColor("#FFFF00"));
        }else if("高".equals(text)){
            t.setTextColor(Color.parseColor("#FE0000"));
        }else if("非常高".equals(text)){
            t.setTextColor(Color.parseColor("#800026"));
        }else{
            t.setTextColor(Color.parseColor("#DE833B"));
        }
    }
    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        if(textList == null || textList.size() < 2){
            return;
        }
        if(handler.hasMessages(FLAG_STOP_AUTO_SCROLL))
            handler.removeMessages(FLAG_STOP_AUTO_SCROLL);
        if(handler.hasMessages(FLAG_START_AUTO_SCROLL))
            return;
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }
    public boolean isAutoScrollOn(){
        return handler.hasMessages(FLAG_START_AUTO_SCROLL);
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        if(textList == null || textList.size() < 2){
            return;
        }
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }
    @Override
    public View makeView() {
//        LogUtil.d("============makeView");
//        View view = inflate(mContext, R.layout.layout_broadcast,null);
////        TextView t = (TextView) view.findViewById(R.id.tv_broadcast_item);
//        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  //根据字符串的长度显示view的宽度
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//        bitmapDrawable.setBounds(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        bitmapDrawable.setBounds(0, 0, bitmapDrawable.getMinimumWidth(), bitmapDrawable.getMinimumHeight());
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(textColor);
        t.setTextSize(mTextSize);
        t.setSingleLine();
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setClickable(false);
        t.setCompoundDrawablePadding(DensityUtil.dp2px(mContext,10));
//        t.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
//                    itemClickListener.onItemClick(currentId % textList.size());
//                }
//            }
//        });
        return t;
    }

    /**
     * 设置点击事件监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        startAutoScroll();
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        stopAutoScroll();
//    }

//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        startAutoScroll();
//    }

}
