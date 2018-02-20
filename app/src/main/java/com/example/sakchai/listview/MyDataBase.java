package com.example.sakchai.listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by Sakchai on 11/10/2017 AD.
 */

public class MyDataBase extends SQLiteOpenHelper{
    public static final int VERSION=1;

    public MyDataBase(Context context) {
        super(context, "TEST2.db", null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 'List2' ('id' INTEGER,'name' TEXT ,'lastname' TEXT,'nickname' TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
