package com.example.miraj.shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.miraj.shop.Adapter.CategoryAdapter;
import com.example.miraj.shop.Helper.DBHelper;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private static final int MAX_RECENT_VIEWED_PRODUCTS = 10;

    private List<Category> categories = new ArrayList<>();
    private List<Product> recentViewedProducts = new ArrayList<>();
    private DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setInitialData();

//        this.deleteDatabase(DBHelper.DB_NAME);

        ListView categoryList = (ListView) findViewById(R.id.categoryList);
        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.list_category, categories);
        categoryList.setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choiceCategory(categories.get(i));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateRecentViewedProducts();
    }

    protected void updateRecentViewedProducts() {
        recentViewedProducts = dbProvider.getRecentViewedProducts();

        for (int i = 0; i < recentViewedProducts.size() - MAX_RECENT_VIEWED_PRODUCTS; i++)
            dbProvider.removeRecentViewedProduct(recentViewedProducts.get(0));

        recentViewedProducts = dbProvider.getRecentViewedProducts();
        Collections.reverse(recentViewedProducts);

        for (Product product : recentViewedProducts) {
            View view = View.inflate(this, R.layout. recent_viewed_product, null);
            ((ImageView) view.findViewById(R.id.image)).setImageBitmap(product.getImage());
            ((TextView) view.findViewById(R.id.name)).setText(product.getName());

            LinearLayout container = (LinearLayout) findViewById(R.id.recentViewedProducts);
            container.addView(view);
        }
    }

    protected void choiceCategory(Category category) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("category_id", category.getId());
        intent.putExtra("category_name", category.getName());
        startActivity(intent);
    }

    protected void setInitialData() {
        dbProvider = new DBProvider(this);
        String[] categoryNames = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categoryNames.length; i++) {
            Category category = new Category(i, categoryNames[i]);
            categories.add(category);
            dbProvider.addCategory(category);
        }
    }
}
