package com.walktogether.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView settingBackBtn;
    private Button personDataChangeBtn,appSettingBtn,exitBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        settingBackBtn = (ImageView) findViewById(R.id.setting_backBtn);
        settingBackBtn.setOnClickListener(this);
        personDataChangeBtn = (Button) findViewById(R.id.setting_personDataChangeBtn);
        personDataChangeBtn.setOnClickListener(this);
        appSettingBtn = (Button) findViewById(R.id.setting_appSettingBtn);
        appSettingBtn.setOnClickListener(this);
        exitBtn = (Button) findViewById(R.id.setting_exitBtn);
        exitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_backBtn:
                finish();
                break;
            case R.id.setting_personDataChangeBtn:
                break;
            case R.id.setting_appSettingBtn:
                break;
            case R.id.setting_exitBtn:
                break;
        }
    }
}
