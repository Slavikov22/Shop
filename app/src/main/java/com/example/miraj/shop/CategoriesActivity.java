package com.example.miraj.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miraj.shop.Adapter.CategoryAdapter;
import com.example.miraj.shop.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        setInitialData();

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

    protected void choiceCategory(Category category) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("Category", category.getName());
        startActivity(intent);
    }

    private void setInitialData() {
        for (String category:getResources().getStringArray(R.array.categories)) {
            categories.add(new Category(category));
        }
    }


}
