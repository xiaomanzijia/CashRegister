package com.licheng.github.cashregister;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.licheng.github.cashregister.bean.CommodityBean;
import com.licheng.github.cashregister.bean.CommodityBeanList;
import com.licheng.github.cashregister.design.BuyTwoGiveOne;
import com.licheng.github.cashregister.design.Discount;
import com.licheng.github.cashregister.design.NineFiveDiscount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void main(String[] args) {
        //根据条形码扫描的条形码生成json数据格式
        List<CommodityBean> commodityList = new ArrayList<>();
        CommodityBean commodity = null;
        String[] inputs = {"ITEM000001-5","ITEM000001-1","ITEM000001-3","ITEM000001-5","ITEM000001-4"
                ,"ITEM000001-7","ITEM000004-9","ITEM000003-2","ITEM000005-3","ITEM000005-2"};
        for (int i = 0; i < inputs.length; i++) {
            commodity = new CommodityBean();
            String barcode = inputs[i];
            if(barcode.contains("-")){
                String[] bar = barcode.split("-");
                commodity.setCommodityBarcode(bar[0]);
                commodity.setCommodityCount(bar[1]);
                commodity.setCommodityPrice("3");
            }else {
                commodity.setCommodityBarcode(barcode);
                commodity.setCommodityCount("0");
                commodity.setCommodityPrice("3");
            }
            commodityList.add(commodity);
        }
        String jsonBody = new Gson().toJson(commodityList);
        String json = "{" +
                "\"commodityBeanList\":" + jsonBody +"}";
        System.out.println(json);
        //把json转换成类
        CommodityBeanList toCalculateList = new Gson().fromJson(json,CommodityBeanList.class);
        //"买二赠一"仓库商品设置
        List<CommodityBean> buyTwoGiveOneWarehouse = new ArrayList<>();
        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000001"));
        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000004"));
        BuyTwoGiveOne buyTwoGiveOne = new BuyTwoGiveOne();
//        buyTwoGiveOne.setWarehouse(buyTwoGiveOneWarehouse);
        //"95折"仓库商品设置
        List<CommodityBean> nineFiveWarehouse = new ArrayList<>();
        nineFiveWarehouse.add(new CommodityBean("ITEM000001"));
        nineFiveWarehouse.add(new CommodityBean("ITEM000003"));
        NineFiveDiscount nineFiveDiscount = new NineFiveDiscount();
//        nineFiveDiscount.setWarehouse(nineFiveWarehouse);

        Discount buytwogiveoneDiscount = new BuyTwoGiveOne();
        Discount ninefiveDiscount = new NineFiveDiscount();

        //商品总价格
        double price = 0.0;

        for (int i = 0; i < toCalculateList.getCommodityBeanList().size(); i++) {
            String barcode = toCalculateList.getCommodityBeanList().get(i).getCommodityBarcode();
            if(buytwogiveoneDiscount.containCommodity(barcode) && ninefiveDiscount.containCommodity(barcode)){
                //"买二赠一"价格计算
                price += buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList());
            }else if(buytwogiveoneDiscount.containCommodity(barcode)){
                //"买二赠一"价格计算
                price += buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList());
            }else if(ninefiveDiscount.containCommodity(barcode)){
                //"95折"价格计算
                price += ninefiveDiscount.calculate(toCalculateList.getCommodityBeanList());
            }else{
                //“无优惠”原价计算
                double a = Double.valueOf(toCalculateList.getCommodityBeanList().get(i).getCommodityPrice());
                double b = Double.valueOf(toCalculateList.getCommodityBeanList().get(i).getCommodityCount());
                price +=  a * b;
            }
        }

        System.out.println("商品价格:"+price);

    }
}
