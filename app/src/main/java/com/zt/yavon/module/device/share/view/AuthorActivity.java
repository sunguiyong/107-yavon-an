package com.zt.yavon.module.device.share.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.share.adapter.RoomNameAdapter;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class AuthorActivity extends BaseActivity{

    @Override
    public int getLayoutId() {
        return R.layout.activity_author;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("临时授权");
//        setRightMenuImage(R.mipmap.more_right);
    }

//    @OnClick({R.id.tv_count_apply,R.id.tv_day_apply,R.id.tv_month_apply,R.id.tv_year_apply,R.id.tv_custom_apply})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        List<Integer> list = new ArrayList<>();
//        for(int i = 0;i<50;i++){
//            list.add(i);
//        }
//        switch (view.getId()){
//            case R.id.tv_count_apply:
//
//                dialog = DialogUtil.createTimeWheelViewDialog(this, "次", null, list, new DialogUtil.OnSelectCompleteListening() {
//                    @Override
//                    public void onSelectComplete(int data) {
//                        tvNum.setText(data+"");
//                        tvUnit.setText("次");
//                    }
//                });
//                break;
//        }
//    }
    public static void startAction(Context context){
        Intent intent = new Intent(context, AuthorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
