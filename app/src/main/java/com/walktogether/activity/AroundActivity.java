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

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AroundActivity extends BaseActivity implements View.OnClickListener {
    private ImageView aroundBackBtn;
    private Button searchShopBtn,searchRestaurantBtn,searchHotelBtn,searchGymBtn,searchParkingLotBtn;
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
        searchRestaurantBtn = (Button) findViewById(R.id.around_search_restaurantBtn);
        searchRestaurantBtn.setOnClickListener(this);
        searchHotelBtn = (Button) findViewById(R.id.around_search_hotelBtn);
        searchHotelBtn.setOnClickListener(this);
        searchGymBtn = (Button) findViewById(R.id.around_search_gymBtn);
        searchGymBtn.setOnClickListener(this);
        searchParkingLotBtn = (Button) findViewById(R.id.around_search_parkingLotBtn);
        searchParkingLotBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.around_backBtn:
                finish();
                break;
            case R.id.around_search_shopBtn:
                Intent shopIntent = new Intent(this,AroundListActivity.class);
                shopIntent.putExtra("criteria","购物服务");
                shopIntent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(shopIntent);
                finish();
                break;
            case R.id.around_search_restaurantBtn:
                Intent restaurantIntent = new Intent(this,AroundListActivity.class);
                restaurantIntent.putExtra("criteria","餐饮服务");
                restaurantIntent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(restaurantIntent);
                finish();
                break;
            case R.id.around_search_hotelBtn:
                Intent hotelIntent = new Intent(this,AroundListActivity.class);
                hotelIntent.putExtra("criteria","住宿服务");
                hotelIntent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(hotelIntent);
                finish();
                break;
            case R.id.around_search_gymBtn:
                Intent gymIntent = new Intent(this,AroundListActivity.class);
                gymIntent.putExtra("criteria","体育休闲服务");
                gymIntent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(gymIntent);
                finish();
                break;
            case R.id.around_search_parkingLotBtn:
                Intent trafficIntent = new Intent(this,AroundListActivity.class);
                trafficIntent.putExtra("criteria","交通设施服务");
                trafficIntent.putExtra("latLonPoint", latLonPointObjList);
                startActivity(trafficIntent);
                finish();
                break;
        }
    }
}
