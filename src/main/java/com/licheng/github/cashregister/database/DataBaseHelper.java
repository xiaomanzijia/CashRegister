package com.licheng.github.cashregister.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by licheng on 3/3/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "mydb.db"; //数据库名称
    private static final int version = 1; //数据库版本


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table commodity(name varchar(20) not null , count varchar(20) not null , price varchar(20) not null" +
                ", category varchar(20) not null, barcode varchar(20) not null );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
