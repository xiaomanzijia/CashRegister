package com.licheng.github.cashregister.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by licheng on 1/3/16.
 */
public class CommodityBean {
    @SerializedName("commodityName")
    private String commodityName;

    @SerializedName("commodityCount")
    private String commodityCount;

    @SerializedName("commodityPrice")
    private String commodityPrice;

    @SerializedName("commodityCatetory")
    private String commodityCatetory;

    @SerializedName("commodityBarcode")
    private String commodityBarcode;

    public CommodityBean() {
    }

    public CommodityBean(String commodityName, String commodityPrice, String commodityBarcode) {
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
        this.commodityBarcode = commodityBarcode;
    }

    public CommodityBean(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(String commodityCount) {
        this.commodityCount = commodityCount;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityCatetory() {
        return commodityCatetory;
    }

    public void setCommodityCatetory(String commodityCatetory) {
        this.commodityCatetory = commodityCatetory;
    }

    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode;
    }
}
