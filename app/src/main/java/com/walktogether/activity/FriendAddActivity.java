package com.walktogether.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/17.
 */

public class FriendAddActivity extends BaseActivity implements View.OnClickListener {
    private ImageView friendAddBackBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_around_add);
        friendAddBackBtn = (ImageView) findViewById(R.id.around_backBtn);
        friendAddBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.around_backBtn:
                finish();
                break;
        }
    }
}
