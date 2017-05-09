package com.walktogether.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/4.
 */

public abstract class BaseActivity extends Activity {
    //是否设置沉浸式
    private boolean ifActivityImmersive = false;
    private final int REQUEST_ACCESS_LOCATION=0x00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariablesAndService();
        initViews(savedInstanceState);
        if(ifActivityImmersive){
            initImmersive();
        }
    }
    //初始化变量数据及服务
    protected abstract void initVariablesAndService();
    //初始化控件
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     */
    private void initImmersive () {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     * 设置是否沉浸式  在子类中super调用
     */

    public void setActivityImmersive(boolean ifActivityImmersive){
        this.ifActivityImmersive = ifActivityImmersive;
    }

    /**
     * 跳转到新页面
     */
    protected void startActivity(Class classes){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), classes);
        //设置要跳转到的页面以及跳转时的动画
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
    /**
     * 判断是否拥有指定权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    /**
     * 请求权限
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
        Toast.makeText(this,"如果拒绝授权,会导致应用无法正常使用",Toast.LENGTH_SHORT).show();
        //ToastUtil.showMessage(this, "如果拒绝授权,会导致应用无法正常使用", Toast.Length_SHORT);
    }
    /**
     * 请求授权的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ACCESS_LOCATION:
                //例子：请求定位
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"授权成功，请继续使用应用",Toast.LENGTH_SHORT).show();
                    // 这里写你需要的业务逻辑
                } else {
                    Toast.makeText(this,"您拒绝授权,会导致应用无法正常使用，您可在设置中进行授权或者再次打开软件以完成授权",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}