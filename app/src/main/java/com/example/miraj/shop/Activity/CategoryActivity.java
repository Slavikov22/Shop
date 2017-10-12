package com.example.miraj.shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.miraj.shop.Fragment.ProductFragment;
import com.example.miraj.shop.Fragment.ProductsFragment;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.Provider.FakeProvider;
import com.example.miraj.shop.Helper.ViewHelper;
import com.example.miraj.shop.R;

import java.util.List;

public class CategoryActivity
        extends AppCompatActivity
        implements
        ProductsFragment.OnFragmentInteractionListener,
        ProductFragment.OnFragmentInteractionListener
{
    public static final String ARG_CATEGORY_ID = "category_id";

    private Category category;
    private ProductFragment productFragment;
    private ProductsFragment productsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setSupportActionBar((Toolbar) findViewById(R.id.menuFragment));

        DBProvider dbProvider = new DBProvider(this);
        try {
            category = dbProvider.getCategory(getIntent().getExtras().getInt(ARG_CATEGORY_ID));
        } catch (Exception e) {}

        List<Product> products = new FakeProvider(this).getProducts(category);

        for (Product product : products) {
            dbProvider.addProduct(product);
        }

        productsFragment = ProductsFragment.newInstance(products);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentList, productsFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void nextProduct(Product currentProduct) {
        try {
            Product product = productsFragment.getNextProduct(currentProduct);
            hideProduct(ProductFragment.LEFT);
            showProduct(product);
        } catch (IndexOutOfBoundsException e) {}
    }

    @Override
    public void prevProduct(Product currentProduct) {
        try {
            Product product = productsFragment.getPrevProduct(currentProduct);
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