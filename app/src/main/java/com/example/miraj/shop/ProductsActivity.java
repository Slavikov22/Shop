package com.example.miraj.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miraj.shop.Adapter.ProductAdapter;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.FakeProvider;
import com.example.miraj.shop.Provider.IProductProvider;
import com.example.miraj.shop.Helper.ViewHelper;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private static final String TAG = "Products";

    private Category category;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Bundle extras = getIntent().getExtras();

        category = new Category(extras.getString("Category"));

        IProductProvider provider = new FakeProvider();
        products = provider.getProducts(category);

        ListView productList = (ListView) findViewById(R.id.productList);
        ProductAdapter adapter = new ProductAdapter(this, R.layout.list_product, products);
        productList.setAdapter(adapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductFragment productFragment = new ProductFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", products.get(i));
                productFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, productFragment)
                        .commit();

                ViewHelper.setEnabledAllViews(getWindow().getDecorView().getRootView(), false);
            }
        });
    }
}
