package com.zt.yavon.module.message.view;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/8/24.
 */

public class MessageDetailActivity extends BaseActivity{
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String title2,content;
    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_detail;
    }

    @Override
    public void initPresenter() {
        title2 = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
    }

    @Override
    public void initView() {
        setTitle("消息详情");
        tvTitle2.setText(title2);
        tvContent.setText(content);
    }
    public static void startAction(Context context, String title,String content) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }
}
