package com.walktogether.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView settingBackBtn;
    private Button personDataChangeBtn,appSettingBtn,exitBtn;
    private final int EXITAPP=0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case EXITAPP:
                    exitApp();
                    break;
            }
        }
    };
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
                startActivity(SettingUserInfoActivity.class);
                break;
            case R.id.setting_appSettingBtn:
                startActivity(SettingAppActivity.class);
                break;
            case R.id.setting_exitBtn:
                Toast.makeText(getApplicationContext(),"退出登录成功  请重新运行程序", Toast.LENGTH_LONG).show();
                handler.sendEmptyMessageDelayed(EXITAPP,1500);
                break;
        }
    }

    /**
     * 退出登录方法
     */
    private void exitApp(){
        BmobUser.logOut();   //清除缓存用户对象
        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
        System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
    }
}
