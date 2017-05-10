package com.walktogether.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;
import com.walktogether.entity.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private Button loginBtn,registerBtn;
    private EditText usernameEt,passwordEt;
    @Override
    protected void initVariablesAndService() {
        //初始化bmob
        Bmob.initialize(this,"f7eae530432178be8018d9c6347127c4");
        super.setActivityImmersive(true);

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.login_loginBtn);
        registerBtn = (Button) findViewById(R.id.login_registerBtn);
        usernameEt = (EditText) findViewById(R.id.login_usernameEt);
        passwordEt = (EditText) findViewById(R.id.login_passwordEt);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_loginBtn:
                login();
                break;
            case R.id.login_registerBtn:
                startActivity(RegisterActivity.class);
                break;
        }
    }
    /**
     * 在bmob数据库上验证账号并登录
     */
    private void login(){
        UserInfo userInformation = new UserInfo();
        userInformation.setUsername(usernameEt.getText().toString());
        userInformation.setPassword(passwordEt.getText().toString());
        userInformation.login(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if(e==null){
                    startActivity(MainActivity.class);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this,"账号或密码错误  请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
