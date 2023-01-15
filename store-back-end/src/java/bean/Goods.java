package com.xzr.glassfishDemo.bean;

import javax.lang.model.element.NestingKind;

public class Goods {

    private String shopname;

    private String cover;

    public String getShopname(String shopname){return this.shopname;}

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
