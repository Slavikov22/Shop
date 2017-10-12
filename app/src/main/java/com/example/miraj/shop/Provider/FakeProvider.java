package com.example.miraj.shop.Provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

import java.util.ArrayList;
import java.util.List;

public class FakeProvider implements IProductProvider {
    private Context context;

    public FakeProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<Product> getProducts(Category category) {
        if (category.getName().equals("Laptops")) {
            List<Product> products = new ArrayList<>();
            products.add(new Product(0, "Lenovo", "Desctiption", 2000, BitmapFactory.decodeResource(context.getResources(), R.drawable.lenovo), category));
            products.add(new Product(1, "Asus", "Desctiption", 1800, BitmapFactory.decodeResource(context.getResources(), R.drawable.asus), category));
            products.add(new Product(2, "HP", "Desctiption", 1000, BitmapFactory.decodeResource(context.getResources(), R.drawable.hp), category));
            products.add(new Product(3, "A", "Desctiption", 3000, BitmapFactory.decodeResource(context.getResources(), R.drawable.a), category));
            products.add(new Product(4, "B", "Desctiption", 4000, BitmapFactory.decodeResource(context.getResources(), R.drawable.b), category));
            products.add(new Product(5, "C", "Desctiption", 5000, BitmapFactory.decodeResource(context.getResources(), R.drawable.c), category));
            products.add(new Product(6, "D", "Desctiption", 6000, BitmapFactory.decodeResource(context.getResources(), R.drawable.d), category));
            products.add(new Product(7, "E", "Desctiption", 800, BitmapFactory.decodeResource(context.getResources(), R.drawable.e), category));
            products.add(new Product(8, "F", "Desctiption", 900, BitmapFactory.decodeResource(context.getResources(), R.drawable.f), category));
            products.add(new Product(9, "G", "Desctiption", 3000, BitmapFactory.decodeResource(context.getResources(), R.drawable.a), category));
            products.add(new Product(10, "H", "Desctiption", 4000, BitmapFactory.decodeResource(context.getResources(), R.drawable.b), category));
            products.add(new Product(11, "Z", "Desctiption", 5000, BitmapFactory.decodeResource(context.getResources(), R.drawable.c), category));
            products.add(new Product(12, "X", "Desctiption", 6000, BitmapFactory.decodeResource(context.getResources(), R.drawable.d), category));
            products.add(new Product(13, "V", "Desctiption", 3000, BitmapFactory.decodeResource(context.getResources(), R.drawable.a), category));
            products.add(new Product(14, "O", "Desctiption", 4000, BitmapFactory.decodeResource(context.getResources(), R.drawable.b), category));
            products.add(new Product(15, "P", "Desctiption", 5000, BitmapFactory.decodeResource(context.getResources(), R.drawable.c), category));
            products.add(new Product(16, "AAAA", "Desctiption", 6000, BitmapFactory.decodeResource(context.getResources(), R.drawable.d), category));

            return products;
        }
        else return null;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(0, "Laptops", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(1, "A", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(2, "B", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(3, "C", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(4, "D", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(5, "E", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(6, "F", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(7, "G", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(8, "H", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(9, "K", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));
        categories.add(new Category(10, "L", BitmapFactory.decodeResource(context.getResources(), R.drawable.d)));

        return categories;
    }
}
