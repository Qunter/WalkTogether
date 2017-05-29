package com.walktogether.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SettingAppActivity extends BaseActivity implements View.OnClickListener {
    private Button appSaveBtn;
    private EditText appSizeEt,appRangeEt;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting_app);
        appSaveBtn = (Button) findViewById(R.id.setting_app_saveBtn);
        appSaveBtn.setOnClickListener(this);
        appSizeEt = (EditText) findViewById(R.id.setting_app_sizeEt);
        appRangeEt = (EditText) findViewById(R.id.setting_app_rangeEt);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_app_saveBtn:
                saveAppSettingChange();
                break;
        }
    }
    /**
     * 储存软件相关设置
     */
    private void saveAppSettingChange(){
        if (appSizeEt.getText().toString().equals("")&&appRangeEt.getText().toString().equals("")){
            Toast.makeText(this,"您未修改任何设置",Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences appSettings = getSharedPreferences("appSettings", 0);
            SharedPreferences.Editor editor = appSettings.edit();
            if(!appSizeEt.getText().toString().equals("")){
                editor.putInt("aroundSearchSize",Integer.getInteger(appSizeEt.getText().toString()));
            }
            if(!appRangeEt.getText().toString().equals("")){
                editor.putInt("aroundSearchRange",Integer.getInteger(appRangeEt.getText().toString()));
            }
            editor.apply();
            finish();
        }
    }
}
