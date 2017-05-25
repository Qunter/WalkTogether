package com.walktogether.entity;

import com.amap.api.services.core.LatLonPoint;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PoiInfo {
    private String title;
    private String snippet;
    private int distance;
    private LatLonPoint point;
    public PoiInfo(String title,String snippet,int distance,LatLonPoint point){
        this.title = title;
        this.snippet = snippet;
        this.distance = distance;
        this.point = point;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LatLonPoint getPoint() {
        return point;
    }

    public void setPoint(LatLonPoint point) {
        this.point = point;
    }
}
