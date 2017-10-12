package com.example.miraj.shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.example.miraj.shop.Fragment.CategoriesFragment;
import com.example.miraj.shop.Fragment.ProductFragment;
import com.example.miraj.shop.Fragment.RecentViewedProductsFragment;
import com.example.miraj.shop.Helper.DBHelper;
import com.example.miraj.shop.Helper.ViewHelper;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

public class CategoriesActivity
        extends AppCompatActivity
        implements
        CategoriesFragment.OnFragmentInteractionListener,
        RecentViewedProductsFragment.OnFragmentInteractionListener,
        ProductFragment.OnFragmentInteractionListener
{
    private ProductFragment productFragment;
    private RecentViewedProductsFragment recentViewedProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories);
        setSupportActionBar((Toolbar) findViewById(R.id.menuFragment));

        recentViewedProductsFragment = (RecentViewedProductsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recentViewedProductsFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void showCategory(Category category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.ARG_CATEGORY_ID, category.getId());
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
