package com.walktogether.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.walktogether.R;
import com.walktogether.adapter.AroundListAdapter;
import com.walktogether.base.BaseActivity;
import com.walktogether.entity.PoiInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class AroundListActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {
    private RecyclerView aroundRecyclerView;
    private AroundListAdapter adapter;
    private LatLonPoint latLonPoint;
    private String searchKey;
    private PoiSearch.Query query;
    private PoiSearch.SearchBound searchBound;
    private ImageView aroundListBackBtn;
    private final int SEARCHAROUNDDATA=0x00,LOADRECYCLERVIEW=0x01;
    private List<PoiInfo> poiInfoList = new ArrayList<PoiInfo>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SEARCHAROUNDDATA:
                    searchAroundData(latLonPoint,searchKey);
                    break;
                case LOADRECYCLERVIEW:
                    loadAroundRecycleView();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {
        //获取传递来的定位点数据
        ArrayList<LatLonPoint> latLonPointObjList = (ArrayList<LatLonPoint>) getIntent().getSerializableExtra("latLonPoint");
        latLonPoint = latLonPointObjList.get(0);
        //获取搜索关键字
        searchKey = getIntent().getExtras().getString("criteria");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //setContentView(R.layout.test);
        setContentView(R.layout.activity_around_list);
        aroundRecyclerView = (RecyclerView) findViewById(R.id.around_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        aroundRecyclerView.setLayoutManager(layoutManager);
        handler.sendEmptyMessage(SEARCHAROUNDDATA);
        //aroundRecyclerView.setAdapter(adapter);
        aroundListBackBtn = (ImageView) findViewById(R.id.around_list_backBtn);
        aroundListBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 以Intent内定位点以及关键字获取周边数据
     */
    private void searchAroundData(LatLonPoint latLonPoint,String searchKey){
        query = new PoiSearch.Query("", searchKey, "");
        //三个参数分别为搜索字符串，搜索类型，搜索区域
        //keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this,query);
        if (latLonPoint!=null){
            searchBound = new PoiSearch.SearchBound(latLonPoint,2000);
            poiSearch.setBound(searchBound);
        }//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            Log.i("---","查询结果:"+i);
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    //poiResults = poiResult;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        /*
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                        */
                        for(PoiItem poiItem:poiItems){
                            poiInfoList.add(new PoiInfo(poiItem.getTitle(),poiItem.getSnippet(),poiItem.getDistance()));
                            /*
                            Log.e("poiItem", poiItem.getTitle());
                            Log.e("poiItem", poiItem.getSnippet());
                            Log.e("poiItem", poiItem.getDistance()+"米" );
                            */
                        }
                        handler.sendEmptyMessage(LOADRECYCLERVIEW);
                        /*
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        //showSuggestCity(suggestionCities);
                        */
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到结果",Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "该距离内没有找到结果",Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("---","查询结果:"+i);
            Toast.makeText(getApplicationContext(), "异常代码---"+i,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
    /**
     * 加载好友信息至RecycleView
     */
    private void loadAroundRecycleView(){
        adapter = new AroundListAdapter(getApplicationContext(),poiInfoList,aroundRecyclerView);
        adapter.setOnItemClickListener(new AroundListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        aroundRecyclerView.setAdapter(adapter);
    }
}
