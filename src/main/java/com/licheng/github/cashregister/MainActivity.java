package com.licheng.github.cashregister;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.licheng.github.cashregister.adapter.ExpandBaseAdapter;
import com.licheng.github.cashregister.bean.CommodityBean;
import com.licheng.github.cashregister.bean.CommodityBeanList;
import com.licheng.github.cashregister.database.CommodityDB;
import com.licheng.github.cashregister.design.BuyTwoGiveOne;
import com.licheng.github.cashregister.design.Discount;
import com.licheng.github.cashregister.design.NineFiveDiscount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String G_TEXT	= "G_TEXT";
    //private static final String G_CB	= "G_CB";
    private static final String C_TITLE = "C_TITLE";
    private static final String C_TEXT	= "C_TEXT";
    private static final String C_CB	= "C_CB";

    private static String[] Discounts = {"买二送一","95折","其他"};

    List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

    List<Map<String, Boolean>> groupCheckBox = new ArrayList<Map<String,Boolean>>();
    List<List<Map<String, Boolean>>> childCheckBox = new ArrayList<List<Map<String,Boolean>>>();

    List<String> commodityInputs = new ArrayList<>();
    ExpandBaseAdapter adapter;
    ExpandableListView exList;

    private CommodityDB db;

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

        db = new CommodityDB(getApplicationContext());
        //清空商品库
        db.clear();

        //商品入库
        db.insert("苹果","5","ITEM000001","水果","16");
        db.insert("羽毛球","1","ITEM000002","运动器材","200");
        db.insert("篮球","188","ITEM000003","运动器材","15");
        db.insert("可口可乐","3","ITEM000004","饮料","134");
        db.insert("雪碧","4","ITEM000005","饮料","156");

        //从数据库查询
        String name = db.getCommodityByBarcode("ITEM000003").getCommodityName();
        Log.i("MainActivity",name);


        //初始化数据Group Text,Child Title Text
        for (int i = 0; i < 3; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(G_TEXT, Discounts[i]);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            if("买二送一".equals(Discounts[i])){
                Map<String, String> curChildMap1 = new HashMap<String, String>();
                curChildMap1.put(C_TITLE,"ITEM000001-5");
                curChildMap1.put(C_TEXT,db.getCommodityByBarcode("ITEM000001").getCommodityName());
                Map<String, String> curChildMap2 = new HashMap<String, String>();
                curChildMap2.put(C_TITLE,"ITEM000004-3");
                curChildMap2.put(C_TEXT,db.getCommodityByBarcode("ITEM000004").getCommodityName());
                children.add(curChildMap1);
                children.add(curChildMap2);
            }
            if("95折".equals(Discounts[i])){
                Map<String, String> curChildMap1 = new HashMap<String, String>();
                curChildMap1.put(C_TITLE,"ITEM000001-2");
                curChildMap1.put(C_TEXT,db.getCommodityByBarcode("ITEM000001").getCommodityName());
                Map<String, String> curChildMap2 = new HashMap<String, String>();
                curChildMap2.put(C_TITLE,"ITEM000003-7");
                curChildMap2.put(C_TEXT,db.getCommodityByBarcode("ITEM000003").getCommodityName());
                children.add(curChildMap1);
                children.add(curChildMap2);
            }
            if("其他".equals(Discounts[i])){
                Map<String, String> curChildMap1 = new HashMap<String, String>();
                curChildMap1.put(C_TITLE,"ITEM000002-5");
                curChildMap1.put(C_TEXT,db.getCommodityByBarcode("ITEM000002").getCommodityName());
                Map<String, String> curChildMap2 = new HashMap<String, String>();
                curChildMap2.put(C_TITLE,"ITEM000005-5");
                curChildMap2.put(C_TEXT,db.getCommodityByBarcode("ITEM000005").getCommodityName());
                children.add(curChildMap1);
                children.add(curChildMap2);
            }

            childData.add(children);
        }
        //初始化数据checkBox
        for ( int i = 0; i < 5; i++) {
            //Map<String, Boolean> curGB = new HashMap<String, Boolean> ();
            //groupCheckBox.add(curGB);
            //curGB.put(G_CB, false);
            List<Map<String, Boolean>> childCB = new ArrayList<Map<String,Boolean>>();
            for (int j = 0; j < 5; j++) {
                Map<String, Boolean> curCB = new HashMap<String, Boolean>();
                childCB.add(curCB);
                curCB.put(C_CB, false); //全不选
            }
            childCheckBox.add(childCB);
        }

        adapter = new ExpandBaseAdapter(MainActivity.this,
                groupData, childData, groupCheckBox, childCheckBox);
        exList  = (ExpandableListView) findViewById(R.id.layoutExListView);
        exList.setAdapter(adapter);

        // 展开所有二级列表
        int groupCount = adapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            exList.expandGroup(i);
        }
        // 监听二级列表
        exList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                CheckBox checkBox = (CheckBox) v.findViewById(R.id.multiple_checkbox);
                checkBox.toggle();

                if (childCheckBox.get(groupPosition).get(childPosition).get(C_CB)) {
                    childCheckBox.get(groupPosition).get(childPosition).put(C_CB, false);
                }
                else {
                    childCheckBox.get(groupPosition).get(childPosition).put(C_CB, true);
                }
                commodityInputs.clear();
                for (int i=0; i < adapter.getGroupCount(); i++) {
                    for (int j=0; j < adapter.getChildrenCount(i); j++) {
                        if (childCheckBox.get(i).get(j).get(C_CB)) {
                            commodityInputs.add(childData.get(i).get(j).get(C_TITLE));
                        }
                    }

                }
                return false;
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
            String[] inputs = new String[commodityInputs.size()];
            for (int i = 0; i < commodityInputs.size(); i++) {
                inputs[i] = commodityInputs.get(i);
            }
            //扔进计算仓库计算
            Toast.makeText(getApplicationContext(),calulate(inputs),Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //收银机结算价格
    public String calulate(String[] inputs){
        //根据条形码扫描的条形码生成json数据格式
        List<CommodityBean> commodityList = new ArrayList<>();
        CommodityBean commodity = null;
        for (int i = 0; i < inputs.length; i++) {
            commodity = new CommodityBean();
            String barcode = inputs[i];
            if(barcode.contains("-")){
                String[] bar = barcode.split("-");
                commodity.setCommodityBarcode(bar[0]);
                commodity.setCommodityCount(bar[1]);
                commodity.setCommodityPrice(db.getCommodityByBarcode(bar[0]).getCommodityPrice());
            }else {
                commodity.setCommodityBarcode(barcode);
                commodity.setCommodityCount("0");
                commodity.setCommodityPrice(db.getCommodityByBarcode(barcode).getCommodityPrice());
            }
            commodityList.add(commodity);
        }
        String jsonBody = new Gson().toJson(commodityList);
        String json = "{" +
                "\"commodityBeanList\":" + jsonBody +"}";
        System.out.println(json);
        //把json转换成类
        CommodityBeanList toCalculateList = new Gson().fromJson(json,CommodityBeanList.class);

        Discount buytwogiveoneDiscount = new BuyTwoGiveOne();
        Discount ninefiveDiscount = new NineFiveDiscount();

//        //"买二赠一"仓库商品设置
//        List<CommodityBean> buyTwoGiveOneWarehouse = new ArrayList<>();
//        BuyTwoGiveOne buyTwo = new BuyTwoGiveOne();
//        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000001"));
//        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000004"));
//        buyTwo.setmWarehouse(buyTwoGiveOneWarehouse);
//
//        //"95折"仓库商品设置
//        List<CommodityBean> nineFiveWarehouse = new ArrayList<>();
//        NineFiveDiscount nineFive = new NineFiveDiscount();
//        nineFiveWarehouse.add(new CommodityBean("ITEM000001"));
//        nineFiveWarehouse.add(new CommodityBean("ITEM000003"));
//        nineFive.setmWarehouse(nineFiveWarehouse);



        //商品优惠总价格
        double price = 0.0;
        //商品不优惠的总价格
        double priceWithoutDiscount = 0.0;
        //购物结算时的“买二赠一”商品
        List<CommodityBean> buytwo = new ArrayList<>();
        //购物结算时的“95折”商品
        List<CommodityBean> ninefive = new ArrayList<>();

        //结果清单
        StringBuilder sb = new StringBuilder();
        sb.append("***<没钱赚商店>购物清单***\n");

        for (int i = 0; i < toCalculateList.getCommodityBeanList().size(); i++) {
            String barcode = toCalculateList.getCommodityBeanList().get(i).getCommodityBarcode();
            String name = db.getCommodityByBarcode(barcode).getCommodityName();
            String count = toCalculateList.getCommodityBeanList().get(i).getCommodityCount();
            String price1 = toCalculateList.getCommodityBeanList().get(i).getCommodityPrice();
            double c = Double.valueOf(count);
            double d = Double.valueOf(price1);
            priceWithoutDiscount += c * d;
            if(buytwogiveoneDiscount.containCommodity(barcode) && ninefiveDiscount.containCommodity(barcode)){
                //"买二赠一"价格计算
                double price2 = buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
                price += price2;
//                buytwo.add(toCalculateList.getCommodityBeanList().get(i));
//                ninefive.add(toCalculateList.getCommodityBeanList().get(i));
                sb.append("名称:"+name+",数量:"+count+",单价:"+price1+"(元),小计:"+String.valueOf(price2)+"(元)\n");
            }else if(buytwogiveoneDiscount.containCommodity(barcode)){
                //"买二赠一"价格计算
                double price2 = buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
                price += price2;
                buytwo.add(toCalculateList.getCommodityBeanList().get(i));
                sb.append("名称:"+name+",数量:"+count+",单价:"+price1+"(元),小计:"+String.valueOf(price2)+"(元)\n");
            }else if(ninefiveDiscount.containCommodity(barcode)){
                //"95折"价格计算
                double price2 = ninefiveDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
                price += price2;
                ninefive.add(toCalculateList.getCommodityBeanList().get(i));
                sb.append("名称:"+name+",数量:"+count+",单价:"+price1+"(元),小计:"+String.valueOf(price2)+"(元)\n");
            }else{
                //“无优惠”原价计算
                double a = Double.valueOf(toCalculateList.getCommodityBeanList().get(i).getCommodityPrice());
                double b = Double.valueOf(toCalculateList.getCommodityBeanList().get(i).getCommodityCount());
                price +=  a * b;
                sb.append("名称:"+name+",数量:"+count+",单价:"+price1+"(元),小计:"+String.valueOf(a*b)+"(元)\n");
            }
        }
        sb.append("----------------------\n");
        sb.append("买二赠一商品:\n");
        for (int i = 0; i < buytwo.size(); i++) {

            sb.append("名称:"+db.getCommodityByBarcode(buytwo.get(i).getCommodityBarcode()).getCommodityName()+",数量:"+buytwo.get(i).getCommodityCount()+"\n");
        }
        sb.append("\n");
        sb.append("95折商品:\n");
        for (int i = 0; i < ninefive.size(); i++) {
            sb.append("名称:"+db.getCommodityByBarcode(ninefive.get(i).getCommodityBarcode()).getCommodityName()+",数量:"+ninefive.get(i).getCommodityCount()+"\n");
        }
        sb.append("----------------------\n");
        sb.append("总计:"+price+"(元)\n");
        sb.append("节省:"+(priceWithoutDiscount-price)+"(元)\n");
        System.out.println(sb.toString());
        return sb.toString();
    }




    //测试
    public static void main(String[] args) {
        //根据条形码扫描的条形码生成json数据格式
        List<CommodityBean> commodityList = new ArrayList<>();
        CommodityBean commodity = null;
        String[] inputs = {"ITEM000001-5","ITEM000001-1"
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
//        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000001"));
//        buyTwoGiveOneWarehouse.add(new CommodityBean("ITEM000004"));
        BuyTwoGiveOne buyTwoGiveOne = new BuyTwoGiveOne();
//        buyTwoGiveOne.setWarehouse(buyTwoGiveOneWarehouse);
        //"95折"仓库商品设置
        List<CommodityBean> nineFiveWarehouse = new ArrayList<>();
//        nineFiveWarehouse.add(new CommodityBean("ITEM000001"));
//        nineFiveWarehouse.add(new CommodityBean("ITEM000003"));
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
                price += buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
            }else if(buytwogiveoneDiscount.containCommodity(barcode)){
                //"买二赠一"价格计算
                price += buytwogiveoneDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
            }else if(ninefiveDiscount.containCommodity(barcode)){
                //"95折"价格计算
                price += ninefiveDiscount.calculate(toCalculateList.getCommodityBeanList().get(i));
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
