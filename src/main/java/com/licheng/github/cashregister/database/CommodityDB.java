package com.licheng.github.cashregister.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.licheng.github.cashregister.bean.CommodityBean;

/**
 * Created by licheng on 3/3/16.
 */
public class CommodityDB {

    DataBaseHelper helper;

    public CommodityDB(Context context) {
        helper = new DataBaseHelper(context);
    }

    //插入商品信息
    public void insert(String name,String price,String barcode,String category,String count){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("barcode",barcode);
        contentValues.put("category",category);
        contentValues.put("count",count);
        db.insert("commodity",null,contentValues);
        db.close();
    }

    //根据条形码获取商品信息
    public CommodityBean getCommodityByBarcode(String barcode){
        SQLiteDatabase db = helper.getReadableDatabase();
        CommodityBean commodityBean = new CommodityBean();
        Cursor cursor = db.rawQuery("select * from commodity where barcode = ?",new String[]{barcode});
        if(cursor != null){
            while (cursor.moveToNext()){
                commodityBean.setCommodityPrice(cursor.getString(cursor.getColumnIndex("price")));
                commodityBean.setCommodityCount(cursor.getString(cursor.getColumnIndex("count")));
                commodityBean.setCommodityBarcode(cursor.getString(cursor.getColumnIndex("barcode")));
                commodityBean.setCommodityCatetory(cursor.getString(cursor.getColumnIndex("category")));
                commodityBean.setCommodityName(cursor.getString(cursor.getColumnIndex("name")));
            }
        }
        cursor.close();
        db.close();
        return commodityBean;
    }

    //清空数据库
    public void clear(){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "delete from commodity";
        db.execSQL(sql);
        db.close();
    }
}
