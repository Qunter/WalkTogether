package com.walktogether.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walktogether.R;
import com.walktogether.base.BaseActivity;
import com.walktogether.entity.UserFriend;
import com.walktogether.entity.UserInfo;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.rong.methods.User;

/**
 * Created by Administrator on 2017/5/7.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText usernameEt,passwordEt,userPhoneEt,userNicknameEt;
    private Button registerBtn,loginBtn;
    private UserInfo userInformation;
    private String rongToken="",userID="";
    private final int ISREGISTER=0x00,GETRONGTOKEN=0x01,ALLSUCCESS=0x02,FAIL=0x03,BUILDUSERFRIENDLIST=0x04;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ISREGISTER:
                    isRegister();
                    break;
                case GETRONGTOKEN:
                    new getRongToken(userPhoneEt.getText().toString().trim(),userNicknameEt.getText().toString().trim()).start();
                    break;
                case BUILDUSERFRIENDLIST:
                    buildUserFriendList();
                    break;
                case ALLSUCCESS:
                    register();
                    break;
                case FAIL:
                    Toast.makeText(RegisterActivity.this,"获取Token失败  请检查网络和账号后重试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {
        setActivityImmersive(true);
        Bmob.initialize(this,"f7eae530432178be8018d9c6347127c4");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        usernameEt = (EditText) findViewById(R.id.register_usernameEt);
        passwordEt = (EditText) findViewById(R.id.register_passwordEt);
        userPhoneEt = (EditText) findViewById(R.id.register_userPhoneEt);
        userNicknameEt = (EditText) findViewById(R.id.register_userNicknameEt);
        registerBtn = (Button) findViewById(R.id.register_registerBtn);
        registerBtn.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.register_loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_registerBtn:
                userID = userPhoneEt.getText().toString().trim();
                handler.sendEmptyMessage(ISREGISTER);
                break;
            case R.id.register_loginBtn:
                startActivity(LoginActivity.class);
                this.finish();
                break;
        }
    }
    /**
     * 判断账号是否已被注册
     */
    private void isRegister(){
        String username = usernameEt.getText().toString().trim();
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("username",username);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e==null&&list.size()==0){
                    handler.sendEmptyMessage(GETRONGTOKEN);
                }else {
                    Toast.makeText(RegisterActivity.this,"您的账号已被使用  请更换", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 注册账号至bmob
     */
    private void register(){
        userInformation = new UserInfo();
        userInformation.setUsername(usernameEt.getText().toString().trim());
        userInformation.setPassword(passwordEt.getText().toString().trim());
        userInformation.setUserPhone(userPhoneEt.getText().toString().trim());
        userInformation.setUserNickname(userNicknameEt.getText().toString().trim());
        userInformation.setRongToken(rongToken);
        userInformation.signUp(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo s, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    userInformation.login(new SaveListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if(e==null){
                                Toast.makeText(RegisterActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(MainActivity.class);
                                RegisterActivity.this.finish();
                            }
                        }
                    });
                }else{
                    Log.e("失败代码",e.toString());
                }

            }
        });
    }
    class getRongToken extends Thread{
        String userId;//定义线程内变量
        String name;
        User rongAppInformation = new User("vnroth0kvfwdo", "WheFfUdmfi1");//融云APP信息
        String Token = "";
        public getRongToken(String userId, String name){//定义带参数的构造函数,达到初始化线程内变量的值
            this.userId=userId;
            this.name=name;
        }
        @Override
        public void run() {
            try {
                //使用默认头像，但函数需要三参
                Token=rongAppInformation.getToken(userId,name,"http://bmob-cdn-8854.b0.upaiyun.com/2017/01/21/910615c0405f9bd280350b57f8dc180c.png").getToken();
                rongToken=Token ;//消息内容
                handler.sendEmptyMessage(BUILDUSERFRIENDLIST);
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(FAIL);
            }
        }
    }
    /**
     * 如果用户此前没有生成好友列表的情况下，执行方法生成好友列表
     */
    private void buildUserFriendList(){
        String loginUserID = userID;
        UserFriend userFriend = new UserFriend();
        userFriend.setUserID(loginUserID);
        BmobRelation relation = new BmobRelation();
        userFriend.setUserFriend(relation);
        userFriend.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    //Toast.makeText(getApplicationContext(), "执行", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(ALLSUCCESS);
                }else{
                    //Toast.makeText(getApplicationContext(), e+"错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

