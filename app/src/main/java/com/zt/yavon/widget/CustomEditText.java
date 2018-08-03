package com.zt.yavon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zt.yavon.R;


/**
 * 自定义 登录 输入 框
 */
public class CustomEditText extends RelativeLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private Context context;
    private EditText editText;
    private ImageView drawbleIconView, drawbleDeleteView, drawbleVisableView,ivDivider;
    // 是否 是 密码 输入 框
    private boolean passwordTag;
    // 是否 显示 密码
    private boolean passwordDisplayTag;
    private TextWatcher textWatcher;
    private View editLayout;
    private int visibleDrawableOpen;
    private int visibleDrawableClose;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        getArrrs(attrs);
    }


    private void init() {
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.view_edittext_custom, this);
        editText = (EditText) viewGroup.findViewById(R.id.editText);
        drawbleIconView = (ImageView) viewGroup.findViewById(R.id.drawbleIconView);
        drawbleDeleteView = (ImageView) viewGroup.findViewById(R.id.drawbleDeleteView);
        drawbleVisableView = (ImageView) viewGroup.findViewById(R.id.drawbleVisableView);
        ivDivider = (ImageView) viewGroup.findViewById(R.id.iv_divider);
        editLayout =  viewGroup.findViewById(R.id.layout_edit);
        setListener();
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                drawbleIconView.setSelected(hasFocus);
                drawbleVisableView.setSelected(hasFocus);
                drawbleDeleteView.setSelected(hasFocus);
                ivDivider.setSelected(hasFocus);
            }
        });
    }

    private void setListener() {
        drawbleDeleteView.setOnClickListener(this);
        drawbleVisableView.setOnClickListener(this);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
    }

    private void getArrrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditTextStyle);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        passwordTag = typedArray.getBoolean(R.styleable.CustomEditTextStyle_passwordTag, false);

        int deleteDrawable = typedArray.getResourceId(R.styleable.CustomEditTextStyle_deleteDrawable,-1);
        int iconDrawable = typedArray.getResourceId(R.styleable.CustomEditTextStyle_iconDrawable,-1);
        visibleDrawableOpen = typedArray.getResourceId(R.styleable.CustomEditTextStyle_visibleDrawable_open,-1);
        visibleDrawableClose = typedArray.getResourceId(R.styleable.CustomEditTextStyle_visibleDrawable_close,-1);
        int hintColor = typedArray.getColor(R.styleable.CustomEditTextStyle_customHintColor,ContextCompat.getColor(getContext(),R.color.gray_text));
        editText.setHintTextColor(hintColor);
        String hintText = typedArray.getString(R.styleable.CustomEditTextStyle_customHint);
        editText.setHint(hintText);
        float textSize = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_textSize,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                14, metrics));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        int paddingLeft = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_drawablePadding,0);
        int marginLeft = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_paddingLeft,0);
        int marginRight = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_paddingRight,0);
        int paddingTop = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_paddingTop,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, metrics));
        int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.CustomEditTextStyle_paddingBottom,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, metrics));
        editText.setPadding(paddingLeft,paddingTop,0,paddingBottom);
        editLayout.setPadding(marginLeft,0,marginRight,0);
        showOrHideDrawableView(drawbleDeleteView, deleteDrawable);
        showOrHideDrawableView(drawbleIconView, iconDrawable);

        if(passwordDisplayTag){
            setIsPassword(passwordTag, visibleDrawableOpen);
        }else{
            setIsPassword(passwordTag, visibleDrawableClose);
        }
        // 先默认 隐藏
        drawbleVisableView.setVisibility(GONE);
        drawbleDeleteView.setVisibility(GONE);
    }

    public void setIsPassword(boolean passwordTag, int visibleDrawable) {
        if (passwordTag) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showOrHideDrawableView(drawbleVisableView, visibleDrawable);
        } else {
            showOrHideDrawableView(drawbleVisableView, -1);
        }
    }

//    public void setIconView(Drawable drawable) {
//        showOrHideDrawableView(drawbleIconView, drawable);
//    }


    /**
     * 显示 或 隐藏 图标
     *
     * @param imageView
     * @param drawable
     */
    public void showOrHideDrawableView(ImageView imageView, int drawable) {
        if (drawable == -1) {
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
            imageView.setImageResource(drawable);
        }
    }

    public Editable getText() {
        return editText.getText();
    }
    public EditText getEditTextView() {
        return editText;
    }
    public void setTextColor(int resColor){
        editText.setTextColor(ContextCompat.getColor(getContext(),resColor));
    }
    public void setHintTextColor(int resColor){
        editText.setHintTextColor(ContextCompat.getColor(getContext(),resColor));
    }
    public void setHint(String text){
        editText.setHint(text);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawbleDeleteView:
                editText.setText("");
                //暴露出去清空事件
                if(ondeleteClickListener!=null){
                    ondeleteClickListener.onClick();
                }
                break;
            case R.id.drawbleVisableView:
                int selection = editText.getSelectionStart();
                if (!passwordDisplayTag) {
                    // 设置 EditText 的 input type  显示 密码
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText.setSelection(selection);
                    drawbleVisableView.setImageResource(visibleDrawableOpen);
                } else {
                    // 隐藏 密码
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText.setSelection(selection);
                    drawbleVisableView.setImageResource(visibleDrawableClose);
                }
                passwordDisplayTag = !passwordDisplayTag;
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(textWatcher != null){
            textWatcher.beforeTextChanged(s,start,count,after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) {
            // 当 输入 字符 长度 不为0 时 显示 删除 按钮
            if(textWatcher != null){
                textWatcher.onTextChanged(s,start,before,count);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(textWatcher != null){
            textWatcher.afterTextChanged(s);
        }
        if (s.length() > 0) {
            drawbleDeleteView.setVisibility(VISIBLE);
            if (passwordTag)
                drawbleVisableView.setVisibility(VISIBLE);
        } else {
            drawbleDeleteView.setVisibility(GONE);
            drawbleVisableView.setVisibility(GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 如果 EditText的 焦点 改变了  则相应的 隐藏 显示 功能 按钮
        if (!hasFocus) {
            drawbleDeleteView.setVisibility(GONE);
            drawbleVisableView.setVisibility(GONE);
        } else if (editText.getText().length() > 0) {
            drawbleDeleteView.setVisibility(VISIBLE);
            if (passwordTag)
                drawbleVisableView.setVisibility(VISIBLE);
        }
    }

    //设置inputtype 我真是个天才
    public void setEditTextInputType(int type){
        editText.setInputType(type);
    }
    public void setEditTextHideText(String text){
        editText.setHint(text);
    }

    public void setEditTextText(String text){
        editText.setText(text);
    }


    //当清空时给activity事件
    public interface OnDeleteClickListener {
        void onClick();
    }
    private OnDeleteClickListener ondeleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickListener ondeleteClickListener) {
        this.ondeleteClickListener = ondeleteClickListener;
    }
    public void addTextChangedListener(TextWatcher watcher){
        this.textWatcher = watcher;
    }

}
