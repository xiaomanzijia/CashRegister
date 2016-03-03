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

    public NineFiveDiscount() {
        mWarehouse.add(new CommodityBean("ITEM000001"));
        mWarehouse.add(new CommodityBean("ITEM000003"));
    }

    public void setmWarehouse(List<CommodityBean> mWarehouse) {
        this.mWarehouse = mWarehouse;
    }

    private String mBarcode;

    @Override
    public double calculate(CommodityBean commodity) {
        double commodityPriceCount = 0.0;
        double commodityPrice = Double.valueOf(commodity.getCommodityPrice());
        double commodityCount = Double.valueOf(commodity.getCommodityCount());
        commodityPriceCount = commodityCount * commodityPrice * 0.95;
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
