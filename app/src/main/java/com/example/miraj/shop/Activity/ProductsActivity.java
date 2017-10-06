package com.example.miraj.shop.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miraj.shop.Adapter.ProductAdapter;
import com.example.miraj.shop.Fragment.ProductFragment;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.FakeProvider;
import com.example.miraj.shop.Provider.IProductProvider;
import com.example.miraj.shop.Helper.ViewHelper;
import com.example.miraj.shop.R;

import java.util.List;

public class ProductsActivity extends AppCompatActivity
                              implements ProductFragment.OnProductEventListener{
    private static final String CATEGORY_ARG = "Category";

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    private Category category;
    private List<Product> products;

    private ProductFragment productFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        category = new Category(getIntent().getExtras().getString(CATEGORY_ARG));
        products = new FakeProvider().getProducts(category);

        ListView productList = (ListView) findViewById(R.id.productList);
        ProductAdapter adapter = new ProductAdapter(this, R.layout.list_product, products);
        productList.setAdapter(adapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openProductFragment(products.get(i));
            }
        });
    }

    @Override
    public void nextProduct(Product currentProduct) {
        int index = products.indexOf(currentProduct) + 1;
        if (index >= products.size()) return;

        closeCurrentProductFragment(LEFT);
        openProductFragment(products.get(index));
    }

    @Override
    public void prevProduct(Product currentProduct) {
        int index = products.indexOf(currentProduct) - 1;
        if (index < 0) return;

        closeCurrentProductFragment(RIGHT);
        openProductFragment(products.get(index));
    }

    @Override
    public void closeProduct() {
        closeCurrentProductFragment(UP);
    }

    public void closeCurrentProductFragment(int direction) {
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fragment_product_alpha_down));

        switch (direction) {
            case LEFT:
                set.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fragment_product_swipe_left));
                break;
            case RIGHT:
                set.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fragment_product_swipe_right));
                break;
            case UP:
                set.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fragment_product_swipe_top));
                break;
        }

        set.setDuration(200);

        ProductFragment fragment = getProductFragment();
        if (fragment.getView() != null)
            fragment.getView().startAnimation(set);

        getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        setProductFragment(null);

        ViewHelper.setEnabledAllViews(getWindow().getDecorView().getRootView(), true);
    }

    public void openProductFragment(Product product) {
        if (getProductFragment() != null) return;

        ViewHelper.setEnabledAllViews(getWindow().getDecorView().getRootView(), false);

        ProductFragment fragment = ProductFragment.newInstance(product);
        setProductFragment(fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    public ProductFragment getProductFragment() {
        return productFragment;
    }

    public void setProductFragment(ProductFragment productFragment) {
        this.productFragment = productFragment;
    }
}
