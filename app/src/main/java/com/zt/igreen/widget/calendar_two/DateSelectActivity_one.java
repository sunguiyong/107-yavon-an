package com.zt.igreen.widget.calendar_two;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.widget.calendar_two.Bean.calendars.SlideWayManager;
import com.zt.igreen.widget.calendar_two.Utils.DPMode;
/**
 * 描述：时间段选择
 * 时间：2018/1/27/027
 * 作者：xjh
 */
public class DateSelectActivity_one extends Activity {
    String time;
    private String str_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlideWayManager slideWayManager=new SlideWayManager(DateSelectActivity_one.this,R.layout.activity_calendar_select_one);
        str_style = getIntent().getStringExtra("style");
        View view=slideWayManager.SlideMode(DPMode.VERTICAL);
        setContentView(view);
        TextView btnback=view.findViewById(R.id.btn_back_header);
        TextView tvright=view.findViewById(R.id.tv_right_header);
        slideWayManager.setCalendarClickListener(new SlideWayManager.OnCalendarClickListener() {
            @Override
            public void setOnCalendarClickListener(String startDate) {
                time=startDate;
                tvright.setVisibility(View.VISIBLE);
            }
        });
        ImmersionBar.with(this)
                .statusBarColor(R.color.white).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.white).init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sure();
            }
        });
    }

    public static void startAction(Activity context, int reqCode,String  style){
        Intent intent = new Intent(context,DateSelectActivity_one.class);
        intent.putExtra("style",style);
        context.startActivityForResult(intent,reqCode);
    }


    public void sure() {
        Intent intent = new Intent();
        intent.putExtra("startDate",time);
        intent.putExtra("style",str_style);
        setResult(RESULT_OK,intent);
        finish();
    }
}
