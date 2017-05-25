package com.walktogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amap.api.services.core.LatLonPoint;
import com.walktogether.R;
import com.walktogether.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AroundActivity extends BaseActivity implements View.OnClickListener {
    private ImageView aroundBackBtn;
    private Button searchShopBtn;
    private ArrayList<LatLonPoint> latLonPointObjList = new ArrayList<LatLonPoint>();
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_around);
        //获取传递来的定位点数据
        latLonPointObjList = (ArrayList<LatLonPoint>) getIntent().getSerializableExtra("latLonPoint");
        Log.e("AroundActivity", ""+latLonPointObjList.size());
        aroundBackBtn = (ImageView) findViewById(R.id.around_backBtn);
        aroundBackBtn.setOnClickListener(this);
        searchShopBtn = (Button) findViewById(R.id.around_search_shopBtn);
        searchShopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.around_backBtn:
                finish();
                break;
            case R.id.around_search_shopBtn:
                Intent intent = new Intent(this,AroundListActivity.class);
                intent.putExtra("criteria","购物服务");
                intent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(intent);
                finish();
                break;
        }
    }
}
