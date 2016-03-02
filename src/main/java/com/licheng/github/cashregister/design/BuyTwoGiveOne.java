package com.licheng.github.cashregister.design;

import com.licheng.github.cashregister.bean.CommodityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licheng on 1/3/16.
 */
public class BuyTwoGiveOne implements Discount{
    //“买二赠一”优惠商品存储仓库
    private List<CommodityBean> mWarehouse = new ArrayList<>();


    public List<CommodityBean> getWarehouse() {
        return mWarehouse;
    }

    public void setWarehouse(List<CommodityBean> warehouse) {
        this.mWarehouse = warehouse;

    }

    public BuyTwoGiveOne() {
        mWarehouse.add(new CommodityBean("ITEM000001"));
        mWarehouse.add(new CommodityBean("ITEM000004"));
    }

    private String mBarcode;

    //TODO:每买2个商品赠送1个商品  算法有误
    @Override
    public double calculate(List<CommodityBean> commodityList) {
        double commodityPriceCount = 0.0;
        if(containCommodity(mBarcode)){
            for (int i = 0; i < commodityList.size(); i++) {
                //只计算“买二赠一”的条形码
                if(mBarcode.equals(commodityList.get(i).getCommodityBarcode())){
                    double commodityPrice = Double.valueOf(commodityList.get(i).getCommodityPrice());
                    double commodityCount = Double.valueOf(commodityList.get(i).getCommodityCount());
                    int a = (int) (commodityCount / 2);
                    if(commodityCount % 2 == 0){
                        commodityPriceCount = (a - 1) * 2 * commodityPrice;
                    }else {
                        commodityPriceCount = a * 2 * commodityPrice;
                    }
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
