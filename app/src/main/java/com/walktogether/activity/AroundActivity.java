package com.walktogether.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AroundActivity extends BaseActivity implements View.OnClickListener {
    private ImageView aroundBackBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_around);
        aroundBackBtn = (ImageView) findViewById(R.id.around_backBtn);
        aroundBackBtn.setOnClickListener(this);
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
