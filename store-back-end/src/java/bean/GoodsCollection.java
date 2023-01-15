package com.xzr.glassfishDemo.bean;

import java.io.Serializable;

public class GoodsCollection implements Serializable {

    private int goodscol_id;

    private int collection_id;

    private int goodsid;

    private int goodsnum;

    private String goodsinfo;

    public int getGoodscol_id() {
        return goodscol_id;
    }

    public void setGoodscol_id(int goodscol_id) {
        this.goodscol_id = goodscol_id;
    }

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public int getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public void setGoodsinfo(String goodsinfo) {
        this.goodsinfo = goodsinfo;
    }

    public String getGoodsinfo() {
        return goodsinfo;
    }

    @Override
    public String toString() {
        return "我删了，没想到吧";
    }
}
