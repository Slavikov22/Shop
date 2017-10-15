package com.example.miraj.shop.Provider;

import android.content.Context;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.R;

import java.util.ArrayList;

public class CategoryProvider {
    private Context context;

    public CategoryProvider(Context context) {
        this.context = context;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : context.getResources().getStringArray(R.array.categories))
            categories.add(new Category(name));

        return categories;
    }
}
