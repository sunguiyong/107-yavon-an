package com.zt.yavon.widget.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.yavon.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018/1/27/027
 * 作者：xjh
 */
public class MonthDateSelectAdapter extends RecyclerView.Adapter<MonthDateSelectAdapter.Holder> {

    private Context context;
    private List<List<DayBean>> dataList;
    private List<MonthBean> list;
//    private List<DayBean> currList;
//    private List<DayBean> nextList;
    private onDateSelectListener listener;
    private DayDateSelectAdapter adapter;

    public MonthDateSelectAdapter(Context context, List<MonthBean> list, List<List<DayBean>> dataList) {
        this.context = context;
        this.list = list;
        this.dataList = dataList;
    }


    public interface onDateSelectListener{
        void onSelect(int pos, int month);
    }

    public void setListener(onDateSelectListener listener) {
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dateselect_month, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

//        if (position==0) {
            RecyclerView mRecyclerView = holder.mRecyclerView;
            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 7));
            adapter = new DayDateSelectAdapter(context,dataList.get(holder.getAdapterPosition()));
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);

            adapter.setListener(new DayDateSelectAdapter.onDateSelectListener() {
                @Override
                public void onSelect(int pos) {
                    if(listener!=null) {
                        listener.onSelect(pos,holder.getAdapterPosition());
                    }
                }
            });

        MonthBean entity = list.get(position);
        holder.tv_year_month.setText(entity.getYear() + "年" + entity.getMonth()+"月");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv_year_month;
        RecyclerView mRecyclerView;
        Holder(View itemView) {
            super(itemView);
            tv_year_month = (TextView) itemView.findViewById(R.id.tv_year_month);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mRecyclerView);
        }
    }

}
