package com.example.miraj.shop.Provider;

import android.content.Context;
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
            products.add(new Product(3, "Asfgdf", "Desctiption", 3000, BitmapFactory.decodeResource(context.getResources(), R.drawable.a), category));
            products.add(new Product(4, "DFgdfg", "Desctiption", 4000, BitmapFactory.decodeResource(context.getResources(), R.drawable.b), category));
            products.add(new Product(5, "CVbcv", "Desctiption", 5000, BitmapFactory.decodeResource(context.getResources(), R.drawable.c), category));
            products.add(new Product(6, "AAAA", "Desctiption", 6000, BitmapFactory.decodeResource(context.getResources(), R.drawable.d), category));
            products.add(new Product(7, "QQQ", "Desctiption", 800, BitmapFactory.decodeResource(context.getResources(), R.drawable.e), category));
            products.add(new Product(8, "Bvbn", "Desctiption", 900, BitmapFactory.decodeResource(context.getResources(), R.drawable.f), category));

            return products;
        }
        else return null;
    }
}
