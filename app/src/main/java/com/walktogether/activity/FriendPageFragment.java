package com.walktogether.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.walktogether.R;
import com.walktogether.adapter.FriendInfoListAdapter;
import com.walktogether.entity.Friend;
import com.walktogether.entity.UserFriend;
import com.walktogether.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/4/20.
 */

public class FriendPageFragment extends Fragment implements RongIM.UserInfoProvider{
    private SwipeRefreshLayout friendSwipeRefreshLayout;
    private FriendInfoListAdapter adapter;
    private List<UserInfo> loginUserFriendInfoList;
    private List<Friend> loginUserFriendRongList;
    private RecyclerView friendRecyclerView;
    private String loginUserFriendObjectID="";
    private final int GETLOGINUSERFRIENDOBJECTID=0x00,GETLOGINUSERFRIENDLIST=0x01,FRIENDINFORMATIONLISTDOWNOVER=0x02;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETLOGINUSERFRIENDOBJECTID:
                    getLoginUserFriendObjectID();
                    break;
                case GETLOGINUSERFRIENDLIST:
                    getLoginUserFriendList();
                    break;
                case FRIENDINFORMATIONLISTDOWNOVER:
                    loadFriendRecycleView();
                    initUserInfo();
                    friendSwipeRefreshLayout.setRefreshing(false);
                    break;

            }
        }
    };
    public static FriendPageFragment newInstance() {
        FriendPageFragment fragment = new FriendPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_friend_list,container,false);
        friendSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_friend);
        friendSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(GETLOGINUSERFRIENDLIST);
            }
        });
        friendRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_friend);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        friendRecyclerView.setLayoutManager(layoutManager);
        handler.sendEmptyMessage(GETLOGINUSERFRIENDOBJECTID);
        return view;
    }
    /**
     * 获取用户好友列表的数据库表单中ID
     */
    private void getLoginUserFriendObjectID(){
        final BmobQuery<UserFriend> userObjectIDQuery = new BmobQuery<UserFriend>();
        userObjectIDQuery.addWhereEqualTo("userID", BmobUser.getCurrentUser(UserInfo.class).getUserPhone());
        userObjectIDQuery.findObjects(new FindListener<UserFriend>() {
            @Override
            public void done(List<UserFriend> object, BmobException e) {
                if(e==null){
                    loginUserFriendObjectID =object.get(0).getObjectId();
                    //Toast.makeText(getContext(), "loginUserFriendObjectID"+loginUserFriendObjectID, Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(GETLOGINUSERFRIENDLIST);
                }else{
                }
            }
        });

    }
    /**
     *获取用户好友列表数据
     */
    private void getLoginUserFriendList(){
        UserFriend userFriend = new UserFriend();
        // 查询好友列表内的所有用户，因此查询的是用户表
        BmobQuery<UserInfo> userFriendQuery = new BmobQuery<UserInfo>();
        userFriend.setObjectId(loginUserFriendObjectID);
        //userFriend是UserFriend表中的字段，用来存储所有该用户的好友关系的用户
        userFriendQuery.addWhereRelatedTo("userFriend", new BmobPointer(userFriend));
        userFriendQuery.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object,BmobException e) {
                if(e==null){
                    loginUserFriendInfoList=object;
                    //Toast.makeText(getContext(), "成功加载好友列表数据"+userFriendInformationList.size(), Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(FRIENDINFORMATIONLISTDOWNOVER);
                }else{
                    Toast.makeText(getContext(), "加载好友列表数据失败"+e, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    /**
     * 加载好友信息至RecycleView
     */
    private void loadFriendRecycleView(){
        adapter = new FriendInfoListAdapter(getContext(),loginUserFriendInfoList,friendRecyclerView);
        adapter.setOnItemClickListener(new FriendInfoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RongIM.getInstance().startPrivateChat(getContext(), loginUserFriendInfoList.get(position).getUserPhone(), loginUserFriendInfoList.get(position).getUserNickname());
            }
        });
        friendRecyclerView.setAdapter(adapter);
    }
    /**
     * 储存好友相关信息：id，昵称，token
     */
    private void initUserInfo() {
        loginUserFriendRongList = new ArrayList<Friend>();
        loginUserFriendRongList.add(new Friend(BmobUser.getCurrentUser(UserInfo.class).getUserPhone(), BmobUser.getCurrentUser(UserInfo.class).getUserNickname(), BmobUser.getCurrentUser(UserInfo.class).getUserAvatar()));
        for(UserInfo userFriendInfo:loginUserFriendInfoList){
            //好友内容为:id,昵称,头像url
            loginUserFriendRongList.add(new Friend(userFriendInfo.getUserPhone(),userFriendInfo.getUserNickname(),userFriendInfo.getUserAvatar()));
        }
        RongIM.setUserInfoProvider(this, true);
    }
    @Override
    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
        for (Friend i : loginUserFriendRongList) {
            if (i.getUserId().equals(s)) {
                //Log.e(TAG, i.getPortraitUri());
                return new io.rong.imlib.model.UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }
        return null;
    }
}