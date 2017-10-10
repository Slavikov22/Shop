package com.example.miraj.shop.Provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Helper.DBHelper;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DBProvider {
    private DBHelper dbHelper;

    public DBProvider(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.FIELD_ID, product.getId());
        cv.put(DBHelper.FIELD_NAME, product.getName());
        cv.put(DBHelper.FIELD_DESCRIPTION, product.getDescription());
        cv.put(DBHelper.FIELD_PRICE, product.getPrice());
        cv.put(DBHelper.FIELD_IMAGE, BitmapHelper.getBitmapAsByteArray(product.getImage()));
        cv.put(DBHelper.FIELD_CATEGORY_ID, product.getCategory().getId());

        db.insert(DBHelper.TABLE_PRODUCT, null, cv);
        db.close();
    }

    public Product getProduct(int id) throws Exception {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DBHelper.FIELD_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(id) };
        Cursor c = db.query(DBHelper.TABLE_PRODUCT, null,
                selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(DBHelper.FIELD_NAME));
            String description = c.getString(c.getColumnIndex(DBHelper.FIELD_DESCRIPTION));
            int price = c.getInt(c.getColumnIndex(DBHelper.FIELD_PRICE));

            byte[] imageBytes = c.getBlob(c.getColumnIndex(DBHelper.FIELD_IMAGE));
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            Category category = getCategory(c.getInt(c.getColumnIndex(DBHelper.FIELD_CATEGORY_ID)));

            c.close();
            db.close();
            return new Product(id, name, description, price, image, category);
        } else {
            c.close();
            db.close();
            throw new Exception("Product not found");
        }
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.FIELD_ID, category.getId());
        cv.put(DBHelper.FIELD_NAME, category.getName());

        db.insert(DBHelper.TABLE_CATEGORY, null, cv);
        db.close();
    }

    public Category getCategory(int id) throws Exception {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DBHelper.FIELD_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(id) };
        Cursor c = db.query(DBHelper.TABLE_CATEGORY, null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(DBHelper.FIELD_NAME));

            c.close();
            db.close();
            return new Category(id, name);
        } else {
            c.close();
            db.close();
            throw new Exception("Category not found");
        }
    }

    public void addRecentViewedProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_PRODUCT_ID, product.getId());
        cv.put(DBHelper.FIELD_ADD_TIME, System.currentTimeMillis());

        String selection = DBHelper.FIELD_PRODUCT_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(product.getId()) };

        Cursor c = db.query(DBHelper.TABLE_RECENT_VIEWED_PRODUCT, null,
                selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            db.update(DBHelper.TABLE_RECENT_VIEWED_PRODUCT, cv, selection, selectionArgs);
        } else {
            db.insert(DBHelper.TABLE_RECENT_VIEWED_PRODUCT, null, cv);
        }

        c.close();
        db.close();
    }

    public void removeRecentViewedProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = DBHelper.FIELD_PRODUCT_ID + " = ?";

        db.delete(DBHelper.TABLE_RECENT_VIEWED_PRODUCT, where,
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }

    public List<Product> getRecentViewedProducts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String orderBy = DBHelper.FIELD_ADD_TIME;
        Cursor c = db.query(DBHelper.TABLE_RECENT_VIEWED_PRODUCT, null, null, null, null, null, orderBy);

        List<Product> products = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                int product_id = c.getInt(c.getColumnIndex(DBHelper.FIELD_PRODUCT_ID));

                try {
                    products.add(getProduct(product_id));
                } catch (Exception e) {
                    // TODO: Delete product_id from table
                }
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return products;
    }
}
