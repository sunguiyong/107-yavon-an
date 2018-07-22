package com.zt.yavon.module.main.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.view.MainActivity;

public class MenuWidget extends LinearLayout implements View.OnClickListener {
    private Context mContext;

    public MenuWidget(Context context) {
        this(context, null);
    }

    public MenuWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            findViewById(R.id.ll_more).setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_recent:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onRecentClick();
                }
                break;
            case R.id.menu_move:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onMoveClick();
                }
                break;
            case R.id.menu_rename:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onRenameClick();
                }
                break;
            case R.id.menu_share:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onShareClick();
                }
                break;
            case R.id.menu_more:
                findViewById(R.id.ll_more).setVisibility(VISIBLE);
                break;
            case R.id.menu_del:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onDelClick();
                }
                break;
            case R.id.menu_report:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onReportClick();
                }
                break;
        }
    }

    public void setRenameEnable(boolean enable) {
        ((TextView) findViewById(R.id.menu_rename)).setEnabled(enable);
    }

    public interface OnItemClickListener {
        void onRecentClick();

        void onMoveClick();

        void onRenameClick();

        void onShareClick();

        void onDelClick();

        void onReportClick();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
