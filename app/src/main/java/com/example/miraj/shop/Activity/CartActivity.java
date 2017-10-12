package com.example.miraj.shop.Activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.miraj.shop.Fragment.CartProductFragment;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private DBProvider dbProvider;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbProvider = new DBProvider(this);
        products = dbProvider.getRecentViewedProducts();

        LinearLayout productList = (LinearLayout) findViewById(R.id.productList);
        for (Product product: products) {
            Fragment fragment = CartProductFragment.newInstance(product);
            getSupportFragmentManager().beginTransaction().add(R.id.productList, fragment).commit();
        }
    }
}
