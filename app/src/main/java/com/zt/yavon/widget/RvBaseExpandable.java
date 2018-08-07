package com.zt.yavon.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zt.yavon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjd on 2016/9/23.
 */
public abstract class RvBaseExpandable<T extends MultiItemEntity> extends RecyclerView {
    public View mLoadingView, mEmptyView, mErrorView;

    public RvBaseExpandable(Context context) {
        this(context, null);
    }

    public RvBaseExpandable(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RvBaseExpandable(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    public void init(Context context) {
        setLayoutManager(customSetLayoutManager(context));

        mLoadingView = LayoutInflater.from(context).inflate(R.layout.rv_loading_layout, this, false);
        mEmptyView = LayoutInflater.from(context).inflate(R.layout.rv_empty_data_layout, this, false);
        mErrorView = LayoutInflater.from(context).inflate(R.layout.rv_error_layout, this, false);
    }

    public void setEmptyViewText(String content, boolean withRetryTip) {
        setEmptyViewText(content, withRetryTip, 0);
    }

    public void setEmptyViewText(String content, boolean withRetryTip, int drawableTopResId) {
        if (withRetryTip) {
            content = content + "\n点击屏幕重试";
        }
        TextView textView = ((TextView) mEmptyView.findViewById(R.id.tv_msg));
        if (textView != null) {
            textView.setText(content);
            if (drawableTopResId != 0) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, drawableTopResId, 0, 0);
                textView.setCompoundDrawablePadding(30);
            }
        }
    }

    public void setLoadingViewText(String content) {
        if (!TextUtils.isEmpty(content)) {
            TextView textView = ((TextView) mLoadingView.findViewById(R.id.tv_msg));
            if (textView != null) {
                textView.setText(content);
            }
        }
    }

    public void setErrorViewText(String content, boolean withRetryTip) {
        if (withRetryTip) {
            content = content + "\n点击屏幕重试";
        }
        TextView textView = ((TextView) mErrorView.findViewById(R.id.tv_msg));
        if (textView != null) {
            textView.setText(content);
        }
    }

    public abstract LayoutManager customSetLayoutManager(Context context);

    public abstract SparseIntArray customSetItemLayoutIds();

    public abstract void customConvert(BaseViewHolder holder, Object bean);

    public void setData(List<T> data) {
        mAdapter = new CustomAdapter(data);
        setAdapter(mAdapter);
        if (data == null || data.size() == 0) {
            showEmptyView();
        }
        mAdapter.expandAll();
    }

    public void addData(T bean) {
        List<T> newData = new ArrayList<>();
        newData.add(bean);
        addData(newData);
    }

    public void addData(List<T> data) {
        mAdapter.addData(data);
    }

    public CustomAdapter mAdapter;

    public class CustomAdapter extends BaseMultiItemQuickAdapter {
        public CustomAdapter(List<T> data) {
            super(data);
            SparseIntArray layouts = customSetItemLayoutIds();
            for (int i = 0; i < layouts.size(); i++) {
                addItemType(layouts.keyAt(i), layouts.valueAt(i));
            }
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Object t) {
            customConvert(baseViewHolder, t);
        }
    }

    public void showLoadingView() {
        mAdapter.setEmptyView(mLoadingView);
    }

    public void showEmptyView() {
        mAdapter.setEmptyView(mEmptyView);
    }

//    public void showErrorView() {
//        mAdapter.setEmptyView(mErrorView);
//    }

    public void showLoadingView(String content) {
        setLoadingViewText(content);
        mAdapter.setEmptyView(mLoadingView);
    }

    public void showEmptyView(String content, boolean withRetryTip) {
        setEmptyViewText(content, withRetryTip);
        mAdapter.setEmptyView(mEmptyView);
    }

    public void showErrorView(String content, boolean withRetryTip) {
        setErrorViewText(content, withRetryTip);
        mAdapter.setEmptyView(mErrorView);
    }

    public void clearEmptyView() {
        mAdapter.setEmptyView(new View(getContext()));
    }

    public void setOnEmptyViewClickListener(OnClickListener listener) {
        mEmptyView.setOnClickListener(listener);
        mErrorView.setOnClickListener(listener);
    }

    public void onGetDataSuccess(List<T> beans) {
        setData(beans);
        if (beans == null) {
            // showErrorView("数据出错", true);
            showEmptyView();
        } else if (beans.size() == 0) {
            showEmptyView();
        } else {
            clearEmptyView();
        }
    }

    public void onGetDataSuccess(boolean hasMore, List<T> beans) {
        setHasMore(hasMore);
        onGetDataSuccess(beans);
    }

    public void onGetDataSuccess(boolean hasMore, List<T> beans, BaseMultiItemQuickAdapter.RequestLoadMoreListener loadMoreListener) {
        setHasMore(hasMore);
        onGetDataSuccess(beans);
        if (hasMore) {
            mAdapter.setOnLoadMoreListener(loadMoreListener);
        }
    }

    public void onGetDataFail(String content, boolean withRetryTip) {
        showErrorView(content, withRetryTip);
    }

    public void onLoadMoreSuccess(List<T> beans) {
        if (beans == null) {
            mAdapter.loadMoreFail();
        } else if (beans.size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            addData(beans);
            mAdapter.loadMoreComplete();
        }
    }

    public void onLoadMoreSuccess(boolean hasMore, List<T> beans) {
        setHasMore(hasMore);
        onLoadMoreSuccess(beans);
    }

    public void onLoadMoreFail() {
        mAdapter.loadMoreFail();
    }

    public void onLoadMoreFail(String error) {
        mAdapter.loadMoreFail();
        Toast.makeText(getContext(), error + "", Toast.LENGTH_LONG).show();
    }

    public void setHasMore(boolean hasMore) {
        if (hasMore) {
            mAdapter.loadMoreEnd(false);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }
}
