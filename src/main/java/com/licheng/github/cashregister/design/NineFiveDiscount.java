package com.licheng.github.cashregister.design;

import com.licheng.github.cashregister.bean.CommodityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licheng on 1/3/16.
 */
public class NineFiveDiscount implements Discount {

    //“95折”优惠商品存储仓库
    private List<CommodityBean> mWarehouse = new ArrayList<>();

    public List<CommodityBean> getWarehouse() {
        return mWarehouse;
    }

    public void setWarehouse(List<CommodityBean> warehouse) {
        this.mWarehouse = warehouse;

    }

    public NineFiveDiscount() {
        mWarehouse.add(new CommodityBean("ITEM000001"));
        mWarehouse.add(new CommodityBean("ITEM000003"));
    }

    private String mBarcode;

    @Override
    public double calculate(List<CommodityBean> commodityList) {
        double commodityPriceCount = 0.0;
        if(containCommodity(mBarcode)){
            for (int i = 0; i < commodityList.size(); i++) {
                //只计算“95折”的条形码
                if(mBarcode.equals(commodityList.get(i).getCommodityBarcode())){
                    double commodityPrice = Double.valueOf(commodityList.get(i).getCommodityPrice());
                    double commodityCount = Double.valueOf(commodityList.get(i).getCommodityCount());
                    commodityPriceCount = commodityCount * commodityPrice * 0.95;
                }
            }
        }
        return commodityPriceCount;
    }

    @Override
    public boolean containCommodity(String barcode) {
        mBarcode = barcode;
        for (int i = 0; i < mWarehouse.size(); i++) {
            if(mBarcode.equals(mWarehouse.get(i).getCommodityBarcode())){
                return true;
            }
        }
        return false;
    }
}
