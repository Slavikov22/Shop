package com.example.miraj.shop.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "shop";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_RECENT_VIEWED_PRODUCT = "recent_viewed_product";
    public static final String TABLE_BASKET_PRODUCT = "basket_product";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_CATEGORY_ID = "category_id";
    public static final String FIELD_PRODUCT_ID = "product_id";
    public static final String FIELD_COUNT = "count";
    public static final String FIELD_ADD_TIME = "add_time";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_NAME + " TEXT, " +
                FIELD_DESCRIPTION + " TEXT, " +
                FIELD_PRICE + " INTEGER, " +
                FIELD_IMAGE + " BLOB, " +
                FIELD_CATEGORY_ID + " INTEGER " +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_CATEGORY + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_NAME + " TEXT" +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_RECENT_VIEWED_PRODUCT + " (" +
                FIELD_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                FIELD_ADD_TIME + " INTEGER" +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_BASKET_PRODUCT + " (" +
                FIELD_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                FIELD_COUNT + " INTEGER" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
