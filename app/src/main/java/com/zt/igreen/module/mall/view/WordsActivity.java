package com.zt.igreen.module.mall.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.mall.contract.WordContract;
import com.zt.igreen.module.mall.presenter.WordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class WordsActivity extends BaseActivity<WordPresenter> implements WordContract.View {
    @BindView(R.id.compandy_name)
    EditText compandyName;
    @BindView(R.id.phone_person)
    EditText phonePerson;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.lin_dian)
    LinearLayout linDian;
    @BindView(R.id.ed_liuyan)
    EditText edLiuyan;
    @BindView(R.id.tv_length)
    TextView tvLength;
    private String device_id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_words;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        device_id = getIntent().getStringExtra("id");
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        sethead(R.color.touming);
        setColor(Color.parseColor("#ffffff"));
        setTitle("留言");
        edLiuyan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //长度发生变化，监听到输入的长度为 editText.getText().length()
                tvLength.setText(String.valueOf(edLiuyan.getText().length()) + "/500");
            }
        });

    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, WordsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_login)
    public void onClick() {
        String edLiu_yan = edLiuyan.getText().toString().trim();
        String compandy_name = compandyName.getText().toString().trim();
        String phone_Person = phonePerson.getText().toString().trim();
        String phone_Number = phoneNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(edLiu_yan) && TextUtils.isEmpty(compandy_name) && TextUtils.isEmpty(phone_Person) && TextUtils.isEmpty(phone_Number)) {
            ToastUtil.show(WordsActivity.this, "公司名称,联系人,电话号码不能为空", Toast.LENGTH_LONG);
        } else if (!TextUtils.isEmpty(edLiu_yan) && !TextUtils.isEmpty(compandy_name) && TextUtils.isEmpty(phone_Person) && TextUtils.isEmpty(phone_Number)) {
            ToastUtil.show(WordsActivity.this, "联系人,电话号码不能为空", Toast.LENGTH_LONG);
        } else if (!TextUtils.isEmpty(edLiu_yan) && !TextUtils.isEmpty(compandy_name) && !TextUtils.isEmpty(phone_Person) && TextUtils.isEmpty(phone_Number)) {
            ToastUtil.show(WordsActivity.this, "电话号码不能为空", Toast.LENGTH_LONG);
        } else if (TextUtils.isEmpty(edLiu_yan) && TextUtils.isEmpty(compandy_name) && TextUtils.isEmpty(phone_Person) && TextUtils.isEmpty(phone_Number)) {
            ToastUtil.show(WordsActivity.this, "留言板,公司名称,联系人,电话号码不能为空", Toast.LENGTH_LONG);
        } else {
            mPresenter.getWord(device_id, edLiu_yan, compandy_name, phone_Person, phone_Number);
        }
    }


    @Override
    public void returnWord() {
        finish();
    }

}
