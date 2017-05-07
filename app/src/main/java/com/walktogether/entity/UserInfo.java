package com.walktogether.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/3/23.
 * 用户表实体
 */

public class UserInfo extends BmobUser {
    private String userNickname;
    private String userPhone;
    private String rongToken;
    //用户头像，先设置初始头像
    private String userAvatar="http://bmob-cdn-8854.b0.upaiyun.com/2017/01/21/910615c0405f9bd280350b57f8dc180c.png";

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
