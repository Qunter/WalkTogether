package com.walktogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.walktogether.R;
import com.walktogether.entity.PoiInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class AroundListAdapter extends RecyclerView.Adapter<AroundListAdapter.ViewHolder> {
    private List<PoiInfo> poiInfoList;
    private Context context;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView aroundTitleTv,aroundAddressTv,aroundDistanceTv;

        public ViewHolder(View view) {
            super(view);
            aroundTitleTv = (TextView) view.findViewById(R.id.item_around_titleTv);
            aroundAddressTv = (TextView) view.findViewById(R.id.item_around_addressTv);
            aroundDistanceTv = (TextView) view.findViewById(R.id.item_around_distance);
        }
    }

    public AroundListAdapter(Context context, List<PoiInfo> poiInfoList, RecyclerView recyclerView) {
        this.context = context;
        this.poiInfoList = poiInfoList;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_around_info, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AroundListAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.aroundTitleTv.setText(poiInfoList.get(position).getTitle());
        holder.aroundAddressTv.setText(poiInfoList.get(position).getSnippet());
        holder.aroundDistanceTv.setText(poiInfoList.get(position).getDistance()+"米");
        if (onItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return poiInfoList.size();
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 设置点击事件方法
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}