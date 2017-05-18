package com.walktogether.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.walktogether.R;
import com.walktogether.base.BaseActivity;
import com.walktogether.entity.UserFriend;
import com.walktogether.entity.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/5/17.
 */

public class FriendAddActivity extends BaseActivity implements View.OnClickListener{
    private EditText friendAddEt;
    private TextView friendAddTv;
    private ImageView friendAddBackBtn;
    private LinearLayout friendAddLinLayout,friendAddAskView;
    private String loginUserFriendObjectID="",loginUserFriendFriendObjectID="";
    private UserInfo searchUser;
    private String searchUserAvatarUrl="",searchUserNickname="";
    //用于标记是否已显示用户信息
    private int flag = 0;
    final int ADDFRIENDASK = 0x00, DELFRIENDASK = 0x01, IFGETFRIENDLIST = 0x02, SEARCHUSERINFO = 0x03 ,SAVEUSERFRIENDLIST=0x04,GETFRIENDFRIENDOBJECT=0x05,SAVEFRIENDFRIENDLIST=0x06;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IFGETFRIENDLIST:
                    searchUserFriendObjectId();
                    break;
                case SEARCHUSERINFO:
                    searchUserInfo(friendAddEt.getText().toString());
                    break;
                case ADDFRIENDASK:
                    if (flag == 1) {
                        friendAddLinLayout.removeView(friendAddAskView);
                        flag = 0;
                    }
                    friendAddLinLayout.addView(addFriendAddAskView(searchUserAvatarUrl, searchUserNickname));
                    flag = 1;
                    break;
                case SAVEUSERFRIENDLIST:
                    addFriend();
                    break;
                case DELFRIENDASK:
                    friendAddLinLayout.removeView(friendAddAskView);
                    break;
                case GETFRIENDFRIENDOBJECT:
                    searchFriendFriendInformation();
                    break;
                case SAVEFRIENDFRIENDLIST:
                    saveFriendFriendList();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {

    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_add);
        friendAddEt = (EditText) findViewById(R.id.friend_addEt);
        friendAddTv = (TextView) findViewById(R.id.friend_addBtn);
        friendAddBackBtn = (ImageView) findViewById(R.id.friend_backBtn);
        friendAddLinLayout = (LinearLayout) findViewById(R.id.friend_add_linearlayout);

        friendAddTv.setOnClickListener(this);
        friendAddBackBtn.setOnClickListener(this);
        handler.sendEmptyMessage(IFGETFRIENDLIST);
    }
    /**
     * bmob查询登录用户好友表ID
     */
    private void searchUserFriendObjectId() {
        final BmobQuery<UserFriend> query = new BmobQuery<UserFriend>();
        query.addWhereEqualTo("userID", BmobUser.getCurrentUser(UserInfo.class).getUserPhone());
        query.findObjects(new FindListener<UserFriend>() {
            @Override
            public void done(List<UserFriend> object, BmobException e) {
                if (e == null) {
                    loginUserFriendObjectID = object.get(0).getObjectId();
                }
            }
        });
    }
    /**
     * bmob查询某用户信息
     */
    private void searchUserInfo(String userNickname) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("userNickname", userNickname);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if (e == null) {
                    searchUser = object.get(0);
                    searchUserAvatarUrl = searchUser.getUserAvatar();
                    searchUserNickname = searchUser.getUserNickname();
                    handler.sendEmptyMessage(ADDFRIENDASK);
                    //Toast.makeText(getApplicationContext(), user.getNickName()+"查询成功", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "查询失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 动态生成是否添加好友（显示用户信息）的控件
     */
    private LinearLayout addFriendAddAskView(String searchUserAvatarUrl, String searchUserNickname) {
        friendAddAskView = new LinearLayout(this);
        friendAddAskView.setOrientation(LinearLayout.HORIZONTAL);
        friendAddAskView.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        friendAddAskView.setLayoutParams(llparams);
        ImageView touxiang = new ImageView(this);
        Glide.with(getApplicationContext()).load(searchUserAvatarUrl).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(touxiang);
        LinearLayout.LayoutParams txparams = new LinearLayout.LayoutParams(100, 100);
        txparams.setMargins(30, 15, 15, 15);
        touxiang.setLayoutParams(txparams);
        LinearLayout.LayoutParams unparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        unparams.setMargins(30, 0, 30, 0);
        TextView userName = new TextView(this);
        userName.setText(searchUserNickname);
        userName.setLayoutParams(unparams);
        userName.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnparams.setMargins(30, 0, 30, 0);
        Button add = new Button(this);
        //add.setBackgroundResource(R.drawable.background_button);
        add.setTextColor(Color.WHITE);
        add.setGravity(Gravity.CENTER);
        add.setBackground(getResources().getDrawable(R.drawable.ripple_button,null));
        add.setText("添加好友");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(SAVEUSERFRIENDLIST);
            }
        });
        add.setLayoutParams(btnparams);
        Button noAdd = new Button(this);
        //noAdd.setBackgroundResource(R.drawable.background_button);
        noAdd.setTextColor(Color.WHITE);
        noAdd.setGravity(Gravity.CENTER);
        noAdd.setBackground(getResources().getDrawable(R.drawable.ripple_button,null));
        noAdd.setText("取消");
        noAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(DELFRIENDASK);
            }
        });
        noAdd.setLayoutParams(btnparams);
        friendAddAskView.addView(touxiang);
        friendAddAskView.addView(userName);
        friendAddAskView.addView(add);
        friendAddAskView.addView(noAdd);
        return friendAddAskView;
    }
    /**
     * 储存登录用户好友关系
     */
    private void addFriend(){
        UserFriend userFriend = new UserFriend();
        userFriend.setObjectId(loginUserFriendObjectID);
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(searchUser);
        //多对多关联指向`post`的`likes`字段
        userFriend.setUserFriend(relation);
        userFriend.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //Toast.makeText(getApplicationContext(), "更新登录用户好友列表成功", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(GETFRIENDFRIENDOBJECT);
                } else {
                    //Toast.makeText(getApplicationContext(), e+"添加失败", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), loginUserFriendObjectID, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * bmob查询想添加为好友的用户的好友信息
     */
    private void searchFriendFriendInformation() {
        final BmobQuery<UserFriend> query = new BmobQuery<UserFriend>();
        query.addWhereEqualTo("userID", searchUser.getUserPhone());
        query.findObjects(new FindListener<UserFriend>() {
            @Override
            public void done(List<UserFriend> object, BmobException e) {
                if (e == null) {
                    loginUserFriendFriendObjectID = object.get(0).getObjectId();
                    handler.sendEmptyMessage(SAVEFRIENDFRIENDLIST);
                } else {

                }
            }
        });
    }
    /**
     * 储存好友用户好友关系
     */
    private void saveFriendFriendList(){
        UserFriend userFriend = new UserFriend();
        userFriend.setObjectId(loginUserFriendFriendObjectID);
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(BmobUser.getCurrentUser(UserInfo.class));
        //多对多关联指向`post`的`likes`字段
        userFriend.setUserFriend(relation);
        userFriend.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "成功添加好友", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //Toast.makeText(getApplicationContext(), e+"添加失败", Toast.LENGTH_SHORT).show();
                    Log.e("错误",e+"" );
                    Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friend_addBtn:
                if(BmobUser.getCurrentUser(UserInfo.class).getUserNickname().equals(friendAddEt.getText().toString())){
                    Toast.makeText(getApplicationContext(), "不能添加自己为好友", Toast.LENGTH_SHORT).show();
                    break;
                }
                handler.sendEmptyMessage(SEARCHUSERINFO);
                break;
            case R.id.friend_backBtn:
                finish();
                break;
        }
    }
}
