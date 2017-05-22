package com.walktogether.entity;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PoiInfo {
    private String title;
    private String snippet;
    private int distance;
    public PoiInfo(String title,String snippet,int distance){
        this.title = title;
        this.snippet = snippet;
        this.distance = distance;
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
}
