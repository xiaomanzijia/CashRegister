package com.licheng.github.cashregister.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by licheng on 1/3/16.
 */
public class CommodityBeanList {
    @SerializedName("commodityBeanList")
    private List<CommodityBean> commodityBeanList;

    public List<CommodityBean> getCommodityBeanList() {
        return commodityBeanList;
    }

    public void setCommodityBeanList(List<CommodityBean> commodityBeanList) {
        this.commodityBeanList = commodityBeanList;
    }
}
