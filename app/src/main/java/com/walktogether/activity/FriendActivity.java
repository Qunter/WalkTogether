package com.walktogether.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.walktogether.R;
import com.walktogether.adapter.FriendPagerAdapter;
import com.walktogether.base.BaseActivity;
import com.walktogether.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2017/5/15.
 */

public class FriendActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView friendBackBtn,friendAddBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend);
        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) findViewById(R.id.friendViewPaper);
        FriendPagerAdapter adapter = new FriendPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.friendTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //左上角返回键
        friendBackBtn = (ImageView) findViewById(R.id.friend_backBtn);
        friendBackBtn.setOnClickListener(this);
        //右上角添加好友按键
        friendAddBtn = (ImageView) findViewById(R.id.friend_addBtn);
        friendAddBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.friend_backBtn:
                finish();
                break;
            case R.id.friend_addBtn:
                startActivity(FriendAddActivity.class);
                finish();
                break;
        }
    }
}
