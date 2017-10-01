package com.example.miraj.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CategoriesActivity extends AppCompatActivity {
    String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categories = getResources().getStringArray(R.array.categories);

        ListView categoryList = (ListView) findViewById(R.id.categoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, categories);
        categoryList.setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choiceCategory(categories[i]);
            }
        });
    }

    protected void choiceCategory(String category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}
