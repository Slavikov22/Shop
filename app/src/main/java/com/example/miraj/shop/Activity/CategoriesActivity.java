package com.example.miraj.shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.miraj.shop.Fragment.CategoriesFragment;
import com.example.miraj.shop.Fragment.ProductFragment;
import com.example.miraj.shop.Fragment.RecentViewedProductsFragment;
import com.example.miraj.shop.Helper.DBHelper;
import com.example.miraj.shop.Helper.ViewHelper;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.ProductProvider;
import com.example.miraj.shop.R;

import java.util.ArrayList;

public class CategoriesActivity
        extends AppCompatActivity
        implements
        CategoriesFragment.OnFragmentInteractionListener,
        RecentViewedProductsFragment.OnFragmentInteractionListener,
        ProductFragment.OnFragmentInteractionListener
{
    private static final String TAG = "CATEGORIES_ACTIVITY";

    private ProductFragment productFragment;
    private RecentViewedProductsFragment recentViewedProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recentViewedProductsFragment = (RecentViewedProductsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recentViewedProductsFragment);

        Log.i(TAG, "Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroyed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Restarted");
    }

    @Override
    public void showCategory(Category category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.ARG_CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void nextProduct(Product currentProduct) {
        try {
            Product product = recentViewedProductsFragment.getNextProduct(currentProduct);
            hideProduct(ProductFragment.LEFT);
            showProduct(product);
        } catch (IndexOutOfBoundsException e) {}
    }

    @Override
    public void prevProduct(Product currentProduct) {
        try {
            Product product = recentViewedProductsFragment.getPrevProduct(currentProduct);
            hideProduct(ProductFragment.RIGHT);
            showProduct(product);
        } catch (IndexOutOfBoundsException e) {}
    }

    @Override
    public void closeProduct() {
        hideProduct(ProductFragment.TOP);
    }

    @Override
    public void showProduct(Product product) {
        if (productFragment != null) return;
        ViewHelper.setEnabledAllViews(getWindow().getDecorView().getRootView(), false);

        productFragment = ProductFragment.newInstance(product);
        getSupportFragmentManager().beginTransaction().add(R.id.container, productFragment).commit();
    }

    public void hideProduct(int direction) {
        productFragment.closeAnimation(direction);
        getSupportFragmentManager().beginTransaction().remove(productFragment).commit();
        productFragment = null;
        ViewHelper.setEnabledAllViews(getWindow().getDecorView().getRootView(), true);
    }
}
