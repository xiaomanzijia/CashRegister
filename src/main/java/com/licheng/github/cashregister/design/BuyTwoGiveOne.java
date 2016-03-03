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

    public void setmWarehouse(List<CommodityBean> mWarehouse) {
        this.mWarehouse = mWarehouse;
    }

    public BuyTwoGiveOne() {
        mWarehouse.add(new CommodityBean("ITEM000001"));
        mWarehouse.add(new CommodityBean("ITEM000004"));
    }


    private String mBarcode;

    //TODO:每买2个商品赠送1个商品
    @Override
    public double calculate(CommodityBean commodity) {
        double commodityPriceCount = 0.0;
        double commodityPrice = Double.valueOf(commodity.getCommodityPrice());
        double commodityCount = Double.valueOf(commodity.getCommodityCount());
        int a = (int) (commodityCount * 2);
        int b = 0;
        if(a % 3 == 0){
            b = a / 3;
        }else {
            b = a / 3 + 1;
        }
        commodityPriceCount = b * commodityPrice;
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
