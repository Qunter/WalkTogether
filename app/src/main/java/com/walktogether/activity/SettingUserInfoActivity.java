package com.walktogether.activity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;
import com.walktogether.entity.UserInfo;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SettingUserInfoActivity extends BaseActivity implements View.OnClickListener {
    private EditText nicknameEt;
    private Button saveChangeBtn;
    private ImageView settingUserInfoBackBtn;
    private final int FINISH=0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FINISH:
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting_user_info);
        nicknameEt = (EditText) findViewById(R.id.setting_user_info_nicknameEt);
        saveChangeBtn = (Button) findViewById(R.id.setting_user_info_saveChangeBtn);
        settingUserInfoBackBtn = (ImageView) findViewById(R.id.setting_user_info_backBtn);

        saveChangeBtn.setOnClickListener(this);
        settingUserInfoBackBtn.setOnClickListener(this);
    }
    /**
     * 更改个性签名，年龄，职业
     */
    private void updata() {
        UserInfo newUser = new UserInfo();
        if (nicknameEt.getText() != null) {
            newUser.setUserNickname(nicknameEt.getText().toString());//设置昵称
        }else{
            Toast.makeText(getApplicationContext(), "输入为空  请确认后再更改",Toast.LENGTH_SHORT).show();
        }
        UserInfo loginUser = UserInfo.getCurrentUser(UserInfo.class);
        newUser.update(loginUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(FINISH);
                } else {
                    Log.e("bmob储存修改失败", e + "");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_user_info_saveChangeBtn:
                updata();
                break;
            case R.id.setting_user_info_backBtn:
                finish();
                break;
        }
    }
}
