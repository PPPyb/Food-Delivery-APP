package com.xzr.glassfishDemo.bean;

import java.io.Serializable;

public class Token implements Serializable {

    private int userid;
    private String token;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "我删了，没想到吧";
    }
}
