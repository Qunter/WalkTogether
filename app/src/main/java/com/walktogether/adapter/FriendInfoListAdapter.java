package com.walktogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.walktogether.R;
import com.walktogether.entity.UserInfo;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/4/21.
 * 好友列表adapter
 */

public class FriendInfoListAdapter extends RecyclerView.Adapter<FriendInfoListAdapter.ViewHolder>{
    private List<UserInfo> loginUserFriendInfoList;
    private Context context;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView userAvatarImg;
        private TextView userNameTv;
        public ViewHolder(View view){
            super(view);
            userAvatarImg = (ImageView) view.findViewById(R.id.item_useravatar);
            userNameTv = (TextView) view.findViewById(R.id.item_username);
        }
    }
    public FriendInfoListAdapter(Context context, List<UserInfo> loginUserFriendInfoList, RecyclerView recyclerView){
        this.context = context;
        this.loginUserFriendInfoList = loginUserFriendInfoList;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_info,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FriendInfoListAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final UserInfo friendInfo = loginUserFriendInfoList.get(position);
        Glide.with(context).load(friendInfo.getUserAvatar()).bitmapTransform(new CropCircleTransformation(context)).into(holder.userAvatarImg);
        holder.userNameTv.setText(friendInfo.getUserNickname());
        if(onItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return loginUserFriendInfoList.size();
    }
    /**
     * 点击事件接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    /**
     * 设置点击事件方法
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
