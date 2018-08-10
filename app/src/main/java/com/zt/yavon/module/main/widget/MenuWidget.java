package com.zt.yavon.module.main.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;

public class MenuWidget extends LinearLayout implements View.OnClickListener {
    public TextView tvOften,tvMove,tvRename,tvShare,tvMore,tvDel,tvReport;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        tvOften = (TextView) findViewById(R.id.menu_recent);
        tvMove = (TextView) findViewById(R.id.menu_move);
        tvRename = (TextView) findViewById(R.id.menu_rename);
        tvShare = (TextView) findViewById(R.id.menu_share);
        tvMore = (TextView) findViewById(R.id.menu_more);
        tvDel = (TextView) findViewById(R.id.menu_del);
        tvReport = (TextView) findViewById(R.id.menu_report);
    }


    private void reset(){
        tvMove.setText("移动");
        tvOften.setEnabled(true);
        tvMore.setEnabled(true);
        tvRename.setEnabled(true);
        tvShare.setEnabled(true);
        tvDel.setEnabled(true);
        tvReport.setEnabled(true);
        tvOften.setSelected(false);
        tvMove.setSelected(false);
        tvRename.setSelected(false);
        tvShare.setSelected(false);
        tvDel.setSelected(false);
        tvReport.setSelected(false);
    }
    @Override
    public void setVisibility(int visibility) {
        if(visibility == VISIBLE){
            reset();
        }
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
                    tvOften.setSelected(true);
                    tvMove.setSelected(false);
                    tvRename.setSelected(false);
                    tvShare.setSelected(false);
                    tvDel.setSelected(false);
                    tvReport.setSelected(false);
                }
                break;
            case R.id.menu_move:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onMoveClick();
                    tvOften.setSelected(false);
                    tvMove.setSelected(true);
                    tvRename.setSelected(false);
                    tvShare.setSelected(false);
                    tvDel.setSelected(false);
                    tvReport.setSelected(false);
                }
                break;
            case R.id.menu_rename:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onRenameClick();
                    tvOften.setSelected(false);
                    tvMove.setSelected(false);
                    tvRename.setSelected(true);
                    tvShare.setSelected(false);
                    tvDel.setSelected(false);
                    tvReport.setSelected(false);
                }
                break;
            case R.id.menu_share:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onShareClick();
                    tvOften.setSelected(false);
                    tvMove.setSelected(false);
                    tvRename.setSelected(false);
                    tvShare.setSelected(true);
                    tvDel.setSelected(false);
                    tvReport.setSelected(false);
                }
                break;
            case R.id.menu_more:
                findViewById(R.id.ll_more).setVisibility(VISIBLE);
                break;
            case R.id.menu_del:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onDelClick();
                    tvOften.setSelected(false);
                    tvMove.setSelected(false);
                    tvRename.setSelected(false);
                    tvShare.setSelected(false);
                    tvDel.setSelected(true);
                    tvReport.setSelected(false);
                }
                break;
            case R.id.menu_report:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onReportClick();
                    tvOften.setSelected(false);
                    tvMove.setSelected(false);
                    tvRename.setSelected(false);
                    tvShare.setSelected(false);
                    tvDel.setSelected(false);
                    tvReport.setSelected(true);
                }
                break;
        }
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
