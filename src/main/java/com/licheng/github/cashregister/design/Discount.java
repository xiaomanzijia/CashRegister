package com.licheng.github.cashregister.design;

import com.licheng.github.cashregister.bean.CommodityBean;

import java.util.List;

/**
 * Created by licheng on 1/3/16.
 */
public interface Discount {
    //计算商品优惠价格
    double calculate(CommodityBean commodity);
    //根据商品条形码判断是否存在该商品优惠
    boolean containCommodity(String barcode);
}
