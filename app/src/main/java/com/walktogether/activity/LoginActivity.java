package com.walktogether.activity;

import android.os.Bundle;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void initVariablesAndService() {
        super.setActivityImmersive(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }
}
